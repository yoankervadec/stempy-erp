package com.lesconstructionssapete.stempyerp.infrastructure.mapper.authentication;

import com.lesconstructionssapete.stempyerp.auth.AuthToken;
import com.lesconstructionssapete.stempyerp.field.auth.RefreshTokenField;
import com.lesconstructionssapete.stempyerp.infrastructure.field.authentication.RefreshTokenSQLField;
import com.lesconstructionssapete.stempyerp.infrastructure.query.SQLBuilder;

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
