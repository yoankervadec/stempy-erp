package com.lesconstructions.sapete.stempyerp.app.service.base.retailproduct;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import com.lesconstructions.sapete.stempyerp.core.config.db.ConnectionPool;
import com.lesconstructions.sapete.stempyerp.core.domain.base.retailproduct.RetailProduct;
import com.lesconstructions.sapete.stempyerp.core.repository.base.retailproduct.IRetailProductRepository;

public class RetailProductService {

  private final IRetailProductRepository retailProductRepository;

  public RetailProductService(IRetailProductRepository retailProductRepository) {
    this.retailProductRepository = retailProductRepository;
  }

  public List<RetailProduct> fetchAllProducts() throws SQLException {

    @SuppressWarnings("resource")
    Connection con = null;

    try {
      con = ConnectionPool.getConnection();
      con.setAutoCommit(false);

      var list = retailProductRepository.fetchAll(con, true);

      con.commit();
      return list;
    } catch (SQLException e) {
      if (con != null)
        con.rollback();
      throw new RuntimeException("Failed...", e);
    } finally {
      if (con != null)
        con.close();
    }
    // try (Connection con = ConnectionPool.getConnection()) {
    // return retailProductRepository.fetchAll(con, true);
    // } catch (SQLException e) {
    // throw new RuntimeException("Failed to fetch", e);
    // }

  }
}
