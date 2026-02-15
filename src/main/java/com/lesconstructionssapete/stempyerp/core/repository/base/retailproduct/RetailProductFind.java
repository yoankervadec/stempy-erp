package com.lesconstructionssapete.stempyerp.core.repository.base.retailproduct;

import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.lesconstructionssapete.stempyerp.core.domain.base.retailproduct.RetailProduct;
import com.lesconstructionssapete.stempyerp.core.shared.query.Query;
import com.lesconstructionssapete.stempyerp.core.shared.query.QueryCache;
import com.lesconstructionssapete.stempyerp.core.shared.query.SqlBuilder;

class RetailProductFind {

  /**
   * Find by RetailProduct Number (e.g., "PR000187")
   */
  RetailProduct findByProductNo(Connection con, String productNo) throws SQLException {
    RetailProduct retailProduct;

    String sqlBase = QueryCache.get(
        Query.SELECT_RETAIL_PRODUCTS);

    SqlBuilder builder = new SqlBuilder(sqlBase)
        .where("rp.product_no = :productNo", productNo);

    String sqlString = builder.build();
    List<SqlBuilder.SqlParam> params = builder.getParams();

    try (var stmt = con.prepareStatement(sqlString)) {

      int idx = 1;
      for (SqlBuilder.SqlParam p : params) {
        stmt.setObject(idx++, p.value(), p.sqlType());
      }

      try (var rs = stmt.executeQuery()) {
        if (!rs.next()) {
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

  public List<RetailProduct> fetchAll(Connection connection, Boolean isEnabled) throws SQLException {

    List<RetailProduct> retailProducts;

    String sqlBase = QueryCache.get(
        Query.SELECT_RETAIL_PRODUCTS);

    SqlBuilder builder = new SqlBuilder(sqlBase)
        .where("rp.is_enabled = :isEnabled", isEnabled);

    String sqlString = builder.build();
    List<SqlBuilder.SqlParam> params = builder.getParams();

    try (var stmt = connection.prepareStatement(sqlString)) {

      int idx = 1;
      for (SqlBuilder.SqlParam p : params) {
        stmt.setObject(idx++, p.value(), p.sqlType());
      }

      try (var rs = stmt.executeQuery()) {
        retailProducts = new ArrayList<>();
        while (rs.next()) {
          retailProducts.add(new RetailProduct(
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
              rs.getLong("created_by")));
        }
      }
    }

    return retailProducts;

  }
}
