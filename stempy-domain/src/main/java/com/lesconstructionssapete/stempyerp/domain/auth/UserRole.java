package com.lesconstructionssapete.stempyerp.domain.auth;

import java.time.Instant;

import com.lesconstructionssapete.stempyerp.domain.field.EntityField;
import com.lesconstructionssapete.stempyerp.domain.field.FieldMeta;

public class UserRole extends ApplicationRole {

  public enum Fields implements EntityField {

    USER_ID(FieldMeta.builder("UserRole", "auth_user_role")
        .field("userId", "user_id")
        .type(Long.class, java.sql.Types.BIGINT)
        .notNullable()
        .build()),

    ROLE_ID(FieldMeta.builder("UserRole", "auth_user_role")
        .field("roleId", "role_id")
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

  private final long userId;
  private final long roleId;

  public UserRole(
      long id,
      String name,
      String description,
      boolean enabled,
      Instant createdAt,
      long userId,
      long roleId) {
    super(
        id,
        name,
        description,
        enabled,
        createdAt);
    this.userId = userId;
    this.roleId = roleId;
  }

  public long getUserId() {
    return userId;
  }

  public long getRoleId() {
    return roleId;
  }

}
