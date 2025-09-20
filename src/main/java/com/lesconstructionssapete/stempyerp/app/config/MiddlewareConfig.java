package com.lesconstructionssapete.stempyerp.app.config;

import com.lesconstructionssapete.stempyerp.app.Dependencies;
import com.lesconstructionssapete.stempyerp.app.middleware.JwtMiddleware;
import com.lesconstructionssapete.stempyerp.app.middleware.UserContextMiddleware;

import io.javalin.Javalin;

public class MiddlewareConfig {

  public static void configure(Javalin app, Dependencies deps) {
    JwtMiddleware jwt = new JwtMiddleware();
    UserContextMiddleware userCtx = new UserContextMiddleware(deps.userFacade);

    app.before("/api/*", ctx -> {
      if (ctx.path().equals("/api/login") || ctx.path().equals("/api/refresh")) {
        return;
      }
      jwt.handle(ctx);
      userCtx.handle(ctx);
    });
  }
}
