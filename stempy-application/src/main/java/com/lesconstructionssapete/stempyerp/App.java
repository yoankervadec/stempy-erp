
package com.lesconstructionssapete.stempyerp;

import com.lesconstructionssapete.stempyerp.bootstrap.AutomationInitializer;
import com.lesconstructionssapete.stempyerp.bootstrap.ConstantCacheInitializer;
import com.lesconstructionssapete.stempyerp.bootstrap.Container;
import com.lesconstructionssapete.stempyerp.bootstrap.Dependencies;
import com.lesconstructionssapete.stempyerp.config.ExceptionConfig;
import com.lesconstructionssapete.stempyerp.config.JsonConfig;
import com.lesconstructionssapete.stempyerp.config.MiddlewareConfig;
import com.lesconstructionssapete.stempyerp.constant.ConstantCache;
import com.lesconstructionssapete.stempyerp.controller.AuthController;
import com.lesconstructionssapete.stempyerp.controller.RetailProductController;
import com.lesconstructionssapete.stempyerp.db.ConnectionProvider;
import com.lesconstructionssapete.stempyerp.facade.spi.user.UserFacade;
import com.lesconstructionssapete.stempyerp.routes.RouteRegistrar;
import com.lesconstructionssapete.stempyerp.security.TokenProvider;

import io.github.cdimascio.dotenv.Dotenv;
import io.javalin.Javalin;

/**
 *
 * @author yoan-kervadec
 */
public class App {

  public static void main(String[] args) {

    // Load environment variables
    Dotenv dotenv = Dotenv.load();
    int APP_PORT = Integer.parseInt(dotenv.get("APP_PORT", "7070"));

    // Dependencies container
    Dependencies deps = new Dependencies();
    Container container = deps.container();

    ConstantCacheInitializer.initialize(container.get(ConstantCache.class));
    AutomationInitializer.initialize(container.get(ConnectionProvider.class));

    // Setup Javalin
    Javalin app = Javalin.create(config -> {
      config.bundledPlugins.enableDevLogging();
      config.jsonMapper(JsonConfig.get());
    }).start(APP_PORT);

    // Middleware
    MiddlewareConfig.configure(app, container.get(UserFacade.class), container.get(TokenProvider.class));

    // Exception handling
    ExceptionConfig.configure(app);

    // Routes
    RouteRegistrar.register(app, container.get(RetailProductController.class), container.get(AuthController.class));

  }
}
