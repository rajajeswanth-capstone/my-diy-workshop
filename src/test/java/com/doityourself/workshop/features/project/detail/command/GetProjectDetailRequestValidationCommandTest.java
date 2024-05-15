package com.doityourself.workshop.features.project.detail.command;

import com.doityourself.workshop.common.command.CommandDTO;
import com.doityourself.workshop.common.constants.ContextConstants;
import com.doityourself.workshop.features.project.detail.validation.GetProjectDetailValidations;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

public class GetProjectDetailRequestValidationCommandTest {
  @Test
  public void testProcess() {
    // Initialize
    GetProjectDetailValidations mockGetProjectDetailValidations = Mockito.mock(GetProjectDetailValidations.class);

    GetProjectDetailRequestValidationCommand command = new GetProjectDetailRequestValidationCommand();
    command.validations = mockGetProjectDetailValidations;

    CommandDTO commandDTO = CommandDTO.builder().add(ContextConstants.CONTEXT_PROJECT_ID, 1L).build();

    // Define Mocks
    Mockito.doNothing().when(mockGetProjectDetailValidations).validateGetProjectDetailRequest(1L);

    // Execute
    Exception expectedException = null;
    try {
      command.process(commandDTO);
    } catch (Exception exception) {
      expectedException = exception;
    }

    // Verify
    Mockito.verify(mockGetProjectDetailValidations, Mockito.times(1)).validateGetProjectDetailRequest(1L);

    // Assertions
    assert expectedException == null;
  }
}
