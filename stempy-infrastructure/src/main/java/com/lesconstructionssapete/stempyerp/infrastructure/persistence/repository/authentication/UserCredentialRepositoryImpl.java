package com.lesconstructionssapete.stempyerp.infrastructure.persistence.repository.authentication;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

import com.lesconstructionssapete.stempyerp.domain.auth.UserCredential;
import com.lesconstructionssapete.stempyerp.domain.field.auth.UserCredentialField;
import com.lesconstructionssapete.stempyerp.domain.query.DomainQuery;
import com.lesconstructionssapete.stempyerp.domain.repository.auth.UserCredentialRepository;
import com.lesconstructionssapete.stempyerp.infrastructure.mapper.ResultSetMapper;
import com.lesconstructionssapete.stempyerp.infrastructure.persistence.SQLExecutor;
import com.lesconstructionssapete.stempyerp.infrastructure.query.DomainQuerySQLTranslator;
import com.lesconstructionssapete.stempyerp.infrastructure.query.Query;
import com.lesconstructionssapete.stempyerp.infrastructure.query.QueryCache;
import com.lesconstructionssapete.stempyerp.infrastructure.query.SQLBuilder;

public class UserCredentialRepositoryImpl implements UserCredentialRepository {

  @Override
  public List<UserCredential> fetch(Connection connection, DomainQuery query) {

    String sql = QueryCache.get(
        Query.SELECT_AUTH_USER_CREDENTIAL);

    SQLBuilder builder = new SQLBuilder(sql);

    DomainQuerySQLTranslator<UserCredentialField> translator = new DomainQuerySQLTranslator<>(
        UserCredentialField.class);

    translator.apply(builder, query);

    return SQLExecutor.query(
        connection,
        builder.build(),
        builder.getParams(),
        rs -> {
          List<UserCredential> list = new ArrayList<>();
          ResultSetMapper<UserCredential> rsMapper = new ResultSetMapper<>(UserCredential.class);
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

}
