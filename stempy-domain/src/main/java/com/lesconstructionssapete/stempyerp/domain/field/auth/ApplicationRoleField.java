package com.lesconstructionssapete.stempyerp.domain.field.auth;

import com.lesconstructionssapete.stempyerp.domain.field.DomainField;
import com.lesconstructionssapete.stempyerp.domain.field.DomainFieldProvider;

public enum ApplicationRoleField implements DomainFieldProvider {

  ID(new DomainField("ApplicationRole", "id", Long.class, false, true, true, "id", "auto_application_role",
      java.sql.Types.BIGINT)),
  NAME(new DomainField("ApplicationRole", "name", String.class, false, false, false, "name", null,
      java.sql.Types.VARCHAR)),
  DESCRIPTION(new DomainField("ApplicationRole", "description", String.class, false, false, false, "description", null,
      java.sql.Types.VARCHAR)),
  ENABLED(new DomainField("ApplicationRole", "enabled", Boolean.class, false, false, false, "enabled", null,
      java.sql.Types.BOOLEAN)),
  CREATED_AT(new DomainField("ApplicationRole", "createdAt", java.time.Instant.class, false, false, false, "created_at",
      null, java.sql.Types.TIMESTAMP));

  private final DomainField attribute;

  ApplicationRoleField(DomainField attribute) {
    this.attribute = attribute;
  }

  @Override
  public DomainField attribute() {
    return attribute;
  }

}
