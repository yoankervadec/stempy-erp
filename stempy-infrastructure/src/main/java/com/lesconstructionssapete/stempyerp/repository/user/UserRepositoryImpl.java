package com.lesconstructionssapete.stempyerp.repository.user;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.lesconstructionssapete.stempyerp.domain.auth.User;
import com.lesconstructionssapete.stempyerp.domain.auth.UserCredential;
import com.lesconstructionssapete.stempyerp.domain.shared.query.DomainQuery;
import com.lesconstructionssapete.stempyerp.field.user.UserField;
import com.lesconstructionssapete.stempyerp.query.DomainQuerySQLTranslator;
import com.lesconstructionssapete.stempyerp.query.Query;
import com.lesconstructionssapete.stempyerp.query.QueryCache;
import com.lesconstructionssapete.stempyerp.query.SQLBuilder;
import com.lesconstructionssapete.stempyerp.repository.UserRepository;

public class UserRepositoryImpl implements UserRepository {

  @Override
  public List<User> fetch(Connection connection, DomainQuery query) throws SQLException {
    List<User> users;

    String sql = QueryCache.get(
        Query.SELECT_USER);

    SQLBuilder builder = new SQLBuilder(sql);

    DomainQuerySQLTranslator translator = new DomainQuerySQLTranslator(UserField.all());

    translator.apply(builder, query);

    String sqlFinal = builder.build();
    List<SQLBuilder.SQLParam> params = builder.getParams();

    try (var stmt = connection.prepareStatement(sqlFinal)) {
      int idx = 1;
      for (SQLBuilder.SQLParam p : params) {
        stmt.setObject(idx++, p.value(), p.sqlType());
      }

      try (var rs = stmt.executeQuery()) {
        users = new ArrayList<>();
        while (rs.next()) {
          users.add(new User(
              rs.getLong("id"),
              rs.getString("user_no"),
              rs.getString("user_name"),
              rs.getBoolean("enabled"),
              rs.getTimestamp("created_at").toLocalDateTime(),
              rs.getLong("created_by_user_id"),
              rs.getTimestamp("updated_at").toLocalDateTime(),
              rs.getLong("updated_by_user_id")));
        }
      }
    }

    return users;
  }

  @Override
  public List<UserCredential> fetchCredentials(Connection connection, DomainQuery query) throws SQLException {

    List<UserCredential> credentials;

    String sql = QueryCache.get(
        Query.SELECT_USER_CREDENTIAL);

    SQLBuilder builder = new SQLBuilder(sql);

    DomainQuerySQLTranslator translator = new DomainQuerySQLTranslator(UserField.all());

    translator.apply(builder, query);

    String sqlFinal = builder.build();
    List<SQLBuilder.SQLParam> params = builder.getParams();

    try (var stmt = connection.prepareStatement(sqlFinal)) {
      int idx = 1;
      for (SQLBuilder.SQLParam p : params) {
        stmt.setObject(idx++, p.value(), p.sqlType());
      }

      try (var rs = stmt.executeQuery()) {
        credentials = new ArrayList<>();
        while (rs.next()) {
          credentials.add(new UserCredential(
              rs.getLong("id"),
              rs.getLong("user_id"),
              rs.getString("password"),
              rs.getBoolean("enabled"),
              rs.getTimestamp("created_at").toLocalDateTime()));
        }
      }
    }

    return credentials;
  }

}
