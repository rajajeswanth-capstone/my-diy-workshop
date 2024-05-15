package com.doityourself.workshop.features.project.material.command;

import com.doityourself.workshop.common.command.CommandDTO;
import com.doityourself.workshop.common.constants.ContextConstants;
import com.doityourself.workshop.features.project.material.validation.DeleteProjectMaterialValidations;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

public class DeleteProjectMaterialRequestValidationCommandTest {
  @Test
  public void testProcess() {
    // Initialize
    DeleteProjectMaterialValidations mockDeleteProjectMaterialValidations = Mockito.mock(DeleteProjectMaterialValidations.class);

    CommandDTO commandDTO = CommandDTO.builder().add(ContextConstants.CONTEXT_MATERIAL_ID, 1L).build();

    DeleteProjectMaterialRequestValidationCommand command = new DeleteProjectMaterialRequestValidationCommand();
    command.validations = mockDeleteProjectMaterialValidations;

    // Define Mocks
    Mockito.doNothing().when(mockDeleteProjectMaterialValidations).validateDeleteProjectMaterialRequest(1L);

    // Execute
    Exception expectedException = null;
    try {
      command.process(commandDTO);
    } catch (Exception exception) {
      expectedException = exception;
    }

    // Verify
    Mockito.verify(mockDeleteProjectMaterialValidations, Mockito.times(1)).validateDeleteProjectMaterialRequest(1L);

    // Assertions
    assert expectedException == null;
  }
}
