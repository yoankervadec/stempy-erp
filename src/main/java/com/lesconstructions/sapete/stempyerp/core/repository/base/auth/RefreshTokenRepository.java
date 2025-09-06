package com.lesconstructions.sapete.stempyerp.core.repository.base.auth;

import java.sql.Connection;
import java.sql.SQLException;

import com.lesconstructions.sapete.stempyerp.core.domain.base.auth.AuthToken;

public interface RefreshTokenRepository {

  void save(Connection connection, AuthToken token) throws SQLException;

  boolean exists(Connection connection, String userNo, String refreshToken) throws SQLException;
}
