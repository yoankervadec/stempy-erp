package com.lesconstructionssapete.stempyerp.core.service.base.constant;

import java.util.List;

import com.lesconstructionssapete.stempyerp.core.domain.base.constant.CustomerOrderHeaderStatus;
import com.lesconstructionssapete.stempyerp.core.domain.base.constant.CustomerOrderLineStatus;
import com.lesconstructionssapete.stempyerp.core.domain.base.constant.EntityType;
import com.lesconstructionssapete.stempyerp.core.domain.base.constant.ItemEntryType;
import com.lesconstructionssapete.stempyerp.core.domain.base.constant.PosTransactionType;
import com.lesconstructionssapete.stempyerp.core.domain.base.constant.RetailCategory;
import com.lesconstructionssapete.stempyerp.core.domain.base.constant.RetailLocation;
import com.lesconstructionssapete.stempyerp.core.domain.base.constant.RoleAction;
import com.lesconstructionssapete.stempyerp.core.domain.base.constant.TaxRegion;
import com.lesconstructionssapete.stempyerp.core.domain.base.constant.UserAction;
import com.lesconstructionssapete.stempyerp.core.domain.base.constant.UserRole;

public interface ConstantService {

  List<EntityType> getEntityTypes();

  List<TaxRegion> getTaxRegions();

  List<CustomerOrderHeaderStatus> getCustomerOrderHeaderStatuses();

  List<CustomerOrderLineStatus> getCustomerOrderLineStatuses();

  List<ItemEntryType> getItemEntryTypes();

  List<PosTransactionType> getPosTransactionTypes();

  List<RetailCategory> getRetailCategories();

  List<RetailLocation> getRetailLocations();

  List<RoleAction> getRoleActions();

  List<UserAction> getUserActions();

  List<UserRole> getUserRoles();

}
