package com.lesconstructionssapete.stempyerp.domain.field.retailproduct;

import com.lesconstructionssapete.stempyerp.domain.field.DomainField;
import com.lesconstructionssapete.stempyerp.domain.field.DomainFieldProvider;

public enum RetailProductMasterPolicyField implements DomainFieldProvider {

  RETAIL_PRODUCT_MASTER_ID(
      new DomainField("RetailProductMasterPolicy", "retailProductMasterId", Long.class, false, true, true,
          "retail_product_master_id", "dom_retail_product_master_policy",
          java.sql.Types.BIGINT)),
  DISCONTINUED(new DomainField("RetailProductMasterPolicy", "discontinued", Boolean.class, false, false, false,
      "discontinued", "dom_retail_product_master_policy",
      java.sql.Types.BOOLEAN)),
  TRACK_INVENTORY(new DomainField("RetailProductMasterPolicy", "trackInventory", Boolean.class, false, false, false,
      "track_inventory", "dom_retail_product_master_policy",
      java.sql.Types.BOOLEAN)),
  ALLOW_NEGATIVE_INVENTORY(new DomainField("RetailProductMasterPolicy", "allowNegativeInventory", Boolean.class, false,
      false, false, "allow_negative_inventory", "dom_retail_product_master_policy",
      java.sql.Types.BOOLEAN)),
  APPLY_TAX(new DomainField("RetailProductMasterPolicy", "applyTax", Boolean.class, false, false, false, "apply_tax",
      "dom_retail_product_master_policy",
      java.sql.Types.BOOLEAN)),
  APPLY_PROMOTION(new DomainField("RetailProductMasterPolicy", "applyPromotion", Boolean.class, false, false, false,
      "apply_promotion", "dom_retail_product_master_policy",
      java.sql.Types.BOOLEAN));

  private final DomainField attribute;

  RetailProductMasterPolicyField(DomainField attribute) {
    this.attribute = attribute;
  }

  @Override
  public DomainField attribute() {
    return attribute;
  }

}
