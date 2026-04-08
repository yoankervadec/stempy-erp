package com.lesconstructionssapete.stempyerp.service.auth;

import java.util.HashMap;
import java.util.Map;

import com.lesconstructionssapete.stempyerp.domain.auth.ApplicationAction;

public class PermissionRegistry {

  private final Map<Long, Integer> permissionIdToIndex = new HashMap<>();
  private final Map<String, Integer> keyToIndex = new HashMap<>();

  public void register(long permissionId, String resource, ApplicationAction action, int index) {
    permissionIdToIndex.put(permissionId, index);
    keyToIndex.put(resource + ":" + action.name().toLowerCase(), index);
  }

  public int getIndex(long permissionId) {
    return permissionIdToIndex.get(permissionId);
  }

  public int getIndex(String key) {
    return keyToIndex.get(key);
  }
}