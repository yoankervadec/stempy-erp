package com.lesconstructionssapete.stempyerp.domain.field.auth;

import com.lesconstructionssapete.stempyerp.domain.field.DomainField;

public enum ApplicationUserRoleField implements DomainField {

  USER_ID("userId"),
  ROLE_ID("roleId");

  private static final String PREFIX = "ApplicationUserRole.";
  private final String logicalName;

  ApplicationUserRoleField(String logicalName) {
    this.logicalName = logicalName;
  }

  @Override
  public String logicalName() {
    return PREFIX + logicalName;
  }

}
