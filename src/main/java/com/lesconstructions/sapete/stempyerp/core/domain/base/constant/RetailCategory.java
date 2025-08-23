package com.lesconstructions.sapete.stempyerp.core.domain.base.constant;

import com.lesconstructions.sapete.stempyerp.core.shared.constant.ConstantEntity;

public class RetailCategory implements ConstantEntity {

  private final int id;
  private final String name;
  private final String description;
  private final boolean isEnabled;

  public RetailCategory(int id, String name, String description, boolean isEnabled) {
    this.id = id;
    this.name = name;
    this.description = description;
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

  public String getDescription() {
    return description;
  }

  @Override
  public boolean isEnabled() {
    return isEnabled;
  }

}
