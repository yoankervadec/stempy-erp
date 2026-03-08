package com.lesconstructionssapete.stempyerp.field.retailproduct;

import java.sql.Types;
import java.util.Map;

import com.lesconstructionssapete.stempyerp.mapper.SQLField;

public final class RetailProductMasterField {

  private RetailProductMasterField() {
  }

  private static final String DOM_RETAIL_PRODUCT_MASTER = "dom_retail_product_master";
  private static final String DOM_RETAIL_PRODUCT_MASTER_POLICY = "dom_retail_product_master_policy";

  public final static SQLField ID = new SQLField(
      "retailProductMasterId",
      DOM_RETAIL_PRODUCT_MASTER,
      "id",
      Types.BIGINT);

  public final static SQLField RETAIL_PRODUCT_MASTER_NO = new SQLField(
      "retailProductMasterNo",
      DOM_RETAIL_PRODUCT_MASTER,
      "retail_product_master_no",
      Types.VARCHAR);

  public final static SQLField NAME = new SQLField(
      "name",
      DOM_RETAIL_PRODUCT_MASTER,
      "name",
      Types.VARCHAR);

  public final static SQLField DESCRIPTION = new SQLField(
      "description",
      DOM_RETAIL_PRODUCT_MASTER,
      "description",
      Types.VARCHAR);

  public final static SQLField ENABLED = new SQLField(
      "enabled",
      DOM_RETAIL_PRODUCT_MASTER,
      "enabled",
      Types.BOOLEAN);

  public final static SQLField CREATED_AT = new SQLField(
      "createdAt",
      DOM_RETAIL_PRODUCT_MASTER,
      "created_at",
      Types.TIMESTAMP);

  public final static SQLField CREATED_BY_USER_ID = new SQLField(
      "createdByUserId",
      DOM_RETAIL_PRODUCT_MASTER,
      "created_by_user_id",
      Types.BIGINT);

  public final static SQLField UPDATED_AT = new SQLField(
      "updatedAt",
      DOM_RETAIL_PRODUCT_MASTER,
      "updated_at",
      Types.TIMESTAMP);

  public final static SQLField UPDATED_BY_USER_ID = new SQLField(
      "updatedByUserId",
      DOM_RETAIL_PRODUCT_MASTER,
      "updated_by_user_id",
      Types.BIGINT);

  public final static SQLField RETAIL_CATEGORY_ID = new SQLField(
      "retailCategoryId",
      DOM_RETAIL_PRODUCT_MASTER,
      "retail_category_id",
      Types.BIGINT);

  public final static SQLField DEFAULT_PRICE = new SQLField(
      "defaultPrice",
      DOM_RETAIL_PRODUCT_MASTER,
      "price",
      Types.DECIMAL);

  public final static SQLField DISCONTINUED = new SQLField(
      "discontinued",
      DOM_RETAIL_PRODUCT_MASTER_POLICY,
      "discontinued",
      Types.BOOLEAN);

  public final static SQLField TRACK_INVENTORY = new SQLField(
      "trackInventory",
      DOM_RETAIL_PRODUCT_MASTER_POLICY,
      "track_inventory",
      Types.BOOLEAN);

  public final static SQLField ALLOW_NEGATIVE_INVENTORY = new SQLField(
      "allowNegativeInventory",
      DOM_RETAIL_PRODUCT_MASTER_POLICY,
      "allow_negative_inventory",
      Types.BOOLEAN);

  public final static SQLField APPLY_TAX = new SQLField(
      "applyTax",
      DOM_RETAIL_PRODUCT_MASTER_POLICY,
      "apply_tax",
      Types.BOOLEAN);

  public final static SQLField APPLY_PROMOTION = new SQLField(
      "applyPromotion",
      DOM_RETAIL_PRODUCT_MASTER_POLICY,
      "apply_promotion",
      Types.BOOLEAN);

  private static final Map<String, SQLField> LOOKUP = Map.ofEntries(
      Map.entry(ID.logicalName(), ID),
      Map.entry(RETAIL_PRODUCT_MASTER_NO.logicalName(), RETAIL_PRODUCT_MASTER_NO),
      Map.entry(NAME.logicalName(), NAME),
      Map.entry(DESCRIPTION.logicalName(), DESCRIPTION),
      Map.entry(ENABLED.logicalName(), ENABLED),
      Map.entry(CREATED_AT.logicalName(), CREATED_AT),
      Map.entry(CREATED_BY_USER_ID.logicalName(), CREATED_BY_USER_ID),
      Map.entry(UPDATED_AT.logicalName(), UPDATED_AT),
      Map.entry(UPDATED_BY_USER_ID.logicalName(), UPDATED_BY_USER_ID),
      Map.entry(RETAIL_CATEGORY_ID.logicalName(), RETAIL_CATEGORY_ID),
      Map.entry(DEFAULT_PRICE.logicalName(), DEFAULT_PRICE),
      Map.entry(DISCONTINUED.logicalName(), DISCONTINUED),
      Map.entry(TRACK_INVENTORY.logicalName(), TRACK_INVENTORY),
      Map.entry(ALLOW_NEGATIVE_INVENTORY.logicalName(), ALLOW_NEGATIVE_INVENTORY),
      Map.entry(APPLY_TAX.logicalName(), APPLY_TAX),
      Map.entry(APPLY_PROMOTION.logicalName(), APPLY_PROMOTION));

  public static SQLField get(String logicalName) {
    return LOOKUP.get(logicalName);
  }

  public static Map<String, SQLField> all() {
    return LOOKUP;
  }

}
