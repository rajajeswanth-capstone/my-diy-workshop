package com.doityourself.workshop.features.project.media.command;

import com.doityourself.workshop.common.command.CommandDTO;
import com.doityourself.workshop.common.constants.ContextConstants;
import com.doityourself.workshop.features.project.media.representation.ProjectDetailWebResourceRepresentation;
import com.doityourself.workshop.features.project.media.validation.SaveProjectWebResourceValidations;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

public class SaveProjectWebResourceRequestValidationCommandTest {
  @Test
  public void testProcess() {
    // Initialize
    SaveProjectWebResourceValidations mockSaveProjectWebResourceValidations = Mockito.mock(SaveProjectWebResourceValidations.class);

    ProjectDetailWebResourceRepresentation projectDetailWebResourceRepresentation = ProjectDetailWebResourceRepresentation.builder().id(1L).build();
    CommandDTO commandDTO = CommandDTO.builder().add(ContextConstants.CONTEXT_PROJECT_WEB_RESOURCE, projectDetailWebResourceRepresentation).build();

    SaveProjectWebResourceRequestValidationCommand command = new SaveProjectWebResourceRequestValidationCommand();
    command.validations = mockSaveProjectWebResourceValidations;

    // Define Mocks
    Mockito.doNothing().when(mockSaveProjectWebResourceValidations).validateSaveWebResourceRequest(projectDetailWebResourceRepresentation);

    // Execute
    Exception expectedException = null;
    try {
      command.process(commandDTO);
    } catch (Exception exception) {
      expectedException = exception;
    }

    // Verify
    Mockito.verify(mockSaveProjectWebResourceValidations, Mockito.times(1)).validateSaveWebResourceRequest(projectDetailWebResourceRepresentation);

    // Assertions
    assert expectedException == null;
  }
}
