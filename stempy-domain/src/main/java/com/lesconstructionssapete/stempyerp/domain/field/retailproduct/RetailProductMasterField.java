package com.lesconstructionssapete.stempyerp.domain.field.retailproduct;

import com.lesconstructionssapete.stempyerp.domain.field.DomainField;
import com.lesconstructionssapete.stempyerp.domain.field.DomainFieldProvider;

public enum RetailProductMasterField implements DomainFieldProvider {

  ID(new DomainField("RetailProductMaster", "id", Long.class, false, true, true, "id", "dom_retail_product_master",
      java.sql.Types.BIGINT)),
  RETAIL_PRODUCT_MASTER_NO(new DomainField("RetailProductMaster", "retailProductMasterNo", String.class, false,
      false, false, "retail_product_master_no", "dom_retail_product_master",
      java.sql.Types.VARCHAR)),
  NAME(new DomainField("RetailProductMaster", "name", String.class, false, false, false, "name",
      "dom_retail_product_master",
      java.sql.Types.VARCHAR)),
  DESCRIPTION(new DomainField("RetailProductMaster", "description", String.class, false, false, false, "description",
      "dom_retail_product_master",
      java.sql.Types.VARCHAR)),
  ENABLED(new DomainField("RetailProductMaster", "enabled", Boolean.class, false, false, false, "enabled",
      "dom_retail_product_master",
      java.sql.Types.BOOLEAN)),
  CREATED_AT(new DomainField("RetailProductMaster", "createdAt", java.sql.Timestamp.class, false, false, false,
      "created_at", "dom_retail_product_master",
      java.sql.Types.TIMESTAMP)),
  CREATED_BY_USER_ID(new DomainField("RetailProductMaster", "createdByUserId", Long.class, false, false, false,
      "created_by_user_id", "dom_retail_product_master",
      java.sql.Types.BIGINT)),
  UPDATED_AT(new DomainField("RetailProductMaster", "updatedAt", java.sql.Timestamp.class, false, false, false,
      "updated_at", "dom_retail_product_master",
      java.sql.Types.TIMESTAMP)),
  UPDATED_BY_USER_ID(new DomainField("RetailProductMaster", "updatedByUserId", Long.class, false, false, false,
      "updated_by_user_id", "dom_retail_product_master",
      java.sql.Types.BIGINT)),
  RETAIL_CATEGORY_ID(new DomainField("RetailProductMaster", "retailCategoryId", Long.class, false, false, false,
      "retail_category_id", "dom_retail_product_master",
      java.sql.Types.BIGINT)),
  DEFAULT_PRICE(new DomainField("RetailProductMaster", "defaultPrice", java.math.BigDecimal.class, false, false,
      false, "default_price", "dom_retail_product_master",
      java.sql.Types.DECIMAL));

  private final DomainField attribute;

  RetailProductMasterField(DomainField attribute) {
    this.attribute = attribute;
  }

  @Override
  public DomainField attribute() {
    return attribute;
  }

}
