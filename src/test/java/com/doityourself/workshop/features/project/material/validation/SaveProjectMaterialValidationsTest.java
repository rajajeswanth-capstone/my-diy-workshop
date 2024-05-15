package com.doityourself.workshop.features.project.material.validation;

import com.doityourself.workshop.database.entities.DiyProjectMaterial;
import com.doityourself.workshop.features.project.material.exception.SaveProjectMaterialFailedException;
import com.doityourself.workshop.features.project.material.representation.ProjectDetailMaterialRepresentation;
import org.junit.jupiter.api.Test;

public class SaveProjectMaterialValidationsTest {
  @Test
  public void testValidateSaveMaterialRequest() {
    // Initialize
    SaveProjectMaterialValidations saveProjectMaterialValidations = new SaveProjectMaterialValidations();
    saveProjectMaterialValidations.projectSaveMaterialFailedErrorMessage = "message1";
    saveProjectMaterialValidations.projectSaveMaterialDescriptionRequiredErrorMessage = "field1message1";
    saveProjectMaterialValidations.projectSaveMaterialDescriptionFieldName = "field1";
    saveProjectMaterialValidations.projectSaveMaterialUnitsRequiredErrorMessage = "field2message1";
    saveProjectMaterialValidations.projectSaveMaterialUnitsFieldName = "field2";
    saveProjectMaterialValidations.projectSaveMaterialPricePerUnitRequiredErrorMessage = "field3message1";
    saveProjectMaterialValidations.projectSaveMaterialPricePerUnitFieldName = "field3";

    // Execute
    Exception expectedException = null;
    try {
      saveProjectMaterialValidations.validateSaveMaterialRequest(
          ProjectDetailMaterialRepresentation
              .builder()
              .materialDescription("description").units(1L).pricePerUnit(10D)
              .build()
      );
    } catch (Exception exception) {
      expectedException = exception;
    }

    // Assertions
    assert expectedException == null;
  }

  @Test
  public void testValidateSaveMaterialRequestDescriptionRequired() {
    // Initialize
    SaveProjectMaterialValidations saveProjectMaterialValidations = new SaveProjectMaterialValidations();
    saveProjectMaterialValidations.projectSaveMaterialFailedErrorMessage = "message1";
    saveProjectMaterialValidations.projectSaveMaterialDescriptionRequiredErrorMessage = "field1message1";
    saveProjectMaterialValidations.projectSaveMaterialDescriptionFieldName = "field1";
    saveProjectMaterialValidations.projectSaveMaterialUnitsRequiredErrorMessage = "field2message1";
    saveProjectMaterialValidations.projectSaveMaterialUnitsFieldName = "field2";
    saveProjectMaterialValidations.projectSaveMaterialPricePerUnitRequiredErrorMessage = "field3message1";
    saveProjectMaterialValidations.projectSaveMaterialPricePerUnitFieldName = "field3";

    // Execute
    Exception expectedException = null;
    try {
      saveProjectMaterialValidations.validateSaveMaterialRequest(
          ProjectDetailMaterialRepresentation
              .builder()
              .units(1L).pricePerUnit(10D)
              .build()
      );
    } catch (Exception exception) {
      expectedException = exception;
    }

    // Assertions
    assert expectedException instanceof SaveProjectMaterialFailedException;
    assert ((SaveProjectMaterialFailedException) expectedException).getFieldMessages().get("field1").equals("field1message1");
  }

  @Test
  public void testValidateSaveMaterialRequestUnitsRequired() {
    // Initialize
    SaveProjectMaterialValidations saveProjectMaterialValidations = new SaveProjectMaterialValidations();
    saveProjectMaterialValidations.projectSaveMaterialFailedErrorMessage = "message1";
    saveProjectMaterialValidations.projectSaveMaterialDescriptionRequiredErrorMessage = "field1message1";
    saveProjectMaterialValidations.projectSaveMaterialDescriptionFieldName = "field1";
    saveProjectMaterialValidations.projectSaveMaterialUnitsRequiredErrorMessage = "field2message1";
    saveProjectMaterialValidations.projectSaveMaterialUnitsFieldName = "field2";
    saveProjectMaterialValidations.projectSaveMaterialPricePerUnitRequiredErrorMessage = "field3message1";
    saveProjectMaterialValidations.projectSaveMaterialPricePerUnitFieldName = "field3";

    // Execute
    Exception expectedException = null;
    try {
      saveProjectMaterialValidations.validateSaveMaterialRequest(
          ProjectDetailMaterialRepresentation
              .builder()
              .materialDescription("description").pricePerUnit(10D)
              .build()
      );
    } catch (Exception exception) {
      expectedException = exception;
    }

    // Assertions
    assert expectedException instanceof SaveProjectMaterialFailedException;
    assert ((SaveProjectMaterialFailedException) expectedException).getFieldMessages().get("field2").equals("field2message1");
  }

