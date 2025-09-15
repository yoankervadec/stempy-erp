package com.lesconstructionssapete.stempyerp.core.repository.base.retailproduct;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Types;
import java.util.List;

import com.lesconstructionssapete.stempyerp.core.domain.base.retailproduct.RetailProduct;
import com.lesconstructionssapete.stempyerp.core.shared.query.Query;
import com.lesconstructionssapete.stempyerp.core.shared.query.QueryCache;
import com.lesconstructionssapete.stempyerp.core.shared.query.SqlBuilder;

class RetailProductUpdate {

  RetailProduct save(Connection connection, RetailProduct rp) throws SQLException {

    String sqlBase = QueryCache.get(
        Query.UPDATE_RETAIL_PRODUCT);

    SqlBuilder builder = new SqlBuilder(sqlBase)
        .bindTyped(rp.getProductNo(), Types.VARCHAR)
        .bindTyped(rp.getEntitySeq(), Types.BIGINT)
        .bindTyped(rp.getRetailPrice(), Types.DECIMAL)
        .bindTyped(rp.getCost(), Types.DECIMAL)
        .bindTyped(rp.getDescription(), Types.VARCHAR)
        .bindTyped(rp.getRetailCategoryId(), Types.INTEGER)
        .bindTyped(rp.getWoodSpecyId(), Types.INTEGER)
        .bindTyped(rp.getProductWidth(), Types.DECIMAL)
        .bindTyped(rp.getProductThickness(), Types.DECIMAL)
        .bindTyped(rp.getProductLength(), Types.DECIMAL)
        .bindTyped(rp.isEnabled(), Types.TINYINT)
        .where("rp.product_no = :productNo", rp.getProductNo());

    String sqlString = builder.build();
    List<SqlBuilder.SqlParam> params = builder.getParams();

    try (var stmt = connection.prepareStatement(sqlString)) {

      int idx = 1;
      for (SqlBuilder.SqlParam p : params) {
        stmt.setObject(idx++, p.value(), p.sqlType());
      }

      int rows = stmt.executeUpdate();
      if (rows == 0) {
        throw new SQLException("Update failed, no rows affected");
      }

      return rp;
    }

  }

}
