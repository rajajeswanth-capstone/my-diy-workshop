package com.doityourself.workshop.features.project.media.validation;

import com.doityourself.workshop.database.entities.DiyProjectLocalResource;
import com.doityourself.workshop.features.project.media.exception.GetProjectLocalResourceFailedException;
import org.junit.jupiter.api.Test;

public class GetProjectLocalResourceValidationsTest {
  @Test
  public void testValidateGetProjectLocalResourceRequest() {
    // Initialize
    GetProjectLocalResourceValidations getProjectLocalResourceValidations = new GetProjectLocalResourceValidations();
    getProjectLocalResourceValidations.projectLocalResourceFailedErrorMessage = "message1";
    getProjectLocalResourceValidations.projectLocalResourceIdRequiredErrorMessage = "field1message1";
    getProjectLocalResourceValidations.projectLocalResourceIdFieldName = "field1";

    // Execute
    Exception expectedException = null;
    try {
      getProjectLocalResourceValidations.validateGetProjectLocalResourceRequest(1L);
    } catch (Exception exception) {
      expectedException = exception;
    }

    // Assertions
    assert expectedException == null;
  }

  @Test
  public void testValidateGetProjectLocalResourceRequestRequired() {
    // Initialize
    GetProjectLocalResourceValidations getProjectLocalResourceValidations = new GetProjectLocalResourceValidations();
    getProjectLocalResourceValidations.projectLocalResourceFailedErrorMessage = "message1";
    getProjectLocalResourceValidations.projectLocalResourceIdRequiredErrorMessage = "field1message1";
    getProjectLocalResourceValidations.projectLocalResourceIdFieldName = "field1";

    // Execute
    Exception expectedException = null;
    try {
      getProjectLocalResourceValidations.validateGetProjectLocalResourceRequest(null);
    } catch (Exception exception) {
      expectedException = exception;
    }

    // Assertions
    assert expectedException != null;
    assert ((GetProjectLocalResourceFailedException) expectedException).getFieldMessages().get("field1").equals("field1message1");
  }

  @Test
  public void testValidateDiyProjectLocalResourceEntity() {
    // Initialize
    GetProjectLocalResourceValidations getProjectLocalResourceValidations = new GetProjectLocalResourceValidations();
    getProjectLocalResourceValidations.projectLocalResourceFailedErrorMessage = "message1";
    getProjectLocalResourceValidations.projectLocalResourceIdRequiredErrorMessage = "field1message1";
    getProjectLocalResourceValidations.projectLocalResourceIdFieldName = "field1";

    // Execute
    Exception expectedException = null;
    try {
      getProjectLocalResourceValidations.validateDiyProjectLocalResourceEntity(
          DiyProjectLocalResource.builder().id(1L).build()
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
    GetProjectLocalResourceValidations getProjectLocalResourceValidations = new GetProjectLocalResourceValidations();
    getProjectLocalResourceValidations.projectLocalResourceFailedErrorMessage = "message1";
    getProjectLocalResourceValidations.projectLocalResourceIdRequiredErrorMessage = "field1message1";
    getProjectLocalResourceValidations.projectLocalResourceIdFieldName = "field1";

    // Execute
    Exception expectedException = null;
    try {
      getProjectLocalResourceValidations.validateDiyProjectLocalResourceEntity(null);
    } catch (Exception exception) {
      expectedException = exception;
    }

    // Assertions
    assert expectedException != null;
    assert ((GetProjectLocalResourceFailedException) expectedException).getMessages().get(0).equals("message1");
  }
}
