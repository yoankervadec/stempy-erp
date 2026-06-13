package com.lesconstructionssapete.stempyerp.infrastructure.mapper.auth;

import java.sql.ResultSet;
import java.util.function.BiConsumer;

import com.lesconstructionssapete.stempyerp.domain.auth.ApplicationRole;
import com.lesconstructionssapete.stempyerp.domain.field.EntityField;
import com.lesconstructionssapete.stempyerp.infrastructure.mapper.EntityMapper;

public final class ApplicationRoleMapper {

  private ApplicationRoleMapper() {
  }

  public static ApplicationRole fromResultSet(ResultSet rs) {
    return new ApplicationRole(
        EntityMapper.read(rs, ApplicationRole.Fields.ID),
        EntityMapper.read(rs, ApplicationRole.Fields.NAME),
        EntityMapper.read(rs, ApplicationRole.Fields.DESCRIPTION),
        EntityMapper.read(rs, ApplicationRole.Fields.ENABLED),
        EntityMapper.read(rs, ApplicationRole.Fields.CREATED_AT));
  }

  public static void bind(ApplicationRole ar, BiConsumer<EntityField, Object> binder) {
    binder.accept(ApplicationRole.Fields.ID, ar.getId());
    binder.accept(ApplicationRole.Fields.NAME, ar.getName());
    binder.accept(ApplicationRole.Fields.DESCRIPTION, ar.getDescription());
    binder.accept(ApplicationRole.Fields.ENABLED, ar.isEnabled());
    binder.accept(ApplicationRole.Fields.CREATED_AT, ar.getCreatedAt());
  }

}
