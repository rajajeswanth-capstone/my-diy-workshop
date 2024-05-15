package com.doityourself.workshop.features.project.detail.exceptionHandler;

import com.doityourself.workshop.common.command.CommandDTO;
import com.doityourself.workshop.features.project.detail.exception.SaveProjectInstructionFailedException;
import org.junit.jupiter.api.Test;

import java.io.IOException;

public class SaveProjectInstructionGlobalExceptionHandlerTest {
  @Test
  public void testHandleRuntimeException() {
    // Initialize
    SaveProjectInstructionGlobalExceptionHandler saveProjectInstructionGlobalExceptionHandler = new SaveProjectInstructionGlobalExceptionHandler();
    saveProjectInstructionGlobalExceptionHandler.projectSaveInstructionFailedErrorMessage = "Failed";

    // Execute
    Exception expectedException = null;
    try {
      saveProjectInstructionGlobalExceptionHandler.handleRuntimeException(CommandDTO.builder().build(), new NullPointerException());
    } catch (Exception exception) {
      expectedException = exception;
    }

    // Assertions
    assert expectedException != null;
    assert expectedException instanceof SaveProjectInstructionFailedException;
    assert ((SaveProjectInstructionFailedException)expectedException).getMessages().get(0).equals("Failed");
  }

  @Test
  public void testHandleException() {
    // Initialize
    SaveProjectInstructionGlobalExceptionHandler saveProjectInstructionGlobalExceptionHandler = new SaveProjectInstructionGlobalExceptionHandler();
    saveProjectInstructionGlobalExceptionHandler.projectSaveInstructionFailedErrorMessage = "Failed";

    // Execute
    Exception expectedException = null;
    try {
      saveProjectInstructionGlobalExceptionHandler.handleException(CommandDTO.builder().build(), new IOException());
    } catch (Exception exception) {
      expectedException = exception;
    }

    // Assertions
    assert expectedException != null;
    assert expectedException instanceof SaveProjectInstructionFailedException;
    assert ((SaveProjectInstructionFailedException)expectedException).getMessages().get(0).equals("Failed");
  }
}
