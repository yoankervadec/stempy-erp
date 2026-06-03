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

  @SuppressWarnings("unchecked")
  public static <T> T read(ResultSet rs, EntityField field) throws SQLException {

    String col = field.qualifiedColumnName();
    Class<?> type = field.javaType();

    if (type == Long.class || type == long.class)
      return (T) (Long) rs.getLong(col);
    if (type == Integer.class || type == int.class)
      return (T) (Integer) rs.getInt(col);
    if (type == String.class)
      return (T) rs.getString(col);
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
  }

  public static void bind(PreparedStatement ps, int index,
      int sqlType, Object value) throws SQLException {
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
