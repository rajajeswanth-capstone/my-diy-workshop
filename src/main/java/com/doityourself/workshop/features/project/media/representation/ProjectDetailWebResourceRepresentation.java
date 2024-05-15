package com.doityourself.workshop.features.project.media.representation;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
 * Project Detail Web Resource Representation
 */
@Data
@NoArgsConstructor
@SuperBuilder(toBuilder = true)
public class ProjectDetailWebResourceRepresentation {
  private Long id;
  private Long webResourceSequence;
  private String description;
  private String resourceType;
  private String type;
  private String link;
  private String title;
  private Integer displaySequence;
}
