
package com.lesconstructionssapete.stempyerp.app;

import com.lesconstructionssapete.stempyerp.app.bootstrap.AutomationInitializer;
import com.lesconstructionssapete.stempyerp.app.bootstrap.ConstantCacheInitializer;
import com.lesconstructionssapete.stempyerp.app.config.ExceptionConfig;
import com.lesconstructionssapete.stempyerp.app.config.JsonConfig;
import com.lesconstructionssapete.stempyerp.app.config.MiddlewareConfig;
import com.lesconstructionssapete.stempyerp.app.routes.RouteRegistrar;

import io.javalin.Javalin;

/**
 *
 * @author yoan-kervadec
 */
public class App {

  public static void main(String[] args) {

    ConstantCacheInitializer.initialize();
    AutomationInitializer.initialize();

    // Dependencies container
    Dependencies deps = new Dependencies();

    // Setup Javalin
    Javalin app = Javalin.create(config -> {
      config.bundledPlugins.enableDevLogging();
      config.jsonMapper(JsonConfig.get());
    }).start(7070);

    // Middleware
    MiddlewareConfig.configure(app, deps);

    // Exception handling
    ExceptionConfig.configure(app);

    // Routes
    RouteRegistrar.register(app, deps);

  }
}
