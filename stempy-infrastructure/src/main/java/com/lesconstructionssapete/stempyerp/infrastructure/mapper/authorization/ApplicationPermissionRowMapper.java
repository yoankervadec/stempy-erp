package com.lesconstructionssapete.stempyerp.infrastructure.mapper.authorization;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.lesconstructionssapete.stempyerp.annotation.AppAction;
import com.lesconstructionssapete.stempyerp.domain.auth.ApplicationPermission;
import com.lesconstructionssapete.stempyerp.domain.field.auth.ApplicationPermissionField;
import com.lesconstructionssapete.stempyerp.infrastructure.field.authorization.ApplicationPermissionSQLField;
import com.lesconstructionssapete.stempyerp.infrastructure.mapper.SQLInstantMapper;

public final class ApplicationPermissionRowMapper {

  private ApplicationPermissionRowMapper() {
  }

  public static ApplicationPermission map(ResultSet rs) throws SQLException {
    return new ApplicationPermission(
        rs.getLong(ApplicationPermissionSQLField.get(ApplicationPermissionField.ID).columnName()),

        rs.getString(ApplicationPermissionSQLField.get(ApplicationPermissionField.RESOURCE).columnName()),

        AppAction
            .valueOf(rs.getString(ApplicationPermissionSQLField.get(ApplicationPermissionField.ACTION).columnName())
                .toUpperCase()),

        rs.getBoolean(ApplicationPermissionSQLField.get(ApplicationPermissionField.ENABLED).columnName()),

        SQLInstantMapper.read(rs,
            ApplicationPermissionSQLField.get(ApplicationPermissionField.CREATED_AT).columnName()));
  }

}
