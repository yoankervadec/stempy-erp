package com.lesconstructionssapete.stempyerp.field.auth;

import java.sql.Types;
import java.util.Map;

import com.lesconstructionssapete.stempyerp.field.DomainField;
import com.lesconstructionssapete.stempyerp.field.SQLField;

public final class UserCredentialSQLField {

  private UserCredentialSQLField() {
  }

  private static final String AUTH_USER_CREDENTIAL = "auth_user_credential";

  private final static SQLField ID = new SQLField(
      UserCredentialField.ID,
      AUTH_USER_CREDENTIAL,
      "id",
      Types.BIGINT);

  private final static SQLField USER_ID = new SQLField(
      UserCredentialField.USER_ID,
      AUTH_USER_CREDENTIAL,
      "user_id",
      Types.BIGINT);

  private final static SQLField PASSWORD = new SQLField(
      UserCredentialField.PASSWORD,
      AUTH_USER_CREDENTIAL,
      "password",
      Types.VARCHAR);

  private final static SQLField ENABLED = new SQLField(
      UserCredentialField.ENABLED,
      AUTH_USER_CREDENTIAL,
      "enabled",
      Types.BOOLEAN);

  private final static SQLField CREATED_AT = new SQLField(
      UserCredentialField.CREATED_AT,
      AUTH_USER_CREDENTIAL,
      "created_at",
      Types.TIMESTAMP);

  private static final Map<DomainField, SQLField> LOOKUP = Map.ofEntries(
      Map.entry(UserCredentialField.ID, ID),
      Map.entry(UserCredentialField.USER_ID, USER_ID),
      Map.entry(UserCredentialField.PASSWORD, PASSWORD),
      Map.entry(UserCredentialField.ENABLED, ENABLED),
      Map.entry(UserCredentialField.CREATED_AT, CREATED_AT));

  public static SQLField get(DomainField field) {
    return LOOKUP.get(field);
  }

  public static Map<DomainField, SQLField> all() {
    return LOOKUP;
  }

}
