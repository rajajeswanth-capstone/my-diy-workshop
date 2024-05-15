package com.doityourself.workshop.features.project.media.command;

import com.doityourself.workshop.common.command.CommandDTO;
import com.doityourself.workshop.common.constants.ContextConstants;
import com.doityourself.workshop.features.project.media.representation.ProjectDetailLocalResourceRepresentation;
import com.doityourself.workshop.features.project.media.validation.SaveProjectLocalResourceValidations;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

public class SaveProjectLocalResourceRequestValidationCommandTest {
  @Test
  public void testProcess() {
    // Initialize
    SaveProjectLocalResourceValidations mockSaveProjectLocalResourceValidations = Mockito.mock(SaveProjectLocalResourceValidations.class);

    ProjectDetailLocalResourceRepresentation projectDetailLocalResourceRepresentation = ProjectDetailLocalResourceRepresentation.builder().id(1L).build();
    CommandDTO commandDTO = CommandDTO.builder().add(ContextConstants.CONTEXT_PROJECT_LOCAL_RESOURCE, projectDetailLocalResourceRepresentation).build();

    SaveProjectLocalResourceRequestValidationCommand command = new SaveProjectLocalResourceRequestValidationCommand();
    command.validations = mockSaveProjectLocalResourceValidations;

    // Define Mocks
    Mockito.doNothing().when(mockSaveProjectLocalResourceValidations).validateSaveLocalResourceRequest(projectDetailLocalResourceRepresentation);

    // Execute
    Exception expectedException = null;
    try {
      command.process(commandDTO);
    } catch (Exception exception) {
      expectedException = exception;
    }

    // Verify
    Mockito.verify(mockSaveProjectLocalResourceValidations, Mockito.times(1)).validateSaveLocalResourceRequest(projectDetailLocalResourceRepresentation);

    // Assertions
    assert expectedException == null;
  }
}
