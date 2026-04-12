package com.lesconstructionssapete.stempyerp.infrastructure.field.retailproduct;

import java.sql.Types;
import java.util.Map;

import com.lesconstructionssapete.stempyerp.field.DomainField;
import com.lesconstructionssapete.stempyerp.field.retailproduct.RetailProductField;
import com.lesconstructionssapete.stempyerp.infrastructure.field.SQLField;

public final class RetailProductSQLField {

  private RetailProductSQLField() {
  }

  private static final String DOM_RETAIL_PRODUCT_VARIANT = "dom_retail_product_variant";

  private static final SQLField ID = new SQLField(
      RetailProductField.ID,
      DOM_RETAIL_PRODUCT_VARIANT,
      "id",
      Types.BIGINT);

  private static final SQLField RETAIL_PRODUCT_MASTER_ID = new SQLField(
      RetailProductField.RETAIL_PRODUCT_MASTER_ID,
      DOM_RETAIL_PRODUCT_VARIANT,
      "retail_product_master_id",
      Types.BIGINT);

  private static final SQLField RETAIL_PRODUCT_NO = new SQLField(
      RetailProductField.RETAIL_PRODUCT_NO,
      DOM_RETAIL_PRODUCT_VARIANT,
      "retail_product_no",
      Types.VARCHAR);

  private static final SQLField RETAIL_PRODUCT_VARIANT_NO = new SQLField(
      RetailProductField.RETAIL_PRODUCT_VARIANT_NO,
      DOM_RETAIL_PRODUCT_VARIANT,
      "retail_product_variant_no",
      Types.VARCHAR);

  private final static SQLField NAME = new SQLField(
      RetailProductField.NAME,
      DOM_RETAIL_PRODUCT_VARIANT,
      "name",
      Types.VARCHAR);

  private final static SQLField DESCRIPTION = new SQLField(
      RetailProductField.DESCRIPTION,
      DOM_RETAIL_PRODUCT_VARIANT,
      "description",
      Types.VARCHAR);

  private final static SQLField ENABLED = new SQLField(
      RetailProductField.ENABLED,
      DOM_RETAIL_PRODUCT_VARIANT,
      "enabled",
      Types.BOOLEAN);

  private final static SQLField CREATED_AT = new SQLField(
      RetailProductField.CREATED_AT,
      DOM_RETAIL_PRODUCT_VARIANT,
      "created_at",
      Types.TIMESTAMP);

  private final static SQLField CREATED_BY_USER_ID = new SQLField(
      RetailProductField.CREATED_BY_USER_ID,
      DOM_RETAIL_PRODUCT_VARIANT,
      "created_by_user_id",
      Types.BIGINT);

  private final static SQLField UPDATED_AT = new SQLField(
      RetailProductField.UPDATED_AT,
      DOM_RETAIL_PRODUCT_VARIANT,
      "updated_at",
      Types.TIMESTAMP);

  private final static SQLField UPDATED_BY_USER_ID = new SQLField(
      RetailProductField.UPDATED_BY_USER_ID,
      DOM_RETAIL_PRODUCT_VARIANT,
      "updated_by_user_id",
      Types.BIGINT);

  private static final Map<DomainField, SQLField> LOOKUP = Map.ofEntries(
      Map.entry(RetailProductField.ID, ID),
      Map.entry(RetailProductField.RETAIL_PRODUCT_MASTER_ID, RETAIL_PRODUCT_MASTER_ID),
      Map.entry(RetailProductField.RETAIL_PRODUCT_NO, RETAIL_PRODUCT_NO),
      Map.entry(RetailProductField.RETAIL_PRODUCT_VARIANT_NO, RETAIL_PRODUCT_VARIANT_NO),
      Map.entry(RetailProductField.NAME, NAME),
      Map.entry(RetailProductField.DESCRIPTION, DESCRIPTION),
      Map.entry(RetailProductField.ENABLED, ENABLED),
      Map.entry(RetailProductField.CREATED_AT, CREATED_AT),
      Map.entry(RetailProductField.CREATED_BY_USER_ID, CREATED_BY_USER_ID),
      Map.entry(RetailProductField.UPDATED_AT, UPDATED_AT),
      Map.entry(RetailProductField.UPDATED_BY_USER_ID, UPDATED_BY_USER_ID));

  public static SQLField get(DomainField field) {
    return LOOKUP.get(field);
  }

  public static Map<DomainField, SQLField> all() {
    return LOOKUP;
  }
}
