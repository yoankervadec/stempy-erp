
package com.lesconstructions.sapete.stempyerp;

import java.sql.Connection;
import java.sql.SQLException;

import com.lesconstructions.sapete.stempyerp.app.service.base.retailproduct.RetailProductService;
import com.lesconstructions.sapete.stempyerp.core.config.db.ConnectionPool;
import com.lesconstructions.sapete.stempyerp.core.repository.base.retailproduct.IRetailProductRepository;
import com.lesconstructions.sapete.stempyerp.core.repository.base.retailproduct.RetailProductRepositoryJdbc;
import com.lesconstructions.sapete.stempyerp.core.shared.constant.ConstantCache;

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

    // List<CustomerOrderHeaderStatus> statuses =
    // ConfigCache.getCustomerOrderHeaderStatuses();

    // for (CustomerOrderHeaderStatus status : statuses) {
    // System.out.println(status.getId());
    // System.out.println(status.getName());
    // System.out.println(status.isEnabled());
    // }

    // CustomerOrderHeaderStatus ready = ConfigUtil.findById(statuses, 1);

    // System.out.println(ready.getName());

    // List<TaxRegion> taxRegions = ConstantCache.getTaxRegions();

    // TaxRegion qcTaxes = ConstantUtil.findById(taxRegions, 1);

    // BigDecimal subtotal = MathUtil.calculateSubtotal(1, BigDecimal.valueOf(1000),
    // BigDecimal.valueOf(25), true);

    // System.out.println(subtotal);

    // BigDecimal taxAmount = MathUtil.calculateTaxAmount(subtotal,
    // qcTaxes.getTaxRate(), true);

    // System.out.println(taxAmount);

    // System.out.println(subtotal.add(taxAmount));

    // List<BigDecimal> subtotals = new ArrayList<>();

    // BigDecimal lineSubtotal1 = new BigDecimal("10");
    // BigDecimal lineSubtotal2 = new BigDecimal("10");
    // BigDecimal lineSubtotal3 = new BigDecimal("10");
    // subtotals.add(lineSubtotal1);
    // subtotals.add(lineSubtotal2);
    // subtotals.add(lineSubtotal3);
    // TaxEngine.TaxResult result = TaxEngine.applyTaxes(subtotals, qcTaxes);

    // System.out.println(result.lineTaxes);
    // System.out.println(result.totalTax);

    // LocalDateTime now = LocalDateTime.now();

    // // Format
    // String formatted = DateTimeUtil.format(now, "yyyy-MM-dd HH:mm:ss");
    // System.out.println(formatted);

    // // Parse
    // LocalDateTime parsed = DateTimeUtil.parse("2025-08-16 14:30:00", "yyyy-MM-dd
    // HH:mm:ss");
    // System.out.println(parsed);

    // // Timezone conversion
    // LocalDateTime parisTime = DateTimeUtil.convertZone(now,
    // ZoneId.of("America/Montreal"),
    // ZoneId.of("America/Vancouver"));
    // System.out.println("Conversion :" + parisTime);

    // // Date-only
    // LocalDate dateOnly = DateTimeUtil.toDateOnly(now);
    // System.out.println(dateOnly);

    // // Truncate
    // LocalDateTime start = DateTimeUtil.startOfDay(now);
    // System.out.println(start);
    // LocalDateTime end = DateTimeUtil.endOfDay(now);
    // System.out.println(end);

  }
}
