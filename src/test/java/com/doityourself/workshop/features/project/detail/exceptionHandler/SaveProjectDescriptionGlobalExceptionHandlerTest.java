package com.doityourself.workshop.features.project.detail.exceptionHandler;

import com.doityourself.workshop.common.command.CommandDTO;
import com.doityourself.workshop.features.project.detail.exception.SaveProjectDescriptionFailedException;
import org.junit.jupiter.api.Test;

import java.io.IOException;

public class SaveProjectDescriptionGlobalExceptionHandlerTest {
  @Test
  public void testHandleRuntimeException() {
    // Initialize
    SaveProjectDescriptionGlobalExceptionHandler saveProjectDescriptionGlobalExceptionHandler = new SaveProjectDescriptionGlobalExceptionHandler();
    saveProjectDescriptionGlobalExceptionHandler.projectSaveDescriptionFailedErrorMessage = "Failed";

    // Execute
    Exception expectedException = null;
    try {
      saveProjectDescriptionGlobalExceptionHandler.handleRuntimeException(CommandDTO.builder().build(), new NullPointerException());
    } catch (Exception exception) {
      expectedException = exception;
    }

    // Assertions
    assert expectedException != null;
    assert expectedException instanceof SaveProjectDescriptionFailedException;
    assert ((SaveProjectDescriptionFailedException)expectedException).getMessages().get(0).equals("Failed");
  }

  @Test
  public void testHandleException() {
    // Initialize
    SaveProjectDescriptionGlobalExceptionHandler saveProjectDescriptionGlobalExceptionHandler = new SaveProjectDescriptionGlobalExceptionHandler();
    saveProjectDescriptionGlobalExceptionHandler.projectSaveDescriptionFailedErrorMessage = "Failed";

    // Execute
    Exception expectedException = null;
    try {
      saveProjectDescriptionGlobalExceptionHandler.handleException(CommandDTO.builder().build(), new IOException());
    } catch (Exception exception) {
      expectedException = exception;
    }

    // Assertions
    assert expectedException != null;
    assert expectedException instanceof SaveProjectDescriptionFailedException;
    assert ((SaveProjectDescriptionFailedException)expectedException).getMessages().get(0).equals("Failed");
  }
}
