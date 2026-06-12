package com.lesconstructionssapete.stempyerp.infrastructure.mapper.retailproduct;

import java.sql.ResultSet;
import java.util.function.BiConsumer;

import com.lesconstructionssapete.stempyerp.domain.field.EntityField;
import com.lesconstructionssapete.stempyerp.domain.retailproduct.RetailProductVariant;
import com.lesconstructionssapete.stempyerp.infrastructure.mapper.EntityMapper;

public final class RetailProductMapper {

  private RetailProductMapper() {
  }

  public static RetailProductVariant fromResultSet(ResultSet rs) {
    return new RetailProductVariant(
        EntityMapper.read(rs, RetailProductVariant.Fields.ID),
        EntityMapper.read(rs, RetailProductVariant.Fields.RETAIL_PRODUCT_MASTER_ID),
        EntityMapper.read(rs, RetailProductVariant.Fields.RETAIL_PRODUCT_NO),
        EntityMapper.read(rs, RetailProductVariant.Fields.RETAIL_PRODUCT_VARIANT_NO),
        EntityMapper.read(rs, RetailProductVariant.Fields.NAME),
        EntityMapper.read(rs, RetailProductVariant.Fields.DESCRIPTION),
        EntityMapper.read(rs, RetailProductVariant.Fields.ENABLED),
        EntityMapper.read(rs, RetailProductVariant.Fields.CREATED_AT),
        EntityMapper.read(rs, RetailProductVariant.Fields.CREATED_BY_USER_ID),
        EntityMapper.read(rs, RetailProductVariant.Fields.UPDATED_AT),
        EntityMapper.read(rs, RetailProductVariant.Fields.UPDATED_BY_USER_ID));
  }

  public static void bind(RetailProductVariant rp, BiConsumer<EntityField, Object> binder) {
    binder.accept(RetailProductVariant.Fields.ID, rp.getEntityId());
    binder.accept(RetailProductVariant.Fields.RETAIL_PRODUCT_MASTER_ID, rp.getRetailProductMasterId());
    binder.accept(RetailProductVariant.Fields.RETAIL_PRODUCT_NO, rp.getRetailProductNo());
    binder.accept(RetailProductVariant.Fields.RETAIL_PRODUCT_VARIANT_NO, rp.getRetailProductVariantNo());
    binder.accept(RetailProductVariant.Fields.NAME, rp.getName());
    binder.accept(RetailProductVariant.Fields.DESCRIPTION, rp.getDescription());
    binder.accept(RetailProductVariant.Fields.ENABLED, rp.isEnabled());
    binder.accept(RetailProductVariant.Fields.CREATED_AT, rp.getCreatedAt());
    binder.accept(RetailProductVariant.Fields.CREATED_BY_USER_ID, rp.getCreatedByUserId());
    binder.accept(RetailProductVariant.Fields.UPDATED_AT, rp.getUpdatedAt());
    binder.accept(RetailProductVariant.Fields.UPDATED_BY_USER_ID, rp.getUpdatedByUserId());
  }

}
