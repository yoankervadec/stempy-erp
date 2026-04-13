package com.lesconstructionssapete.stempyerp.service.spi.constant;

import java.util.List;

import com.lesconstructionssapete.stempyerp.domain.constant.DomainEntityType;
import com.lesconstructionssapete.stempyerp.domain.constant.PaymentMethod;
import com.lesconstructionssapete.stempyerp.domain.constant.RetailCategory;
import com.lesconstructionssapete.stempyerp.domain.constant.TaxGroup;

public interface ConstantService {

  List<DomainEntityType> getDomainEntityTypes();

  List<PaymentMethod> getPaymentMethods();

  List<RetailCategory> getRetailCategories();

  List<TaxGroup> getTaxGroups();

}
