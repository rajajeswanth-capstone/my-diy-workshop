package com.doityourself.workshop.features.project.media.command;

import com.doityourself.workshop.common.command.CommandDTO;
import com.doityourself.workshop.common.constants.ContextConstants;
import com.doityourself.workshop.features.project.media.validation.GetProjectLocalResourceValidations;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

public class GetProjectLocalResourceRequestValidationCommandTest {
  @Test
  public void testProcess() {
    // Initialize
    GetProjectLocalResourceValidations mockGetProjectLocalResourceValidations = Mockito.mock(GetProjectLocalResourceValidations.class);

    CommandDTO commandDTO = CommandDTO.builder().add(ContextConstants.CONTEXT_LOCAL_RESOURCE_ID, 1L).build();

    GetProjectLocalResourceRequestValidationCommand command = new GetProjectLocalResourceRequestValidationCommand();
    command.validations = mockGetProjectLocalResourceValidations;

    // Define Mocks
    Mockito.doNothing().when(mockGetProjectLocalResourceValidations).validateGetProjectLocalResourceRequest(1L);

    // Execute
    Exception expectedException = null;
    try {
      command.process(commandDTO);
    } catch (Exception exception) {
      expectedException = exception;
    }

    // Verify
    Mockito.verify(mockGetProjectLocalResourceValidations, Mockito.times(1)).validateGetProjectLocalResourceRequest(1L);

    // Assertions
    assert expectedException == null;
  }
}
