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

  public TransactionManager(ConnectionProvider provider) {
    this.provider = provider;
  }

  /**
   * Executes the given callback within a transaction according to the specified
   * propagation behavior.
   * 
   * @param <T>
   * @param propagation
   * @param callback
   * @return the result of the callback execution
   */

  @Override
  public <T> T execute(
      TransactionPropagation propagation,
      TransactionCallback<T> callback) {

    Deque<Connection> stack = CONNECTION_STACK.get();
    Connection current = stack.peek();

    return switch (propagation) {

      // If there's an existing transaction, join it; otherwise, start a new one
      case REQUIRED -> {
        if (current != null) {
          yield executeInsideExistingTransaction(current, callback);
        }
        yield startNewTransaction(callback);
      }

      // Always start a new transaction, suspending any existing one
      case REQUIRES_NEW -> startNewTransaction(callback);

      // If there's an existing transaction, join it; otherwise, execute without a
      // transaction
      case SUPPORTS -> {
        if (current == null) {
          yield executeWithoutTransaction(callback);
        }
        yield executeInsideExistingTransaction(current, callback);
      }

      default -> throw new TransactionFailureException("Unknown propagation type",
          new IllegalStateException("Unexpected propagation: " + propagation));
    };
  }

  public void executeVoid(
      TransactionPropagation propagation,
      TransactionVoidCallback callback) {

    execute(propagation, connection -> {
      callback.execute(connection);
      return null;
    });
  }

  private <T> T executeInsideExistingTransaction(
      Connection connection,
      TransactionCallback<T> callback) {

    try {
      return callback.execute(connection);
    } catch (Exception e) {
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

  private <T> T startNewTransaction(TransactionCallback<T> callback) {

    Deque<Connection> stack = CONNECTION_STACK.get();
    Connection connection = null;

    boolean pushed = false;

    try {
      connection = provider.getConnection();
      connection.setAutoCommit(false);

      stack.push(connection);
      pushed = true;

      T result = callback.execute(connection);

      connection.commit();
      return result;

    } catch (Exception e) {
      rollbackQuietly(connection);
      throw classify(e);

    } finally {
      closeQuietly(connection);
      if (pushed) {
        stack.pop();
      }
      if (stack.isEmpty()) {
        CONNECTION_STACK.remove();
      }

    }
  }

  private void rollbackQuietly(Connection connection) {
    if (connection != null) {
      try {
        connection.rollback();
      } catch (SQLException rollbackEx) {
      }
    }
  }

  private void closeQuietly(Connection connection) {
    if (connection != null) {
      try {
        connection.close();
      } catch (SQLException ignored) {
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
      throw new DatabaseAccessException(
          "The operation could not be completed due to a database error",
          sqlEx);
    }

    // ---- UNEXPECTED PROGRAMMING ERROR ----
    throw new TransactionFailureException(
        "Unexpected failure inside transactional boundary",
        e);
  }

}
