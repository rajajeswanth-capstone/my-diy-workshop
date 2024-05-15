package com.doityourself.workshop.features.project.media.validation;

import com.doityourself.workshop.features.project.media.exception.DeleteProjectWebResourceFailedException;
import org.junit.jupiter.api.Test;

public class DeleteProjectWebResourceValidationsTest {
  @Test
  public void testValidateDeleteProjectWebResourceRequest() {
    // Initialize
    DeleteProjectWebResourceValidations deleteProjectWebResourceValidations = new DeleteProjectWebResourceValidations();
    deleteProjectWebResourceValidations.projectDeleteWebResourceIdRequiredErrorMessage = "field1message1";
    deleteProjectWebResourceValidations.projectDeleteWebResourceIdFieldName = "field1";

    // Execute
    Exception expectedException = null;
    try {
      deleteProjectWebResourceValidations.validateDeleteProjectWebResourceRequest(1L);
    } catch (Exception exception) {
      expectedException = exception;
    }

    // Assertions
    assert expectedException == null;
  }

  @Test
  public void testValidateDeleteProjectWebResourceRequestRequired() {
    // Initialize
    DeleteProjectWebResourceValidations deleteProjectWebResourceValidations = new DeleteProjectWebResourceValidations();
    deleteProjectWebResourceValidations.projectDeleteWebResourceIdRequiredErrorMessage = "field1message1";
    deleteProjectWebResourceValidations.projectDeleteWebResourceIdFieldName = "field1";

    // Execute
    Exception expectedException = null;
    try {
      deleteProjectWebResourceValidations.validateDeleteProjectWebResourceRequest(null);
    } catch (Exception exception) {
      expectedException = exception;
    }

    // Assertions
    assert expectedException != null;
    assert ((DeleteProjectWebResourceFailedException) expectedException).getFieldMessages().get("field1").equals("field1message1");
  }
}
