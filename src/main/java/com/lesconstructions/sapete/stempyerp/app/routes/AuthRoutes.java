package com.lesconstructions.sapete.stempyerp.app.routes;

import com.lesconstructions.sapete.stempyerp.app.controller.AuthController;

import io.javalin.Javalin;

public class AuthRoutes {

  private final AuthController authController;

  public AuthRoutes(AuthController authController) {
    this.authController = authController;
  }

  public void register(Javalin app) {
    app.post("/api/login", authController::login);
  }

}
