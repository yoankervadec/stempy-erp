package com.lesconstructionssapete.stempyerp.service.retailproduct;

import java.sql.Connection;
import java.util.List;

import com.lesconstructionssapete.stempyerp.domain.retailproduct.RetailProduct;
import com.lesconstructionssapete.stempyerp.domain.retailproduct.RetailProductMaster;
import com.lesconstructionssapete.stempyerp.domain.shared.query.DomainQuery;
import com.lesconstructionssapete.stempyerp.repository.retailproduct.RetailProductMasterRepository;
import com.lesconstructionssapete.stempyerp.repository.retailproduct.RetailProductRepository;

public class RetailProductServiceImpl implements RetailProductService {

  private final RetailProductRepository variantRepository;
  private final RetailProductMasterRepository masterRepository;

  public RetailProductServiceImpl(
      RetailProductRepository variantRepository,
      RetailProductMasterRepository masterRepository) {
    this.variantRepository = variantRepository;
    this.masterRepository = masterRepository;
  }

  @Override
  public List<RetailProduct> fetch(Connection connection, DomainQuery query) {

    return variantRepository.fetch(connection, query);
  }

  @Override
  public List<RetailProductMaster> fetchMasters(Connection connection, DomainQuery query) {
    return masterRepository.fetch(connection, query);
  }

  @Override
  public RetailProduct insert(Connection connection, RetailProduct retailProduct) {
    long generatedId = variantRepository.insert(connection, retailProduct);
    retailProduct.setEntityId(generatedId);
    return retailProduct;
  }

  @Override
  public RetailProductMaster insertMaster(Connection connection, RetailProductMaster retailProductMaster) {
    long generatedId = masterRepository.insert(connection, retailProductMaster);
    retailProductMaster.setEntityId(generatedId);
    return retailProductMaster;
  }

  @Override
  public int save(Connection connection, RetailProduct retailProduct) {
    return variantRepository.save(connection, retailProduct);
  }

  @Override
  public int saveMaster(Connection connection, RetailProductMaster retailProductMaster) {
    return masterRepository.save(connection, retailProductMaster);
  }
}
