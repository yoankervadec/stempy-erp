package com.lesconstructionssapete.stempyerp.service.auth;

import com.lesconstructionssapete.stempyerp.annotation.ApplicationAction;

class AuthorizationServiceImpl implements AuthorizationService {

  private final UserPermissionService permissionService;
  private final PermissionRegistryService registryService;

  AuthorizationServiceImpl(
      UserPermissionService permissionService,
      PermissionRegistryService registryService) {
    this.permissionService = permissionService;
    this.registryService = registryService;
  }

  @Override
  public boolean has(long userId, String resource, ApplicationAction action) {

    UserPermissions permissions = permissionService.getUserPermissions(userId);

    int idx = registryService.get().getIndex(resource + ":" + action.name().toLowerCase());

    if (idx == -1)
      return false; // Permission not registered

    return permissions.has(idx);
  }

}
