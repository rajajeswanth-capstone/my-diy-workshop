package com.doityourself.workshop.features.project.media.command;

import com.doityourself.workshop.common.command.CommandDTO;
import com.doityourself.workshop.common.constants.ContextConstants;
import com.doityourself.workshop.features.project.media.validation.GetProjectWebResourceValidations;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

public class GetProjectWebResourceRequestValidationCommandTest {
  @Test
  public void testProcess() {
    // Initialize
    GetProjectWebResourceValidations mockGetProjectWebResourceValidations = Mockito.mock(GetProjectWebResourceValidations.class);

    CommandDTO commandDTO = CommandDTO.builder().add(ContextConstants.CONTEXT_WEB_RESOURCE_ID, 1L).build();

    GetProjectWebResourceRequestValidationCommand command = new GetProjectWebResourceRequestValidationCommand();
    command.validations = mockGetProjectWebResourceValidations;

    // Define Mocks
    Mockito.doNothing().when(mockGetProjectWebResourceValidations).validateGetProjectWebResourceRequest(1L);

    // Execute
    Exception expectedException = null;
    try {
      command.process(commandDTO);
    } catch (Exception exception) {
      expectedException = exception;
    }

    // Verify
    Mockito.verify(mockGetProjectWebResourceValidations, Mockito.times(1)).validateGetProjectWebResourceRequest(1L);

    // Assertions
    assert expectedException == null;
  }
}
