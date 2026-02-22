package com.lesconstructionssapete.stempyerp.app.routes;

import com.lesconstructionssapete.stempyerp.app.controller.RetailProductController;

import io.javalin.Javalin;

public class RetailProductRoutesV1 implements RouteGroup {

  private final RetailProductController retailProductController;

  private final static String BASE_PATH = Routes.api(ApiVersion.V1.value(), "/retail-products");

  public RetailProductRoutesV1(RetailProductController retailProductController) {
    this.retailProductController = retailProductController;
  }

  @Override
  public void register(Javalin app) {

    Routes.get(app, BASE_PATH, retailProductController::fetch);
    Routes.post(app, BASE_PATH, retailProductController::insert);

  }

}
