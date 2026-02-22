package com.lesconstructionssapete.stempyerp.core.repository.base.retailproduct;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Types;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.lesconstructionssapete.stempyerp.core.domain.base.retailproduct.RetailProduct;
import com.lesconstructionssapete.stempyerp.core.domain.shared.query.DomainQuery;
import com.lesconstructionssapete.stempyerp.core.repository.query.DomainQuerySQLTranslator;
import com.lesconstructionssapete.stempyerp.core.repository.query.SqlBuilder;
import com.lesconstructionssapete.stempyerp.core.shared.query.Query;
import com.lesconstructionssapete.stempyerp.core.shared.query.QueryCache;

public class RetailProductRepositoryImpl implements RetailProductRepository {

  private static final Map<String, String> FIELD_MAP = Map.ofEntries(
      Map.entry("productNo", "rp.product_no"),
      Map.entry("sequenceNo", "rp.product_seq"),
      Map.entry("retailPrice", "rp.retail_price"),
      Map.entry("cost", "rp.cost"),
      Map.entry("description", "rp.description"),
      Map.entry("retailCategoryId", "rp.retail_category_id"),
      Map.entry("woodSpecyId", "rp.wood_specy_id"),
      Map.entry("productWidth", "rp.product_width"),
      Map.entry("productThickness", "rp.product_thickness"),
      Map.entry("productLength", "rp.product_length"),
      Map.entry("enabled", "rp.is_enabled"),
      Map.entry("createdAt", "rp.created_at"),
      Map.entry("createdBy", "rp.created_by"));

  @Override
  public List<RetailProduct> fetch(Connection connection, DomainQuery query) throws SQLException {

    List<RetailProduct> retailProducts;

    String sql = QueryCache.get(
        Query.SELECT_RETAIL_PRODUCTS);

    SqlBuilder builder = new SqlBuilder(sql);

    DomainQuerySQLTranslator translator = new DomainQuerySQLTranslator(FIELD_MAP);

    translator.apply(builder, query);

    String sqlFinal = builder.build();
    List<SqlBuilder.SqlParam> params = builder.getParams();

    try (var stmt = connection.prepareStatement(sqlFinal)) {

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

  @Override
  public RetailProduct insert(Connection connection, RetailProduct retailProduct) throws SQLException {

    String sqlBase = QueryCache.get(
        Query.INSERT_RETAIL_PRODUCT);

    SqlBuilder builder = new SqlBuilder(sqlBase)
        .bindTyped(retailProduct.getProductNo(), Types.VARCHAR)
        .bindTyped(retailProduct.getEntitySeq(), Types.BIGINT)
        .bindTyped(retailProduct.getRetailPrice(), Types.DECIMAL)
        .bindTyped(retailProduct.getCost(), Types.DECIMAL)
        .bindTyped(retailProduct.getDescription(), Types.VARCHAR)
        .bindTyped(retailProduct.getRetailCategoryId(), Types.INTEGER)
        .bindTyped(retailProduct.getWoodSpecyId(), Types.INTEGER)
        .bindTyped(retailProduct.getProductWidth(), Types.DECIMAL)
        .bindTyped(retailProduct.getProductThickness(), Types.DECIMAL)
        .bindTyped(retailProduct.getProductLength(), Types.DECIMAL)
        .bindTyped(retailProduct.isEnabled(), Types.TINYINT)
        .bindTyped(retailProduct.getCreatedAt(), Types.TIMESTAMP)
        .bindTyped(retailProduct.getCreatedByUserSeq(), Types.BIGINT);

    String sqlFinal = builder.build();
    List<SqlBuilder.SqlParam> params = builder.getParams();

    try (var stmt = connection.prepareStatement(sqlFinal)) {

      int idx = 1;
      for (SqlBuilder.SqlParam p : params) {
        stmt.setObject(idx++, p.value(), p.sqlType());
      }

      stmt.executeUpdate();

      return retailProduct;
    }
  }

  @Override
  public RetailProduct save(Connection connection, RetailProduct retailProduct) throws SQLException {

    String sqlBase = QueryCache.get(
        Query.UPDATE_RETAIL_PRODUCT);

    SqlBuilder builder = new SqlBuilder(sqlBase)
        .bindTyped(retailProduct.getProductNo(), Types.VARCHAR)
        .bindTyped(retailProduct.getEntitySeq(), Types.BIGINT)
        .bindTyped(retailProduct.getRetailPrice(), Types.DECIMAL)
        .bindTyped(retailProduct.getCost(), Types.DECIMAL)
        .bindTyped(retailProduct.getDescription(), Types.VARCHAR)
        .bindTyped(retailProduct.getRetailCategoryId(), Types.INTEGER)
        .bindTyped(retailProduct.getWoodSpecyId(), Types.INTEGER)
        .bindTyped(retailProduct.getProductWidth(), Types.DECIMAL)
        .bindTyped(retailProduct.getProductThickness(), Types.DECIMAL)
        .bindTyped(retailProduct.getProductLength(), Types.DECIMAL)
        .bindTyped(retailProduct.isEnabled(), Types.TINYINT)
        .where("rp.product_no = :productNo", retailProduct.getProductNo());

    String sqlString = builder.build();
    List<SqlBuilder.SqlParam> params = builder.getParams();

    try (var stmt = connection.prepareStatement(sqlString)) {

      int idx = 1;
      for (SqlBuilder.SqlParam p : params) {
        stmt.setObject(idx++, p.value(), p.sqlType());
      }

      stmt.executeUpdate();

      return retailProduct;
    }
  }

}
