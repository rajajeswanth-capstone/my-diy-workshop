package com.doityourself.workshop.features.project.detail.command;

import com.doityourself.workshop.common.command.CommandDTO;
import com.doityourself.workshop.common.constants.ContextConstants;
import com.doityourself.workshop.features.project.detail.validation.GetProjectInstructionValidations;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

public class GetProjectInstructionRequestValidationCommandTest {
  @Test
  public void testProcess() {
    // Initialize
    GetProjectInstructionValidations mockGetProjectInstructionValidations = Mockito.mock(GetProjectInstructionValidations.class);

    GetProjectInstructionRequestValidationCommand command = new GetProjectInstructionRequestValidationCommand();
    command.validations = mockGetProjectInstructionValidations;

    CommandDTO commandDTO = CommandDTO.builder().add(ContextConstants.CONTEXT_INSTRUCTION_ID, 1L).build();

    // Define Mocks
    Mockito.doNothing().when(mockGetProjectInstructionValidations).validateGetProjectInstructionRequest(1L);

    // Execute
    Exception expectedException = null;
    try {
      command.process(commandDTO);
    } catch (Exception exception) {
      expectedException = exception;
    }

    // Verify
    Mockito.verify(mockGetProjectInstructionValidations, Mockito.times(1)).validateGetProjectInstructionRequest(1L);

    // Assertions
    assert expectedException == null;
  }
}
