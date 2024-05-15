package com.doityourself.workshop.features.project.material.validation;

import com.doityourself.workshop.database.entities.DiyProjectMaterial;
import com.doityourself.workshop.features.project.material.exception.UpdateProjectMaterialFailedException;
import com.doityourself.workshop.features.project.material.representation.ProjectDetailMaterialRepresentation;
import org.junit.jupiter.api.Test;

public class UpdateProjectMaterialValidationsTest {
  @Test
  public void testValidateUpdateMaterialRequest() {
    // Initialize
    UpdateProjectMaterialValidations updateProjectMaterialValidations = new UpdateProjectMaterialValidations();
    updateProjectMaterialValidations.projectUpdateMaterialFailedErrorMessage = "message1";
    updateProjectMaterialValidations.projectUpdateMaterialDescriptionRequiredErrorMessage = "field1message1";
    updateProjectMaterialValidations.projectUpdateMaterialDescriptionFieldName = "field1";
    updateProjectMaterialValidations.projectUpdateMaterialIdRequiredErrorMessage = "field2message1";
    updateProjectMaterialValidations.projectUpdateMaterialIdFieldName = "field2";
    updateProjectMaterialValidations.projectUpdateMaterialUnitsRequiredErrorMessage = "field3message1";
    updateProjectMaterialValidations.projectUpdateSaveMaterialUnitsFieldName = "field3";

    // Execute
    Exception expectedException = null;
    try {
      updateProjectMaterialValidations.validateUpdateMaterialRequest(
          ProjectDetailMaterialRepresentation
              .builder()
              .id(1L).materialDescription("description").units(1L).pricePerUnit(10D)
              .build()
      );
    } catch (Exception exception) {
      expectedException = exception;
    }

    // Assertions
    assert expectedException == null;
  }

  @Test
  public void testValidateUpdateMaterialRequestDescriptionRequired() {
    // Initialize
    UpdateProjectMaterialValidations updateProjectMaterialValidations = new UpdateProjectMaterialValidations();
    updateProjectMaterialValidations.projectUpdateMaterialFailedErrorMessage = "message1";
    updateProjectMaterialValidations.projectUpdateMaterialDescriptionRequiredErrorMessage = "field1message1";
    updateProjectMaterialValidations.projectUpdateMaterialDescriptionFieldName = "field1";
    updateProjectMaterialValidations.projectUpdateMaterialIdRequiredErrorMessage = "field2message1";
    updateProjectMaterialValidations.projectUpdateMaterialIdFieldName = "field2";
    updateProjectMaterialValidations.projectUpdateMaterialUnitsRequiredErrorMessage = "field3message1";
    updateProjectMaterialValidations.projectUpdateSaveMaterialUnitsFieldName = "field3";

    // Execute
    Exception expectedException = null;
    try {
      updateProjectMaterialValidations.validateUpdateMaterialRequest(
          ProjectDetailMaterialRepresentation
              .builder()
              .units(1L).pricePerUnit(10D)
              .build()
      );
    } catch (Exception exception) {
      expectedException = exception;
    }

    // Assertions
    assert expectedException instanceof UpdateProjectMaterialFailedException;
    assert ((UpdateProjectMaterialFailedException) expectedException).getFieldMessages().get("field1").equals("field1message1");
  }

  @Test
  public void testValidateUpdateMaterialRequestUnitsRequired() {
    // Initialize
    UpdateProjectMaterialValidations updateProjectMaterialValidations = new UpdateProjectMaterialValidations();
    updateProjectMaterialValidations.projectUpdateMaterialFailedErrorMessage = "message1";
    updateProjectMaterialValidations.projectUpdateMaterialDescriptionRequiredErrorMessage = "field1message1";
    updateProjectMaterialValidations.projectUpdateMaterialDescriptionFieldName = "field1";
    updateProjectMaterialValidations.projectUpdateMaterialIdRequiredErrorMessage = "field2message1";
    updateProjectMaterialValidations.projectUpdateMaterialIdFieldName = "field2";
    updateProjectMaterialValidations.projectUpdateMaterialUnitsRequiredErrorMessage = "field3message1";
    updateProjectMaterialValidations.projectUpdateSaveMaterialUnitsFieldName = "field3";

    // Execute
    Exception expectedException = null;
    try {
      updateProjectMaterialValidations.validateUpdateMaterialRequest(
          ProjectDetailMaterialRepresentation
              .builder()
              .materialDescription("description").pricePerUnit(10D)
              .build()
      );
    } catch (Exception exception) {
      expectedException = exception;
    }

    // Assertions
    assert expectedException instanceof UpdateProjectMaterialFailedException;
    assert ((UpdateProjectMaterialFailedException) expectedException).getFieldMessages().get("field2").equals("field2message1");
  }

