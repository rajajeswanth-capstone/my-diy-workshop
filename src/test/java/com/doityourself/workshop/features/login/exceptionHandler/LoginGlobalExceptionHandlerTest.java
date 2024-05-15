package com.doityourself.workshop.features.login.exceptionHandler;

import com.doityourself.workshop.common.command.CommandDTO;
import com.doityourself.workshop.features.login.exception.LoginFailedException;
import org.junit.jupiter.api.Test;

import java.io.IOException;

public class LoginGlobalExceptionHandlerTest {
  @Test
  public void testHandleRuntimeException() {
    // Initialize
    LoginGlobalExceptionHandler loginGlobalExceptionHandler = new LoginGlobalExceptionHandler();
    loginGlobalExceptionHandler.loginFailedErrorMessage = "Login Failed";

    // Execute
    Exception expectedException = null;
    try {
      loginGlobalExceptionHandler.handleRuntimeException(CommandDTO.builder().build(), new NullPointerException());
    } catch (Exception exception) {
      expectedException = exception;
    }

    // Assertions
    assert expectedException != null;
    assert expectedException instanceof LoginFailedException;
    assert ((LoginFailedException)expectedException).getMessages().get(0).equals("Login Failed");
  }

  @Test
  public void testHandleException() {
    // Initialize
    LoginGlobalExceptionHandler loginGlobalExceptionHandler = new LoginGlobalExceptionHandler();
    loginGlobalExceptionHandler.loginFailedErrorMessage = "Login Failed";

    // Execute
    Exception expectedException = null;
    try {
      loginGlobalExceptionHandler.handleException(CommandDTO.builder().build(), new IOException());
    } catch (Exception exception) {
      expectedException = exception;
    }

    // Assertions
    assert expectedException != null;
    assert expectedException instanceof LoginFailedException;
    assert ((LoginFailedException)expectedException).getMessages().get(0).equals("Login Failed");
  }
}
