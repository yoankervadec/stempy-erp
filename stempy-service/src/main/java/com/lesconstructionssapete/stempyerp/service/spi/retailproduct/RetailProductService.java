package com.lesconstructionssapete.stempyerp.service.spi.retailproduct;

import java.sql.Connection;
import java.util.List;

import com.lesconstructionssapete.stempyerp.annotation.AppAction;
import com.lesconstructionssapete.stempyerp.annotation.AppResource;
import com.lesconstructionssapete.stempyerp.annotation.Permission;
import com.lesconstructionssapete.stempyerp.domain.query.DomainQuery;
import com.lesconstructionssapete.stempyerp.domain.retailproduct.RetailProduct;
import com.lesconstructionssapete.stempyerp.domain.retailproduct.RetailProductMaster;

public interface RetailProductService {

  @Permission(resource = AppResource.RETAIL_PRODUCT_VARIANT, action = AppAction.READ)
  List<RetailProduct> fetch(Connection connection, DomainQuery query);

  @Permission(resource = AppResource.RETAIL_PRODUCT_MASTER, action = AppAction.READ)
  List<RetailProductMaster> fetchMasters(Connection connection, DomainQuery query);

  @Permission(resource = AppResource.RETAIL_PRODUCT_VARIANT, action = AppAction.CREATE)
  RetailProduct insert(Connection connection, RetailProduct retailProduct);

  @Permission(resource = AppResource.RETAIL_PRODUCT_MASTER, action = AppAction.CREATE)
  RetailProductMaster insertMaster(Connection connection, RetailProductMaster retailProductMaster);

  int save(Connection connection, RetailProduct retailProduct);

  int saveMaster(Connection connection, RetailProductMaster retailProductMaster);

}
