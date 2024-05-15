package com.doityourself.workshop.common.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * Resource Type
 */
@Getter
@RequiredArgsConstructor
public enum WebResourceType {
  WEB("weblinks");

  private final String value;
}
