package com.doityourself.workshop.features.project.material.exceptionHandler;

import com.doityourself.workshop.common.command.CommandDTO;
import com.doityourself.workshop.features.project.material.exception.UpdateProjectMaterialFailedException;
import org.junit.jupiter.api.Test;

public class UpdateProjectMaterialGlobalExceptionHandlerTest {
  @Test
  public void testHandleRuntimeException() {
    // Initialize
    UpdateProjectMaterialGlobalExceptionHandler updateProjectMaterialGlobalExceptionHandler = new UpdateProjectMaterialGlobalExceptionHandler();
    updateProjectMaterialGlobalExceptionHandler.projectUpdateMaterialFailedErrorMessage = "Failed";

    // Execute
    Exception expectedException = null;
    try {
      updateProjectMaterialGlobalExceptionHandler.handleRuntimeException(CommandDTO.builder().build(), new NullPointerException());
    } catch (Exception exception) {
      expectedException = exception;
    }

    // Assertions
    assert expectedException != null;
    assert expectedException instanceof UpdateProjectMaterialFailedException;
    assert ((UpdateProjectMaterialFailedException)expectedException).getMessages().get(0).equals("Failed");
  }
}
