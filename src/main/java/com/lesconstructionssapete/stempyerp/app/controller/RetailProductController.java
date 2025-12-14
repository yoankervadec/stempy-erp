package com.lesconstructionssapete.stempyerp.app.controller;

import java.util.List;

import com.lesconstructionssapete.stempyerp.app.dto.base.RetailProductRequest;
import com.lesconstructionssapete.stempyerp.app.facade.base.retailproduct.RetailProductFacade;
import com.lesconstructionssapete.stempyerp.app.mapper.base.RetailProductMapper;
import com.lesconstructionssapete.stempyerp.core.domain.base.retailproduct.RetailProduct;
import com.lesconstructionssapete.stempyerp.core.domain.base.user.User;
import com.lesconstructionssapete.stempyerp.core.http.ApiRequest;
import com.lesconstructionssapete.stempyerp.core.http.ApiResponse;
import com.lesconstructionssapete.stempyerp.core.http.RequestMapper;

import io.javalin.http.Context;

public class RetailProductController {

  private final RetailProductFacade retailProductFacade;

  public RetailProductController(RetailProductFacade retailProductService) {
    this.retailProductFacade = retailProductService;
  }

  public void fetchAllProducts(Context ctx) {
    List<RetailProduct> list = retailProductFacade.fetchAllProducts();
    ctx.json(ApiResponse.ofSuccess("Products fetched", list));
  }

  public void insertProduct(Context ctx) {

    ApiRequest<RetailProductRequest> request = RequestMapper.fromApiRequest(ctx, RetailProductRequest.class);

    User user = ctx.attribute("user");
    RetailProductRequest newProduct = request.getPayload();

    retailProductFacade.insert(RetailProductMapper.toDomain(newProduct));

    ctx.status(201).json(
        ApiResponse.ofSuccess("Product created", newProduct));
  }

}
