package com.doityourself.workshop.features.project.detail.command;

import com.doityourself.workshop.common.command.CommandDTO;
import com.doityourself.workshop.common.constants.ContextConstants;
import com.doityourself.workshop.common.constants.EntityConstants;
import com.doityourself.workshop.database.entities.DiyProjectInstruction;
import com.doityourself.workshop.features.project.detail.representation.ProjectDetailInstructionRepresentation;
import org.junit.jupiter.api.Test;

public class GetProjectInstructionByInstructionIdResponseCommandTest {
  @Test
  public void testProcess() {
    // Initialize
    GetProjectInstructionByInstructionIdResponseCommand command = new GetProjectInstructionByInstructionIdResponseCommand();

    DiyProjectInstruction diyProjectInstruction = DiyProjectInstruction.builder().id(1L).title("title").instructionSequence(1L).instruction("instruction").build();

    CommandDTO commandDTO = CommandDTO
        .builder()
        .add(EntityConstants.ENTITY_DIY_PROJECT_INSTRUCTION, diyProjectInstruction)
        .build();

    // Execute
    Exception expectedException = null;
    try {
      command.process(commandDTO);
    } catch (Exception exception) {
      expectedException = exception;
    }

    // Assertions
    ProjectDetailInstructionRepresentation projectDetailInstructionRepresentation = (ProjectDetailInstructionRepresentation) commandDTO.get(ContextConstants.CONTEXT_PROJECT_INSTRUCTION);
    assert expectedException == null;
    assert projectDetailInstructionRepresentation != null;
    assert projectDetailInstructionRepresentation.getId() == 1L;
    assert projectDetailInstructionRepresentation.getTitle().equals("title");
    assert projectDetailInstructionRepresentation.getInstructionSequence() == 1L;
    assert projectDetailInstructionRepresentation.getInstruction().equals("instruction");
  }
}
