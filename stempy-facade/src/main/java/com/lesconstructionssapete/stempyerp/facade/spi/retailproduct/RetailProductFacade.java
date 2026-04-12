package com.lesconstructionssapete.stempyerp.facade.spi.retailproduct;

import java.util.List;

import com.lesconstructionssapete.stempyerp.query.DomainQuery;
import com.lesconstructionssapete.stempyerp.retailproduct.RetailProduct;
import com.lesconstructionssapete.stempyerp.retailproduct.RetailProductMaster;
import com.lesconstructionssapete.stempyerp.user.User;

public interface RetailProductFacade {

  List<RetailProduct> fetch(DomainQuery query);

  List<RetailProductMaster> fetchMasters(DomainQuery query);

  RetailProduct insert(User user, RetailProduct retailProduct);

  RetailProductMaster insertMaster(User user, RetailProductMaster retailProductMaster);
}
