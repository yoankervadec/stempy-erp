package com.lesconstructionssapete.stempyerp.service.impl.authorization;

import java.util.BitSet;

class RolePermissions {

  private final long roleId;
  private final BitSet allow;
  private final BitSet deny;

  RolePermissions(long roleId, BitSet allow, BitSet deny) {
    this.roleId = roleId;
    this.allow = allow;
    this.deny = deny;
  }

  BitSet getAllow() {
    return allow;
  }

  BitSet getDeny() {
    return deny;
  }

}
