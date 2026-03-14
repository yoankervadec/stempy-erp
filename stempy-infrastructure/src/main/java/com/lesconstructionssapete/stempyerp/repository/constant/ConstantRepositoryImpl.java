package com.lesconstructionssapete.stempyerp.repository.constant;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.lesconstructionssapete.stempyerp.domain.constant.DomainEntityType;
import com.lesconstructionssapete.stempyerp.domain.constant.PaymentMethod;
import com.lesconstructionssapete.stempyerp.domain.constant.RetailCategory;
import com.lesconstructionssapete.stempyerp.domain.constant.TaxGroup;
import com.lesconstructionssapete.stempyerp.domain.constant.TaxGroupLine;
import com.lesconstructionssapete.stempyerp.domain.constant.TaxRate;
import com.lesconstructionssapete.stempyerp.query.Query;
import com.lesconstructionssapete.stempyerp.query.QueryCache;
import com.lesconstructionssapete.stempyerp.repository.ConstantRepository;

/*
 * Load configuration constants from the database.
 */

public class ConstantRepositoryImpl implements ConstantRepository {

  // Entity Type
  @Override
  public List<DomainEntityType> getEntityTypes(Connection con) throws SQLException {

    String sqlString = QueryCache.get(
        Query.SELECT_CORE_DOMAIN_ENTITY_CONFIG);

    try (var stmt = con.prepareStatement(sqlString);
        var rs = stmt.executeQuery()) {
      List<DomainEntityType> list = new ArrayList<>();
      while (rs.next()) {
        list.add(new DomainEntityType(
            rs.getLong("id"),
            rs.getString("name"),
            rs.getBoolean("enabled"),
            rs.getTimestamp("created_at").toInstant(),
            rs.getInt("pad_length"),
            rs.getString("prefix")));
      }
      return list;
    }
  }

  // Retail Categories
  @Override
  public List<RetailCategory> getRetailCategories(Connection con) throws SQLException {

    String sqlString = QueryCache.get(
        Query.SELECT_REF_RETAIL_CATEGORY);

    try (var stmt = con.prepareStatement(sqlString);
        var rs = stmt.executeQuery()) {
      List<RetailCategory> list = new ArrayList<>();
      while (rs.next()) {
        list.add(new RetailCategory(
            rs.getLong("id"),
            rs.getString("name"),
            rs.getString("description"),
            rs.getBoolean("enabled"),
            rs.getTimestamp("created_at").toInstant()));
      }

      return list;
    }
  }

  @Override
  public List<PaymentMethod> getPaymentMethods(Connection con) throws SQLException {

    String sqlString = QueryCache.get(
        Query.SELECT_REF_PAYMENT_METHOD);

    try (var stmt = con.prepareStatement(sqlString);
        var rs = stmt.executeQuery()) {
      List<PaymentMethod> list = new ArrayList<>();
      while (rs.next()) {
        list.add(new PaymentMethod(
            rs.getLong("id"),
            rs.getString("name"),
            rs.getString("description"),
            rs.getBoolean("enabled"),
            rs.getTimestamp("created_at").toInstant()));
      }

      return list;
    }

  }

  @Override
  public List<TaxGroup> getTaxGroups(Connection con) throws SQLException {

    String sqlString = QueryCache.get(
        Query.SELECT_REF_TAX_GROUP);

    try (var stmt = con.prepareStatement(sqlString);
        var rs = stmt.executeQuery()) {
      List<TaxGroup> list = new ArrayList<>();
      while (rs.next()) {
        list.add(new TaxGroup(
            rs.getLong("id"),
            rs.getString("name"),
            rs.getString("description"),
            rs.getBoolean("enabled"),
            rs.getTimestamp("created_at").toInstant()));
      }

      return list;
    }

  }

  @Override
  public List<TaxGroupLine> getTaxGroupLines(Connection con) throws SQLException {

    String sqlString = QueryCache.get(
        Query.SELECT_REF_TAX_GROUP_LINE);

    try (var stmt = con.prepareStatement(sqlString);
        var rs = stmt.executeQuery()) {
      List<TaxGroupLine> list = new ArrayList<>();
      while (rs.next()) {
        list.add(new TaxGroupLine(
            rs.getLong("id"),
            rs.getLong("tax_group_id"),
            rs.getLong("tax_rate_id"),
            rs.getInt("sort_order")));
      }

      return list;
    }

  }

  @Override
  public List<TaxRate> getTaxRates(Connection con) throws SQLException {

    String sqlString = QueryCache.get(
        Query.SELECT_REF_TAX_RATE);

    try (var stmt = con.prepareStatement(sqlString);
        var rs = stmt.executeQuery()) {
      List<TaxRate> list = new ArrayList<>();
      while (rs.next()) {
        list.add(new TaxRate(
            rs.getLong("id"),
            rs.getString("name"),
            rs.getString("description"),
            rs.getBoolean("enabled"),
            rs.getTimestamp("created_at").toInstant(),
            rs.getDouble("rate"),
            rs.getBoolean("is_compound"),
            rs.getInt("calculation_order"),
            rs.getDate("valid_from").toLocalDate(),
            rs.getDate("valid_to") != null ? rs.getDate("valid_to").toLocalDate() : null));
      }

      return list;
    }

  }

}
