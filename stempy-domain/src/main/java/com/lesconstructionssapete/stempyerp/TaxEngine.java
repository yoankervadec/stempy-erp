package com.lesconstructionssapete.stempyerp;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import com.lesconstructionssapete.stempyerp.domain.constant.TaxGroup;
import com.lesconstructionssapete.stempyerp.domain.constant.TaxGroupLine;

/*
 * Receives a List of subtotals and une TaxGroup
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

  public static TaxResult applyTaxes(List<BigDecimal> subtotals, TaxGroup taxGroup) {

    if (subtotals == null || subtotals.isEmpty()) {
      return new TaxResult(List.of(), BigDecimal.ZERO.setScale(2));
    }

    List<BigDecimal> totalLineTaxes = new ArrayList<>();
    for (int i = 0; i < subtotals.size(); i++) {
      totalLineTaxes.add(BigDecimal.ZERO.setScale(2));
    }

    BigDecimal totalTaxSum = BigDecimal.ZERO.setScale(2);

    for (TaxGroupLine taxRate : taxGroup.getRates()) {

      BigDecimal rate = BigDecimal.valueOf(taxRate.getTaxRate().getRate())
          .divide(BigDecimal.valueOf(100), 6, RoundingMode.HALF_UP);

      BigDecimal subtotalSum = subtotals.stream()
          .reduce(BigDecimal.ZERO, BigDecimal::add);

      BigDecimal expectedTotalTax = subtotalSum
          .multiply(rate)
          .setScale(2, RoundingMode.HALF_UP);

      class Line {
        BigDecimal ideal;
        BigDecimal rounded;
        BigDecimal remainder;
        int index;
      }

      List<Line> lines = new ArrayList<>();
      BigDecimal lineTaxSum = BigDecimal.ZERO;

      // Step 1: calculate per-line values
      for (int i = 0; i < subtotals.size(); i++) {
        BigDecimal subtotal = subtotals.get(i);

        Line line = new Line();
        line.index = i;

        line.ideal = subtotal.multiply(rate);
        line.rounded = line.ideal.setScale(2, RoundingMode.HALF_UP);

        // Fractional cents part
        line.remainder = line.ideal
            .subtract(line.ideal.setScale(2, RoundingMode.DOWN));

        lines.add(line);
        lineTaxSum = lineTaxSum.add(line.rounded);
      }

      // Step 2: distribute rounding difference
      BigDecimal diff = expectedTotalTax.subtract(lineTaxSum);

      if (diff.compareTo(BigDecimal.ZERO) != 0) {
        BigDecimal penny = new BigDecimal("0.01");
        int steps = diff.abs()
            .divideToIntegralValue(penny)
            .intValue();

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

      // Step 3: accumulate results per line
      for (Line l : lines) {
        totalLineTaxes.set(
            l.index,
            totalLineTaxes.get(l.index).add(l.rounded));
      }

      totalTaxSum = totalTaxSum.add(lineTaxSum);
    }

    return new TaxResult(totalLineTaxes, totalTaxSum);
  }
}