package com.lesconstructionssapete.stempyerp.app.routes;

import com.lesconstructionssapete.stempyerp.app.Dependencies;

import io.javalin.Javalin;

public class RouteRegistrar {

  public static void register(Javalin app, Dependencies deps) {
    new RetailProductRoute(deps.retailProductController).register(app);
    new AuthRoutes(deps.authController).register(app);
  }
}
