package com.lesconstructionssapete.stempyerp.mapper.auth;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.lesconstructionssapete.stempyerp.domain.auth.UserCredential;
import com.lesconstructionssapete.stempyerp.field.auth.UserCredentialField;
import com.lesconstructionssapete.stempyerp.mapper.SQLInstantMapper;

public final class UserCredentialRowMapper {

  private UserCredentialRowMapper() {
  }

  public static UserCredential map(ResultSet rs) throws SQLException {
    return new UserCredential(
        rs.getLong(UserCredentialField.ID.columnName()),
        rs.getLong(UserCredentialField.USER_ID.columnName()),
        rs.getString(UserCredentialField.PASSWORD.columnName()),
        rs.getBoolean(UserCredentialField.ENABLED.columnName()),
        SQLInstantMapper.read(rs, UserCredentialField.CREATED_AT.columnName()));
  }

}
