package com.doityourself.workshop.features.project.media.representation;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
 * Project Detail Local Resource Representation
 */
@Data
@NoArgsConstructor
@SuperBuilder(toBuilder = true)
public class ProjectDetailLocalResourceRepresentation {
  private Long id;
  private Long localResourceSequence;
  private String description;
  private String type;
  private String resourceType;
  private String link;
  private String title;
  private Integer displaySequence;
}
