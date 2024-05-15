package com.doityourself.workshop.common.utility;

import java.text.DecimalFormat;

/**
 * Decimal Utility
 */
public class DecimalUtility {
  /**
   * Method to format decimal with a provided {@link DecimalFormat}
   *
   * @param decimalFormat {@link DecimalFormat}
   * @param input {@link Double}
   * @return {@link Double}
   */
  public static Double formatDecimal(DecimalFormat decimalFormat, Double input) {
    return new Double(decimalFormat.format(input));
  }
}
