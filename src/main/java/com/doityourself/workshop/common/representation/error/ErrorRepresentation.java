package com.doityourself.workshop.common.representation.error;

import lombok.Data;
import lombok.experimental.SuperBuilder;

/**
 * Error Representation
 *
 */
@Data
@SuperBuilder(toBuilder = true)
public class ErrorRepresentation {
  private String errorMessage;
}
