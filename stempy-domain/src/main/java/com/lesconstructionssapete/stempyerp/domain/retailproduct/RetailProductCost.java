package com.lesconstructionssapete.stempyerp.domain.retailproduct;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class RetailProductCost {

  private Long retailProductCostId;
  private Long retailProductVariantId;
  private final LocalDateTime createdAt;
  private final Long createdByUserId;
  private LocalDateTime updatedAt;
  private Long updatedByUserId;
  private BigDecimal cost;
  private LocalDateTime validFrom;
  private LocalDateTime validTo;

  public RetailProductCost(
      Long retailProductCostId,
      Long retailProductVariantId,
      final LocalDateTime createdAt,
      final Long createdByUserId,
      LocalDateTime updatedAt,
      Long updatedByUserId,
      BigDecimal cost,
      LocalDateTime validFrom,
      LocalDateTime validTo) {
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

  public LocalDateTime getCreatedAt() {
    return createdAt;
  }

  public Long getCreatedByUserId() {
    return createdByUserId;
  }

  public LocalDateTime getUpdatedAt() {
    return updatedAt;
  }

  public void setUpdatedAt(LocalDateTime updatedAt) {
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

  public LocalDateTime getValidFrom() {
    return validFrom;
  }

  public void setValidFrom(LocalDateTime validFrom) {
    this.validFrom = validFrom;
  }

  public LocalDateTime getValidTo() {
    return validTo;
  }

  public void setValidTo(LocalDateTime validTo) {
    this.validTo = validTo;
  }

}
