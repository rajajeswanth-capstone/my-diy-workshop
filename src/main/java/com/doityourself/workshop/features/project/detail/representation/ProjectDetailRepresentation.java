package com.doityourself.workshop.features.project.detail.representation;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Singular;
import lombok.experimental.SuperBuilder;

import java.util.List;

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
  private String shortDescription;
  @Singular("addInstruction")
  private List<ProjectDetailInstructionRepresentation> instructions;
}
