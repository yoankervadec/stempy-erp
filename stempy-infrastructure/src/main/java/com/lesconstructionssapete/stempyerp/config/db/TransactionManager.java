package com.lesconstructionssapete.stempyerp.config.db;

import java.sql.Connection;
import java.sql.SQLException;
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
 */

public class TransactionManager implements TransactionRunner {

  private final ConnectionProvider provider;

  private static final ThreadLocal<Deque<Connection>> CONNECTION_STACK = ThreadLocal.withInitial(ArrayDeque::new);

  private static final ThreadLocal<Deque<Boolean>> ROLLBACK_STACK = ThreadLocal.withInitial(ArrayDeque::new);

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

    Deque<Connection> stack = CONNECTION_STACK.get();
    Connection current = stack.peek();

    return switch (propagation) {

      case REQUIRED -> {
        if (current != null) {
          yield executeInExisting(current, callback);
        }
        yield startNew(callback);
      }

      case REQUIRES_NEW -> {
        yield executeRequiresNew(callback);
      }

      case SUPPORTS -> {
        if (current == null) {
          yield executeWithoutTransaction(callback);
        }
        yield executeInExisting(current, callback);
      }

      default -> throw new TransactionFailureException("Unknown propagation type",
          new IllegalStateException("Unexpected propagation: " + propagation));
    };
  }

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
      Connection connection,
      TransactionCallback<T> callback) {

    try {
      return callback.execute(connection);
    } catch (Exception e) {
      markRollbackOnly();
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

    Deque<Connection> stack = CONNECTION_STACK.get();
    Connection connection = null;

    ROLLBACK_STACK.get().push(false); // isolate rollback state for this new transaction

    try {
      connection = provider.getConnection();
      connection.setAutoCommit(false);

      stack.push(connection);

      T result = callback.execute(connection);

      if (ROLLBACK_STACK.get().peek()) {
        connection.rollback();
      } else {
        connection.commit();
      }

      return result;
    } catch (Exception e) {
      rollbackQuietly(connection);
      throw classify(e);
    } finally {
      stack.pop();
      ROLLBACK_STACK.get().pop();
      closeQuietly(connection);
    }

  }

  private <T> T startNew(TransactionCallback<T> callback) {

    Deque<Connection> stack = CONNECTION_STACK.get();
    Connection connection = null;

    ROLLBACK_STACK.get().push(false);

    try {
      connection = provider.getConnection();
      connection.setAutoCommit(false);

      stack.push(connection);

      T result = callback.execute(connection);

      if (ROLLBACK_STACK.get().peek()) {
        connection.rollback();
      } else {
        connection.commit();
      }

      return result;

    } catch (Exception e) {
      rollbackQuietly(connection);
      throw classify(e);

    } finally {
      stack.pop();
      ROLLBACK_STACK.get().pop();
      closeQuietly(connection);

      if (stack.isEmpty()) {
        CONNECTION_STACK.remove();
        ROLLBACK_STACK.remove();
      }

    }
  }

  // -------------------------
  // Helpers
  // -------------------------
  private void markRollbackOnly() {
    Deque<Boolean> stack = ROLLBACK_STACK.get();
    if (!stack.isEmpty()) {
      stack.pop();
      stack.push(true);
    }

  }

  public Connection currentConnection() {
    return CONNECTION_STACK.get().peek();
  }

  private void rollbackQuietly(Connection connection) {
    if (connection != null) {
      try {
        connection.rollback();
      } catch (SQLException e) {
        System.err.println("Failed to rollback transaction: " + e.getMessage());
      }
    }
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
