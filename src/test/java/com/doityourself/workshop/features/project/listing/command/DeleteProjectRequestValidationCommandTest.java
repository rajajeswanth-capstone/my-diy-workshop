package com.doityourself.workshop.features.project.listing.command;

import com.doityourself.workshop.common.command.CommandDTO;
import com.doityourself.workshop.common.constants.ContextConstants;
import com.doityourself.workshop.features.project.listing.validation.DeleteProjectValidations;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

public class DeleteProjectRequestValidationCommandTest {
  @Test
  public void testProcess() {
    // Initialize
    DeleteProjectValidations mockDeleteProjectValidations = Mockito.mock(DeleteProjectValidations.class);

    CommandDTO commandDTO = CommandDTO.builder().add(ContextConstants.CONTEXT_PROJECT_ID, 1L).build();

    DeleteProjectRequestValidationCommand command = new DeleteProjectRequestValidationCommand();
    command.validations = mockDeleteProjectValidations;

    // Define Mocks
    Mockito.doNothing().when(mockDeleteProjectValidations).validateDeleteRequest(1L);

    // Execute
    Exception expectedException = null;
    try {
      command.process(commandDTO);
    } catch (Exception exception) {
      expectedException = exception;
    }

    // Verify
    Mockito.verify(mockDeleteProjectValidations, Mockito.times(1)).validateDeleteRequest(1L);

    // Assertions
    assert expectedException == null;
  }
}
