package com.lesconstructions.sapete.stempyerp.core.domain.base.constant;

import com.lesconstructions.sapete.stempyerp.core.shared.constant.ConstantEntity;

public class UserAction implements ConstantEntity {

  private final int id;
  private final String name;
  private final boolean isEnabled;

  public UserAction(int id, String name, boolean isEnabled) {
    this.id = id;
    this.name = name;
    this.isEnabled = isEnabled;
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
    return isEnabled;
  }
}
