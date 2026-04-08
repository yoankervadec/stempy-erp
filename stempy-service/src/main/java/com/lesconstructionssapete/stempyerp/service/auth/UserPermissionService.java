package com.lesconstructionssapete.stempyerp.service.auth;

import java.sql.Connection;
import java.util.BitSet;
import java.util.List;
import java.util.Map;

import com.lesconstructionssapete.stempyerp.cache.RedisCache;
import com.lesconstructionssapete.stempyerp.domain.auth.ApplicationPermission;
import com.lesconstructionssapete.stempyerp.domain.auth.ApplicationRole;
import com.lesconstructionssapete.stempyerp.repository.auth.ApplicationPermissionRepository;

public class UserPermissionService {

  private final RolePermissionService roleService;
  private final PermissionRegistryService registryService;
  private final ApplicationPermissionRepository applicationPermissionRepository;
  private final RedisCache cache;

  public UserPermissionService(
      RolePermissionService roleService,
      PermissionRegistryService registryService,
      ApplicationPermissionRepository applicationPermissionRepository,
      RedisCache cache) {
    this.roleService = roleService;
    this.registryService = registryService;
    this.applicationPermissionRepository = applicationPermissionRepository;
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
  public UserPermissions getUserPermissions(Connection connection, long userId) {

    // 1. Try cache
    UserPermissions cached = cache.get("user_permissions:" + userId, UserPermissions.class);
    if (cached != null) {
      return cached;
    }

    PermissionRegistry registry = registryService.get();

    BitSet allow = new BitSet();
    BitSet deny = new BitSet();

    // 2. Load roles
    List<ApplicationRole> roles = applicationPermissionRepository.fetchUserRoles(connection, null);

    for (ApplicationRole role : roles) {
      RolePermissions rp = roleService.get(role.getId());

      if (rp == null)
        continue;

      allow.or(rp.getAllow());
      deny.or(rp.getDeny());
    }

    // 3. Apply user overrides
    Map<ApplicationPermission, Boolean> overrides = applicationPermissionRepository.fetchUserPermissions(connection,
        null);

    for (Map.Entry<ApplicationPermission, Boolean> userPermission : overrides.entrySet()) {
      int index = registry.getIndex(userPermission.getKey().getPermissionKey());
      if (index == -1)
        continue;

      if (userPermission.getValue()) {
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
