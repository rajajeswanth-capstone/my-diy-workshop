package com.doityourself.workshop.features.project.listing.validation;

import com.doityourself.workshop.common.utility.FileUtility;
import com.doityourself.workshop.features.project.listing.exception.ProjectDeleteFailedException;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;

public class DeleteProjectValidationsTest {
  @Test
  public void testValidateDeleteRequest() {
    // Initialize
    DeleteProjectValidations deleteProjectValidations = new DeleteProjectValidations();
    deleteProjectValidations.projectDeleteIdRequiredErrorMessage = "field1message1";
    deleteProjectValidations.projectDeleteTitleFieldName = "field1";
    deleteProjectValidations.projectDeleteFailedErrorMessage = "message1";

    // Execute
    Exception expectedException = null;
    try {
      deleteProjectValidations.validateDeleteRequest(1L);
    } catch (Exception exception) {
      expectedException = exception;
    }

    // Assertions
    assert expectedException == null;
  }

  @Test
  public void testValidateDeleteRequestRequired() {
    // Initialize
    DeleteProjectValidations deleteProjectValidations = new DeleteProjectValidations();
    deleteProjectValidations.projectDeleteIdRequiredErrorMessage = "field1message1";
    deleteProjectValidations.projectDeleteTitleFieldName = "field1";
    deleteProjectValidations.projectDeleteFailedErrorMessage = "message1";

    // Execute
    Exception expectedException = null;
    try {
      deleteProjectValidations.validateDeleteRequest(null);
    } catch (Exception exception) {
      expectedException = exception;
    }

    // Assertions
    assert expectedException instanceof ProjectDeleteFailedException;
    assert ((ProjectDeleteFailedException) expectedException).getFieldMessages().get("field1").equals("field1message1");
  }

  @Test
  public void testValidateProjectFolderDelete() {
    try (MockedStatic<FileUtility> mockFileUtility = Mockito.mockStatic(FileUtility.class)) {
      // Initialize
      DeleteProjectValidations deleteProjectValidations = new DeleteProjectValidations();
      deleteProjectValidations.projectDeleteIdRequiredErrorMessage = "field1message1";
      deleteProjectValidations.projectDeleteTitleFieldName = "field1";
      deleteProjectValidations.projectDeleteFailedErrorMessage = "message1";

      // Define Mocks
      mockFileUtility.when(() -> FileUtility.isProjectFolderDeleted(1L)).thenReturn(true);

      // Execute
      Exception expectedException = null;
      try {
        deleteProjectValidations.validateProjectFolderDelete(1L);
      } catch (Exception exception) {
        expectedException = exception;
      }

      // Assertions
      assert expectedException == null;
    }
  }

  @Test
  public void testValidateProjectFolderNotDeleted() {
    try (MockedStatic<FileUtility> mockFileUtility = Mockito.mockStatic(FileUtility.class)) {
      // Initialize
      DeleteProjectValidations deleteProjectValidations = new DeleteProjectValidations();
      deleteProjectValidations.projectDeleteIdRequiredErrorMessage = "field1message1";
      deleteProjectValidations.projectDeleteTitleFieldName = "field1";
      deleteProjectValidations.projectDeleteFailedErrorMessage = "message1";

      // Define Mocks
      mockFileUtility.when(() -> FileUtility.isProjectFolderDeleted(1L)).thenReturn(false);

      // Execute
      Exception expectedException = null;
      try {
        deleteProjectValidations.validateProjectFolderDelete(1L);
      } catch (Exception exception) {
        expectedException = exception;
      }

      // Assertions
      assert expectedException instanceof ProjectDeleteFailedException;
      assert ((ProjectDeleteFailedException) expectedException).getMessages().get(0).equals("message1");
    }
  }
}
