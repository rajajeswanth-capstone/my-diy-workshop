package com.doityourself.workshop.features.project.media.command;

import com.doityourself.workshop.common.command.CommandDTO;
import com.doityourself.workshop.common.constants.ContextConstants;
import com.doityourself.workshop.features.project.media.representation.ProjectDetailLocalResourceRepresentation;
import com.doityourself.workshop.features.project.media.validation.UpdateProjectLocalResourceValidations;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

public class UpdateProjectLocalResourceRequestValidationCommandTest {
  @Test
  public void testProcess() {
    // Initialize
    UpdateProjectLocalResourceValidations mockUpdateProjectLocalResourceValidations = Mockito.mock(UpdateProjectLocalResourceValidations.class);

    ProjectDetailLocalResourceRepresentation projectDetailLocalResourceRepresentation = ProjectDetailLocalResourceRepresentation.builder().id(1L).build();
    CommandDTO commandDTO = CommandDTO.builder().add(ContextConstants.CONTEXT_PROJECT_LOCAL_RESOURCE, projectDetailLocalResourceRepresentation).build();

    UpdateProjectLocalResourceRequestValidationCommand command = new UpdateProjectLocalResourceRequestValidationCommand();
    command.validations = mockUpdateProjectLocalResourceValidations;

    // Define Mocks
    Mockito.doNothing().when(mockUpdateProjectLocalResourceValidations).validateUpdateLocalResourceRequest(projectDetailLocalResourceRepresentation);

    // Execute
    Exception expectedException = null;
    try {
      command.process(commandDTO);
    } catch (Exception exception) {
      expectedException = exception;
    }

    // Verify
    Mockito.verify(mockUpdateProjectLocalResourceValidations, Mockito.times(1)).validateUpdateLocalResourceRequest(projectDetailLocalResourceRepresentation);

    // Assertions
    assert expectedException == null;
  }
}
