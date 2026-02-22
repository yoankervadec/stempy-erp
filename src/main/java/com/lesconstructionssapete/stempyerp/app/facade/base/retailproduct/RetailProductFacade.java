package com.lesconstructionssapete.stempyerp.app.facade.base.retailproduct;

import java.util.List;

import com.lesconstructionssapete.stempyerp.core.domain.base.retailproduct.RetailProduct;
import com.lesconstructionssapete.stempyerp.core.domain.shared.query.DomainQuery;

public interface RetailProductFacade {

  List<RetailProduct> fetch(DomainQuery query);

  RetailProduct insert(RetailProduct newProduct);
}
