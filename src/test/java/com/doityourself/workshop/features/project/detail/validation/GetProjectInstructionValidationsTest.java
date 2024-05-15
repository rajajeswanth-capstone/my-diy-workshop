package com.doityourself.workshop.features.project.detail.validation;

import com.doityourself.workshop.database.entities.DiyProjectInstruction;
import com.doityourself.workshop.features.project.detail.exception.GetProjectInstructionFailedException;
import org.junit.jupiter.api.Test;

public class GetProjectInstructionValidationsTest {
  @Test
  public void testValidateGetProjectInstructionRequest() {
    // Initialize
    GetProjectInstructionValidations getProjectInstructionValidations = new GetProjectInstructionValidations();
    getProjectInstructionValidations.projectInstructionFailedErrorMessage = "field1message1";
    getProjectInstructionValidations.projectInstructionIdRequiredErrorMessage = "message1";
    getProjectInstructionValidations.projectInstructionIdFieldName = "field1";

    // Execute
    Exception expectedException = null;
    try {
      getProjectInstructionValidations.validateGetProjectInstructionRequest(1L);
    } catch (Exception exception) {
      expectedException = exception;
    }

    // Assertions
    assert expectedException == null;
  }

  @Test
  public void testValidateGetProjectInstructionRequestRequired() {
    // Initialize
    GetProjectInstructionValidations getProjectInstructionValidations = new GetProjectInstructionValidations();
    getProjectInstructionValidations.projectInstructionFailedErrorMessage = "field1message1";
    getProjectInstructionValidations.projectInstructionIdRequiredErrorMessage = "message1";
    getProjectInstructionValidations.projectInstructionIdFieldName = "field1";

    // Execute
    Exception expectedException = null;
    try {
      getProjectInstructionValidations.validateGetProjectInstructionRequest(null);
    } catch (Exception exception) {
      expectedException = exception;
    }

    // Assertions
    assert expectedException instanceof GetProjectInstructionFailedException;
    assert ((GetProjectInstructionFailedException) expectedException).getFieldMessages().get("field1").equals("message1");
  }

  @Test
  public void testValidateDiyProjectInstructionEntity() {
    // Initialize
    GetProjectInstructionValidations getProjectInstructionValidations = new GetProjectInstructionValidations();
    getProjectInstructionValidations.projectInstructionFailedErrorMessage = "field1message1";
    getProjectInstructionValidations.projectInstructionIdRequiredErrorMessage = "message1";
    getProjectInstructionValidations.projectInstructionIdFieldName = "field1";

    // Execute
    Exception expectedException = null;
    try {
      getProjectInstructionValidations.validateDiyProjectInstructionEntity(DiyProjectInstruction.builder().build());
    } catch (Exception exception) {
      expectedException = exception;
    }

    // Assertions
    assert expectedException == null;
  }

  @Test
  public void testValidateDiyProjectInstructionEntityRequired() {
    // Initialize
    GetProjectInstructionValidations getProjectInstructionValidations = new GetProjectInstructionValidations();
    getProjectInstructionValidations.projectInstructionFailedErrorMessage = "field1message1";
    getProjectInstructionValidations.projectInstructionIdRequiredErrorMessage = "message1";
    getProjectInstructionValidations.projectInstructionIdFieldName = "field1";

    // Execute
    Exception expectedException = null;
    try {
      getProjectInstructionValidations.validateDiyProjectInstructionEntity(null);
    } catch (Exception exception) {
      expectedException = exception;
    }

    // Assertions
    assert expectedException instanceof GetProjectInstructionFailedException;
    assert ((GetProjectInstructionFailedException) expectedException).getMessages().get(0).equals("field1message1");
  }
}
