package com.doityourself.workshop.features.project.media.exceptionHandler;

import com.doityourself.workshop.common.command.CommandDTO;
import com.doityourself.workshop.features.project.media.exception.DeleteProjectLocalResourceFailedException;
import org.junit.jupiter.api.Test;

public class DeleteProjectLocalResourceGlobalExceptionHandlerTest {
  @Test
  public void testHandleRuntimeException() {
    // Initialize
    DeleteProjectLocalResourceGlobalExceptionHandler deleteProjectLocalResourceGlobalExceptionHandler = new DeleteProjectLocalResourceGlobalExceptionHandler();
    deleteProjectLocalResourceGlobalExceptionHandler.projectDeleteLocalResourceFailedErrorMessage = "Failed";

    // Execute
    Exception expectedException = null;
    try {
      deleteProjectLocalResourceGlobalExceptionHandler.handleRuntimeException(CommandDTO.builder().build(), new NullPointerException());
    } catch (Exception exception) {
      expectedException = exception;
    }

    // Assertions
    assert expectedException != null;
    assert expectedException instanceof DeleteProjectLocalResourceFailedException;
    assert ((DeleteProjectLocalResourceFailedException)expectedException).getMessages().get(0).equals("Failed");
  }
}
