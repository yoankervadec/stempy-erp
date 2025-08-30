package com.lesconstructions.sapete.stempyerp.app.service.base.retailproduct;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import com.lesconstructions.sapete.stempyerp.core.config.db.ConnectionPool;
import com.lesconstructions.sapete.stempyerp.core.domain.base.retailproduct.RetailProduct;
import com.lesconstructions.sapete.stempyerp.core.repository.base.retailproduct.RetailProductRepository;

public class RetailProductServiceImpl implements RetailProductService {

  private final RetailProductRepository retailProductRepository;

  public RetailProductServiceImpl(RetailProductRepository retailProductRepository) {
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
    // try (Connection con = ConnectionPool.getConnection()) {
    // return retailProductRepository.fetchAll(con, true);
    // } catch (SQLException e) {
    // throw new RuntimeException("Failed to fetch", e);
    // }

  }
}
