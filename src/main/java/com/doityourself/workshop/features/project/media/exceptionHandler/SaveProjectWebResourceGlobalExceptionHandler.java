package com.doityourself.workshop.features.project.media.exceptionHandler;

import com.doityourself.workshop.common.command.CommandDTO;
import com.doityourself.workshop.common.command.ICommandExceptionHandler;
import com.doityourself.workshop.features.project.media.exception.SaveProjectWebResourceFailedException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Collections;

/**
 * Save Project Web Resource Global Exception Handler
 */
@Slf4j
@Component
public class SaveProjectWebResourceGlobalExceptionHandler implements ICommandExceptionHandler<CommandDTO> {
  @Value("${project.save.webresource.validation.failed}")
  String projectSaveWebResourceFailedErrorMessage;

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
    SaveProjectWebResourceFailedException exception = new SaveProjectWebResourceFailedException();
    exception.setMessages(Collections.singletonList(projectSaveWebResourceFailedErrorMessage));
    throw exception;
  }
}
