package com.doityourself.workshop.features.project.media.validation;

import com.doityourself.workshop.features.project.media.exception.DeleteProjectLocalResourceFailedException;
import org.junit.jupiter.api.Test;

public class DeleteProjectLocalResourceValidationsTest {
  @Test
  public void testValidateDeleteProjectLocalResourceRequest() {
    // Initialize
    DeleteProjectLocalResourceValidations deleteProjectLocalResourceValidations = new DeleteProjectLocalResourceValidations();
    deleteProjectLocalResourceValidations.projectDeleteLocalResourceIdRequiredErrorMessage = "field1message1";
    deleteProjectLocalResourceValidations.projectDeleteLocalResourceIdFieldName = "field1";

    // Execute
    Exception expectedException = null;
    try {
      deleteProjectLocalResourceValidations.validateDeleteProjectLocalResourceRequest(1L);
    } catch (Exception exception) {
      expectedException = exception;
    }

    // Assertions
    assert expectedException == null;
  }

  @Test
  public void testValidateDeleteProjectLocalResourceRequestRequired() {
    // Initialize
    DeleteProjectLocalResourceValidations deleteProjectLocalResourceValidations = new DeleteProjectLocalResourceValidations();
    deleteProjectLocalResourceValidations.projectDeleteLocalResourceIdRequiredErrorMessage = "field1message1";
    deleteProjectLocalResourceValidations.projectDeleteLocalResourceIdFieldName = "field1";

    // Execute
    Exception expectedException = null;
    try {
      deleteProjectLocalResourceValidations.validateDeleteProjectLocalResourceRequest(null);
    } catch (Exception exception) {
      expectedException = exception;
    }

    // Assertions
    assert expectedException != null;
    assert ((DeleteProjectLocalResourceFailedException) expectedException).getFieldMessages().get("field1").equals("field1message1");
  }
}
