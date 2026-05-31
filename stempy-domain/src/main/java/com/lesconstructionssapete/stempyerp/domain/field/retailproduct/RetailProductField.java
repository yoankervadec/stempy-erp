package com.lesconstructionssapete.stempyerp.domain.field.retailproduct;

import com.lesconstructionssapete.stempyerp.domain.field.DomainField;
import com.lesconstructionssapete.stempyerp.domain.field.DomainFieldProvider;

public enum RetailProductField implements DomainFieldProvider {

  ID(new DomainField("RetailProduct", "id", Long.class, false, true, true, "id", "dom_retail_product_variant",
      java.sql.Types.BIGINT)),
  RETAIL_PRODUCT_MASTER_ID(new DomainField("RetailProduct", "retailProductMasterId", Long.class, false, false, false,
      "retail_product_master_id",
      "dom_retail_product_variant",
      java.sql.Types.BIGINT)),
  RETAIL_PRODUCT_NO(new DomainField("RetailProduct", "retailProductNo", String.class, false, false, false,
      "retail_product_no", "dom_retail_product_variant",
      java.sql.Types.VARCHAR)),
  RETAIL_PRODUCT_VARIANT_NO(new DomainField("RetailProduct", "retailProductVariantNo", String.class, false, false,
      false, "retail_product_variant_no", "dom_retail_product_variant",
      java.sql.Types.VARCHAR)),
  NAME(new DomainField("RetailProduct", "name", String.class, false, false, false, "name", "dom_retail_product_variant",
      java.sql.Types.VARCHAR)),
  DESCRIPTION(new DomainField("RetailProduct", "description", String.class, false, false, false, "description",
      "dom_retail_product_variant",
      java.sql.Types.VARCHAR)),
  ENABLED(new DomainField("RetailProduct", "enabled", Boolean.class, false, false, false, "enabled",
      "dom_retail_product_variant",
      java.sql.Types.BOOLEAN)),
  CREATED_AT(new DomainField("RetailProduct", "createdAt", java.sql.Timestamp.class, false, false, false, "created_at",
      "dom_retail_product_variant",
      java.sql.Types.TIMESTAMP)),
  CREATED_BY_USER_ID(new DomainField("RetailProduct", "createdByUserId", Long.class, false, false, false,
      "created_by_user_id", "dom_retail_product_variant",
      java.sql.Types.BIGINT)),
  UPDATED_AT(new DomainField("RetailProduct", "updatedAt", java.sql.Timestamp.class, false, false, false, "updated_at",
      "dom_retail_product_variant",
      java.sql.Types.TIMESTAMP)),
  UPDATED_BY_USER_ID(new DomainField("RetailProduct", "updatedByUserId", Long.class, false, false, false,
      "updated_by_user_id", "dom_retail_product_variant",
      java.sql.Types.BIGINT));

  private final DomainField attribute;

  RetailProductField(DomainField attribute) {
    this.attribute = attribute;
  }

  @Override
  public DomainField attribute() {
    return attribute;
  }

}
