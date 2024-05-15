package com.doityourself.workshop.common.utility;

import org.junit.jupiter.api.Test;

public class StringUtilityTest {
  @Test
  public void testIsEmpty() {
    // Initialize
    String input = "";

    // Execute
    boolean result = StringUtility.isEmpty(input);

    // Assertions
    assert result;
  }
}
