package com.lesconstructionssapete.stempyerp.infrastructure.mapper.retailproduct;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.lesconstructionssapete.stempyerp.domain.retailproduct.RetailProductVariant;
import com.lesconstructionssapete.stempyerp.infrastructure.mapper.EntityMapper;
import com.lesconstructionssapete.stempyerp.infrastructure.query.SQLBuilder;

public final class RetailProductMapper {

  private RetailProductMapper() {
  }

  public static RetailProductVariant fromResultSet(ResultSet rs) throws SQLException {
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

  public static void bind(SQLBuilder builder, RetailProductVariant retailProduct) {

    builder.bind(RetailProductVariant.Fields.RETAIL_PRODUCT_MASTER_ID, retailProduct.getRetailProductMasterId());
    builder.bind(RetailProductVariant.Fields.RETAIL_PRODUCT_NO, retailProduct.getRetailProductNo());
    builder.bind(RetailProductVariant.Fields.RETAIL_PRODUCT_VARIANT_NO, retailProduct.getRetailProductVariantNo());
    builder.bind(RetailProductVariant.Fields.NAME, retailProduct.getName());
    builder.bind(RetailProductVariant.Fields.DESCRIPTION, retailProduct.getDescription());
    builder.bind(RetailProductVariant.Fields.ENABLED, retailProduct.isEnabled());
  }

}
