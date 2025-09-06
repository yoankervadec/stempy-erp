
package com.lesconstructions.sapete.stempyerp.app;

import java.sql.Connection;
import java.sql.SQLException;

import com.lesconstructions.sapete.stempyerp.app.middleware.JwtMiddleware;
import com.lesconstructions.sapete.stempyerp.app.routes.RouteRegistrar;
import com.lesconstructions.sapete.stempyerp.core.config.db.ConnectionPool;
import com.lesconstructions.sapete.stempyerp.core.shared.constant.ConstantCache;

import io.javalin.Javalin;

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

    Javalin app = Javalin.create(config -> {
      config.bundledPlugins.enableDevLogging();
    }).start(7070);

    JwtMiddleware jwtMiddleware = new JwtMiddleware();

    app.before("/api/*", ctx -> {
      if (ctx.path().equals("/api/login") || ctx.path().equals("/api/refresh")) {
        return; // skip JWT check
      }
      jwtMiddleware.handle(ctx);
    });

    RouteRegistrar.register(app, deps);

  }
}
