package com.doityourself.workshop.features.signup.exceptionHandler;

import com.doityourself.workshop.common.command.CommandDTO;
import com.doityourself.workshop.common.command.ICommandExceptionHandler;
import com.doityourself.workshop.features.signup.exception.SignupFailedException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Collections;

/**
 * Signup Global Exception Handler
 */
@Slf4j
@Component
public class SignupGlobalExceptionHandler implements ICommandExceptionHandler<CommandDTO> {
  @Value("${signup.validation.failed}")
  String signupFailedErrorMessage;

  /**
   * Method to handle runtime exception
   *
   * @param dto {@link CommandDTO}
   * @param runtimeException {@link RuntimeException}
   */
  @Override
  public void handleRuntimeException(CommandDTO dto, RuntimeException runtimeException) {
    if (runtimeException instanceof SignupFailedException) {
      throw runtimeException;
    }
    handle();
  }

  /**
   * Method to handle exception
   *
   * @param dto {@link CommandDTO}
   * @param exception {@link Exception}
   */
  @Override
  public void handleException(CommandDTO dto, Exception exception) {
    handle();
  }

  /**
   * Common exception handler
   */
  private void handle() {
    SignupFailedException exception = new SignupFailedException();
    exception.setMessages(Collections.singletonList(signupFailedErrorMessage));
    throw exception;
  }
}
