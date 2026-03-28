package com.lesconstructionssapete.stempyerp.field.user;

import java.sql.Types;
import java.util.Map;

import com.lesconstructionssapete.stempyerp.field.DomainField;
import com.lesconstructionssapete.stempyerp.field.SQLField;

public final class UserSQLField {

  private UserSQLField() {
  }

  private static final String AUTH_USER = "auth_user";

  private final static SQLField ID = new SQLField(
      UserField.ID,
      AUTH_USER,
      "id",
      Types.BIGINT);

  private final static SQLField USER_NO = new SQLField(
      UserField.USER_NO,
      AUTH_USER,
      "user_no",
      Types.VARCHAR);

  private final static SQLField USER_NAME = new SQLField(
      UserField.USER_NAME,
      AUTH_USER,
      "user_name",
      Types.VARCHAR);

  private final static SQLField ENABLED = new SQLField(
      UserField.ENABLED,
      AUTH_USER,
      "enabled",
      Types.BOOLEAN);

  private final static SQLField CREATED_AT = new SQLField(
      UserField.CREATED_AT,
      AUTH_USER,
      "created_at",
      Types.TIMESTAMP);

  private final static SQLField CREATED_BY_USER_ID = new SQLField(
      UserField.CREATED_BY_USER_ID,
      AUTH_USER,
      "created_by_user_id",
      Types.BIGINT);

  private final static SQLField UPDATED_AT = new SQLField(
      UserField.UPDATED_AT,
      AUTH_USER,
      "updated_at",
      Types.TIMESTAMP);

  private final static SQLField UPDATED_BY_USER_ID = new SQLField(
      UserField.UPDATED_BY_USER_ID,
      AUTH_USER,
      "updated_by_user_id",
      Types.BIGINT);

  private static final Map<DomainField, SQLField> LOOKUP = Map.ofEntries(
      Map.entry(UserField.ID, ID),
      Map.entry(UserField.USER_NO, USER_NO),
      Map.entry(UserField.USER_NAME, USER_NAME),
      Map.entry(UserField.ENABLED, ENABLED),
      Map.entry(UserField.CREATED_AT, CREATED_AT),
      Map.entry(UserField.CREATED_BY_USER_ID, CREATED_BY_USER_ID),
      Map.entry(UserField.UPDATED_AT, UPDATED_AT),
      Map.entry(UserField.UPDATED_BY_USER_ID, UPDATED_BY_USER_ID));

  public static SQLField get(DomainField field) {
    return LOOKUP.get(field);
  }

  public static Map<DomainField, SQLField> all() {
    return LOOKUP;
  }
}
