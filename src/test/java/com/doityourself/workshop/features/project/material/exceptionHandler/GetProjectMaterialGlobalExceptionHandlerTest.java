package com.doityourself.workshop.features.project.material.exceptionHandler;

import com.doityourself.workshop.common.command.CommandDTO;
import com.doityourself.workshop.features.project.material.exception.GetProjectMaterialFailedException;
import org.junit.jupiter.api.Test;

public class GetProjectMaterialGlobalExceptionHandlerTest {
  @Test
  public void testHandleRuntimeException() {
    // Initialize
    GetProjectMaterialGlobalExceptionHandler getProjectMaterialGlobalExceptionHandler = new GetProjectMaterialGlobalExceptionHandler();
    getProjectMaterialGlobalExceptionHandler.projectMaterialFailedErrorMessage = "Failed";

    // Execute
    Exception expectedException = null;
    try {
      getProjectMaterialGlobalExceptionHandler.handleRuntimeException(CommandDTO.builder().build(), new NullPointerException());
    } catch (Exception exception) {
      expectedException = exception;
    }

    // Assertions
    assert expectedException != null;
    assert expectedException instanceof GetProjectMaterialFailedException;
    assert ((GetProjectMaterialFailedException)expectedException).getMessages().get(0).equals("Failed");
  }
}
