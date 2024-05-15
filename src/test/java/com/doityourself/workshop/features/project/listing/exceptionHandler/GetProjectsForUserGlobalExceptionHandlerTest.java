package com.doityourself.workshop.features.project.listing.exceptionHandler;

import com.doityourself.workshop.common.command.CommandDTO;
import com.doityourself.workshop.features.project.listing.exception.ProjectsForUserGetFailedException;
import org.junit.jupiter.api.Test;

public class GetProjectsForUserGlobalExceptionHandlerTest {
  @Test
  public void testHandleRuntimeException() {
    // Initialize
    GetProjectsForUserGlobalExceptionHandler getProjectsForUserGlobalExceptionHandler = new GetProjectsForUserGlobalExceptionHandler();
    getProjectsForUserGlobalExceptionHandler.projectsGetForUserFailedErrorMessage = "Failed";

    // Execute
    Exception expectedException = null;
    try {
      getProjectsForUserGlobalExceptionHandler.handleRuntimeException(CommandDTO.builder().build(), new NullPointerException());
    } catch (Exception exception) {
      expectedException = exception;
    }

    // Assertions
    assert expectedException != null;
    assert expectedException instanceof ProjectsForUserGetFailedException;
    assert ((ProjectsForUserGetFailedException)expectedException).getMessages().get(0).equals("Failed");
  }
}
