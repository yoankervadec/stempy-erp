package com.lesconstructionssapete.stempyerp.field.retailproduct;

import java.sql.Types;
import java.util.Map;

import com.lesconstructionssapete.stempyerp.mapper.SQLField;

public final class RetailProductField {

  private RetailProductField() {
  }

  private static final String DOM_RETAIL_PRODUCT_VARIANT = "dom_retail_product_variant";

  public static final SQLField ID = new SQLField(
      "id",
      DOM_RETAIL_PRODUCT_VARIANT,
      "id",
      Types.BIGINT);

  public static final SQLField RETAIL_PRODUCT_MASTER_ID = new SQLField(
      "retailProductMasterId",
      DOM_RETAIL_PRODUCT_VARIANT,
      "retail_product_master_id",
      Types.BIGINT);

  public static final SQLField RETAIL_PRODUCT_NO = new SQLField(
      "retailProductNo",
      DOM_RETAIL_PRODUCT_VARIANT,
      "retail_product_no",
      Types.VARCHAR);

  public static final SQLField RETAIL_PRODUCT_VARIANT_NO = new SQLField(
      "retailProductVariantNo",
      DOM_RETAIL_PRODUCT_VARIANT,
      "retail_product_variant_no",
      Types.VARCHAR);

  public final static SQLField NAME = new SQLField(
      "name",
      DOM_RETAIL_PRODUCT_VARIANT,
      "name",
      Types.VARCHAR);

  public final static SQLField DESCRIPTION = new SQLField(
      "description",
      DOM_RETAIL_PRODUCT_VARIANT,
      "description",
      Types.VARCHAR);

  public final static SQLField ENABLED = new SQLField(
      "enabled",
      DOM_RETAIL_PRODUCT_VARIANT,
      "enabled",
      Types.BOOLEAN);

  public final static SQLField CREATED_AT = new SQLField(
      "createdAt",
      DOM_RETAIL_PRODUCT_VARIANT,
      "created_at",
      Types.TIMESTAMP);

  public final static SQLField CREATED_BY_USER_ID = new SQLField(
      "createdByUserId",
      DOM_RETAIL_PRODUCT_VARIANT,
      "created_by_user_id",
      Types.BIGINT);

  public final static SQLField UPDATED_AT = new SQLField(
      "updatedAt",
      DOM_RETAIL_PRODUCT_VARIANT,
      "updated_at",
      Types.TIMESTAMP);

  public final static SQLField UPDATED_BY_USER_ID = new SQLField(
      "updatedByUserId",
      DOM_RETAIL_PRODUCT_VARIANT,
      "updated_by_user_id",
      Types.BIGINT);

  private static final Map<String, SQLField> LOOKUP = Map.ofEntries(
      Map.entry(ID.logicalName(), ID),
      Map.entry(RETAIL_PRODUCT_MASTER_ID.logicalName(), RETAIL_PRODUCT_MASTER_ID),
      Map.entry(RETAIL_PRODUCT_NO.logicalName(), RETAIL_PRODUCT_NO),
      Map.entry(RETAIL_PRODUCT_VARIANT_NO.logicalName(), RETAIL_PRODUCT_VARIANT_NO),
      Map.entry(NAME.logicalName(), NAME),
      Map.entry(DESCRIPTION.logicalName(), DESCRIPTION),
      Map.entry(ENABLED.logicalName(), ENABLED),
      Map.entry(CREATED_AT.logicalName(), CREATED_AT),
      Map.entry(CREATED_BY_USER_ID.logicalName(), CREATED_BY_USER_ID),
      Map.entry(UPDATED_AT.logicalName(), UPDATED_AT),
      Map.entry(UPDATED_BY_USER_ID.logicalName(), UPDATED_BY_USER_ID));

  public static SQLField get(String logicalName) {
    return LOOKUP.get(logicalName);
  }

  public static Map<String, SQLField> all() {
    return LOOKUP;
  }
}
