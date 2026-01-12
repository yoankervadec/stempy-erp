package com.lesconstructionssapete.stempyerp.core.domain.base.constant;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.lesconstructionssapete.stempyerp.core.shared.constant.ConstantEntity;

public class EntityType implements ConstantEntity {

  private final int id;
  private final String name;
  private final int padLength;
  private final String prefixString;
  private final boolean isEnabled;

  @JsonCreator
  public EntityType(
      @JsonProperty("id") int id,
      @JsonProperty("name") String name,
      @JsonProperty("padLength") int padLength,
      @JsonProperty("prefixString") String prefixString,
      @JsonProperty("isEnabled") boolean isEnabled) {
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
