package com.lesconstructionssapete.stempyerp.app.controller;

import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lesconstructionssapete.stempyerp.app.dto.retailproduct.RetailProductDto;
import com.lesconstructionssapete.stempyerp.app.facade.base.retailproduct.RetailProductFacade;
import com.lesconstructionssapete.stempyerp.core.domain.base.retailproduct.RetailProduct;
import com.lesconstructionssapete.stempyerp.core.domain.base.user.User;
import com.lesconstructionssapete.stempyerp.core.http.ApiRequest;

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
      RetailProductDto rpDto = mapper.convertValue(request.getPayload(), RetailProductDto.class);

      User user = ctx.attribute("user");

      if (user != null) {
        RetailProduct rp = retailProductService.insert(
            rpDto.getProductNo(),
            rpDto.getRetailPrice(),
            rpDto.getCost(),
            rpDto.getDescription(),
            rpDto.getRetailCategoryId(),
            rpDto.getWoodSpecyId(),
            rpDto.getProductWidth(),
            rpDto.getProductThickness(),
            rpDto.getProductLength(),
            user.getCreatedByUserSeq());

        ctx.json(rp);
      } else {
        throw new RuntimeException("Failed to insert: no user defined");
      }

    } catch (IllegalArgumentException e) {
      ctx.status(400).result("Invalid request body: " + e.getMessage());
    }
  }
}
