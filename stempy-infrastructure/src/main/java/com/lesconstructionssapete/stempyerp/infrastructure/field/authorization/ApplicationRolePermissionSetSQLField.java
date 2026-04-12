package com.lesconstructionssapete.stempyerp.infrastructure.field.authorization;

import java.util.Map;

import com.lesconstructionssapete.stempyerp.field.DomainField;
import com.lesconstructionssapete.stempyerp.field.auth.ApplicationPermissionSetField;
import com.lesconstructionssapete.stempyerp.infrastructure.field.SQLField;

public final class ApplicationRolePermissionSetSQLField {

  private ApplicationRolePermissionSetSQLField() {
  }

  private static final String AUTH_ROLE_PERMISSION = "auth_role_permission";

  private static final SQLField ROLE_ID = new SQLField(
      ApplicationPermissionSetField.REFERENCE_ID,
      AUTH_ROLE_PERMISSION,
      "role_id",
      java.sql.Types.BIGINT);

  private static final SQLField PERMISSION_ID = new SQLField(
      ApplicationPermissionSetField.PERMISSION_ID,
      AUTH_ROLE_PERMISSION,
      "permission_id",
      java.sql.Types.BIGINT);

  private static final SQLField ALLOW = new SQLField(
      ApplicationPermissionSetField.ALLOW,
      AUTH_ROLE_PERMISSION,
      "allow",
      java.sql.Types.BOOLEAN);

  private static final Map<DomainField, SQLField> LOOKUP = Map.ofEntries(
      Map.entry(ApplicationPermissionSetField.REFERENCE_ID, ROLE_ID),
      Map.entry(ApplicationPermissionSetField.PERMISSION_ID, PERMISSION_ID),
      Map.entry(ApplicationPermissionSetField.ALLOW, ALLOW));

  public static SQLField get(ApplicationPermissionSetField field) {
    return LOOKUP.get(field);
  }

  public static Map<DomainField, SQLField> all() {
    return LOOKUP;
  }

}
