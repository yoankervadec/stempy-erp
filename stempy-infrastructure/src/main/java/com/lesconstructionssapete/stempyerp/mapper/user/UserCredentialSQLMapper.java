package com.lesconstructionssapete.stempyerp.mapper.user;

import com.lesconstructionssapete.stempyerp.domain.auth.UserCredential;
import com.lesconstructionssapete.stempyerp.field.user.UserCredentialField;
import com.lesconstructionssapete.stempyerp.query.SQLBuilder;

public final class UserCredentialSQLMapper {

  private UserCredentialSQLMapper() {
  }

  public static void bindInsert(SQLBuilder builder, UserCredential userCredential) {
    builder
        .bind(UserCredentialField.ID, userCredential.getUserCredentialId())
        .bind(UserCredentialField.USER_ID, userCredential.getUserId())
        .bind(UserCredentialField.PASSWORD, userCredential.getPassword())
        .bind(UserCredentialField.ENABLED, userCredential.isEnabled());
  }

  public static void bindUpdate(SQLBuilder builder, UserCredential userCredential) {
    builder
        .bind(UserCredentialField.ENABLED, userCredential.isEnabled());
  }

}
