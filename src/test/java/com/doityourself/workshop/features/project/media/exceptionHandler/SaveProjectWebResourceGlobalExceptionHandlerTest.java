package com.doityourself.workshop.features.project.media.exceptionHandler;

import com.doityourself.workshop.common.command.CommandDTO;
import com.doityourself.workshop.features.project.media.exception.SaveProjectWebResourceFailedException;
import org.junit.jupiter.api.Test;

public class SaveProjectWebResourceGlobalExceptionHandlerTest {
  @Test
  public void testHandleRuntimeException() {
    // Initialize
    SaveProjectWebResourceGlobalExceptionHandler saveProjectWebResourceGlobalExceptionHandler = new SaveProjectWebResourceGlobalExceptionHandler();
    saveProjectWebResourceGlobalExceptionHandler.projectSaveWebResourceFailedErrorMessage = "Failed";

    // Execute
    Exception expectedException = null;
    try {
      saveProjectWebResourceGlobalExceptionHandler.handleRuntimeException(CommandDTO.builder().build(), new NullPointerException());
    } catch (Exception exception) {
      expectedException = exception;
    }

    // Assertions
    assert expectedException != null;
    assert expectedException instanceof SaveProjectWebResourceFailedException;
    assert ((SaveProjectWebResourceFailedException)expectedException).getMessages().get(0).equals("Failed");
  }
}
