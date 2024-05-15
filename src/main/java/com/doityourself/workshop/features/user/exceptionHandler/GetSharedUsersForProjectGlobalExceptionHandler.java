package com.doityourself.workshop.features.user.exceptionHandler;

import com.doityourself.workshop.common.command.CommandDTO;
import com.doityourself.workshop.common.command.ICommandExceptionHandler;
import com.doityourself.workshop.features.user.exception.GetSharedUsersForProjectFailedException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Collections;

/**
 * Get Shared Users for project Global Exception Handler
 */
@Slf4j
@Component
public class GetSharedUsersForProjectGlobalExceptionHandler implements ICommandExceptionHandler<CommandDTO> {
  @Value("${user.share.validation.failed}")
  String userShareValidationErrorMessage;

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
   * Common exception handler
   */
  private void handle() {
    GetSharedUsersForProjectFailedException exception = new GetSharedUsersForProjectFailedException();
    exception.setMessages(Collections.singletonList(userShareValidationErrorMessage));
    throw exception;
  }
}
