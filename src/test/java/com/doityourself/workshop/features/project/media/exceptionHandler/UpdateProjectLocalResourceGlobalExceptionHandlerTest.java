package com.doityourself.workshop.features.project.media.exceptionHandler;

import com.doityourself.workshop.common.command.CommandDTO;
import com.doityourself.workshop.features.project.media.exception.UpdateProjectLocalResourceFailedException;
import org.junit.jupiter.api.Test;

public class UpdateProjectLocalResourceGlobalExceptionHandlerTest {
  @Test
  public void testHandleRuntimeException() {
    // Initialize
    UpdateProjectLocalResourceGlobalExceptionHandler updateProjectLocalResourceGlobalExceptionHandler = new UpdateProjectLocalResourceGlobalExceptionHandler();
    updateProjectLocalResourceGlobalExceptionHandler.projectUpdateLocalResourceFailedErrorMessage = "Failed";

    // Execute
    Exception expectedException = null;
    try {
      updateProjectLocalResourceGlobalExceptionHandler.handleRuntimeException(CommandDTO.builder().build(), new NullPointerException());
    } catch (Exception exception) {
      expectedException = exception;
    }

    // Assertions
    assert expectedException != null;
    assert expectedException instanceof UpdateProjectLocalResourceFailedException;
    assert ((UpdateProjectLocalResourceFailedException)expectedException).getMessages().get(0).equals("Failed");
  }
}
