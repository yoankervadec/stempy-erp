package com.lesconstructionssapete.stempyerp.core.repository.base.constant;

import java.sql.Connection;
import java.sql.SQLException;
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

public interface ConstantRepository {

  List<EntityType> getEntityTypes(Connection con) throws SQLException;

  List<TaxRegion> getTaxRegions(Connection con) throws SQLException;

  List<CustomerOrderHeaderStatus> getCustomerOrderHeaderStatuses(Connection con) throws SQLException;

  List<CustomerOrderLineStatus> getCustomerOrderLineStatuses(Connection con) throws SQLException;

  List<ItemEntryType> getItemEntryTypes(Connection con) throws SQLException;

  List<PosTransactionType> getPosTransactionTypes(Connection con) throws SQLException;

  List<RetailCategory> getRetailCategories(Connection con) throws SQLException;

  List<RetailLocation> getRetailLocations(Connection con) throws SQLException;

  List<RoleAction> getRoleActions(Connection con) throws SQLException;

  List<UserAction> getUserActions(Connection con) throws SQLException;

  List<UserRole> getUserRoles(Connection con) throws SQLException;

}
