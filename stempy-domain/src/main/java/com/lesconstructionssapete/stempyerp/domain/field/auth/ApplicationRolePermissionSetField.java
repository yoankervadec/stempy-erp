package com.lesconstructionssapete.stempyerp.domain.field.auth;

import com.lesconstructionssapete.stempyerp.domain.field.DomainField;
import com.lesconstructionssapete.stempyerp.domain.field.DomainFieldProvider;

public enum ApplicationRolePermissionSetField implements DomainFieldProvider {

  PERMISSION_ID(new DomainField("ApplicationRolePermissionSet", "permissionId", Long.class, false, true, true,
      "permission_id", "auth_role_permission", java.sql.Types.BIGINT)),

  ROLE_ID(new DomainField("ApplicationRolePermissionSet", "roleId", Long.class, false, true, true,
      "role_id", "auth_role_permission", java.sql.Types.BIGINT)),

  ALLOW(new DomainField("ApplicationRolePermissionSet", "allow", Boolean.class, false, true, true,
      "allow", "auth_role_permission", java.sql.Types.BOOLEAN));

  private final DomainField attribute;

  ApplicationRolePermissionSetField(DomainField attribute) {
    this.attribute = attribute;
  }

  @Override
  public DomainField attribute() {
    return attribute;
  }

}
