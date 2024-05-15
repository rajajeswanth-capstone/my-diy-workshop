package com.doityourself.workshop.features.project.listing.exceptionHandler;

import com.doityourself.workshop.common.command.CommandDTO;
import com.doityourself.workshop.features.project.listing.exception.ProjectDeleteFailedException;
import org.junit.jupiter.api.Test;

public class DeleteProjectGlobalExceptionHandlerTest {
  @Test
  public void testHandleRuntimeException() {
    // Initialize
    DeleteProjectGlobalExceptionHandler deleteProjectGlobalExceptionHandler = new DeleteProjectGlobalExceptionHandler();
    deleteProjectGlobalExceptionHandler.projectDeleteFailedErrorMessage = "Failed";

    // Execute
    Exception expectedException = null;
    try {
      deleteProjectGlobalExceptionHandler.handleRuntimeException(CommandDTO.builder().build(), new NullPointerException());
    } catch (Exception exception) {
      expectedException = exception;
    }

    // Assertions
    assert expectedException != null;
    assert expectedException instanceof ProjectDeleteFailedException;
    assert ((ProjectDeleteFailedException)expectedException).getMessages().get(0).equals("Failed");
  }
}
