package com.doityourself.workshop.features.project.detail.command;

import com.doityourself.workshop.common.command.CommandDTO;
import com.doityourself.workshop.common.constants.ContextConstants;
import com.doityourself.workshop.common.constants.EntityConstants;
import com.doityourself.workshop.database.entities.DiyProject;
import com.doityourself.workshop.database.entities.DiyProjectInstruction;
import com.doityourself.workshop.features.project.detail.representation.ProjectDetailInstructionRepresentation;
import com.doityourself.workshop.features.project.detail.representation.ProjectDetailRepresentation;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

public class GetProjectDetailResponseCommandTest {
  @Test
  public void testProcess() {
    // Initialize
    GetProjectDetailResponseCommand command = new GetProjectDetailResponseCommand();

    DiyProject diyProject = DiyProject.builder().id(1L).title("project").shortDescription("short description").description("description").build();
    List<DiyProjectInstruction> instructions = new ArrayList<>();
    instructions.add(DiyProjectInstruction.builder().id(1L).title("title").instructionSequence(1L).instruction("instruction").build());

    CommandDTO commandDTO = CommandDTO
        .builder()
        .add(EntityConstants.ENTITY_DIY_PROJECT, diyProject)
        .add(EntityConstants.ENTITY_DIY_PROJECT_INSTRUCTIONS, instructions)
        .build();

    // Execute
    Exception expectedException = null;
    try {
      command.process(commandDTO);
    } catch (Exception exception) {
      expectedException = exception;
    }

    // Assertions
    ProjectDetailRepresentation projectDetailRepresentation = (ProjectDetailRepresentation) commandDTO.get(ContextConstants.CONTEXT_PROJECT_DETAIL);
    assert expectedException == null;
    assert projectDetailRepresentation != null;
    assert projectDetailRepresentation.getId() == 1L;
    assert projectDetailRepresentation.getTitle().equals("project");
    assert projectDetailRepresentation.getShortDescription().equals("short description");
    assert projectDetailRepresentation.getDescription().equals("description");

    List<ProjectDetailInstructionRepresentation> projectDetailInstructionRepresentations = projectDetailRepresentation.getInstructions();
    assert projectDetailInstructionRepresentations.size() == 1;
    assert projectDetailInstructionRepresentations.get(0).getId() == 1L;
    assert projectDetailInstructionRepresentations.get(0).getTitle().equals("title");
    assert projectDetailInstructionRepresentations.get(0).getInstruction().equals("instruction");
    assert projectDetailInstructionRepresentations.get(0).getInstructionSequence() == 1L;
  }
}
