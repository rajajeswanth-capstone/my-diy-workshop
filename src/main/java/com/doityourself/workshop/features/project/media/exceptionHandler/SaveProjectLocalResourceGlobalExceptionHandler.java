package com.doityourself.workshop.features.project.media.exceptionHandler;

import com.doityourself.workshop.common.command.CommandDTO;
import com.doityourself.workshop.common.command.ICommandExceptionHandler;
import com.doityourself.workshop.features.project.media.exception.SaveProjectLocalResourceFailedException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Collections;

/**
 * Save Project Local Resource Global Exception Handler
 */
@Slf4j
@Component
public class SaveProjectLocalResourceGlobalExceptionHandler implements ICommandExceptionHandler<CommandDTO> {
  @Value("${project.save.localresource.validation.failed}")
  String projectSaveLocalResourceFailedErrorMessage;

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
    SaveProjectLocalResourceFailedException exception = new SaveProjectLocalResourceFailedException();
    exception.setMessages(Collections.singletonList(projectSaveLocalResourceFailedErrorMessage));
    throw exception;
  }
}
