package com.lesconstructionssapete.stempyerp.repository;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.lesconstructionssapete.stempyerp.domain.base.constant.EntityType;
import com.lesconstructionssapete.stempyerp.domain.base.constant.PaymentMethod;
import com.lesconstructionssapete.stempyerp.domain.base.constant.RetailCategory;
import com.lesconstructionssapete.stempyerp.domain.base.constant.TaxGroup;
import com.lesconstructionssapete.stempyerp.query.Query;
import com.lesconstructionssapete.stempyerp.query.QueryCache;

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

  @Override
  public List<PaymentMethod> getPaymentMethods(Connection con) throws SQLException {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public List<TaxGroup> getTaxGroups(Connection con) throws SQLException {
    // TODO Auto-generated method stub
    return null;
  }

}
