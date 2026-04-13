package com.lesconstructionssapete.stempyerp.infrastructure.mapper.authentication;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.lesconstructionssapete.stempyerp.domain.auth.AuthToken;
import com.lesconstructionssapete.stempyerp.domain.field.auth.RefreshTokenField;
import com.lesconstructionssapete.stempyerp.infrastructure.field.authentication.RefreshTokenSQLField;
import com.lesconstructionssapete.stempyerp.infrastructure.mapper.SQLInstantMapper;

public final class RefreshTokenRowMapper {

  private RefreshTokenRowMapper() {
  }

  public static AuthToken map(ResultSet rs) throws SQLException {
    return new AuthToken(
        rs.getLong(RefreshTokenSQLField.get(RefreshTokenField.ID).columnName()),
        rs.getLong(RefreshTokenSQLField.get(RefreshTokenField.USER_ID).columnName()),
        null,
        rs.getString(RefreshTokenSQLField.get(RefreshTokenField.TOKEN).columnName()),
        SQLInstantMapper.read(rs, RefreshTokenSQLField.get(RefreshTokenField.EXPIRES_AT).columnName()),
        rs.getBoolean(RefreshTokenSQLField.get(RefreshTokenField.ENABLED).columnName()),
        SQLInstantMapper.read(rs, RefreshTokenSQLField.get(RefreshTokenField.CREATED_AT).columnName()));
  }

}
