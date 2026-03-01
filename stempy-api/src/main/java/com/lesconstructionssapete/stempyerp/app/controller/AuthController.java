package com.lesconstructionssapete.stempyerp.app.controller;

import java.util.List;
import java.util.Map;

import com.lesconstructionssapete.stempyerp.app.http.ApiRequestContext;
import com.lesconstructionssapete.stempyerp.app.http.BodyKey;
import com.lesconstructionssapete.stempyerp.app.http.RequestMapper;
import com.lesconstructionssapete.stempyerp.app.http.Response;
import com.lesconstructionssapete.stempyerp.app.http.contract.ApiRequest;
import com.lesconstructionssapete.stempyerp.domain.base.auth.AuthToken;
import com.lesconstructionssapete.stempyerp.domain.base.auth.User;
import com.lesconstructionssapete.stempyerp.domain.base.auth.UserCredential;
import com.lesconstructionssapete.stempyerp.domain.shared.query.ComparisonOperator;
import com.lesconstructionssapete.stempyerp.domain.shared.query.DomainQuery;
import com.lesconstructionssapete.stempyerp.domain.shared.query.FilterCondition;
import com.lesconstructionssapete.stempyerp.exception.ErrorCode;
import com.lesconstructionssapete.stempyerp.exception.UnauthorizedException;
import com.lesconstructionssapete.stempyerp.facade.base.auth.AuthFacade;
import com.lesconstructionssapete.stempyerp.facade.base.auth.UserFacade;
import com.lesconstructionssapete.stempyerp.security.JwtUtil;

import io.javalin.http.Context;

public class AuthController {

  private final UserFacade userFacade;
  private final AuthFacade authFacade;

  public AuthController(UserFacade userFacade, AuthFacade authFacade) {
    this.userFacade = userFacade;
    this.authFacade = authFacade;
  }

  public void login(Context ctx) {

    ApiRequest request = ApiRequestContext.get(ctx);

    var userCredential = RequestMapper.map(request.getBody(), UserCredential.class, BodyKey.PAYLOAD);

    AuthToken token = authFacade.login(userCredential);

    Response.ok(ctx, null, Map.of(
        "accessToken", token.getToken(),
        "refreshToken", token.getRefreshToken()));

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

    DomainQuery userQuery = new DomainQuery(
        new FilterCondition(
            "userNo",
            ComparisonOperator.EQUALS,
            userNo),
        null,
        null);

    List<User> users = userFacade.fetch(userQuery);

    if (users.isEmpty()) {
      throw new UnauthorizedException(ErrorCode.UNAUTHORIZED.getCode(), "User not found.");
    }

    User user = users.get(0);

    String newAccessToken = JwtUtil.generateAccessToken(user.getEntityNo());

    Response.ok(ctx, null, Map.of("accessToken", newAccessToken));
  }

}
