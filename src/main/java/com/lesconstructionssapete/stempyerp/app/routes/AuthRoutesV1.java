package com.lesconstructionssapete.stempyerp.app.routes;

import com.lesconstructionssapete.stempyerp.app.controller.AuthController;

import io.javalin.Javalin;

public class AuthRoutesV1 implements RouteGroup {

  private final AuthController authController;

  private final static String BASE_PATH = Routes.api(ApiVersion.V1.value(), "/auth");

  public AuthRoutesV1(AuthController authController) {
    this.authController = authController;
  }

  @Override
  public void register(Javalin app) {

    Routes.post(app, BASE_PATH + "/login", authController::login);
    Routes.post(app, BASE_PATH + "/refresh", authController::refresh);

  }

}
