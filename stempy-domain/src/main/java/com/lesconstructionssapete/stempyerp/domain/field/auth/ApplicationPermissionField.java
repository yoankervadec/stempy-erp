package com.lesconstructionssapete.stempyerp.domain.field.auth;

import com.lesconstructionssapete.stempyerp.domain.field.DomainField;

public enum ApplicationPermissionField implements DomainField {

  ID("id"),
  RESOURCE("resource"),
  ACTION("action"),
  ENABLED("enabled"),
  CREATED_AT("createdAt");

  private static final String PREFIX = "ApplicationPermission.";
  private final String logicalName;

  ApplicationPermissionField(String logicalName) {
    this.logicalName = logicalName;
  }

  @Override
  public String logicalName() {
    return PREFIX + logicalName;
  }

}
