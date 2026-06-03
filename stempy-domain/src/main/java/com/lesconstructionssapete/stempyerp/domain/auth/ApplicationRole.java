package com.lesconstructionssapete.stempyerp.domain.auth;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import com.lesconstructionssapete.stempyerp.domain.field.EntityField;
import com.lesconstructionssapete.stempyerp.domain.field.FieldMeta;

public class ApplicationRole {

  public enum Fields implements EntityField {

    ID(FieldMeta.builder("ApplicationRole", "auth_role")
        .field("id", "id")
        .type(Long.class, java.sql.Types.BIGINT)
        .notNullable()
        .build()),

    NAME(FieldMeta.builder("ApplicationRole", "auth_role")
        .field("name", "name")
        .type(String.class, java.sql.Types.VARCHAR)
        .notNullable()
        .build()),

    DESCRIPTION(FieldMeta.builder("ApplicationRole", "auth_role")
        .field("description", "description")
        .type(String.class, java.sql.Types.VARCHAR)
        .notNullable()
        .build()),

    ENABLED(FieldMeta.builder("ApplicationRole", "auth_role")
        .field("enabled", "enabled")
        .type(Boolean.class, java.sql.Types.BOOLEAN)
        .notNullable()
        .build()),

    CREATED_AT(FieldMeta.builder("ApplicationRole", "auth_role")
        .field("createdAt", "created_at")
        .type(Instant.class, java.sql.Types.TIMESTAMP)
        .notNullable()
        .build());

    private final FieldMeta meta;

    Fields(FieldMeta meta) {
      this.meta = meta;
    }

    @Override
    public FieldMeta meta() {
      return meta;
    }
  }

  private final long id;
  private final String name; // e.g., "ADMIN", "USER", "MANAGER"
  private final String description; // e.g., "Administrator role with full permissions"
  private final boolean enabled;
  private final Instant createdAt;
  private final List<ApplicationPermissionSet> permissions = new ArrayList<>();

  public ApplicationRole(
      long id,
      String name,
      String description,
      boolean enabled,
      Instant createdAt) {
    this.id = id;
    this.name = name;
    this.description = description;
    this.enabled = enabled;
    this.createdAt = createdAt;
  }

  public long getId() {
    return id;
  }

  public String getName() {
    return name;
  }

  public String getDescription() {
    return description;
  }

  public boolean isEnabled() {
    return enabled;
  }

  public Instant getCreatedAt() {
    return createdAt;
  }

  public List<ApplicationPermissionSet> getPermissions() {
    return permissions;
  }

  public void setPermissions(List<ApplicationPermissionSet> permissions) {
    this.permissions.clear();
    this.permissions.addAll(permissions);
  }
}
