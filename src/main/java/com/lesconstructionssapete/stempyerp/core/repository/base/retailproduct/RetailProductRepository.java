package com.lesconstructionssapete.stempyerp.core.repository.base.retailproduct;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import com.lesconstructionssapete.stempyerp.core.domain.base.retailproduct.RetailProduct;

public interface RetailProductRepository {

  RetailProduct findByProductNo(Connection connection, String productNo) throws SQLException;

  List<RetailProduct> fetchAll(Connection connection, Boolean isEnabled)
      throws SQLException; // Wrapper class for null value

  RetailProduct insertRetailProduct(Connection connection, RetailProduct retailProduct)
      throws SQLException;

  RetailProduct save(Connection connection, RetailProduct retailProduct) throws SQLException;
}
