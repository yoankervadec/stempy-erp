package com.lesconstructionssapete.stempyerp.app.middleware;

import com.lesconstructionssapete.stempyerp.app.facade.base.user.UserFacade;
import com.lesconstructionssapete.stempyerp.app.http.ApiRequestContext;
import com.lesconstructionssapete.stempyerp.app.http.contract.ApiRequest;
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

    ApiRequest req = ApiRequestContext.get(ctx);

    String userNo = req.getContext().getUserNo();
    if (userNo == null) {
      throw new RuntimeException("Missing authenticated user number during authorization");
    }

    User user = userFacade.findByUserNo(userNo);

    if (user == null) {
      throw new RuntimeException("User not found during authorization");
    }

    req.setContextUser(user);

    // Further authorization logic will be added later

  }

}
