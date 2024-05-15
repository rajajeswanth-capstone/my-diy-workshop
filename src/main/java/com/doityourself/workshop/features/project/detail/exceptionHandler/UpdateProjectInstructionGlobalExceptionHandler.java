package com.doityourself.workshop.features.project.detail.exceptionHandler;

import com.doityourself.workshop.common.command.CommandDTO;
import com.doityourself.workshop.common.command.ICommandExceptionHandler;
import com.doityourself.workshop.features.project.detail.exception.UpdateProjectInstructionFailedException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Collections;

/**
 * Update Project Description Global Exception Handler
 */
@Slf4j
@Component
public class UpdateProjectInstructionGlobalExceptionHandler implements ICommandExceptionHandler<CommandDTO> {
  @Value("${project.update.instruction.validation.failed}")
  String projectUpdateInstructionFailedErrorMessage;

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
    UpdateProjectInstructionFailedException exception = new UpdateProjectInstructionFailedException();
    exception.setMessages(Collections.singletonList(projectUpdateInstructionFailedErrorMessage));
    throw exception;
  }
}
