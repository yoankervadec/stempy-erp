package com.lesconstructionssapete.stempyerp.core.service.base.constant;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import com.lesconstructionssapete.stempyerp.core.config.db.ConnectionPool;
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

public class ConstantServiceImpl implements ConstantService {

  private final ConstantRepository constantRepository;

  public ConstantServiceImpl(ConstantRepository constantRepository) {
    this.constantRepository = constantRepository;
  }

  @Override
  public List<CustomerOrderHeaderStatus> getCustomerOrderHeaderStatuses() {
    try (Connection conn = ConnectionPool.getConnection()) {
      return constantRepository.getCustomerOrderHeaderStatuses(conn);
    } catch (SQLException e) {
      throw new RuntimeException("Failed to fetch Customer Order Header Statuses", e);
    }
  }

  @Override
  public List<CustomerOrderLineStatus> getCustomerOrderLineStatuses() {
    try (Connection conn = ConnectionPool.getConnection()) {
      return constantRepository.getCustomerOrderLineStatuses(conn);
    } catch (SQLException e) {
      throw new RuntimeException("Failed to fetch Customer Order Line Statuses", e);
    }
  }

  @Override
  public List<EntityType> getEntityTypes() {
    try (Connection conn = ConnectionPool.getConnection()) {
      return constantRepository.getEntityTypes(conn);
    } catch (SQLException e) {
      throw new RuntimeException("Failed to fetch Entity Types", e);
    }
  }

  @Override
  public List<ItemEntryType> getItemEntryTypes() {
    try (Connection conn = ConnectionPool.getConnection()) {
      return constantRepository.getItemEntryTypes(conn);
    } catch (SQLException e) {
      throw new RuntimeException("Failed to fetch Item Entry Types", e);
    }
  }

  @Override
  public List<PosTransactionType> getPosTransactionTypes() {
    try (Connection conn = ConnectionPool.getConnection()) {
      return constantRepository.getPosTransactionTypes(conn);
    } catch (SQLException e) {
      throw new RuntimeException("Failed to fetch POS Transaction Types", e);
    }
  }

  @Override
  public List<RetailCategory> getRetailCategories() {
    try (Connection conn = ConnectionPool.getConnection()) {
      return constantRepository.getRetailCategories(conn);
    } catch (SQLException e) {
      throw new RuntimeException("Failed to fetch Retail Categories", e);
    }
  }

  @Override
  public List<RetailLocation> getRetailLocations() {
    try (Connection conn = ConnectionPool.getConnection()) {
      return constantRepository.getRetailLocations(conn);
    } catch (SQLException e) {
      throw new RuntimeException("Failed to fetch Retail Locations", e);
    }
  }

  @Override
  public List<RoleAction> getRoleActions() {
    try (Connection conn = ConnectionPool.getConnection()) {
      return constantRepository.getRoleActions(conn);
    } catch (SQLException e) {
      throw new RuntimeException("Failed to fetch Role Actions", e);
    }
  }

  @Override
  public List<TaxRegion> getTaxRegions() {
    try (Connection conn = ConnectionPool.getConnection()) {
      return constantRepository.getTaxRegions(conn);
    } catch (SQLException e) {
      throw new RuntimeException("Failed to fetch Tax Regions", e);
    }
  }

  @Override
  public List<UserAction> getUserActions() {
    try (Connection conn = ConnectionPool.getConnection()) {
      return constantRepository.getUserActions(conn);
    } catch (SQLException e) {
      throw new RuntimeException("Failed to fetch User Actions", e);
    }
  }

  @Override
  public List<UserRole> getUserRoles() {
    try (Connection conn = ConnectionPool.getConnection()) {
      return constantRepository.getUserRoles(conn);
    } catch (SQLException e) {
      throw new RuntimeException("Failed to fetch User Roles", e);
    }
  }

}
