package com.doityourself.workshop.features.project.material.exceptionHandler;

import com.doityourself.workshop.common.command.CommandDTO;
import com.doityourself.workshop.features.project.material.exception.SaveProjectMaterialFailedException;
import org.junit.jupiter.api.Test;

public class SaveProjectMaterialGlobalExceptionHandlerTest {
  @Test
  public void testHandleRuntimeException() {
    // Initialize
    SaveProjectMaterialGlobalExceptionHandler saveProjectMaterialGlobalExceptionHandler = new SaveProjectMaterialGlobalExceptionHandler();
    saveProjectMaterialGlobalExceptionHandler.projectSaveMaterialFailedErrorMessage = "Failed";

    // Execute
    Exception expectedException = null;
    try {
      saveProjectMaterialGlobalExceptionHandler.handleRuntimeException(CommandDTO.builder().build(), new NullPointerException());
    } catch (Exception exception) {
      expectedException = exception;
    }

    // Assertions
    assert expectedException != null;
    assert expectedException instanceof SaveProjectMaterialFailedException;
    assert ((SaveProjectMaterialFailedException)expectedException).getMessages().get(0).equals("Failed");
  }
}
