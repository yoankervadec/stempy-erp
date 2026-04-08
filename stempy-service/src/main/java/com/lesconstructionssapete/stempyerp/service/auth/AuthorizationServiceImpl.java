package com.lesconstructionssapete.stempyerp.service.auth;

import java.sql.Connection;

import com.lesconstructionssapete.stempyerp.domain.auth.ApplicationAction;

public class AuthorizationServiceImpl implements AuthorizationService {

  private final UserPermissionService permissionService;
  private final PermissionRegistryService registryService;

  public AuthorizationServiceImpl(
      UserPermissionService permissionService,
      PermissionRegistryService registryService) {
    this.permissionService = permissionService;
    this.registryService = registryService;
  }

  @Override
  public boolean has(Connection connection, long userId, String resource, ApplicationAction action) {

    UserPermissions permissions = permissionService.getUserPermissions(connection, userId);

    int idx = registryService.get().getIndex(resource + ":" + action.name().toLowerCase());

    return permissions.has(idx);
  }

}
