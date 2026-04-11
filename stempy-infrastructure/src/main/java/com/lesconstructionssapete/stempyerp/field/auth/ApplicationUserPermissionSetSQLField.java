package com.lesconstructionssapete.stempyerp.field.auth;

import java.util.Map;

import com.lesconstructionssapete.stempyerp.field.DomainField;
import com.lesconstructionssapete.stempyerp.field.SQLField;

public final class ApplicationUserPermissionSetSQLField {

  private ApplicationUserPermissionSetSQLField() {
  }

  private static final String AUTH_USER_PERMISSION = "auth_user_permission";

  private static final SQLField USER_ID = new SQLField(
      ApplicationPermissionSetField.REFERENCE_ID,
      AUTH_USER_PERMISSION,
      "user_id",
      java.sql.Types.BIGINT);

  private static final SQLField PERMISSION_ID = new SQLField(
      ApplicationPermissionSetField.PERMISSION_ID,
      AUTH_USER_PERMISSION,
      "permission_id",
      java.sql.Types.BIGINT);

  private static final SQLField ALLOW = new SQLField(
      ApplicationPermissionSetField.ALLOW,
      AUTH_USER_PERMISSION,
      "allow",
      java.sql.Types.BOOLEAN);

  private static final Map<DomainField, SQLField> LOOKUP = Map.ofEntries(
      Map.entry(ApplicationPermissionSetField.REFERENCE_ID, USER_ID),
      Map.entry(ApplicationPermissionSetField.PERMISSION_ID, PERMISSION_ID),
      Map.entry(ApplicationPermissionSetField.ALLOW, ALLOW));

  public static SQLField get(ApplicationPermissionSetField field) {
    return LOOKUP.get(field);
  }

  public static Map<DomainField, SQLField> all() {
    return LOOKUP;
  }

}
