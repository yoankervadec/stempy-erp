package com.lesconstructions.sapete.stempyerp.core.repository.base.retailproduct;

import java.sql.Connection;
import java.sql.SQLException;

import com.lesconstructions.sapete.stempyerp.core.domain.base.retailproduct.RetailProduct;
import com.lesconstructions.sapete.stempyerp.core.shared.query.Query;
import com.lesconstructions.sapete.stempyerp.core.shared.query.QueryCache;

class RetailProductInsert {

  RetailProduct insertRetailProduct(Connection con, RetailProduct retailProduct) throws SQLException {

    String sqlString = QueryCache.get(
        Query.INSERT_RETAIL_PRODUCT);

    try (var stmt = con.prepareStatement(sqlString)) {
      stmt.setString(1, retailProduct.getEntityNo());
      stmt.setLong(2, retailProduct.getSequenceNo());
      stmt.setBigDecimal(3, retailProduct.getRetailPrice());
      stmt.setBigDecimal(4, retailProduct.getCost());
      stmt.setString(5, retailProduct.getDescription());
      stmt.setInt(6, retailProduct.getRetailCategoryId());
      stmt.setInt(7, retailProduct.getWoodSpecyId());
      stmt.setDouble(8, retailProduct.getProductWidth());
      stmt.setDouble(9, retailProduct.getProductThickness());
      stmt.setDouble(10, retailProduct.getProductLength());
      stmt.setObject(11, retailProduct.getCreatedAt());
      stmt.setLong(12, retailProduct.getCreatedByUserSeq());

      int rows = stmt.executeUpdate();
      if (rows == 0) {
        throw new SQLException("Insert failed, no rows affected.");
      }

      return retailProduct;
    }
  }
}
