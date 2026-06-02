package com.lesconstructionssapete.stempyerp.infrastructure.persistence.repository.authentication;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

import com.lesconstructionssapete.stempyerp.domain.auth.AuthToken;
import com.lesconstructionssapete.stempyerp.domain.field.auth.RefreshTokenField;
import com.lesconstructionssapete.stempyerp.domain.query.DomainQuery;
import com.lesconstructionssapete.stempyerp.domain.repository.RefreshTokenRepository;
import com.lesconstructionssapete.stempyerp.infrastructure.mapper.ResultSetMapper;
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

    DomainQuerySQLTranslator<RefreshTokenField> translator = new DomainQuerySQLTranslator<>(RefreshTokenField.class);

    translator.apply(builder, query);

    return SQLExecutor.query(
        connection,
        builder.build(),
        builder.getParams(),
        rs -> {
          List<AuthToken> list = new ArrayList<>();
          ResultSetMapper<AuthToken> rsMapper = new ResultSetMapper<>(AuthToken.class);
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
