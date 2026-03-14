package com.lesconstructionssapete.stempyerp.repository.retailproduct;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import com.lesconstructionssapete.stempyerp.domain.retailproduct.RetailProduct;
import com.lesconstructionssapete.stempyerp.domain.shared.query.DomainQuery;
import com.lesconstructionssapete.stempyerp.field.retailproduct.RetailProductField;
import com.lesconstructionssapete.stempyerp.mapper.retailproduct.RetailProductRowMapper;
import com.lesconstructionssapete.stempyerp.mapper.retailproduct.RetailProductSQLMapper;
import com.lesconstructionssapete.stempyerp.query.DomainQuerySQLTranslator;
import com.lesconstructionssapete.stempyerp.query.Query;
import com.lesconstructionssapete.stempyerp.query.QueryCache;
import com.lesconstructionssapete.stempyerp.query.SQLBinder;
import com.lesconstructionssapete.stempyerp.query.SQLBuilder;

public class RetailProductRepositoryImpl implements RetailProductRepository {

  @Override
  public List<RetailProduct> fetch(Connection connection, DomainQuery query) throws SQLException {

    List<RetailProduct> retailProducts;

    String sql = QueryCache.get(
        Query.SELECT_DOM_RETAIL_PRODUCT_VARIANT);

    SQLBuilder builder = new SQLBuilder(sql);

    DomainQuerySQLTranslator translator = new DomainQuerySQLTranslator(RetailProductField.all());

    translator.apply(builder, query);

    String sqlFinal = builder.build();
    List<SQLBuilder.SQLParam> params = builder.getParams();

    try (var stmt = connection.prepareStatement(sqlFinal)) {

      SQLBinder.bind(stmt, params);

      try (var rs = stmt.executeQuery()) {
        retailProducts = new ArrayList<>();
        while (rs.next()) {
          retailProducts.add(RetailProductRowMapper.map(rs));
        }
      }
    }

    return retailProducts;
  }

  @Override
  public long insert(Connection connection, RetailProduct retailProduct) throws SQLException {

    String sql = QueryCache.get(
        Query.INSERT_DOM_RETAIL_PRODUCT_VARIANT);

    SQLBuilder builder = new SQLBuilder(sql);

    RetailProductSQLMapper.bindInsert(builder, retailProduct);

    String sqlFinal = builder.build();
    List<SQLBuilder.SQLParam> params = builder.getParams();

    try (var stmt = connection.prepareStatement(sqlFinal, Statement.RETURN_GENERATED_KEYS)) {

      SQLBinder.bind(stmt, params);

      stmt.executeUpdate();

      try (var rs = stmt.getGeneratedKeys()) {
        if (rs.next()) {
          return rs.getLong(1);
        }
      }
    }

    return 0;
  }

  @Override
  public RetailProduct save(Connection connection, RetailProduct retailProduct) throws SQLException {

    String sql = QueryCache.get(
        Query.UPDATE_DOM_RETAIL_PRODUCT_VARIANT);

    SQLBuilder builder = new SQLBuilder(sql);

    RetailProductSQLMapper.bindUpdate(builder, retailProduct);

    builder.where("retail_product_variant.id = :id")
        .bind("id",
            retailProduct.getRetailProductId(),
            Types.BIGINT);

    String sqlString = builder.build();
    List<SQLBuilder.SQLParam> params = builder.getParams();

    try (var stmt = connection.prepareStatement(sqlString)) {

      SQLBinder.bind(stmt, params);

      stmt.executeUpdate();

      return retailProduct;
    }
  }

}
