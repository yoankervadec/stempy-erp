package com.lesconstructionssapete.stempyerp.app.config;

import com.lesconstructionssapete.stempyerp.app.Dependencies;
import com.lesconstructionssapete.stempyerp.app.middleware.ApiRequestMiddleware;
import com.lesconstructionssapete.stempyerp.app.middleware.AuthorizationMiddleware;
import com.lesconstructionssapete.stempyerp.app.middleware.JwtAuthenticationMiddleware;
import com.lesconstructionssapete.stempyerp.app.middleware.ServerContextMiddleware;

import io.javalin.Javalin;

public final class MiddlewareConfig {

  public static void configure(Javalin app, Dependencies deps) {

    JwtAuthenticationMiddleware jwtAuthMiddleware = new JwtAuthenticationMiddleware();
    ServerContextMiddleware serverContextMiddleware = new ServerContextMiddleware();
    ApiRequestMiddleware apiRequestMiddleware = new ApiRequestMiddleware();
    AuthorizationMiddleware authorizationMiddleware = new AuthorizationMiddleware(deps.userFacade);

    app.before("/api/*", ctx -> {
      if (ctx.path().equals("/api/v1/auth/login") ||
          ctx.path().equals("/api/v1/auth/refresh")) {

        // Skip authentication and authorization for login and refresh endpoints

        // Build ApiRequest with metadata
        serverContextMiddleware.handle(ctx);
        apiRequestMiddleware.handle(ctx);

        return;
      }

      // JWT Authentication
      jwtAuthMiddleware.handle(ctx);

      // Build ApiRequest with metadata
      serverContextMiddleware.handle(ctx);
      apiRequestMiddleware.handle(ctx);

      // Authorization
      authorizationMiddleware.handle(ctx);

    });

  }
}
