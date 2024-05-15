package com.doityourself.workshop.features.project.detail.exceptionHandler;

import com.doityourself.workshop.common.command.CommandDTO;
import com.doityourself.workshop.features.project.detail.exception.GetProjectInstructionFailedException;
import org.junit.jupiter.api.Test;

import java.io.IOException;

public class GetProjectInstructionGlobalExceptionHandlerTest {
  @Test
  public void testHandleRuntimeException() {
    // Initialize
    GetProjectInstructionGlobalExceptionHandler getProjectInstructionGlobalExceptionHandler = new GetProjectInstructionGlobalExceptionHandler();
    getProjectInstructionGlobalExceptionHandler.projectInstructionFailedErrorMessage = "Failed";

    // Execute
    Exception expectedException = null;
    try {
      getProjectInstructionGlobalExceptionHandler.handleRuntimeException(CommandDTO.builder().build(), new NullPointerException());
    } catch (Exception exception) {
      expectedException = exception;
    }

    // Assertions
    assert expectedException != null;
    assert expectedException instanceof GetProjectInstructionFailedException;
    assert ((GetProjectInstructionFailedException)expectedException).getMessages().get(0).equals("Failed");
  }

  @Test
  public void testHandleException() {
    // Initialize
    GetProjectInstructionGlobalExceptionHandler getProjectInstructionGlobalExceptionHandler = new GetProjectInstructionGlobalExceptionHandler();
    getProjectInstructionGlobalExceptionHandler.projectInstructionFailedErrorMessage = "Failed";

    // Execute
    Exception expectedException = null;
    try {
      getProjectInstructionGlobalExceptionHandler.handleException(CommandDTO.builder().build(), new IOException());
    } catch (Exception exception) {
      expectedException = exception;
    }

    // Assertions
    assert expectedException != null;
    assert expectedException instanceof GetProjectInstructionFailedException;
    assert ((GetProjectInstructionFailedException)expectedException).getMessages().get(0).equals("Failed");
  }
}
