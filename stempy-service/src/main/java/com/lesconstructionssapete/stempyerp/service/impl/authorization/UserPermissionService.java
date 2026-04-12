package com.lesconstructionssapete.stempyerp.service.impl.authorization;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.BitSet;
import java.util.List;

import com.lesconstructionssapete.stempyerp.auth.ApplicationPermissionSet;
import com.lesconstructionssapete.stempyerp.auth.ApplicationRole;
import com.lesconstructionssapete.stempyerp.cache.RedisCache;
import com.lesconstructionssapete.stempyerp.db.ConnectionProvider;
import com.lesconstructionssapete.stempyerp.field.auth.ApplicationPermissionSetField;
import com.lesconstructionssapete.stempyerp.field.auth.ApplicationUserRoleField;
import com.lesconstructionssapete.stempyerp.query.DomainQuery;
import com.lesconstructionssapete.stempyerp.repository.auth.ApplicationPermissionRepository;

class UserPermissionService {

  private final RolePermissionService roleService;
  private final PermissionRegistryService registryService;
  private final ApplicationPermissionRepository applicationPermissionRepository;
  private final ConnectionProvider connectionProvider;
  private final RedisCache cache;

  UserPermissionService(
      RolePermissionService roleService,
      PermissionRegistryService registryService,
      ApplicationPermissionRepository applicationPermissionRepository,
      ConnectionProvider connectionProvider,
      RedisCache cache) {
    this.roleService = roleService;
    this.registryService = registryService;
    this.applicationPermissionRepository = applicationPermissionRepository;
    this.connectionProvider = connectionProvider;
    this.cache = cache;
  }

  /**
   * Retrieves the permissions for a given user. This method first checks the
   * cache for the user's permissions. If not found, it loads the user's roles and
   * their associated permissions, applies any user-specific overrides, and then
   * caches the result for future use.
   *
   * @param connection The database connection to use for retrieving permissions.
   * @param userId     The ID of the user whose permissions are to be retrieved.
   * @return A UserPermissions object containing the permissions of the user.
   */
  UserPermissions getUserPermissions(long userId) {

    // 1. Try cache
    UserPermissions cached = cache.get("user_permissions:" + userId, UserPermissions.class);
    if (cached != null) {
      return cached;
    }

    // 2. Load roles
    List<ApplicationRole> roles;
    try (Connection connection = connectionProvider.getConnection()) {

      roles = applicationPermissionRepository.fetchUserApplicationRoles(
          connection, DomainQuery.builder()
              .where(w -> w.and(
                  c -> c.equals(ApplicationUserRoleField.USER_ID, userId)))
              .build());
    } catch (SQLException e) {
      throw new RuntimeException("Failed to fetch user roles: " + e.getMessage(), e);
    }

    PermissionRegistry registry = registryService.get();

    BitSet allow = new BitSet();
    BitSet deny = new BitSet();

    for (ApplicationRole role : roles) {
      RolePermissions rp = roleService.get(role.getId());

      if (rp == null)
        continue;

      allow.or(rp.getAllow());
      deny.or(rp.getDeny());
    }

    // 3. Apply user overrides
    List<ApplicationPermissionSet> overrides;
    try (Connection connection = connectionProvider.getConnection()) {
      overrides = applicationPermissionRepository.fetchUserPermissions(
          connection,
          DomainQuery.builder()
              .where(w -> w.and(
                  c -> c.equals(ApplicationPermissionSetField.REFERENCE_ID, userId)))
              .build());
    } catch (SQLException e) {
      throw new RuntimeException("Failed to fetch user permissions: " + e.getMessage(), e);
    }

    for (ApplicationPermissionSet userPermission : overrides) {
      int index = registry.getIndex(userPermission.getPermissionKey());
      if (index == -1)
        continue;

      if (userPermission.isAllow()) {
        allow.set(index);
        deny.clear(index);
      } else {
        deny.set(index);
        allow.clear(index);
      }
    }

    // 4. Cache
    UserPermissions result = new UserPermissions(allow, deny);
    cache.set("user_permissions:" + userId, result);

    return result;
  }

}
