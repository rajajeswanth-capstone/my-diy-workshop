package com.doityourself.workshop.features.project.detail.validation;

import com.doityourself.workshop.database.entities.DiyProject;
import com.doityourself.workshop.features.project.detail.exception.GetProjectDetailFailedException;
import org.junit.jupiter.api.Test;

public class GetProjectDetailValidationsTest {
  @Test
  public void testValidateLoginRequest() {
    // Initialize
    GetProjectDetailValidations getProjectDetailValidations = new GetProjectDetailValidations();
    getProjectDetailValidations.projectDetailFailedErrorMessage = "field1message1";
    getProjectDetailValidations.projectDetailIdRequiredErrorMessage = "message1";
    getProjectDetailValidations.projectDetailTitleFieldName = "field1";

    // Execute
    Exception expectedException = null;
    try {
      getProjectDetailValidations.validateGetProjectDetailRequest(1L);
    } catch (Exception exception) {
      expectedException = exception;
    }

    // Assertions
    assert expectedException == null;
  }

  @Test
  public void testValidateLoginRequestIdRequired() {
    // Initialize
    GetProjectDetailValidations getProjectDetailValidations = new GetProjectDetailValidations();
    getProjectDetailValidations.projectDetailFailedErrorMessage = "field1message1";
    getProjectDetailValidations.projectDetailIdRequiredErrorMessage = "message1";
    getProjectDetailValidations.projectDetailTitleFieldName = "field1";

    // Execute
    Exception expectedException = null;
    try {
      getProjectDetailValidations.validateGetProjectDetailRequest(null);
    } catch (Exception exception) {
      expectedException = exception;
    }

    // Assertions
    assert expectedException instanceof GetProjectDetailFailedException;
    assert ((GetProjectDetailFailedException) expectedException).getFieldMessages().get("field1").equals("message1");
  }

  @Test
  public void testValidateDiyProjectEntity() {
    // Initialize
    GetProjectDetailValidations getProjectDetailValidations = new GetProjectDetailValidations();
    getProjectDetailValidations.projectDetailFailedErrorMessage = "field1message1";
    getProjectDetailValidations.projectDetailIdRequiredErrorMessage = "message1";
    getProjectDetailValidations.projectDetailTitleFieldName = "field1";

    // Execute
    Exception expectedException = null;
    try {
      getProjectDetailValidations.validateDiyProjectEntity(DiyProject.builder().build());
    } catch (Exception exception) {
      expectedException = exception;
    }

    // Assertions
    assert expectedException == null;
  }

  @Test
  public void testValidateDiyProjectEntityRequired() {
    // Initialize
    GetProjectDetailValidations getProjectDetailValidations = new GetProjectDetailValidations();
    getProjectDetailValidations.projectDetailFailedErrorMessage = "field1message1";
    getProjectDetailValidations.projectDetailIdRequiredErrorMessage = "message1";
    getProjectDetailValidations.projectDetailTitleFieldName = "field1";

    // Execute
    Exception expectedException = null;
    try {
      getProjectDetailValidations.validateDiyProjectEntity(null);
    } catch (Exception exception) {
      expectedException = exception;
    }

    // Assertions
    assert expectedException instanceof GetProjectDetailFailedException;
    assert ((GetProjectDetailFailedException) expectedException).getMessages().get(0).equals("field1message1");
  }
}
