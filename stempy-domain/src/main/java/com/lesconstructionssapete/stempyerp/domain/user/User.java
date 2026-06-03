package com.lesconstructionssapete.stempyerp.domain.user;

import java.time.Instant;

import com.lesconstructionssapete.stempyerp.domain.field.EntityField;
import com.lesconstructionssapete.stempyerp.domain.field.FieldMeta;
import com.lesconstructionssapete.stempyerp.domain.generic.GenericEntity;

/*
 * Full user object
 */

public class User extends GenericEntity {

  public enum Fields implements EntityField {

    ID(FieldMeta.builder("User", "auth_user")
        .field("id", "id")
        .type(Long.class, java.sql.Types.BIGINT)
        .notNullable()
        .build()),

    USER_NO(FieldMeta.builder("User", "auth_user")
        .field("userNo", "user_no")
        .type(String.class, java.sql.Types.VARCHAR)
        .notNullable()
        .build()),

    USER_NAME(FieldMeta.builder("User", "auth_user")
        .field("userName", "user_name")
        .type(String.class, java.sql.Types.VARCHAR)
        .notNullable()
        .build()),

    ENABLED(FieldMeta.builder("User", "auth_user")
        .field("enabled", "enabled")
        .type(Boolean.class, java.sql.Types.BOOLEAN)
        .notNullable()
        .build()),

    CREATED_AT(FieldMeta.builder("User", "auth_user")
        .field("createdAt", "created_at")
        .type(Instant.class, java.sql.Types.TIMESTAMP)
        .notNullable()
        .build()),

    CREATED_BY_USER_ID(FieldMeta.builder("User", "auth_user")
        .field("createdByUserId", "created_by_user_id")
        .type(Long.class, java.sql.Types.BIGINT)
        .notNullable()
        .build()),

    UPDATED_AT(FieldMeta.builder("User", "auth_user")
        .field("updatedAt", "updated_at")
        .type(Instant.class, java.sql.Types.TIMESTAMP)
        .notNullable()
        .build()),

    UPDATED_BY_USER_ID(FieldMeta.builder("User", "auth_user")
        .field("updatedByUserId", "updated_by_user_id")
        .type(Long.class, java.sql.Types.BIGINT)
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

  public static final String USER_ENTITY_NAME = "USER";

  private String userName;
  private Boolean enabled;
  private Instant updatedAt;
  private Long updatedByUserId;

  public User(
      Long userId,
      String userNo,
      String userName,
      Boolean enabled,
      Instant createdAt,
      Long createdByUserId,
      Instant updatedAt,
      Long updatedByUserId) {
    super(
        USER_ENTITY_NAME,
        userNo,
        userId,
        createdAt,
        createdByUserId);
    this.enabled = enabled;
    this.userName = userName;
    this.updatedAt = updatedAt;
    this.updatedByUserId = updatedByUserId;
  }

  public static String getUserEntityName() {
    return USER_ENTITY_NAME;
  }

  public String getUserName() {
    return userName;
  }

  public void setUserName(String userName) {
    this.userName = userName;
  }

  public Boolean isEnabled() {
    return enabled;
  }

  public void setEnabled(Boolean enabled) {
    this.enabled = enabled;
  }

  public Instant getUpdatedAt() {
    return updatedAt;
  }

  public void setUpdatedAt(Instant updatedAt) {
    this.updatedAt = updatedAt;
  }

  public Long getUpdatedByUserId() {
    return updatedByUserId;
  }

  public void setUpdatedByUserId(Long updatedByUserId) {
    this.updatedByUserId = updatedByUserId;
  }

}
