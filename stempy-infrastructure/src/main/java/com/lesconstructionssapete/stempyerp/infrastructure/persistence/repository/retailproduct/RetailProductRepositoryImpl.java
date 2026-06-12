package com.lesconstructionssapete.stempyerp.infrastructure.persistence.repository.retailproduct;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

import com.lesconstructionssapete.stempyerp.domain.query.DomainQuery;
import com.lesconstructionssapete.stempyerp.domain.repository.retailproduct.RetailProductRepository;
import com.lesconstructionssapete.stempyerp.domain.retailproduct.RetailProductVariant;
import com.lesconstructionssapete.stempyerp.infrastructure.mapper.retailproduct.RetailProductMapper;
import com.lesconstructionssapete.stempyerp.infrastructure.persistence.SQLExecutor;
import com.lesconstructionssapete.stempyerp.infrastructure.query.DomainQuerySQLTranslator;
import com.lesconstructionssapete.stempyerp.infrastructure.query.Query;
import com.lesconstructionssapete.stempyerp.infrastructure.query.QueryCache;
import com.lesconstructionssapete.stempyerp.infrastructure.query.SQLBuilder;

public class RetailProductRepositoryImpl implements RetailProductRepository {

  @Override
  public List<RetailProductVariant> fetch(Connection connection, DomainQuery query) {

    String sql = QueryCache.get(
        Query.SELECT_DOM_RETAIL_PRODUCT_VARIANT);

    SQLBuilder builder = new SQLBuilder(sql);

    DomainQuerySQLTranslator<RetailProductVariant.Fields> translator = new DomainQuerySQLTranslator<>(
        RetailProductVariant.Fields.class);

    translator.apply(builder, query);

    return SQLExecutor.query(
        connection,
        builder.build(),
        builder.getParams(),
        rs -> {
          List<RetailProductVariant> list = new ArrayList<>();
          while (rs.next()) {
            list.add(RetailProductMapper.fromResultSet(rs));
          }
          return list;
        });

  }

  @Override
  public long insert(Connection connection, RetailProductVariant retailProduct) {

    String sql = QueryCache.get(
        Query.INSERT_DOM_RETAIL_PRODUCT_VARIANT);

    SQLBuilder builder = new SQLBuilder(sql);

    RetailProductMapper.bind(retailProduct, builder::bindInsert);

    long generatedId = SQLExecutor.insert(
        connection,
        builder.build(),
        builder.getParams());

    return generatedId;
  }

  @Override
  public int save(Connection connection, RetailProductVariant retailProduct) {

    String sql = QueryCache.get(
        Query.UPDATE_DOM_RETAIL_PRODUCT_VARIANT);

    SQLBuilder builder = new SQLBuilder(sql);

    RetailProductMapper.bind(retailProduct, builder::bindUpdate);

    builder.where(RetailProductVariant.Fields.ID, "= :id")
        .bind(RetailProductVariant.Fields.ID, retailProduct.getEntityId());

    int rowsAffected = SQLExecutor.update(
        connection,
        builder.build(),
        builder.getParams());

    return rowsAffected;
  }
}
