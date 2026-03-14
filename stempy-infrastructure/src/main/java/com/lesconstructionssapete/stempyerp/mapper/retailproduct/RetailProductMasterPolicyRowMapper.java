package com.lesconstructionssapete.stempyerp.mapper.retailproduct;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.lesconstructionssapete.stempyerp.domain.retailproduct.RetailProductMasterPolicy;
import com.lesconstructionssapete.stempyerp.field.retailproduct.RetailProductMasterField;

public class RetailProductMasterPolicyRowMapper {

  private RetailProductMasterPolicyRowMapper() {
  }

  public static RetailProductMasterPolicy map(ResultSet rs) throws SQLException {
    return new RetailProductMasterPolicy(
        rs.getLong(RetailProductMasterField.ID.columnName()),
        rs.getBoolean(RetailProductMasterField.DISCONTINUED.columnName()),
        rs.getBoolean(RetailProductMasterField.TRACK_INVENTORY.columnName()),
        rs.getBoolean(RetailProductMasterField.ALLOW_NEGATIVE_INVENTORY.columnName()),
        rs.getBoolean(RetailProductMasterField.APPLY_TAX.columnName()),
        rs.getBoolean(RetailProductMasterField.APPLY_PROMOTION.columnName()));
  }

}
