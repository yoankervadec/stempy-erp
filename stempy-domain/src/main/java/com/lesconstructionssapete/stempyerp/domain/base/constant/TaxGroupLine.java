package com.lesconstructionssapete.stempyerp.domain.base.constant;

public class TaxGroupLine {

  private final long id;
  private final long taxGroupId;
  private final long taxRateId;
  private final int sortOrder;
  private TaxRate taxRate;

  public TaxGroupLine(
      long id,
      long taxGroupId,
      long taxRateId,
      int sortOrder) {
    this.id = id;
    this.taxGroupId = taxGroupId;
    this.taxRateId = taxRateId;
    this.sortOrder = sortOrder;
  }

  public long getId() {
    return id;
  }

  public long getTaxGroupId() {
    return taxGroupId;
  }

  public long getTaxRateId() {
    return taxRateId;
  }

  public int getSortOrder() {
    return sortOrder;
  }

  public TaxRate getTaxRate() {
    return taxRate;
  }

  public void setTaxRate(TaxRate taxRate) {
    this.taxRate = taxRate;
  }

}
