package com.lesconstructionssapete.stempyerp.infrastructure.mapper.retailproduct;

import java.time.Instant;

import com.lesconstructionssapete.stempyerp.field.retailproduct.RetailProductField;
import com.lesconstructionssapete.stempyerp.infrastructure.field.retailproduct.RetailProductSQLField;
import com.lesconstructionssapete.stempyerp.infrastructure.query.SQLBuilder;
import com.lesconstructionssapete.stempyerp.retailproduct.RetailProduct;

public final class RetailProductSQLMapper {

  private RetailProductSQLMapper() {
  }

  public static void bindInsert(SQLBuilder builder, RetailProduct rp) {
    builder
        .bind(RetailProductSQLField.get(RetailProductField.RETAIL_PRODUCT_MASTER_ID), rp.getRetailProductMasterId())
        .bind(RetailProductSQLField.get(RetailProductField.RETAIL_PRODUCT_NO), rp.getRetailProductNo())
        .bind(RetailProductSQLField.get(RetailProductField.RETAIL_PRODUCT_VARIANT_NO), rp.getRetailProductVariantNo())
        .bind(RetailProductSQLField.get(RetailProductField.NAME), rp.getName())
        .bind(RetailProductSQLField.get(RetailProductField.DESCRIPTION), rp.getDescription())
        .bind(RetailProductSQLField.get(RetailProductField.ENABLED), rp.isEnabled())
        .bind(RetailProductSQLField.get(RetailProductField.CREATED_BY_USER_ID), rp.getCreatedByUserId())
        .bind(RetailProductSQLField.get(RetailProductField.CREATED_AT), rp.getCreatedAt())
        .bind(RetailProductSQLField.get(RetailProductField.UPDATED_BY_USER_ID), rp.getUpdatedByUserId())
        .bind(RetailProductSQLField.get(RetailProductField.UPDATED_AT), rp.getUpdatedAt());
  }

  public static void bindUpdate(SQLBuilder builder, RetailProduct rp) {
    builder
        .bind(RetailProductSQLField.get(RetailProductField.RETAIL_PRODUCT_MASTER_ID), rp.getRetailProductMasterId())
        .bind(RetailProductSQLField.get(RetailProductField.RETAIL_PRODUCT_NO), rp.getRetailProductNo())
        .bind(RetailProductSQLField.get(RetailProductField.RETAIL_PRODUCT_VARIANT_NO), rp.getRetailProductVariantNo())
        .bind(RetailProductSQLField.get(RetailProductField.NAME), rp.getName())
        .bind(RetailProductSQLField.get(RetailProductField.DESCRIPTION), rp.getDescription())
        .bind(RetailProductSQLField.get(RetailProductField.ENABLED), rp.isEnabled())
        .bind(RetailProductSQLField.get(RetailProductField.UPDATED_AT), Instant.now())
        .bind(RetailProductSQLField.get(RetailProductField.UPDATED_BY_USER_ID), rp.getUpdatedByUserId());
  }

}
