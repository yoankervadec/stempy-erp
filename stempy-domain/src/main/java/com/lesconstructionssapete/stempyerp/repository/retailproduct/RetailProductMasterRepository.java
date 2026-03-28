package com.lesconstructionssapete.stempyerp.repository.retailproduct;

import java.sql.Connection;
import java.util.List;

import com.lesconstructionssapete.stempyerp.domain.retailproduct.RetailProductMaster;
import com.lesconstructionssapete.stempyerp.domain.shared.query.DomainQuery;

public interface RetailProductMasterRepository {

  List<RetailProductMaster> fetch(Connection connection, DomainQuery query);

  long insert(Connection connection, RetailProductMaster retailProductMaster);

  int save(Connection connection, RetailProductMaster retailProductMaster);

}
