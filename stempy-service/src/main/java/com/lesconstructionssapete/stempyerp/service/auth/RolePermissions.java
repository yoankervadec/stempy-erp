package com.lesconstructionssapete.stempyerp.service.auth;

import java.util.BitSet;

public class RolePermissions {

  private final long roleId;
  private final BitSet allow;
  private final BitSet deny;

  public RolePermissions(long roleId, BitSet allow, BitSet deny) {
    this.roleId = roleId;
    this.allow = allow;
    this.deny = deny;
  }

  public BitSet getAllow() {
    return allow;
  }

  public BitSet getDeny() {
    return deny;
  }

  public long getRoleId() {
    return roleId;
  }
}
