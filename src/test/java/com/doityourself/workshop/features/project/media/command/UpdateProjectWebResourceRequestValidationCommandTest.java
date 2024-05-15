package com.doityourself.workshop.features.project.media.command;

import com.doityourself.workshop.common.command.CommandDTO;
import com.doityourself.workshop.common.constants.ContextConstants;
import com.doityourself.workshop.features.project.media.representation.ProjectDetailWebResourceRepresentation;
import com.doityourself.workshop.features.project.media.validation.UpdateProjectWebResourceValidations;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

public class UpdateProjectWebResourceRequestValidationCommandTest {
  @Test
  public void testProcess() {
    // Initialize
    UpdateProjectWebResourceValidations mockUpdateProjectWebResourceValidations = Mockito.mock(UpdateProjectWebResourceValidations.class);

    ProjectDetailWebResourceRepresentation projectDetailWebResourceRepresentation = ProjectDetailWebResourceRepresentation.builder().id(1L).build();
    CommandDTO commandDTO = CommandDTO.builder().add(ContextConstants.CONTEXT_PROJECT_WEB_RESOURCE, projectDetailWebResourceRepresentation).build();

    UpdateProjectWebResourceRequestValidationCommand command = new UpdateProjectWebResourceRequestValidationCommand();
    command.validations = mockUpdateProjectWebResourceValidations;

    // Define Mocks
    Mockito.doNothing().when(mockUpdateProjectWebResourceValidations).validateUpdateWebResourceRequest(projectDetailWebResourceRepresentation);

    // Execute
    Exception expectedException = null;
    try {
      command.process(commandDTO);
    } catch (Exception exception) {
      expectedException = exception;
    }

    // Verify
    Mockito.verify(mockUpdateProjectWebResourceValidations, Mockito.times(1)).validateUpdateWebResourceRequest(projectDetailWebResourceRepresentation);

    // Assertions
    assert expectedException == null;
  }
}
