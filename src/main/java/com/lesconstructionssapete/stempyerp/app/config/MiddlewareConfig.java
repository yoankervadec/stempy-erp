package com.lesconstructionssapete.stempyerp.app.config;

import com.lesconstructionssapete.stempyerp.app.Dependencies;
import com.lesconstructionssapete.stempyerp.app.middleware.JwtMiddleware;
import com.lesconstructionssapete.stempyerp.app.middleware.RequestMetadataMiddleware;
import com.lesconstructionssapete.stempyerp.app.middleware.UserContextMiddleware;

import io.javalin.Javalin;

public class MiddlewareConfig {

  public static void configure(Javalin app, Dependencies deps) {
    JwtMiddleware jwt = new JwtMiddleware();
    UserContextMiddleware userCtx = new UserContextMiddleware(deps.userFacade);
    RequestMetadataMiddleware metadata = new RequestMetadataMiddleware();

    app.before("/api/*", ctx -> {
      if (ctx.path().equals("/api/v1/auth/login") || ctx.path().equals("/api/v1/auth/refresh")) {
        return;
      }
      jwt.handle(ctx);
      userCtx.handle(ctx);
      metadata.handle(ctx);
    });
  }
}
