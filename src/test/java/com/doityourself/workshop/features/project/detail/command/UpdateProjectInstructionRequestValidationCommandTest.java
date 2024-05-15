package com.doityourself.workshop.features.project.detail.command;

import com.doityourself.workshop.common.command.CommandDTO;
import com.doityourself.workshop.common.constants.ContextConstants;
import com.doityourself.workshop.features.project.detail.representation.ProjectDetailInstructionRepresentation;
import com.doityourself.workshop.features.project.detail.validation.UpdateProjectInstructionValidations;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

public class UpdateProjectInstructionRequestValidationCommandTest {
  @Test
  public void testProcess() {
    // Initialize
    UpdateProjectInstructionValidations mockUpdateProjectInstructionValidations = Mockito.mock(UpdateProjectInstructionValidations.class);

    UpdateProjectInstructionRequestValidationCommand command = new UpdateProjectInstructionRequestValidationCommand();
    command.validations = mockUpdateProjectInstructionValidations;

    ProjectDetailInstructionRepresentation projectDetailInstructionRepresentation = ProjectDetailInstructionRepresentation.builder().id(1L).build();

    CommandDTO commandDTO = CommandDTO
        .builder()
        .add(ContextConstants.CONTEXT_PROJECT_INSTRUCTION, projectDetailInstructionRepresentation)
        .build();

    // Define Mocks
    Mockito.doNothing().when(mockUpdateProjectInstructionValidations).validateUpdateInstructionRequest(projectDetailInstructionRepresentation);

    // Execute
    Exception expectedException = null;
    try {
      command.process(commandDTO);
    } catch (Exception exception) {
      expectedException = exception;
    }

    // Verify
    Mockito.verify(mockUpdateProjectInstructionValidations, Mockito.times(1)).validateUpdateInstructionRequest(projectDetailInstructionRepresentation);

    // Assertions
    assert expectedException == null;
  }
}
