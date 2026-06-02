package com.lesconstructionssapete.stempyerp.infrastructure.persistence.repository.retailproduct;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

import com.lesconstructionssapete.stempyerp.domain.field.retailproduct.RetailProductMasterField;
import com.lesconstructionssapete.stempyerp.domain.query.DomainQuery;
import com.lesconstructionssapete.stempyerp.domain.repository.retailproduct.RetailProductMasterRepository;
import com.lesconstructionssapete.stempyerp.domain.retailproduct.RetailProductMaster;
import com.lesconstructionssapete.stempyerp.infrastructure.mapper.ResultSetMapper;
import com.lesconstructionssapete.stempyerp.infrastructure.persistence.SQLExecutor;
import com.lesconstructionssapete.stempyerp.infrastructure.query.DomainQuerySQLTranslator;
import com.lesconstructionssapete.stempyerp.infrastructure.query.Query;
import com.lesconstructionssapete.stempyerp.infrastructure.query.QueryCache;
import com.lesconstructionssapete.stempyerp.infrastructure.query.SQLBuilder;

public class RetailProductMasterRepositoryImpl implements RetailProductMasterRepository {

  @Override
  public List<RetailProductMaster> fetch(Connection connection, DomainQuery query) {

    String sql = QueryCache.get(Query.SELECT_DOM_RETAIL_PRODUCT_MASTER);

    SQLBuilder builder = new SQLBuilder(sql);
    DomainQuerySQLTranslator<RetailProductMasterField> translator = new DomainQuerySQLTranslator<>(
        RetailProductMasterField.class);

    translator.apply(builder, query);

    return SQLExecutor.query(
        connection,
        builder.build(),
        builder.getParams(),
        rs -> {
          List<RetailProductMaster> list = new ArrayList<>();
          ResultSetMapper<RetailProductMaster> rsMapper = new ResultSetMapper<>(RetailProductMaster.class);
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
  public long insert(Connection connection, RetailProductMaster retailProductMaster) {

    String sql = QueryCache.get(Query.INSERT_DOM_RETAIL_PRODUCT_MASTER);

    SQLBuilder builder = new SQLBuilder(sql);

    RetailProductMasterSQLMapper.bindInsert(builder, retailProductMaster);

    long generatedId = SQLExecutor.insert(
        connection,
        builder.build(),
        builder.getParams());

    return generatedId;

  }

  @Override
  public int save(Connection connection, RetailProductMaster retailProductMaster) {

    String sql = QueryCache.get(Query.UPDATE_DOM_RETAIL_PRODUCT_MASTER);

    SQLBuilder builder = new SQLBuilder(sql);

    RetailProductMasterSQLMapper.bindUpdate(builder, retailProductMaster);

    builder.where("retail_product_master.id = :id")
        .bind(RetailProductMasterSQLField.get(RetailProductMasterField.ID), retailProductMaster.getEntityId());

    int rowsAffected = SQLExecutor.update(
        connection,
        builder.build(),
        builder.getParams());

    return rowsAffected;

  }
}
