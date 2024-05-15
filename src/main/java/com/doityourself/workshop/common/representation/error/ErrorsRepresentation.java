package com.doityourself.workshop.common.representation.error;

import lombok.Data;
import lombok.experimental.SuperBuilder;

import java.util.List;
import java.util.Map;

/**
 * Error list Representation
 *
 */
@Data
@SuperBuilder(toBuilder = true)
public class ErrorsRepresentation {
  private List<String> errorMessages;
  private Map<String, String> fieldErrorMessages;
}
