
package com.lesconstructions.sapete.stempyerp.app;

import java.sql.Connection;
import java.sql.SQLException;

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

  public static void main(String[] args) throws SQLException {

    try (Connection con = ConnectionPool.getConnection()) {
      ConstantCache.loadAll(con);
    }

    Dependencies deps = new Dependencies();

    ObjectMapper mapper = new ObjectMapper();
    mapper.registerModule(new JavaTimeModule());
    mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

    // pass the mapper + whether to pretty print (false = compact JSON)
    JavalinJackson jackson = new JavalinJackson(mapper, false);

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

    RouteRegistrar.register(app, deps);

  }
}
