package com.lesconstructionssapete.stempyerp.domain.field.retailproduct;

import com.lesconstructionssapete.stempyerp.domain.field.DomainField;

public enum RetailProductField implements DomainField {

  ID("id"),
  RETAIL_PRODUCT_MASTER_ID("retailProductMasterId"),
  RETAIL_PRODUCT_NO("retailProductNo"),
  RETAIL_PRODUCT_VARIANT_NO("retailProductVariantNo"),
  NAME("name"),
  DESCRIPTION("description"),
  ENABLED("enabled"),
  CREATED_AT("createdAt"),
  CREATED_BY_USER_ID("createdByUserId"),
  UPDATED_AT("updatedAt"),
  UPDATED_BY_USER_ID("updatedByUserId");

  private static final String PREFIX = "RetailProduct.";
  private final String logicalName;

  RetailProductField(String logicalName) {
    this.logicalName = logicalName;
  }

  @Override
  public String logicalName() {
    return PREFIX + logicalName;
  }

}
