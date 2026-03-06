package com.lesconstructionssapete.stempyerp.repository.auth;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import com.lesconstructionssapete.stempyerp.domain.auth.AuthToken;
import com.lesconstructionssapete.stempyerp.query.Query;
import com.lesconstructionssapete.stempyerp.query.QueryCache;
import com.lesconstructionssapete.stempyerp.query.SQLBuilder;
import com.lesconstructionssapete.stempyerp.repository.RefreshTokenRepository;

public class RefreshTokenRepositoryImpl implements RefreshTokenRepository {

  @Override
  public boolean exists(Connection connection, long userId, String refreshToken) throws SQLException {

    String sqlBase = QueryCache.get(Query.SELECT_AUTH_REFRESH_TOKENS);

    SQLBuilder builder = new SQLBuilder(sqlBase)
        .where("auth_refresh_token.user_id = :userId", userId)
        .and("auth_refresh_token.token = :refreshToken", refreshToken);

    String sqlString = builder.build();
    List<SQLBuilder.SQLParam> params = builder.getParams();

    try (var stmt = connection.prepareStatement(sqlString)) {

      int idx = 1;
      for (SQLBuilder.SQLParam p : params) {
        stmt.setObject(idx++, p.value(), p.sqlType());
      }

      try (var rs = stmt.executeQuery()) {

        return rs.next();
      }

    }
  }

  @Override
  public long save(Connection connection, AuthToken token) throws SQLException {

    String sqlString = QueryCache.get(Query.INSERT_AUTH_REFRESH_TOKEN);

    try (var stmt = connection.prepareStatement(sqlString, Statement.RETURN_GENERATED_KEYS)) {

      stmt.setLong(1, token.getUserId());
      stmt.setString(2, token.getRefreshToken());
      stmt.setObject(3, token.getRefreshTokenExpiresAt());

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
