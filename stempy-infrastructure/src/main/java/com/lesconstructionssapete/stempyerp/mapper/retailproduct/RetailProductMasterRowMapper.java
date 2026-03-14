package com.lesconstructionssapete.stempyerp.mapper.retailproduct;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.lesconstructionssapete.stempyerp.domain.retailproduct.RetailProductMaster;
import com.lesconstructionssapete.stempyerp.field.retailproduct.RetailProductMasterField;
import com.lesconstructionssapete.stempyerp.mapper.SQLInstantMapper;

public class RetailProductMasterRowMapper {

  private RetailProductMasterRowMapper() {
  }

  public static RetailProductMaster map(ResultSet rs) throws SQLException {
    return new RetailProductMaster(
        rs.getLong(RetailProductMasterField.ID.columnName()),
        rs.getString(RetailProductMasterField.RETAIL_PRODUCT_MASTER_NO.columnName()),
        rs.getString(RetailProductMasterField.NAME.columnName()),
        rs.getString(RetailProductMasterField.DESCRIPTION.columnName()),
        rs.getBoolean(RetailProductMasterField.ENABLED.columnName()),
        SQLInstantMapper.read(rs, RetailProductMasterField.CREATED_AT.columnName()),
        rs.getLong(RetailProductMasterField.CREATED_BY_USER_ID.columnName()),
        SQLInstantMapper.read(rs, RetailProductMasterField.UPDATED_AT.columnName()),
        rs.getLong(RetailProductMasterField.UPDATED_BY_USER_ID.columnName()),
        rs.getLong(RetailProductMasterField.RETAIL_CATEGORY_ID.columnName()),
        rs.getBigDecimal(RetailProductMasterField.DEFAULT_PRICE.columnName()),
        RetailProductMasterPolicyMapper.map(rs));
  }

}
