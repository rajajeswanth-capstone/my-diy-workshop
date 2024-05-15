package com.doityourself.workshop.features.project.material.exceptionHandler;

import com.doityourself.workshop.common.command.CommandDTO;
import com.doityourself.workshop.features.project.material.exception.DeleteProjectMaterialFailedException;
import org.junit.jupiter.api.Test;

public class DeleteProjectMaterialGlobalExceptionHandlerTest {
  @Test
  public void testHandleRuntimeException() {
    // Initialize
    DeleteProjectMaterialGlobalExceptionHandler deleteProjectMaterialGlobalExceptionHandler = new DeleteProjectMaterialGlobalExceptionHandler();
    deleteProjectMaterialGlobalExceptionHandler.projectDeleteMaterialIdRequiredErrorMessage = "Failed";

    // Execute
    Exception expectedException = null;
    try {
      deleteProjectMaterialGlobalExceptionHandler.handleRuntimeException(CommandDTO.builder().build(), new NullPointerException());
    } catch (Exception exception) {
      expectedException = exception;
    }

    // Assertions
    assert expectedException != null;
    assert expectedException instanceof DeleteProjectMaterialFailedException;
    assert ((DeleteProjectMaterialFailedException)expectedException).getMessages().get(0).equals("Failed");
  }
}
