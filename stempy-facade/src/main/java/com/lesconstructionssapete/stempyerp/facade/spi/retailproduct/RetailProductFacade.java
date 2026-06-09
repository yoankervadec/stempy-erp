package com.lesconstructionssapete.stempyerp.facade.spi.retailproduct;

import java.util.List;

import com.lesconstructionssapete.stempyerp.domain.query.DomainQuery;
import com.lesconstructionssapete.stempyerp.domain.retailproduct.RetailProductVariant;
import com.lesconstructionssapete.stempyerp.domain.retailproduct.RetailProductMaster;
import com.lesconstructionssapete.stempyerp.domain.user.User;

public interface RetailProductFacade {

  List<RetailProductVariant> fetch(DomainQuery query);

  List<RetailProductMaster> fetchMasters(DomainQuery query);

  RetailProductVariant insert(User user, RetailProductVariant retailProduct);

  RetailProductMaster insertMaster(User user, RetailProductMaster retailProductMaster);
}
