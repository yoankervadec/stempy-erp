package com.lesconstructionssapete.stempyerp.repository.auth;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.lesconstructionssapete.stempyerp.domain.auth.AuthToken;
import com.lesconstructionssapete.stempyerp.domain.shared.query.DomainQuery;
import com.lesconstructionssapete.stempyerp.field.auth.RefreshTokenField;
import com.lesconstructionssapete.stempyerp.mapper.auth.RefreshTokenRowMapper;
import com.lesconstructionssapete.stempyerp.mapper.auth.RefreshTokenSQLMapper;
import com.lesconstructionssapete.stempyerp.query.DomainQuerySQLTranslator;
import com.lesconstructionssapete.stempyerp.query.Query;
import com.lesconstructionssapete.stempyerp.query.QueryCache;
import com.lesconstructionssapete.stempyerp.query.SQLBinder;
import com.lesconstructionssapete.stempyerp.query.SQLBuilder;
import com.lesconstructionssapete.stempyerp.repository.RefreshTokenRepository;

public class RefreshTokenRepositoryImpl implements RefreshTokenRepository {

  @Override
  public List<AuthToken> fetch(Connection connection, DomainQuery query) throws SQLException {

    List<AuthToken> tokens;

    String sql = QueryCache.get(Query.SELECT_AUTH_REFRESH_TOKENS);

    SQLBuilder builder = new SQLBuilder(sql);

    DomainQuerySQLTranslator translator = new DomainQuerySQLTranslator(RefreshTokenField.all());

    translator.apply(builder, query);

    String sqlFinal = builder.build();
    List<SQLBuilder.SQLParam> params = builder.getParams();

    try (var stmt = connection.prepareStatement(sqlFinal)) {

      SQLBinder.bind(stmt, params);

      try (var rs = stmt.executeQuery()) {
        tokens = new ArrayList<>();
        while (rs.next()) {
          tokens.add(RefreshTokenRowMapper.map(rs));
        }
      }
      return tokens;
    }

  }

  @Override
  public long insert(Connection connection, AuthToken token) throws SQLException {

    String sql = QueryCache.get(Query.INSERT_AUTH_REFRESH_TOKEN);

    SQLBuilder builder = new SQLBuilder(sql);

    RefreshTokenSQLMapper.bindInsert(builder, token);

    String sqlFinal = builder.build();
    List<SQLBuilder.SQLParam> params = builder.getParams();

    try (var stmt = connection.prepareStatement(sqlFinal, Statement.RETURN_GENERATED_KEYS)) {

      SQLBinder.bind(stmt, params);

      stmt.executeUpdate();

      try (var rs = stmt.getGeneratedKeys()) {
        if (rs.next()) {
          return rs.getLong(1);
        }
        return 0;
      }
    }

  }

}
