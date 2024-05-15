package com.doityourself.workshop.features.project.listing.exceptionHandler;

import com.doityourself.workshop.common.command.CommandDTO;
import com.doityourself.workshop.common.command.ICommandExceptionHandler;
import com.doityourself.workshop.features.project.listing.exception.ProjectsForUserGetFailedException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Collections;

/**
 * Get Projects for User Command
 */
@Slf4j
@Component
public class GetProjectsForUserGlobalExceptionHandler implements ICommandExceptionHandler<CommandDTO> {
  @Value("${project.get.for.user.validation.failed}")
  String projectsGetForUserFailedErrorMessage;

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
    ProjectsForUserGetFailedException exception = new ProjectsForUserGetFailedException();
    exception.setMessages(Collections.singletonList(projectsGetForUserFailedErrorMessage));
    throw exception;
  }
}
