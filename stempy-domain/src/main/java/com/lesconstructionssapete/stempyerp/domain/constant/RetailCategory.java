package com.lesconstructionssapete.stempyerp.domain.constant;

import java.time.Instant;

public class RetailCategory implements ConstantEntity {

  private final long id;
  private final String name;
  private final String description;
  private final boolean enabled;
  private final Instant createdAt;

  public RetailCategory(long id, String name, String description, boolean enabled, Instant createdAt) {
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

  public String getDescription() {
    return description;
  }

  @Override
  public boolean isEnabled() {
    return enabled;
  }

  public Instant getCreatedAt() {
    return createdAt;
  }

}
