package com.lesconstructionssapete.stempyerp.infrastructure.mapper.retailproduct;

import com.lesconstructionssapete.stempyerp.domain.field.retailproduct.RetailProductMasterField;
import com.lesconstructionssapete.stempyerp.domain.retailproduct.RetailProductMaster;
import com.lesconstructionssapete.stempyerp.infrastructure.field.retailproduct.RetailProductMasterSQLField;
import com.lesconstructionssapete.stempyerp.infrastructure.query.SQLBuilder;

public final class RetailProductMasterSQLMapper {

  private RetailProductMasterSQLMapper() {
  }

  public static void bindInsert(SQLBuilder builder, RetailProductMaster p) {
    builder
        .bind(RetailProductMasterSQLField.get(RetailProductMasterField.RETAIL_PRODUCT_MASTER_NO), p.getEntityNo())
        .bind(RetailProductMasterSQLField.get(RetailProductMasterField.NAME), p.getName())
        .bind(RetailProductMasterSQLField.get(RetailProductMasterField.DESCRIPTION), p.getDescription())
        .bind(RetailProductMasterSQLField.get(RetailProductMasterField.ENABLED), p.isEnabled())
        .bind(RetailProductMasterSQLField.get(RetailProductMasterField.CREATED_BY_USER_ID), p.getCreatedByUserId())
        .bind(RetailProductMasterSQLField.get(RetailProductMasterField.CREATED_AT), p.getCreatedAt())
        .bind(RetailProductMasterSQLField.get(RetailProductMasterField.UPDATED_BY_USER_ID), p.getUpdatedByUserId())
        .bind(RetailProductMasterSQLField.get(RetailProductMasterField.UPDATED_AT), p.getUpdatedAt())
        .bind(RetailProductMasterSQLField.get(RetailProductMasterField.RETAIL_CATEGORY_ID), p.getRetailCategoryId())
        .bind(RetailProductMasterSQLField.get(RetailProductMasterField.DEFAULT_PRICE), p.getDefaultPrice());
  }

  public static void bindUpdate(SQLBuilder builder, RetailProductMaster p) {
    builder
        .bind(RetailProductMasterSQLField.get(RetailProductMasterField.RETAIL_PRODUCT_MASTER_NO), p.getEntityNo())
        .bind(RetailProductMasterSQLField.get(RetailProductMasterField.NAME), p.getName())
        .bind(RetailProductMasterSQLField.get(RetailProductMasterField.DESCRIPTION), p.getDescription())
        .bind(RetailProductMasterSQLField.get(RetailProductMasterField.ENABLED), p.isEnabled())
        .bind(RetailProductMasterSQLField.get(RetailProductMasterField.UPDATED_BY_USER_ID), p.getUpdatedByUserId())
        .bind(RetailProductMasterSQLField.get(RetailProductMasterField.UPDATED_AT), p.getUpdatedAt())
        .bind(RetailProductMasterSQLField.get(RetailProductMasterField.RETAIL_CATEGORY_ID), p.getRetailCategoryId())
        .bind(RetailProductMasterSQLField.get(RetailProductMasterField.DEFAULT_PRICE), p.getDefaultPrice());
  }

}
