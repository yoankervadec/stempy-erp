package com.lesconstructionssapete.stempyerp.field.auth;

import java.sql.Types;
import java.util.Map;

import com.lesconstructionssapete.stempyerp.field.DomainField;
import com.lesconstructionssapete.stempyerp.field.SQLField;

public final class RefreshTokenSQLField {

  private RefreshTokenSQLField() {
  }

  private static final String AUTH_REFRESH_TOKEN = "auth_refresh_token";

  private static final SQLField ID = new SQLField(
      RefreshTokenField.ID,
      AUTH_REFRESH_TOKEN,
      "id",
      Types.BIGINT);

  private static final SQLField USER_ID = new SQLField(
      RefreshTokenField.USER_ID,
      AUTH_REFRESH_TOKEN,
      "user_id",
      Types.BIGINT);

  private static final SQLField TOKEN = new SQLField(
      RefreshTokenField.TOKEN,
      AUTH_REFRESH_TOKEN,
      "token",
      Types.VARCHAR);

  private static final SQLField EXPIRES_AT = new SQLField(
      RefreshTokenField.EXPIRES_AT,
      AUTH_REFRESH_TOKEN,
      "expires_at",
      Types.TIMESTAMP);

  private static final SQLField ENABLED = new SQLField(
      RefreshTokenField.ENABLED,
      AUTH_REFRESH_TOKEN,
      "enabled",
      Types.BOOLEAN);

  private static final SQLField CREATED_AT = new SQLField(
      RefreshTokenField.CREATED_AT,
      AUTH_REFRESH_TOKEN,
      "created_at",
      Types.TIMESTAMP);

  public static Map<DomainField, SQLField> LOOKUP = Map.ofEntries(
      Map.entry(RefreshTokenField.ID, ID),
      Map.entry(RefreshTokenField.USER_ID, USER_ID),
      Map.entry(RefreshTokenField.TOKEN, TOKEN),
      Map.entry(RefreshTokenField.EXPIRES_AT, EXPIRES_AT),
      Map.entry(RefreshTokenField.ENABLED, ENABLED),
      Map.entry(RefreshTokenField.CREATED_AT, CREATED_AT));

  public static SQLField get(RefreshTokenField field) {
    return LOOKUP.get(field);
  }

  public static Map<DomainField, SQLField> all() {
    return LOOKUP;
  }

}
