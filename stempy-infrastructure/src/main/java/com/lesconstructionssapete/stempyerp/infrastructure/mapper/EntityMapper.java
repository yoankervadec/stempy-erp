package com.lesconstructionssapete.stempyerp.infrastructure.mapper;

import java.math.BigDecimal;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.sql.Types;
import java.time.Instant;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import com.lesconstructionssapete.stempyerp.domain.field.EntityField;

public final class EntityMapper {

  /**
   * Reads a value from the ResultSet based on the EntityField's Java type and
   * qualified column name.
   * 
   * @param <T>   The type of the value to be read.
   * @param rs    The ResultSet from which to read the value.
   * @param field The EntityField representing the column to read.
   * @return The value read from the ResultSet.
   * @throws SQLException If a database access error occurs.
   * 
   */
  @SuppressWarnings("unchecked")
  public static <T> T read(ResultSet rs, EntityField field) {

    String col = field.qualifiedColumnName();
    Class<?> type = field.javaType();

    try {
      if (type == Long.class || type == long.class)
        return (T) (Long) rs.getLong(col);
      if (type == Integer.class || type == int.class)
        return (T) (Integer) rs.getInt(col);
      if (type == String.class)
        return (T) rs.getString(col);
      if (type == Double.class || type == double.class)
        return (T) (Double) rs.getDouble(col);
      if (type == BigDecimal.class)
        return (T) rs.getBigDecimal(col);
      if (type == Boolean.class || type == boolean.class)
        return (T) (Boolean) rs.getBoolean(col);
      if (type == LocalDate.class)
        return (T) rs.getObject(col, LocalDate.class);
      if (type == Instant.class) {
        Timestamp ts = rs.getTimestamp(col);
        return (T) (ts != null ? ts.toInstant() : null);
      }

      return (T) rs.getObject(col);
    } catch (SQLException e) {
      throw new RuntimeException("Error reading field " + field.qualifiedColumnName() + " from ResultSet", e);
    }
  }

  /**
   * Binds a value to a PreparedStatement based on the specified SQL type.
   * 
   * @param ps      The PreparedStatement to which the value will be bound.
   * @param index   The parameter index (1-based) to which the value will be
   *                bound.
   * @param sqlType The SQL type of the parameter (e.g., Types.VARCHAR).
   * @param value   The value to be bound to the PreparedStatement.
   * @throws SQLException If a database access error occurs.
   */
  public static void bind(PreparedStatement ps, int index,
      int sqlType, Object value) {

    try {
      if (value == null) {
        ps.setNull(index, sqlType);
        return;
      }
      switch (sqlType) {
        case Types.BIGINT -> ps.setLong(index, (Long) value);
        case Types.INTEGER -> ps.setInt(index, (Integer) value);
        case Types.VARCHAR,
            Types.CHAR ->
          ps.setString(index, (String) value);
        case Types.DECIMAL,
            Types.NUMERIC ->
          ps.setBigDecimal(index, (BigDecimal) value);
        case Types.BOOLEAN -> ps.setBoolean(index, (Boolean) value);
        case Types.TIMESTAMP -> ps.setTimestamp(index,
            Timestamp.from((Instant) value));
        case Types.DATE -> ps.setDate(index,
            Date.valueOf((LocalDate) value));
        default -> ps.setObject(index, value, sqlType);
      }
    } catch (SQLException e) {
      throw new RuntimeException("Error binding value to PreparedStatement at index " + index, e);
    }

  }

  public static <F extends Enum<F> & EntityField> List<F> insertableFields(Class<F> entityEnum) {
    return Arrays.stream(entityEnum.getEnumConstants())
        .filter(f -> !f.isVirtual() && f.meta().isInsertable())
        .toList();
  }

  public static <F extends Enum<F> & EntityField> List<F> updatableFields(Class<F> entityEnum) {
    return Arrays.stream(entityEnum.getEnumConstants())
        .filter(f -> !f.isVirtual() && f.meta().isUpdatable())
        .toList();
  }

}
