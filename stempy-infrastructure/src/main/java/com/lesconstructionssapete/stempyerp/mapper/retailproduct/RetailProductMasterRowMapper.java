package com.lesconstructionssapete.stempyerp.mapper.retailproduct;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.lesconstructionssapete.stempyerp.domain.retailproduct.RetailProductMaster;
import com.lesconstructionssapete.stempyerp.field.retailproduct.RetailProductMasterField;
import com.lesconstructionssapete.stempyerp.field.retailproduct.RetailProductMasterSQLField;
import com.lesconstructionssapete.stempyerp.mapper.SQLInstantMapper;

public class RetailProductMasterRowMapper {

  private RetailProductMasterRowMapper() {
  }

  public static RetailProductMaster map(ResultSet rs) throws SQLException {
    return new RetailProductMaster(
        rs.getLong(RetailProductMasterSQLField.get(RetailProductMasterField.ID).columnName()),
        rs.getString(RetailProductMasterSQLField.get(RetailProductMasterField.RETAIL_PRODUCT_MASTER_NO).columnName()),
        rs.getString(RetailProductMasterSQLField.get(RetailProductMasterField.NAME).columnName()),
        rs.getString(RetailProductMasterSQLField.get(RetailProductMasterField.DESCRIPTION).columnName()),
        rs.getBoolean(RetailProductMasterSQLField.get(RetailProductMasterField.ENABLED).columnName()),
        SQLInstantMapper.read(rs, RetailProductMasterSQLField.get(RetailProductMasterField.CREATED_AT).columnName()),
        rs.getLong(RetailProductMasterSQLField.get(RetailProductMasterField.CREATED_BY_USER_ID).columnName()),
        SQLInstantMapper.read(rs, RetailProductMasterSQLField.get(RetailProductMasterField.UPDATED_AT).columnName()),
        rs.getLong(RetailProductMasterSQLField.get(RetailProductMasterField.UPDATED_BY_USER_ID).columnName()),
        rs.getLong(RetailProductMasterSQLField.get(RetailProductMasterField.RETAIL_CATEGORY_ID).columnName()),
        rs.getBigDecimal(RetailProductMasterSQLField.get(RetailProductMasterField.DEFAULT_PRICE).columnName()),
        RetailProductMasterPolicyRowMapper.map(rs));
  }

}
