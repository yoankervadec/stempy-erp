package com.lesconstructionssapete.stempyerp.mapper.auth;

import com.lesconstructionssapete.stempyerp.domain.auth.AuthToken;
import com.lesconstructionssapete.stempyerp.field.auth.RefreshTokenField;
import com.lesconstructionssapete.stempyerp.query.SQLBuilder;

public final class RefreshTokenSQLMapper {

  private RefreshTokenSQLMapper() {
  }

  public static void bindInsert(SQLBuilder builder, AuthToken token) {
    builder
        .bind(RefreshTokenField.USER_ID, token.getUserId())
        .bind(RefreshTokenField.TOKEN, token.getRefreshToken())
        .bind(RefreshTokenField.EXPIRES_AT, token.getRefreshTokenExpiresAt())
        .bind(RefreshTokenField.ENABLED, token.isEnabled());
  }

  public static void bindUpdate(SQLBuilder builder, AuthToken token) {
    builder
        .bind(RefreshTokenField.USER_ID, token.getUserId())
        .bind(RefreshTokenField.TOKEN, token.getToken())
        .bind(RefreshTokenField.EXPIRES_AT, token.getRefreshTokenExpiresAt())
        .bind(RefreshTokenField.ENABLED, token.isEnabled())
        .bind(RefreshTokenField.CREATED_AT, token.getCreatedAt());
  }

}
