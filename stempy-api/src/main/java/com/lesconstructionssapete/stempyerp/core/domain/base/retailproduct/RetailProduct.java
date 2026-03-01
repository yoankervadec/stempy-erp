package com.lesconstructionssapete.stempyerp.core.domain.base.retailproduct;

import java.time.LocalDateTime;

import com.lesconstructionssapete.stempyerp.core.domain.generic.GenericEntity;

public class RetailProduct extends GenericEntity {

  public static final String RETAIL_PRODUCT_ENTITY_NAME = "RETAIL PRODUCT";

  private Long retailProductId;
  private Long retailProductMasterId;

  private String retailProductNo;
  private String retailProductVariantNo;

  private String name;
  private String description;

  private boolean enabled;

  private LocalDateTime updatedAt;
  private Long updatedByUserId;

  public RetailProduct(
      Long retailProductId,
      Long retailProductMasterId,
      String retailProductNo,
      String retailProductVariantNo,
      String name,
      String description,
      boolean enabled,
      LocalDateTime createdAt,
      Long createdByUserId,
      LocalDateTime updatedAt,
      Long updatedByUserId) {
    super(
        RETAIL_PRODUCT_ENTITY_NAME,
        retailProductNo,
        retailProductId,
        createdAt,
        createdByUserId);
    this.retailProductId = retailProductId;
    this.retailProductMasterId = retailProductMasterId;
    this.retailProductNo = retailProductNo;
    this.retailProductVariantNo = retailProductVariantNo;
    this.name = name;
    this.description = description;
    this.enabled = enabled;
    this.updatedAt = updatedAt;
    this.updatedByUserId = updatedByUserId;
  }

  public static String getRetailProductEntityName() {
    return RETAIL_PRODUCT_ENTITY_NAME;
  }

  public Long getRetailProductId() {
    return retailProductId;
  }

  public void setRetailProductId(Long retailProductId) {
    this.retailProductId = retailProductId;
  }

  public Long getRetailProductMasterId() {
    return retailProductMasterId;
  }

  public void setRetailProductMasterId(Long retailProductMasterId) {
    this.retailProductMasterId = retailProductMasterId;
  }

  public String getRetailProductNo() {
    return retailProductNo;
  }

  public void setRetailProductNo(String retailProductNo) {
    this.retailProductNo = retailProductNo;
  }

  public String getRetailProductVariantNo() {
    return retailProductVariantNo;
  }

  public void setRetailProductVariantNo(String retailProductVariantNo) {
    this.retailProductVariantNo = retailProductVariantNo;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public boolean isEnabled() {
    return enabled;
  }

  public void setEnabled(boolean enabled) {
    this.enabled = enabled;
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

}
