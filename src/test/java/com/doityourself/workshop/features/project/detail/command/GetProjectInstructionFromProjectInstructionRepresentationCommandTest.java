package com.doityourself.workshop.features.project.detail.command;

import com.doityourself.workshop.common.command.CommandDTO;
import com.doityourself.workshop.common.constants.ContextConstants;
import com.doityourself.workshop.common.constants.EntityConstants;
import com.doityourself.workshop.database.entities.DiyProjectInstruction;
import com.doityourself.workshop.features.project.detail.dao.ProjectDetailDao;
import com.doityourself.workshop.features.project.detail.representation.ProjectDetailInstructionRepresentation;
import com.doityourself.workshop.features.project.detail.validation.GetProjectInstructionValidations;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

public class GetProjectInstructionFromProjectInstructionRepresentationCommandTest {
  @Test
  public void testProcess() {
    // Initialize
    ProjectDetailDao mockProjectDetailDao = Mockito.mock(ProjectDetailDao.class);

    ProjectDetailInstructionRepresentation projectDetailInstructionRepresentation = ProjectDetailInstructionRepresentation.builder().id(1L).build();

    CommandDTO commandDTO = CommandDTO
        .builder()
        .add(ContextConstants.CONTEXT_PROJECT_INSTRUCTION, projectDetailInstructionRepresentation)
        .build();

    GetProjectInstructionFromProjectInstructionRepresentationCommand command = new GetProjectInstructionFromProjectInstructionRepresentationCommand();
    command.projectDetailDao = mockProjectDetailDao;

    DiyProjectInstruction diyProjectInstruction = DiyProjectInstruction.builder().id(1L).build();

    // Define Mocks
    Mockito.when(mockProjectDetailDao.findProjectInstructionByInstructionId(1L)).thenReturn(diyProjectInstruction);

    // Execute
    Exception expectedException = null;
    try {
      command.process(commandDTO);
    } catch (Exception exception) {
      expectedException = exception;
    }

    // Verify
    Mockito.verify(mockProjectDetailDao, Mockito.times(1)).findProjectInstructionByInstructionId(1L);

    // Assertions
    assert expectedException == null;
    assert ((DiyProjectInstruction)commandDTO.get(EntityConstants.ENTITY_DIY_PROJECT_INSTRUCTION)).getId() == 1L;
  }

  @Test
  public void testPostProcess() {
    // Initialize
    GetProjectInstructionValidations mockGetProjectInstructionValidations = Mockito.mock(GetProjectInstructionValidations.class);

    GetProjectInstructionFromProjectInstructionRepresentationCommand command = new GetProjectInstructionFromProjectInstructionRepresentationCommand();
    command.validations = mockGetProjectInstructionValidations;

    DiyProjectInstruction diyProjectInstruction = DiyProjectInstruction.builder().id(1L).build();

    CommandDTO commandDTO = CommandDTO.builder().add(EntityConstants.ENTITY_DIY_PROJECT_INSTRUCTION, diyProjectInstruction).build();

    // Define Mocks
    Mockito.doNothing().when(mockGetProjectInstructionValidations).validateDiyProjectInstructionEntity(diyProjectInstruction);

    // Execute
    Exception expectedException = null;
    try {
      command.postProcess(commandDTO);
    } catch (Exception exception) {
      expectedException = exception;
    }

    // Verify
    Mockito.verify(mockGetProjectInstructionValidations, Mockito.times(1)).validateDiyProjectInstructionEntity(diyProjectInstruction);

    // Assertions
    assert expectedException == null;
  }
}
