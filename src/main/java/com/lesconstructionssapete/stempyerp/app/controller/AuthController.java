package com.lesconstructionssapete.stempyerp.app.controller;

import java.time.LocalDateTime;
import java.util.Map;

import com.lesconstructionssapete.stempyerp.app.facade.base.auth.AuthFacade;
import com.lesconstructionssapete.stempyerp.app.facade.base.user.UserFacade;
import com.lesconstructionssapete.stempyerp.app.http.ApiRequestContext;
import com.lesconstructionssapete.stempyerp.app.http.BodyKey;
import com.lesconstructionssapete.stempyerp.app.http.RequestMapper;
import com.lesconstructionssapete.stempyerp.app.http.Response;
import com.lesconstructionssapete.stempyerp.app.http.contract.ApiRequest;
import com.lesconstructionssapete.stempyerp.core.domain.base.auth.AuthToken;
import com.lesconstructionssapete.stempyerp.core.domain.base.user.User;
import com.lesconstructionssapete.stempyerp.core.domain.base.user.UserCredential;
import com.lesconstructionssapete.stempyerp.core.exception.ErrorCode;
import com.lesconstructionssapete.stempyerp.core.exception.api.UnauthorizedException;
import com.lesconstructionssapete.stempyerp.core.jwt.JwtConfig;
import com.lesconstructionssapete.stempyerp.core.jwt.JwtUtil;

import io.javalin.http.Context;

public class AuthController {

  private final UserFacade userFacade;
  private final AuthFacade authFacade;

  public AuthController(UserFacade userService, AuthFacade authService) {
    this.userFacade = userService;
    this.authFacade = authService;
  }

  public void login(Context ctx) {

    ApiRequest request = ApiRequestContext.get(ctx);

    var userCredential = RequestMapper.map(request.getBody(), UserCredential.class, BodyKey.PAYLOAD);

    User user = userFacade.validateCredentials(userCredential);
    if (user == null) {
      throw new UnauthorizedException(ErrorCode.UNAUTHORIZED.getCode(), "Invalid username or password.");
    }

    String accessToken = JwtUtil.generateAccessToken(user.getEntityNo(), user.getUserRole().getName());
    String refreshToken = JwtUtil.generateRefreshToken(user.getEntityNo());

    AuthToken token = new AuthToken(
        null,
        user.getEntitySeq(),
        user.getEntityNo(),
        accessToken,
        refreshToken,
        LocalDateTime.now().plus(java.time.Duration.ofMillis(JwtConfig.REFRESH_TOKEN_EXPIRATION)),
        LocalDateTime.now());

    authFacade.save(token);

    Response.ok(ctx, null, Map.of(
        "accessToken", accessToken,
        "refreshToken", refreshToken));

  }

  public void refresh(Context ctx) {
    String refreshToken = ctx.formParam("refreshToken");

    if (refreshToken == null || refreshToken.isEmpty()) {
      throw new UnauthorizedException(ErrorCode.UNAUTHORIZED.getCode(), "Refresh token is required.");
    }

    String userNo;
    try {
      userNo = JwtUtil.validateRefreshTokenAndGetUserNo(refreshToken);
    } catch (Exception e) {
      throw new UnauthorizedException(ErrorCode.UNAUTHORIZED.getCode(), "Invalid refresh token.");
    }

    if (!authFacade.exists(userNo, refreshToken)) {
      throw new UnauthorizedException(ErrorCode.UNAUTHORIZED.getCode(), "Refresh token revoked");
    }

    User user = userFacade.findByUserNo(userNo);
    String role = user.getUserRole().getName();

    String newAccessToken = JwtUtil.generateAccessToken(userNo, role);

    Response.ok(ctx, null, Map.of("accessToken", newAccessToken));
  }

}
