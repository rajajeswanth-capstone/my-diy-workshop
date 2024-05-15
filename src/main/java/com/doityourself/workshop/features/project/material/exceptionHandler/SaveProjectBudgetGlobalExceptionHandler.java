package com.doityourself.workshop.features.project.material.exceptionHandler;

import com.doityourself.workshop.common.command.CommandDTO;
import com.doityourself.workshop.common.command.ICommandExceptionHandler;
import com.doityourself.workshop.features.project.material.exception.SaveProjectBudgetFailedException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Collections;

/**
 * Save Project Budget Global Exception Handler
 */
@Slf4j
@Component
public class SaveProjectBudgetGlobalExceptionHandler implements ICommandExceptionHandler<CommandDTO> {
  @Value("${project.save.budget.validation.failed}")
  String projectSaveBudgetFailedErrorMessage;

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
    SaveProjectBudgetFailedException exception = new SaveProjectBudgetFailedException();
    exception.setMessages(Collections.singletonList(projectSaveBudgetFailedErrorMessage));
    throw exception;
  }
}
