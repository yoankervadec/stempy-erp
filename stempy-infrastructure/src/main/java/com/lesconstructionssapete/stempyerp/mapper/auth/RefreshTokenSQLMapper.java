package com.lesconstructionssapete.stempyerp.mapper.auth;

import com.lesconstructionssapete.stempyerp.domain.auth.AuthToken;
import com.lesconstructionssapete.stempyerp.field.auth.RefreshTokenField;
import com.lesconstructionssapete.stempyerp.field.auth.RefreshTokenSQLField;
import com.lesconstructionssapete.stempyerp.query.SQLBuilder;

public final class RefreshTokenSQLMapper {

  private RefreshTokenSQLMapper() {
  }

  public static void bindInsert(SQLBuilder builder, AuthToken token) {
    builder
        .bind(RefreshTokenSQLField.get(RefreshTokenField.USER_ID), token.getUserId())
        .bind(RefreshTokenSQLField.get(RefreshTokenField.TOKEN), token.getRefreshToken())
        .bind(RefreshTokenSQLField.get(RefreshTokenField.EXPIRES_AT), token.getRefreshTokenExpiresAt())
        .bind(RefreshTokenSQLField.get(RefreshTokenField.ENABLED), token.isEnabled());
  }

  public static void bindUpdate(SQLBuilder builder, AuthToken token) {
    builder
        .bind(RefreshTokenSQLField.get(RefreshTokenField.USER_ID), token.getUserId())
        .bind(RefreshTokenSQLField.get(RefreshTokenField.TOKEN), token.getToken())
        .bind(RefreshTokenSQLField.get(RefreshTokenField.EXPIRES_AT), token.getRefreshTokenExpiresAt())
        .bind(RefreshTokenSQLField.get(RefreshTokenField.ENABLED), token.isEnabled());
  }

}
