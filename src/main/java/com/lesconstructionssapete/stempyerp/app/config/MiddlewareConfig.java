package com.lesconstructionssapete.stempyerp.app.config;

import com.lesconstructionssapete.stempyerp.app.Dependencies;
import com.lesconstructionssapete.stempyerp.app.middleware.ApiRequestMiddleware;
import com.lesconstructionssapete.stempyerp.app.middleware.AuthorizationMiddleware;
import com.lesconstructionssapete.stempyerp.app.middleware.JwtAuthenticationMiddleware;
import com.lesconstructionssapete.stempyerp.app.middleware.RequestOptionsMiddleware;
import com.lesconstructionssapete.stempyerp.app.middleware.ServerContextMiddleware;

import io.javalin.Javalin;

public final class MiddlewareConfig {

  public static void configure(Javalin app, Dependencies deps) {

    JwtAuthenticationMiddleware jwtAuthMiddleware = new JwtAuthenticationMiddleware();
    ApiRequestMiddleware apiRequestMiddleware = new ApiRequestMiddleware();
    ServerContextMiddleware serverContextMiddleware = new ServerContextMiddleware();
    RequestOptionsMiddleware requestOptionsMiddleware = new RequestOptionsMiddleware();
    AuthorizationMiddleware authorizationMiddleware = new AuthorizationMiddleware(deps.userFacade);

    app.before("/api/*", ctx -> {
      if (ctx.path().equals("/api/v1/auth/login") ||
          ctx.path().equals("/api/v1/auth/refresh")) {

        // Skip authentication and authorization for login and refresh endpoints

        // Build ApiRequest with metadata
        apiRequestMiddleware.handle(ctx);
        serverContextMiddleware.handle(ctx);
        requestOptionsMiddleware.handle(ctx);

        return;
      }

      // JWT Authentication
      jwtAuthMiddleware.handle(ctx);

      // Build ApiRequest with metadata
      apiRequestMiddleware.handle(ctx);
      serverContextMiddleware.handle(ctx);
      requestOptionsMiddleware.handle(ctx);

      // Authorization
      authorizationMiddleware.handle(ctx);

    });

  }
}
