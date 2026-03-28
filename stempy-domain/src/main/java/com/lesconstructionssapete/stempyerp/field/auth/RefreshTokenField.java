package com.lesconstructionssapete.stempyerp.field.auth;

import com.lesconstructionssapete.stempyerp.field.DomainField;

public enum RefreshTokenField implements DomainField {

  ID("id"),
  USER_ID("userId"),
  TOKEN("token"),
  EXPIRES_AT("expiresAt"),
  ENABLED("enabled"),
  CREATED_AT("createdAt");

  private static final String PREFIX = "RefreshToken.";
  private final String logicalName;

  RefreshTokenField(String logicalName) {
    this.logicalName = logicalName;
  }

  @Override
  public String logicalName() {
    return PREFIX + logicalName;
  }

}
