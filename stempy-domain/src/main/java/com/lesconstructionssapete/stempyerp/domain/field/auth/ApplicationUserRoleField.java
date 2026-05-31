package com.lesconstructionssapete.stempyerp.domain.field.auth;

import com.lesconstructionssapete.stempyerp.domain.field.DomainField;
import com.lesconstructionssapete.stempyerp.domain.field.DomainFieldProvider;

public enum ApplicationUserRoleField implements DomainFieldProvider {

  USER_ID(new DomainField("ApplicationUserRole", "userId", Long.class, false, true, true, "user_id", "auth_user_role",
      java.sql.Types.BIGINT)),
  ROLE_ID(new DomainField("ApplicationUserRole", "roleId", Long.class, false, true, true, "role_id", "auth_user_role",
      java.sql.Types.BIGINT));

  private final DomainField attribute;

  ApplicationUserRoleField(DomainField attribute) {
    this.attribute = attribute;
  }

  @Override
  public DomainField attribute() {
    return attribute;
  }

}
