package com.lesconstructionssapete.stempyerp.core.domain.base.constant;

import com.lesconstructionssapete.stempyerp.core.shared.constant.ConstantEntity;

/**
 * Represents a user role (assigned with pre-defined permissions).
 */

public class UserRole implements ConstantEntity {

  private final int id;
  private final String name;
  private final boolean enabled;

  public UserRole(int id, String name, boolean enabled) {
    this.id = id;
    this.name = name;
    this.enabled = enabled;
  }

  @Override
  public int getId() {
    return id;
  }

  @Override
  public String getName() {
    return name;
  }

  @Override
  public boolean isEnabled() {
    return enabled;
  }
}
