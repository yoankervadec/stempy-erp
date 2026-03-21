package com.lesconstructionssapete.stempyerp.field.auth;

import java.sql.Types;
import java.util.Map;

import com.lesconstructionssapete.stempyerp.mapper.SQLField;

public final class UserCredentialField {

  private UserCredentialField() {
  }

  private static final String AUTH_USER_CREDENTIAL = "auth_user_credential";

  public final static SQLField ID = new SQLField(
      "id",
      AUTH_USER_CREDENTIAL,
      "id",
      Types.BIGINT);

  public final static SQLField USER_ID = new SQLField(
      "userId",
      AUTH_USER_CREDENTIAL,
      "user_id",
      Types.BIGINT);

  public final static SQLField PASSWORD = new SQLField(
      "password",
      AUTH_USER_CREDENTIAL,
      "password",
      Types.VARCHAR);

  public final static SQLField ENABLED = new SQLField(
      "enabled",
      AUTH_USER_CREDENTIAL,
      "enabled",
      Types.BOOLEAN);

  public final static SQLField CREATED_AT = new SQLField(
      "createdAt",
      AUTH_USER_CREDENTIAL,
      "created_at",
      Types.TIMESTAMP);

  private static final Map<String, SQLField> LOOKUP = Map.ofEntries(
      Map.entry(ID.logicalName(), ID),
      Map.entry(USER_ID.logicalName(), USER_ID),
      Map.entry(PASSWORD.logicalName(), PASSWORD),
      Map.entry(ENABLED.logicalName(), ENABLED),
      Map.entry(CREATED_AT.logicalName(), CREATED_AT));

  public static SQLField get(String logicalName) {
    return LOOKUP.get(logicalName);
  }

  public static Map<String, SQLField> all() {
    return LOOKUP;
  }

}
