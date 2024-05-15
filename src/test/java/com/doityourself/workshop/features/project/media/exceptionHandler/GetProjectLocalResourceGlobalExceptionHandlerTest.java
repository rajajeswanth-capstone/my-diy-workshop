package com.doityourself.workshop.features.project.media.exceptionHandler;

import com.doityourself.workshop.common.command.CommandDTO;
import com.doityourself.workshop.features.project.media.exception.GetProjectLocalResourceFailedException;
import org.junit.jupiter.api.Test;

public class GetProjectLocalResourceGlobalExceptionHandlerTest {
  @Test
  public void testHandleRuntimeException() {
    // Initialize
    GetProjectLocalResourceGlobalExceptionHandler getProjectLocalResourceGlobalExceptionHandler = new GetProjectLocalResourceGlobalExceptionHandler();
    getProjectLocalResourceGlobalExceptionHandler.projectLocalResourceFailedErrorMessage = "Failed";

    // Execute
    Exception expectedException = null;
    try {
      getProjectLocalResourceGlobalExceptionHandler.handleRuntimeException(CommandDTO.builder().build(), new NullPointerException());
    } catch (Exception exception) {
      expectedException = exception;
    }

    // Assertions
    assert expectedException != null;
    assert expectedException instanceof GetProjectLocalResourceFailedException;
    assert ((GetProjectLocalResourceFailedException)expectedException).getMessages().get(0).equals("Failed");
  }
}
