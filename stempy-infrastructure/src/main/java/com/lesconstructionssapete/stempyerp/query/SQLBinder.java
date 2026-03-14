package com.lesconstructionssapete.stempyerp.query;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import com.lesconstructionssapete.stempyerp.exception.SQLBindingException;

public final class SQLBinder {

  private SQLBinder() {
  }

  /**
   * Binds the provided SQL parameters to the given PreparedStatement.
   * 
   * @param stmt   The PreparedStatement to which the parameters will be bound.
   * @param params The list of SQL parameters to bind.
   * @throws SQLBindingException if an SQLException occurs during parameter
   *                             binding.
   */
  public static void bind(PreparedStatement stmt, List<SQLBuilder.SQLParam> params) {

    int idx = 1;

    for (SQLBuilder.SQLParam p : params) {
      try {
        stmt.setObject(idx++, p.value(), p.sqlType());
      } catch (SQLException e) {
        throw new SQLBindingException("Error binding SQL parameter: " + p.value(), e);
      }
    }

  }
}
