package com.lesconstructionssapete.stempyerp.core.shared.constant;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
import com.lesconstructionssapete.stempyerp.core.repository.base.constant.ConstantRepository;

/*
 * Caches configuration constants into memory.
 */

public class ConstantCache {

  private static final Logger LOGGER = LoggerFactory.getLogger(ConstantCache.class);

  private static List<EntityType> entityTypes;
  private static List<TaxRegion> taxRegions;
  private static List<CustomerOrderHeaderStatus> customerOrderHeaderStatuses;
  private static List<CustomerOrderLineStatus> customerOrderLineStatuses;
  private static List<ItemEntryType> itemEntryTypes;
  private static List<PosTransactionType> posTransactionTypes;
  private static List<RetailCategory> retailCategories;
  private static List<RetailLocation> retailLocations;
  private static List<RoleAction> roleActions;
  private static List<UserAction> userActions;
  private static List<UserRole> userRoles;

  public static void loadAll(Connection con) throws SQLException {

    LOGGER.info("Loading and caching configuration constants...");

    entityTypes = ConstantRepository.loadEntityTypes(con);
    LOGGER.info("\t ...Cached Entity Type");

    taxRegions = ConstantRepository.loadTaxRegions(con);
    LOGGER.info("\t ...Cached Tax Region");

    customerOrderHeaderStatuses = ConstantRepository.loadCustomerOrderHeaderStatuses(con);
    LOGGER.info("\t ...Cached Customer Order Header Status");

    customerOrderLineStatuses = ConstantRepository.loadCustomerOrderLineStatuses(con);
    LOGGER.info("\t ...Cached Customer Order Line Status");

    itemEntryTypes = ConstantRepository.loadItemEntryTypes(con);
    LOGGER.info("\t ...Cached Item Entry Type");

    posTransactionTypes = ConstantRepository.loadPosTransactionTypes(con);
    LOGGER.info("\t ...Cached POS Transaction Type");

    retailCategories = ConstantRepository.loadRetailCategories(con);
    LOGGER.info("\t ...Cached Retail Categories");

    retailLocations = ConstantRepository.loadRetailLocations(con);
    LOGGER.info("\t ...Cached Retail Locations");

    roleActions = ConstantRepository.loadRoleActions(con);
    LOGGER.info("\t ...Cached Role Actions");

    userActions = ConstantRepository.loadUserActions(con);
    LOGGER.info("\t ...Cached User Actions");

    userRoles = ConstantRepository.loadUserRoles(con);
    LOGGER.info("\t ...Cached User Roles");

    LOGGER.info("Finished caching configuration constants.\n");
  }

  public static List<EntityType> getEntityTypes() {
    return entityTypes;
  }

  public static List<TaxRegion> getTaxRegions() {
    return taxRegions;
  }

  public static List<CustomerOrderHeaderStatus> getCustomerOrderHeaderStatuses() {
    return customerOrderHeaderStatuses;
  }

  public static List<CustomerOrderLineStatus> getCustomerOrderLineStatuses() {
    return customerOrderLineStatuses;
  }

  public static List<ItemEntryType> getItemEntryTypes() {
    return itemEntryTypes;
  }

  public static List<PosTransactionType> getPosTransactionTypes() {
    return posTransactionTypes;
  }

  public static List<RetailCategory> getRetailCategories() {
    return retailCategories;
  }

  public static List<RetailLocation> getRetailLocations() {
    return retailLocations;
  }

  public static List<RoleAction> getRoleActions() {
    return roleActions;
  }

  public static List<UserAction> getUserActions() {
    return userActions;
  }

  public static List<UserRole> getUserRoles() {
    return userRoles;
  }

}
