package com.lesconstructionssapete.stempyerp.domain.constant;

import java.time.Instant;

public class DomainEntityType implements ConstantEntity {

  private final long id;
  private final String name;
  private final boolean enabled;
  private final Instant createdAt;
  private final int padLength;
  private final String prefix;

  public DomainEntityType(
      long id,
      String name,
      boolean enabled,
      Instant createdAt,
      int padLength,
      String prefix) {
    this.id = id;
    this.name = name;
    this.enabled = enabled;
    this.createdAt = createdAt;
    this.padLength = padLength;
    this.prefix = prefix;
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

  public Instant getCreatedAt() {
    return createdAt;
  }

  public int getPadLength() {
    return padLength;
  }

  public String getPrefix() {
    return prefix;
  }

}
