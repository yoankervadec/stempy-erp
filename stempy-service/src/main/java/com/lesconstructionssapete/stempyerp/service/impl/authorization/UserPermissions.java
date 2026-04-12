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

}
