package com.doityourself.workshop.features.project.media.validation;

import com.doityourself.workshop.database.entities.DiyProjectWebResource;
import com.doityourself.workshop.features.project.media.exception.UpdateProjectWebResourceFailedException;
import com.doityourself.workshop.features.project.media.representation.ProjectDetailWebResourceRepresentation;
import org.junit.jupiter.api.Test;

public class UpdateProjectWebResourceValidationsTest {
  @Test
  public void testValidateSaveWebResourceRequest() {
    // Initialize
    UpdateProjectWebResourceValidations updateProjectWebResourceValidations = new UpdateProjectWebResourceValidations();
    updateProjectWebResourceValidations.projectUpdateWebResourceFailedErrorMessage = "message1";
    updateProjectWebResourceValidations.projectUpdateWebResourceTitleRequiredErrorMessage = "field1message1";
    updateProjectWebResourceValidations.projectUpdateWebResourceTitleFieldName = "field1";
    updateProjectWebResourceValidations.projectUpdateWebResourceSequenceRequiredErrorMessage = "field2message1";
    updateProjectWebResourceValidations.projectUpdateWebResourceSequenceFieldName = "field2";
    updateProjectWebResourceValidations.projectUpdateWebResourceIdRequiredErrorMessage = "field3message1";
    updateProjectWebResourceValidations.projectUpdateWebResourceIdFieldName = "field3";
    updateProjectWebResourceValidations.projectUpdateWebResourceLinkRequiredErrorMessage = "field4message1";
    updateProjectWebResourceValidations.projectUpdateWebResourceLinkFieldName = "field4";

    // Execute
    Exception expectedException = null;
    try {
      updateProjectWebResourceValidations.validateUpdateWebResourceRequest(
          ProjectDetailWebResourceRepresentation.builder().id(1L).title("title").webResourceSequence(1L).link("link").build()
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
    UpdateProjectWebResourceValidations updateProjectWebResourceValidations = new UpdateProjectWebResourceValidations();
    updateProjectWebResourceValidations.projectUpdateWebResourceFailedErrorMessage = "message1";
    updateProjectWebResourceValidations.projectUpdateWebResourceTitleRequiredErrorMessage = "field1message1";
    updateProjectWebResourceValidations.projectUpdateWebResourceTitleFieldName = "field1";
    updateProjectWebResourceValidations.projectUpdateWebResourceSequenceRequiredErrorMessage = "field2message1";
    updateProjectWebResourceValidations.projectUpdateWebResourceSequenceFieldName = "field2";
    updateProjectWebResourceValidations.projectUpdateWebResourceIdRequiredErrorMessage = "field3message1";
    updateProjectWebResourceValidations.projectUpdateWebResourceIdFieldName = "field3";
    updateProjectWebResourceValidations.projectUpdateWebResourceLinkRequiredErrorMessage = "field4message1";
    updateProjectWebResourceValidations.projectUpdateWebResourceLinkFieldName = "field4";

    // Execute
    Exception expectedException = null;
    try {
      updateProjectWebResourceValidations.validateUpdateWebResourceRequest(
          ProjectDetailWebResourceRepresentation.builder().id(1L).webResourceSequence(1L).link("link").build()
      );
    } catch (Exception exception) {
      expectedException = exception;
    }

    // Assertions
    assert expectedException != null;
    assert ((UpdateProjectWebResourceFailedException) expectedException).getFieldMessages().get("field1").equals("field1message1");

    expectedException = null;
    try {
      updateProjectWebResourceValidations.validateUpdateWebResourceRequest(
          ProjectDetailWebResourceRepresentation.builder().id(1L).title("title").link("link").build()
      );
    } catch (Exception exception) {
      expectedException = exception;
    }

    // Assertions
    assert expectedException != null;
    assert ((UpdateProjectWebResourceFailedException) expectedException).getFieldMessages().get("field2").equals("field2message1");

    expectedException = null;
    try {
      updateProjectWebResourceValidations.validateUpdateWebResourceRequest(
          ProjectDetailWebResourceRepresentation.builder().title("title").webResourceSequence(1L).link("link").build()
      );
    } catch (Exception exception) {
      expectedException = exception;
    }

    // Assertions
    assert expectedException != null;
    assert ((UpdateProjectWebResourceFailedException) expectedException).getFieldMessages().get("field3").equals("field3message1");

    expectedException = null;
    try {
      updateProjectWebResourceValidations.validateUpdateWebResourceRequest(
          ProjectDetailWebResourceRepresentation.builder().id(1L).title("title").webResourceSequence(1L).build()
      );
    } catch (Exception exception) {
      expectedException = exception;
    }

    // Assertions
    assert expectedException != null;
    assert ((UpdateProjectWebResourceFailedException) expectedException).getFieldMessages().get("field4").equals("field4message1");
  }

  @Test
  public void testValidateDiyProjectWebResourceEntity() {
    // Initialize
    UpdateProjectWebResourceValidations updateProjectWebResourceValidations = new UpdateProjectWebResourceValidations();
    updateProjectWebResourceValidations.projectUpdateWebResourceFailedErrorMessage = "message1";
    updateProjectWebResourceValidations.projectUpdateWebResourceTitleRequiredErrorMessage = "field1message1";
    updateProjectWebResourceValidations.projectUpdateWebResourceTitleFieldName = "field1";
    updateProjectWebResourceValidations.projectUpdateWebResourceSequenceRequiredErrorMessage = "field2message1";
    updateProjectWebResourceValidations.projectUpdateWebResourceSequenceFieldName = "field2";
    updateProjectWebResourceValidations.projectUpdateWebResourceIdRequiredErrorMessage = "field3message1";
    updateProjectWebResourceValidations.projectUpdateWebResourceIdFieldName = "field3";
    updateProjectWebResourceValidations.projectUpdateWebResourceLinkRequiredErrorMessage = "field4message1";
    updateProjectWebResourceValidations.projectUpdateWebResourceLinkFieldName = "field4";

    // Execute
    Exception expectedException = null;
    try {
      updateProjectWebResourceValidations.validateDiyProjectWebResourceEntity(
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
    UpdateProjectWebResourceValidations updateProjectWebResourceValidations = new UpdateProjectWebResourceValidations();
    updateProjectWebResourceValidations.projectUpdateWebResourceFailedErrorMessage = "message1";
    updateProjectWebResourceValidations.projectUpdateWebResourceTitleRequiredErrorMessage = "field1message1";
    updateProjectWebResourceValidations.projectUpdateWebResourceTitleFieldName = "field1";
    updateProjectWebResourceValidations.projectUpdateWebResourceSequenceRequiredErrorMessage = "field2message1";
    updateProjectWebResourceValidations.projectUpdateWebResourceSequenceFieldName = "field2";
    updateProjectWebResourceValidations.projectUpdateWebResourceIdRequiredErrorMessage = "field3message1";
    updateProjectWebResourceValidations.projectUpdateWebResourceIdFieldName = "field3";
    updateProjectWebResourceValidations.projectUpdateWebResourceLinkRequiredErrorMessage = "field4message1";
    updateProjectWebResourceValidations.projectUpdateWebResourceLinkFieldName = "field4";

    // Execute
    Exception expectedException = null;
    try {
      updateProjectWebResourceValidations.validateDiyProjectWebResourceEntity(null);
    } catch (Exception exception) {
      expectedException = exception;
    }

    // Assertions
    assert expectedException != null;
    assert ((UpdateProjectWebResourceFailedException) expectedException).getMessages().get(0).equals("message1");
  }
}
