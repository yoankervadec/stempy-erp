package com.lesconstructionssapete.stempyerp.domain.field.auth;

import com.lesconstructionssapete.stempyerp.domain.field.DomainField;
import com.lesconstructionssapete.stempyerp.domain.field.DomainFieldProvider;

public enum UserCredentialField implements DomainFieldProvider {

  ID(new DomainField("UserCredential", "id", Long.class, false, true, true,
      "id", "auth_user_credential", java.sql.Types.BIGINT)),
  USER_ID(new DomainField("UserCredential", "userId", Long.class, false, false, false,
      "user_id", "auth_user_credential", java.sql.Types.BIGINT)),
  PASSWORD(new DomainField("UserCredential", "password", String.class, false, false, false,
      "password", "auth_user_credential", java.sql.Types.VARCHAR)),
  ENABLED(new DomainField("UserCredential", "enabled", Boolean.class, false, false, false,
      "enabled", "auth_user_credential", java.sql.Types.BOOLEAN)),
  CREATED_AT(new DomainField("UserCredential", "createdAt", java.time.LocalDateTime.class, false, false, false,
      "created_at", "auth_user_credential", java.sql.Types.TIMESTAMP));

  private final DomainField attribute;

  UserCredentialField(DomainField attribute) {
    this.attribute = attribute;
  }

  @Override
  public DomainField attribute() {
    return attribute;
  }

}
