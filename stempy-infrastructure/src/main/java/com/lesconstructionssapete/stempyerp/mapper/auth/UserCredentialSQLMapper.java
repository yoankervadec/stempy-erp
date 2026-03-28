package com.lesconstructionssapete.stempyerp.mapper.auth;

import com.lesconstructionssapete.stempyerp.domain.auth.UserCredential;
import com.lesconstructionssapete.stempyerp.field.auth.UserCredentialField;
import com.lesconstructionssapete.stempyerp.field.auth.UserCredentialSQLField;
import com.lesconstructionssapete.stempyerp.query.SQLBuilder;

public final class UserCredentialSQLMapper {

  private UserCredentialSQLMapper() {
  }

  public static void bindInsert(SQLBuilder builder, UserCredential userCredential) {
    builder
        .bind(UserCredentialSQLField.get(UserCredentialField.ID), userCredential.getUserCredentialId())
        .bind(UserCredentialSQLField.get(UserCredentialField.USER_ID), userCredential.getUserId())
        .bind(UserCredentialSQLField.get(UserCredentialField.PASSWORD), userCredential.getPassword())
        .bind(UserCredentialSQLField.get(UserCredentialField.ENABLED), userCredential.isEnabled());
  }

  public static void bindUpdate(SQLBuilder builder, UserCredential userCredential) {
    builder
        .bind(UserCredentialSQLField.get(UserCredentialField.ENABLED), userCredential.isEnabled());
  }

}
