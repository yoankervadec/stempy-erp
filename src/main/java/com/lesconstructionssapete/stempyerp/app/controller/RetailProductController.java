package com.lesconstructionssapete.stempyerp.app.controller;

import java.util.List;

import com.lesconstructionssapete.stempyerp.app.dto.base.RetailProductRequest;
import com.lesconstructionssapete.stempyerp.app.facade.base.retailproduct.RetailProductFacade;
import com.lesconstructionssapete.stempyerp.app.http.ApiRequest;
import com.lesconstructionssapete.stempyerp.app.http.ApiRequestMapper;
import com.lesconstructionssapete.stempyerp.app.http.Response;
import com.lesconstructionssapete.stempyerp.app.http.TypedApiRequest;
import com.lesconstructionssapete.stempyerp.app.mapper.base.RetailProductMapper;
import com.lesconstructionssapete.stempyerp.core.domain.base.retailproduct.RetailProduct;

import io.javalin.http.Context;

public class RetailProductController {

  private final RetailProductFacade retailProductFacade;

  public RetailProductController(RetailProductFacade retailProductService) {
    this.retailProductFacade = retailProductService;
  }

  public void fetchAllProducts(Context ctx) {
    List<RetailProduct> list = retailProductFacade.fetchAllProducts();

    Response.ok(ctx, null, list);

  }

  public void insertProduct(Context ctx) {

    ApiRequest request = ctx.attribute(ApiRequest.class.getName());

    TypedApiRequest<RetailProductRequest> typed = ApiRequestMapper.map(request, RetailProductRequest.class);

    RetailProductRequest productRequest = typed.getBody();

    RetailProduct result = retailProductFacade.insert(RetailProductMapper.toDomain(productRequest));

    Response.created(ctx, "Product created", result);

  }

}
