package com.lesconstructionssapete.stempyerp.domain.field.user;

import com.lesconstructionssapete.stempyerp.domain.field.DomainField;
import com.lesconstructionssapete.stempyerp.domain.field.DomainFieldProvider;

public enum UserField implements DomainFieldProvider {

  ID(new DomainField("User", "id", Long.class, false, true, true, "id", "auth_user", java.sql.Types.BIGINT)),
  USER_NO(new DomainField("User", "userNo", String.class, false, false, false, "user_no", "auth_user",
      java.sql.Types.VARCHAR)),
  USER_NAME(new DomainField("User", "userName", String.class, false, false, false, "user_name", "auth_user",
      java.sql.Types.VARCHAR)),
  ENABLED(new DomainField("User", "enabled", Boolean.class, false, false, false, "enabled", "auth_user",
      java.sql.Types.BOOLEAN)),
  CREATED_AT(
      new DomainField("User", "createdAt", java.sql.Timestamp.class, false, false, false, "created_at", "auth_user",
          java.sql.Types.TIMESTAMP)),
  CREATED_BY_USER_ID(
      new DomainField("User", "createdByUserId", Long.class, false, false, false, "created_by_user_id", "auth_user",
          java.sql.Types.BIGINT)),
  UPDATED_AT(
      new DomainField("User", "updatedAt", java.sql.Timestamp.class, false, false, false, "updated_at", "auth_user",
          java.sql.Types.TIMESTAMP)),
  UPDATED_BY_USER_ID(
      new DomainField("User", "updatedByUserId", Long.class, false, false, false, "updated_by_user_id", "auth_user",
          java.sql.Types.BIGINT));

  private final DomainField attribute;

  UserField(DomainField attribute) {
    this.attribute = attribute;
  }

  @Override
  public DomainField attribute() {
    return attribute;
  }

}
