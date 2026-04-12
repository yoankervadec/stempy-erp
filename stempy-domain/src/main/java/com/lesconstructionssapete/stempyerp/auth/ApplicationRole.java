package com.lesconstructionssapete.stempyerp.auth;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

public class ApplicationRole {
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
