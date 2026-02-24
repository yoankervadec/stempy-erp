package com.lesconstructionssapete.stempyerp.app.middleware;

import com.lesconstructionssapete.stempyerp.app.facade.base.user.UserFacade;
import com.lesconstructionssapete.stempyerp.app.http.ApiRequestContext;
import com.lesconstructionssapete.stempyerp.app.http.contract.ApiRequest;
import com.lesconstructionssapete.stempyerp.core.domain.base.user.User;
import com.lesconstructionssapete.stempyerp.core.exception.api.AuthenticationException;
import com.lesconstructionssapete.stempyerp.core.exception.api.UserNotFoundException;

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
      throw new AuthenticationException(null, "No authenticated user found in the request context");
    }

    User user = userFacade.findByUserNo(userNo);

    if (user == null) {
      throw new UserNotFoundException(null, "Authenticated user not found in the system");
    }

    req.setContextUser(user);

    // Further authorization logic will be added later

  }

}
