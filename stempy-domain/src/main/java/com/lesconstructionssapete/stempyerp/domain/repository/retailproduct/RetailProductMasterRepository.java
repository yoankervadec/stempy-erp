package com.lesconstructionssapete.stempyerp.domain.repository.retailproduct;

import java.sql.Connection;
import java.util.List;

import com.lesconstructionssapete.stempyerp.domain.query.DomainQuery;
import com.lesconstructionssapete.stempyerp.domain.retailproduct.RetailProductMaster;

public interface RetailProductMasterRepository {

  List<RetailProductMaster> fetch(Connection connection, DomainQuery query);

  long insert(Connection connection, RetailProductMaster retailProductMaster);

  int save(Connection connection, RetailProductMaster retailProductMaster);

}
