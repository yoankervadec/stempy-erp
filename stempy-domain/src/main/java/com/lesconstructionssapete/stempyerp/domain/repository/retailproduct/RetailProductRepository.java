package com.lesconstructionssapete.stempyerp.domain.repository.retailproduct;

import java.sql.Connection;
import java.util.List;

import com.lesconstructionssapete.stempyerp.domain.query.DomainQuery;
import com.lesconstructionssapete.stempyerp.domain.retailproduct.RetailProduct;

public interface RetailProductRepository {

  List<RetailProduct> fetch(Connection connection, DomainQuery query);

  long insert(Connection connection, RetailProduct retailProduct);

  int save(Connection connection, RetailProduct retailProduct);
}
