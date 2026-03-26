package com.lesconstructionssapete.stempyerp.service.auth;

import java.sql.Connection;

import com.lesconstructionssapete.stempyerp.domain.auth.AuthToken;

public interface AuthService {

  boolean isValidRefreshToken(Connection connection, long userId, String refreshToken);

  long insert(Connection connection, AuthToken token);

  boolean isValidCredential(Connection connection, long userId, String password);

}
