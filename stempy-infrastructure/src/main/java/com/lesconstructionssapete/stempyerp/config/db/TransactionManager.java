package com.lesconstructionssapete.stempyerp.config.db;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Savepoint;
import java.util.ArrayDeque;
import java.util.Deque;

import com.lesconstructionssapete.stempyerp.db.ConnectionProvider;
import com.lesconstructionssapete.stempyerp.exception.DatabaseAccessException;
import com.lesconstructionssapete.stempyerp.exception.DomainException;
import com.lesconstructionssapete.stempyerp.exception.InternalException;
import com.lesconstructionssapete.stempyerp.exception.TransactionFailureException;
import com.lesconstructionssapete.stempyerp.transaction.TransactionCallback;
import com.lesconstructionssapete.stempyerp.transaction.TransactionPropagation;
import com.lesconstructionssapete.stempyerp.transaction.TransactionRunner;
import com.lesconstructionssapete.stempyerp.transaction.TransactionVoidCallback;

/**
 * Manages database transactions with support for different propagation
 * behaviors.
 * 
 * <p>
 * This class provides a simple API to execute code within transactional
 * boundaries, handling connection management, commit/rollback, and exception
 * classification.
 *
 * <p>
 * Supports nested transactions with proper isolation:
 * <ul>
 * <li>REQUIRED: Joins existing transaction or creates new one</li>
 * <li>REQUIRES_NEW: Always creates a new transaction, suspending any existing
 * one</li>
 * <li>SUPPORTS: Joins existing transaction if exists, otherwise executes
 * non-transactionally</li>
 * </ul>
 *
 */

public class TransactionManager implements TransactionRunner {

  // Transaction context holding connection and state information
  private static class TransactionContext {
    final Connection connection;
    boolean rollbackOnly;
    Savepoint savepoint;
    int savepointCounter = 0;

    TransactionContext(Connection connection) {
      this.connection = connection;
    }
  }

  private final ConnectionProvider provider;

  // Thread-local stack for managing nested transactions
  private static final ThreadLocal<Deque<TransactionContext>> CONTEXT_STACK = ThreadLocal.withInitial(ArrayDeque::new);

  /**
   * Creates a new TransactionManager with the given connection provider.
   *
   * @param provider the connection provider to use for acquiring database
   *                 connections
   */
  public TransactionManager(ConnectionProvider provider) {
    this.provider = provider;
  }

  /**
   * Executes the given callback within a transaction according to the specified
   * propagation behavior.
   *
   * @param propagation the transaction propagation behavior
   * @param callback    the code to execute within the transaction
   * @return the result of the callback execution
   * @throws TransactionFailureException if the transaction fails due to an
   *                                     unexpected error
   * @throws DomainException             if the callback throws a domain-specific
   *                                     exception
   * @throws InternalException           if the callback throws an internal
   *                                     exception
   * @throws DatabaseAccessException     if a database access error occurs
   */

