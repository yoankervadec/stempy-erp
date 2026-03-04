package com.lesconstructionssapete.stempyerp.domain.base.constant;

import java.time.LocalDateTime;

public class PaymentMethod implements ConstantEntity {

  private final long id;
  private final String name;
  private final String description;
  private final boolean enabled;
  private final LocalDateTime createdAt;

  public PaymentMethod(long id, String name, String description, boolean enabled, LocalDateTime createdAt) {
    this.id = id;
    this.name = name;
    this.description = description;
    this.enabled = enabled;
    this.createdAt = createdAt;
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
    return enabled;
  }

  public String getDescription() {
    return description;
  }

  public LocalDateTime getCreatedAt() {
    return createdAt;
  }

}
