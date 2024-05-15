package com.doityourself.workshop.features.project.media.command;

import com.doityourself.workshop.common.command.CommandDTO;
import com.doityourself.workshop.common.constants.ContextConstants;
import com.doityourself.workshop.features.project.media.validation.DeleteProjectLocalResourceValidations;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

public class DeleteProjectLocalResourceRequestValidationCommandTest {
  @Test
  public void testProcess() {
    // Initialize
    DeleteProjectLocalResourceValidations mockDeleteProjectLocalResourceValidations = Mockito.mock(DeleteProjectLocalResourceValidations.class);

    CommandDTO commandDTO = CommandDTO.builder().add(ContextConstants.CONTEXT_LOCAL_RESOURCE_ID, 1L).build();

    DeleteProjectLocalResourceRequestValidationCommand command = new DeleteProjectLocalResourceRequestValidationCommand();
    command.validations = mockDeleteProjectLocalResourceValidations;

    // Define Mocks
    Mockito.doNothing().when(mockDeleteProjectLocalResourceValidations).validateDeleteProjectLocalResourceRequest(1L);

    // Execute
    Exception expectedException = null;
    try {
      command.process(commandDTO);
    } catch (Exception exception) {
      expectedException = exception;
    }

    // Verify
    Mockito.verify(mockDeleteProjectLocalResourceValidations, Mockito.times(1)).validateDeleteProjectLocalResourceRequest(1L);

    // Assertions
    assert expectedException == null;
  }
}
