package com.lesconstructionssapete.stempyerp.service.auth;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import com.lesconstructionssapete.stempyerp.cache.RedisCache;
import com.lesconstructionssapete.stempyerp.db.ConnectionProvider;
import com.lesconstructionssapete.stempyerp.domain.auth.ApplicationPermission;
import com.lesconstructionssapete.stempyerp.domain.auth.ApplicationPermissionSet;
import com.lesconstructionssapete.stempyerp.domain.auth.ApplicationRole;
import com.lesconstructionssapete.stempyerp.domain.shared.query.DomainQuery;
import com.lesconstructionssapete.stempyerp.field.auth.ApplicationPermissionField;
import com.lesconstructionssapete.stempyerp.field.auth.ApplicationRoleField;
import com.lesconstructionssapete.stempyerp.repository.auth.ApplicationPermissionRepository;

public final class AuthorizationModule {

  private AuthorizationModule() {
  }

  public static AuthorizationService initialize(
      ConnectionProvider connectionProvider,
      ApplicationPermissionRepository permissionRepository,
      RedisCache cache) {

    // 1. Fetch permissions and roles from the database
    List<ApplicationPermission> applicationPermissions;
    List<ApplicationRole> applicationRoles;
    List<ApplicationPermissionSet> rolePermissions;

    try (Connection connection = connectionProvider.getConnection()) {

      applicationPermissions = permissionRepository.fetchApplicationPermissions(
          connection,
          DomainQuery.builder()
              .where(w -> w.and(
                  c -> c.equals(ApplicationPermissionField.ENABLED, true)))
              .build());

      applicationRoles = permissionRepository.fetchApplicationRoles(
          connection,
          DomainQuery.builder()
              .where(w -> w.and(
                  c -> c.equals(ApplicationRoleField.ENABLED, true)))
              .build());

      rolePermissions = permissionRepository.fetchRolePermissions(
          connection,
          null); // Fetch all role permissions without filtering

    } catch (SQLException e) {
      throw new RuntimeException("Failed to initialize AuthorizationModule: " + e.getMessage(), e);
    }

    // 2. Map permissions to roles
    for (ApplicationRole role : applicationRoles) {
      for (ApplicationPermissionSet perm : rolePermissions) {
        if (perm.getReferenceId() == role.getId()) {
          role.getPermissions().add(perm);
        }
      }
    }

    // 3. Initialize the permission registry
    PermissionRegistryService registryService = new PermissionRegistryService();
    registryService.initialize(
        applicationPermissions);

    // 4. Initialize role and user permission services
    RolePermissionService roleService = new RolePermissionService();
    roleService.initialize(
        applicationRoles,
        registryService.get());

    // 5. Initialize the main authorization service
    UserPermissionService permissionService = new UserPermissionService(
        roleService,
        registryService,
        permissionRepository,
        connectionProvider,
        cache);

    // 6. Create and return the authorization service instance
    AuthorizationService authService = new AuthorizationServiceImpl(
        permissionService,
        registryService);

    return authService;
  }

}
