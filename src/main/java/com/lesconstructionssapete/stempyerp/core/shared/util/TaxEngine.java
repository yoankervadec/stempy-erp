package com.lesconstructionssapete.stempyerp.core.shared.util;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import com.lesconstructionssapete.stempyerp.core.domain.base.constant.TaxRegion;

/*
 * Receives a List of subtotals and une TaxRegion
 * Returns a List of taxAmount and one total TaxAmount
 */

public class TaxEngine {

  public static class TaxResult {
    public final List<BigDecimal> lineTaxes;
    public final BigDecimal totalTax;

    public TaxResult(List<BigDecimal> lineTaxes, BigDecimal totalTax) {
      this.lineTaxes = lineTaxes;
      this.totalTax = totalTax;
    }
  }

  public static TaxResult applyTaxes(List<BigDecimal> subtotals, TaxRegion taxRegion) {
    // 14.975 -> 0.14975
    BigDecimal rate = BigDecimal.valueOf(taxRegion.getTaxRate())
        .divide(BigDecimal.valueOf(100), 6, RoundingMode.HALF_UP);

    // Expected total tax amount
    BigDecimal subtotalSum = subtotals.stream()
        .reduce(BigDecimal.ZERO, BigDecimal::add);
    BigDecimal expectedTotalTax = subtotalSum
        .multiply(rate).setScale(2, RoundingMode.HALF_UP);

    class Line {
      BigDecimal ideal;
      BigDecimal rounded;
      BigDecimal remainder;
    }

    List<Line> lines = new ArrayList<>();
    BigDecimal lineTaxSum = BigDecimal.ZERO;

    // Step 1: calculate per-line ideal, rounded and remainder
    for (BigDecimal subtotal : subtotals) {
      Line line = new Line();
      line.ideal = subtotal.multiply(rate);
      line.rounded = line.ideal.setScale(2, RoundingMode.HALF_UP);
      line.remainder = line.ideal.remainder(BigDecimal.ONE); // fractional part
      lines.add(line);
      lineTaxSum = lineTaxSum.add(line.rounded);
    }

    // Step 2: adjustment
    BigDecimal diff = expectedTotalTax.subtract(lineTaxSum);
    if (diff.compareTo(BigDecimal.ZERO) != 0) {
      BigDecimal penny = new BigDecimal("0.01");
      int steps = diff.abs().divideToIntegralValue(penny).intValue();

      // Sort lines by remainder (descending if we need to add, ascending if subtract)
      Comparator<Line> comparator = Comparator.comparing(l -> l.remainder);
      if (diff.signum() > 0) {
        comparator = comparator.reversed();
      }
      lines.sort(comparator);

      for (int i = 0; i < steps && i < lines.size(); i++) {
        Line target = lines.get(i);
        target.rounded = diff.signum() > 0
            ? target.rounded.add(penny)
            : target.rounded.subtract(penny);
      }
      lineTaxSum = lineTaxSum.add(diff);
    }

    // Step 3: collect results
    List<BigDecimal> lineTaxes = new ArrayList<>();
    for (Line l : lines) {
      lineTaxes.add(l.rounded);
    }

    return new TaxResult(lineTaxes, lineTaxSum);
  }
}