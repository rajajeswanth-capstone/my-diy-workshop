package com.doityourself.workshop.features.project.media.validation;

import com.doityourself.workshop.database.entities.DiyProjectLocalResource;
import com.doityourself.workshop.features.project.media.exception.UpdateProjectLocalResourceFailedException;
import com.doityourself.workshop.features.project.media.representation.ProjectDetailLocalResourceRepresentation;
import org.junit.jupiter.api.Test;

public class UpdateProjectLocalResourceValidationsTest {
  @Test
  public void testValidateGetProjectLocalResourceRequest() {
    // Initialize
    UpdateProjectLocalResourceValidations updateProjectLocalResourceValidations = new UpdateProjectLocalResourceValidations();
    updateProjectLocalResourceValidations.projectUpdateLocalResourceFailedErrorMessage = "message1";
    updateProjectLocalResourceValidations.projectUpdateLocalResourceTitleRequiredErrorMessage = "field1message1";
    updateProjectLocalResourceValidations.projectUpdateLocalResourceTitleFieldName = "field1";
    updateProjectLocalResourceValidations.projectUpdateLocalResourceSequenceRequiredErrorMessage = "field2message1";
    updateProjectLocalResourceValidations.projectUpdateLocalResourceSequenceFieldName = "field2";
    updateProjectLocalResourceValidations.projectUpdateLocalResourceIdRequiredErrorMessage = "field3message1";
    updateProjectLocalResourceValidations.projectUpdateLocalResourceIdFieldName = "field3";

    // Execute
    Exception expectedException = null;
    try {
      updateProjectLocalResourceValidations.validateUpdateLocalResourceRequest(
          ProjectDetailLocalResourceRepresentation.builder().title("title").localResourceSequence(1L).id(1L).build()
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
    UpdateProjectLocalResourceValidations updateProjectLocalResourceValidations = new UpdateProjectLocalResourceValidations();
    updateProjectLocalResourceValidations.projectUpdateLocalResourceFailedErrorMessage = "message1";
    updateProjectLocalResourceValidations.projectUpdateLocalResourceTitleRequiredErrorMessage = "field1message1";
    updateProjectLocalResourceValidations.projectUpdateLocalResourceTitleFieldName = "field1";
    updateProjectLocalResourceValidations.projectUpdateLocalResourceSequenceRequiredErrorMessage = "field2message1";
    updateProjectLocalResourceValidations.projectUpdateLocalResourceSequenceFieldName = "field2";
    updateProjectLocalResourceValidations.projectUpdateLocalResourceIdRequiredErrorMessage = "field3message1";
    updateProjectLocalResourceValidations.projectUpdateLocalResourceIdFieldName = "field3";

    // Execute
    Exception expectedException = null;
    try {
      updateProjectLocalResourceValidations.validateUpdateLocalResourceRequest(
          ProjectDetailLocalResourceRepresentation.builder().localResourceSequence(1L).id(1L).build()
      );
    } catch (Exception exception) {
      expectedException = exception;
    }

    // Assertions
    assert expectedException != null;
    assert ((UpdateProjectLocalResourceFailedException) expectedException).getFieldMessages().get("field1").equals("field1message1");

    expectedException = null;
    try {
      updateProjectLocalResourceValidations.validateUpdateLocalResourceRequest(
          ProjectDetailLocalResourceRepresentation.builder().title("title").id(1L).build()
      );
    } catch (Exception exception) {
      expectedException = exception;
    }

    // Assertions
    assert expectedException != null;
    assert ((UpdateProjectLocalResourceFailedException) expectedException).getFieldMessages().get("field2").equals("field2message1");

    expectedException = null;
    try {
      updateProjectLocalResourceValidations.validateUpdateLocalResourceRequest(
          ProjectDetailLocalResourceRepresentation.builder().title("title").localResourceSequence(1L).build()
      );
    } catch (Exception exception) {
      expectedException = exception;
    }

    // Assertions
    assert expectedException != null;
    assert ((UpdateProjectLocalResourceFailedException) expectedException).getFieldMessages().get("field3").equals("field3message1");
  }

  @Test
  public void testValidateDiyProjectLocalResourceEntity() {
    // Initialize
    UpdateProjectLocalResourceValidations updateProjectLocalResourceValidations = new UpdateProjectLocalResourceValidations();
    updateProjectLocalResourceValidations.projectUpdateLocalResourceFailedErrorMessage = "message1";
    updateProjectLocalResourceValidations.projectUpdateLocalResourceTitleRequiredErrorMessage = "field1message1";
    updateProjectLocalResourceValidations.projectUpdateLocalResourceTitleFieldName = "field1";
    updateProjectLocalResourceValidations.projectUpdateLocalResourceSequenceRequiredErrorMessage = "field2message1";
    updateProjectLocalResourceValidations.projectUpdateLocalResourceSequenceFieldName = "field2";
    updateProjectLocalResourceValidations.projectUpdateLocalResourceIdRequiredErrorMessage = "field3message1";
    updateProjectLocalResourceValidations.projectUpdateLocalResourceIdFieldName = "field3";

    // Execute
    Exception expectedException = null;
    try {
      updateProjectLocalResourceValidations.validateDiyProjectLocalResourceEntity(
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
    UpdateProjectLocalResourceValidations updateProjectLocalResourceValidations = new UpdateProjectLocalResourceValidations();
    updateProjectLocalResourceValidations.projectUpdateLocalResourceFailedErrorMessage = "message1";
    updateProjectLocalResourceValidations.projectUpdateLocalResourceTitleRequiredErrorMessage = "field1message1";
    updateProjectLocalResourceValidations.projectUpdateLocalResourceTitleFieldName = "field1";
    updateProjectLocalResourceValidations.projectUpdateLocalResourceSequenceRequiredErrorMessage = "field2message1";
    updateProjectLocalResourceValidations.projectUpdateLocalResourceSequenceFieldName = "field2";
    updateProjectLocalResourceValidations.projectUpdateLocalResourceIdRequiredErrorMessage = "field3message1";
    updateProjectLocalResourceValidations.projectUpdateLocalResourceIdFieldName = "field3";

    // Execute
    Exception expectedException = null;
    try {
      updateProjectLocalResourceValidations.validateDiyProjectLocalResourceEntity(null);
    } catch (Exception exception) {
      expectedException = exception;
    }

    // Assertions
    assert expectedException != null;
    assert ((UpdateProjectLocalResourceFailedException) expectedException).getMessages().get(0).equals("message1");
  }
}
