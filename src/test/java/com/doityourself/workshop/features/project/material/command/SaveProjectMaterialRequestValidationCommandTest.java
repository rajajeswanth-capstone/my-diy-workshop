package com.doityourself.workshop.features.project.material.command;

import com.doityourself.workshop.common.command.CommandDTO;
import com.doityourself.workshop.common.constants.ContextConstants;
import com.doityourself.workshop.features.project.material.representation.ProjectDetailMaterialRepresentation;
import com.doityourself.workshop.features.project.material.validation.SaveProjectMaterialValidations;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

public class SaveProjectMaterialRequestValidationCommandTest {
  @Test
  public void testProcess() {
    // Initialize
    SaveProjectMaterialValidations mockSaveProjectMaterialValidations = Mockito.mock(SaveProjectMaterialValidations.class);

    ProjectDetailMaterialRepresentation projectDetailMaterialRepresentation = ProjectDetailMaterialRepresentation.builder().id(1L).build();

    CommandDTO commandDTO = CommandDTO
        .builder()
        .add(ContextConstants.CONTEXT_PROJECT_MATERIAL, projectDetailMaterialRepresentation)
        .build();

    SaveProjectMaterialRequestValidationCommand command = new SaveProjectMaterialRequestValidationCommand();
    command.validations = mockSaveProjectMaterialValidations;

    // Define Mocks
    Mockito.doNothing().when(mockSaveProjectMaterialValidations).validateSaveMaterialRequest(projectDetailMaterialRepresentation);

    // Execute
    Exception expectedException = null;
    try {
      command.process(commandDTO);
    } catch (Exception exception) {
      expectedException = exception;
    }

    // Verify
    Mockito.verify(mockSaveProjectMaterialValidations, Mockito.times(1)).validateSaveMaterialRequest(projectDetailMaterialRepresentation);

    // Assertions
    assert expectedException == null;
  }
}
