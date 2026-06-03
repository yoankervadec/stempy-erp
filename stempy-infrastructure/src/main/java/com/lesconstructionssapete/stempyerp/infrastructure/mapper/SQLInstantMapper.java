package com.lesconstructionssapete.stempyerp.infrastructure.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.Instant;

import com.lesconstructionssapete.stempyerp.domain.field.EntityField;
import com.lesconstructionssapete.stempyerp.infrastructure.query.SQLBuilder;

public final class SQLInstantMapper {

  private SQLInstantMapper() {
  }

  public static Instant read(ResultSet rs, String columnName) throws SQLException {
    return rs.getTimestamp(columnName).toInstant();
  }

  public static void write(SQLBuilder builder, EntityField field, Instant instant) {
    builder.bind(field, Timestamp.from(instant));

  }

}
