package com.lesconstructions.sapete.stempyerp.app.controller;

import java.time.LocalDateTime;
import java.util.Map;

import com.lesconstructions.sapete.stempyerp.app.facade.base.auth.AuthFacade;
import com.lesconstructions.sapete.stempyerp.app.facade.base.user.UserFacade;
import com.lesconstructions.sapete.stempyerp.core.auth.JwtConfig;
import com.lesconstructions.sapete.stempyerp.core.auth.JwtUtil;
import com.lesconstructions.sapete.stempyerp.core.domain.base.auth.AuthToken;
import com.lesconstructions.sapete.stempyerp.core.domain.base.constant.UserRole;
import com.lesconstructions.sapete.stempyerp.core.domain.base.user.User;
import com.lesconstructions.sapete.stempyerp.core.domain.base.user.UserSimple;

import io.javalin.http.Context;

public class AuthController {

  private final UserFacade userFacade;
  private final AuthFacade authService;

  public AuthController(UserFacade userService, AuthFacade authService) {
    this.userFacade = userService;
    this.authService = authService;
  }

  public void login(Context ctx) {
    UserSimple req = ctx.bodyAsClass(UserSimple.class);

    User user = userFacade.validateCredentials(req.getUsernameLong(), req.getPassword());
    if (user == null) {
      ctx.status(401).result("Invalid credentials");
      return;
    }

    String accessToken = JwtUtil.generateAccessToken(user.getEntityNo(), user.getUserRole());
    String refreshToken = JwtUtil.generateRefreshToken(user.getEntityNo());

    AuthToken token = new AuthToken(
        null,
        user.getSequenceNo(),
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
      userNo = JwtUtil.validateTokenAndGetUserId(refreshToken);
    } catch (Exception e) {
      ctx.status(401).result("Invalid refresh token");
      return;
    }

    if (!authService.exists(userNo, refreshToken)) {
      ctx.status(401).result("Refresh token revoked");
      return;
    }

    String newAccessToken = JwtUtil.generateAccessToken(userNo, new UserRole(0, "role", true));
    ctx.json(Map.of("accessToken", newAccessToken));
  }

}
