package com.lesconstructionssapete.stempyerp.infrastructure.mapper.auth;

import java.sql.ResultSet;

import com.lesconstructionssapete.stempyerp.domain.auth.ApplicationRole;
import com.lesconstructionssapete.stempyerp.domain.auth.UserRole;
import com.lesconstructionssapete.stempyerp.infrastructure.mapper.EntityMapper;

public final class UserRoleMapper {

  private UserRoleMapper() {
  }

  public static UserRole fromResultSet(ResultSet rs) {
    return new UserRole(
        EntityMapper.read(rs, ApplicationRole.Fields.ID),
        EntityMapper.read(rs, ApplicationRole.Fields.NAME),
        EntityMapper.read(rs, ApplicationRole.Fields.DESCRIPTION),
        EntityMapper.read(rs, ApplicationRole.Fields.ENABLED),
        EntityMapper.read(rs, ApplicationRole.Fields.CREATED_AT),
        EntityMapper.read(rs, UserRole.Fields.USER_ID),
        EntityMapper.read(rs, UserRole.Fields.ROLE_ID));
  }

}
