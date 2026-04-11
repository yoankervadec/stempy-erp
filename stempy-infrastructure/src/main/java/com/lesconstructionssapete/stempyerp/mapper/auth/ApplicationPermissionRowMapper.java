package com.lesconstructionssapete.stempyerp.mapper.auth;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.lesconstructionssapete.stempyerp.domain.auth.ApplicationAction;
import com.lesconstructionssapete.stempyerp.domain.auth.ApplicationPermission;
import com.lesconstructionssapete.stempyerp.field.auth.ApplicationPermissionField;
import com.lesconstructionssapete.stempyerp.field.auth.ApplicationPermissionSQLField;
import com.lesconstructionssapete.stempyerp.mapper.SQLInstantMapper;

public final class ApplicationPermissionRowMapper {

  private ApplicationPermissionRowMapper() {
  }

  public static ApplicationPermission map(ResultSet rs) throws SQLException {
    return new ApplicationPermission(
        rs.getLong(ApplicationPermissionSQLField.get(ApplicationPermissionField.ID).columnName()),

        rs.getString(ApplicationPermissionSQLField.get(ApplicationPermissionField.RESOURCE).columnName()),

        ApplicationAction
            .valueOf(rs.getString(ApplicationPermissionSQLField.get(ApplicationPermissionField.ACTION).columnName())
                .toUpperCase()),

        rs.getBoolean(ApplicationPermissionSQLField.get(ApplicationPermissionField.ENABLED).columnName()),

        SQLInstantMapper.read(rs,
            ApplicationPermissionSQLField.get(ApplicationPermissionField.CREATED_AT).columnName()));
  }

}
