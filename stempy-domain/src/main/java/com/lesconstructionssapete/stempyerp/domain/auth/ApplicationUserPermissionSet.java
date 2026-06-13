package com.lesconstructionssapete.stempyerp.domain.auth;

import java.time.Instant;

import com.lesconstructionssapete.stempyerp.domain.field.EntityField;
import com.lesconstructionssapete.stempyerp.domain.field.FieldMeta;

public class ApplicationUserPermissionSet extends ApplicationPermissionSet {

  public enum Fields implements EntityField {

    PERMISSION_ID(FieldMeta.builder("ApplicationUserPermissionSet", "auth_user_permission")
        .field("permissionId", "permission_id")
        .type(Long.class, java.sql.Types.BIGINT)
        .notNullable()
        .build()),

    USER_ID(FieldMeta.builder("ApplicationUserPermissionSet", "auth_user_permission")
        .field("userId", "user_id")
        .type(Long.class, java.sql.Types.BIGINT)
        .notNullable()
        .build()),

    ALLOW(FieldMeta.builder("ApplicationUserPermissionSet", "auth_user_permission")
        .field("allow", "allow")
        .type(Boolean.class, java.sql.Types.BOOLEAN)
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

  public ApplicationUserPermissionSet(
      long id,
      String resource,
      String action,
      boolean enabled,
      Instant createdAt,
      long userId,
      boolean allow) {
    super(id, resource, action, enabled, createdAt, userId, allow);
  }

}
