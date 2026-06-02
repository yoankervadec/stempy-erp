package com.lesconstructionssapete.stempyerp.infrastructure.persistence.repository.user;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

import com.lesconstructionssapete.stempyerp.domain.field.user.UserField;
import com.lesconstructionssapete.stempyerp.domain.query.DomainQuery;
import com.lesconstructionssapete.stempyerp.domain.repository.UserRepository;
import com.lesconstructionssapete.stempyerp.domain.user.User;
import com.lesconstructionssapete.stempyerp.infrastructure.mapper.ResultSetMapper;
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

    DomainQuerySQLTranslator<UserField> translator = new DomainQuerySQLTranslator<>(UserField.class);

    translator.apply(builder, query);

    return SQLExecutor.query(
        connection,
        builder.build(),
        builder.getParams(),
        rs -> {
          List<User> list = new ArrayList<>();
          ResultSetMapper<User> rsMapper = new ResultSetMapper<>(User.class);
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
