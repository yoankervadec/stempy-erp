package com.lesconstructionssapete.stempyerp.mapper.auth;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.lesconstructionssapete.stempyerp.domain.auth.ApplicationAction;
import com.lesconstructionssapete.stempyerp.domain.auth.ApplicationPermissionSet;
import com.lesconstructionssapete.stempyerp.field.auth.ApplicationPermissionField;
import com.lesconstructionssapete.stempyerp.field.auth.ApplicationPermissionSQLField;
import com.lesconstructionssapete.stempyerp.field.auth.ApplicationPermissionSetField;
import com.lesconstructionssapete.stempyerp.field.auth.ApplicationRolePermissionSetSQLField;
import com.lesconstructionssapete.stempyerp.mapper.SQLInstantMapper;

public final class ApplicationRolePermissionSetRowMapper {

  private ApplicationRolePermissionSetRowMapper() {
  }

  public static ApplicationPermissionSet map(ResultSet rs) throws SQLException {
    return new ApplicationPermissionSet(
        rs.getLong(ApplicationRolePermissionSetSQLField.get(
            ApplicationPermissionSetField.PERMISSION_ID).columnName()),

        rs.getString(ApplicationPermissionSQLField.get(ApplicationPermissionField.RESOURCE).columnName()),

        ApplicationAction
            .valueOf(rs.getString(ApplicationPermissionSQLField.get(ApplicationPermissionField.ACTION).columnName())
                .toUpperCase()),

        rs.getBoolean(ApplicationPermissionSQLField.get(ApplicationPermissionField.ENABLED).columnName()),

        SQLInstantMapper.read(rs,
            ApplicationPermissionSQLField.get(ApplicationPermissionField.CREATED_AT).columnName()),

        rs.getLong(ApplicationRolePermissionSetSQLField.get(ApplicationPermissionSetField.REFERENCE_ID).columnName()),

        rs.getBoolean(ApplicationRolePermissionSetSQLField.get(ApplicationPermissionSetField.ALLOW).columnName()));
  }

}
