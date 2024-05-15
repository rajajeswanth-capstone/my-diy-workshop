package com.doityourself.workshop.common.exception;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.Map;

/**
 * Common parent exception class
 */
@NoArgsConstructor
@Getter
@Setter
public class DiyWorkshopException extends RuntimeException {
  protected List<String> messages;
  protected Map<String, String> fieldMessages;
}
