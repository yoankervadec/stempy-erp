package com.lesconstructionssapete.stempyerp.repository.auth;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

import com.lesconstructionssapete.stempyerp.config.db.SQLExecutor;
import com.lesconstructionssapete.stempyerp.domain.auth.UserCredential;
import com.lesconstructionssapete.stempyerp.domain.shared.query.DomainQuery;
import com.lesconstructionssapete.stempyerp.field.auth.UserCredentialSQLField;
import com.lesconstructionssapete.stempyerp.mapper.auth.UserCredentialRowMapper;
import com.lesconstructionssapete.stempyerp.query.DomainQuerySQLTranslator;
import com.lesconstructionssapete.stempyerp.query.Query;
import com.lesconstructionssapete.stempyerp.query.QueryCache;
import com.lesconstructionssapete.stempyerp.query.SQLBuilder;

public class UserCredentialRepositoryImpl implements UserCredentialRepository {

  @Override
  public List<UserCredential> fetch(Connection connection, DomainQuery query) {

    String sql = QueryCache.get(
        Query.AUTH_SELECT_USER_CREDENTIAL);

    SQLBuilder builder = new SQLBuilder(sql);

    DomainQuerySQLTranslator translator = new DomainQuerySQLTranslator(UserCredentialSQLField.all());

    translator.apply(builder, query);

    return SQLExecutor.query(
        connection,
        builder.build(),
        builder.getParams(),
        rs -> {
          List<UserCredential> list = new ArrayList<>();
          while (rs.next()) {
            list.add(UserCredentialRowMapper.map(rs));
          }
          return list;
        });

  }

}
