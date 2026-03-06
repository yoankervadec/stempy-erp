package com.lesconstructionssapete.stempyerp.domain.retailproduct;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class RetailProductPrice {

  private Long retailProductPriceId;
  private Long retailProductVariantId;
  private final LocalDateTime createdAt;
  private final Long createdByUserId;
  private LocalDateTime updatedAt;
  private Long updatedByUserId;
  private BigDecimal price;
  private LocalDateTime validFrom;
  private LocalDateTime validTo;

  public RetailProductPrice(
      Long retailProductPriceId,
      Long retailProductVariantId,
      final LocalDateTime createdAt,
      final Long createdByUserId,
      LocalDateTime updatedAt,
      Long updatedByUserId,
      BigDecimal price,
      LocalDateTime validFrom,
      LocalDateTime validTo) {
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

  public BigDecimal getPrice() {
    return price;
  }

  public void setPrice(BigDecimal price) {
    this.price = price;
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
