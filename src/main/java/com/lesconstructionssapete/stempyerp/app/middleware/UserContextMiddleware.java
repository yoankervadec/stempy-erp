package com.lesconstructionssapete.stempyerp.app.middleware;

import com.lesconstructionssapete.stempyerp.app.facade.base.user.UserFacade;
import com.lesconstructionssapete.stempyerp.core.domain.base.user.User;
import com.lesconstructionssapete.stempyerp.core.exception.api.UnauthorizedException;

import io.javalin.http.Context;
import io.javalin.http.Handler;

public class UserContextMiddleware implements Handler {

  private final UserFacade userFacade;

  public UserContextMiddleware(UserFacade userFacade) {
    this.userFacade = userFacade;
  }

  @Override
  public void handle(Context ctx) throws Exception {
    String userNo = ctx.attribute("userNo");

    if (userNo == null) {
      throw new UnauthorizedException(null, null);
    }

    User user = userFacade.findByUserNo(userNo);
    if (user == null) {
      throw new UnauthorizedException(null, null);
    }

    ctx.attribute("user", user);

  }

}
