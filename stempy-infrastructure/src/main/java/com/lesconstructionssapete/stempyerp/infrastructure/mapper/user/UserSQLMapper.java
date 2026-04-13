package com.lesconstructionssapete.stempyerp.infrastructure.mapper.user;

import java.sql.Timestamp;
import java.time.Instant;

import com.lesconstructionssapete.stempyerp.domain.field.user.UserField;
import com.lesconstructionssapete.stempyerp.domain.user.User;
import com.lesconstructionssapete.stempyerp.infrastructure.field.user.UserSQLField;
import com.lesconstructionssapete.stempyerp.infrastructure.query.SQLBuilder;

public final class UserSQLMapper {

  private UserSQLMapper() {
  }

  public static void bindInsert(SQLBuilder builder, User user) {
    builder
        .bind(UserSQLField.get(UserField.ID), user.getEntityId())
        .bind(UserSQLField.get(UserField.USER_NO), user.getEntityNo())
        .bind(UserSQLField.get(UserField.USER_NAME), user.getUserName())
        .bind(UserSQLField.get(UserField.ENABLED), user.isEnabled())
        .bind(UserSQLField.get(UserField.CREATED_BY_USER_ID), user.getCreatedByUserId());
  }

  public static void bindUpdate(SQLBuilder builder, User user) {
    builder
        .bind(UserSQLField.get(UserField.USER_NO), user.getEntityNo())
        .bind(UserSQLField.get(UserField.USER_NAME), user.getUserName())
        .bind(UserSQLField.get(UserField.ENABLED), user.isEnabled())
        .bind(UserSQLField.get(UserField.UPDATED_AT), Timestamp.from(Instant.now()))
        .bind(UserSQLField.get(UserField.UPDATED_BY_USER_ID), user.getUpdatedByUserId());
  }

}
