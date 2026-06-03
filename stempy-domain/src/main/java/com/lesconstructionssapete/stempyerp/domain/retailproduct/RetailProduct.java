package com.lesconstructionssapete.stempyerp.domain.retailproduct;

import java.time.Instant;

import com.lesconstructionssapete.stempyerp.domain.field.EntityField;
import com.lesconstructionssapete.stempyerp.domain.field.FieldMeta;
import com.lesconstructionssapete.stempyerp.domain.generic.GenericEntity;

public class RetailProduct extends GenericEntity {

  public enum Fields implements EntityField {

    ID(FieldMeta.builder("RetailProduct", "dom_retail_product_variant")
        .field("id", "id")
        .type(Long.class, java.sql.Types.BIGINT)
        .notNullable()
        .build()),

    RETAIL_PRODUCT_MASTER_ID(FieldMeta.builder("RetailProduct", "dom_retail_product_variant")
        .field("retailProductMasterId", "retail_product_master_id")
        .type(Long.class, java.sql.Types.BIGINT)
        .notNullable()
        .build()),

    RETAIL_PRODUCT_VARIANT_NO(FieldMeta.builder("RetailProduct", "dom_retail_product_variant")
        .field("retailProductVariantNo", "retail_product_variant_no")
        .type(String.class, java.sql.Types.VARCHAR)
        .notNullable()
        .build()),

    NAME(FieldMeta.builder("RetailProduct", "dom_retail_product_variant")
        .field("name", "name")
        .type(String.class, java.sql.Types.VARCHAR)
        .notNullable()
        .build()),

    DESCRIPTION(FieldMeta.builder("RetailProduct", "dom_retail_product_variant")
        .field("description", "description")
        .type(String.class, java.sql.Types.VARCHAR)
        .notNullable()
        .build()),

    ENABLED(FieldMeta.builder("RetailProduct", "dom_retail_product_variant")
        .field("enabled", "enabled")
        .type(Boolean.class, java.sql.Types.BOOLEAN)
        .notNullable()
        .build()),

    CREATED_AT(FieldMeta.builder("RetailProduct", "dom_retail_product_variant")
        .field("createdAt", "created_at")
        .type(Instant.class, java.sql.Types.TIMESTAMP)
        .notNullable()
        .build()),

    CREATED_BY_USER_ID(FieldMeta.builder("RetailProduct", "dom_retail_product_variant")
        .field("createdByUserId", "created_by_user_id")
        .type(Long.class, java.sql.Types.BIGINT)
        .notNullable()
        .build()),

    UPDATED_AT(FieldMeta.builder("RetailProduct", "dom_retail_product_variant")
        .field("updatedAt", "updated_at")
        .type(Instant.class, java.sql.Types.TIMESTAMP)
        .notNullable()
        .build()),

    UPDATED_BY_USER_ID(FieldMeta.builder("RetailProduct", "dom_retail_product_variant")
        .field("updatedByUserId", "updated_by_user_id")
        .type(Long.class, java.sql.Types.BIGINT)
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

  public static final String RETAIL_PRODUCT_ENTITY_NAME = "RETAIL PRODUCT";

  private Long retailProductMasterId;

  private String retailProductNo;
  private String retailProductVariantNo;

  private String name;
  private String description;

  private boolean enabled;

  private Instant updatedAt;
  private Long updatedByUserId;

  public RetailProduct(
      Long retailProductId,
      Long retailProductMasterId,
      String retailProductNo,
      String retailProductVariantNo,
      String name,
      String description,
      boolean enabled,
      Instant createdAt,
      Long createdByUserId,
      Instant updatedAt,
      Long updatedByUserId) {
    super(
        RETAIL_PRODUCT_ENTITY_NAME,
        retailProductNo,
        retailProductId,
        createdAt,
        createdByUserId);
    this.retailProductMasterId = retailProductMasterId;
    this.retailProductNo = retailProductNo;
    this.retailProductVariantNo = retailProductVariantNo;
    this.name = name;
    this.description = description;
    this.enabled = enabled;
    this.updatedAt = updatedAt;
    this.updatedByUserId = updatedByUserId;
  }

  public static String getRetailProductEntityName() {
    return RETAIL_PRODUCT_ENTITY_NAME;
  }

  public Long getRetailProductMasterId() {
    return retailProductMasterId;
  }

  public void setRetailProductMasterId(Long retailProductMasterId) {
    this.retailProductMasterId = retailProductMasterId;
  }

  public String getRetailProductNo() {
    return retailProductNo;
  }

  public void setRetailProductNo(String retailProductNo) {
    this.retailProductNo = retailProductNo;
  }

  public String getRetailProductVariantNo() {
    return retailProductVariantNo;
  }

  public void setRetailProductVariantNo(String retailProductVariantNo) {
    this.retailProductVariantNo = retailProductVariantNo;
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

}
