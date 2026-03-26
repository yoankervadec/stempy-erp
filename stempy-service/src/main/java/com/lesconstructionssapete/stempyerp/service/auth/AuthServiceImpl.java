package com.lesconstructionssapete.stempyerp.service.auth;

import java.sql.Connection;
import java.time.Instant;
import java.util.List;

import com.lesconstructionssapete.stempyerp.domain.auth.AuthToken;
import com.lesconstructionssapete.stempyerp.domain.auth.UserCredential;
import com.lesconstructionssapete.stempyerp.domain.shared.query.ComparisonOperator;
import com.lesconstructionssapete.stempyerp.domain.shared.query.DomainQuery;
import com.lesconstructionssapete.stempyerp.domain.shared.query.FilterCondition;
import com.lesconstructionssapete.stempyerp.domain.shared.query.FilterGroup;
import com.lesconstructionssapete.stempyerp.domain.shared.query.LogicalOperator;
import com.lesconstructionssapete.stempyerp.repository.RefreshTokenRepository;
import com.lesconstructionssapete.stempyerp.repository.auth.UserCredentialRepository;
import com.lesconstructionssapete.stempyerp.security.PasswordHashProvider;

public class AuthServiceImpl implements AuthService {

  private final RefreshTokenRepository refreshTokenRepository;
  private final UserCredentialRepository userCredentialRepository;
  private final PasswordHashProvider passwordHashProvider;

  public AuthServiceImpl(
      RefreshTokenRepository refreshTokenRepository,
      UserCredentialRepository userCredentialRepository,
      PasswordHashProvider passwordHashProvider) {
    this.refreshTokenRepository = refreshTokenRepository;
    this.userCredentialRepository = userCredentialRepository;
    this.passwordHashProvider = passwordHashProvider;
  }

  @Override
  public boolean isValidRefreshToken(Connection connection, long userId, String refreshToken) {

    DomainQuery query = new DomainQuery(
        new FilterGroup(
            LogicalOperator.AND,
            List.of(
                new FilterCondition("userId", ComparisonOperator.EQUALS, userId),
                new FilterCondition("token", ComparisonOperator.EQUALS, refreshToken),
                new FilterCondition("enabled", ComparisonOperator.EQUALS, true),
                new FilterCondition("expiresAt", ComparisonOperator.GREATER_THAN, Instant.now()))),
        null,
        null);

    List<AuthToken> tokens = refreshTokenRepository.fetch(connection, query);

    return !tokens.isEmpty();
  }

  @Override
  public long insert(Connection connection, AuthToken token) {

    return refreshTokenRepository.insert(connection, token);
  }

  @Override
  public boolean isValidCredential(Connection connection, long userId, String password) {

    DomainQuery query = new DomainQuery(
        new FilterGroup(
            LogicalOperator.AND,
            List.of(
                new FilterCondition("userId", ComparisonOperator.EQUALS, userId),
                new FilterCondition("enabled", ComparisonOperator.EQUALS, true))),
        null,
        null);

    List<UserCredential> credentials = userCredentialRepository.fetch(connection, query);

    if (credentials.isEmpty()) {
      return false;
    }

    String hash = credentials.get(0).getPassword();

    return passwordHashProvider.verifyPassword(password, hash);
  }

}
