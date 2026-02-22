package com.lesconstructionssapete.stempyerp.core.repository.base.retailproduct;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import com.lesconstructionssapete.stempyerp.core.domain.base.retailproduct.RetailProduct;
import com.lesconstructionssapete.stempyerp.core.domain.shared.query.DomainQuery;

public interface RetailProductRepository {

  List<RetailProduct> fetch(Connection connection, DomainQuery query)
      throws SQLException;

  RetailProduct insertRetailProduct(Connection connection, RetailProduct retailProduct)
      throws SQLException;

  RetailProduct save(Connection connection, RetailProduct retailProduct) throws SQLException;
}
