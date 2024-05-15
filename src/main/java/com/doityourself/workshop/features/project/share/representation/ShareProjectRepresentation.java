package com.doityourself.workshop.features.project.share.representation;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
 * Share Project Representation
 */
@Data
@NoArgsConstructor
@SuperBuilder(toBuilder = true)
public class ShareProjectRepresentation {
  private Long sharedUserId;
  private Long projectId;
}
