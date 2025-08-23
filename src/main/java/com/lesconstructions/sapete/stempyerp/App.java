
package com.lesconstructions.sapete.stempyerp;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import com.lesconstructions.sapete.stempyerp.app.service.base.retailproduct.RetailProductService;
import com.lesconstructions.sapete.stempyerp.core.config.db.ConnectionPool;
import com.lesconstructions.sapete.stempyerp.core.repository.base.retailproduct.IRetailProductRepository;
import com.lesconstructions.sapete.stempyerp.core.repository.base.retailproduct.RetailProductRepositoryJdbc;
import com.lesconstructions.sapete.stempyerp.core.shared.constant.ConstantCache;
import com.lesconstructions.sapete.stempyerp.core.shared.query.Query;
import com.lesconstructions.sapete.stempyerp.core.shared.query.QueryCache;
import com.lesconstructions.sapete.stempyerp.core.shared.query.SqlBuilder;

/**
 *
 * @author yoan-kervadec
 */
public class App {

  public static void main(String[] args) throws SQLException {

    try (Connection con = ConnectionPool.getConnection()) {
      ConstantCache.loadAll(con);

    }

    IRetailProductRepository repository = new RetailProductRepositoryJdbc();
    RetailProductService service = new RetailProductService(repository);

    var list = service.fetchAllProducts();

    System.out.println(list);

    String baseString = QueryCache.get(Query.SELECT_RETAIL_PRODUCT_BY_PRODUCT_NO);

    SqlBuilder builder = new SqlBuilder(baseString)
        .where("rp.item_no = ?", 123)
        .and("rp.retail_category_id = ?", "rat");

    String finalSql = builder.build();
    List<Object> params = builder.getParams();

    for (int i = 0; i < params.size(); i++) {
      System.out.println(params.get(i));
    }

    System.out.println(finalSql);

  }
}
