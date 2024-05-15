package com.doityourself.workshop.features.project.material.representation;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Singular;
import lombok.experimental.SuperBuilder;

import java.util.List;
import java.util.Map;

/**
 * Project Detail Representation
 */
@Data
@NoArgsConstructor
@SuperBuilder(toBuilder = true)
public class ProjectDetailRepresentation {
  private Long id;
  private String title;
  private String description;
  private Double totalProjectCost;
  private Double budget;
  private Boolean overBudget;
  private Double overBudgetAmount;
  @Singular("addInstruction")
  private List<ProjectDetailMaterialRepresentation> materials;
  @Singular("addVendor")
  private List<String> vendors;
  private Map<String, List<ProjectDetailMaterialRepresentation>> materialsByVendors;
}
