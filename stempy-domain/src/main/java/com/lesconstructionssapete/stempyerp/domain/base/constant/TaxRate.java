package com.lesconstructionssapete.stempyerp.domain.base.constant;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class TaxRate implements ConstantEntity {

  private final long id;
  private final String name;
  private final String description;
  private final boolean enabled;
  private final LocalDateTime createdAt;
  private final double rate;
  private final boolean compound;
  private final int calculationOrder;
  private final LocalDate validFrom;
  private final LocalDate validTo;

  public TaxRate(
      long id,
      String name,
      String description,
      boolean enabled,
      LocalDateTime createdAt,
      double rate,
      boolean compound,
      int calculationOrder,
      LocalDate validFrom,
      LocalDate validTo) {
    this.id = id;
    this.name = name;
    this.description = description;
    this.enabled = enabled;
    this.createdAt = createdAt;
    this.rate = rate;
    this.compound = compound;
    this.calculationOrder = calculationOrder;
    this.validFrom = validFrom;
    this.validTo = validTo;
  }

  @Override
  public long getId() {
    return id;
  }

  @Override
  public String getName() {
    return name;
  }

  public double getRate() {
    return rate;
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

  public boolean isCompound() {
    return compound;
  }

  public int getCalculationOrder() {
    return calculationOrder;
  }

  public LocalDate getValidFrom() {
    return validFrom;
  }

  public LocalDate getValidTo() {
    return validTo;
  }

}
