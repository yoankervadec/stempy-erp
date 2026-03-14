package com.lesconstructionssapete.stempyerp.mapper.retailproduct;

import java.time.Instant;

import com.lesconstructionssapete.stempyerp.domain.retailproduct.RetailProduct;
import com.lesconstructionssapete.stempyerp.field.retailproduct.RetailProductField;
import com.lesconstructionssapete.stempyerp.query.SQLBuilder;

public final class RetailProductSQLMapper {

  private RetailProductSQLMapper() {
  }

  public static void bindInsert(SQLBuilder builder, RetailProduct rp) {
    builder
        .bind(RetailProductField.RETAIL_PRODUCT_MASTER_ID, rp.getRetailProductMasterId())
        .bind(RetailProductField.RETAIL_PRODUCT_NO, rp.getRetailProductNo())
        .bind(RetailProductField.RETAIL_PRODUCT_VARIANT_NO, rp.getRetailProductVariantNo())
        .bind(RetailProductField.NAME, rp.getName())
        .bind(RetailProductField.DESCRIPTION, rp.getDescription())
        .bind(RetailProductField.ENABLED, rp.isEnabled())
        .bind(RetailProductField.CREATED_BY_USER_ID, rp.getCreatedByUserId())
        .bind(RetailProductField.CREATED_AT, rp.getCreatedAt())
        .bind(RetailProductField.UPDATED_BY_USER_ID, rp.getUpdatedByUserId())
        .bind(RetailProductField.UPDATED_AT, rp.getUpdatedAt());
  }

  public static void bindUpdate(SQLBuilder builder, RetailProduct rp) {
    builder
        .bind(RetailProductField.RETAIL_PRODUCT_MASTER_ID, rp.getRetailProductMasterId())
        .bind(RetailProductField.RETAIL_PRODUCT_NO, rp.getRetailProductNo())
        .bind(RetailProductField.RETAIL_PRODUCT_VARIANT_NO, rp.getRetailProductVariantNo())
        .bind(RetailProductField.NAME, rp.getName())
        .bind(RetailProductField.DESCRIPTION, rp.getDescription())
        .bind(RetailProductField.ENABLED, rp.isEnabled())
        .bind(RetailProductField.UPDATED_AT, Instant.now())
        .bind(RetailProductField.UPDATED_BY_USER_ID, rp.getUpdatedByUserId());
  }

}
