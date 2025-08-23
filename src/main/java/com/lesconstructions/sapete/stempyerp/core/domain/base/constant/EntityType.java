package com.lesconstructions.sapete.stempyerp.core.domain.base.constant;

import com.lesconstructions.sapete.stempyerp.core.shared.constant.ConstantEntity;

public class EntityType implements ConstantEntity {

  private final int id;
  private final String name;
  private final int padLength;
  private final String prefixString;
  private final boolean isEnabled;

  public EntityType(int id, String name, int padLength, String prefixString, boolean isEnabled) {
    this.id = id;
    this.name = name;
    this.padLength = padLength;
    this.prefixString = prefixString;
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

  public int getPadLength() {
    return padLength;
  }

  public String getPrefixString() {
    return prefixString.toUpperCase();
  }

  @Override
  public boolean isEnabled() {
    return isEnabled;
  }

}
