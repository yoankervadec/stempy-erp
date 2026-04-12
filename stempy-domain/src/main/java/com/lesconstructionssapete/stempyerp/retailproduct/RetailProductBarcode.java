package com.lesconstructionssapete.stempyerp.retailproduct;

import java.time.Instant;

public class RetailProductBarcode {

  private Long retailProductBarcodeId;
  private Long retailProductMasterId;
  private Long retailProductVariantId;
  private boolean enabled;
  private final Instant createdAt;
  private final Long createdByUserId;
  private Instant updatedAt;
  private Long updatedByUserId;
  private String barcodeType;
  private String barcode;

  public RetailProductBarcode(
      Long retailProductBarcodeId,
      Long retailProductMasterId,
      Long retailProductVariantId,
      boolean enabled,
      Instant createdAt,
      Long createdByUserId,
      Instant updatedAt,
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
