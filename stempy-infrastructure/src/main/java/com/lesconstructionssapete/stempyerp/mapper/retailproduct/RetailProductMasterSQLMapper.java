package com.lesconstructionssapete.stempyerp.mapper.retailproduct;

import com.lesconstructionssapete.stempyerp.domain.retailproduct.RetailProductMaster;
import com.lesconstructionssapete.stempyerp.field.retailproduct.RetailProductMasterField;
import com.lesconstructionssapete.stempyerp.query.SQLBuilder;

public final class RetailProductMasterSQLMapper {

  private RetailProductMasterSQLMapper() {
  }

  public static void bindInsert(SQLBuilder builder, RetailProductMaster p) {
    builder
        .bind(RetailProductMasterField.RETAIL_PRODUCT_MASTER_NO, p.getEntityNo())
        .bind(RetailProductMasterField.NAME, p.getName())
        .bind(RetailProductMasterField.DESCRIPTION, p.getDescription())
        .bind(RetailProductMasterField.ENABLED, p.isEnabled())
        .bind(RetailProductMasterField.CREATED_BY_USER_ID, p.getCreatedByUserId())
        .bind(RetailProductMasterField.CREATED_AT, p.getCreatedAt())
        .bind(RetailProductMasterField.UPDATED_BY_USER_ID, p.getUpdatedByUserId())
        .bind(RetailProductMasterField.UPDATED_AT, p.getUpdatedAt())
        .bind(RetailProductMasterField.RETAIL_CATEGORY_ID, p.getRetailCategoryId())
        .bind(RetailProductMasterField.DEFAULT_PRICE, p.getDefaultPrice());
  }

  public static void bindUpdate(SQLBuilder builder, RetailProductMaster p) {
    builder
        .bind(RetailProductMasterField.RETAIL_PRODUCT_MASTER_NO, p.getEntityNo())
        .bind(RetailProductMasterField.NAME, p.getName())
        .bind(RetailProductMasterField.DESCRIPTION, p.getDescription())
        .bind(RetailProductMasterField.ENABLED, p.isEnabled())
        .bind(RetailProductMasterField.UPDATED_BY_USER_ID, p.getUpdatedByUserId())
        .bind(RetailProductMasterField.UPDATED_AT, p.getUpdatedAt())
        .bind(RetailProductMasterField.RETAIL_CATEGORY_ID, p.getRetailCategoryId())
        .bind(RetailProductMasterField.DEFAULT_PRICE, p.getDefaultPrice());
  }

}
