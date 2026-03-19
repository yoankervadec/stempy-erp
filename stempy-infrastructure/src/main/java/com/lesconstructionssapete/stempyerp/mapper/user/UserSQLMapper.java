package com.lesconstructionssapete.stempyerp.mapper.user;

import java.sql.Timestamp;
import java.time.Instant;

import com.lesconstructionssapete.stempyerp.domain.auth.User;
import com.lesconstructionssapete.stempyerp.field.user.UserField;
import com.lesconstructionssapete.stempyerp.query.SQLBuilder;

public final class UserSQLMapper {

  private UserSQLMapper() {
  }

  public static void bindInsert(SQLBuilder builder, User user) {
    builder
        .bind(UserField.ID, user.getEntityId())
        .bind(UserField.USER_NO, user.getEntityNo())
        .bind(UserField.USER_NAME, user.getUserName())
        .bind(UserField.ENABLED, user.isEnabled())
        .bind(UserField.CREATED_BY_USER_ID, user.getCreatedByUserId());
  }

  public static void bindUpdate(SQLBuilder builder, User user) {
    builder
        .bind(UserField.USER_NO, user.getEntityNo())
        .bind(UserField.USER_NAME, user.getUserName())
        .bind(UserField.ENABLED, user.isEnabled())
        .bind(UserField.UPDATED_AT, Timestamp.from(Instant.now()))
        .bind(UserField.UPDATED_BY_USER_ID, user.getUpdatedByUserId());
  }

}
