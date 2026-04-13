package com.lesconstructionssapete.stempyerp.domain.field.retailproduct;

import com.lesconstructionssapete.stempyerp.domain.field.DomainField;

public enum RetailProductMasterField implements DomainField {

  ID("id"),
  RETAIL_PRODUCT_MASTER_NO("retailProductMasterNo"),
  NAME("name"),
  DESCRIPTION("description"),
  ENABLED("enabled"),
  CREATED_AT("createdAt"),
  CREATED_BY_USER_ID("createdByUserId"),
  UPDATED_AT("updatedAt"),
  UPDATED_BY_USER_ID("updatedByUserId"),
  RETAIL_CATEGORY_ID("retailCategoryId"),
  DEFAULT_PRICE("defaultPrice");

  private static final String PREFIX = "RetailProductMaster.";
  private final String logicalName;

  RetailProductMasterField(String logicalName) {
    this.logicalName = logicalName;
  }

  @Override
  public String logicalName() {
    return PREFIX + logicalName;
  }

}
