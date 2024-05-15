package com.doityourself.workshop.features.project.material.validation;

import com.doityourself.workshop.database.entities.DiyProjectMaterial;
import com.doityourself.workshop.features.project.material.exception.GetProjectMaterialFailedException;
import org.junit.jupiter.api.Test;

public class GetProjectMaterialValidationsTest {
  @Test
  public void testValidateGetProjectMaterialRequest() {
    // Initialize
    GetProjectMaterialValidations getProjectMaterialValidations = new GetProjectMaterialValidations();
    getProjectMaterialValidations.projectMaterialIdRequiredErrorMessage = "field1message1";
    getProjectMaterialValidations.projectMaterialIdFieldName = "field1";
    getProjectMaterialValidations.projectMaterialFailedErrorMessage = "message1";

    // Execute
    Exception expectedException = null;
    try {
      getProjectMaterialValidations.validateGetProjectMaterialRequest(1L);
    } catch (Exception exception) {
      expectedException = exception;
    }

    // Assertions
    assert expectedException == null;
  }

  @Test
  public void testValidateGetProjectMaterialRequestRequired() {
    // Initialize
    GetProjectMaterialValidations getProjectMaterialValidations = new GetProjectMaterialValidations();
    getProjectMaterialValidations.projectMaterialIdRequiredErrorMessage = "field1message1";
    getProjectMaterialValidations.projectMaterialIdFieldName = "field1";
    getProjectMaterialValidations.projectMaterialFailedErrorMessage = "message1";

    // Execute
    Exception expectedException = null;
    try {
      getProjectMaterialValidations.validateGetProjectMaterialRequest(null);
    } catch (Exception exception) {
      expectedException = exception;
    }

    // Assertions
    assert expectedException instanceof GetProjectMaterialFailedException;
    assert ((GetProjectMaterialFailedException) expectedException).getFieldMessages().get("field1").equals("field1message1");
  }

  @Test
  public void testValidateDiyProjectMaterialEntity() {
    // Initialize
    GetProjectMaterialValidations getProjectMaterialValidations = new GetProjectMaterialValidations();
    getProjectMaterialValidations.projectMaterialIdRequiredErrorMessage = "field1message1";
    getProjectMaterialValidations.projectMaterialIdFieldName = "field1";
    getProjectMaterialValidations.projectMaterialFailedErrorMessage = "message1";

    DiyProjectMaterial diyProjectMaterial = DiyProjectMaterial.builder().build();

    // Execute
    Exception expectedException = null;
    try {
      getProjectMaterialValidations.validateDiyProjectMaterialEntity(diyProjectMaterial);
    } catch (Exception exception) {
      expectedException = exception;
    }

    // Assertions
    assert expectedException == null;
  }

  @Test
  public void testValidateDiyProjectMaterialEntityRequired() {
    // Initialize
    GetProjectMaterialValidations getProjectMaterialValidations = new GetProjectMaterialValidations();
    getProjectMaterialValidations.projectMaterialIdRequiredErrorMessage = "field1message1";
    getProjectMaterialValidations.projectMaterialIdFieldName = "field1";
    getProjectMaterialValidations.projectMaterialFailedErrorMessage = "message1";

    // Execute
    Exception expectedException = null;
    try {
      getProjectMaterialValidations.validateDiyProjectMaterialEntity(null);
    } catch (Exception exception) {
      expectedException = exception;
    }

    // Assertions
    assert expectedException instanceof GetProjectMaterialFailedException;
    assert ((GetProjectMaterialFailedException) expectedException).getMessages().get(0).equals("message1");
  }
}
