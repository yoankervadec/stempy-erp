package com.lesconstructionssapete.stempyerp.controller;

import java.util.Map;

import com.lesconstructionssapete.stempyerp.domain.auth.AuthToken;
import com.lesconstructionssapete.stempyerp.domain.auth.UserCredential;
import com.lesconstructionssapete.stempyerp.facade.auth.AuthFacade;
import com.lesconstructionssapete.stempyerp.facade.auth.UserFacade;
import com.lesconstructionssapete.stempyerp.http.ApiRequestContext;
import com.lesconstructionssapete.stempyerp.http.BodyKey;
import com.lesconstructionssapete.stempyerp.http.RequestMapper;
import com.lesconstructionssapete.stempyerp.http.Response;
import com.lesconstructionssapete.stempyerp.http.contract.ApiRequest;

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

    AuthToken token = authFacade.refresh(refreshToken);

    Response.ok(ctx, null, token);
  }

}
