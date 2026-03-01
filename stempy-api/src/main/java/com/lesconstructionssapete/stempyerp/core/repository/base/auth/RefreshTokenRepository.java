package com.lesconstructionssapete.stempyerp.core.repository.base.auth;

import java.sql.Connection;
import java.sql.SQLException;

import com.lesconstructionssapete.stempyerp.domain.base.auth.AuthToken;

public interface RefreshTokenRepository {

  long save(Connection connection, AuthToken token) throws SQLException;

  boolean exists(Connection connection, long userId, String refreshToken) throws SQLException;
}
