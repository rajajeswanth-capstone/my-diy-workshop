package com.doityourself.workshop.features.project.detail.command;

import com.doityourself.workshop.common.command.CommandDTO;
import com.doityourself.workshop.common.command.ICommand;
import com.doityourself.workshop.common.constants.ContextConstants;
import com.doityourself.workshop.common.constants.EntityConstants;
import com.doityourself.workshop.database.entities.DiyProjectInstruction;
import com.doityourself.workshop.features.project.detail.representation.ProjectDetailInstructionRepresentation;
import org.springframework.stereotype.Component;

/**
 * Get Project Instruction By Id Response Command
 */
@Component
public class GetProjectInstructionByInstructionIdResponseCommand implements ICommand<CommandDTO> {
  /**
   * Build Project instruction by id response
   *
   * @param dto {@link CommandDTO}
   */
  @Override
  public void process(CommandDTO dto) {
    DiyProjectInstruction diyProjectInstruction = (DiyProjectInstruction) dto.get(EntityConstants.ENTITY_DIY_PROJECT_INSTRUCTION);
    ProjectDetailInstructionRepresentation projectDetailInstructionRepresentation = ProjectDetailInstructionRepresentation.builder()
        .id(diyProjectInstruction.getId())
        .title(diyProjectInstruction.getTitle())
        .instructionSequence(diyProjectInstruction.getInstructionSequence())
        .instruction(diyProjectInstruction.getInstruction())
        .build();

    dto.add(ContextConstants.CONTEXT_PROJECT_INSTRUCTION, projectDetailInstructionRepresentation);
  }
}
