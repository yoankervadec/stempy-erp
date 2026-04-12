package com.lesconstructionssapete.stempyerp.constant;

import java.sql.SQLException;
import java.util.List;

public interface ConstantCache {

  void warmUp() throws SQLException;

  List<DomainEntityType> getDomainEntityTypes();

  List<RetailCategory> getRetailCategories();

  List<TaxGroup> getTaxGroups();

  List<PaymentMethod> getPaymentMethods();

}
