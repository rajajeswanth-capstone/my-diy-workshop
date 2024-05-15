package com.doityourself.workshop.features.project.share.command;

import com.doityourself.workshop.common.command.CommandDTO;
import com.doityourself.workshop.common.constants.ContextConstants;
import com.doityourself.workshop.features.project.share.representation.ShareProjectRepresentation;
import com.doityourself.workshop.features.project.share.validation.ShareProjectValidations;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

public class ShareProjectRequestValidationCommandTest {
  @Test
  public void testProcess() {
    // Initialize
    ShareProjectValidations mockShareProjectValidations = Mockito.mock(ShareProjectValidations.class);

    ShareProjectRepresentation shareProjectRepresentation = ShareProjectRepresentation.builder().projectId(1L).build();
    CommandDTO commandDTO = CommandDTO.builder().add(ContextConstants.CONTEXT_SHARE_PROJECT, shareProjectRepresentation).build();

    ShareProjectRequestValidationCommand command = new ShareProjectRequestValidationCommand();
    command.validations = mockShareProjectValidations;

    // Define Mocks
    Mockito.doNothing().when(mockShareProjectValidations).validateShareProjectRequest(shareProjectRepresentation);

    // Execute
    Exception expectedException = null;
    try {
      command.process(commandDTO);
    } catch (Exception exception) {
      expectedException = exception;
    }

    // Verify
    Mockito.verify(mockShareProjectValidations, Mockito.times(1)).validateShareProjectRequest(shareProjectRepresentation);

    // Assertions
    assert expectedException == null;
  }
}
