package com.lesconstructionssapete.stempyerp.core.repository.base.auth;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import com.lesconstructionssapete.stempyerp.core.domain.base.auth.AuthToken;
import com.lesconstructionssapete.stempyerp.core.shared.query.Query;
import com.lesconstructionssapete.stempyerp.core.shared.query.QueryCache;
import com.lesconstructionssapete.stempyerp.core.shared.query.SqlBuilder;

public class RefreshTokenRepositoryImpl implements RefreshTokenRepository {

  @Override
  public boolean exists(Connection connection, String userNo, String refreshToken) throws SQLException {

    String sqlBase = QueryCache.get(Query.SELECT_AUTH_REFRESH_TOKENS);

    SqlBuilder builder = new SqlBuilder(sqlBase)
        .where("us.user_no = :userNo", userNo)
        .and("ort.token = :refreshToken", refreshToken);

    String sqlString = builder.build();
    List<SqlBuilder.SqlParam> params = builder.getParams();

    try (var stmt = connection.prepareStatement(sqlString)) {

      int idx = 1;
      for (SqlBuilder.SqlParam p : params) {
        stmt.setObject(idx++, p.value(), p.sqlType());
      }

      try (var rs = stmt.executeQuery()) {

        return rs.next();
      }

    }
  }

  @Override
  public void save(Connection connection, AuthToken token) throws SQLException {

    String sqlString = QueryCache.get(Query.INSERT_AUTH_REFRESH_TOKEN);

    try (var stmt = connection.prepareStatement(sqlString)) {

      stmt.setLong(1, token.getUserSeq());
      stmt.setString(2, token.getRefreshToken());
      stmt.setObject(3, token.getExpiresAt());

      int rows = stmt.executeUpdate();
      if (rows == 0) {
        throw new SQLException("Insert failed, no rows affected.");
      }
    }

  }

}
