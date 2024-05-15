package com.doityourself.workshop.features.project.listing.exceptionHandler;

import com.doityourself.workshop.common.command.CommandDTO;
import com.doityourself.workshop.common.command.ICommandExceptionHandler;
import com.doityourself.workshop.features.project.listing.exception.ProjectDeleteFailedException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Collections;

/**
 * Delete Project exception handler Command
 */
@Slf4j
@Component
public class DeleteProjectGlobalExceptionHandler implements ICommandExceptionHandler<CommandDTO> {
  @Value("${project.delete.validation.failed}")
  String projectDeleteFailedErrorMessage;

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
    ProjectDeleteFailedException exception = new ProjectDeleteFailedException();
    exception.setMessages(Collections.singletonList(projectDeleteFailedErrorMessage));
    throw exception;
  }
}
