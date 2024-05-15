package com.doityourself.workshop.features.project.detail.command;

import com.doityourself.workshop.common.command.CommandDTO;
import com.doityourself.workshop.common.constants.ContextConstants;
import com.doityourself.workshop.common.constants.EntityConstants;
import com.doityourself.workshop.database.entities.DiyProject;
import com.doityourself.workshop.database.entities.DiyProjectInstruction;
import com.doityourself.workshop.features.project.detail.dao.ProjectDetailDao;
import com.doityourself.workshop.features.project.detail.representation.ProjectDetailInstructionRepresentation;
import com.doityourself.workshop.features.project.detail.validation.UpdateProjectInstructionValidations;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;

public class UpdateProjectInstructionCommandTest {
  @Test
  public void testProcess() {
    // Initialize
    ProjectDetailDao mockProjectDetailDao = Mockito.mock(ProjectDetailDao.class);

    ArgumentCaptor<DiyProjectInstruction> diyProjectInstructionCaptor = ArgumentCaptor.forClass(DiyProjectInstruction.class);

    UpdateProjectInstructionCommand command = new UpdateProjectInstructionCommand();
    command.projectDetailDao = mockProjectDetailDao;

    DiyProject diyProject = new DiyProject();
    diyProject.setId(1L);

    DiyProjectInstruction diyProjectInstruction = DiyProjectInstruction.builder().id(1L).build();

    ProjectDetailInstructionRepresentation projectDetailInstructionRepresentation = ProjectDetailInstructionRepresentation
        .builder()
        .id(1L).title("title").instructionSequence(1L).instruction("instruction")
        .build();

    CommandDTO commandDTO = CommandDTO
        .builder()
        .add(EntityConstants.ENTITY_DIY_PROJECT, diyProject)
        .add(EntityConstants.ENTITY_DIY_PROJECT_INSTRUCTION, diyProjectInstruction)
        .add(ContextConstants.CONTEXT_PROJECT_INSTRUCTION, projectDetailInstructionRepresentation)
        .build();

    // Define Mocks
    Mockito.when(mockProjectDetailDao.saveProjectInstruction(Mockito.any())).thenReturn(diyProjectInstruction);

    // Execute
    Exception expectedException = null;
    try {
      command.process(commandDTO);
    } catch (Exception exception) {
      expectedException = exception;
    }

    // Verify
    Mockito.verify(mockProjectDetailDao, Mockito.times(1)).saveProjectInstruction(diyProjectInstructionCaptor.capture());

    // Assertions
    assert expectedException == null;
    assert diyProjectInstructionCaptor.getValue().getDiyProject() == diyProject;
    assert diyProjectInstructionCaptor.getValue().getTitle().equals("title");
    assert diyProjectInstructionCaptor.getValue().getInstructionSequence() == 1L;
    assert diyProjectInstructionCaptor.getValue().getInstruction().equals("instruction");
    assert commandDTO.get(EntityConstants.ENTITY_DIY_PROJECT_INSTRUCTION) == diyProjectInstruction;
  }

  @Test
  public void testPostProcess() {
    // Initialize
    UpdateProjectInstructionValidations mockUpdateProjectInstructionValidations = Mockito.mock(UpdateProjectInstructionValidations.class);

    UpdateProjectInstructionCommand command = new UpdateProjectInstructionCommand();
    command.validations = mockUpdateProjectInstructionValidations;

    DiyProjectInstruction diyProjectInstruction = DiyProjectInstruction.builder().id(1L).build();

    CommandDTO commandDTO = CommandDTO
        .builder()
        .add(EntityConstants.ENTITY_DIY_PROJECT_INSTRUCTION, diyProjectInstruction)
        .build();

    // Define Mocks
    Mockito.doNothing().when(mockUpdateProjectInstructionValidations).validateDiyProjectInstructionEntity(diyProjectInstruction);

    // Execute
    Exception expectedException = null;
    try {
      command.postProcess(commandDTO);
    } catch (Exception exception) {
      expectedException = exception;
    }

    // Verify
    Mockito.verify(mockUpdateProjectInstructionValidations, Mockito.times(1)).validateDiyProjectInstructionEntity(diyProjectInstruction);

    // Assertions
    assert expectedException == null;
  }
}
