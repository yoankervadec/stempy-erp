package com.lesconstructionssapete.stempyerp.domain.retailproduct;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class RetailProductMaster {

  private Long retailProductMasterId;
  private String retailProductMasterNo;

  private String name;
  private String description;

  private boolean enabled;

  private final LocalDateTime createdAt;
  private final Long createdByUserId;
  private LocalDateTime updatedAt;
  private Long updatedByUserId;

  private Long retailCategoryId;

  private BigDecimal defaultPrice;

  private RetailProductMasterPolicy policy;

  public RetailProductMaster(
      Long retailProductMasterId,
      String retailProductMasterNo,
      String name,
      String description,
      boolean enabled,
      LocalDateTime createdAt,
      Long createdByUserId,
      LocalDateTime updatedAt,
      Long updatedByUserId,
      Long retailCategoryId,
      BigDecimal defaultPrice,
      RetailProductMasterPolicy policy) {
    this.retailProductMasterId = retailProductMasterId;
    this.retailProductMasterNo = retailProductMasterNo;
    this.name = name;
    this.description = description;
    this.enabled = enabled;
    this.createdAt = createdAt;
    this.createdByUserId = createdByUserId;
    this.updatedAt = updatedAt;
    this.updatedByUserId = updatedByUserId;
    this.retailCategoryId = retailCategoryId;
    this.defaultPrice = defaultPrice;
    this.policy = policy;
  }

  public Long getRetailProductMasterId() {
    return retailProductMasterId;
  }

  public void setRetailProductMasterId(Long retailProductMasterId) {
    this.retailProductMasterId = retailProductMasterId;
  }

  public String getRetailProductMasterNo() {
    return retailProductMasterNo;
  }

  public void setRetailProductMasterNo(String retailProductMasterNo) {
    this.retailProductMasterNo = retailProductMasterNo;
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

  public Long getRetailCategoryId() {
    return retailCategoryId;
  }

  public void setRetailCategoryId(Long retailCategoryId) {
    this.retailCategoryId = retailCategoryId;
  }

  public BigDecimal getDefaultPrice() {
    return defaultPrice;
  }

  public void setDefaultPrice(BigDecimal defaultPrice) {
    this.defaultPrice = defaultPrice;
  }

  public RetailProductMasterPolicy getPolicy() {
    return policy;
  }

  public void setPolicy(RetailProductMasterPolicy policy) {
    this.policy = policy;
  }

}
