package com.doityourself.workshop.features.project.media.exceptionHandler;

import com.doityourself.workshop.common.command.CommandDTO;
import com.doityourself.workshop.features.project.media.exception.UpdateProjectWebResourceFailedException;
import org.junit.jupiter.api.Test;

public class UpdateProjectWebResourceGlobalExceptionHandlerTest {
  @Test
  public void testHandleRuntimeException() {
    // Initialize
    UpdateProjectWebResourceGlobalExceptionHandler updateProjectWebResourceGlobalExceptionHandler = new UpdateProjectWebResourceGlobalExceptionHandler();
    updateProjectWebResourceGlobalExceptionHandler.projectUpdateWebResourceFailedErrorMessage = "Failed";

    // Execute
    Exception expectedException = null;
    try {
      updateProjectWebResourceGlobalExceptionHandler.handleRuntimeException(CommandDTO.builder().build(), new NullPointerException());
    } catch (Exception exception) {
      expectedException = exception;
    }

    // Assertions
    assert expectedException != null;
    assert expectedException instanceof UpdateProjectWebResourceFailedException;
    assert ((UpdateProjectWebResourceFailedException)expectedException).getMessages().get(0).equals("Failed");
  }
}
