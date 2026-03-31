package com.lesconstructionssapete.stempyerp.facade.auth;

import java.time.Instant;
import java.util.List;

import com.lesconstructionssapete.stempyerp.domain.auth.AuthToken;
import com.lesconstructionssapete.stempyerp.domain.auth.User;
import com.lesconstructionssapete.stempyerp.domain.auth.UserCredential;
import com.lesconstructionssapete.stempyerp.domain.shared.query.DomainQuery;
import com.lesconstructionssapete.stempyerp.exception.InvalidCredentialsException;
import com.lesconstructionssapete.stempyerp.exception.InvalidRefreshTokenException;
import com.lesconstructionssapete.stempyerp.exception.RefreshTokenRevokedException;
import com.lesconstructionssapete.stempyerp.exception.UserNotFoundException;
import com.lesconstructionssapete.stempyerp.facade.user.UserFacade;
import com.lesconstructionssapete.stempyerp.field.user.UserField;
import com.lesconstructionssapete.stempyerp.security.TokenProvider;
import com.lesconstructionssapete.stempyerp.service.auth.AuthService;
import com.lesconstructionssapete.stempyerp.transaction.TransactionPropagation;
import com.lesconstructionssapete.stempyerp.transaction.TransactionRunner;

public class AuthFacadeImpl implements AuthFacade {

  private final TransactionRunner transaction;
  private final TokenProvider tokenProvider;
  private final UserFacade userFacade;
  private final AuthService authService;

  public AuthFacadeImpl(
      TransactionRunner transaction,
      TokenProvider tokenProvider,
      UserFacade userFacade,
      AuthService authService) {
    this.transaction = transaction;
    this.tokenProvider = tokenProvider;
    this.userFacade = userFacade;
    this.authService = authService;
  }

  @Override
  public AuthToken login(UserCredential userCredential) {

    DomainQuery q = DomainQuery.builder()
        .where(w -> w.and(
            c -> c.equals(UserField.USER_NO, userCredential.getUserNo()),
            c -> c.equals(UserField.ENABLED, true)))
        .build();

    AuthToken authToken = transaction.execute(
        TransactionPropagation.REQUIRED,
        con -> {

          // Fetch the user based on the provided user number and ensure they are enabled
          var users = userFacade.fetch(q);
          if (users.isEmpty()) {
            throw new UserNotFoundException("No active user found with the provided user number.");
          }
          User u = users.get(0);

          // Validate the provided password against the stored credentials for the user
          if (!authService.isValidCredential(con, u.getEntityId(), userCredential.getPassword())) {
            throw new InvalidCredentialsException("Invalid user number or password.");
          }

          // Generate a new access token and refresh token for the authenticated user
          String accessToken = tokenProvider
              .generateAccessToken(u.getEntityNo());
          String refreshToken = tokenProvider
              .generateRefreshToken(u.getEntityNo());
          Instant refreshTokenExpiration = Instant.now()
              .plusMillis(tokenProvider.getRefreshTokenExpirationMillis());

          AuthToken token = new AuthToken(
              null,
              u.getEntityId(),
              accessToken,
              refreshToken,
              refreshTokenExpiration,
              true,
              Instant.now());

          // Store the refresh token in the database to allow for future validation and
          // potential revocation
          authService.insert(con, token);

          return token;
        });

    return authToken;
  }

  @Override
  public AuthToken refresh(String refreshToken) {

    if (refreshToken == null || refreshToken.isEmpty()) {
      throw new InvalidRefreshTokenException("Refresh token is required.");
    }

    // Validate the refresh token and extract the associated user number. If the
    // token is invalid, an exception will be thrown.
    String userNo;
    try {
      userNo = tokenProvider.validateRefreshTokenAndGetUserNo(refreshToken);
    } catch (Exception e) {
      throw new InvalidRefreshTokenException("Invalid refresh token.");
    }

    DomainQuery q = DomainQuery.builder()
        .where(w -> w.and(
            c -> c.equals(UserField.USER_NO, userNo),
            c -> c.equals(UserField.ENABLED, true)))
        .build();

    AuthToken token = transaction.execute(
        TransactionPropagation.REQUIRED,
        con -> {

          // Fetch the user associated with the refresh token and ensure they are enabled.
          // If no user is found, an exception will be thrown.
          List<User> users = userFacade.fetch(q);
          if (users.isEmpty()) {
            throw new UserNotFoundException("The user associated with the refresh token was not found.");
          }
          User user = users.get(0);

          // Check if the provided refresh token has been revoked for the user. If it has
          // been revoked, an exception will be thrown.
          if (!authService.isValidRefreshToken(con, user.getEntityId(), refreshToken)) {
            throw new RefreshTokenRevokedException("This refresh token has been revoked.");
          }

          // Generate a new access token for the user. The refresh token remains the same.
          String newAccessToken = tokenProvider.generateAccessToken(user.getEntityNo());

          return new AuthToken(
              null,
              null,
              newAccessToken,
              null,
              null,
              true,
              null);
        });

    return token;
  }

}
