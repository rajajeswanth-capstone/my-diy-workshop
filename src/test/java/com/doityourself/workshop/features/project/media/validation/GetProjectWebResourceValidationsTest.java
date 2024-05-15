package com.doityourself.workshop.features.project.media.validation;

import com.doityourself.workshop.database.entities.DiyProjectWebResource;
import com.doityourself.workshop.features.project.media.exception.GetProjectWebResourceFailedException;
import org.junit.jupiter.api.Test;

public class GetProjectWebResourceValidationsTest {
  @Test
  public void testValidateGetProjectLocalResourceRequest() {
    // Initialize
    GetProjectWebResourceValidations getProjectWebResourceValidations = new GetProjectWebResourceValidations();
    getProjectWebResourceValidations.projectWebResourceFailedErrorMessage = "message1";
    getProjectWebResourceValidations.projectWebResourceIdRequiredErrorMessage = "field1message1";
    getProjectWebResourceValidations.projectWebResourceIdFieldName = "field1";

    // Execute
    Exception expectedException = null;
    try {
      getProjectWebResourceValidations.validateGetProjectWebResourceRequest(1L);
    } catch (Exception exception) {
      expectedException = exception;
    }

    // Assertions
    assert expectedException == null;
  }

  @Test
  public void testValidateGetProjectLocalResourceRequestRequired() {
    // Initialize
    GetProjectWebResourceValidations getProjectWebResourceValidations = new GetProjectWebResourceValidations();
    getProjectWebResourceValidations.projectWebResourceFailedErrorMessage = "message1";
    getProjectWebResourceValidations.projectWebResourceIdRequiredErrorMessage = "field1message1";
    getProjectWebResourceValidations.projectWebResourceIdFieldName = "field1";

    // Execute
    Exception expectedException = null;
    try {
      getProjectWebResourceValidations.validateGetProjectWebResourceRequest(null);
    } catch (Exception exception) {
      expectedException = exception;
    }

    // Assertions
    assert expectedException != null;
    assert ((GetProjectWebResourceFailedException) expectedException).getFieldMessages().get("field1").equals("field1message1");
  }

  @Test
  public void testValidateDiyProjectWebResourceEntity() {
    // Initialize
    GetProjectWebResourceValidations getProjectWebResourceValidations = new GetProjectWebResourceValidations();
    getProjectWebResourceValidations.projectWebResourceFailedErrorMessage = "message1";
    getProjectWebResourceValidations.projectWebResourceIdRequiredErrorMessage = "field1message1";
    getProjectWebResourceValidations.projectWebResourceIdFieldName = "field1";

    // Execute
    Exception expectedException = null;
    try {
      getProjectWebResourceValidations.validateDiyProjectWebResourceEntity(
          DiyProjectWebResource.builder().id(1L).build()
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
    GetProjectWebResourceValidations getProjectWebResourceValidations = new GetProjectWebResourceValidations();
    getProjectWebResourceValidations.projectWebResourceFailedErrorMessage = "message1";
    getProjectWebResourceValidations.projectWebResourceIdRequiredErrorMessage = "field1message1";
    getProjectWebResourceValidations.projectWebResourceIdFieldName = "field1";

    // Execute
    Exception expectedException = null;
    try {
      getProjectWebResourceValidations.validateDiyProjectWebResourceEntity(null);
    } catch (Exception exception) {
      expectedException = exception;
    }

    // Assertions
    assert expectedException != null;
    assert ((GetProjectWebResourceFailedException) expectedException).getMessages().get(0).equals("message1");
  }
}
