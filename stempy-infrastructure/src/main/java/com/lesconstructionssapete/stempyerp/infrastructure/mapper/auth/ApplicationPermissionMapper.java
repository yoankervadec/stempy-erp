package com.lesconstructionssapete.stempyerp.infrastructure.mapper.auth;

import java.sql.ResultSet;
import java.util.function.BiConsumer;

import com.lesconstructionssapete.stempyerp.domain.auth.ApplicationPermission;
import com.lesconstructionssapete.stempyerp.domain.field.EntityField;
import com.lesconstructionssapete.stempyerp.infrastructure.mapper.EntityMapper;

public final class ApplicationPermissionMapper {

  private ApplicationPermissionMapper() {
  }

  public static ApplicationPermission fromResultSet(ResultSet rs) {
    return new ApplicationPermission(
        EntityMapper.read(rs, ApplicationPermission.Fields.ID),
        EntityMapper.read(rs, ApplicationPermission.Fields.RESOURCE),
        EntityMapper.read(rs, ApplicationPermission.Fields.ACTION),
        EntityMapper.read(rs, ApplicationPermission.Fields.ENABLED),
        EntityMapper.read(rs, ApplicationPermission.Fields.CREATED_AT));
  }

  public static void bind(ApplicationPermission ap, BiConsumer<EntityField, Object> binder) {
    binder.accept(ApplicationPermission.Fields.ID, ap.getId());
    binder.accept(ApplicationPermission.Fields.RESOURCE, ap.getResource());
    binder.accept(ApplicationPermission.Fields.ACTION, ap.getAction().name());
    binder.accept(ApplicationPermission.Fields.ENABLED, ap.isEnabled());
    binder.accept(ApplicationPermission.Fields.CREATED_AT, ap.getCreatedAt());
  }

}
