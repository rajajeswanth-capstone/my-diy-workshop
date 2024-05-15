package com.doityourself.workshop.features.project.detail.command;

import com.doityourself.workshop.common.command.CommandDTO;
import com.doityourself.workshop.common.constants.ContextConstants;
import com.doityourself.workshop.features.project.detail.validation.DeleteProjectInstructionValidations;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

public class DeleteProjectInstructionRequestValidationCommandTest {
  @Test
  public void testProcess() {
    // Initialize
    DeleteProjectInstructionValidations mockDeleteProjectInstructionValidations = Mockito.mock(DeleteProjectInstructionValidations.class);

    CommandDTO commandDTO = CommandDTO.builder().add(ContextConstants.CONTEXT_INSTRUCTION_ID, 1L).build();

    DeleteProjectInstructionRequestValidationCommand command = new DeleteProjectInstructionRequestValidationCommand();
    command.validations = mockDeleteProjectInstructionValidations;

    // Define Mocks
    Mockito.doNothing().when(mockDeleteProjectInstructionValidations).validateDeleteProjectInstructionRequest(1L);

    // Execute
    Exception expectedException = null;
    try {
      command.process(commandDTO);
    } catch (Exception exception) {
      expectedException = exception;
    }

    // Verify
    Mockito.verify(mockDeleteProjectInstructionValidations, Mockito.times(1)).validateDeleteProjectInstructionRequest(1L);

    // Assertions
    assert expectedException == null;
  }
}
