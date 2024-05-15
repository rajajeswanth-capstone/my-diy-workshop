package com.doityourself.workshop.features.project.material.exceptionHandler;

import com.doityourself.workshop.common.command.CommandDTO;
import com.doityourself.workshop.common.command.ICommandExceptionHandler;
import com.doityourself.workshop.features.project.material.exception.DeleteProjectMaterialFailedException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Collections;

/**
 * Delete Project Material Global Exception Handler
 */
@Slf4j
@Component
public class DeleteProjectMaterialGlobalExceptionHandler implements ICommandExceptionHandler<CommandDTO> {
  @Value("${project.delete.material.validation.id.required}")
  String projectDeleteMaterialIdRequiredErrorMessage;

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
    DeleteProjectMaterialFailedException exception = new DeleteProjectMaterialFailedException();
    exception.setMessages(Collections.singletonList(projectDeleteMaterialIdRequiredErrorMessage));
    throw exception;
  }
}
