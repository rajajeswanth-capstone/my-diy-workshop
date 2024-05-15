package com.doityourself.workshop.features.project.media.validation;

import com.doityourself.workshop.database.entities.DiyProjectWebResource;
import com.doityourself.workshop.features.project.media.exception.SaveProjectWebResourceFailedException;
import com.doityourself.workshop.features.project.media.representation.ProjectDetailWebResourceRepresentation;
import org.junit.jupiter.api.Test;

public class SaveProjectWebResourceValidationsTest {
  @Test
  public void testValidateSaveWebResourceRequest() {
    // Initialize
    SaveProjectWebResourceValidations saveProjectWebResourceValidations = new SaveProjectWebResourceValidations();
    saveProjectWebResourceValidations.projectSaveWebResourceFailedErrorMessage = "message1";
    saveProjectWebResourceValidations.projectSaveWebResourceTitleRequiredErrorMessage = "field1message1";
    saveProjectWebResourceValidations.projectSaveWebResourceTitleFieldName = "field1";
    saveProjectWebResourceValidations.projectSaveWebResourceSequenceRequiredErrorMessage = "field2message1";
    saveProjectWebResourceValidations.projectSaveWebResourceSequenceFieldName = "field2";
    saveProjectWebResourceValidations.projectSaveWebResourceLinkRequiredErrorMessage = "field3message1";
    saveProjectWebResourceValidations.projectSaveWebResourceLinkFieldName = "field3";

    // Execute
    Exception expectedException = null;
    try {
      saveProjectWebResourceValidations.validateSaveWebResourceRequest(
          ProjectDetailWebResourceRepresentation.builder().title("title").webResourceSequence(1L).link("link").build()
      );
    } catch (Exception exception) {
      expectedException = exception;
    }

    // Assertions
    assert expectedException == null;
  }

  @Test
  public void testValidateSaveWebResourceRequestRequired() {
    // Initialize
    SaveProjectWebResourceValidations saveProjectWebResourceValidations = new SaveProjectWebResourceValidations();
    saveProjectWebResourceValidations.projectSaveWebResourceFailedErrorMessage = "message1";
    saveProjectWebResourceValidations.projectSaveWebResourceTitleRequiredErrorMessage = "field1message1";
    saveProjectWebResourceValidations.projectSaveWebResourceTitleFieldName = "field1";
    saveProjectWebResourceValidations.projectSaveWebResourceSequenceRequiredErrorMessage = "field2message1";
    saveProjectWebResourceValidations.projectSaveWebResourceSequenceFieldName = "field2";
    saveProjectWebResourceValidations.projectSaveWebResourceLinkRequiredErrorMessage = "field3message1";
    saveProjectWebResourceValidations.projectSaveWebResourceLinkFieldName = "field3";

    // Execute
    Exception expectedException = null;
    try {
      saveProjectWebResourceValidations.validateSaveWebResourceRequest(
          ProjectDetailWebResourceRepresentation.builder().webResourceSequence(1L).link("link").build()
      );
    } catch (Exception exception) {
      expectedException = exception;
    }

    // Assertions
    assert expectedException != null;
    assert ((SaveProjectWebResourceFailedException) expectedException).getFieldMessages().get("field1").equals("field1message1");

    expectedException = null;
    try {
      saveProjectWebResourceValidations.validateSaveWebResourceRequest(
          ProjectDetailWebResourceRepresentation.builder().title("title").link("link").build()
      );
    } catch (Exception exception) {
      expectedException = exception;
    }

    // Assertions
    assert expectedException != null;
    assert ((SaveProjectWebResourceFailedException) expectedException).getFieldMessages().get("field2").equals("field2message1");

    expectedException = null;
    try {
      saveProjectWebResourceValidations.validateSaveWebResourceRequest(
          ProjectDetailWebResourceRepresentation.builder().title("title").webResourceSequence(1L).build()
      );
    } catch (Exception exception) {
      expectedException = exception;
    }

    // Assertions
    assert expectedException != null;
    assert ((SaveProjectWebResourceFailedException) expectedException).getFieldMessages().get("field3").equals("field3message1");
  }

  @Test
  public void testValidateDiyProjectWebResourceEntity() {
    // Initialize
    SaveProjectWebResourceValidations saveProjectWebResourceValidations = new SaveProjectWebResourceValidations();
    saveProjectWebResourceValidations.projectSaveWebResourceFailedErrorMessage = "message1";
    saveProjectWebResourceValidations.projectSaveWebResourceTitleRequiredErrorMessage = "field1message1";
    saveProjectWebResourceValidations.projectSaveWebResourceTitleFieldName = "field1";
    saveProjectWebResourceValidations.projectSaveWebResourceSequenceRequiredErrorMessage = "field2message1";
    saveProjectWebResourceValidations.projectSaveWebResourceSequenceFieldName = "field2";
    saveProjectWebResourceValidations.projectSaveWebResourceLinkRequiredErrorMessage = "field3message1";
    saveProjectWebResourceValidations.projectSaveWebResourceLinkFieldName = "field3";

    // Execute
    Exception expectedException = null;
    try {
      saveProjectWebResourceValidations.validateDiyProjectWebResourceEntity(
          DiyProjectWebResource.builder().title("title").webResourceSequence(1L).link("link").build()
      );
    } catch (Exception exception) {
      expectedException = exception;
    }

    // Assertions
    assert expectedException == null;
  }

  @Test
  public void testValidateDiyProjectWebResourceEntityRequired() {
    // Initialize
    SaveProjectWebResourceValidations saveProjectWebResourceValidations = new SaveProjectWebResourceValidations();
    saveProjectWebResourceValidations.projectSaveWebResourceFailedErrorMessage = "message1";
    saveProjectWebResourceValidations.projectSaveWebResourceTitleRequiredErrorMessage = "field1message1";
    saveProjectWebResourceValidations.projectSaveWebResourceTitleFieldName = "field1";
    saveProjectWebResourceValidations.projectSaveWebResourceSequenceRequiredErrorMessage = "field2message1";
    saveProjectWebResourceValidations.projectSaveWebResourceSequenceFieldName = "field2";
    saveProjectWebResourceValidations.projectSaveWebResourceLinkRequiredErrorMessage = "field3message1";
    saveProjectWebResourceValidations.projectSaveWebResourceLinkFieldName = "field3";

    // Execute
    Exception expectedException = null;
    try {
      saveProjectWebResourceValidations.validateDiyProjectWebResourceEntity(null);
    } catch (Exception exception) {
      expectedException = exception;
    }

    // Assertions
    assert expectedException != null;
    assert ((SaveProjectWebResourceFailedException) expectedException).getMessages().get(0).equals("message1");
  }
}
