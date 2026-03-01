package com.lesconstructionssapete.stempyerp.core.domain.base.constant;

import com.lesconstructionssapete.stempyerp.core.shared.constant.ConstantEntity;

public class EntityType implements ConstantEntity {

  private final long id;
  private final String name;
  private final int padLength;
  private final String prefixString;
  private final boolean enabled;

  public EntityType(
      long id,
      String name,
      int padLength,
      String prefixString,
      boolean enabled) {
    this.id = id;
    this.name = name;
    this.padLength = padLength;
    this.prefixString = prefixString;
    this.enabled = enabled;
  }

  @Override
  public long getId() {
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
    return enabled;
  }

}
