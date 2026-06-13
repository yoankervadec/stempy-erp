package com.lesconstructionssapete.stempyerp.infrastructure.mapper.auth;

import java.util.function.BiConsumer;

import com.lesconstructionssapete.stempyerp.domain.auth.ApplicationPermission;
import com.lesconstructionssapete.stempyerp.domain.auth.ApplicationUserPermissionSet;
import com.lesconstructionssapete.stempyerp.domain.field.EntityField;
import com.lesconstructionssapete.stempyerp.infrastructure.mapper.EntityMapper;

public final class ApplicationUserPermissionSetMapper {

  private ApplicationUserPermissionSetMapper() {
  }

  public static ApplicationUserPermissionSet fromResultSet(java.sql.ResultSet rs) {
    return new ApplicationUserPermissionSet(
        EntityMapper.read(rs, ApplicationUserPermissionSet.Fields.USER_ID),
        EntityMapper.read(rs, ApplicationPermission.Fields.RESOURCE),
        EntityMapper.read(rs, ApplicationPermission.Fields.ACTION),
        EntityMapper.read(rs, ApplicationPermission.Fields.ENABLED),
        EntityMapper.read(rs, ApplicationPermission.Fields.CREATED_AT),
        EntityMapper.read(rs, ApplicationUserPermissionSet.Fields.USER_ID),
        EntityMapper.read(rs, ApplicationUserPermissionSet.Fields.ALLOW));
  }

  public static void bind(ApplicationUserPermissionSet arps,
      BiConsumer<EntityField, Object> binder) {
    binder.accept(ApplicationUserPermissionSet.Fields.USER_ID, arps.getReferenceId());
    binder.accept(ApplicationPermission.Fields.RESOURCE, arps.getResource());
    binder.accept(ApplicationPermission.Fields.ACTION, arps.getAction().name());
    binder.accept(ApplicationPermission.Fields.ENABLED, arps.isEnabled());
    binder.accept(ApplicationPermission.Fields.CREATED_AT, arps.getCreatedAt());
    binder.accept(ApplicationUserPermissionSet.Fields.USER_ID, arps.getReferenceId());
    binder.accept(ApplicationUserPermissionSet.Fields.ALLOW, arps.isAllow());
  }

}
