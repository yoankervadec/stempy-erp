package com.lesconstructionssapete.stempyerp.core.config.db;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayDeque;
import java.util.Deque;

public class TransactionManager {

  private static final ThreadLocal<Deque<Connection>> CONNECTION_STACK = ThreadLocal.withInitial(ArrayDeque::new);

  public static <T> T execute(
      TransactionPropagation propagation,
      TransactionCallback<T> callback) {

    Deque<Connection> stack = CONNECTION_STACK.get();
    Connection current = stack.peek();

    switch (propagation) {

      case REQUIRED -> {
        if (current != null) {
          try {
            return callback.executeInTransaction(current);
          } catch (Exception e) {
            throw new RuntimeException("Transaction failed", e);
          }
        }
        return startNewTransaction(callback);
      }

      case REQUIRES_NEW -> {
        return startNewTransaction(callback);
      }

      case SUPPORTS -> {
        try {
          return callback.executeInTransaction(current);
        } catch (Exception e) {
          throw new RuntimeException("Transaction failed", e);
        }
      }

      default -> throw new IllegalStateException("Unknown propagation type");
    }
  }

  public static void executeVoid(
      TransactionPropagation propagation,
      TransactionVoidCallback callback) {

    execute(propagation, connection -> {
      callback.executeInTransaction(connection);
      return null;
    });
  }

  private static <T> T startNewTransaction(TransactionCallback<T> callback) {

    Deque<Connection> stack = CONNECTION_STACK.get();
    Connection connection = null;

    try {
      connection = ConnectionPool.getConnection();
      connection.setAutoCommit(false);

      stack.push(connection);

      T result = callback.executeInTransaction(connection);

      connection.commit();
      return result;

    } catch (Exception e) {

      if (connection != null) {
        try {
          connection.rollback();
        } catch (SQLException ignored) {
        }
      }

      throw new RuntimeException("Transaction failed", e);

    } finally {

      if (connection != null) {
        try {
          connection.close();
        } catch (SQLException ignored) {
        }
      }

      stack.pop();

      if (stack.isEmpty()) {
        CONNECTION_STACK.remove();
      }
    }
  }
}
