package com.doityourself.workshop.features.project.detail.exceptionHandler;

import com.doityourself.workshop.common.command.CommandDTO;
import com.doityourself.workshop.features.project.detail.exception.GetProjectDetailFailedException;
import org.junit.jupiter.api.Test;

import java.io.IOException;

public class GetProjectDetailGlobalExceptionHandlerTest {
  @Test
  public void testHandleRuntimeException() {
    // Initialize
    GetProjectDetailGlobalExceptionHandler getProjectDetailGlobalExceptionHandler = new GetProjectDetailGlobalExceptionHandler();
    getProjectDetailGlobalExceptionHandler.projectDetailFailedErrorMessage = "Failed";

    // Execute
    Exception expectedException = null;
    try {
      getProjectDetailGlobalExceptionHandler.handleRuntimeException(CommandDTO.builder().build(), new NullPointerException());
    } catch (Exception exception) {
      expectedException = exception;
    }

    // Assertions
    assert expectedException != null;
    assert expectedException instanceof GetProjectDetailFailedException;
    assert ((GetProjectDetailFailedException)expectedException).getMessages().get(0).equals("Failed");
  }

  @Test
  public void testHandleException() {
    // Initialize
    GetProjectDetailGlobalExceptionHandler getProjectDetailGlobalExceptionHandler = new GetProjectDetailGlobalExceptionHandler();
    getProjectDetailGlobalExceptionHandler.projectDetailFailedErrorMessage = "Failed";

    // Execute
    Exception expectedException = null;
    try {
      getProjectDetailGlobalExceptionHandler.handleException(CommandDTO.builder().build(), new IOException());
    } catch (Exception exception) {
      expectedException = exception;
    }

    // Assertions
    assert expectedException != null;
    assert expectedException instanceof GetProjectDetailFailedException;
    assert ((GetProjectDetailFailedException)expectedException).getMessages().get(0).equals("Failed");
  }
}
