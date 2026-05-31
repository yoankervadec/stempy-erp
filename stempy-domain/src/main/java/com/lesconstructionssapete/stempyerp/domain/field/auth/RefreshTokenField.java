package com.lesconstructionssapete.stempyerp.domain.field.auth;

import com.lesconstructionssapete.stempyerp.domain.field.DomainField;
import com.lesconstructionssapete.stempyerp.domain.field.DomainFieldProvider;

public enum RefreshTokenField implements DomainFieldProvider {

  ID(new DomainField("RefreshToken", "id", Long.class, false, true, true,
      "id", "auth_refresh_token", java.sql.Types.BIGINT)),
  USER_ID(new DomainField("RefreshToken", "userId", Long.class, false, true, true,
      "user_id", "auth_refresh_token", java.sql.Types.BIGINT)),
  TOKEN(new DomainField("RefreshToken", "token", String.class, false, true, true,
      "token", "auth_refresh_token", java.sql.Types.VARCHAR)),
  EXPIRES_AT(new DomainField("RefreshToken", "expiresAt", java.time.LocalDateTime.class, false, true, true,
      "expires_at", "auth_refresh_token", java.sql.Types.TIMESTAMP)),
  ENABLED(new DomainField("RefreshToken", "enabled", Boolean.class, false, true, true,
      "enabled", "auth_refresh_token", java.sql.Types.BOOLEAN)),
  CREATED_AT(new DomainField("RefreshToken", "createdAt", java.time.LocalDateTime.class, false, true, true,
      "created_at", "auth_refresh_token", java.sql.Types.TIMESTAMP));

  private final DomainField attribute;

  RefreshTokenField(DomainField attribute) {
    this.attribute = attribute;
  }

  @Override
  public DomainField attribute() {
    return attribute;
  }

}
