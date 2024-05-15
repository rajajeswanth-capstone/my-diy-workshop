package com.doityourself.workshop.features.project.material.command;

import com.doityourself.workshop.common.command.CommandDTO;
import com.doityourself.workshop.common.constants.ContextConstants;
import com.doityourself.workshop.features.project.material.validation.GetProjectMaterialValidations;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

public class GetProjectMaterialRequestValidationCommandTest {
  @Test
  public void testProcess() {
    // Initialize
    GetProjectMaterialValidations mockGetProjectMaterialValidations = Mockito.mock(GetProjectMaterialValidations.class);

    CommandDTO commandDTO = CommandDTO.builder().add(ContextConstants.CONTEXT_MATERIAL_ID, 1L).build();

    GetProjectMaterialRequestValidationCommand command = new GetProjectMaterialRequestValidationCommand();
    command.validations = mockGetProjectMaterialValidations;

    // Define Mocks
    Mockito.doNothing().when(mockGetProjectMaterialValidations).validateGetProjectMaterialRequest(1L);

    // Execute
    Exception expectedException = null;
    try {
      command.process(commandDTO);
    } catch (Exception exception) {
      expectedException = exception;
    }

    // Verify
    Mockito.verify(mockGetProjectMaterialValidations, Mockito.times(1)).validateGetProjectMaterialRequest(1L);

    // Assertions
    assert expectedException == null;
  }
}
