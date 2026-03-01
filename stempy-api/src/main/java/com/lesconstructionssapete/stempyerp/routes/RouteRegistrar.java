package com.lesconstructionssapete.stempyerp.routes;

import java.util.List;

import com.lesconstructionssapete.stempyerp.controller.AuthController;
import com.lesconstructionssapete.stempyerp.controller.RetailProductController;

import io.javalin.Javalin;

public class RouteRegistrar {

  public static void register(Javalin app, RetailProductController retailProductController,
      AuthController authController) {

    List<RouteGroup> v1Routes = List.of(
        new RetailProductRoutesV1(retailProductController),
        new AuthRoutesV1(authController));

    v1Routes.forEach(routeGroup -> routeGroup.register(app));

  }
}
