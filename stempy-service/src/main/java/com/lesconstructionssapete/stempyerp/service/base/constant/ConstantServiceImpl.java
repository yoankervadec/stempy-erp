package com.lesconstructionssapete.stempyerp.service.base.constant;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import com.lesconstructionssapete.stempyerp.config.db.ConnectionPool;
import com.lesconstructionssapete.stempyerp.domain.base.constant.EntityType;
import com.lesconstructionssapete.stempyerp.domain.base.constant.PaymentMethod;
import com.lesconstructionssapete.stempyerp.domain.base.constant.RetailCategory;
import com.lesconstructionssapete.stempyerp.domain.base.constant.TaxGroup;
import com.lesconstructionssapete.stempyerp.repository.ConstantRepository;

public class ConstantServiceImpl implements ConstantService {

  private final ConstantRepository constantRepository;

  public ConstantServiceImpl(ConstantRepository constantRepository) {
    this.constantRepository = constantRepository;
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
  public List<RetailCategory> getRetailCategories() {
    try (Connection conn = ConnectionPool.getConnection()) {
      return constantRepository.getRetailCategories(conn);
    } catch (SQLException e) {
      throw new RuntimeException("Failed to fetch Retail Categories", e);
    }
  }

  @Override
  public List<PaymentMethod> getPaymentMethods() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public List<TaxGroup> getTaxGroups() {
    // TODO Auto-generated method stub
    return null;
  }

}
