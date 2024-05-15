package com.doityourself.workshop.features.project.detail.validation;

import com.doityourself.workshop.database.entities.DiyProjectInstruction;
import com.doityourself.workshop.features.project.detail.exception.UpdateProjectInstructionFailedException;
import com.doityourself.workshop.features.project.detail.representation.ProjectDetailInstructionRepresentation;
import org.junit.jupiter.api.Test;

public class UpdateProjectInstructionValidationsTest {
  @Test
  public void testValidateUpdateInstructionRequest() {
    // Initialize
    UpdateProjectInstructionValidations updateProjectInstructionValidations = new UpdateProjectInstructionValidations();
    updateProjectInstructionValidations.projectUpdateInstructionTitleRequiredErrorMessage = "field1message1";
    updateProjectInstructionValidations.projectUpdateInstructionIdRequiredErrorMessage = "field2message1";
    updateProjectInstructionValidations.projectUpdateInstructionFailedErrorMessage = "message1";
    updateProjectInstructionValidations.projectUpdateInstructionTitleFieldName = "field1";
    updateProjectInstructionValidations.projectUpdateInstructionIdFieldName = "field2";

    // Execute
    Exception expectedException = null;
    try {
      updateProjectInstructionValidations.validateUpdateInstructionRequest(ProjectDetailInstructionRepresentation.builder().id(1L).title("title").build());
    } catch (Exception exception) {
      expectedException = exception;
    }

    // Assertions
    assert expectedException == null;
  }

  @Test
  public void testValidateUpdateInstructionRequestRequired() {
    // Initialize
    UpdateProjectInstructionValidations updateProjectInstructionValidations = new UpdateProjectInstructionValidations();
    updateProjectInstructionValidations.projectUpdateInstructionTitleRequiredErrorMessage = "field1message1";
    updateProjectInstructionValidations.projectUpdateInstructionIdRequiredErrorMessage = "field2message1";
    updateProjectInstructionValidations.projectUpdateInstructionFailedErrorMessage = "message1";
    updateProjectInstructionValidations.projectUpdateInstructionTitleFieldName = "field1";
    updateProjectInstructionValidations.projectUpdateInstructionIdFieldName = "field2";

    // Execute
    Exception expectedException = null;
    try {
      updateProjectInstructionValidations.validateUpdateInstructionRequest(ProjectDetailInstructionRepresentation.builder().title("title").build());
    } catch (Exception exception) {
      expectedException = exception;
    }

    // Assertions
    assert expectedException instanceof UpdateProjectInstructionFailedException;
    assert ((UpdateProjectInstructionFailedException) expectedException).getFieldMessages().get("field2").equals("field2message1");

    try {
      updateProjectInstructionValidations.validateUpdateInstructionRequest(ProjectDetailInstructionRepresentation.builder().id(1L).build());
    } catch (Exception exception) {
      expectedException = exception;
    }

    // Assertions
    assert expectedException instanceof UpdateProjectInstructionFailedException;
    assert ((UpdateProjectInstructionFailedException) expectedException).getFieldMessages().get("field1").equals("field1message1");
  }

  @Test
  public void testValidateDiyProjectInstructionEntity() {
    // Initialize
    UpdateProjectInstructionValidations updateProjectInstructionValidations = new UpdateProjectInstructionValidations();
    updateProjectInstructionValidations.projectUpdateInstructionTitleRequiredErrorMessage = "field1message1";
    updateProjectInstructionValidations.projectUpdateInstructionIdRequiredErrorMessage = "field2message1";
    updateProjectInstructionValidations.projectUpdateInstructionFailedErrorMessage = "message1";
    updateProjectInstructionValidations.projectUpdateInstructionTitleFieldName = "field1";
    updateProjectInstructionValidations.projectUpdateInstructionIdFieldName = "field2";

    // Execute
    Exception expectedException = null;
    try {
      updateProjectInstructionValidations.validateDiyProjectInstructionEntity(DiyProjectInstruction.builder().build());
    } catch (Exception exception) {
      expectedException = exception;
    }

    // Assertions
    assert expectedException == null;
  }

  @Test
  public void testValidateDiyProjectInstructionEntityRequired() {
    // Initialize
    UpdateProjectInstructionValidations updateProjectInstructionValidations = new UpdateProjectInstructionValidations();
    updateProjectInstructionValidations.projectUpdateInstructionTitleRequiredErrorMessage = "field1message1";
    updateProjectInstructionValidations.projectUpdateInstructionIdRequiredErrorMessage = "field2message1";
    updateProjectInstructionValidations.projectUpdateInstructionFailedErrorMessage = "message1";
    updateProjectInstructionValidations.projectUpdateInstructionTitleFieldName = "field1";
    updateProjectInstructionValidations.projectUpdateInstructionIdFieldName = "field2";

    // Execute
    Exception expectedException = null;
    try {
      updateProjectInstructionValidations.validateDiyProjectInstructionEntity(null);
    } catch (Exception exception) {
      expectedException = exception;
    }

    // Assertions
    assert expectedException instanceof UpdateProjectInstructionFailedException;
    assert ((UpdateProjectInstructionFailedException) expectedException).getMessages().get(0).equals("message1");
  }
}
