package com.lesconstructionssapete.stempyerp.service.spi.retailproduct;

import java.sql.Connection;
import java.util.List;

import com.lesconstructionssapete.stempyerp.annotation.AppAction;
import com.lesconstructionssapete.stempyerp.annotation.AppResource;
import com.lesconstructionssapete.stempyerp.annotation.Permission;
import com.lesconstructionssapete.stempyerp.domain.query.DomainQuery;
import com.lesconstructionssapete.stempyerp.domain.retailproduct.RetailProductVariant;
import com.lesconstructionssapete.stempyerp.domain.retailproduct.RetailProductMaster;

public interface RetailProductService {

  @Permission(resource = AppResource.RETAIL_PRODUCT_VARIANT, action = AppAction.READ)
  List<RetailProductVariant> fetch(Connection connection, DomainQuery query);

  @Permission(resource = AppResource.RETAIL_PRODUCT_MASTER, action = AppAction.READ)
  List<RetailProductMaster> fetchMasters(Connection connection, DomainQuery query);

  @Permission(resource = AppResource.RETAIL_PRODUCT_VARIANT, action = AppAction.CREATE)
  RetailProductVariant insert(Connection connection, RetailProductVariant retailProduct);

  @Permission(resource = AppResource.RETAIL_PRODUCT_MASTER, action = AppAction.CREATE)
  RetailProductMaster insertMaster(Connection connection, RetailProductMaster retailProductMaster);

  int save(Connection connection, RetailProductVariant retailProduct);

  int saveMaster(Connection connection, RetailProductMaster retailProductMaster);

}
