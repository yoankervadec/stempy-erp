package com.lesconstructionssapete.stempyerp.infrastructure.persistence.repository.retailproduct;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

import com.lesconstructionssapete.stempyerp.domain.query.DomainQuery;
import com.lesconstructionssapete.stempyerp.domain.repository.retailproduct.RetailProductMasterRepository;
import com.lesconstructionssapete.stempyerp.domain.retailproduct.RetailProductMaster;
import com.lesconstructionssapete.stempyerp.infrastructure.mapper.retailproduct.RetailProductMasterMapper;
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

    DomainQuerySQLTranslator<RetailProductMaster.Fields> translator = new DomainQuerySQLTranslator<>(
        RetailProductMaster.Fields.class);

    translator.apply(builder, query);

    return SQLExecutor.query(
        connection,
        builder.build(),
        builder.getParams(),
        rs -> {
          List<RetailProductMaster> list = new ArrayList<>();
          while (rs.next()) {
            list.add(RetailProductMasterMapper.fromResultSet(rs));
          }
          return list;
        });

  }

  @Override
  public long insert(Connection connection, RetailProductMaster retailProductMaster) {

    String sql = QueryCache.get(Query.INSERT_DOM_RETAIL_PRODUCT_MASTER);

    SQLBuilder builder = new SQLBuilder(sql);

    RetailProductMasterMapper.bind(retailProductMaster, builder::bindInsert);

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

    RetailProductMasterMapper.bind(retailProductMaster, builder::bindUpdate);

    builder.where(RetailProductMaster.Fields.ID, "= :id")
        .bind(RetailProductMaster.Fields.ID, retailProductMaster.getEntityId());

    int rowsAffected = SQLExecutor.update(
        connection,
        builder.build(),
        builder.getParams());

    return rowsAffected;

  }
}
