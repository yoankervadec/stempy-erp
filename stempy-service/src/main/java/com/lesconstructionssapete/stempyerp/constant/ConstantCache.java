package com.lesconstructionssapete.stempyerp.constant;

import java.sql.SQLException;
import java.util.List;

import com.lesconstructionssapete.stempyerp.domain.constant.DomainEntityType;
import com.lesconstructionssapete.stempyerp.domain.constant.PaymentMethod;
import com.lesconstructionssapete.stempyerp.domain.constant.RetailCategory;
import com.lesconstructionssapete.stempyerp.domain.constant.TaxGroup;

public interface ConstantCache {

  void warmUp() throws SQLException;

  List<DomainEntityType> getDomainEntityTypes();

  List<RetailCategory> getRetailCategories();

  List<TaxGroup> getTaxGroups();

  List<PaymentMethod> getPaymentMethods();

}
