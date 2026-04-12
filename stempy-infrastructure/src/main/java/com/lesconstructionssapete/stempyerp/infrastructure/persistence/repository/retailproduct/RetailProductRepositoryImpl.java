package com.lesconstructionssapete.stempyerp.infrastructure.persistence.repository.retailproduct;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

import com.lesconstructionssapete.stempyerp.field.retailproduct.RetailProductField;
import com.lesconstructionssapete.stempyerp.infrastructure.field.retailproduct.RetailProductSQLField;
import com.lesconstructionssapete.stempyerp.infrastructure.mapper.retailproduct.RetailProductRowMapper;
import com.lesconstructionssapete.stempyerp.infrastructure.mapper.retailproduct.RetailProductSQLMapper;
import com.lesconstructionssapete.stempyerp.infrastructure.persistence.SQLExecutor;
import com.lesconstructionssapete.stempyerp.infrastructure.query.DomainQuerySQLTranslator;
import com.lesconstructionssapete.stempyerp.infrastructure.query.Query;
import com.lesconstructionssapete.stempyerp.infrastructure.query.QueryCache;
import com.lesconstructionssapete.stempyerp.infrastructure.query.SQLBuilder;
import com.lesconstructionssapete.stempyerp.query.DomainQuery;
import com.lesconstructionssapete.stempyerp.repository.retailproduct.RetailProductRepository;
import com.lesconstructionssapete.stempyerp.retailproduct.RetailProduct;

public class RetailProductRepositoryImpl implements RetailProductRepository {

  @Override
  public List<RetailProduct> fetch(Connection connection, DomainQuery query) {

    String sql = QueryCache.get(
        Query.SELECT_DOM_RETAIL_PRODUCT_VARIANT);

    SQLBuilder builder = new SQLBuilder(sql);

    DomainQuerySQLTranslator translator = new DomainQuerySQLTranslator(RetailProductSQLField.all());

    translator.apply(builder, query);

    return SQLExecutor.query(
        connection,
        builder.build(),
        builder.getParams(),
        rs -> {
          List<RetailProduct> list = new ArrayList<>();
          while (rs.next()) {
            list.add(RetailProductRowMapper.map(rs));
          }
          return list;
        });

  }

  @Override
  public long insert(Connection connection, RetailProduct retailProduct) {

    String sql = QueryCache.get(
        Query.INSERT_DOM_RETAIL_PRODUCT_VARIANT);

    SQLBuilder builder = new SQLBuilder(sql);

    RetailProductSQLMapper.bindInsert(builder, retailProduct);

    long generatedId = SQLExecutor.insert(
        connection,
        builder.build(),
        builder.getParams());

    return generatedId;
  }

  @Override
  public int save(Connection connection, RetailProduct retailProduct) {

    String sql = QueryCache.get(
        Query.UPDATE_DOM_RETAIL_PRODUCT_VARIANT);

    SQLBuilder builder = new SQLBuilder(sql);

    RetailProductSQLMapper.bindUpdate(builder, retailProduct);

    builder.where("retail_product_variant.id = :id")
        .bind(RetailProductSQLField.get(RetailProductField.ID), retailProduct.getEntityId());

    int rowsAffected = SQLExecutor.update(
        connection,
        builder.build(),
        builder.getParams());

    return rowsAffected;
  }
}
