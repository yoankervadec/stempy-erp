package com.lesconstructionssapete.stempyerp.app.facade.base.retailproduct;

import java.util.List;

import com.lesconstructionssapete.stempyerp.core.domain.base.retailproduct.RetailProduct;

public interface RetailProductFacade {

  List<RetailProduct> fetchAllProducts();

  RetailProduct insert(RetailProduct newProduct);
}
