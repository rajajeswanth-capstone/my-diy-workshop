package com.doityourself.workshop.features.signup.representation;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
 * Signup User Representation
 */
@Data
@SuperBuilder(toBuilder = true)
@NoArgsConstructor
public class SignupUserRepresentation {
  private String userName;
  private String password;
  private String name;
}
