package com.lesconstructionssapete.stempyerp.mapper.auth;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.lesconstructionssapete.stempyerp.domain.auth.ApplicationRole;
import com.lesconstructionssapete.stempyerp.field.auth.ApplicationRoleField;
import com.lesconstructionssapete.stempyerp.field.auth.ApplicationRoleSQLField;
import com.lesconstructionssapete.stempyerp.mapper.SQLInstantMapper;

public final class ApplicationRoleRowMapper {

  private ApplicationRoleRowMapper() {
  }

  public static ApplicationRole map(ResultSet rs) throws SQLException {
    return new ApplicationRole(
        rs.getLong(ApplicationRoleSQLField.get(ApplicationRoleField.ID).columnName()),
        rs.getString(ApplicationRoleSQLField.get(ApplicationRoleField.NAME).columnName()),
        rs.getString(ApplicationRoleSQLField.get(ApplicationRoleField.DESCRIPTION).columnName()),
        rs.getBoolean(ApplicationRoleSQLField.get(ApplicationRoleField.ENABLED).columnName()),
        SQLInstantMapper.read(rs,
            ApplicationRoleSQLField.get(ApplicationRoleField.CREATED_AT).columnName()));
  }

}
