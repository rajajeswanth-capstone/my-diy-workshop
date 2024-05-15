package com.doityourself.workshop.features.user.exceptionHandler;

import com.doityourself.workshop.common.command.CommandDTO;
import com.doityourself.workshop.features.user.exception.GetSharedUsersForProjectFailedException;
import org.junit.jupiter.api.Test;

public class GetSharedUsersForProjectGlobalExceptionHandlerTest {
  @Test
  public void testHandleRuntimeException() {
    // Initialize
    GetSharedUsersForProjectGlobalExceptionHandler getSharedUsersForProjectGlobalExceptionHandler = new GetSharedUsersForProjectGlobalExceptionHandler();
    getSharedUsersForProjectGlobalExceptionHandler.userShareValidationErrorMessage = "Failed";

    // Execute
    Exception expectedException = null;
    try {
      getSharedUsersForProjectGlobalExceptionHandler.handleRuntimeException(CommandDTO.builder().build(), new NullPointerException());
    } catch (Exception exception) {
      expectedException = exception;
    }

    // Assertions
    assert expectedException != null;
    assert expectedException instanceof GetSharedUsersForProjectFailedException;
    assert ((GetSharedUsersForProjectFailedException)expectedException).getMessages().get(0).equals("Failed");
  }
}
