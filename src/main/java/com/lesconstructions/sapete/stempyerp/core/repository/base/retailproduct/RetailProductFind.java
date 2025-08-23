package com.lesconstructions.sapete.stempyerp.core.repository.base.retailproduct;

import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;

import com.lesconstructions.sapete.stempyerp.core.domain.base.retailproduct.RetailProduct;
import com.lesconstructions.sapete.stempyerp.core.shared.query.Query;
import com.lesconstructions.sapete.stempyerp.core.shared.query.QueryCache;
import com.lesconstructions.sapete.stempyerp.core.shared.query.SqlBuilder;

class RetailProductFind {

  RetailProduct findByproductNo(Connection con, String productNo) throws SQLException {
    RetailProduct retailProduct;

    String sqlBase = QueryCache.get(
        Query.SELECT_RETAIL_PRODUCT_BY_PRODUCT_NO);

    SqlBuilder builder = new SqlBuilder(sqlBase)
        .where("rp.product_no = ?", productNo);

    String sqlString = builder.build();
    List<Object> params = builder.getParams();

    try (var stmt = con.prepareStatement(sqlString)) {

      for (int i = 0; i < params.size(); i++) {
        stmt.setObject(i + 1, params.get(i));
      }

      try (var rs = stmt.executeQuery()) {
        if (!rs.next()) {
          // TODO Handle empty resultSet
          return null;
        }
        retailProduct = new RetailProduct(
            rs.getLong("product_seq"),
            rs.getString("product_no"),
            rs.getBigDecimal("retail_price"),
            rs.getBigDecimal("cost"),
            rs.getString("description"),
            rs.getInt("retail_category_id"),
            rs.getInt("wood_specy_id"),
            rs.getDouble("product_width"),
            rs.getDouble("product_thickness"),
            rs.getDouble("product_length"),
            rs.getBoolean("is_enabled"),
            rs.getObject("created_at", LocalDateTime.class),
            rs.getLong("created_by"));
      }
    }

    return retailProduct;
  }
}
