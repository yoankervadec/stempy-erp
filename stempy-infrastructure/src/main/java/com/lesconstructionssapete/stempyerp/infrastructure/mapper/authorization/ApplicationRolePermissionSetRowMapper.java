package com.lesconstructionssapete.stempyerp.infrastructure.mapper.authorization;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.lesconstructionssapete.stempyerp.annotation.AppAction;
import com.lesconstructionssapete.stempyerp.domain.auth.ApplicationPermissionSet;
import com.lesconstructionssapete.stempyerp.domain.field.auth.ApplicationPermissionField;
import com.lesconstructionssapete.stempyerp.domain.field.auth.ApplicationPermissionSetField;
import com.lesconstructionssapete.stempyerp.infrastructure.field.authorization.ApplicationPermissionSQLField;
import com.lesconstructionssapete.stempyerp.infrastructure.field.authorization.ApplicationRolePermissionSetSQLField;
import com.lesconstructionssapete.stempyerp.infrastructure.mapper.SQLInstantMapper;

public final class ApplicationRolePermissionSetRowMapper {

  private ApplicationRolePermissionSetRowMapper() {
  }

  public static ApplicationPermissionSet map(ResultSet rs) throws SQLException {
    return new ApplicationPermissionSet(
        rs.getLong(ApplicationRolePermissionSetSQLField.get(
            ApplicationPermissionSetField.PERMISSION_ID).columnName()),

        rs.getString(ApplicationPermissionSQLField.get(ApplicationPermissionField.RESOURCE).columnName()),

        AppAction
            .valueOf(rs.getString(ApplicationPermissionSQLField.get(ApplicationPermissionField.ACTION).columnName())
                .toUpperCase()),

        rs.getBoolean(ApplicationPermissionSQLField.get(ApplicationPermissionField.ENABLED).columnName()),

        SQLInstantMapper.read(rs,
            ApplicationPermissionSQLField.get(ApplicationPermissionField.CREATED_AT).columnName()),

        rs.getLong(ApplicationRolePermissionSetSQLField.get(ApplicationPermissionSetField.REFERENCE_ID).columnName()),

        rs.getBoolean(ApplicationRolePermissionSetSQLField.get(ApplicationPermissionSetField.ALLOW).columnName()));
  }

}
