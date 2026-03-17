package com.lesconstructionssapete.stempyerp.service.retailproduct;

import java.sql.Connection;
import java.util.List;

import com.lesconstructionssapete.stempyerp.domain.retailproduct.RetailProduct;
import com.lesconstructionssapete.stempyerp.domain.shared.query.DomainQuery;

public interface RetailProductService {

  List<RetailProduct> fetch(Connection connection, DomainQuery query);

  RetailProduct insert(Connection connection, RetailProduct retailProduct);

  int save(Connection connection, RetailProduct retailProduct);

}
