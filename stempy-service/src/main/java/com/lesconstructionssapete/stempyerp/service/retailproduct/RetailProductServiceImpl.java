package com.lesconstructionssapete.stempyerp.service.retailproduct;

import java.sql.Connection;
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

    return repository.fetch(connection, query);
  }

  @Override
  public RetailProduct insert(Connection connection, RetailProduct retailProduct) {
    long generatedId = repository.insert(connection, retailProduct);
    retailProduct.setEntityId(generatedId);
    return retailProduct;
  }

  @Override
  public int save(Connection connection, RetailProduct retailProduct) {
    return repository.save(connection, retailProduct);
  }
}
