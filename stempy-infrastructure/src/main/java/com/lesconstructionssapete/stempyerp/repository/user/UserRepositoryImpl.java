package com.lesconstructionssapete.stempyerp.repository.user;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

import com.lesconstructionssapete.stempyerp.config.db.SQLExecutor;
import com.lesconstructionssapete.stempyerp.domain.auth.User;
import com.lesconstructionssapete.stempyerp.domain.shared.query.DomainQuery;
import com.lesconstructionssapete.stempyerp.field.user.UserSQLField;
import com.lesconstructionssapete.stempyerp.mapper.user.UserRowMapper;
import com.lesconstructionssapete.stempyerp.query.DomainQuerySQLTranslator;
import com.lesconstructionssapete.stempyerp.query.Query;
import com.lesconstructionssapete.stempyerp.query.QueryCache;
import com.lesconstructionssapete.stempyerp.query.SQLBuilder;
import com.lesconstructionssapete.stempyerp.repository.UserRepository;

public class UserRepositoryImpl implements UserRepository {

  @Override
  public List<User> fetch(Connection connection, DomainQuery query) {

    String sql = QueryCache.get(
        Query.AUTH_SELECT_USER);

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
