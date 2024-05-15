package com.doityourself.workshop.features.project.detail.validation;

import com.doityourself.workshop.database.entities.DiyProjectInstruction;
import com.doityourself.workshop.features.project.detail.exception.SaveProjectInstructionFailedException;
import com.doityourself.workshop.features.project.detail.representation.ProjectDetailInstructionRepresentation;
import org.junit.jupiter.api.Test;

public class SaveProjectInstructionValidationsTest {
  @Test
  public void testValidateSaveInstructionRequest() {
    // Initialize
    SaveProjectInstructionValidations saveProjectInstructionValidations = new SaveProjectInstructionValidations();
    saveProjectInstructionValidations.projectSaveInstructionTitleRequiredErrorMessage = "field1message1";
    saveProjectInstructionValidations.projectSaveInstructionFailedErrorMessage = "message1";
    saveProjectInstructionValidations.projectSaveInstructionTitleFieldName = "field1";

    // Execute
    Exception expectedException = null;
    try {
      saveProjectInstructionValidations.validateSaveInstructionRequest(ProjectDetailInstructionRepresentation.builder().title("title").build());
    } catch (Exception exception) {
      expectedException = exception;
    }

    // Assertions
    assert expectedException == null;
  }

  @Test
  public void testValidateSaveInstructionRequestRequired() {
    // Initialize
    SaveProjectInstructionValidations saveProjectInstructionValidations = new SaveProjectInstructionValidations();
    saveProjectInstructionValidations.projectSaveInstructionTitleRequiredErrorMessage = "field1message1";
    saveProjectInstructionValidations.projectSaveInstructionFailedErrorMessage = "message1";
    saveProjectInstructionValidations.projectSaveInstructionTitleFieldName = "field1";

    // Execute
    Exception expectedException = null;
    try {
      saveProjectInstructionValidations.validateSaveInstructionRequest(ProjectDetailInstructionRepresentation.builder().build());
    } catch (Exception exception) {
      expectedException = exception;
    }

    // Assertions
    assert expectedException instanceof SaveProjectInstructionFailedException;
    assert ((SaveProjectInstructionFailedException) expectedException).getFieldMessages().get("field1").equals("field1message1");
  }

  @Test
  public void testValidateDiyProjectInstructionEntity() {
    // Initialize
    SaveProjectInstructionValidations saveProjectInstructionValidations = new SaveProjectInstructionValidations();
    saveProjectInstructionValidations.projectSaveInstructionTitleRequiredErrorMessage = "field1message1";
    saveProjectInstructionValidations.projectSaveInstructionFailedErrorMessage = "message1";
    saveProjectInstructionValidations.projectSaveInstructionTitleFieldName = "field1";

    // Execute
    Exception expectedException = null;
    try {
      saveProjectInstructionValidations.validateDiyProjectInstructionEntity(DiyProjectInstruction.builder().title("title").build());
    } catch (Exception exception) {
      expectedException = exception;
    }

    // Assertions
    assert expectedException == null;
  }

  @Test
  public void testValidateDiyProjectInstructionEntityRequired() {
    // Initialize
    SaveProjectInstructionValidations saveProjectInstructionValidations = new SaveProjectInstructionValidations();
    saveProjectInstructionValidations.projectSaveInstructionTitleRequiredErrorMessage = "field1message1";
    saveProjectInstructionValidations.projectSaveInstructionFailedErrorMessage = "message1";
    saveProjectInstructionValidations.projectSaveInstructionTitleFieldName = "field1";

    // Execute
    Exception expectedException = null;
    try {
      saveProjectInstructionValidations.validateDiyProjectInstructionEntity(null);
    } catch (Exception exception) {
      expectedException = exception;
    }

    // Assertions
    assert expectedException instanceof SaveProjectInstructionFailedException;
    assert ((SaveProjectInstructionFailedException) expectedException).getMessages().get(0).equals("message1");
  }
}
