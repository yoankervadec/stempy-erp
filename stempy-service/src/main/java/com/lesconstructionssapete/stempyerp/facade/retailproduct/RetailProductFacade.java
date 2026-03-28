package com.lesconstructionssapete.stempyerp.facade.retailproduct;

import java.util.List;

import com.lesconstructionssapete.stempyerp.domain.auth.User;
import com.lesconstructionssapete.stempyerp.domain.retailproduct.RetailProduct;
import com.lesconstructionssapete.stempyerp.domain.retailproduct.RetailProductMaster;
import com.lesconstructionssapete.stempyerp.domain.shared.query.DomainQuery;

public interface RetailProductFacade {

  List<RetailProduct> fetch(DomainQuery query);

  List<RetailProductMaster> fetchMasters(DomainQuery query);

  RetailProduct insert(User user, RetailProduct retailProduct);

  RetailProductMaster insertMaster(User user, RetailProductMaster retailProductMaster);
}
