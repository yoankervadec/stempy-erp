package com.lesconstructionssapete.stempyerp.repository.auth;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

import com.lesconstructionssapete.stempyerp.config.db.SQLExecutor;
import com.lesconstructionssapete.stempyerp.domain.auth.AuthToken;
import com.lesconstructionssapete.stempyerp.domain.shared.query.DomainQuery;
import com.lesconstructionssapete.stempyerp.field.auth.RefreshTokenField;
import com.lesconstructionssapete.stempyerp.mapper.auth.RefreshTokenRowMapper;
import com.lesconstructionssapete.stempyerp.mapper.auth.RefreshTokenSQLMapper;
import com.lesconstructionssapete.stempyerp.query.DomainQuerySQLTranslator;
import com.lesconstructionssapete.stempyerp.query.Query;
import com.lesconstructionssapete.stempyerp.query.QueryCache;
import com.lesconstructionssapete.stempyerp.query.SQLBuilder;
import com.lesconstructionssapete.stempyerp.repository.RefreshTokenRepository;

public class RefreshTokenRepositoryImpl implements RefreshTokenRepository {

  @Override
  public List<AuthToken> fetch(Connection connection, DomainQuery query) {

    String sql = QueryCache.get(Query.SELECT_AUTH_REFRESH_TOKENS);

    SQLBuilder builder = new SQLBuilder(sql);

    DomainQuerySQLTranslator translator = new DomainQuerySQLTranslator(RefreshTokenField.all());

    translator.apply(builder, query);

    return SQLExecutor.query(
        connection,
        builder.build(),
        builder.getParams(),
        rs -> {
          List<AuthToken> list = new ArrayList<>();
          while (rs.next()) {
            list.add(RefreshTokenRowMapper.map(rs));
          }
          return list;
        });
  }

  @Override
  public long insert(Connection connection, AuthToken token) {

    String sql = QueryCache.get(Query.INSERT_AUTH_REFRESH_TOKEN);

    SQLBuilder builder = new SQLBuilder(sql);

    RefreshTokenSQLMapper.bindInsert(builder, token);

    long generatedId = SQLExecutor.insert(
        connection,
        builder.build(),
        builder.getParams());

    return generatedId;
  }

}
