package com.lesconstructionssapete.stempyerp.app.facade.base.retailproduct;

import java.math.BigDecimal;
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
  public RetailProduct insert(String productNo, BigDecimal retailPrice, BigDecimal cost, String description,
      int retailCategory, int woodSpecie, double productWidth, double productThickness, double productLength,
      long createdByUserSeq) {

    Connection con = null;

    try {
      con = ConnectionPool.getConnection();
      con.setAutoCommit(false);

      LiveSequence liveSequence = SequenceRepository.generateNextSequence(
          con,
          ConstantUtil.findByName(
              ConstantCache.getEntityTypes(), "RETAIL PRODUCT"),
          createdByUserSeq);

      var rp = new RetailProduct(
          liveSequence.getSequenceNo(),
          liveSequence.getEntityNo(),
          retailPrice,
          cost,
          description,
          retailCategory,
          woodSpecie,
          productWidth,
          productThickness,
          productLength,
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
