package com.lesconstructionssapete.stempyerp.domain.field.auth;

import com.lesconstructionssapete.stempyerp.domain.field.DomainField;
import com.lesconstructionssapete.stempyerp.domain.field.DomainFieldProvider;

public enum ApplicationPermissionField implements DomainFieldProvider {

  ID(new DomainField("ApplicationPermission", "id", Long.class, false, true, true, "id", "auto_application_permission",
      java.sql.Types.BIGINT)),
  RESOURCE(new DomainField("ApplicationPermission", "resource", String.class, false, false, false, "resource", null,
      java.sql.Types.VARCHAR)),
  ACTION(new DomainField("ApplicationPermission", "action", String.class, false, false, false, "action", null,
      java.sql.Types.VARCHAR)),
  ENABLED(new DomainField("ApplicationPermission", "enabled", Boolean.class, false, false, false, "enabled", null,
      java.sql.Types.BOOLEAN)),
  CREATED_AT(new DomainField("ApplicationPermission", "createdAt", java.time.Instant.class, false, false, false,
      "created_at", null, java.sql.Types.TIMESTAMP));

  private final DomainField attribute;

  ApplicationPermissionField(DomainField attribute) {
    this.attribute = attribute;
  }

  @Override
  public DomainField attribute() {
    return attribute;
  }

}
