package com.lesconstructionssapete.stempyerp.config;

import com.lesconstructionssapete.stempyerp.facade.auth.UserFacade;
import com.lesconstructionssapete.stempyerp.middleware.ApiRequestMiddleware;
import com.lesconstructionssapete.stempyerp.middleware.AuthorizationMiddleware;
import com.lesconstructionssapete.stempyerp.middleware.JwtAuthenticationMiddleware;
import com.lesconstructionssapete.stempyerp.middleware.RequestOptionsMiddleware;
import com.lesconstructionssapete.stempyerp.middleware.RequestQueryMiddleware;
import com.lesconstructionssapete.stempyerp.middleware.ServerContextMiddleware;
import com.lesconstructionssapete.stempyerp.security.TokenProvider;

import io.javalin.Javalin;

public final class MiddlewareConfig {

  public static void configure(Javalin app, UserFacade userFacade, TokenProvider tokenProvider) {

    JwtAuthenticationMiddleware jwtAuthMiddleware = new JwtAuthenticationMiddleware(tokenProvider);
    ApiRequestMiddleware apiRequestMiddleware = new ApiRequestMiddleware();
    ServerContextMiddleware serverContextMiddleware = new ServerContextMiddleware();
    RequestOptionsMiddleware requestOptionsMiddleware = new RequestOptionsMiddleware();
    RequestQueryMiddleware requestQueryMiddleware = new RequestQueryMiddleware();
    AuthorizationMiddleware authorizationMiddleware = new AuthorizationMiddleware(userFacade);

    // Middleware runs only if route exists
    app.beforeMatched("/api/*", ctx -> {
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
      requestQueryMiddleware.handle(ctx);

      // Authorization
      authorizationMiddleware.handle(ctx);

    });

  }
}
