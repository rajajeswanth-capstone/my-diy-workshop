package com.doityourself.workshop.features.project.material.validation;

import com.doityourself.workshop.database.entities.DiyProject;
import com.doityourself.workshop.features.project.listing.exception.ProjectSaveFailedException;
import com.doityourself.workshop.features.project.material.representation.ProjectDetailRepresentation;
import org.junit.jupiter.api.Test;

public class SaveProjectBudgetValidationsTest {
  @Test
  public void testValidateGetProjectMaterialRequestNull() {
    // Initialize
    SaveProjectBudgetValidations saveProjectBudgetValidations = new SaveProjectBudgetValidations();
    saveProjectBudgetValidations.projectSaveBudgetFailedErrorMessage = "message1";

    // Execute
    Exception expectedException = null;
    try {
      saveProjectBudgetValidations.validateDiyProjectBudget(DiyProject.builder().build(), ProjectDetailRepresentation.builder().build());
    } catch (Exception exception) {
      expectedException = exception;
    }

    // Assertions
    assert expectedException == null;
  }

  @Test
  public void testValidateGetProjectMaterialRequestSame() {
    // Initialize
    SaveProjectBudgetValidations saveProjectBudgetValidations = new SaveProjectBudgetValidations();
    saveProjectBudgetValidations.projectSaveBudgetFailedErrorMessage = "message1";

    // Execute
    Exception expectedException = null;
    try {
      saveProjectBudgetValidations.validateDiyProjectBudget(DiyProject.builder().budget(10D).build(), ProjectDetailRepresentation.builder().budget(10D).build());
    } catch (Exception exception) {
      expectedException = exception;
    }

    // Assertions
    assert expectedException == null;
  }

  @Test
  public void testValidateGetProjectMaterialRequestRequired() {
    // Initialize
    SaveProjectBudgetValidations saveProjectBudgetValidations = new SaveProjectBudgetValidations();
    saveProjectBudgetValidations.projectSaveBudgetFailedErrorMessage = "message1";

    // Execute
    Exception expectedException = null;
    try {
      saveProjectBudgetValidations.validateDiyProjectBudget(null, null);
    } catch (Exception exception) {
      expectedException = exception;
    }

    // Assertions
    assert expectedException instanceof ProjectSaveFailedException;
    assert ((ProjectSaveFailedException) expectedException).getMessages().get(0).equals("message1");
  }
}
