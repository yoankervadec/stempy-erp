package com.lesconstructionssapete.stempyerp.core.service.base.constant;

import java.util.List;

import com.lesconstructionssapete.stempyerp.domain.base.constant.EntityType;
import com.lesconstructionssapete.stempyerp.domain.base.constant.PaymentMethod;
import com.lesconstructionssapete.stempyerp.domain.base.constant.RetailCategory;
import com.lesconstructionssapete.stempyerp.domain.base.constant.TaxGroup;

public interface ConstantService {

  List<EntityType> getEntityTypes();

  List<PaymentMethod> getPaymentMethods();

  List<RetailCategory> getRetailCategories();

  List<TaxGroup> getTaxGroups();

}
