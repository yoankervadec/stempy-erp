package com.lesconstructionssapete.stempyerp.service.spi.retailproduct;

import java.sql.Connection;
import java.util.List;

import com.lesconstructionssapete.stempyerp.domain.query.DomainQuery;
import com.lesconstructionssapete.stempyerp.domain.retailproduct.RetailProduct;
import com.lesconstructionssapete.stempyerp.domain.retailproduct.RetailProductMaster;

public interface RetailProductService {

  List<RetailProduct> fetch(Connection connection, DomainQuery query);

  List<RetailProductMaster> fetchMasters(Connection connection, DomainQuery query);

  RetailProduct insert(Connection connection, RetailProduct retailProduct);

  RetailProductMaster insertMaster(Connection connection, RetailProductMaster retailProductMaster);

  int save(Connection connection, RetailProduct retailProduct);

  int saveMaster(Connection connection, RetailProductMaster retailProductMaster);

}
