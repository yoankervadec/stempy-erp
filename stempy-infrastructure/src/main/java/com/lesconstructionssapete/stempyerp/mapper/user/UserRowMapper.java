package com.lesconstructionssapete.stempyerp.mapper.user;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.lesconstructionssapete.stempyerp.domain.user.User;
import com.lesconstructionssapete.stempyerp.field.user.UserField;
import com.lesconstructionssapete.stempyerp.field.user.UserSQLField;
import com.lesconstructionssapete.stempyerp.mapper.SQLInstantMapper;

public final class UserRowMapper {

  private UserRowMapper() {
  }

  public static User map(ResultSet rs) throws SQLException {
    return new User(
        rs.getLong(UserSQLField.get(UserField.ID).columnName()),
        rs.getString(UserSQLField.get(UserField.USER_NO).columnName()),
        rs.getString(UserSQLField.get(UserField.USER_NAME).columnName()),
        rs.getBoolean(UserSQLField.get(UserField.ENABLED).columnName()),
        SQLInstantMapper.read(rs, UserSQLField.get(UserField.CREATED_AT).columnName()),
        rs.getLong(UserSQLField.get(UserField.CREATED_BY_USER_ID).columnName()),
        SQLInstantMapper.read(rs, UserSQLField.get(UserField.UPDATED_AT).columnName()),
        rs.getLong(UserSQLField.get(UserField.UPDATED_BY_USER_ID).columnName()));
  }

}
