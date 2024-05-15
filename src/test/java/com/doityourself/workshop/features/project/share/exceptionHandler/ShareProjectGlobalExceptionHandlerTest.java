package com.doityourself.workshop.features.project.share.exceptionHandler;

import com.doityourself.workshop.common.command.CommandDTO;
import com.doityourself.workshop.features.project.share.exception.ProjectShareFailedException;
import org.junit.jupiter.api.Test;

public class ShareProjectGlobalExceptionHandlerTest {
  @Test
  public void testHandleRuntimeException() {
    // Initialize
    ShareProjectGlobalExceptionHandler shareProjectGlobalExceptionHandler = new ShareProjectGlobalExceptionHandler();
    shareProjectGlobalExceptionHandler.projectShareFailedErrorMessage = "Failed";

    // Execute
    Exception expectedException = null;
    try {
      shareProjectGlobalExceptionHandler.handleRuntimeException(CommandDTO.builder().build(), new NullPointerException());
    } catch (Exception exception) {
      expectedException = exception;
    }

    // Assertions
    assert expectedException != null;
    assert expectedException instanceof ProjectShareFailedException;
    assert ((ProjectShareFailedException)expectedException).getMessages().get(0).equals("Failed");
  }
}
