package com.lesconstructionssapete.stempyerp.core.repository.base.retailproduct;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import com.lesconstructionssapete.stempyerp.core.domain.base.retailproduct.RetailProduct;

public class RetailProductRepositoryImpl implements RetailProductRepository {

  private final RetailProductFind retailProductFind;
  private final RetailProductInsert retailProductInsert;
  private final RetailProductUpdate retailProductUpdate;

  public RetailProductRepositoryImpl() {
    this.retailProductFind = new RetailProductFind();
    this.retailProductInsert = new RetailProductInsert();
    this.retailProductUpdate = new RetailProductUpdate();
  }

  @Override
  public List<RetailProduct> fetchAll(Connection connection, Boolean isEnabled) throws SQLException {

    return null;
  }

  @Override
  public RetailProduct findByProductNo(Connection connection, String productNo) throws SQLException {

    return retailProductFind.findByProductNo(connection, productNo);
  }

  @Override
  public RetailProduct insertRetailProduct(Connection connection, RetailProduct retailProduct) throws SQLException {

    return retailProductInsert.insertRetailProduct(connection, retailProduct);
  }

  @Override
  public RetailProduct save(Connection connection, RetailProduct retailProduct) throws SQLException {

    return retailProductUpdate.save(connection, retailProduct);
  }

}
