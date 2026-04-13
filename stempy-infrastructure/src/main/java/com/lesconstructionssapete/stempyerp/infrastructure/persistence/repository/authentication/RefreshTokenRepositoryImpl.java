package com.lesconstructionssapete.stempyerp.infrastructure.persistence.repository.authentication;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

import com.lesconstructionssapete.stempyerp.domain.auth.AuthToken;
import com.lesconstructionssapete.stempyerp.domain.query.DomainQuery;
import com.lesconstructionssapete.stempyerp.domain.repository.RefreshTokenRepository;
import com.lesconstructionssapete.stempyerp.infrastructure.field.authentication.RefreshTokenSQLField;
import com.lesconstructionssapete.stempyerp.infrastructure.mapper.authentication.RefreshTokenRowMapper;
import com.lesconstructionssapete.stempyerp.infrastructure.mapper.authentication.RefreshTokenSQLMapper;
import com.lesconstructionssapete.stempyerp.infrastructure.persistence.SQLExecutor;
import com.lesconstructionssapete.stempyerp.infrastructure.query.DomainQuerySQLTranslator;
import com.lesconstructionssapete.stempyerp.infrastructure.query.Query;
import com.lesconstructionssapete.stempyerp.infrastructure.query.QueryCache;
import com.lesconstructionssapete.stempyerp.infrastructure.query.SQLBuilder;

public class RefreshTokenRepositoryImpl implements RefreshTokenRepository {

  @Override
  public List<AuthToken> fetch(Connection connection, DomainQuery query) {

    String sql = QueryCache.get(Query.SELECT_AUTH_REFRESH_TOKENS);

    SQLBuilder builder = new SQLBuilder(sql);

    DomainQuerySQLTranslator translator = new DomainQuerySQLTranslator(RefreshTokenSQLField.all());

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
