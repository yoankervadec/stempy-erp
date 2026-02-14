package com.lesconstructionssapete.stempyerp.app.facade.base.retailproduct;

import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;

import com.lesconstructionssapete.stempyerp.core.config.db.ConnectionPool;
import com.lesconstructionssapete.stempyerp.core.domain.base.retailproduct.RetailProduct;
import com.lesconstructionssapete.stempyerp.core.domain.base.sequence.LiveSequence;
import com.lesconstructionssapete.stempyerp.core.repository.base.retailproduct.RetailProductRepository;
import com.lesconstructionssapete.stempyerp.core.repository.base.sequence.SequenceRepository;
import com.lesconstructionssapete.stempyerp.core.shared.constant.ConstantCache;
import com.lesconstructionssapete.stempyerp.core.shared.constant.ConstantUtil;

public class RetailProductFacadeImpl implements RetailProductFacade {

  private final RetailProductRepository retailProductRepository;

  public RetailProductFacadeImpl(RetailProductRepository retailProductRepository) {
    this.retailProductRepository = retailProductRepository;
  }

  @Override
  public List<RetailProduct> fetchAllProducts() {

    Connection con = null;

    try {
      con = ConnectionPool.getConnection();
      con.setAutoCommit(false);

      var list = retailProductRepository.fetchAll(con, true);

      con.commit();
      return list;
    } catch (SQLException e) {
      if (con != null)
        try {
          con.rollback();
        } catch (SQLException e1) {
        }
      throw new RuntimeException("Failed...", e);
    } finally {
      if (con != null)
        try {
          con.close();
        } catch (SQLException e) {
        }
    }
  }

  @Override
  public RetailProduct insert(RetailProduct product) {

    Connection con = null;

    try {
      con = ConnectionPool.getConnection();
      con.setAutoCommit(false);

      LiveSequence liveSequence = SequenceRepository.generateNextSequence(
          con,
          ConstantUtil.findByName(
              ConstantCache.getInstance().getEntityTypes(), "RETAIL PRODUCT"),
          product.getCreatedByUserSeq());

      var rp = new RetailProduct(
          liveSequence.getSequenceNo(),
          liveSequence.getEntityNo(),
          product.getRetailPrice(),
          product.getCost(),
          product.getDescription(),
          product.getRetailCategoryId(),
          product.getWoodSpecyId(),
          product.getProductWidth(),
          product.getProductThickness(),
          product.getProductLength(),
          false,
          LocalDateTime.now(),
          liveSequence.getCreatedByUserSeq());

      RetailProduct result = retailProductRepository
          .insertRetailProduct(con, rp);

      con.commit();
      return result;
    } catch (SQLException e) {
      if (con != null)
        try {
          con.rollback();
        } catch (SQLException e1) {
        }
      throw new RuntimeException("Failed...", e);
    } finally {
      if (con != null)
        try {
          con.close();
        } catch (SQLException e) {
        }
    }
  }

}
