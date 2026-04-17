package com.lesconstructionssapete.stempyerp.service.impl.authorization;

import java.util.BitSet;

class UserPermissions {

  private final BitSet allow;
  private final BitSet deny;

  UserPermissions(BitSet allow, BitSet deny) {
    this.allow = allow;
    this.deny = deny;
  }

  boolean has(int index) {
    if (deny.get(index))
      return false;
    return allow.get(index);
  }

  public BitSet getAllow() {
    return allow;
  }

  public BitSet getDeny() {
    return deny;
  }

  // Cache representation of UserPermissions for efficient storage in cache
  static final class UserPermissionsCache {
    public long[] allow;
    public long[] deny;
  }

  // Mapper to convert between UserPermissions and UserPermissionsCache for
  // caching purposes
  static final class UserPermissionsMapper {

    /**
     * Converts a UserPermissions object to a UserPermissionsCache object for
     * caching.
     * 
     * @param permissions The UserPermissions object to convert.
     * @return A UserPermissionsCache object representing the same permissions.
     */
    static UserPermissionsCache toCache(UserPermissions permissions) {
      UserPermissionsCache cache = new UserPermissionsCache();
      cache.allow = permissions.allow.toLongArray();
      cache.deny = permissions.deny.toLongArray();

      return cache;
    }

    /**
     * Converts a UserPermissionsCache object back to a UserPermissions object.
     * 
     * @param cache The UserPermissionsCache object to convert.
     * @return A UserPermissions object representing the same permissions.
     */
    static UserPermissions fromCache(UserPermissionsCache cache) {
      // Return null if cache is null to avoid NullPointerException (key not found)
      if (cache == null)
        return null;

      BitSet allow = BitSet.valueOf(cache.allow);
      BitSet deny = BitSet.valueOf(cache.deny);

      return new UserPermissions(allow, deny);
    }
  }

}
