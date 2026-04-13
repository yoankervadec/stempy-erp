package com.lesconstructionssapete.stempyerp.domain.retailproduct;

import java.math.BigDecimal;
import java.time.Instant;

import com.lesconstructionssapete.stempyerp.domain.generic.GenericEntity;

public class RetailProductMaster extends GenericEntity {

  public static final String RETAIL_PRODUCT_MASTER_ENTITY_NAME = "RETAIL PRODUCT MASTER";

  private String name;
  private String description;

  private boolean enabled;

  private Instant updatedAt;
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
      Instant createdAt,
      Long createdByUserId,
      Instant updatedAt,
      Long updatedByUserId,
      Long retailCategoryId,
      BigDecimal defaultPrice,
      RetailProductMasterPolicy policy) {
    super(
        RETAIL_PRODUCT_MASTER_ENTITY_NAME,
        retailProductMasterNo,
        retailProductMasterId,
        createdAt,
        createdByUserId);
    this.name = name;
    this.description = description;
    this.enabled = enabled;
    this.updatedAt = updatedAt;
    this.updatedByUserId = updatedByUserId;
    this.retailCategoryId = retailCategoryId;
    this.defaultPrice = defaultPrice;
    this.policy = policy;
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
