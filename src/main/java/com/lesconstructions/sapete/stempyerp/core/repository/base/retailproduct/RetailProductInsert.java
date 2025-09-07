package com.lesconstructions.sapete.stempyerp.core.repository.base.retailproduct;

import java.sql.Connection;
import java.sql.SQLException;

import com.lesconstructions.sapete.stempyerp.core.domain.base.retailproduct.RetailProduct;
import com.lesconstructions.sapete.stempyerp.core.shared.query.Query;
import com.lesconstructions.sapete.stempyerp.core.shared.query.QueryCache;

class RetailProductInsert {

  RetailProduct insertRetailProduct(Connection con, RetailProduct rp) throws SQLException {

    String sqlString = QueryCache.get(
        Query.INSERT_RETAIL_PRODUCT);

    try (var stmt = con.prepareStatement(sqlString)) {
      stmt.setString(1, rp.getProductNo());
      stmt.setLong(2, rp.getSequenceNo());
      stmt.setBigDecimal(3, rp.getRetailPrice());
      stmt.setBigDecimal(4, rp.getCost());
      stmt.setString(5, rp.getDescription());
      stmt.setInt(6, rp.getRetailCategoryId());
      stmt.setInt(7, rp.getWoodSpecyId());
      stmt.setDouble(8, rp.getProductWidth());
      stmt.setDouble(9, rp.getProductThickness());
      stmt.setDouble(10, rp.getProductLength());
      stmt.setObject(11, rp.getCreatedAt());
      stmt.setLong(12, rp.getCreatedByUserSeq());

      int rows = stmt.executeUpdate();
      if (rows == 0) {
        throw new SQLException("Insert failed, no rows affected.");
      }

      return rp;
    }
  }
}