  @Override
  public <T> T execute(
      TransactionPropagation propagation,
      TransactionCallback<T> callback) {

    Deque<TransactionContext> stack = CONTEXT_STACK.get();
    TransactionContext currentContext = stack.peek();

    return switch (propagation) {

      case REQUIRED -> {
        if (currentContext != null) {
          yield executeInExisting(currentContext, callback);
        }
        yield startNew(callback);
      }

      case REQUIRES_NEW -> {
        yield executeRequiresNew(callback);
      }

      case SUPPORTS -> {
        if (currentContext == null) {
          yield executeWithoutTransaction(callback);
        }
        yield executeInExisting(currentContext, callback);
      }

      default -> throw new TransactionFailureException("Unknown propagation type",
          new IllegalStateException("Unexpected propagation: " + propagation));
    };
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void executeVoid(
      TransactionPropagation propagation,
      TransactionVoidCallback callback) {
    execute(propagation, connection -> {
      callback.execute(connection);
      return null;
    });
  }

  // -------------------------
  // Core Execution Strategies
  // -------------------------

  private <T> T executeInExisting(
      TransactionContext context,
      TransactionCallback<T> callback) {

    Connection connection = context.connection;
    try {
      return callback.execute(connection);
    } catch (Exception e) {
      context.rollbackOnly = true;
      throw classify(e);
    }
  }

  private <T> T executeWithoutTransaction(
      TransactionCallback<T> callback) {

    Connection connection = null;
    try {
      connection = provider.getConnection();
      return callback.execute(connection);
    } catch (Exception e) {
      throw classify(e);
    } finally {
      closeQuietly(connection);
    }
  }

  private <T> T executeRequiresNew(
      TransactionCallback<T> callback) {

    Deque<TransactionContext> stack = CONTEXT_STACK.get();
    Deque<TransactionContext> suspendedStack = new ArrayDeque<>(stack);
    stack.clear(); // Suspend current transaction stack

    Connection connection = null;
    TransactionContext newContext = null;

    try {
      connection = provider.getConnection();
      connection.setAutoCommit(false);

      newContext = new TransactionContext(connection);
      stack.push(newContext);

      T result = callback.execute(connection);

      if (newContext.rollbackOnly) {
        connection.rollback();
      } else {
        connection.commit();
      }

      return result;
    } catch (Exception e) {
      if (connection != null) {
        try {
          connection.rollback();
        } catch (SQLException sqle) {
          System.err.println("Failed to rollback transaction: " + sqle.getMessage());
        }
      }
      throw classify(e);
    } finally {
      if (stack.peek() == newContext) {
        stack.pop();
      }

      // Restore suspended transaction if any
      if (!suspendedStack.isEmpty()) {
        stack.addAll(suspendedStack);
      }

      closeQuietly(connection);

      // Clean up thread locals if stack is empty
      if (stack.isEmpty()) {
        CONTEXT_STACK.remove();
      }
    }
  }

  private <T> T startNew(
      TransactionCallback<T> callback) {

    Deque<TransactionContext> stack = CONTEXT_STACK.get();
    Connection connection = null;
    TransactionContext context = null;

    try {
      connection = provider.getConnection();
      connection.setAutoCommit(false);

      context = new TransactionContext(connection);
      stack.push(context);

      T result = callback.execute(connection);

      if (context.rollbackOnly) {
        connection.rollback();
      } else {
        connection.commit();
      }

      return result;
    } catch (Exception e) {
      if (connection != null) {
        try {
          connection.rollback();
        } catch (SQLException sqle) {
          System.err.println("Failed to rollback transaction: " + sqle.getMessage());
        }
      }
      throw classify(e);
    } finally {
      if (stack.peek() == context) {
        stack.pop();
      }
      closeQuietly(connection);

      // Clean up thread locals if stack is empty
      if (stack.isEmpty()) {
        CONTEXT_STACK.remove();
      }
    }
  }

  // -------------------------
  // Helpers
  // -------------------------

  /**
   * Gets the current connection associated with the active transaction.
   *
   * @return the current connection or null if no transaction is active
   */
  public Connection currentConnection() {
    Deque<TransactionContext> stack = CONTEXT_STACK.get();
    TransactionContext context = stack.peek();
    return context != null ? context.connection : null;
  }

  private void closeQuietly(Connection connection) {
    if (connection != null) {
      try {
        connection.setAutoCommit(true);
      } catch (SQLException ignored) {
      }
      try {
        connection.close();
      } catch (SQLException e) {
        System.err.println("Failed to close connection: " + e.getMessage());
      }
    }
  }

  private RuntimeException classify(Exception e) {
    if (e instanceof DomainException domainException) {
      return domainException;
    }

    if (e instanceof InternalException internalException) {
      return internalException;
    }

    if (e instanceof SQLException sqlEx) {
      return new DatabaseAccessException(
          "The operation could not be completed due to a database error",
          sqlEx);
    }

    // ---- UNEXPECTED PROGRAMMING ERROR ----
    return new TransactionFailureException(
        "Unexpected failure inside transactional boundary",
        e);
  }
}