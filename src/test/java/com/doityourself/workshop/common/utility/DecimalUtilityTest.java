package com.doityourself.workshop.common.utility;

import org.junit.jupiter.api.Test;

import java.text.DecimalFormat;

public class DecimalUtilityTest {
  @Test
  public void testFormatDecimal() {
    // Execute
    Exception expectedException = null;
    Double result = null;
    try {
      result = DecimalUtility.formatDecimal(new DecimalFormat("##.00"), 200.2345);
    } catch (Exception exception) {
      expectedException = exception;
    }

    // Assertions
    assert expectedException == null;
    assert result == 200.23;
  }
}
