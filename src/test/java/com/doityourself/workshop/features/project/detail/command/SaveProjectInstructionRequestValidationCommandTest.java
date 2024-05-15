package com.doityourself.workshop.features.project.detail.command;

import com.doityourself.workshop.common.command.CommandDTO;
import com.doityourself.workshop.common.constants.ContextConstants;
import com.doityourself.workshop.features.project.detail.representation.ProjectDetailInstructionRepresentation;
import com.doityourself.workshop.features.project.detail.validation.SaveProjectInstructionValidations;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

public class SaveProjectInstructionRequestValidationCommandTest {
  @Test
  public void testProcess() {
    // Initialize
    SaveProjectInstructionValidations mockSaveProjectInstructionValidations = Mockito.mock(SaveProjectInstructionValidations.class);

    SaveProjectInstructionRequestValidationCommand command = new SaveProjectInstructionRequestValidationCommand();
    command.validations = mockSaveProjectInstructionValidations;

    ProjectDetailInstructionRepresentation projectDetailInstructionRepresentation = ProjectDetailInstructionRepresentation.builder().id(1L).build();

    CommandDTO commandDTO = CommandDTO
        .builder()
        .add(ContextConstants.CONTEXT_PROJECT_INSTRUCTION, projectDetailInstructionRepresentation)
        .build();

    // Define Mocks
    Mockito.doNothing().when(mockSaveProjectInstructionValidations).validateSaveInstructionRequest(projectDetailInstructionRepresentation);

    // Execute
    Exception expectedException = null;
    try {
      command.process(commandDTO);
    } catch (Exception exception) {
      expectedException = exception;
    }

    // Verify
    Mockito.verify(mockSaveProjectInstructionValidations, Mockito.times(1)).validateSaveInstructionRequest(projectDetailInstructionRepresentation);

    // Assertions
    assert expectedException == null;
  }
}
