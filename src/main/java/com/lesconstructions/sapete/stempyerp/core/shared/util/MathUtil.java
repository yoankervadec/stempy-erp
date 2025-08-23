package com.lesconstructions.sapete.stempyerp.core.shared.util;

import java.math.BigDecimal;
import java.math.RoundingMode;

public final class MathUtil {

  private MathUtil() {
  }

  public static BigDecimal calculateSubtotal(int quantity, BigDecimal unitPrice, boolean round) {

    return maybeRound(unitPrice.multiply(BigDecimal.valueOf(quantity)), round);
  }

  public static BigDecimal calculateSubtotal(int quantity, BigDecimal unitPrice, BigDecimal discountPercent,
      boolean round) {
    BigDecimal beforeDiscount = calculateSubtotal(quantity, unitPrice, false);
    BigDecimal discountRate = discountPercent.divide(BigDecimal.valueOf(100), 4, RoundingMode.HALF_UP);
    BigDecimal subtotal = beforeDiscount.subtract(beforeDiscount.multiply(discountRate));

    return maybeRound(subtotal, round);
  }

  public static double calculateBoardfeet(int quantity, double width, double thickness, double length, boolean round) {
    double boardfeet = width * thickness / length * 12;

    return maybeRound(BigDecimal.valueOf(boardfeet * quantity)
        .setScale(2, RoundingMode.HALF_UP), round)
        .doubleValue();
  }

  private static BigDecimal maybeRound(BigDecimal value, boolean round) {

    return round ? value.setScale(2, RoundingMode.HALF_UP) : value;
  }
}