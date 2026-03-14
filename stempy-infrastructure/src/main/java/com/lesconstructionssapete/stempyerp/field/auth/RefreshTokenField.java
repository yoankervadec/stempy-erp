package com.lesconstructionssapete.stempyerp.field.auth;

import java.sql.Types;
import java.util.Map;

import com.lesconstructionssapete.stempyerp.mapper.SQLField;

public final class RefreshTokenField {

  private RefreshTokenField() {
  }

  private static final String AUTH_REFRESH_TOKEN = "auth_refresh_token";

  public static final SQLField ID = new SQLField(
      "id",
      AUTH_REFRESH_TOKEN,
      "id",
      Types.BIGINT);

  public static final SQLField USER_ID = new SQLField(
      "userId",
      AUTH_REFRESH_TOKEN,
      "user_id",
      Types.BIGINT);

  public static final SQLField TOKEN = new SQLField(
      "token",
      AUTH_REFRESH_TOKEN,
      "token",
      Types.VARCHAR);

  public static final SQLField EXPIRES_AT = new SQLField(
      "expiresAt",
      AUTH_REFRESH_TOKEN,
      "expires_at",
      Types.TIMESTAMP);

  public static final SQLField ENABLED = new SQLField(
      "enabled",
      AUTH_REFRESH_TOKEN,
      "enabled",
      Types.BOOLEAN);

  public static final SQLField CREATED_AT = new SQLField(
      "createdAt",
      AUTH_REFRESH_TOKEN,
      "created_at",
      Types.TIMESTAMP);

  public static Map<String, SQLField> LOOKUP = Map.ofEntries(
      Map.entry(ID.logicalName(), ID),
      Map.entry(USER_ID.logicalName(), USER_ID),
      Map.entry(TOKEN.logicalName(), TOKEN),
      Map.entry(EXPIRES_AT.logicalName(), EXPIRES_AT),
      Map.entry(ENABLED.logicalName(), ENABLED),
      Map.entry(CREATED_AT.logicalName(), CREATED_AT));

  public static SQLField get(String logicalName) {
    return LOOKUP.get(logicalName);
  }

  public static Map<String, SQLField> all() {
    return LOOKUP;
  }

}
