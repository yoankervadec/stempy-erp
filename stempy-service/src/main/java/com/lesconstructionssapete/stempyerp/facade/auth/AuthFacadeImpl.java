package com.lesconstructionssapete.stempyerp.facade.auth;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

import com.lesconstructionssapete.stempyerp.domain.auth.AuthToken;
import com.lesconstructionssapete.stempyerp.domain.auth.User;
import com.lesconstructionssapete.stempyerp.domain.auth.UserCredential;
import com.lesconstructionssapete.stempyerp.domain.shared.query.ComparisonOperator;
import com.lesconstructionssapete.stempyerp.domain.shared.query.DomainQuery;
import com.lesconstructionssapete.stempyerp.domain.shared.query.FilterCondition;
import com.lesconstructionssapete.stempyerp.domain.shared.query.FilterGroup;
import com.lesconstructionssapete.stempyerp.domain.shared.query.LogicalOperator;
import com.lesconstructionssapete.stempyerp.exception.InvalidRefreshTokenException;
import com.lesconstructionssapete.stempyerp.exception.RefreshTokenRevokedException;
import com.lesconstructionssapete.stempyerp.exception.UserNotFoundException;
import com.lesconstructionssapete.stempyerp.repository.RefreshTokenRepository;
import com.lesconstructionssapete.stempyerp.security.TokenProvider;
import com.lesconstructionssapete.stempyerp.service.auth.AuthService;
import com.lesconstructionssapete.stempyerp.transaction.TransactionPropagation;
import com.lesconstructionssapete.stempyerp.transaction.TransactionRunner;

public class AuthFacadeImpl implements AuthFacade {

  private final TransactionRunner transaction;
  private final RefreshTokenRepository refreshTokenRepository;
  private final TokenProvider tokenProvider;
  private final UserFacade userFacade;
  private final AuthService authService;

  public AuthFacadeImpl(
      TransactionRunner transaction,
      RefreshTokenRepository refreshTokenRepository,
      TokenProvider tokenProvider,
      UserFacade userFacade,
      AuthService authService) {
    this.transaction = transaction;
    this.refreshTokenRepository = refreshTokenRepository;
    this.tokenProvider = tokenProvider;
    this.userFacade = userFacade;
    this.authService = authService;
  }

  @Override
  public AuthToken save(AuthToken token) {

    return transaction.execute(
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

    AuthToken authToken = transaction.execute(
        TransactionPropagation.REQUIRED,
        con -> {
          var users = userFacade.fetch(userQuery);
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

          var credentials = authService.fetchUserCredentials(con, credentialQuery);
          if (credentials.isEmpty()) {
            return null;
          }
          UserCredential c = credentials.get(0);

          if (!c.getPassword().equals(userCredential.getPassword())) {
            return null;
          }

          String accessToken = tokenProvider
              .generateAccessToken(u.getEntityNo());
          String refreshToken = tokenProvider
              .generateRefreshToken(u.getEntityNo());
          LocalDateTime refreshTokenExpiration = LocalDateTime.now()
              .plus(Duration.ofMillis(tokenProvider.getRefreshTokenExpirationMillis()));

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

    if (refreshToken == null || refreshToken.isEmpty()) {
      throw new InvalidRefreshTokenException("Refresh token is required.");
    }

    String userNo;

    try {
      userNo = tokenProvider.validateRefreshTokenAndGetUserNo(refreshToken);
    } catch (Exception e) {
      throw new InvalidRefreshTokenException("Invalid refresh token.");
    }

    DomainQuery userQuery = new DomainQuery(
        new FilterCondition(
            "userNo",
            ComparisonOperator.EQUALS,
            userNo),
        null,
        null);

    AuthToken token = transaction.execute(
        TransactionPropagation.REQUIRED,
        con -> {

          List<User> users = userFacade.fetch(userQuery);

          if (users.isEmpty()) {
            throw new UserNotFoundException("The user associated with the refresh token was not found.");
          }

          User user = users.get(0);

          if (!authService.refreshTokenExists(con, user.getEntityId(), refreshToken)) {
            throw new RefreshTokenRevokedException("This refresh token has been revoked.");
          }

          String newAccessToken = tokenProvider.generateAccessToken(user.getEntityNo());

          return new AuthToken(
              null,
              null,
              newAccessToken,
              null,
              null,
              null);
        });

    return token;
  }

}
