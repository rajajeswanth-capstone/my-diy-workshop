package com.doityourself.workshop.features.project.detail.representation;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
 * Project Detail Instruction Representation
 */
@Data
@NoArgsConstructor
@SuperBuilder(toBuilder = true)
public class ProjectDetailInstructionRepresentation {
  private Long id;
  private Long instructionSequence;
  private String instruction;
  private String title;
}
