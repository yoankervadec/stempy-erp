package com.lesconstructionssapete.stempyerp.service.impl.authorization;

import java.util.List;

import com.lesconstructionssapete.stempyerp.auth.ApplicationPermission;

class PermissionRegistryService {

  private PermissionRegistry registry;

  void initialize(List<ApplicationPermission> permissions) {

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

  PermissionRegistry get() {
    return registry;
  }

}
