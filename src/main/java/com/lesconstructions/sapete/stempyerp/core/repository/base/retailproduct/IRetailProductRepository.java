package com.lesconstructions.sapete.stempyerp.core.repository.base.retailproduct;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import com.lesconstructions.sapete.stempyerp.core.domain.base.retailproduct.RetailProduct;
import com.lesconstructions.sapete.stempyerp.core.domain.base.user.UserReference;

public interface IRetailProductRepository {

  RetailProduct findByproductNo(Connection connection, String productNo) throws SQLException;

  List<RetailProduct> fetchAll(Connection connection, Boolean isEnabled); // Wrapper class for null value

  RetailProduct insRetailProduct(Connection connection, RetailProduct retailProduct, UserReference userReference)
      throws SQLException;
}
