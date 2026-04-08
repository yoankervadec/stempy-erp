package com.lesconstructionssapete.stempyerp.service.auth;

import java.util.List;

import com.lesconstructionssapete.stempyerp.domain.auth.ApplicationPermission;

public class PermissionRegistryService {

  private PermissionRegistry registry;

  public void initialize(List<ApplicationPermission> permissions) {

    registry = new PermissionRegistry();

    int idx = 0;
    for (ApplicationPermission permission : permissions) {
      registry.register(
          permission.getId(),
          permission.getResource(),
          permission.getAction(),
          idx++);
    }

  }

  public PermissionRegistry get() {
    return registry;
  }

}
