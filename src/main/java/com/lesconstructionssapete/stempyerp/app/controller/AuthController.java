package com.lesconstructionssapete.stempyerp.app.controller;

import java.time.LocalDateTime;
import java.util.Map;

import com.lesconstructionssapete.stempyerp.app.facade.base.auth.AuthFacade;
import com.lesconstructionssapete.stempyerp.app.facade.base.user.UserFacade;
import com.lesconstructionssapete.stempyerp.core.auth.JwtConfig;
import com.lesconstructionssapete.stempyerp.core.auth.JwtUtil;
import com.lesconstructionssapete.stempyerp.core.domain.base.auth.AuthToken;
import com.lesconstructionssapete.stempyerp.core.domain.base.user.User;
import com.lesconstructionssapete.stempyerp.core.domain.base.user.UserCredential;

import io.javalin.http.Context;

public class AuthController {

  private final UserFacade userFacade;
  private final AuthFacade authService;

  public AuthController(UserFacade userService, AuthFacade authService) {
    this.userFacade = userService;
    this.authService = authService;
  }

  public void login(Context ctx) {
    UserCredential userCredential = ctx.bodyAsClass(UserCredential.class);

    User user = userFacade.validateCredentials(userCredential);
    if (user == null) {
      ctx.status(401).result("Invalid credentials");
      return;
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

    authService.save(token);

    ctx.json(Map.of(
        "accessToken", accessToken,
        "refreshToken", refreshToken));
  }

  public void refresh(Context ctx) {
    String refreshToken = ctx.formParam("refreshToken");

    String userNo;
    try {
      userNo = JwtUtil.validateTokenAndGetUserNo(refreshToken);
    } catch (Exception e) {
      ctx.status(401).result("Invalid refresh token");
      return;
    }

    if (!authService.exists(userNo, refreshToken)) {
      ctx.status(401).result("Refresh token revoked");
      return;
    }

    User user = userFacade.findByUserNo(userNo);
    String role = user.getUserRole().getName();

    String newAccessToken = JwtUtil.generateAccessToken(userNo, role);

    ctx.json(Map.of("accessToken", newAccessToken));
  }

}
