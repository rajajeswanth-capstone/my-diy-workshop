package com.doityourself.workshop.features.login.representation;

import lombok.Data;
import lombok.experimental.SuperBuilder;

/**
 * LoggedIn User Representation
 */
@Data
@SuperBuilder(toBuilder = true)
public class LoggedInUserRepresentation {
  private String userName;
  private String name;
}
