package com.lesconstructionssapete.stempyerp.domain.field.auth;

import com.lesconstructionssapete.stempyerp.domain.field.DomainField;

public enum ApplicationRoleField implements DomainField {

  ID("id"),
  NAME("name"),
  DESCRIPTION("description"),
  ENABLED("enabled"),
  CREATED_AT("createdAt");

  private static final String PREFIX = "ApplicationRole.";
  private final String logicalName;

  ApplicationRoleField(String logicalName) {
    this.logicalName = logicalName;
  }

  @Override
  public String logicalName() {
    return PREFIX + logicalName;
  }

}
