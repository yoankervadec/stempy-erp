package com.lesconstructionssapete.stempyerp.domain.field.auth;

import com.lesconstructionssapete.stempyerp.domain.field.DomainField;

public enum ApplicationPermissionSetField implements DomainField {
  PERMISSION_ID("permissionId"),
  RESOURCE("resource"),
  ACTION("action"),
  ENABLED("enabled"),
  CREATED_AT("createdAt"),
  REFERENCE_ID("referenceId"),
  ALLOW("allow");

  private static final String PREFIX = "ApplicationPermissionSet.";
  private final String columnName;

  ApplicationPermissionSetField(String columnName) {
    this.columnName = columnName;
  }

  @Override
  public String logicalName() {
    return PREFIX + columnName;
  }

}
