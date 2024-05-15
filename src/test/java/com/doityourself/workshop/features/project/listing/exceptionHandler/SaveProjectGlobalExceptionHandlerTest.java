package com.doityourself.workshop.features.project.listing.exceptionHandler;

import com.doityourself.workshop.common.command.CommandDTO;
import com.doityourself.workshop.features.project.listing.exception.ProjectSaveFailedException;
import org.junit.jupiter.api.Test;

public class SaveProjectGlobalExceptionHandlerTest {
  @Test
  public void testHandleRuntimeException() {
    // Initialize
    SaveProjectGlobalExceptionHandler saveProjectGlobalExceptionHandler = new SaveProjectGlobalExceptionHandler();
    saveProjectGlobalExceptionHandler.projectSaveFailedErrorMessage = "Failed";

    // Execute
    Exception expectedException = null;
    try {
      saveProjectGlobalExceptionHandler.handleRuntimeException(CommandDTO.builder().build(), new NullPointerException());
    } catch (Exception exception) {
      expectedException = exception;
    }

    // Assertions
    assert expectedException != null;
    assert expectedException instanceof ProjectSaveFailedException;
    assert ((ProjectSaveFailedException)expectedException).getMessages().get(0).equals("Failed");
  }
}
