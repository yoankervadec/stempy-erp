package com.lesconstructionssapete.stempyerp.app.facade.base.auth;

import java.time.Duration;
import java.time.LocalDateTime;

import com.lesconstructionssapete.stempyerp.core.config.db.TransactionManager;
import com.lesconstructionssapete.stempyerp.core.config.db.TransactionPropagation;
import com.lesconstructionssapete.stempyerp.core.jwt.JwtConfig;
import com.lesconstructionssapete.stempyerp.core.jwt.JwtUtil;
import com.lesconstructionssapete.stempyerp.core.repository.base.auth.RefreshTokenRepository;
import com.lesconstructionssapete.stempyerp.core.repository.base.auth.UserRepository;
import com.lesconstructionssapete.stempyerp.core.service.base.auth.AuthService;
import com.lesconstructionssapete.stempyerp.domain.base.auth.AuthToken;
import com.lesconstructionssapete.stempyerp.domain.base.auth.User;
import com.lesconstructionssapete.stempyerp.domain.base.auth.UserCredential;
import com.lesconstructionssapete.stempyerp.domain.shared.query.ComparisonOperator;
import com.lesconstructionssapete.stempyerp.domain.shared.query.DomainQuery;
import com.lesconstructionssapete.stempyerp.domain.shared.query.FilterCondition;

public class AuthFacadeImpl implements AuthFacade {

  private final RefreshTokenRepository refreshTokenRepository;
  private final UserRepository userRepository;
  private final AuthService authService;

  public AuthFacadeImpl(RefreshTokenRepository refreshTokenRepository, UserRepository userRepository,
      AuthService authService) {
    this.refreshTokenRepository = refreshTokenRepository;
    this.userRepository = userRepository;
    this.authService = authService;
  }

  @Override
  public boolean exists(String userNo, String refreshToken) {

    return TransactionManager.execute(
        TransactionPropagation.REQUIRED,
        con -> {
          return authService.refreshTokenExists(con, 0, refreshToken);
        });

  }

  @Override
  public AuthToken save(AuthToken token) {

    return TransactionManager.execute(
        TransactionPropagation.REQUIRED,
        con -> {
          refreshTokenRepository.save(con, token);
          return token;
        });
  }

  @Override
  public AuthToken login(UserCredential userCredential) {

    DomainQuery userQuery = new DomainQuery(
        new FilterCondition(
            "userId",
            ComparisonOperator.EQUALS,
            userCredential.getUserId()),
        null,
        null);

    DomainQuery credentialQuery = new DomainQuery(
        new FilterCondition(
            "userId",
            ComparisonOperator.EQUALS,
            userCredential.getUserId()),
        null,
        null);

    AuthToken authToken = TransactionManager.execute(
        TransactionPropagation.REQUIRED,
        con -> {
          var users = userRepository.fetch(con, userQuery);
          if (users.isEmpty()) {
            return null;
          }
          User u = users.get(0);

          var credentials = userRepository.fetchCredentials(con, credentialQuery);
          if (credentials.isEmpty()) {
            return null;
          }
          UserCredential c = credentials.get(0);

          if (!c.getPassword().equals(userCredential.getPassword())) {
            return null;
          }

          String accessToken = JwtUtil
              .generateAccessToken(u.getEntityNo());
          String refreshToken = JwtUtil
              .generateRefreshToken(u.getEntityNo());
          LocalDateTime refreshTokenExpiration = LocalDateTime.now()
              .plus(Duration.ofMillis(JwtConfig.REFRESH_TOKEN_EXPIRATION));

          AuthToken token = new AuthToken(
              null,
              u.getEntityId(),
              accessToken,
              refreshToken,
              refreshTokenExpiration,
              LocalDateTime.now());

          save(token);

          return token;
        });

    return authToken;
  }

  @Override
  public AuthToken refresh(String refreshToken) {
    // TODO Auto-generated method stub
    return null;
  }

}
