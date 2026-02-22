package com.lesconstructionssapete.stempyerp.app.controller;

import java.util.List;

import com.lesconstructionssapete.stempyerp.app.dto.base.RetailProductRequest;
import com.lesconstructionssapete.stempyerp.app.facade.base.retailproduct.RetailProductFacade;
import com.lesconstructionssapete.stempyerp.app.http.ApiRequestContext;
import com.lesconstructionssapete.stempyerp.app.http.BodyKey;
import com.lesconstructionssapete.stempyerp.app.http.RequestMapper;
import com.lesconstructionssapete.stempyerp.app.http.Response;
import com.lesconstructionssapete.stempyerp.app.http.contract.ApiRequest;
import com.lesconstructionssapete.stempyerp.app.http.query.RequestQueryMapper;
import com.lesconstructionssapete.stempyerp.app.mapper.base.RetailProductMapper;
import com.lesconstructionssapete.stempyerp.core.domain.base.retailproduct.RetailProduct;

import io.javalin.http.Context;

public class RetailProductController {

  private final RetailProductFacade retailProductFacade;

  public RetailProductController(RetailProductFacade retailProductService) {
    this.retailProductFacade = retailProductService;
  }

  public void fetch(Context ctx) {

    ApiRequest request = ApiRequestContext.get(ctx);

    var query = RequestQueryMapper.map(request.getQuery());

    List<RetailProduct> list = retailProductFacade.fetch(query);

    Response.ok(ctx, null, list);

  }

  public void insertProduct(Context ctx) {

    ApiRequest request = ApiRequestContext.get(ctx);

    var payload = RequestMapper.map(
        request.getBody(), RetailProductRequest.class, BodyKey.PAYLOAD);

    RetailProduct result = retailProductFacade.insert(
        RetailProductMapper.toDomain(payload));

    Response.created(ctx, "Product created", result);

  }

}
