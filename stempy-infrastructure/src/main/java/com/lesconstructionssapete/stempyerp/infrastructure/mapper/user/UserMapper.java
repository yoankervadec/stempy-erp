package com.lesconstructionssapete.stempyerp.infrastructure.mapper.user;

import java.sql.ResultSet;
import java.util.function.BiConsumer;

import com.lesconstructionssapete.stempyerp.domain.field.EntityField;
import com.lesconstructionssapete.stempyerp.domain.user.User;
import com.lesconstructionssapete.stempyerp.infrastructure.mapper.EntityMapper;

public final class UserMapper {

  private UserMapper() {
  }

  public static User fromResultSet(ResultSet rs) {
    return new User(
        EntityMapper.read(rs, User.Fields.ID),
        EntityMapper.read(rs, User.Fields.USER_NO),
        EntityMapper.read(rs, User.Fields.USER_NAME),
        EntityMapper.read(rs, User.Fields.ENABLED),
        EntityMapper.read(rs, User.Fields.CREATED_AT),
        EntityMapper.read(rs, User.Fields.CREATED_BY_USER_ID),
        EntityMapper.read(rs, User.Fields.UPDATED_AT),
        EntityMapper.read(rs, User.Fields.UPDATED_BY_USER_ID));
  }

  public static void bind(User user, BiConsumer<EntityField, Object> binder) {
    binder.accept(User.Fields.ID, user.getEntityId());
    binder.accept(User.Fields.USER_NO, user.getEntityNo());
    binder.accept(User.Fields.USER_NAME, user.getUserName());
    binder.accept(User.Fields.ENABLED, user.isEnabled());
    binder.accept(User.Fields.CREATED_AT, user.getCreatedAt());
    binder.accept(User.Fields.CREATED_BY_USER_ID, user.getCreatedByUserId());
    binder.accept(User.Fields.UPDATED_AT, user.getUpdatedAt());
    binder.accept(User.Fields.UPDATED_BY_USER_ID, user.getUpdatedByUserId());
  }

}
