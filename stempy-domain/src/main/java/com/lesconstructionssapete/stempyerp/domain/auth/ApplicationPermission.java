package com.lesconstructionssapete.stempyerp.domain.auth;

import java.time.Instant;

public class ApplicationPermission {
  private final long id;
  private final String resource; // e.g., "USER", "ORDER", "INVENTORY"
  private final ApplicationAction action; // e.g., "CREATE", "READ", "UPDATE", "DELETE"
  private final boolean enabled;
  private final Instant createdAt;

  public ApplicationPermission(
      long id,
      String resource,
      ApplicationAction action,
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

  public ApplicationAction getAction() {
    return action;
  }

  public boolean isEnabled() {
    return enabled;
  }

  public Instant getCreatedAt() {
    return createdAt;
  }

}
