package com.lesconstructionssapete.stempyerp.controller;

import java.util.List;

import com.lesconstructionssapete.stempyerp.domain.retailproduct.RetailProduct;
import com.lesconstructionssapete.stempyerp.dto.RetailProductRequest;
import com.lesconstructionssapete.stempyerp.facade.retailproduct.RetailProductFacade;
import com.lesconstructionssapete.stempyerp.http.ApiRequestContext;
import com.lesconstructionssapete.stempyerp.http.BodyKey;
import com.lesconstructionssapete.stempyerp.http.RequestMapper;
import com.lesconstructionssapete.stempyerp.http.Response;
import com.lesconstructionssapete.stempyerp.http.contract.ApiRequest;
import com.lesconstructionssapete.stempyerp.http.query.RequestQueryMapper;
import com.lesconstructionssapete.stempyerp.mapper.RetailProductMapper;

import io.javalin.http.Context;

public class RetailProductController {

  private final RetailProductFacade retailProductFacade;
  private final RequestQueryMapper requestQueryMapper;

  public RetailProductController(RequestQueryMapper requestQueryMapper, RetailProductFacade retailProductService) {
    this.requestQueryMapper = requestQueryMapper;
    this.retailProductFacade = retailProductService;
  }

  public void fetch(Context ctx) {

    ApiRequest request = ApiRequestContext.get(ctx);

    var query = requestQueryMapper.map(request.getQuery());

    List<RetailProduct> list = retailProductFacade.fetch(query);

    Response.ok(ctx, null, list);

  }

  public void insert(Context ctx) {

    ApiRequest request = ApiRequestContext.get(ctx);

    var payload = RequestMapper.map(
        request.getBody(), RetailProductRequest.class, BodyKey.PAYLOAD);

    RetailProduct result = retailProductFacade.insert(
        request.getContext().getUser(),
        RetailProductMapper.toDomain(payload));

    Response.created(ctx, "Product created", result);

  }

}
