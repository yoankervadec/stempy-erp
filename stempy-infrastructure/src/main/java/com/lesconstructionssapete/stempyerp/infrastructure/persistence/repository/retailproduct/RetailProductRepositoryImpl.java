package com.lesconstructionssapete.stempyerp.infrastructure.persistence.repository.retailproduct;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

import com.lesconstructionssapete.stempyerp.domain.field.retailproduct.RetailProductField;
import com.lesconstructionssapete.stempyerp.domain.query.DomainQuery;
import com.lesconstructionssapete.stempyerp.domain.repository.retailproduct.RetailProductRepository;
import com.lesconstructionssapete.stempyerp.domain.retailproduct.RetailProduct;
import com.lesconstructionssapete.stempyerp.infrastructure.mapper.ResultSetMapper;
import com.lesconstructionssapete.stempyerp.infrastructure.persistence.SQLExecutor;
import com.lesconstructionssapete.stempyerp.infrastructure.query.DomainQuerySQLTranslator;
import com.lesconstructionssapete.stempyerp.infrastructure.query.Query;
import com.lesconstructionssapete.stempyerp.infrastructure.query.QueryCache;
import com.lesconstructionssapete.stempyerp.infrastructure.query.SQLBuilder;

public class RetailProductRepositoryImpl implements RetailProductRepository {

  @Override
  public List<RetailProduct> fetch(Connection connection, DomainQuery query) {

    String sql = QueryCache.get(
        Query.SELECT_DOM_RETAIL_PRODUCT_VARIANT);

    SQLBuilder builder = new SQLBuilder(sql);

    DomainQuerySQLTranslator<RetailProductField> translator = new DomainQuerySQLTranslator<>(RetailProductField.class);

    translator.apply(builder, query);

    return SQLExecutor.query(
        connection,
        builder.build(),
        builder.getParams(),
        rs -> {
          List<RetailProduct> list = new ArrayList<>();
          ResultSetMapper<RetailProduct> rsMapper = new ResultSetMapper<>(RetailProduct.class);
          while (rs.next()) {
            try {
              list.add(rsMapper.mapRow(rs));
            } catch (Exception e) {
              e.printStackTrace();
            }
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
