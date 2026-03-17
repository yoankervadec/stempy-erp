package com.lesconstructionssapete.stempyerp.repository.retailproduct;

import java.sql.Connection;
import java.util.List;

import com.lesconstructionssapete.stempyerp.domain.retailproduct.RetailProduct;
import com.lesconstructionssapete.stempyerp.domain.shared.query.DomainQuery;

public interface RetailProductRepository {

  List<RetailProduct> fetch(Connection connection, DomainQuery query);

  long insert(Connection connection, RetailProduct retailProduct);

  int save(Connection connection, RetailProduct retailProduct);
}
