package com.doityourself.workshop.features.project.material.command;

import com.doityourself.workshop.common.command.CommandDTO;
import com.doityourself.workshop.common.constants.ContextConstants;
import com.doityourself.workshop.features.project.material.representation.ProjectDetailMaterialRepresentation;
import com.doityourself.workshop.features.project.material.validation.UpdateProjectMaterialValidations;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

public class UpdateProjectMaterialRequestValidationCommandTest {
  @Test
  public void testProcess() {
    // Initialize
    UpdateProjectMaterialValidations mockUpdateProjectMaterialValidations = Mockito.mock(UpdateProjectMaterialValidations.class);

    ProjectDetailMaterialRepresentation projectDetailMaterialRepresentation = ProjectDetailMaterialRepresentation.builder().id(1L).build();
    CommandDTO commandDTO = CommandDTO
        .builder()
        .add(ContextConstants.CONTEXT_PROJECT_MATERIAL, projectDetailMaterialRepresentation)
        .build();

    UpdateProjectMaterialRequestValidationCommand command = new UpdateProjectMaterialRequestValidationCommand();
    command.validations = mockUpdateProjectMaterialValidations;

    // Define Mocks
    Mockito.doNothing().when(mockUpdateProjectMaterialValidations).validateUpdateMaterialRequest(projectDetailMaterialRepresentation);

    // Execute
    Exception expectedException = null;
    try {
      command.process(commandDTO);
    } catch (Exception exception) {
      expectedException = exception;
    }

    // Verify
    Mockito.verify(mockUpdateProjectMaterialValidations, Mockito.times(1)).validateUpdateMaterialRequest(projectDetailMaterialRepresentation);

    // Assertions
    assert expectedException == null;
  }
}
