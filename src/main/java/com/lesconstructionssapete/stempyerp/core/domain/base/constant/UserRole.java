package com.lesconstructionssapete.stempyerp.core.domain.base.constant;

import com.lesconstructionssapete.stempyerp.core.shared.constant.ConstantEntity;

public class UserRole implements ConstantEntity {

  private final int id;
  private final String name;
  private final boolean isEnabled;

  public UserRole(int id, String name, boolean isEnabled) {
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
