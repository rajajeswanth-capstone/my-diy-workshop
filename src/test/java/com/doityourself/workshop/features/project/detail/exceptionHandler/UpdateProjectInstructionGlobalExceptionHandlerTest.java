package com.doityourself.workshop.features.project.detail.exceptionHandler;

import com.doityourself.workshop.common.command.CommandDTO;
import com.doityourself.workshop.features.project.detail.exception.UpdateProjectInstructionFailedException;
import org.junit.jupiter.api.Test;

import java.io.IOException;

public class UpdateProjectInstructionGlobalExceptionHandlerTest {
  @Test
  public void testHandleRuntimeException() {
    // Initialize
    UpdateProjectInstructionGlobalExceptionHandler updateProjectInstructionGlobalExceptionHandler = new UpdateProjectInstructionGlobalExceptionHandler();
    updateProjectInstructionGlobalExceptionHandler.projectUpdateInstructionFailedErrorMessage = "Failed";

    // Execute
    Exception expectedException = null;
    try {
      updateProjectInstructionGlobalExceptionHandler.handleRuntimeException(CommandDTO.builder().build(), new NullPointerException());
    } catch (Exception exception) {
      expectedException = exception;
    }

    // Assertions
    assert expectedException != null;
    assert expectedException instanceof UpdateProjectInstructionFailedException;
    assert ((UpdateProjectInstructionFailedException)expectedException).getMessages().get(0).equals("Failed");
  }

  @Test
  public void testHandleException() {
    // Initialize
    UpdateProjectInstructionGlobalExceptionHandler updateProjectInstructionGlobalExceptionHandler = new UpdateProjectInstructionGlobalExceptionHandler();
    updateProjectInstructionGlobalExceptionHandler.projectUpdateInstructionFailedErrorMessage = "Failed";

    // Execute
    Exception expectedException = null;
    try {
      updateProjectInstructionGlobalExceptionHandler.handleException(CommandDTO.builder().build(), new IOException());
    } catch (Exception exception) {
      expectedException = exception;
    }

    // Assertions
    assert expectedException != null;
    assert expectedException instanceof UpdateProjectInstructionFailedException;
    assert ((UpdateProjectInstructionFailedException)expectedException).getMessages().get(0).equals("Failed");
  }
}
