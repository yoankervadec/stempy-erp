package com.lesconstructions.sapete.stempyerp.app.facade.base.retailproduct;

import java.math.BigDecimal;
import java.util.List;

import com.lesconstructions.sapete.stempyerp.core.domain.base.retailproduct.RetailProduct;

public interface RetailProductFacade {

  List<RetailProduct> fetchAllProducts();

  RetailProduct insert(
      String productNo,
      BigDecimal retailPrice,
      BigDecimal cost,
      String description,
      int retailCategory,
      int woodSpecie,
      double productWidth,
      double productThickness,
      double productLength,
      long createdByUserSeq);
}
