package com.doityourself.workshop.features.project.media.exceptionHandler;

import com.doityourself.workshop.common.command.CommandDTO;
import com.doityourself.workshop.features.project.media.exception.GetProjectWebResourceFailedException;
import org.junit.jupiter.api.Test;

public class GetProjectWebResourceGlobalExceptionHandlerTest {
  @Test
  public void testHandleRuntimeException() {
    // Initialize
    GetProjectWebResourceGlobalExceptionHandler getProjectWebResourceGlobalExceptionHandler = new GetProjectWebResourceGlobalExceptionHandler();
    getProjectWebResourceGlobalExceptionHandler.projectWebResourceFailedErrorMessage = "Failed";

    // Execute
    Exception expectedException = null;
    try {
      getProjectWebResourceGlobalExceptionHandler.handleRuntimeException(CommandDTO.builder().build(), new NullPointerException());
    } catch (Exception exception) {
      expectedException = exception;
    }

    // Assertions
    assert expectedException != null;
    assert expectedException instanceof GetProjectWebResourceFailedException;
    assert ((GetProjectWebResourceFailedException)expectedException).getMessages().get(0).equals("Failed");
  }
}
