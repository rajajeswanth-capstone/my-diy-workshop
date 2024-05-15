package com.doityourself.workshop.features.project.detail.validation;

import com.doityourself.workshop.features.project.detail.exception.DeleteProjectInstructionFailedException;
import org.junit.jupiter.api.Test;

public class DeleteProjectInstructionValidationsTest {
  @Test
  public void testValidateLoginRequest() {
    // Initialize
    DeleteProjectInstructionValidations deleteProjectInstructionValidations = new DeleteProjectInstructionValidations();
    deleteProjectInstructionValidations.projectDeleteInstructionIdRequiredErrorMessage = "message1";
    deleteProjectInstructionValidations.projectDeleteInstructionIdFieldName = "field1";

    // Execute
    Exception expectedException = null;
    try {
      deleteProjectInstructionValidations.validateDeleteProjectInstructionRequest(1L);
    } catch (Exception exception) {
      expectedException = exception;
    }

    // Assertions
    assert expectedException == null;
  }

  @Test
  public void testValidateLoginRequestIdRequired() {
    // Initialize
    DeleteProjectInstructionValidations deleteProjectInstructionValidations = new DeleteProjectInstructionValidations();
    deleteProjectInstructionValidations.projectDeleteInstructionIdRequiredErrorMessage = "message1";
    deleteProjectInstructionValidations.projectDeleteInstructionIdFieldName = "field1";

    // Execute
    Exception expectedException = null;
    try {
      deleteProjectInstructionValidations.validateDeleteProjectInstructionRequest(null);
    } catch (Exception exception) {
      expectedException = exception;
    }

    // Assertions
    assert expectedException instanceof DeleteProjectInstructionFailedException;
    assert ((DeleteProjectInstructionFailedException) expectedException).getFieldMessages().get("field1").equals("message1");
  }
}
