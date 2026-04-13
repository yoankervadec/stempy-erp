package com.lesconstructionssapete.stempyerp.domain.constant;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

public class TaxGroup implements ConstantEntity {

  private final long id;
  private final String name;
  private final String description;
  private final boolean enabled;
  private final Instant createdAt;
  private List<TaxGroupLine> rates;
  private List<TaxRate> innactiveRates;

  public TaxGroup(
      long id,
      String name,
      String description,
      boolean enabled,
      Instant createdAt) {
    this.id = id;
    this.name = name;
    this.description = description;
    this.enabled = enabled;
    this.createdAt = createdAt;

    this.rates = new ArrayList<>();
    this.innactiveRates = new ArrayList<>();
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

  public Instant getCreatedAt() {
    return createdAt;
  }

  public List<TaxGroupLine> getRates() {
    return rates;
  }

  public void setRates(List<TaxGroupLine> rates) {
    this.rates = rates;
  }

  public List<TaxRate> getInnactiveRates() {
    return innactiveRates;
  }

  public void setInnactiveRates(List<TaxRate> innactiveRates) {
    this.innactiveRates = innactiveRates;
  }
}
