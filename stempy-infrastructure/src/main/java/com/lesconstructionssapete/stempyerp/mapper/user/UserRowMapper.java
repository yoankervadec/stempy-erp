package com.lesconstructionssapete.stempyerp.mapper.user;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.lesconstructionssapete.stempyerp.domain.auth.User;
import com.lesconstructionssapete.stempyerp.field.user.UserField;
import com.lesconstructionssapete.stempyerp.mapper.SQLInstantMapper;

public final class UserRowMapper {

  private UserRowMapper() {
  }

  public static User map(ResultSet rs) throws SQLException {
    return new User(
        rs.getLong(UserField.ID.columnName()),
        rs.getString(UserField.USER_NO.columnName()),
        rs.getString(UserField.USER_NAME.columnName()),
        rs.getBoolean(UserField.ENABLED.columnName()),
        SQLInstantMapper.read(rs, UserField.CREATED_AT.columnName()),
        rs.getLong(UserField.CREATED_BY_USER_ID.columnName()),
        SQLInstantMapper.read(rs, UserField.UPDATED_AT.columnName()),
        rs.getLong(UserField.UPDATED_BY_USER_ID.columnName()));
  }

}
