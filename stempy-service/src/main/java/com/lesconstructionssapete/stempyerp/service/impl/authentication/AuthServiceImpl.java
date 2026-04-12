package com.lesconstructionssapete.stempyerp.service.impl.authentication;

import java.sql.Connection;
import java.time.Instant;
import java.util.List;

import com.lesconstructionssapete.stempyerp.auth.AuthToken;
import com.lesconstructionssapete.stempyerp.auth.UserCredential;
import com.lesconstructionssapete.stempyerp.field.auth.RefreshTokenField;
import com.lesconstructionssapete.stempyerp.field.auth.UserCredentialField;
import com.lesconstructionssapete.stempyerp.query.DomainQuery;
import com.lesconstructionssapete.stempyerp.repository.RefreshTokenRepository;
import com.lesconstructionssapete.stempyerp.repository.auth.UserCredentialRepository;
import com.lesconstructionssapete.stempyerp.security.PasswordHashProvider;
import com.lesconstructionssapete.stempyerp.service.spi.authentication.AuthService;

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

    DomainQuery q = DomainQuery.builder()
        .where(w -> w.and(
            c -> c.equals(RefreshTokenField.USER_ID, userId),
            c -> c.equals(RefreshTokenField.TOKEN, refreshToken),
            c -> c.equals(RefreshTokenField.ENABLED, true),
            c -> c.greaterThan(RefreshTokenField.EXPIRES_AT, Instant.now())))
        .build();

    List<AuthToken> tokens = refreshTokenRepository.fetch(connection, q);

    return !tokens.isEmpty();
  }

  @Override
  public long insert(Connection connection, AuthToken token) {

    return refreshTokenRepository.insert(connection, token);
  }

  @Override
  public boolean isValidCredential(Connection connection, long userId, String password) {

    DomainQuery q = DomainQuery.builder()
        .where(w -> w.and(
            c -> c.equals(UserCredentialField.USER_ID, userId),
            c -> c.equals(UserCredentialField.ENABLED, true)))
        .build();

    List<UserCredential> credentials = userCredentialRepository.fetch(connection, q);

    if (credentials.isEmpty()) {
      return false;
    }

    String hash = credentials.get(0).getPassword();

    return passwordHashProvider.verifyPassword(password, hash);
  }

}
