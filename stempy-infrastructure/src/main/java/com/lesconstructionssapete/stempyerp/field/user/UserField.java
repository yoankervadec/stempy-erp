package com.lesconstructionssapete.stempyerp.field.user;

import java.sql.Types;
import java.util.Map;

import com.lesconstructionssapete.stempyerp.mapper.SQLField;

public final class UserField {

  private UserField() {
  }

  private static final String AUTH_USER = "auth_user";

  public final static SQLField ID = new SQLField(
      "userId",
      AUTH_USER,
      "id",
      Types.BIGINT);

  public final static SQLField USER_NO = new SQLField(
      "userNo",
      AUTH_USER,
      "user_no",
      Types.VARCHAR);

  public final static SQLField USER_NAME = new SQLField(
      "userName",
      AUTH_USER,
      "user_name",
      Types.VARCHAR);

  public final static SQLField ENABLED = new SQLField(
      "enabled",
      AUTH_USER,
      "enabled",
      Types.BOOLEAN);

  public final static SQLField CREATED_AT = new SQLField(
      "createdAt",
      AUTH_USER,
      "created_at",
      Types.TIMESTAMP);

  public final static SQLField CREATED_BY_USER_ID = new SQLField(
      "createdByUserId",
      AUTH_USER,
      "created_by_user_id",
      Types.BIGINT);

  public final static SQLField UPDATED_AT = new SQLField(
      "updatedAt",
      AUTH_USER,
      "updated_at",
      Types.TIMESTAMP);

  public final static SQLField UPDATED_BY_USER_ID = new SQLField(
      "updatedByUserId",
      AUTH_USER,
      "updated_by_user_id",
      Types.BIGINT);

  private static final Map<String, SQLField> LOOKUP = Map.ofEntries(
      Map.entry(ID.logicalName(), ID),
      Map.entry(USER_NO.logicalName(), USER_NO),
      Map.entry(USER_NAME.logicalName(), USER_NAME),
      Map.entry(ENABLED.logicalName(), ENABLED),
      Map.entry(CREATED_AT.logicalName(), CREATED_AT),
      Map.entry(CREATED_BY_USER_ID.logicalName(), CREATED_BY_USER_ID),
      Map.entry(UPDATED_AT.logicalName(), UPDATED_AT),
      Map.entry(UPDATED_BY_USER_ID.logicalName(), UPDATED_BY_USER_ID));

  public static SQLField get(String logicalName) {
    return LOOKUP.get(logicalName);
  }

  public static Map<String, SQLField> all() {
    return LOOKUP;
  }
}
