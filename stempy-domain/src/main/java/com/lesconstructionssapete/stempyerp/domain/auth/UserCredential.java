package com.lesconstructionssapete.stempyerp.domain.auth;

import java.time.Instant;

import com.lesconstructionssapete.stempyerp.domain.field.EntityField;
import com.lesconstructionssapete.stempyerp.domain.field.FieldMeta;

public class UserCredential {

  public enum Fields implements EntityField {
    ID(FieldMeta.builder("UserCredential", "auth_user_credential")
        .field("userCredentialId", "id")
        .type(Long.class, java.sql.Types.BIGINT)
        .notNullable().notUpdatable()
        .build()),

    USER_ID(FieldMeta.builder("UserCredential", "auth_user_credential")
        .field("userId", "user_id")
        .type(Long.class, java.sql.Types.BIGINT)
        .notNullable().notUpdatable()
        .build()),

    PASSWORD(FieldMeta.builder("UserCredential", "auth_user_credential")
        .field("password", "password")
        .type(String.class, java.sql.Types.VARCHAR)
        .notNullable().notUpdatable()
        .build()),

    ENABLED(FieldMeta.builder("UserCredential", "auth_user_credential")
        .field("enabled", "enabled")
        .type(Boolean.class, java.sql.Types.BOOLEAN)
        .notNullable()
        .build()),

    CREATED_AT(FieldMeta.builder("UserCredential", "auth_user_credential")
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

  private final long userCredentialId;
  private final long userId;
  private String userNo;
  private String password;
  private boolean enabled;
  private final Instant createdAt;

  public UserCredential(
      long userCredentialId,
      long userId,
      String password,
      boolean enabled,
      Instant createdAt) {
    this.userCredentialId = userCredentialId;
    this.userId = userId;
    this.password = password;
    this.enabled = enabled;
    this.createdAt = createdAt;
  }

  public long getUserCredentialId() {
    return userCredentialId;
  }

  public long getUserId() {
    return userId;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
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

  public String getUserNo() {
    return userNo;
  }

  public void setUserNo(String userNo) {
    this.userNo = userNo;
  }

}
