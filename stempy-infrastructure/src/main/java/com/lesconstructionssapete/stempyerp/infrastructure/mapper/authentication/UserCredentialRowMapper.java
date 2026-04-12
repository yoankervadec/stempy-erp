package com.lesconstructionssapete.stempyerp.infrastructure.mapper.authentication;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.lesconstructionssapete.stempyerp.auth.UserCredential;
import com.lesconstructionssapete.stempyerp.field.auth.UserCredentialField;
import com.lesconstructionssapete.stempyerp.infrastructure.field.authentication.UserCredentialSQLField;
import com.lesconstructionssapete.stempyerp.infrastructure.mapper.SQLInstantMapper;

public final class UserCredentialRowMapper {

  private UserCredentialRowMapper() {
  }

  public static UserCredential map(ResultSet rs) throws SQLException {
    return new UserCredential(
        rs.getLong(UserCredentialSQLField.get(UserCredentialField.ID).columnName()),
        rs.getLong(UserCredentialSQLField.get(UserCredentialField.USER_ID).columnName()),
        rs.getString(UserCredentialSQLField.get(UserCredentialField.PASSWORD).columnName()),
        rs.getBoolean(UserCredentialSQLField.get(UserCredentialField.ENABLED).columnName()),
        SQLInstantMapper.read(rs, UserCredentialSQLField.get(UserCredentialField.CREATED_AT).columnName()));
  }

}
