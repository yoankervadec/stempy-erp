package com.lesconstructionssapete.stempyerp.infrastructure.field.authorization;

import java.util.Map;

import com.lesconstructionssapete.stempyerp.domain.field.DomainField;
import com.lesconstructionssapete.stempyerp.domain.field.auth.ApplicationRoleField;
import com.lesconstructionssapete.stempyerp.infrastructure.field.SQLField;

public final class ApplicationRoleSQLField {

  private ApplicationRoleSQLField() {
  }

  private static final String AUTH_ROLE = "auth_role";

  private static final SQLField ID = new SQLField(
      ApplicationRoleField.ID,
      AUTH_ROLE,
      "id",
      java.sql.Types.BIGINT);

  private static final SQLField NAME = new SQLField(
      ApplicationRoleField.NAME,
      AUTH_ROLE,
      "name",
      java.sql.Types.VARCHAR);

  private static final SQLField DESCRIPTION = new SQLField(
      ApplicationRoleField.DESCRIPTION,
      AUTH_ROLE,
      "description",
      java.sql.Types.VARCHAR);

  private static final SQLField ENABLED = new SQLField(
      ApplicationRoleField.ENABLED,
      AUTH_ROLE,
      "enabled",
      java.sql.Types.BOOLEAN);

  private static final SQLField CREATED_AT = new SQLField(
      ApplicationRoleField.CREATED_AT,
      AUTH_ROLE,
      "created_at",
      java.sql.Types.TIMESTAMP);

  private static final Map<DomainField, SQLField> LOOKUP = Map.ofEntries(
      Map.entry(ApplicationRoleField.ID, ID),
      Map.entry(ApplicationRoleField.NAME, NAME),
      Map.entry(ApplicationRoleField.DESCRIPTION, DESCRIPTION),
      Map.entry(ApplicationRoleField.ENABLED, ENABLED),
      Map.entry(ApplicationRoleField.CREATED_AT, CREATED_AT));

  public static SQLField get(ApplicationRoleField field) {
    return LOOKUP.get(field);
  }

  public static Map<DomainField, SQLField> all() {
    return LOOKUP;
  }

}
