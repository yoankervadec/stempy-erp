package com.lesconstructionssapete.stempyerp.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.Instant;

import com.lesconstructionssapete.stempyerp.field.SQLField;
import com.lesconstructionssapete.stempyerp.query.SQLBuilder;

public final class SQLInstantMapper {

  private SQLInstantMapper() {
  }

  public static Instant read(ResultSet rs, String columnName) throws SQLException {
    return rs.getTimestamp(columnName).toInstant();
  }

  public static void write(SQLBuilder builder, SQLField field, Instant instant) {
    builder.bind(field, Timestamp.from(instant));

  }

}
