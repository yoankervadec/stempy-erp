package com.lesconstructionssapete.stempyerp.mapper.auth;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.lesconstructionssapete.stempyerp.domain.auth.AuthToken;
import com.lesconstructionssapete.stempyerp.field.auth.RefreshTokenField;

public final class RefreshTokenRowMapper {

  private RefreshTokenRowMapper() {
  }

  public static AuthToken map(ResultSet rs) throws SQLException {
    return new AuthToken(
        rs.getLong(RefreshTokenField.ID.columnName()),
        rs.getLong(RefreshTokenField.USER_ID.columnName()),
        null,
        rs.getString(RefreshTokenField.TOKEN.columnName()),
        rs.getTimestamp(RefreshTokenField.EXPIRES_AT.columnName()).toLocalDateTime(),
        rs.getBoolean(RefreshTokenField.ENABLED.columnName()),
        rs.getTimestamp(RefreshTokenField.CREATED_AT.columnName()).toLocalDateTime());
  }

}
