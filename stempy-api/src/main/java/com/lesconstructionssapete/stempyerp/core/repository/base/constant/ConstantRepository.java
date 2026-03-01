package com.lesconstructionssapete.stempyerp.core.repository.base.constant;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import com.lesconstructionssapete.stempyerp.domain.base.constant.EntityType;
import com.lesconstructionssapete.stempyerp.domain.base.constant.PaymentMethod;
import com.lesconstructionssapete.stempyerp.domain.base.constant.RetailCategory;
import com.lesconstructionssapete.stempyerp.domain.base.constant.TaxGroup;

public interface ConstantRepository {

  List<EntityType> getEntityTypes(Connection con) throws SQLException;

  List<PaymentMethod> getPaymentMethods(Connection con) throws SQLException;

  List<RetailCategory> getRetailCategories(Connection con) throws SQLException;

  List<TaxGroup> getTaxGroups(Connection con) throws SQLException;

}
