package com.lesconstructionssapete.stempyerp.retailproduct;

import java.math.BigDecimal;
import java.time.Instant;

public class RetailProductCost {

  private Long retailProductCostId;
  private Long retailProductVariantId;
  private final Instant createdAt;
  private final Long createdByUserId;
  private Instant updatedAt;
  private Long updatedByUserId;
  private BigDecimal cost;
  private Instant validFrom;
  private Instant validTo;

  public RetailProductCost(
      Long retailProductCostId,
      Long retailProductVariantId,
      final Instant createdAt,
      final Long createdByUserId,
      Instant updatedAt,
      Long updatedByUserId,
      BigDecimal cost,
      Instant validFrom,
      Instant validTo) {
    this.retailProductCostId = retailProductCostId;
    this.retailProductVariantId = retailProductVariantId;
    this.createdAt = createdAt;
    this.createdByUserId = createdByUserId;
    this.updatedAt = updatedAt;
    this.updatedByUserId = updatedByUserId;
    this.cost = cost;
    this.validFrom = validFrom;
    this.validTo = validTo;
  }

  public Long getRetailProductCostId() {
    return retailProductCostId;
  }

  public void setRetailProductCostId(Long retailProductCostId) {
    this.retailProductCostId = retailProductCostId;
  }

  public Long getRetailProductVariantId() {
    return retailProductVariantId;
  }

  public void setRetailProductVariantId(Long retailProductVariantId) {
    this.retailProductVariantId = retailProductVariantId;
  }

  public Instant getCreatedAt() {
    return createdAt;
  }

  public Long getCreatedByUserId() {
    return createdByUserId;
  }

  public Instant getUpdatedAt() {
    return updatedAt;
  }

  public void setUpdatedAt(Instant updatedAt) {
    this.updatedAt = updatedAt;
  }

  public Long getUpdatedByUserId() {
    return updatedByUserId;
  }

  public void setUpdatedByUserId(Long updatedByUserId) {
    this.updatedByUserId = updatedByUserId;
  }

  public BigDecimal getCost() {
    return cost;
  }

  public void setCost(BigDecimal cost) {
    this.cost = cost;
  }

  public Instant getValidFrom() {
    return validFrom;
  }

  public void setValidFrom(Instant validFrom) {
    this.validFrom = validFrom;
  }

  public Instant getValidTo() {
    return validTo;
  }

  public void setValidTo(Instant validTo) {
    this.validTo = validTo;
  }

}
