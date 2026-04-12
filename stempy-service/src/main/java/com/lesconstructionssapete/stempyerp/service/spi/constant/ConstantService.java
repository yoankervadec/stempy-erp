package com.lesconstructionssapete.stempyerp.service.spi.constant;

import java.util.List;

import com.lesconstructionssapete.stempyerp.constant.DomainEntityType;
import com.lesconstructionssapete.stempyerp.constant.PaymentMethod;
import com.lesconstructionssapete.stempyerp.constant.RetailCategory;
import com.lesconstructionssapete.stempyerp.constant.TaxGroup;

public interface ConstantService {

  List<DomainEntityType> getDomainEntityTypes();

  List<PaymentMethod> getPaymentMethods();

  List<RetailCategory> getRetailCategories();

  List<TaxGroup> getTaxGroups();

}