  @Test
  public void testValidateUpdateMaterialRequestPricePerUnitRequired() {
    // Initialize
    UpdateProjectMaterialValidations updateProjectMaterialValidations = new UpdateProjectMaterialValidations();
    updateProjectMaterialValidations.projectUpdateMaterialFailedErrorMessage = "message1";
    updateProjectMaterialValidations.projectUpdateMaterialDescriptionRequiredErrorMessage = "field1message1";
    updateProjectMaterialValidations.projectUpdateMaterialDescriptionFieldName = "field1";
    updateProjectMaterialValidations.projectUpdateMaterialIdRequiredErrorMessage = "field2message1";
    updateProjectMaterialValidations.projectUpdateMaterialIdFieldName = "field2";
    updateProjectMaterialValidations.projectUpdateMaterialUnitsRequiredErrorMessage = "field3message1";
    updateProjectMaterialValidations.projectUpdateSaveMaterialUnitsFieldName = "field3";

    // Execute
    Exception expectedException = null;
    try {
      updateProjectMaterialValidations.validateUpdateMaterialRequest(
          ProjectDetailMaterialRepresentation
              .builder()
              .materialDescription("description").units(1L)
              .build()
      );
    } catch (Exception exception) {
      expectedException = exception;
    }

    // Assertions
    assert expectedException instanceof UpdateProjectMaterialFailedException;
    assert ((UpdateProjectMaterialFailedException) expectedException).getFieldMessages().get("field2").equals("field2message1");
  }

  @Test
  public void testValidateDiyProjectMaterialEntity() {
    // Initialize
    UpdateProjectMaterialValidations updateProjectMaterialValidations = new UpdateProjectMaterialValidations();
    updateProjectMaterialValidations.projectUpdateMaterialFailedErrorMessage = "message1";
    updateProjectMaterialValidations.projectUpdateMaterialDescriptionRequiredErrorMessage = "field1message1";
    updateProjectMaterialValidations.projectUpdateMaterialDescriptionFieldName = "field1";
    updateProjectMaterialValidations.projectUpdateMaterialIdRequiredErrorMessage = "field2message1";
    updateProjectMaterialValidations.projectUpdateMaterialIdFieldName = "field2";
    updateProjectMaterialValidations.projectUpdateMaterialUnitsRequiredErrorMessage = "field3message1";
    updateProjectMaterialValidations.projectUpdateSaveMaterialUnitsFieldName = "field3";

    // Execute
    Exception expectedException = null;
    try {
      updateProjectMaterialValidations.validateDiyProjectMaterialEntity(
          DiyProjectMaterial.builder().build()
      );
    } catch (Exception exception) {
      expectedException = exception;
    }

    // Assertions
    assert expectedException == null;
  }

  @Test
  public void testValidateDiyProjectMaterialEntityRequired() {
    // Initialize
    UpdateProjectMaterialValidations updateProjectMaterialValidations = new UpdateProjectMaterialValidations();
    updateProjectMaterialValidations.projectUpdateMaterialFailedErrorMessage = "message1";
    updateProjectMaterialValidations.projectUpdateMaterialDescriptionRequiredErrorMessage = "field1message1";
    updateProjectMaterialValidations.projectUpdateMaterialDescriptionFieldName = "field1";
    updateProjectMaterialValidations.projectUpdateMaterialIdRequiredErrorMessage = "field2message1";
    updateProjectMaterialValidations.projectUpdateMaterialIdFieldName = "field2";
    updateProjectMaterialValidations.projectUpdateMaterialUnitsRequiredErrorMessage = "field3message1";
    updateProjectMaterialValidations.projectUpdateSaveMaterialUnitsFieldName = "field3";

    // Execute
    Exception expectedException = null;
    try {
      updateProjectMaterialValidations.validateDiyProjectMaterialEntity(
          null
      );
    } catch (Exception exception) {
      expectedException = exception;
    }

    // Assertions
    assert expectedException instanceof UpdateProjectMaterialFailedException;
    assert ((UpdateProjectMaterialFailedException) expectedException).getMessages().get(0).equals("message1");
  }
}
