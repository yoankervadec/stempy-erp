
package com.lesconstructions.sapete.stempyerp.app;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.lesconstructions.sapete.stempyerp.app.middleware.JwtMiddleware;
import com.lesconstructions.sapete.stempyerp.app.middleware.UserContextMiddleware;
import com.lesconstructions.sapete.stempyerp.app.routes.RouteRegistrar;
import com.lesconstructions.sapete.stempyerp.core.config.db.ConnectionPool;
import com.lesconstructions.sapete.stempyerp.core.shared.constant.ConstantCache;

import io.javalin.Javalin;
import io.javalin.json.JavalinJackson;

/**
 *
 * @author yoan-kervadec
 */
public class App {

  private static final Logger LOGGER = LoggerFactory.getLogger(App.class);

  @SuppressWarnings("CallToPrintStackTrace")
  public static void main(String[] args) throws SQLException {

    try (Connection con = ConnectionPool.getConnection()) {
      ConstantCache.loadAll(con);
    }

    Dependencies deps = new Dependencies();

    ObjectMapper mapper = new ObjectMapper();
    mapper.registerModule(new JavaTimeModule());
    mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

    // Pass the mapper + whether to pretty print (false = compact JSON)
    JavalinJackson jackson = new JavalinJackson(mapper, true);

    Javalin app = Javalin.create(config -> {
      config.bundledPlugins.enableDevLogging();
      config.jsonMapper(jackson);
    }).start(7070);

    JwtMiddleware jwtMiddleware = new JwtMiddleware();
    UserContextMiddleware userContextMiddleware = new UserContextMiddleware(deps.userFacade);

    app.before("/api/*", ctx -> {
      if (ctx.path().equals("/api/login") || ctx.path().equals("/api/refresh")) {
        return; // skip JWT check
      }
      jwtMiddleware.handle(ctx);
      userContextMiddleware.handle(ctx);
    });

    // Exception handling
    app.exception(com.lesconstructions.sapete.stempyerp.core.exception.ApiException.class, (e, ctx) -> {
      // Log with stack trace for internal monitoring

      LOGGER.error("API Exception : {}", e.getMessage());

      ctx.status(e.getHttpStatus());
      ctx.json(Map.of(
          "errorCode", e.getErrorCode(),
          "message", e.getMessage()));
    });

    app.exception(com.lesconstructions.sapete.stempyerp.core.exception.InternalException.class, (e, ctx) -> {
      // Log with stack trace for internal monitoring
      e.printStackTrace();

      ctx.status(500);
      ctx.json(Map.of(
          "errorCode", "INTERNAL_SERVER_ERROR",
          "message", "An unexpected error occurred."));
    });

    // Fallback for any uncaught exception
    app.exception(Exception.class, (e, ctx) -> {
      e.printStackTrace();
      ctx.status(500);
      ctx.status(500).json(Map.of(
          "errorCode", "UNCAUGHT_EXCEPTION",
          "message", "Something went wrong."));
    });

    RouteRegistrar.register(app, deps);

  }
}
