package com.lesconstructionssapete.stempyerp.app.routes;

import java.util.List;

import com.lesconstructionssapete.stempyerp.app.Dependencies;

import io.javalin.Javalin;

public class RouteRegistrar {

  public static void register(Javalin app, Dependencies deps) {

    List<RouteGroup> v1Routes = List.of(
        new RetailProductRoutesV1(deps.retailProductController),
        new AuthRoutesV1(deps.authController));

    v1Routes.forEach(routeGroup -> routeGroup.register(app));

  }
}
