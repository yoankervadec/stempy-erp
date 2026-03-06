package com.lesconstructionssapete.stempyerp.domain.retailproduct;

import java.time.LocalDateTime;

public class RetailProductBarcode {

  private Long retailProductBarcodeId;
  private Long retailProductMasterId;
  private Long retailProductVariantId;
  private boolean enabled;
  private final LocalDateTime createdAt;
  private final Long createdByUserId;
  private LocalDateTime updatedAt;
  private Long updatedByUserId;
  private String barcodeType;
  private String barcode;

  public RetailProductBarcode(
      Long retailProductBarcodeId,
      Long retailProductMasterId,
      Long retailProductVariantId,
      boolean enabled,
      LocalDateTime createdAt,
      Long createdByUserId,
      LocalDateTime updatedAt,
      Long updatedByUserId,
      String barcodeType,
      String barcode) {
    this.retailProductBarcodeId = retailProductBarcodeId;
    this.retailProductMasterId = retailProductMasterId;
    this.retailProductVariantId = retailProductVariantId;
    this.enabled = enabled;
    this.createdAt = createdAt;
    this.createdByUserId = createdByUserId;
    this.updatedAt = updatedAt;
    this.updatedByUserId = updatedByUserId;
    this.barcodeType = barcodeType;
    this.barcode = barcode;
  }

  public Long getRetailProductBarcodeId() {
    return retailProductBarcodeId;
  }

  public void setRetailProductBarcodeId(Long retailProductBarcodeId) {
    this.retailProductBarcodeId = retailProductBarcodeId;
  }

  public Long getRetailProductMasterId() {
    return retailProductMasterId;
  }

  public void setRetailProductMasterId(Long retailProductMasterId) {
    this.retailProductMasterId = retailProductMasterId;
  }

  public Long getRetailProductVariantId() {
    return retailProductVariantId;
  }

  public void setRetailProductVariantId(Long retailProductVariantId) {
    this.retailProductVariantId = retailProductVariantId;
  }

  public boolean isEnabled() {
    return enabled;
  }

  public void setEnabled(boolean enabled) {
    this.enabled = enabled;
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

  public String getBarcodeType() {
    return barcodeType;
  }

  public void setBarcodeType(String barcodeType) {
    this.barcodeType = barcodeType;
  }

  public String getBarcode() {
    return barcode;
  }

  public void setBarcode(String barcode) {
    this.barcode = barcode;
  }

}
