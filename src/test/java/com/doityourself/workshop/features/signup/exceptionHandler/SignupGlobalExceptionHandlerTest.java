package com.doityourself.workshop.features.signup.exceptionHandler;

import com.doityourself.workshop.common.command.CommandDTO;
import com.doityourself.workshop.features.signup.exception.SignupFailedException;
import org.junit.jupiter.api.Test;

public class SignupGlobalExceptionHandlerTest {
  @Test
  public void testHandleRuntimeException() {
    // Initialize
    SignupGlobalExceptionHandler signupGlobalExceptionHandler = new SignupGlobalExceptionHandler();
    signupGlobalExceptionHandler.signupFailedErrorMessage = "Failed";

    // Execute
    Exception expectedException = null;
    try {
      signupGlobalExceptionHandler.handleRuntimeException(CommandDTO.builder().build(), new NullPointerException());
    } catch (Exception exception) {
      expectedException = exception;
    }

    // Assertions
    assert expectedException != null;
    assert expectedException instanceof SignupFailedException;
    assert ((SignupFailedException)expectedException).getMessages().get(0).equals("Failed");
  }
}
