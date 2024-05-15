package com.doityourself.workshop.features.project.media.validation;

import com.doityourself.workshop.database.entities.DiyProjectLocalResource;
import com.doityourself.workshop.features.project.media.exception.SaveProjectLocalResourceFailedException;
import com.doityourself.workshop.features.project.media.representation.ProjectDetailLocalResourceRepresentation;
import org.junit.jupiter.api.Test;

public class SaveProjectLocalResourceValidationsTest {
  @Test
  public void testValidateGetProjectLocalResourceRequest() {
    // Initialize
    SaveProjectLocalResourceValidations saveProjectLocalResourceValidations = new SaveProjectLocalResourceValidations();
    saveProjectLocalResourceValidations.projectSaveLocalResourceFailedErrorMessage = "message1";
    saveProjectLocalResourceValidations.projectSaveLocalResourceTitleRequiredErrorMessage = "field1message1";
    saveProjectLocalResourceValidations.projectSaveLocalResourceTitleFieldName = "field1";
    saveProjectLocalResourceValidations.projectSaveLocalResourceSequenceRequiredErrorMessage = "field2message1";
    saveProjectLocalResourceValidations.projectSaveLocalResourceSequenceFieldName = "field2";

    // Execute
    Exception expectedException = null;
    try {
      saveProjectLocalResourceValidations.validateSaveLocalResourceRequest(
          ProjectDetailLocalResourceRepresentation.builder().title("title").localResourceSequence(1L).build()
      );
    } catch (Exception exception) {
      expectedException = exception;
    }

    // Assertions
    assert expectedException == null;
  }

  @Test
  public void testValidateGetProjectLocalResourceRequestRequired() {
    // Initialize
    SaveProjectLocalResourceValidations saveProjectLocalResourceValidations = new SaveProjectLocalResourceValidations();
    saveProjectLocalResourceValidations.projectSaveLocalResourceFailedErrorMessage = "message1";
    saveProjectLocalResourceValidations.projectSaveLocalResourceTitleRequiredErrorMessage = "field1message1";
    saveProjectLocalResourceValidations.projectSaveLocalResourceTitleFieldName = "field1";
    saveProjectLocalResourceValidations.projectSaveLocalResourceSequenceRequiredErrorMessage = "field2message1";
    saveProjectLocalResourceValidations.projectSaveLocalResourceSequenceFieldName = "field2";

    // Execute
    Exception expectedException = null;
    try {
      saveProjectLocalResourceValidations.validateSaveLocalResourceRequest(
          ProjectDetailLocalResourceRepresentation.builder().localResourceSequence(1L).build()
      );
    } catch (Exception exception) {
      expectedException = exception;
    }

    // Assertions
    assert expectedException != null;
    assert ((SaveProjectLocalResourceFailedException) expectedException).getFieldMessages().get("field1").equals("field1message1");

    expectedException = null;
    try {
      saveProjectLocalResourceValidations.validateSaveLocalResourceRequest(
          ProjectDetailLocalResourceRepresentation.builder().title("title").build()
      );
    } catch (Exception exception) {
      expectedException = exception;
    }

    // Assertions
    assert expectedException != null;
    assert ((SaveProjectLocalResourceFailedException) expectedException).getFieldMessages().get("field2").equals("field2message1");
  }

  @Test
  public void testValidateDiyProjectLocalResourceEntity() {
    // Initialize
    SaveProjectLocalResourceValidations saveProjectLocalResourceValidations = new SaveProjectLocalResourceValidations();
    saveProjectLocalResourceValidations.projectSaveLocalResourceFailedErrorMessage = "message1";
    saveProjectLocalResourceValidations.projectSaveLocalResourceTitleRequiredErrorMessage = "field1message1";
    saveProjectLocalResourceValidations.projectSaveLocalResourceTitleFieldName = "field1";
    saveProjectLocalResourceValidations.projectSaveLocalResourceSequenceRequiredErrorMessage = "field2message1";
    saveProjectLocalResourceValidations.projectSaveLocalResourceSequenceFieldName = "field2";

    // Execute
    Exception expectedException = null;
    try {
      saveProjectLocalResourceValidations.validateDiyProjectLocalResourceEntity(
          DiyProjectLocalResource.builder().title("title").localResourceSequence(1L).build()
      );
    } catch (Exception exception) {
      expectedException = exception;
    }

    // Assertions
    assert expectedException == null;
  }

  @Test
  public void testValidateDiyProjectLocalResourceEntityRequired() {
    // Initialize
    SaveProjectLocalResourceValidations saveProjectLocalResourceValidations = new SaveProjectLocalResourceValidations();
    saveProjectLocalResourceValidations.projectSaveLocalResourceFailedErrorMessage = "message1";
    saveProjectLocalResourceValidations.projectSaveLocalResourceTitleRequiredErrorMessage = "field1message1";
    saveProjectLocalResourceValidations.projectSaveLocalResourceTitleFieldName = "field1";
    saveProjectLocalResourceValidations.projectSaveLocalResourceSequenceRequiredErrorMessage = "field2message1";
    saveProjectLocalResourceValidations.projectSaveLocalResourceSequenceFieldName = "field2";

    // Execute
    Exception expectedException = null;
    try {
      saveProjectLocalResourceValidations.validateDiyProjectLocalResourceEntity(null);
    } catch (Exception exception) {
      expectedException = exception;
    }

    // Assertions
    assert expectedException != null;
    assert ((SaveProjectLocalResourceFailedException) expectedException).getMessages().get(0).equals("message1");
  }
}
