package com.lesconstructionssapete.stempyerp.domain.repository.retailproduct;

import java.sql.Connection;
import java.util.List;

import com.lesconstructionssapete.stempyerp.domain.query.DomainQuery;
import com.lesconstructionssapete.stempyerp.domain.retailproduct.RetailProductVariant;

public interface RetailProductRepository {

  List<RetailProductVariant> fetch(Connection connection, DomainQuery query);

  long insert(Connection connection, RetailProductVariant retailProduct);

  int save(Connection connection, RetailProductVariant retailProduct);
}
