package com.lesconstructionssapete.stempyerp.mapper.retailproduct;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.lesconstructionssapete.stempyerp.domain.retailproduct.RetailProduct;

public final class RetailProductRowMapper {

  private RetailProductRowMapper() {
  }

  public static RetailProduct map(ResultSet rs) throws SQLException {
    return new RetailProduct(
        null,
        null,
        null,
        null,
        null,
        null,
        false,
        null,
        null,
        null,
        null);
  }

}
