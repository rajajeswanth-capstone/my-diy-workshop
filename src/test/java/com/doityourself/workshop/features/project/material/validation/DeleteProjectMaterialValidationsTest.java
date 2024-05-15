package com.doityourself.workshop.features.project.material.validation;

import com.doityourself.workshop.features.project.material.exception.DeleteProjectMaterialFailedException;
import org.junit.jupiter.api.Test;

public class DeleteProjectMaterialValidationsTest {
  @Test
  public void testValidateDeleteRequest() {
    // Initialize
    DeleteProjectMaterialValidations deleteProjectMaterialValidations = new DeleteProjectMaterialValidations();
    deleteProjectMaterialValidations.projectDeleteMaterialIdRequiredErrorMessage = "field1message1";
    deleteProjectMaterialValidations.projectDeleteMaterialIdFieldName = "field1";

    // Execute
    Exception expectedException = null;
    try {
      deleteProjectMaterialValidations.validateDeleteProjectMaterialRequest(1L);
    } catch (Exception exception) {
      expectedException = exception;
    }

    // Assertions
    assert expectedException == null;
  }

  @Test
  public void testValidateDeleteRequestRequired() {
    // Initialize
    DeleteProjectMaterialValidations deleteProjectMaterialValidations = new DeleteProjectMaterialValidations();
    deleteProjectMaterialValidations.projectDeleteMaterialIdRequiredErrorMessage = "field1message1";
    deleteProjectMaterialValidations.projectDeleteMaterialIdFieldName = "field1";

    // Execute
    Exception expectedException = null;
    try {
      deleteProjectMaterialValidations.validateDeleteProjectMaterialRequest(null);
    } catch (Exception exception) {
      expectedException = exception;
    }

    // Assertions
    assert expectedException instanceof DeleteProjectMaterialFailedException;
    assert ((DeleteProjectMaterialFailedException) expectedException).getFieldMessages().get("field1").equals("field1message1");
  }
}
