package com.lesconstructionssapete.stempyerp.domain.retailproduct;

import java.math.BigDecimal;
import java.time.Instant;

import com.lesconstructionssapete.stempyerp.domain.field.EntityField;
import com.lesconstructionssapete.stempyerp.domain.field.FieldMeta;
import com.lesconstructionssapete.stempyerp.domain.generic.GenericEntity;

public class RetailProductMaster extends GenericEntity {

  public enum Fields implements EntityField {

    ID(FieldMeta.builder("RetailProductMaster", "dom_retail_product_master")
        .field("id", "id")
        .type(Long.class, java.sql.Types.BIGINT)
        .notNullable()
        .build()),

    RETAIL_PRODUCT_MASTER_NO(FieldMeta.builder("RetailProductMaster", "dom_retail_product_master")
        .field("retailProductMasterNo", "retail_product_master_no")
        .type(String.class, java.sql.Types.VARCHAR)
        .notNullable()
        .build()),

    NAME(FieldMeta.builder("RetailProductMaster", "dom_retail_product_master")
        .field("name", "name")
        .type(String.class, java.sql.Types.VARCHAR)
        .notNullable()
        .build()),

    DESCRIPTION(FieldMeta.builder("RetailProductMaster", "dom_retail_product_master")
        .field("description", "description")
        .type(String.class, java.sql.Types.VARCHAR)
        .notNullable()
        .build()),

    ENABLED(FieldMeta.builder("RetailProductMaster", "dom_retail_product_master")
        .field("enabled", "enabled")
        .type(Boolean.class, java.sql.Types.BOOLEAN)
        .notNullable()
        .build()),

    CREATED_AT(FieldMeta.builder("RetailProductMaster", "dom_retail_product_master")
        .field("createdAt", "created_at")
        .type(Instant.class, java.sql.Types.TIMESTAMP)
        .notNullable()
        .build()),

    CREATED_BY_USER_ID(FieldMeta.builder("RetailProductMaster", "dom_retail_product_master")
        .field("createdByUserId", "created_by_user_id")
        .type(Long.class, java.sql.Types.BIGINT)
        .notNullable()
        .build()),

    UPDATED_AT(FieldMeta.builder("RetailProductMaster", "dom_retail_product_master")
        .field("updatedAt", "updated_at")
        .type(Instant.class, java.sql.Types.TIMESTAMP)
        .notNullable()
        .build()),

    UPDATED_BY_USER_ID(FieldMeta.builder("RetailProductMaster", "dom_retail_product_master")
        .field("updatedByUserId", "updated_by_user_id")
        .type(Long.class, java.sql.Types.BIGINT)
        .notNullable()
        .build()),

    RETAIL_CATEGORY_ID(FieldMeta.builder("RetailProductMaster", "dom_retail_product_master")
        .field("retailCategoryId", "retail_category_id")
        .type(Long.class, java.sql.Types.BIGINT)
        .notNullable()
        .build()),

    DEFAULT_PRICE(FieldMeta.builder("RetailProductMaster", "dom_retail_product_master")
        .field("defaultPrice", "default_price")
        .type(BigDecimal.class, java.sql.Types.DECIMAL)
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

  public static final String RETAIL_PRODUCT_MASTER_ENTITY_NAME = "RETAIL PRODUCT MASTER";

  private String name;
  private String description;

  private boolean enabled;

  private Instant updatedAt;
  private Long updatedByUserId;

  private Long retailCategoryId;

  private BigDecimal defaultPrice;

  private RetailProductMasterPolicy policy;

  public RetailProductMaster(

      Long retailProductMasterId,
      String retailProductMasterNo,
      String name,
      String description,
      boolean enabled,
      Instant createdAt,
      Long createdByUserId,
      Instant updatedAt,
      Long updatedByUserId,
      Long retailCategoryId,
      BigDecimal defaultPrice,
      RetailProductMasterPolicy policy) {
    super(
        RETAIL_PRODUCT_MASTER_ENTITY_NAME,
        retailProductMasterNo,
        retailProductMasterId,
        createdAt,
        createdByUserId);
    this.name = name;
    this.description = description;
    this.enabled = enabled;
    this.updatedAt = updatedAt;
    this.updatedByUserId = updatedByUserId;
    this.retailCategoryId = retailCategoryId;
    this.defaultPrice = defaultPrice;
    this.policy = policy;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public boolean isEnabled() {
    return enabled;
  }

  public void setEnabled(boolean enabled) {
    this.enabled = enabled;
  }

  public Instant getUpdatedAt() {
    return updatedAt;
  }

  public void setUpdatedAt(Instant updatedAt) {
    this.updatedAt = updatedAt;
  }

  public Long getUpdatedByUserId() {
    return updatedByUserId;
  }

  public void setUpdatedByUserId(Long updatedByUserId) {
    this.updatedByUserId = updatedByUserId;
  }

  public Long getRetailCategoryId() {
    return retailCategoryId;
  }

  public void setRetailCategoryId(Long retailCategoryId) {
    this.retailCategoryId = retailCategoryId;
  }

  public BigDecimal getDefaultPrice() {
    return defaultPrice;
  }

  public void setDefaultPrice(BigDecimal defaultPrice) {
    this.defaultPrice = defaultPrice;
  }

  public RetailProductMasterPolicy getPolicy() {
    return policy;
  }

  public void setPolicy(RetailProductMasterPolicy policy) {
    this.policy = policy;
  }

}
