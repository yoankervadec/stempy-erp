package com.lesconstructions.sapete.stempyerp.app.routes;

import com.lesconstructions.sapete.stempyerp.app.controller.RetailProductController;

import io.javalin.Javalin;

public class RetailProductRoute {

  private final RetailProductController retailProductController;

  public RetailProductRoute(RetailProductController retailProductController) {
    this.retailProductController = retailProductController;
  }

  public void register(Javalin app) {
    app.get("/api/retail-products", retailProductController::fetchAllProducts);
    app.post("/api/retail-products", retailProductController::insertProduct);
  }
}
