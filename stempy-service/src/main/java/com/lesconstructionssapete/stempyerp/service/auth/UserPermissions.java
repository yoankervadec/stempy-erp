package com.lesconstructionssapete.stempyerp.service.auth;

import java.util.BitSet;

public class UserPermissions {

  private final BitSet allow;
  private final BitSet deny;

  public UserPermissions(BitSet allow, BitSet deny) {
    this.allow = allow;
    this.deny = deny;
  }

  public boolean has(int index) {
    if (deny.get(index))
      return false;
    return allow.get(index);
  }
}
