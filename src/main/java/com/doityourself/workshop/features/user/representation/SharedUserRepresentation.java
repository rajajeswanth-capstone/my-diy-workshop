package com.doityourself.workshop.features.user.representation;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
 * Shared User Representation
 */
@Data
@NoArgsConstructor
@SuperBuilder(toBuilder = true)
public class SharedUserRepresentation {
  private Long id;
  private String userName;
  private String name;
}
