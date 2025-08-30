package com.lesconstructions.sapete.stempyerp.app.service.base.retailproduct;

import java.util.List;

import com.lesconstructions.sapete.stempyerp.core.domain.base.retailproduct.RetailProduct;

public interface RetailProductService {

  List<RetailProduct> fetchAllProducts();
}
