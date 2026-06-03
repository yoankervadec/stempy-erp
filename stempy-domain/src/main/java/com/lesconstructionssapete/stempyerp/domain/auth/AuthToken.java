package com.lesconstructionssapete.stempyerp.domain.auth;

import java.time.Instant;

import com.lesconstructionssapete.stempyerp.domain.field.EntityField;
import com.lesconstructionssapete.stempyerp.domain.field.FieldMeta;

public class AuthToken {

  public enum Fields implements EntityField {

    ID(FieldMeta.builder("AuthToken", "auth_token")
        .field("id", "id")
        .type(Long.class, java.sql.Types.BIGINT)
        .notNullable()
        .build()),

    USER_ID(FieldMeta.builder("AuthToken", "auth_token")
        .field("userId", "user_id")
        .type(Long.class, java.sql.Types.BIGINT)
        .notNullable().notUpdatable()
        .build()),

    TOKEN(FieldMeta.builder("AuthToken", "auth_token")
        .field("token", "token")
        .type(String.class, java.sql.Types.VARCHAR)
        .notNullable().notUpdatable()
        .build()),

    REFRESH_TOKEN_EXPIRES_AT(FieldMeta.builder("AuthToken", "auth_token")
        .field("refreshTokenExpiresAt", "refresh_token_expires_at")
        .type(Instant.class, java.sql.Types.TIMESTAMP)
        .notNullable()
        .build()),

    ENABLED(FieldMeta.builder("AuthToken", "auth_token")
        .field("enabled", "enabled")
        .type(Boolean.class, java.sql.Types.BOOLEAN)
        .notNullable()
        .build()),

    CREATED_AT(FieldMeta.builder("AuthToken", "auth_token")
        .field("createdAt", "created_at")
        .type(Instant.class, java.sql.Types.TIMESTAMP)
        .notNullable()
        .build());

    private final FieldMeta meta;

    Fields(FieldMeta meta) {
      this.meta = meta;
    }

    @Override
    public FieldMeta meta() {
      return meta;
    }
  }

  private Long id;
  private Long userId;
  private String token;
  private String refreshToken;
  private Instant refreshTokenExpiresAt;
  private boolean enabled;
  private Instant createdAt;

  public AuthToken(
      Long id,
      Long userId,
      String token,
      String refreshToken,
      Instant refreshTokenExpiresAt,
      boolean enabled,
      Instant createdAt) {
    this.id = id;
    this.userId = userId;
    this.token = token;
    this.refreshToken = refreshToken;
    this.enabled = enabled;
    this.refreshTokenExpiresAt = refreshTokenExpiresAt;
    this.createdAt = createdAt;
  }

  public void setId(long id) {
    this.id = id;
  }

  public Long getId() {
    return id;
  }

  public void setRefreshTokenSeq(long refreshTokenSeq) {
    this.id = refreshTokenSeq;
  }

  public Long getUserId() {
    return userId;
  }

  public void setUserSeq(long userSeq) {
    this.userId = userSeq;
  }

  public String getToken() {
    return token;
  }

  public void setToken(String token) {
    this.token = token;
  }

  public String getRefreshToken() {
    return refreshToken;
  }

  public void setRefreshToken(String refreshToken) {
    this.refreshToken = refreshToken;
  }

  public Instant getRefreshTokenExpiresAt() {
    return refreshTokenExpiresAt;
  }

  public void setRefreshTokenExpiresAt(Instant expiresAt) {
    this.refreshTokenExpiresAt = expiresAt;
  }

  public boolean isEnabled() {
    return enabled;
  }

  public void setEnabled(boolean enabled) {
    this.enabled = enabled;
  }

  public Instant getCreatedAt() {
    return createdAt;
  }

  public void setCreatedAt(Instant createdAt) {
    this.createdAt = createdAt;
  }

}
