package com.doityourself.workshop.features.project.media.command;

import com.doityourself.workshop.common.command.CommandDTO;
import com.doityourself.workshop.common.constants.ContextConstants;
import com.doityourself.workshop.features.project.media.validation.DeleteProjectWebResourceValidations;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

public class DeleteProjectWebResourceRequestValidationCommandTest {
  @Test
  public void testProcess() {
    // Initialize
    DeleteProjectWebResourceValidations mockDeleteProjectWebResourceValidations = Mockito.mock(DeleteProjectWebResourceValidations.class);

    CommandDTO commandDTO = CommandDTO.builder().add(ContextConstants.CONTEXT_WEB_RESOURCE_ID, 1L).build();

    DeleteProjectWebResourceRequestValidationCommand command = new DeleteProjectWebResourceRequestValidationCommand();
    command.validations = mockDeleteProjectWebResourceValidations;

    // Define Mocks
    Mockito.doNothing().when(mockDeleteProjectWebResourceValidations).validateDeleteProjectWebResourceRequest(1L);

    // Execute
    Exception expectedException = null;
    try {
      command.process(commandDTO);
    } catch (Exception exception) {
      expectedException = exception;
    }

    // Verify
    Mockito.verify(mockDeleteProjectWebResourceValidations, Mockito.times(1)).validateDeleteProjectWebResourceRequest(1L);

    // Assertions
    assert expectedException == null;
  }
}
