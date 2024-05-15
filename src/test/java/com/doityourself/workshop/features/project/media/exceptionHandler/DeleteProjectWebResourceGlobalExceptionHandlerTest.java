package com.doityourself.workshop.features.project.media.exceptionHandler;

import com.doityourself.workshop.common.command.CommandDTO;
import com.doityourself.workshop.features.project.media.exception.DeleteProjectWebResourceFailedException;
import org.junit.jupiter.api.Test;

public class DeleteProjectWebResourceGlobalExceptionHandlerTest {
  @Test
  public void testHandleRuntimeException() {
    // Initialize
    DeleteProjectWebResourceGlobalExceptionHandler deleteProjectWebResourceGlobalExceptionHandler = new DeleteProjectWebResourceGlobalExceptionHandler();
    deleteProjectWebResourceGlobalExceptionHandler.projectDeleteWebResourceIdRequiredErrorMessage = "Failed";

    // Execute
    Exception expectedException = null;
    try {
      deleteProjectWebResourceGlobalExceptionHandler.handleRuntimeException(CommandDTO.builder().build(), new NullPointerException());
    } catch (Exception exception) {
      expectedException = exception;
    }

    // Assertions
    assert expectedException != null;
    assert expectedException instanceof DeleteProjectWebResourceFailedException;
    assert ((DeleteProjectWebResourceFailedException)expectedException).getMessages().get(0).equals("Failed");
  }
}
