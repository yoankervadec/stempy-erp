package com.lesconstructionssapete.stempyerp.domain.field.auth;

import com.lesconstructionssapete.stempyerp.domain.field.DomainField;
import com.lesconstructionssapete.stempyerp.domain.field.DomainFieldProvider;

public enum ApplicationUserPermissionSetField implements DomainFieldProvider {

  PERMISSION_ID(new DomainField("ApplicationUserPermissionSet", "permissionId", Long.class, false, true, true,
      "permission_id", "auth_user_permission", java.sql.Types.BIGINT)),

  USER_ID(new DomainField("ApplicationUserPermissionSet", "userId", Long.class, false, true, true,
      "user_id", "auth_user_permission", java.sql.Types.BIGINT)),

  ALLOW(new DomainField("ApplicationUserPermissionSet", "allow", Boolean.class, false, true, true,
      "allow", "auth_user_permission", java.sql.Types.BOOLEAN));

  private final DomainField attribute;

  ApplicationUserPermissionSetField(DomainField attribute) {
    this.attribute = attribute;
  }

  @Override
  public DomainField attribute() {
    return attribute;
  }

}
