package com.lesconstructionssapete.stempyerp.infrastructure.field.authorization;

import java.util.Map;

import com.lesconstructionssapete.stempyerp.field.DomainField;
import com.lesconstructionssapete.stempyerp.field.auth.ApplicationUserRoleField;
import com.lesconstructionssapete.stempyerp.infrastructure.field.SQLField;

public final class ApplicationUserRoleSQLField {

  private ApplicationUserRoleSQLField() {
  }

  private static final String AUTH_USER_ROLE = "auth_user_role";

  private static final SQLField USER_ID = new SQLField(
      ApplicationUserRoleField.USER_ID,
      AUTH_USER_ROLE,
      "user_id",
      java.sql.Types.BIGINT);

  private static final SQLField ROLE_ID = new SQLField(
      ApplicationUserRoleField.ROLE_ID,
      AUTH_USER_ROLE,
      "role_id",
      java.sql.Types.BIGINT);

  private static final Map<DomainField, SQLField> LOOKUP = Map.ofEntries(
      Map.entry(ApplicationUserRoleField.USER_ID, USER_ID),
      Map.entry(ApplicationUserRoleField.ROLE_ID, ROLE_ID));

  public static SQLField get(ApplicationUserRoleField field) {
    return LOOKUP.get(field);
  }

  public static Map<DomainField, SQLField> all() {
    return LOOKUP;
  }
}
