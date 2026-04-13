package com.lesconstructionssapete.stempyerp.domain.field.auth;

import com.lesconstructionssapete.stempyerp.domain.field.DomainField;

public enum UserCredentialField implements DomainField {

  ID("id"),
  USER_ID("userId"),
  PASSWORD("password"),
  ENABLED("enabled"),
  CREATED_AT("createdAt");

  private static final String PREFIX = "UserCredential.";
  private final String logicalName;

  UserCredentialField(String logicalName) {
    this.logicalName = logicalName;
  }

  @Override
  public String logicalName() {
    return PREFIX + logicalName;
  }

}
