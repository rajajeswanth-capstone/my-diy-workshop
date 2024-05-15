package com.doityourself.workshop.features.project.listing.command;

import com.doityourself.workshop.common.command.CommandDTO;
import com.doityourself.workshop.common.constants.ContextConstants;
import com.doityourself.workshop.common.utility.FileUtility;
import com.doityourself.workshop.features.project.listing.validation.DeleteProjectValidations;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.stubbing.Answer;

public class DeleteProjectFolderCommandTest {
  @Test
  public void testProcess() {
    try (MockedStatic<FileUtility> mockFileUtility = Mockito.mockStatic(FileUtility.class)) {
      // Initialize
      CommandDTO commandDTO = CommandDTO
          .builder()
          .add(ContextConstants.CONTEXT_PROJECT_ID, 1L)
          .build();

      DeleteProjectFolderCommand command = new DeleteProjectFolderCommand();

      // Define Mocks
      mockFileUtility.when(() -> FileUtility.deleteProjectFolder(1L)).thenAnswer((Answer<Void>) invocation -> null);

      // Execute
      Exception expectedException = null;
      try {
        command.process(commandDTO);
      } catch (Exception exception) {
        expectedException = exception;
      }

      // Assertions
      assert expectedException == null;
    }
  }

  @Test
  public void testPostProcess() {
    // Initialize
    DeleteProjectValidations mockDeleteProjectValidations = Mockito.mock(DeleteProjectValidations.class);

    CommandDTO commandDTO = CommandDTO.builder().add(ContextConstants.CONTEXT_PROJECT_ID, 1L).build();

    DeleteProjectFolderCommand command = new DeleteProjectFolderCommand();
    command.validations = mockDeleteProjectValidations;

    // Define Mocks
    Mockito.doNothing().when(mockDeleteProjectValidations).validateProjectFolderDelete(1L);

    // Execute
    Exception expectedException = null;
    try {
      command.postProcess(commandDTO);
    } catch (Exception exception) {
      expectedException = exception;
    }

    // Verify
    Mockito.verify(mockDeleteProjectValidations, Mockito.times(1)).validateProjectFolderDelete(1L);

    // Assertions
    assert expectedException == null;
  }
}
