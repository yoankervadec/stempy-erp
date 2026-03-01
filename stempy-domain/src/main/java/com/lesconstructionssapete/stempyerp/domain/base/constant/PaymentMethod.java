package com.lesconstructionssapete.stempyerp.domain.base.constant;

public class PaymentMethod implements ConstantEntity {

  private final long id;
  private final String name;
  private final boolean isEnabled;

  public PaymentMethod(long id, String name, boolean isEnabled) {
    this.id = id;
    this.name = name;
    this.isEnabled = isEnabled;
  }

  @Override
  public long getId() {
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
