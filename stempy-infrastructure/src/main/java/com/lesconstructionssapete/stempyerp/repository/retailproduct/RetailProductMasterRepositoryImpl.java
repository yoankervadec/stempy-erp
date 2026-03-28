package com.lesconstructionssapete.stempyerp.repository.retailproduct;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

import com.lesconstructionssapete.stempyerp.config.db.SQLExecutor;
import com.lesconstructionssapete.stempyerp.domain.retailproduct.RetailProductMaster;
import com.lesconstructionssapete.stempyerp.domain.shared.query.DomainQuery;
import com.lesconstructionssapete.stempyerp.field.retailproduct.RetailProductMasterField;
import com.lesconstructionssapete.stempyerp.mapper.retailproduct.RetailProductMasterRowMapper;
import com.lesconstructionssapete.stempyerp.mapper.retailproduct.RetailProductMasterSQLMapper;
import com.lesconstructionssapete.stempyerp.query.DomainQuerySQLTranslator;
import com.lesconstructionssapete.stempyerp.query.Query;
import com.lesconstructionssapete.stempyerp.query.QueryCache;
import com.lesconstructionssapete.stempyerp.query.SQLBuilder;

public class RetailProductMasterRepositoryImpl implements RetailProductMasterRepository {

  @Override
  public List<RetailProductMaster> fetch(Connection connection, DomainQuery query) {

    String sql = QueryCache.get(Query.SELECT_DOM_RETAIL_PRODUCT_MASTER);

    SQLBuilder builder = new SQLBuilder(sql);
    DomainQuerySQLTranslator translator = new DomainQuerySQLTranslator(RetailProductMasterField.all());

    translator.apply(builder, query);

    return SQLExecutor.query(
        connection,
        builder.build(),
        builder.getParams(),
        rs -> {
          List<RetailProductMaster> list = new ArrayList<>();
          while (rs.next()) {
            list.add(RetailProductMasterRowMapper.map(rs));
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
        .bind(RetailProductMasterField.ID, retailProductMaster.getEntityId());

    int rowsAffected = SQLExecutor.update(
        connection,
        builder.build(),
        builder.getParams());

    return rowsAffected;

  }
}
