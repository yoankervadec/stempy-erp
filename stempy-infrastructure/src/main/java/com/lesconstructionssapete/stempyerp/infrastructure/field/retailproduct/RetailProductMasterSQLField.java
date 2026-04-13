package com.lesconstructionssapete.stempyerp.infrastructure.field.retailproduct;

import java.sql.Types;
import java.util.Map;

import com.lesconstructionssapete.stempyerp.domain.field.DomainField;
import com.lesconstructionssapete.stempyerp.domain.field.retailproduct.RetailProductMasterField;
import com.lesconstructionssapete.stempyerp.domain.field.retailproduct.RetailProductMasterPolicyField;
import com.lesconstructionssapete.stempyerp.infrastructure.field.SQLField;

public final class RetailProductMasterSQLField {

  private RetailProductMasterSQLField() {
  }

  private static final String DOM_RETAIL_PRODUCT_MASTER = "dom_retail_product_master";
  private static final String DOM_RETAIL_PRODUCT_MASTER_POLICY = "dom_retail_product_master_policy";

  private final static SQLField ID = new SQLField(
      RetailProductMasterField.ID,
      DOM_RETAIL_PRODUCT_MASTER,
      "id",
      Types.BIGINT);

  private final static SQLField RETAIL_PRODUCT_MASTER_NO = new SQLField(
      RetailProductMasterField.RETAIL_PRODUCT_MASTER_NO,
      DOM_RETAIL_PRODUCT_MASTER,
      "retail_product_master_no",
      Types.VARCHAR);

  private final static SQLField NAME = new SQLField(
      RetailProductMasterField.NAME,
      DOM_RETAIL_PRODUCT_MASTER,
      "name",
      Types.VARCHAR);

  private final static SQLField DESCRIPTION = new SQLField(
      RetailProductMasterField.DESCRIPTION,
      DOM_RETAIL_PRODUCT_MASTER,
      "description",
      Types.VARCHAR);

  private final static SQLField ENABLED = new SQLField(
      RetailProductMasterField.ENABLED,
      DOM_RETAIL_PRODUCT_MASTER,
      "enabled",
      Types.BOOLEAN);

  private final static SQLField CREATED_AT = new SQLField(
      RetailProductMasterField.CREATED_AT,
      DOM_RETAIL_PRODUCT_MASTER,
      "created_at",
      Types.TIMESTAMP);

  private final static SQLField CREATED_BY_USER_ID = new SQLField(
      RetailProductMasterField.CREATED_BY_USER_ID,
      DOM_RETAIL_PRODUCT_MASTER,
      "created_by_user_id",
      Types.BIGINT);

  private final static SQLField UPDATED_AT = new SQLField(
      RetailProductMasterField.UPDATED_AT,
      DOM_RETAIL_PRODUCT_MASTER,
      "updated_at",
      Types.TIMESTAMP);

  private final static SQLField UPDATED_BY_USER_ID = new SQLField(
      RetailProductMasterField.UPDATED_BY_USER_ID,
      DOM_RETAIL_PRODUCT_MASTER,
      "updated_by_user_id",
      Types.BIGINT);

  private final static SQLField RETAIL_CATEGORY_ID = new SQLField(
      RetailProductMasterField.RETAIL_CATEGORY_ID,
      DOM_RETAIL_PRODUCT_MASTER,
      "retail_category_id",
      Types.BIGINT);

  private final static SQLField DEFAULT_PRICE = new SQLField(
      RetailProductMasterField.DEFAULT_PRICE,
      DOM_RETAIL_PRODUCT_MASTER,
      "price",
      Types.DECIMAL);

  private final static SQLField DISCONTINUED = new SQLField(
      RetailProductMasterPolicyField.DISCONTINUED,
      DOM_RETAIL_PRODUCT_MASTER_POLICY,
      "discontinued",
      Types.BOOLEAN);

  private final static SQLField TRACK_INVENTORY = new SQLField(
      RetailProductMasterPolicyField.TRACK_INVENTORY,
      DOM_RETAIL_PRODUCT_MASTER_POLICY,
      "track_inventory",
      Types.BOOLEAN);

  private final static SQLField ALLOW_NEGATIVE_INVENTORY = new SQLField(
      RetailProductMasterPolicyField.ALLOW_NEGATIVE_INVENTORY,
      DOM_RETAIL_PRODUCT_MASTER_POLICY,
      "allow_negative_inventory",
      Types.BOOLEAN);

  private final static SQLField APPLY_TAX = new SQLField(
      RetailProductMasterPolicyField.APPLY_TAX,
      DOM_RETAIL_PRODUCT_MASTER_POLICY,
      "apply_tax",
      Types.BOOLEAN);

  private final static SQLField APPLY_PROMOTION = new SQLField(
      RetailProductMasterPolicyField.APPLY_PROMOTION,
      DOM_RETAIL_PRODUCT_MASTER_POLICY,
      "apply_promotion",
      Types.BOOLEAN);

  private static final Map<DomainField, SQLField> LOOKUP = Map.ofEntries(
      Map.entry(RetailProductMasterField.ID, ID),
      Map.entry(RetailProductMasterField.RETAIL_PRODUCT_MASTER_NO, RETAIL_PRODUCT_MASTER_NO),
      Map.entry(RetailProductMasterField.NAME, NAME),
      Map.entry(RetailProductMasterField.DESCRIPTION, DESCRIPTION),
      Map.entry(RetailProductMasterField.ENABLED, ENABLED),
      Map.entry(RetailProductMasterField.CREATED_AT, CREATED_AT),
      Map.entry(RetailProductMasterField.CREATED_BY_USER_ID, CREATED_BY_USER_ID),
      Map.entry(RetailProductMasterField.UPDATED_AT, UPDATED_AT),
      Map.entry(RetailProductMasterField.UPDATED_BY_USER_ID, UPDATED_BY_USER_ID),
      Map.entry(RetailProductMasterField.RETAIL_CATEGORY_ID, RETAIL_CATEGORY_ID),
      Map.entry(RetailProductMasterField.DEFAULT_PRICE, DEFAULT_PRICE),
      Map.entry(RetailProductMasterPolicyField.DISCONTINUED, DISCONTINUED),
      Map.entry(RetailProductMasterPolicyField.TRACK_INVENTORY, TRACK_INVENTORY),
      Map.entry(RetailProductMasterPolicyField.ALLOW_NEGATIVE_INVENTORY, ALLOW_NEGATIVE_INVENTORY),
      Map.entry(RetailProductMasterPolicyField.APPLY_TAX, APPLY_TAX),
      Map.entry(RetailProductMasterPolicyField.APPLY_PROMOTION, APPLY_PROMOTION));

  public static SQLField get(DomainField field) {
    return LOOKUP.get(field);
  }

  public static Map<DomainField, SQLField> all() {
    return LOOKUP;
  }

}
