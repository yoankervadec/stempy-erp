package com.lesconstructionssapete.stempyerp.core.service.base.auth;

import java.sql.Connection;

import com.lesconstructionssapete.stempyerp.core.domain.base.auth.AuthToken;

public interface AuthService {

  boolean refreshTokenExists(Connection connection, long userId, String refreshToken);

  long save(Connection connection, AuthToken token);

  boolean isValidCredential(Connection connection, long userId, String password);
}
