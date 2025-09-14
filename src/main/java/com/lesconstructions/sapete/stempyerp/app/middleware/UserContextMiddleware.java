package com.lesconstructions.sapete.stempyerp.app.middleware;

import java.util.Map;

import com.lesconstructions.sapete.stempyerp.app.facade.base.user.UserFacade;
import com.lesconstructions.sapete.stempyerp.core.domain.base.user.User;

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

    if (userNo != null) {
      User user = userFacade.findByUserNo(userNo);
      if (user != null) {
        ctx.attribute("user", user);
      } else {
        ctx.status(401).json(Map.of(
            "message", "Invalid or expired token",
            "errorCode", "UNAUTHORIZED"));
      }
    }

  }

}
