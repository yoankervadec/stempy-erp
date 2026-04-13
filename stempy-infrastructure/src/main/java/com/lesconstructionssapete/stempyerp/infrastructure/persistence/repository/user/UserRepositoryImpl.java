package com.lesconstructionssapete.stempyerp.infrastructure.persistence.repository.user;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

import com.lesconstructionssapete.stempyerp.domain.query.DomainQuery;
import com.lesconstructionssapete.stempyerp.domain.repository.UserRepository;
import com.lesconstructionssapete.stempyerp.domain.user.User;
import com.lesconstructionssapete.stempyerp.infrastructure.field.user.UserSQLField;
import com.lesconstructionssapete.stempyerp.infrastructure.mapper.user.UserRowMapper;
import com.lesconstructionssapete.stempyerp.infrastructure.persistence.SQLExecutor;
import com.lesconstructionssapete.stempyerp.infrastructure.query.DomainQuerySQLTranslator;
import com.lesconstructionssapete.stempyerp.infrastructure.query.Query;
import com.lesconstructionssapete.stempyerp.infrastructure.query.QueryCache;
import com.lesconstructionssapete.stempyerp.infrastructure.query.SQLBuilder;

public class UserRepositoryImpl implements UserRepository {

  @Override
  public List<User> fetch(Connection connection, DomainQuery query) {

    String sql = QueryCache.get(
        Query.SELECT_AUTH_USER);

    SQLBuilder builder = new SQLBuilder(sql);

    DomainQuerySQLTranslator translator = new DomainQuerySQLTranslator(UserSQLField.all());

    translator.apply(builder, query);

    return SQLExecutor.query(
        connection,
        builder.build(),
        builder.getParams(),
        rs -> {
          List<User> list = new ArrayList<>();
          while (rs.next()) {
            list.add(UserRowMapper.map(rs));
          }
          return list;
        });
  }

}
