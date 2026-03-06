package com.lesconstructionssapete.stempyerp.service.auth;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import com.lesconstructionssapete.stempyerp.domain.auth.AuthToken;
import com.lesconstructionssapete.stempyerp.domain.auth.UserCredential;
import com.lesconstructionssapete.stempyerp.domain.shared.query.DomainQuery;
import com.lesconstructionssapete.stempyerp.repository.RefreshTokenRepository;
import com.lesconstructionssapete.stempyerp.repository.UserRepository;

public class AuthServiceImpl implements AuthService {

  private final RefreshTokenRepository refreshTokenRepository;
  private final UserRepository userRepository;

  public AuthServiceImpl(RefreshTokenRepository refreshTokenRepository, UserRepository userRepository) {
    this.refreshTokenRepository = refreshTokenRepository;
    this.userRepository = userRepository;
  }

  @Override
  public boolean refreshTokenExists(Connection connection, long userId, String refreshToken) {

    try {
      return refreshTokenRepository.exists(connection, userId, refreshToken);
    } catch (SQLException e) {
      return false;
    }
  }

  @Override
  public long save(Connection connection, AuthToken token) {
    try {
      return refreshTokenRepository.save(connection, token);
    } catch (SQLException e) {
      return 0; // Placeholder return value
    }
  }

  @Override
  public boolean isValidCredential(Connection connection, long userId, String password) {
    // Implementation to validate user credentials
    return false; // Placeholder return value
  }

  @Override
  public List<UserCredential> fetchUserCredentials(Connection connection, DomainQuery query) {
    List<UserCredential> userCredentials = null;
    try {
      userCredentials = userRepository.fetchCredentials(connection, query);
    } catch (SQLException e) {
    }
    return userCredentials;
  }

}
