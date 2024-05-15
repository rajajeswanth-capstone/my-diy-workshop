package com.doityourself.workshop.features.project.material.exceptionHandler;

import com.doityourself.workshop.common.command.CommandDTO;
import com.doityourself.workshop.features.project.material.exception.SaveProjectBudgetFailedException;
import org.junit.jupiter.api.Test;

public class SaveProjectBudgetGlobalExceptionHandlerTest {
  @Test
  public void testHandleRuntimeException() {
    // Initialize
    SaveProjectBudgetGlobalExceptionHandler saveProjectBudgetGlobalExceptionHandler = new SaveProjectBudgetGlobalExceptionHandler();
    saveProjectBudgetGlobalExceptionHandler.projectSaveBudgetFailedErrorMessage = "Failed";

    // Execute
    Exception expectedException = null;
    try {
      saveProjectBudgetGlobalExceptionHandler.handleRuntimeException(CommandDTO.builder().build(), new NullPointerException());
    } catch (Exception exception) {
      expectedException = exception;
    }

    // Assertions
    assert expectedException != null;
    assert expectedException instanceof SaveProjectBudgetFailedException;
    assert ((SaveProjectBudgetFailedException)expectedException).getMessages().get(0).equals("Failed");
  }
}
