package com.doityourself.workshop.features.project.media.exceptionHandler;

import com.doityourself.workshop.common.command.CommandDTO;
import com.doityourself.workshop.features.project.media.exception.SaveProjectLocalResourceFailedException;
import org.junit.jupiter.api.Test;

public class SaveProjectLocalResourceGlobalExceptionHandlerTest {
  @Test
  public void testHandleRuntimeException() {
    // Initialize
    SaveProjectLocalResourceGlobalExceptionHandler saveProjectLocalResourceGlobalExceptionHandler = new SaveProjectLocalResourceGlobalExceptionHandler();
    saveProjectLocalResourceGlobalExceptionHandler.projectSaveLocalResourceFailedErrorMessage = "Failed";

    // Execute
    Exception expectedException = null;
    try {
      saveProjectLocalResourceGlobalExceptionHandler.handleRuntimeException(CommandDTO.builder().build(), new NullPointerException());
    } catch (Exception exception) {
      expectedException = exception;
    }

    // Assertions
    assert expectedException != null;
    assert expectedException instanceof SaveProjectLocalResourceFailedException;
    assert ((SaveProjectLocalResourceFailedException)expectedException).getMessages().get(0).equals("Failed");
  }
}
