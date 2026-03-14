package com.lesconstructionssapete.stempyerp.repository.retailproduct;

import java.sql.Connection;
import java.sql.Statement;
import java.sql.Types;
import java.util.List;

import com.lesconstructionssapete.stempyerp.domain.retailproduct.RetailProductMaster;
import com.lesconstructionssapete.stempyerp.domain.shared.query.DomainQuery;
import com.lesconstructionssapete.stempyerp.field.retailproduct.RetailProductMasterField;
import com.lesconstructionssapete.stempyerp.mapper.retailproduct.RetailProductMasterRowMapper;
import com.lesconstructionssapete.stempyerp.mapper.retailproduct.RetailProductMasterSQLMapper;
import com.lesconstructionssapete.stempyerp.query.DomainQuerySQLTranslator;
import com.lesconstructionssapete.stempyerp.query.Query;
import com.lesconstructionssapete.stempyerp.query.QueryCache;
import com.lesconstructionssapete.stempyerp.query.SQLBinder;
import com.lesconstructionssapete.stempyerp.query.SQLBuilder;

public class RetailProductMasterRepositoryImpl implements RetailProductMasterRepository {

  @Override
  public List<RetailProductMaster> fetch(Connection connection, DomainQuery query) throws Exception {

    List<RetailProductMaster> retailProductMasters;

    String sql = QueryCache.get(Query.SELECT_DOM_RETAIL_PRODUCT_MASTER);

    SQLBuilder builder = new SQLBuilder(sql);
    DomainQuerySQLTranslator translator = new DomainQuerySQLTranslator(RetailProductMasterField.all());

    translator.apply(builder, query);

    String sqlFinal = builder.build();
    List<SQLBuilder.SQLParam> params = builder.getParams();

    try (var stmt = connection.prepareStatement(sqlFinal)) {

      SQLBinder.bind(stmt, params);

      try (var rs = stmt.executeQuery()) {
        retailProductMasters = new java.util.ArrayList<>();
        while (rs.next()) {
          retailProductMasters.add(RetailProductMasterRowMapper.map(rs));
        }
      }
    }
    return retailProductMasters;
  }

  @Override
  public long insert(Connection connection, RetailProductMaster retailProductMaster) throws Exception {

    String sql = QueryCache.get(Query.INSERT_DOM_RETAIL_PRODUCT_MASTER);

    SQLBuilder builder = new SQLBuilder(sql);

    RetailProductMasterSQLMapper.bindInsert(builder, retailProductMaster);

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
  public RetailProductMaster save(Connection connection, RetailProductMaster retailProductMaster) throws Exception {

    String sql = QueryCache.get(Query.UPDATE_DOM_RETAIL_PRODUCT_MASTER);

    SQLBuilder builder = new SQLBuilder(sql);

    RetailProductMasterSQLMapper.bindUpdate(builder, retailProductMaster);

    builder.where("retail_product_master.id = :id")
        .bind("id",
            retailProductMaster.getRetailProductMasterId(),
            Types.BIGINT);

    String sqlString = builder.build();
    List<SQLBuilder.SQLParam> params = builder.getParams();

    try (var stmt = connection.prepareStatement(sqlString)) {

      SQLBinder.bind(stmt, params);

      stmt.executeUpdate();

      return retailProductMaster;
    }
  }

}
