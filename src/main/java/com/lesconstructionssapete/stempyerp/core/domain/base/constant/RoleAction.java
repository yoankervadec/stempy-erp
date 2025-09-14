package com.lesconstructionssapete.stempyerp.core.domain.base.constant;

import com.lesconstructionssapete.stempyerp.core.shared.constant.ConstantEntity;

public class RoleAction implements ConstantEntity {

  private final int roleId;
  private final int actionId;
  private final String name;
  private final boolean isEnabled;

  public RoleAction(int roleId, int actionId, String name, boolean isEnabled) {
    this.roleId = roleId;
    this.actionId = actionId;
    this.name = name;
    this.isEnabled = isEnabled;
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
    return isEnabled;
  }

}
