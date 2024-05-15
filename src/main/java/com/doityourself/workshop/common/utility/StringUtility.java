package com.doityourself.workshop.common.utility;

import java.util.Objects;

/**
 * String Utility
 */
public class StringUtility {
  /**
   * Method to check if string is empty
   *
   * @param string {@link String}
   * @return boolean
   */
  public static boolean isEmpty(String string) {
    return Objects.isNull(string) || string.length() == 0;
  }
}
