package com.lesconstructionssapete.stempyerp.core.repository.base.retailproduct;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Types;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.lesconstructionssapete.stempyerp.core.repository.query.DomainQuerySQLTranslator;
import com.lesconstructionssapete.stempyerp.core.repository.query.SQLBuilder;
import com.lesconstructionssapete.stempyerp.core.shared.query.Query;
import com.lesconstructionssapete.stempyerp.core.shared.query.QueryCache;
import com.lesconstructionssapete.stempyerp.domain.base.retailproduct.RetailProduct;
import com.lesconstructionssapete.stempyerp.domain.shared.query.DomainQuery;

public class RetailProductRepositoryImpl implements RetailProductRepository {

  private static final Map<String, String> FIELD_MAP = Map.ofEntries(
      Map.entry("retailProductId", "retail_product_variant.id"),
      Map.entry("retailProductMasterId", "retail_product_variant.retail_product_master_id"),
      Map.entry("retailProductNo", "retail_product_variant.retail_product_no"),
      Map.entry("retailProductVariantNo", "retail_product_variant.retail_product_variant_no"),
      Map.entry("name", "retail_product_variant.name"),
      Map.entry("description", "retail_product_variant.description"),
      Map.entry("enabled", "retail_product_variant.enabled"),
      Map.entry("createdAt", "retail_product_variant.created_at"),
      Map.entry("createdByUserId", "retail_product_variant.created_by_user_id"),
      Map.entry("updatedAt", "retail_product_variant.updated_at"),
      Map.entry("updatedByUserId", "retail_product_variant.updated_by_user_id"));

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

    SQLBuilder builder = new SQLBuilder(sqlBase)
        .bindTyped(retailProduct.getRetailProductMasterId(), Types.BIGINT)
        .bindTyped(retailProduct.getRetailProductNo(), Types.VARCHAR)
        .bindTyped(retailProduct.getRetailProductVariantNo(), Types.VARCHAR)
        .bindTyped(retailProduct.getName(), Types.VARCHAR)
        .bindTyped(retailProduct.getDescription(), Types.VARCHAR)
        .bindTyped(retailProduct.isEnabled(), Types.TINYINT)
        .bindTyped(retailProduct.getCreatedByUserId(), Types.BIGINT)
        .bindTyped(retailProduct.getCreatedAt(), Types.TIMESTAMP)
        .bindTyped(retailProduct.getUpdatedByUserId(), Types.BIGINT)
        .bindTyped(retailProduct.getUpdatedAt(), Types.TIMESTAMP);

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

    SQLBuilder builder = new SQLBuilder(sqlBase)
        .bindTyped(retailProduct.getRetailProductMasterId(), Types.BIGINT)
        .bindTyped(retailProduct.getRetailProductNo(), Types.VARCHAR)
        .bindTyped(retailProduct.getRetailProductVariantNo(), Types.VARCHAR)
        .bindTyped(retailProduct.getName(), Types.VARCHAR)
        .bindTyped(retailProduct.getDescription(), Types.VARCHAR)
        .bindTyped(retailProduct.isEnabled(), Types.TINYINT)
        .bindTyped(retailProduct.getUpdatedByUserId(), Types.BIGINT)
        .bindTyped(LocalDateTime.now(), Types.TIMESTAMP)
        .bindTyped(retailProduct.getRetailProductId(), Types.BIGINT)
        .where("retail_product_variant.id = :id", retailProduct.getRetailProductId(), Types.BIGINT);

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
