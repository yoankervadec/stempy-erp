package com.lesconstructionssapete.stempyerp.service.retailproduct;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import com.lesconstructionssapete.stempyerp.domain.retailproduct.RetailProduct;
import com.lesconstructionssapete.stempyerp.domain.shared.query.DomainQuery;
import com.lesconstructionssapete.stempyerp.repository.retailproduct.RetailProductRepository;

public class RetailProductServiceImpl implements RetailProductService {

  private final RetailProductRepository repository;

  public RetailProductServiceImpl(RetailProductRepository repository) {
    this.repository = repository;
  }

  @Override
  public List<RetailProduct> fetch(Connection connection, DomainQuery query) {

    try {
      return repository.fetch(connection, query);
    } catch (SQLException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    return null;
  }

  @Override
  public RetailProduct insert(Connection connection, RetailProduct retailProduct) {
    try {
      long generatedId = repository.insert(connection, retailProduct);
      retailProduct.setEntityId(generatedId);
      return retailProduct;
    } catch (SQLException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    return null;
  }

  @Override
  public RetailProduct save(Connection connection, RetailProduct retailProduct) {
    try {
      return repository.save(connection, retailProduct);
    } catch (SQLException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    return null;
  }

}
