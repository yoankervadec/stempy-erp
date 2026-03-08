package com.lesconstructionssapete.stempyerp.repository.retailproduct;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.lesconstructionssapete.stempyerp.domain.retailproduct.RetailProduct;
import com.lesconstructionssapete.stempyerp.domain.shared.query.DomainQuery;
import com.lesconstructionssapete.stempyerp.mapper.retailproduct.RetailProductSqlMapper;
import com.lesconstructionssapete.stempyerp.query.DomainQuerySQLTranslator;
import com.lesconstructionssapete.stempyerp.query.Query;
import com.lesconstructionssapete.stempyerp.query.QueryCache;
import com.lesconstructionssapete.stempyerp.query.SQLBuilder;
import com.lesconstructionssapete.stempyerp.repository.RetailProductRepository;

public class RetailProductRepositoryImpl implements RetailProductRepository {

  private static final Map<String, String> FIELD_MAP = Map.ofEntries(
      Map.entry("retailProductId", "dom_retail_product_variant.id"),
      Map.entry("retailProductMasterId", "dom_retail_product_variant.retail_product_master_id"),
      Map.entry("retailProductNo", "dom_retail_product_variant.retail_product_no"),
      Map.entry("retailProductVariantNo", "dom_retail_product_variant.dom_retail_product_variant_no"),
      Map.entry("name", "dom_retail_product_variant.name"),
      Map.entry("description", "dom_retail_product_variant.description"),
      Map.entry("enabled", "dom_retail_product_variant.enabled"),
      Map.entry("createdAt", "dom_retail_product_variant.created_at"),
      Map.entry("createdByUserId", "dom_retail_product_variant.created_by_user_id"),
      Map.entry("updatedAt", "dom_retail_product_variant.updated_at"),
      Map.entry("updatedByUserId", "dom_retail_product_variant.updated_by_user_id"));

  @Override
  public List<RetailProduct> fetch(Connection connection, DomainQuery query) throws SQLException {

    List<RetailProduct> retailProducts;

    String sql = QueryCache.get(
        Query.SELECT_RETAIL_PRODUCT_VARIANT);

    SQLBuilder builder = new SQLBuilder(sql);

    DomainQuerySQLTranslator translator = new DomainQuerySQLTranslator(FIELD_MAP);

    translator.apply(builder, query);

    String sqlFinal = builder.build();
    List<SQLBuilder.SQLParam> params = builder.getParams();

    try (var stmt = connection.prepareStatement(sqlFinal)) {

      int idx = 1;
      for (SQLBuilder.SQLParam p : params) {
        stmt.setObject(idx++, p.value(), p.sqlType());
      }

      try (var rs = stmt.executeQuery()) {
        retailProducts = new ArrayList<>();
        while (rs.next()) {
          retailProducts.add(new RetailProduct(
              rs.getLong("id"),
              rs.getLong("retail_product_master_id"),
              rs.getString("retail_product_no"),
              rs.getString("retail_product_variant_no"),
              rs.getString("name"),
              rs.getString("description"),
              rs.getBoolean("enabled"),
              rs.getTimestamp("created_at").toLocalDateTime(),
              rs.getLong("created_by_user_id"),
              rs.getTimestamp("updated_at").toLocalDateTime(),
              rs.getLong("updated_by_user_id")));
        }
      }
    }

    return retailProducts;
  }

  @Override
  public RetailProduct insert(Connection connection, RetailProduct retailProduct) throws SQLException {

    String sqlBase = QueryCache.get(
        Query.INSERT_RETAIL_PRODUCT_VARIANT);

    SQLBuilder builder = new SQLBuilder(sqlBase);

    RetailProductSqlMapper.bindInsert(builder, retailProduct);

    String sqlFinal = builder.build();
    List<SQLBuilder.SQLParam> params = builder.getParams();

    try (var stmt = connection.prepareStatement(sqlFinal)) {

      int idx = 1;
      for (SQLBuilder.SQLParam p : params) {
        stmt.setObject(idx++, p.value(), p.sqlType());
      }

      stmt.executeUpdate();

      return retailProduct;
    }
  }

  @Override
  public RetailProduct save(Connection connection, RetailProduct retailProduct) throws SQLException {

    String sqlBase = QueryCache.get(
        Query.UPDATE_RETAIL_PRODUCT_VARIANT);

    SQLBuilder builder = new SQLBuilder(sqlBase);

    RetailProductSqlMapper.bindUpdate(builder, retailProduct);

    builder.where(
        "retail_product_variant.id = :id",
        retailProduct.getRetailProductId(),
        Types.BIGINT);

    String sqlString = builder.build();
    List<SQLBuilder.SQLParam> params = builder.getParams();

    try (var stmt = connection.prepareStatement(sqlString)) {

      int idx = 1;
      for (SQLBuilder.SQLParam p : params) {
        stmt.setObject(idx++, p.value(), p.sqlType());
      }

      stmt.executeUpdate();

      return retailProduct;
    }
  }

}
