package com.lesconstructionssapete.stempyerp.infrastructure.mapper.retailproduct;

import java.sql.ResultSet;
import java.util.function.BiConsumer;

import com.lesconstructionssapete.stempyerp.domain.field.EntityField;
import com.lesconstructionssapete.stempyerp.domain.retailproduct.RetailProductMaster;
import com.lesconstructionssapete.stempyerp.infrastructure.mapper.EntityMapper;

public final class RetailProductMasterMapper {

  private RetailProductMasterMapper() {
  }

  public static RetailProductMaster fromResultSet(ResultSet rs) {
    return new RetailProductMaster(
        EntityMapper.read(rs, RetailProductMaster.Fields.ID),
        EntityMapper.read(rs, RetailProductMaster.Fields.RETAIL_PRODUCT_MASTER_NO),
        EntityMapper.read(rs, RetailProductMaster.Fields.NAME),
        EntityMapper.read(rs, RetailProductMaster.Fields.DESCRIPTION),
        EntityMapper.read(rs, RetailProductMaster.Fields.ENABLED),
        EntityMapper.read(rs, RetailProductMaster.Fields.CREATED_AT),
        EntityMapper.read(rs, RetailProductMaster.Fields.CREATED_BY_USER_ID),
        EntityMapper.read(rs, RetailProductMaster.Fields.UPDATED_AT),
        EntityMapper.read(rs, RetailProductMaster.Fields.UPDATED_BY_USER_ID),
        EntityMapper.read(rs, RetailProductMaster.Fields.RETAIL_CATEGORY_ID),
        EntityMapper.read(rs, RetailProductMaster.Fields.DEFAULT_PRICE),
        null);
  }

  public static void bind(RetailProductMaster rpm, BiConsumer<EntityField, Object> binder) {
    binder.accept(RetailProductMaster.Fields.ID, rpm.getEntityId());
    binder.accept(RetailProductMaster.Fields.RETAIL_PRODUCT_MASTER_NO, rpm.getEntityNo());
    binder.accept(RetailProductMaster.Fields.NAME, rpm.getName());
    binder.accept(RetailProductMaster.Fields.DESCRIPTION, rpm.getDescription());
    binder.accept(RetailProductMaster.Fields.ENABLED, rpm.isEnabled());
    binder.accept(RetailProductMaster.Fields.CREATED_AT, rpm.getCreatedAt());
    binder.accept(RetailProductMaster.Fields.CREATED_BY_USER_ID, rpm.getCreatedByUserId());
    binder.accept(RetailProductMaster.Fields.UPDATED_AT, rpm.getUpdatedAt());
    binder.accept(RetailProductMaster.Fields.UPDATED_BY_USER_ID, rpm.getUpdatedByUserId());
    binder.accept(RetailProductMaster.Fields.RETAIL_CATEGORY_ID, rpm.getRetailCategoryId());
    binder.accept(RetailProductMaster.Fields.DEFAULT_PRICE, rpm.getDefaultPrice());
  }

}
