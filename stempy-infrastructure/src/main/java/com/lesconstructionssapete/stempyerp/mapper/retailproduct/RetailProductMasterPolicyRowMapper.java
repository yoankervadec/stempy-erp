package com.lesconstructionssapete.stempyerp.mapper.retailproduct;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.lesconstructionssapete.stempyerp.domain.retailproduct.RetailProductMasterPolicy;
import com.lesconstructionssapete.stempyerp.field.retailproduct.RetailProductMasterPolicyField;
import com.lesconstructionssapete.stempyerp.field.retailproduct.RetailProductMasterSQLField;

public class RetailProductMasterPolicyRowMapper {

  private RetailProductMasterPolicyRowMapper() {
  }

  public static RetailProductMasterPolicy map(ResultSet rs) throws SQLException {
    return new RetailProductMasterPolicy(
        rs.getLong(
            RetailProductMasterSQLField.get(RetailProductMasterPolicyField.RETAIL_PRODUCT_MASTER_ID).columnName()),
        rs.getBoolean(RetailProductMasterSQLField.get(RetailProductMasterPolicyField.DISCONTINUED).columnName()),
        rs.getBoolean(RetailProductMasterSQLField.get(RetailProductMasterPolicyField.TRACK_INVENTORY).columnName()),
        rs.getBoolean(
            RetailProductMasterSQLField.get(RetailProductMasterPolicyField.ALLOW_NEGATIVE_INVENTORY).columnName()),
        rs.getBoolean(RetailProductMasterSQLField.get(RetailProductMasterPolicyField.APPLY_TAX).columnName()),
        rs.getBoolean(RetailProductMasterSQLField.get(RetailProductMasterPolicyField.APPLY_PROMOTION).columnName()));
  }

}
