package com.lesconstructionssapete.stempyerp.infrastructure.mapper.auth;

import java.sql.ResultSet;
import java.util.function.BiConsumer;

import com.lesconstructionssapete.stempyerp.domain.auth.AuthToken;
import com.lesconstructionssapete.stempyerp.domain.field.EntityField;
import com.lesconstructionssapete.stempyerp.infrastructure.mapper.EntityMapper;

public final class AuthTokenMapper {

  private AuthTokenMapper() {
  }

  public static AuthToken fromResultSet(ResultSet rs) {
    return new AuthToken(
        EntityMapper.read(rs, AuthToken.Fields.ID),
        EntityMapper.read(rs, AuthToken.Fields.USER_ID),
        null,
        EntityMapper.read(rs, AuthToken.Fields.TOKEN),
        EntityMapper.read(rs, AuthToken.Fields.REFRESH_TOKEN_EXPIRES_AT),
        EntityMapper.read(rs, AuthToken.Fields.ENABLED),
        EntityMapper.read(rs, AuthToken.Fields.CREATED_AT));
  }

  public static void bind(AuthToken t, BiConsumer<EntityField, Object> binder) {
    binder.accept(AuthToken.Fields.ID, t.getId());
    binder.accept(AuthToken.Fields.USER_ID, t.getUserId());
    binder.accept(AuthToken.Fields.TOKEN, t.getRefreshToken());
    binder.accept(AuthToken.Fields.REFRESH_TOKEN_EXPIRES_AT, t.getRefreshTokenExpiresAt());
    binder.accept(AuthToken.Fields.ENABLED, t.isEnabled());
    binder.accept(AuthToken.Fields.CREATED_AT, t.getCreatedAt());
  }

}
