package com.doityourself.workshop.features.project.material.representation;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
 * Project Detail Material Representation
 */
@Data
@NoArgsConstructor
@SuperBuilder(toBuilder = true)
public class ProjectDetailMaterialRepresentation {
  private Long id;
  private Long materialSequence;
  private String materialDescription;
  private Long units;
  private Double pricePerUnit;
  private Double cost;
  private String vendor;
}
