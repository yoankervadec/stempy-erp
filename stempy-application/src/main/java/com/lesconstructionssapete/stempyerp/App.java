
package com.lesconstructionssapete.stempyerp;

import com.lesconstructionssapete.stempyerp.bootstrap.AutomationInitializer;
import com.lesconstructionssapete.stempyerp.bootstrap.ConstantCacheInitializer;
import com.lesconstructionssapete.stempyerp.bootstrap.Dependencies;
import com.lesconstructionssapete.stempyerp.config.ExceptionConfig;
import com.lesconstructionssapete.stempyerp.config.JsonConfig;
import com.lesconstructionssapete.stempyerp.config.MiddlewareConfig;
import com.lesconstructionssapete.stempyerp.routes.RouteRegistrar;

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

    ConstantCacheInitializer.initialize(deps);
    AutomationInitializer.initialize(deps.connectionProvider);

    // Setup Javalin
    Javalin app = Javalin.create(config -> {
      config.bundledPlugins.enableDevLogging();
      config.jsonMapper(JsonConfig.get());
    }).start(APP_PORT);

    // Middleware
    MiddlewareConfig.configure(app, deps.userFacade, deps.tokenProvider);

    // Exception handling
    ExceptionConfig.configure(app);

    // Routes
    RouteRegistrar.register(app, deps.retailProductController, deps.authController);

  }
}
