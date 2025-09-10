package com.lesconstructions.sapete.stempyerp.app.controller;

import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lesconstructions.sapete.stempyerp.app.dto.retailproduct.RetailProductDto;
import com.lesconstructions.sapete.stempyerp.app.facade.base.retailproduct.RetailProductFacade;
import com.lesconstructions.sapete.stempyerp.core.domain.base.retailproduct.RetailProduct;
import com.lesconstructions.sapete.stempyerp.core.http.ApiRequest;
import com.lesconstructions.sapete.stempyerp.core.shared.util.StringUtil;

import io.javalin.http.Context;

public class RetailProductController {

  private final RetailProductFacade retailProductService;

  public RetailProductController(RetailProductFacade retailProductService) {
    this.retailProductService = retailProductService;
  }

  public void fetchAllProducts(Context ctx) {
    List<RetailProduct> list = retailProductService.fetchAllProducts();
    ctx.json(list);
  }

  public void insertProduct(Context ctx) {
    try {
      ObjectMapper mapper = new ObjectMapper();
      // JsonNode node = mapper.readTree(ctx.body());

      // String productNo = node.get("productNo").asText();
      // BigDecimal retailPrice = new BigDecimal(node.get("retailPrice").asText());
      // BigDecimal cost = new BigDecimal(node.get("cost").asText());
      // String description = node.get("description").asText();
      // int retailCategory = node.get("retailCategory").asInt();
      // int woodSpecie = node.get("woodSpecie").asInt();
      // double productWidth = node.get("productWidth").asDouble();
      // double productThickness = node.get("productThickness").asDouble();
      // double productLength = node.get("productLength").asDouble();
      // long createdByUserSeq = node.get("createdByUserSeq").asLong();

      // RetailProduct rp = retailProductService.insert(
      // productNo,
      // retailPrice,
      // cost,
      // description,
      // retailCategory,
      // woodSpecie,
      // productWidth,
      // productThickness,
      // productLength,
      // createdByUserSeq);

      // ctx.json(rp);

      ApiRequest<?> request = ctx.bodyAsClass(ApiRequest.class);
      RetailProductDto rpd = mapper.convertValue(request.getPayload(), RetailProductDto.class);

      String userNo = ctx.attribute("userNo");
      long createdByUserSeq = StringUtil.parseEntityNo(userNo);

      RetailProduct rp2 = retailProductService.insert(
          rpd.getProductNo(),
          rpd.getRetailPrice(),
          rpd.getCost(),
          rpd.getDescription(),
          rpd.getRetailCategoryId(),
          rpd.getWoodSpecyId(),
          rpd.getProductWidth(),
          rpd.getProductThickness(),
          rpd.getProductLength(),
          createdByUserSeq);

      ctx.json(rp2);

    } catch (IllegalArgumentException e) {
      ctx.status(400).result("Invalid request body: " + e.getMessage());
    }
  }
}
