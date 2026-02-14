package com.lesconstructionssapete.stempyerp.core.domain.base.constant;

import com.lesconstructionssapete.stempyerp.core.shared.constant.ConstantEntity;

/**
 * Represents an action associated to a ressource.
 */

public class UserAction implements ConstantEntity {

  private final int id;
  private final String name;
  private final boolean enabled;

  public UserAction(int id, String name, boolean enabled) {
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
