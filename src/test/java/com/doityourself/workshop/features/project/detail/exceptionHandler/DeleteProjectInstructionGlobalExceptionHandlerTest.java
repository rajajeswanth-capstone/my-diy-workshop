package com.doityourself.workshop.features.project.detail.exceptionHandler;

import com.doityourself.workshop.common.command.CommandDTO;
import com.doityourself.workshop.features.project.detail.exception.DeleteProjectInstructionFailedException;
import org.junit.jupiter.api.Test;

import java.io.IOException;

public class DeleteProjectInstructionGlobalExceptionHandlerTest {
  @Test
  public void testHandleRuntimeException() {
    // Initialize
    DeleteProjectInstructionGlobalExceptionHandler deleteProjectInstructionGlobalExceptionHandler = new DeleteProjectInstructionGlobalExceptionHandler();
    deleteProjectInstructionGlobalExceptionHandler.projectDeleteInstructionFailedErrorMessage = "Failed";

    // Execute
    Exception expectedException = null;
    try {
      deleteProjectInstructionGlobalExceptionHandler.handleRuntimeException(CommandDTO.builder().build(), new NullPointerException());
    } catch (Exception exception) {
      expectedException = exception;
    }

    // Assertions
    assert expectedException != null;
    assert expectedException instanceof DeleteProjectInstructionFailedException;
    assert ((DeleteProjectInstructionFailedException)expectedException).getMessages().get(0).equals("Failed");
  }

  @Test
  public void testHandleException() {
    // Initialize
    DeleteProjectInstructionGlobalExceptionHandler deleteProjectInstructionGlobalExceptionHandler = new DeleteProjectInstructionGlobalExceptionHandler();
    deleteProjectInstructionGlobalExceptionHandler.projectDeleteInstructionFailedErrorMessage = "Failed";

    // Execute
    Exception expectedException = null;
    try {
      deleteProjectInstructionGlobalExceptionHandler.handleException(CommandDTO.builder().build(), new IOException());
    } catch (Exception exception) {
      expectedException = exception;
    }

    // Assertions
    assert expectedException != null;
    assert expectedException instanceof DeleteProjectInstructionFailedException;
    assert ((DeleteProjectInstructionFailedException)expectedException).getMessages().get(0).equals("Failed");
  }
}
