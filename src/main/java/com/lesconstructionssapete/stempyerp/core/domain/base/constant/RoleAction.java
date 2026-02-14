package com.lesconstructionssapete.stempyerp.core.domain.base.constant;

import com.lesconstructionssapete.stempyerp.core.shared.constant.ConstantEntity;

/**
 * Represents an action associated with a role.
 */

public class RoleAction implements ConstantEntity {

  private final int roleId;
  private final int actionId;
  private final String name;
  private final boolean enabled;

  public RoleAction(int roleId, int actionId, String name, boolean enabled) {
    this.roleId = roleId;
    this.actionId = actionId;
    this.name = name;
    this.enabled = enabled;
  }

  @Override
  public int getId() {
    return roleId;
  }

  public int getActionId() {
    return actionId;
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
