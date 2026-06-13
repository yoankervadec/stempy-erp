package com.lesconstructionssapete.stempyerp.infrastructure.mapper.auth;

import com.lesconstructionssapete.stempyerp.domain.auth.ApplicationPermission;
import com.lesconstructionssapete.stempyerp.domain.auth.ApplicationRolePermissionSet;
import com.lesconstructionssapete.stempyerp.infrastructure.mapper.EntityMapper;

public final class ApplicationRolePermissionSetMapper {

  private ApplicationRolePermissionSetMapper() {
  }

  public static ApplicationRolePermissionSet fromResultSet(java.sql.ResultSet rs) {
    return new ApplicationRolePermissionSet(
        EntityMapper.read(rs, ApplicationRolePermissionSet.Fields.ROLE_ID),
        EntityMapper.read(rs, ApplicationPermission.Fields.RESOURCE),
        EntityMapper.read(rs, ApplicationPermission.Fields.ACTION),
        EntityMapper.read(rs, ApplicationPermission.Fields.ENABLED),
        EntityMapper.read(rs, ApplicationPermission.Fields.CREATED_AT),
        EntityMapper.read(rs, ApplicationRolePermissionSet.Fields.ROLE_ID),
        EntityMapper.read(rs, ApplicationRolePermissionSet.Fields.ALLOW));
  }

  public static void bind(ApplicationRolePermissionSet arps,
      java.util.function.BiConsumer<com.lesconstructionssapete.stempyerp.domain.field.EntityField, Object> binder) {
    binder.accept(ApplicationRolePermissionSet.Fields.ROLE_ID, arps.getReferenceId());
    binder.accept(ApplicationPermission.Fields.RESOURCE, arps.getResource());
    binder.accept(ApplicationPermission.Fields.ACTION, arps.getAction().name());
    binder.accept(ApplicationPermission.Fields.ENABLED, arps.isEnabled());
    binder.accept(ApplicationPermission.Fields.CREATED_AT, arps.getCreatedAt());
    binder.accept(ApplicationRolePermissionSet.Fields.ROLE_ID, arps.getReferenceId());
    binder.accept(ApplicationRolePermissionSet.Fields.ALLOW, arps.isAllow());
  }

}
