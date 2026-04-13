package com.lesconstructionssapete.stempyerp.infrastructure.mapper.authentication;

import com.lesconstructionssapete.stempyerp.domain.auth.UserCredential;
import com.lesconstructionssapete.stempyerp.domain.field.auth.UserCredentialField;
import com.lesconstructionssapete.stempyerp.infrastructure.field.authentication.UserCredentialSQLField;
import com.lesconstructionssapete.stempyerp.infrastructure.query.SQLBuilder;

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
