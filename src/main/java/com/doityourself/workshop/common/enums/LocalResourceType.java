package com.doityourself.workshop.common.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * Resource Type
 */
@Getter
@RequiredArgsConstructor
public enum LocalResourceType {
  IMAGE("pictures"),
  VIDEO("videos"),
  DOCUMENT("documents"),
  OTHER("other");

  private final String value;
}
