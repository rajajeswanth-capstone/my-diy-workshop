package com.doityourself.workshop.features.login.exceptionHandler;

import com.doityourself.workshop.common.command.CommandDTO;
import com.doityourself.workshop.common.command.ICommandExceptionHandler;
import com.doityourself.workshop.features.login.exception.LoginFailedException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Collections;

/**
 * Login Global Exception Handler
 */
@Slf4j
@Component
public class LoginGlobalExceptionHandler implements ICommandExceptionHandler<CommandDTO> {
  @Value("${login.validation.failed}")
  String loginFailedErrorMessage;

  /**
   * Method to handle runtime exception
   *
   * @param dto {@link CommandDTO}
   * @param runtimeException {@link RuntimeException}
   */
  @Override
  public void handleRuntimeException(CommandDTO dto, RuntimeException runtimeException) {
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
   * Common exception handling for global errors
   */
  private void handle() {
    LoginFailedException exception = new LoginFailedException();
    exception.setMessages(Collections.singletonList(loginFailedErrorMessage));
    throw exception;
  }
}
