package com.lesconstructionssapete.stempyerp.core.repository.base.constant;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
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
import com.lesconstructionssapete.stempyerp.core.shared.query.Query;
import com.lesconstructionssapete.stempyerp.core.shared.query.QueryCache;

/*
 * Load configuration constants from the database.
 */

public class ConstantRepositoryImpl implements ConstantRepository {

  // Entity Type
  @Override
  public List<EntityType> getEntityTypes(Connection con) throws SQLException {

    String sqlString = QueryCache.get(
        Query.SELECT_CONFIG_ENTITY_TYPE);

    try (var stmt = con.prepareStatement(sqlString);
        var rs = stmt.executeQuery()) {
      List<EntityType> list = new ArrayList<>();
      while (rs.next()) {
        list.add(new EntityType(
            rs.getInt("id"),
            rs.getString("name"),
            rs.getInt("pad_length"),
            rs.getString("prefix_string"),
            rs.getBoolean("is_enabled")));
      }
      return list;
    }
  }

  // Tax Region
  @Override
  public List<TaxRegion> getTaxRegions(Connection con) throws SQLException {

    String sqlString = QueryCache.get(
        Query.SELECT_CONFIG_TAX_REGION);

    try (var stmt = con.prepareStatement(sqlString);
        var rs = stmt.executeQuery()) {
      List<TaxRegion> list = new ArrayList<>();
      while (rs.next()) {
        list.add(new TaxRegion(
            rs.getInt("id"),
            rs.getString("tax_region"),
            rs.getString("name"),
            rs.getDouble("gst_rate"),
            rs.getDouble("pst_rate"),
            rs.getBoolean("is_enabled")));
      }
      return list;
    }
  }

  // Customer Order Header Status
  @Override
  public List<CustomerOrderHeaderStatus> getCustomerOrderHeaderStatuses(Connection con) throws SQLException {

    String sqlString = QueryCache.get(
        Query.SELECT_CONFIG_CUSTOMER_ORDER_HEADER_STATUS);

    try (var stmt = con.prepareStatement(sqlString);
        var rs = stmt.executeQuery()) {
      List<CustomerOrderHeaderStatus> list = new ArrayList<>();
      while (rs.next()) {
        list.add(new CustomerOrderHeaderStatus(
            rs.getInt("id"),
            rs.getString("name"),
            rs.getBoolean("is_enabled")));
      }

      return list;
    }
  }

  // Customer Order Line Status
  @Override
  public List<CustomerOrderLineStatus> getCustomerOrderLineStatuses(Connection con) throws SQLException {

    String sqlString = QueryCache.get(
        Query.SELECT_CONFIG_CUSTOMER_ORDER_LINE_STATUS);

    try (var stmt = con.prepareStatement(sqlString);
        var rs = stmt.executeQuery()) {
      List<CustomerOrderLineStatus> list = new ArrayList<>();
      while (rs.next()) {
        list.add(new CustomerOrderLineStatus(
            rs.getInt("id"),
            rs.getString("name"),
            rs.getBoolean("is_enabled")));
      }

      return list;
    }

  }

  // Item Entry Type
  @Override
  public List<ItemEntryType> getItemEntryTypes(Connection con) throws SQLException {

    String sqlString = QueryCache.get(
        Query.SELECT_CONFIG_ITEM_ENTRY_TYPE);

    try (var stmt = con.prepareStatement(sqlString);
        var rs = stmt.executeQuery()) {
      List<ItemEntryType> list = new ArrayList<>();
      while (rs.next()) {
        list.add(new ItemEntryType(
            rs.getInt("id"),
            rs.getString("name"),
            rs.getBoolean("is_enabled")));
      }

      return list;
    }
  }

  // POS Transaction Type
  @Override
  public List<PosTransactionType> getPosTransactionTypes(Connection con) throws SQLException {

    String sqlString = QueryCache.get(
        Query.SELECT_CONFIG_POS_TRANSACTION_TYPE);

    try (var stmt = con.prepareStatement(sqlString);
        var rs = stmt.executeQuery()) {
      List<PosTransactionType> list = new ArrayList<>();
      while (rs.next()) {
        list.add(new PosTransactionType(
            rs.getInt("id"),
            rs.getString("name"),
            rs.getBoolean("is_enabled")));
      }

      return list;
    }
  }

  // Retail Categories
  @Override
  public List<RetailCategory> getRetailCategories(Connection con) throws SQLException {

    String sqlString = QueryCache.get(
        Query.SELECT_CONFIG_RETAIL_CATEGORY);

    try (var stmt = con.prepareStatement(sqlString);
        var rs = stmt.executeQuery()) {
      List<RetailCategory> list = new ArrayList<>();
      while (rs.next()) {
        list.add(new RetailCategory(
            rs.getInt("id"),
            rs.getString("name"),
            rs.getString("description"),
            rs.getBoolean("is_enabled")));
      }

      return list;
    }
  }

  // Retail Locations
  @Override
  public List<RetailLocation> getRetailLocations(Connection con) throws SQLException {

    String sqlString = QueryCache.get(
        Query.SELECT_CONFIG_RETAIL_LOCATION);

    try (var stmt = con.prepareStatement(sqlString);
        var rs = stmt.executeQuery()) {
      List<RetailLocation> list = new ArrayList<>();
      while (rs.next()) {
        list.add(new RetailLocation(
            rs.getInt("id"),
            rs.getString("name"),
            rs.getString("description"),
            rs.getBoolean("is_enabled")));
      }

      return list;
    }
  }

  // Role Actions
  @Override
  public List<RoleAction> getRoleActions(Connection con) throws SQLException {

    String sqlString = QueryCache.get(
        Query.SELECT_CONFIG_ROLE_ACTION);

    try (var stmt = con.prepareStatement(sqlString);
        var rs = stmt.executeQuery()) {
      List<RoleAction> list = new ArrayList<>();
      while (rs.next()) {
        list.add(new RoleAction(
            rs.getInt("role_id"),
            rs.getInt("action_id"),
            rs.getString("action_name"),
            rs.getBoolean("is_enabled")));
      }

      return list;
    }
  }

  // User Actions
  @Override
  public List<UserAction> getUserActions(Connection con) throws SQLException {

    String sqlString = QueryCache.get(
        Query.SELECT_CONFIG_USER_ACTION);

    try (var stmt = con.prepareStatement(sqlString);
        var rs = stmt.executeQuery()) {
      List<UserAction> list = new ArrayList<>();
      while (rs.next()) {
        list.add(new UserAction(
            rs.getInt("id"),
            rs.getString("name"),
            rs.getBoolean("is_enabled")));
      }

      return list;
    }
  }

  // User Role
  @Override
  public List<UserRole> getUserRoles(Connection con) throws SQLException {

    String sqlString = QueryCache.get(
        Query.SELECT_CONFIG_USER_ROLE);

    try (var stmt = con.prepareStatement(sqlString);
        var rs = stmt.executeQuery()) {
      List<UserRole> list = new ArrayList<>();
      while (rs.next()) {
        list.add(new UserRole(
            rs.getInt("id"),
            rs.getString("name"),
            rs.getBoolean("is_enabled")));
      }

      return list;
    }
  }

}
