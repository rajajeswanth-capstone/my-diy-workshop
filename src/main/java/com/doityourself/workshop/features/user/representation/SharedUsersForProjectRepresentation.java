package com.doityourself.workshop.features.user.representation;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.List;

/**
 * Shared User for Project Representation
 */
@Data
@NoArgsConstructor
@SuperBuilder(toBuilder = true)
public class SharedUsersForProjectRepresentation {
  private Long projectId;
  private List<SharedUserRepresentation> sharedUsers;
}
