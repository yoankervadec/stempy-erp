package com.lesconstructionssapete.stempyerp.facade.base.auth;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

import com.lesconstructionssapete.stempyerp.config.db.TransactionManager;
import com.lesconstructionssapete.stempyerp.config.db.TransactionPropagation;
import com.lesconstructionssapete.stempyerp.domain.base.auth.AuthToken;
import com.lesconstructionssapete.stempyerp.domain.base.auth.User;
import com.lesconstructionssapete.stempyerp.domain.base.auth.UserCredential;
import com.lesconstructionssapete.stempyerp.domain.shared.query.ComparisonOperator;
import com.lesconstructionssapete.stempyerp.domain.shared.query.DomainQuery;
import com.lesconstructionssapete.stempyerp.domain.shared.query.FilterCondition;
import com.lesconstructionssapete.stempyerp.domain.shared.query.FilterGroup;
import com.lesconstructionssapete.stempyerp.domain.shared.query.LogicalOperator;
import com.lesconstructionssapete.stempyerp.repository.RefreshTokenRepository;
import com.lesconstructionssapete.stempyerp.repository.UserRepository;
import com.lesconstructionssapete.stempyerp.security.JwtConfig;
import com.lesconstructionssapete.stempyerp.security.JwtUtil;
import com.lesconstructionssapete.stempyerp.service.base.auth.AuthService;

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
            "userNo",
            ComparisonOperator.EQUALS,
            userCredential.getUserNo()),
        null,
        null);

    //

    AuthToken authToken = TransactionManager.execute(
        TransactionPropagation.REQUIRED,
        con -> {
          var users = userRepository.fetch(con, userQuery);
          if (users.isEmpty()) {
            throw new RuntimeException("User not found!");
          }
          User u = users.get(0);

          DomainQuery credentialQuery = new DomainQuery(
              new FilterGroup(
                  LogicalOperator.AND,
                  List.of(
                      new FilterCondition("userId", ComparisonOperator.EQUALS, u.getEntityId()),
                      new FilterCondition("password", ComparisonOperator.EQUALS, userCredential.getPassword()))),
              null,
              null);

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
