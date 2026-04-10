package com.lesconstructionssapete.stempyerp.service.auth;

import java.util.BitSet;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.lesconstructionssapete.stempyerp.domain.auth.ApplicationPermissionSet;
import com.lesconstructionssapete.stempyerp.domain.auth.ApplicationRole;

class RolePermissionService {

  private final Map<Long, RolePermissions> roleCache = new HashMap<>(); // roleId -> permissions

  void initialize(List<ApplicationRole> rolePermissions, PermissionRegistry registry) {
    Map<Long, BitSet> allowMap = new HashMap<>();
    Map<Long, BitSet> denyMap = new HashMap<>();

    roleCache.clear(); // Clear existing cache before initializing (prevent stale data)

    for (ApplicationRole role : rolePermissions) {

      for (ApplicationPermissionSet permission : role.getPermissions()) {
        boolean isAllow = permission.isAllow();
        int index = registry.getIndex(permission.getId());

        BitSet allow = allowMap.computeIfAbsent(role.getId(), k -> new BitSet());
        BitSet deny = denyMap.computeIfAbsent(role.getId(), k -> new BitSet());

        if (isAllow)
          allow.set(index);
        else
          deny.set(index);
      }

    }

    for (Long roleId : allowMap.keySet()) {
      roleCache.put(
          roleId,
          new RolePermissions(
              roleId,
              allowMap.getOrDefault(roleId, new BitSet()),
              denyMap.getOrDefault(roleId, new BitSet())));
    }

  }

  /**
   * Get permissions for a role. Returns null if role not found. Caller should
   * handle null case.
   *
   * @param roleId The ID of the role whose permissions are to be retrieved.
   * @return A RolePermissions object containing the permissions of the role, or
   *         null if the role is not found.
   */
  RolePermissions get(long roleId) {
    return roleCache.get(roleId);
  }

}
