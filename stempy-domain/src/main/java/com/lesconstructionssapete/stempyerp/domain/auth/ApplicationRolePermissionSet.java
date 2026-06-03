package com.lesconstructionssapete.stempyerp.domain.auth;

import java.time.Instant;

import com.lesconstructionssapete.stempyerp.annotation.AppAction;
import com.lesconstructionssapete.stempyerp.domain.field.EntityField;
import com.lesconstructionssapete.stempyerp.domain.field.FieldMeta;

public class ApplicationRolePermissionSet extends ApplicationPermissionSet {

  public enum Fields implements EntityField {

    PERMISSION_ID(FieldMeta.builder("ApplicationRolePermissionSet", "auth_role_permission")
        .field("permissionId", "permission_id")
        .type(Long.class, java.sql.Types.BIGINT)
        .notNullable()
        .build()),

    ROLE_ID(FieldMeta.builder("ApplicationRolePermissionSet", "auth_role_permission")
        .field("roleId", "role_id")
        .type(Long.class, java.sql.Types.BIGINT)
        .notNullable()
        .build()),

    ALLOW(FieldMeta.builder("ApplicationRolePermissionSet", "auth_role_permission")
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

  public ApplicationRolePermissionSet(
      long id,
      String resource,
      AppAction action,
      boolean enabled,
      Instant createdAt,
      long roleId,
      boolean allow) {
    super(id, resource, action, enabled, createdAt, roleId, allow);
  }

}
