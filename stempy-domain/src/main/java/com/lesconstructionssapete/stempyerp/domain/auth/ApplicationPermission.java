package com.lesconstructionssapete.stempyerp.domain.auth;

import java.time.Instant;

import com.lesconstructionssapete.stempyerp.annotation.AppAction;
import com.lesconstructionssapete.stempyerp.domain.field.EntityField;
import com.lesconstructionssapete.stempyerp.domain.field.FieldMeta;

public class ApplicationPermission {

  public enum Fields implements EntityField {

    ID(FieldMeta.builder("ApplicationPermission", "auth_permission")
        .field("id", "id")
        .type(Long.class, java.sql.Types.BIGINT)
        .notNullable()
        .build()),

    RESOURCE(FieldMeta.builder("ApplicationPermission", "auth_permission")
        .field("resource", "resource")
        .type(String.class, java.sql.Types.VARCHAR)
        .notNullable()
        .build()),

    ACTION(FieldMeta.builder("ApplicationPermission", "auth_permission")
        .field("action", "action")
        .type(String.class, java.sql.Types.VARCHAR)
        .notNullable()
        .build()),

    ENABLED(FieldMeta.builder("ApplicationPermission", "auth_permission")
        .field("enabled", "enabled")
        .type(Boolean.class, java.sql.Types.BOOLEAN)
        .notNullable()
        .build()),

    CREATED_AT(FieldMeta.builder("ApplicationPermission", "auth_permission")
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
  private final String resource; // e.g., "USER", "ORDER", "INVENTORY"
  private final AppAction action; // e.g., "CREATE", "READ", "UPDATE", "DELETE"
  private final boolean enabled;
  private final Instant createdAt;

  public ApplicationPermission(
      long id,
      String resource,
      AppAction action,
      boolean enabled,
      Instant createdAt) {
    this.id = id;
    this.resource = resource;
    this.action = action;
    this.enabled = enabled;
    this.createdAt = createdAt;
  }

  public String getPermissionKey() {
    return resource + ":" + action.name().toLowerCase();
  }

  public long getId() {
    return id;
  }

  public String getResource() {
    return resource;
  }

  public AppAction getAction() {
    return action;
  }

  public boolean isEnabled() {
    return enabled;
  }

  public Instant getCreatedAt() {
    return createdAt;
  }

}
