package com.lesconstructionssapete.stempyerp.domain.field.retailproduct;

import com.lesconstructionssapete.stempyerp.domain.field.DomainField;

public enum RetailProductMasterPolicyField implements DomainField {

  RETAIL_PRODUCT_MASTER_ID("retailProductMasterId"),
  DISCONTINUED("discontinued"),
  TRACK_INVENTORY("trackInventory"),
  ALLOW_NEGATIVE_INVENTORY("allowNegativeInventory"),
  APPLY_TAX("applyTax"),
  APPLY_PROMOTION("applyPromotion");

  private static final String PREFIX = "RetailProductMasterPolicy.";
  private final String logicalName;

  RetailProductMasterPolicyField(String logicalName) {
    this.logicalName = logicalName;
  }

  @Override
  public String logicalName() {
    return PREFIX + logicalName;
  }

}
