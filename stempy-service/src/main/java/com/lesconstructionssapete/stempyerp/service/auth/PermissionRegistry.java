package com.lesconstructionssapete.stempyerp.service.auth;

import java.util.HashMap;
import java.util.Map;

import com.lesconstructionssapete.stempyerp.domain.auth.ApplicationAction;

class PermissionRegistry {

  private final Map<Long, Integer> permissionIdToIndex = new HashMap<>(); // permissionId -> index
  private final Map<String, Integer> keyToIndex = new HashMap<>(); // "resource:action" -> index

  void register(long permissionId, String resource, ApplicationAction action, int index) {
    permissionIdToIndex.put(permissionId, index);

    String key = resource + ":" + action.name().toLowerCase();

    if (keyToIndex.containsKey(key)) {
      throw new IllegalArgumentException("Permission already registered for key: " + key);
    }
    keyToIndex.put(key, index);
  }

  int getIndex(long permissionId) {
    return permissionIdToIndex.getOrDefault(permissionId, -1);
  }

  int getIndex(String key) {
    return keyToIndex.getOrDefault(key, -1);
  }
}