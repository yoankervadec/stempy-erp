package com.lesconstructionssapete.stempyerp.service.base.constant;

import java.util.List;

import com.lesconstructionssapete.stempyerp.domain.base.constant.DomainEntityType;
import com.lesconstructionssapete.stempyerp.domain.base.constant.PaymentMethod;
import com.lesconstructionssapete.stempyerp.domain.base.constant.RetailCategory;
import com.lesconstructionssapete.stempyerp.domain.base.constant.TaxGroup;

public interface ConstantService {

  List<DomainEntityType> getDomainEntityTypes();

  List<PaymentMethod> getPaymentMethods();

  List<RetailCategory> getRetailCategories();

  List<TaxGroup> getTaxGroups();

}
