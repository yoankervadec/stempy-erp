package com.lesconstructionssapete.stempyerp.domain.auth;

import java.time.Instant;

import com.lesconstructionssapete.stempyerp.annotation.ApplicationAction;

public class ApplicationPermissionSet extends ApplicationPermission {
  private final long referenceId; // ID of the referenced entity (e.g., Role, User)
  private final boolean allow;

  public ApplicationPermissionSet(
      long id,
      String resource,
      ApplicationAction action,
      boolean enabled,
      Instant createdAt,
      long referenceId,
      boolean allow) {
    super(id, resource, action, enabled, createdAt);
    this.referenceId = referenceId;
    this.allow = allow;
  }

  public long getReferenceId() {
    return referenceId;
  }

  public boolean isAllow() {
    return allow;
  }

}
