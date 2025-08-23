package com.lesconstructions.sapete.stempyerp.core.repository.base.retailproduct;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import com.lesconstructions.sapete.stempyerp.core.domain.base.retailproduct.RetailProduct;
import com.lesconstructions.sapete.stempyerp.core.domain.base.user.UserReference;

public class RetailProductRepositoryJdbc implements IRetailProductRepository {

  private final RetailProductFind retailProductFind;
  private final RetailProductInsert retailProductInsert;

  public RetailProductRepositoryJdbc() {
    this.retailProductFind = new RetailProductFind();
    this.retailProductInsert = new RetailProductInsert();
  }

  @Override
  public List<RetailProduct> fetchAll(Connection connection, Boolean isEnabled) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public RetailProduct findByproductNo(Connection connection, String productNo) throws SQLException {

    return retailProductFind.findByproductNo(connection, productNo);
  }

  @Override
  public RetailProduct insRetailProduct(Connection connection, RetailProduct retailProduct,
      UserReference userReference) throws SQLException {

    return retailProductInsert.insertRetailProduct(connection, retailProduct);
  }

}
