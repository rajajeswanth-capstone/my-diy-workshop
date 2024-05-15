package com.doityourself.workshop.features.project.listing.command;

import com.doityourself.workshop.common.command.CommandDTO;
import com.doityourself.workshop.common.constants.ContextConstants;
import com.doityourself.workshop.features.project.listing.representation.ProjectListingRepresentation;
import com.doityourself.workshop.features.project.listing.validation.SaveProjectValidations;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

public class SaveProjectRequestValidationCommandTest {
  @Test
  public void testProcess() {
    // Initialize
    SaveProjectValidations mockSaveProjectValidations = Mockito.mock(SaveProjectValidations.class);

    ProjectListingRepresentation projectListingRepresentation = ProjectListingRepresentation.builder().id(1L).build();
    CommandDTO commandDTO = CommandDTO
        .builder()
        .add(ContextConstants.CONTEXT_PROJECT, projectListingRepresentation)
        .build();

    SaveProjectRequestValidationCommand command = new SaveProjectRequestValidationCommand();
    command.validations = mockSaveProjectValidations;

    // Define Mocks
    Mockito.doNothing().when(mockSaveProjectValidations).validateSaveRequest(projectListingRepresentation);

    // Execute
    Exception expectedException = null;
    try {
      command.process(commandDTO);
    } catch (Exception exception) {
      expectedException = exception;
    }

    // Verify
    Mockito.verify(mockSaveProjectValidations, Mockito.times(1)).validateSaveRequest(projectListingRepresentation);

    // Assertions
    assert expectedException == null;
  }
}
