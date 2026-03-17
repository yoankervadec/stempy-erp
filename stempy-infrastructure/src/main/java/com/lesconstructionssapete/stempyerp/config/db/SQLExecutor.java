package com.lesconstructionssapete.stempyerp.config.db;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import com.lesconstructionssapete.stempyerp.exception.DatabaseAccessException;
import com.lesconstructionssapete.stempyerp.exception.DuplicateKeyException;
import com.lesconstructionssapete.stempyerp.exception.ForeignKeyViolationException;
import com.lesconstructionssapete.stempyerp.exception.NotNullConstraintException;
import com.lesconstructionssapete.stempyerp.query.SQLBinder;
import com.lesconstructionssapete.stempyerp.query.SQLBuilder;

public final class SQLExecutor {

  @FunctionalInterface
  public interface ResultSetHandler<T> {
    T handle(ResultSet rs) throws SQLException;
  }

  public static <T> T query(
      Connection connection,
      String sql,
      List<SQLBuilder.SQLParam> params,
      ResultSetHandler<T> handler) {

    try (var stmt = connection.prepareStatement(sql)) {

      SQLBinder.bind(stmt, params);

      try (var rs = stmt.executeQuery()) {
        return handler.handle(rs);
      }

    } catch (SQLException e) {
      translate(e);
      throw new IllegalStateException("Unreachable"); // required by compiler
    }
  }

  public static int update(
      Connection connection,
      String sql,
      List<SQLBuilder.SQLParam> params) {

    try (var stmt = connection.prepareStatement(sql)) {

      SQLBinder.bind(stmt, params);

      return stmt.executeUpdate();

    } catch (SQLException e) {
      translate(e);
      throw new IllegalStateException("Unreachable"); // required by compiler
    }
  }

  public static long insert(
      Connection connection,
      String sql,
      List<SQLBuilder.SQLParam> params) {

    try (var stmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

      SQLBinder.bind(stmt, params);

      stmt.executeUpdate();

      try (var rs = stmt.getGeneratedKeys()) {
        if (rs.next()) {
          return rs.getLong(1);
        }
      }

      return 0;

    } catch (SQLException e) {
      translate(e);
      throw new IllegalStateException("Unreachable"); // required by compiler
    }
  }

  public static void batch(
      Connection connection,
      String sql,
      List<List<SQLBuilder.SQLParam>> batchParams) {

    try (var stmt = connection.prepareStatement(sql)) {

      for (var params : batchParams) {
        stmt.clearParameters();

        SQLBinder.bind(stmt, params);

        stmt.addBatch();
      }

      stmt.executeBatch();

    } catch (SQLException e) {
      translate(e);
      throw new IllegalStateException("Unreachable"); // required by compiler
    }
  }

  private static void translate(SQLException e) {

    switch (e.getErrorCode()) {

      case 1048 -> throw new NotNullConstraintException("Null value not allowed", e);

      case 1062 -> throw new DuplicateKeyException("Duplicate entry", e);

      case 1451, 1453 -> throw new ForeignKeyViolationException(
          "Cannot delete or update parent row", e);

      case 1452 -> throw new ForeignKeyViolationException("Invalid reference", e);

      default -> throw new DatabaseAccessException("Database access error", e);
    }

  }
}
