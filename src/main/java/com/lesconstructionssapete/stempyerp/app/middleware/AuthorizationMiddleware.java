package com.lesconstructionssapete.stempyerp.app.middleware;

import com.lesconstructionssapete.stempyerp.app.facade.base.user.UserFacade;
import com.lesconstructionssapete.stempyerp.app.http.ApiRequest;
import com.lesconstructionssapete.stempyerp.core.domain.base.user.User;

import io.javalin.http.Context;
import io.javalin.http.Handler;

public class AuthorizationMiddleware implements Handler {

  private final UserFacade userFacade;

  public AuthorizationMiddleware(UserFacade userFacade) {
    this.userFacade = userFacade;
  }

  @Override
  public void handle(Context ctx) {

    String userNo = ctx.attribute("AUTHENTICATED_USER_NO");
    if (userNo == null) {
      throw new RuntimeException("Missing authenticated user number during authorization");
    }

    User user = userFacade.findByUserNo(userNo);

    if (user == null) {
      throw new RuntimeException("User not found during authorization");
    }

    ApiRequest apiRequest = ctx.attribute(ApiRequest.class.getName());
    if (apiRequest == null) {
      throw new RuntimeException("Missing API request during authorization");
    } else {
      apiRequest.setContextUser(user);
      ctx.attribute(ApiRequest.class.getName(), apiRequest);
    }

    // Further authorization logic will be added later

  }

}
