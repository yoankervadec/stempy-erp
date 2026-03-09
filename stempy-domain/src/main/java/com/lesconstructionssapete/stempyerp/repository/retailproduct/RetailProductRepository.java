package com.lesconstructionssapete.stempyerp.repository.retailproduct;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import com.lesconstructionssapete.stempyerp.domain.retailproduct.RetailProduct;
import com.lesconstructionssapete.stempyerp.domain.shared.query.DomainQuery;

public interface RetailProductRepository {

  List<RetailProduct> fetch(Connection connection, DomainQuery query)
      throws SQLException;

  long insert(Connection connection, RetailProduct retailProduct)
      throws SQLException;

  RetailProduct save(Connection connection, RetailProduct retailProduct) throws SQLException;
}
