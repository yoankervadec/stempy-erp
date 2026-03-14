package com.lesconstructionssapete.stempyerp.mapper.auth;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.lesconstructionssapete.stempyerp.domain.auth.AuthToken;
import com.lesconstructionssapete.stempyerp.field.auth.RefreshTokenField;
import com.lesconstructionssapete.stempyerp.mapper.SQLInstantMapper;

public final class RefreshTokenRowMapper {

  private RefreshTokenRowMapper() {
  }

  public static AuthToken map(ResultSet rs) throws SQLException {
    return new AuthToken(
        rs.getLong(RefreshTokenField.ID.columnName()),
        rs.getLong(RefreshTokenField.USER_ID.columnName()),
        null,
        rs.getString(RefreshTokenField.TOKEN.columnName()),
        SQLInstantMapper.read(rs, RefreshTokenField.EXPIRES_AT.columnName()),
        rs.getBoolean(RefreshTokenField.ENABLED.columnName()),
        SQLInstantMapper.read(rs, RefreshTokenField.CREATED_AT.columnName()));
  }

}
