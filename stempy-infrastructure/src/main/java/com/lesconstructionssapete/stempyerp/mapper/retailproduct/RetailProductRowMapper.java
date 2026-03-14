package com.lesconstructionssapete.stempyerp.mapper.retailproduct;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.lesconstructionssapete.stempyerp.domain.retailproduct.RetailProduct;
import com.lesconstructionssapete.stempyerp.field.retailproduct.RetailProductField;
import com.lesconstructionssapete.stempyerp.mapper.SQLInstantMapper;

public final class RetailProductRowMapper {

  private RetailProductRowMapper() {
  }

  public static RetailProduct map(ResultSet rs) throws SQLException {
    return new RetailProduct(
        rs.getLong(RetailProductField.ID.columnName()),
        rs.getLong(RetailProductField.RETAIL_PRODUCT_MASTER_ID.columnName()),
        rs.getString(RetailProductField.RETAIL_PRODUCT_NO.columnName()),
        rs.getString(RetailProductField.RETAIL_PRODUCT_VARIANT_NO.columnName()),
        rs.getString(RetailProductField.NAME.columnName()),
        rs.getString(RetailProductField.DESCRIPTION.columnName()),
        rs.getBoolean(RetailProductField.ENABLED.columnName()),
        SQLInstantMapper.read(rs, RetailProductField.CREATED_AT.columnName()),
        rs.getLong(RetailProductField.CREATED_BY_USER_ID.columnName()),
        SQLInstantMapper.read(rs, RetailProductField.UPDATED_AT.columnName()),
        rs.getLong(RetailProductField.UPDATED_BY_USER_ID.columnName()));
  }

}
