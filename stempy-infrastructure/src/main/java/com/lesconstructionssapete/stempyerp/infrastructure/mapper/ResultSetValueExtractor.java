package com.lesconstructionssapete.stempyerp.infrastructure.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.lesconstructionssapete.stempyerp.domain.field.DomainField;

public final class ResultSetValueExtractor {

  private ResultSetValueExtractor() {
  }

  public static Object extract(ResultSet rs, DomainField field) throws SQLException {

    String column = field.qualifiedColumnName();

    switch (field.sqlType) {
      case java.sql.Types.VARCHAR -> {
        return rs.getString(column);
      }
      case java.sql.Types.INTEGER -> {
        int intValue = rs.getInt(column);
        return rs.wasNull() ? null : intValue;
      }
      case java.sql.Types.BIGINT -> {
        long longValue = rs.getLong(column);
        return rs.wasNull() ? null : longValue;
      }
      case java.sql.Types.DOUBLE -> {
        double doubleValue = rs.getDouble(column);
        return rs.wasNull() ? null : doubleValue;
      }
      case java.sql.Types.DECIMAL -> {
        return rs.getBigDecimal(column);
      }
      case java.sql.Types.BOOLEAN -> {
        boolean booleanValue = rs.getBoolean(column);
        return rs.wasNull() ? null : booleanValue;
      }
      case java.sql.Types.TIMESTAMP -> {
        java.sql.Timestamp timestamp = rs.getTimestamp(column);
        return timestamp != null ? timestamp.toInstant() : null;
      }
      default -> {
        return rs.getObject(column);
      }
    }

  }

}
