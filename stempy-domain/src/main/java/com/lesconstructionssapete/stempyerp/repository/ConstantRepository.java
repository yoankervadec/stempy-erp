package com.lesconstructionssapete.stempyerp.repository;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import com.lesconstructionssapete.stempyerp.domain.base.constant.DomainEntityType;
import com.lesconstructionssapete.stempyerp.domain.base.constant.PaymentMethod;
import com.lesconstructionssapete.stempyerp.domain.base.constant.RetailCategory;
import com.lesconstructionssapete.stempyerp.domain.base.constant.TaxGroup;
import com.lesconstructionssapete.stempyerp.domain.base.constant.TaxGroupLine;
import com.lesconstructionssapete.stempyerp.domain.base.constant.TaxRate;

public interface ConstantRepository {

  List<DomainEntityType> getEntityTypes(Connection con) throws SQLException;

  List<PaymentMethod> getPaymentMethods(Connection con) throws SQLException;

  List<RetailCategory> getRetailCategories(Connection con) throws SQLException;

  List<TaxGroup> getTaxGroups(Connection con) throws SQLException;

  List<TaxGroupLine> getTaxGroupLines(Connection con) throws SQLException;

  List<TaxRate> getTaxRates(Connection con) throws SQLException;

}
