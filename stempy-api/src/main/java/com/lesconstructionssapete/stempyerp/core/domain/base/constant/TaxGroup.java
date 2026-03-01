package com.lesconstructionssapete.stempyerp.core.domain.base.constant;

import java.time.LocalDateTime;
import java.util.List;

import com.lesconstructionssapete.stempyerp.core.shared.constant.ConstantEntity;

public class TaxGroup implements ConstantEntity {

  private final long id;
  private final String name;
  private final String description;
  private final boolean enabled;
  private final LocalDateTime createdAt;
  private final List<TaxRate> taxRates;

  public TaxGroup(
      long id,
      String name,
      String description,
      boolean enabled,
      LocalDateTime createdAt,
      List<TaxRate> taxRates) {
    this.id = id;
    this.name = name;
    this.description = description;
    this.enabled = enabled;
    this.createdAt = createdAt;
    this.taxRates = taxRates;
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

  public List<TaxRate> getTaxRates() {
    return taxRates;
  }

}