  @Test
  public void testValidateSaveMaterialRequestPricePerUnitRequired() {
    // Initialize
    SaveProjectMaterialValidations saveProjectMaterialValidations = new SaveProjectMaterialValidations();
    saveProjectMaterialValidations.projectSaveMaterialFailedErrorMessage = "message1";
    saveProjectMaterialValidations.projectSaveMaterialDescriptionRequiredErrorMessage = "field1message1";
    saveProjectMaterialValidations.projectSaveMaterialDescriptionFieldName = "field1";
    saveProjectMaterialValidations.projectSaveMaterialUnitsRequiredErrorMessage = "field2message1";
    saveProjectMaterialValidations.projectSaveMaterialUnitsFieldName = "field2";
    saveProjectMaterialValidations.projectSaveMaterialPricePerUnitRequiredErrorMessage = "field3message1";
    saveProjectMaterialValidations.projectSaveMaterialPricePerUnitFieldName = "field3";

    // Execute
    Exception expectedException = null;
    try {
      saveProjectMaterialValidations.validateSaveMaterialRequest(
          ProjectDetailMaterialRepresentation
              .builder()
              .materialDescription("description").units(1L)
              .build()
      );
    } catch (Exception exception) {
      expectedException = exception;
    }

    // Assertions
    assert expectedException instanceof SaveProjectMaterialFailedException;
    assert ((SaveProjectMaterialFailedException) expectedException).getFieldMessages().get("field3").equals("field3message1");
  }

  @Test
  public void testValidateDiyProjectMaterialEntity() {
    // Initialize
    SaveProjectMaterialValidations saveProjectMaterialValidations = new SaveProjectMaterialValidations();
    saveProjectMaterialValidations.projectSaveMaterialFailedErrorMessage = "message1";
    saveProjectMaterialValidations.projectSaveMaterialDescriptionRequiredErrorMessage = "field1message1";
    saveProjectMaterialValidations.projectSaveMaterialDescriptionFieldName = "field1";
    saveProjectMaterialValidations.projectSaveMaterialUnitsRequiredErrorMessage = "field2message1";
    saveProjectMaterialValidations.projectSaveMaterialUnitsFieldName = "field2";
    saveProjectMaterialValidations.projectSaveMaterialPricePerUnitRequiredErrorMessage = "field3message1";
    saveProjectMaterialValidations.projectSaveMaterialPricePerUnitFieldName = "field3";

    // Execute
    Exception expectedException = null;
    try {
      saveProjectMaterialValidations.validateDiyProjectMaterialEntity(
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
    SaveProjectMaterialValidations saveProjectMaterialValidations = new SaveProjectMaterialValidations();
    saveProjectMaterialValidations.projectSaveMaterialFailedErrorMessage = "message1";
    saveProjectMaterialValidations.projectSaveMaterialDescriptionRequiredErrorMessage = "field1message1";
    saveProjectMaterialValidations.projectSaveMaterialDescriptionFieldName = "field1";
    saveProjectMaterialValidations.projectSaveMaterialUnitsRequiredErrorMessage = "field2message1";
    saveProjectMaterialValidations.projectSaveMaterialUnitsFieldName = "field2";
    saveProjectMaterialValidations.projectSaveMaterialPricePerUnitRequiredErrorMessage = "field3message1";
    saveProjectMaterialValidations.projectSaveMaterialPricePerUnitFieldName = "field3";

    // Execute
    Exception expectedException = null;
    try {
      saveProjectMaterialValidations.validateDiyProjectMaterialEntity(
          null
      );
    } catch (Exception exception) {
      expectedException = exception;
    }

    // Assertions
    assert expectedException instanceof SaveProjectMaterialFailedException;
    assert ((SaveProjectMaterialFailedException) expectedException).getMessages().get(0).equals("message1");
  }
}
