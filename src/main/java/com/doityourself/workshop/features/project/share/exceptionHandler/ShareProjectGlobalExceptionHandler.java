package com.doityourself.workshop.features.project.share.exceptionHandler;

import com.doityourself.workshop.common.command.CommandDTO;
import com.doityourself.workshop.common.command.ICommandExceptionHandler;
import com.doityourself.workshop.features.project.share.exception.ProjectShareFailedException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Collections;

/**
 * Share Project Global Exception Handler
 */
@Slf4j
@Component
public class ShareProjectGlobalExceptionHandler implements ICommandExceptionHandler<CommandDTO> {
  @Value("${project.share.validation.failed}")
  String projectShareFailedErrorMessage;

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
    ProjectShareFailedException exception = new ProjectShareFailedException();
    exception.setMessages(Collections.singletonList(projectShareFailedErrorMessage));
    throw exception;
  }
}
