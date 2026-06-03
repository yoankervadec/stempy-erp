package com.lesconstructionssapete.stempyerp.domain.retailproduct;

import com.lesconstructionssapete.stempyerp.domain.field.EntityField;
import com.lesconstructionssapete.stempyerp.domain.field.FieldMeta;

public class RetailProductMasterPolicy {

  public enum Fields implements EntityField {

    RETAIL_PRODUCT_MASTER_ID(FieldMeta.builder("RetailProductMasterPolicy", "dom_retail_product_master_policy")
        .field("retailProductMasterId", "retail_product_master_id")
        .type(Long.class, java.sql.Types.BIGINT)
        .notNullable()
        .build()),

    DISCONTINUED(FieldMeta.builder("RetailProductMasterPolicy", "dom_retail_product_master_policy")
        .field("discontinued", "discontinued")
        .type(Boolean.class, java.sql.Types.BOOLEAN)
        .notNullable()
        .build()),

    TRACK_INVENTORY(FieldMeta.builder("RetailProductMasterPolicy", "dom_retail_product_master_policy")
        .field("trackInventory", "track_inventory")
        .type(Boolean.class, java.sql.Types.BOOLEAN)
        .notNullable()
        .build()),

    ALLOW_NEGATIVE_INVENTORY(FieldMeta.builder("RetailProductMasterPolicy", "dom_retail_product_master_policy")
        .field("allowNegativeInventory", "allow_negative_inventory")
        .type(Boolean.class, java.sql.Types.BOOLEAN)
        .notNullable()
        .build()),

    APPLY_TAX(FieldMeta.builder("RetailProductMasterPolicy", "dom_retail_product_master_policy")
        .field("applyTax", "apply_tax")
        .type(Boolean.class, java.sql.Types.BOOLEAN)
        .notNullable()
        .build()),

    APPLY_PROMOTION(FieldMeta.builder("RetailProductMasterPolicy", "dom_retail_product_master_policy")
        .field("applyPromotion", "apply_promotion")
        .type(Boolean.class, java.sql.Types.BOOLEAN)
        .notNullable()
        .build());

    private final FieldMeta meta;

    Fields(FieldMeta meta) {
      this.meta = meta;
    }

    @Override
    public FieldMeta meta() {
      return meta;
    }
  }

  private Long retailProductMasterId;
  private boolean discontiued;
  private boolean trackInventory;
  private boolean allowNegativeInventory;
  private boolean applyTax;
  private boolean applyPromotion;

  public RetailProductMasterPolicy(
      Long retailProductMasterId,
      boolean discontiued,
      boolean trackInventory,
      boolean allowNegativeInventory,
      boolean applyTax,
      boolean applyPromotion) {
    this.retailProductMasterId = retailProductMasterId;
    this.discontiued = discontiued;
    this.trackInventory = trackInventory;
    this.allowNegativeInventory = allowNegativeInventory;
    this.applyTax = applyTax;
    this.applyPromotion = applyPromotion;
  }

  public Long getRetailProductMasterId() {
    return retailProductMasterId;
  }

  public void setRetailProductMasterId(Long retailProductMasterId) {
    this.retailProductMasterId = retailProductMasterId;
  }

  public boolean isDiscontiued() {
    return discontiued;
  }

  public void setDiscontiued(boolean discontiued) {
    this.discontiued = discontiued;
  }

  public boolean isTrackInventory() {
    return trackInventory;
  }

  public void setTrackInventory(boolean trackInventory) {
    this.trackInventory = trackInventory;
  }

  public boolean isAllowNegativeInventory() {
    return allowNegativeInventory;
  }

  public void setAllowNegativeInventory(boolean allowNegativeInventory) {
    this.allowNegativeInventory = allowNegativeInventory;
  }

  public boolean isApplyTax() {
    return applyTax;
  }

  public void setApplyTax(boolean applyTax) {
    this.applyTax = applyTax;
  }

  public boolean isApplyPromotion() {
    return applyPromotion;
  }

  public void setApplyPromotion(boolean applyPromotion) {
    this.applyPromotion = applyPromotion;
  }

}
