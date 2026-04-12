package com.lesconstructionssapete.stempyerp.infrastructure.mapper.retailproduct;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.lesconstructionssapete.stempyerp.field.retailproduct.RetailProductField;
import com.lesconstructionssapete.stempyerp.infrastructure.field.retailproduct.RetailProductSQLField;
import com.lesconstructionssapete.stempyerp.infrastructure.mapper.SQLInstantMapper;
import com.lesconstructionssapete.stempyerp.retailproduct.RetailProduct;

public final class RetailProductRowMapper {

  private RetailProductRowMapper() {
  }

  public static RetailProduct map(ResultSet rs) throws SQLException {
    return new RetailProduct(
        rs.getLong(RetailProductSQLField.get(RetailProductField.ID).columnName()),
        rs.getLong(RetailProductSQLField.get(RetailProductField.RETAIL_PRODUCT_MASTER_ID).columnName()),
        rs.getString(RetailProductSQLField.get(RetailProductField.RETAIL_PRODUCT_NO).columnName()),
        rs.getString(RetailProductSQLField.get(RetailProductField.RETAIL_PRODUCT_VARIANT_NO).columnName()),
        rs.getString(RetailProductSQLField.get(RetailProductField.NAME).columnName()),
        rs.getString(RetailProductSQLField.get(RetailProductField.DESCRIPTION).columnName()),
        rs.getBoolean(RetailProductSQLField.get(RetailProductField.ENABLED).columnName()),
        SQLInstantMapper.read(rs, RetailProductSQLField.get(RetailProductField.CREATED_AT).columnName()),
        rs.getLong(RetailProductSQLField.get(RetailProductField.CREATED_BY_USER_ID).columnName()),
        SQLInstantMapper.read(rs, RetailProductSQLField.get(RetailProductField.UPDATED_AT).columnName()),
        rs.getLong(RetailProductSQLField.get(RetailProductField.UPDATED_BY_USER_ID).columnName()));
  }

}
