package com.lesconstructionssapete.stempyerp.field.user;

import java.sql.Types;

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
      "user_id",
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
      "created_at",
      AUTH_USER_CREDENTIAL,
      "created_at",
      Types.TIMESTAMP);

}
