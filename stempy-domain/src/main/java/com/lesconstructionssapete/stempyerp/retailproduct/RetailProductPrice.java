package com.lesconstructionssapete.stempyerp.retailproduct;

import java.math.BigDecimal;
import java.time.Instant;

public class RetailProductPrice {

  private Long retailProductPriceId;
  private Long retailProductVariantId;
  private final Instant createdAt;
  private final Long createdByUserId;
  private Instant updatedAt;
  private Long updatedByUserId;
  private BigDecimal price;
  private Instant validFrom;
  private Instant validTo;

  public RetailProductPrice(
      Long retailProductPriceId,
      Long retailProductVariantId,
      final Instant createdAt,
      final Long createdByUserId,
      Instant updatedAt,
      Long updatedByUserId,
      BigDecimal price,
      Instant validFrom,
      Instant validTo) {
    this.retailProductPriceId = retailProductPriceId;
    this.retailProductVariantId = retailProductVariantId;
    this.createdAt = createdAt;
    this.createdByUserId = createdByUserId;
    this.updatedAt = updatedAt;
    this.updatedByUserId = updatedByUserId;
    this.price = price;
    this.validFrom = validFrom;
    this.validTo = validTo;
  }

  public Long getRetailProductPriceId() {
    return retailProductPriceId;
  }

  public void setRetailProductPriceId(Long retailProductPriceId) {
    this.retailProductPriceId = retailProductPriceId;
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

  public BigDecimal getPrice() {
    return price;
  }

  public void setPrice(BigDecimal price) {
    this.price = price;
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
