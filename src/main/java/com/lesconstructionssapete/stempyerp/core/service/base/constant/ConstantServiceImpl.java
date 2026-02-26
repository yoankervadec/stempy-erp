package com.lesconstructionssapete.stempyerp.core.service.base.constant;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import com.lesconstructionssapete.stempyerp.core.config.db.ConnectionPool;
import com.lesconstructionssapete.stempyerp.core.domain.base.constant.EntityType;
import com.lesconstructionssapete.stempyerp.core.domain.base.constant.PaymentMethod;
import com.lesconstructionssapete.stempyerp.core.domain.base.constant.RetailCategory;
import com.lesconstructionssapete.stempyerp.core.domain.base.constant.TaxGroup;
import com.lesconstructionssapete.stempyerp.core.repository.base.constant.ConstantRepository;

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
