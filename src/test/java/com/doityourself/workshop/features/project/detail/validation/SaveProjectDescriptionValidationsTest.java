package com.doityourself.workshop.features.project.detail.validation;

import com.doityourself.workshop.database.entities.DiyProject;
import com.doityourself.workshop.features.project.detail.representation.ProjectDetailRepresentation;
import com.doityourself.workshop.features.project.listing.exception.ProjectSaveFailedException;
import org.junit.jupiter.api.Test;

public class SaveProjectDescriptionValidationsTest {
  @Test
  public void testValidateDiyProjectDescription() {
    // Initialize
    SaveProjectDescriptionValidations saveProjectDescriptionValidations = new SaveProjectDescriptionValidations();
    saveProjectDescriptionValidations.projectSaveDescriptionFailedErrorMessage = "message1";

    // Execute
    Exception expectedException = null;
    try {
      saveProjectDescriptionValidations.validateDiyProjectDescription(DiyProject.builder().description("des").build(),
          ProjectDetailRepresentation.builder().description("des").build());
    } catch (Exception exception) {
      expectedException = exception;
    }

    // Assertions
    assert expectedException == null;

    try {
      saveProjectDescriptionValidations.validateDiyProjectDescription(DiyProject.builder().build(),
          ProjectDetailRepresentation.builder().build());
    } catch (Exception exception) {
      expectedException = exception;
    }

    // Assertions
    assert expectedException == null;
  }

  @Test
  public void testValidateDiyProjectDescriptionMismatch() {
    // Initialize
    SaveProjectDescriptionValidations saveProjectDescriptionValidations = new SaveProjectDescriptionValidations();
    saveProjectDescriptionValidations.projectSaveDescriptionFailedErrorMessage = "message1";

    // Execute
    Exception expectedException = null;
    try {
      saveProjectDescriptionValidations.validateDiyProjectDescription(DiyProject.builder().description("desc").build(),
          ProjectDetailRepresentation.builder().description("des").build());
    } catch (Exception exception) {
      expectedException = exception;
    }

    // Assertions
    assert expectedException instanceof ProjectSaveFailedException;
    assert ((ProjectSaveFailedException) expectedException).getMessages().get(0).equals("message1");
  }

  @Test
  public void testValidateDiyProjectDescriptionNullNonNull() {
    // Initialize
    SaveProjectDescriptionValidations saveProjectDescriptionValidations = new SaveProjectDescriptionValidations();
    saveProjectDescriptionValidations.projectSaveDescriptionFailedErrorMessage = "message1";

    // Execute 1
    Exception expectedException = null;
    try {
      saveProjectDescriptionValidations.validateDiyProjectDescription(null,
          ProjectDetailRepresentation.builder().description("des").build());
    } catch (Exception exception) {
      expectedException = exception;
    }

    // Assertions
    assert expectedException instanceof ProjectSaveFailedException;
    assert ((ProjectSaveFailedException) expectedException).getMessages().get(0).equals("message1");

    // Execute 2
    try {
      saveProjectDescriptionValidations.validateDiyProjectDescription(DiyProject.builder().description("desc").build(),
          null);
    } catch (Exception exception) {
      expectedException = exception;
    }

    // Assertions
    assert expectedException instanceof ProjectSaveFailedException;
    assert ((ProjectSaveFailedException) expectedException).getMessages().get(0).equals("message1");
  }
}
