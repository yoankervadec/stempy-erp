package com.lesconstructionssapete.stempyerp.infrastructure.field.authorization;

import java.util.Map;

import com.lesconstructionssapete.stempyerp.field.DomainField;
import com.lesconstructionssapete.stempyerp.field.auth.ApplicationPermissionField;
import com.lesconstructionssapete.stempyerp.infrastructure.field.SQLField;

public final class ApplicationPermissionSQLField {

  private ApplicationPermissionSQLField() {
  }

  private static final String AUTH_PERMISSION = "auth_permission";

  private static final SQLField ID = new SQLField(
      ApplicationPermissionField.ID,
      AUTH_PERMISSION,
      "id",
      java.sql.Types.BIGINT);

  private static final SQLField RESOURCE = new SQLField(
      ApplicationPermissionField.RESOURCE,
      AUTH_PERMISSION,
      "resource",
      java.sql.Types.VARCHAR);

  private static final SQLField ACTION = new SQLField(
      ApplicationPermissionField.ACTION,
      AUTH_PERMISSION,
      "action",
      java.sql.Types.VARCHAR);

  private static final SQLField ENABLED = new SQLField(
      ApplicationPermissionField.ENABLED,
      AUTH_PERMISSION,
      "enabled",
      java.sql.Types.BOOLEAN);

  private static final SQLField CREATED_AT = new SQLField(
      ApplicationPermissionField.CREATED_AT,
      AUTH_PERMISSION,
      "created_at",
      java.sql.Types.TIMESTAMP);

  private static final Map<DomainField, SQLField> LOOKUP = Map.ofEntries(
      Map.entry(ApplicationPermissionField.ID, ID),
      Map.entry(ApplicationPermissionField.RESOURCE, RESOURCE),
      Map.entry(ApplicationPermissionField.ACTION, ACTION),
      Map.entry(ApplicationPermissionField.ENABLED, ENABLED),
      Map.entry(ApplicationPermissionField.CREATED_AT, CREATED_AT));

  public static SQLField get(ApplicationPermissionField field) {
    return LOOKUP.get(field);
  }

  public static Map<DomainField, SQLField> all() {
    return LOOKUP;
  }

}
