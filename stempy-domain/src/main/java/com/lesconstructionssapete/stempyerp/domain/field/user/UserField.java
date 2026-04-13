package com.lesconstructionssapete.stempyerp.domain.field.user;

import com.lesconstructionssapete.stempyerp.domain.field.DomainField;

public enum UserField implements DomainField {

  ID("id"),
  USER_NO("userNo"),
  USER_NAME("userName"),
  ENABLED("enabled"),
  CREATED_AT("createdAt"),
  CREATED_BY_USER_ID("createdByUserId"),
  UPDATED_AT("updatedAt"),
  UPDATED_BY_USER_ID("updatedByUserId");

  private static final String PREFIX = "User.";
  private final String logicalName;

  UserField(String logicalName) {
    this.logicalName = logicalName;
  }

  @Override
  public String logicalName() {
    return PREFIX + logicalName;
  }

}
