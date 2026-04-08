package com.lesconstructionssapete.stempyerp.domain.auth;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

public class ApplicationRole {
  private final long id;
  private final String name; // e.g., "ADMIN", "USER", "MANAGER"
  private final String description; // e.g., "Administrator role with full permissions"
  private final boolean enabled;
  private final Instant createdAt;
  private final Map<ApplicationPermission, Boolean> permissions = new HashMap<>();

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

  public Map<ApplicationPermission, Boolean> getPermissions() {
    return permissions;
  }

  public void setPermissions(Map<ApplicationPermission, Boolean> permissions) {
    if (permissions.isEmpty()) {
      throw new IllegalArgumentException("Permissions map cannot be empty");
    }
    this.permissions.putAll(permissions);
  }
}
