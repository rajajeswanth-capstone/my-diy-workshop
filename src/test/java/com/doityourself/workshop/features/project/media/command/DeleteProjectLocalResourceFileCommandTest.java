package com.doityourself.workshop.features.project.media.command;

import com.doityourself.workshop.common.command.CommandDTO;
import com.doityourself.workshop.common.constants.EntityConstants;
import com.doityourself.workshop.common.utility.FileUtility;
import com.doityourself.workshop.database.entities.DiyProjectLocalResource;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.stubbing.Answer;

public class DeleteProjectLocalResourceFileCommandTest {
  @Test
  public void testProcess() {
    try (MockedStatic<FileUtility> mockFileUtility = Mockito.mockStatic(FileUtility.class)) {
      // Initialize
      DiyProjectLocalResource diyProjectLocalResource = DiyProjectLocalResource.builder().id(1L).link("link").build();
      CommandDTO commandDTO = CommandDTO.builder().add(EntityConstants.ENTITY_DIY_PROJECT_LOCAL_RESOURCE, diyProjectLocalResource).build();

      DeleteProjectLocalResourceFileCommand command = new DeleteProjectLocalResourceFileCommand();

      // Define Mocks
      mockFileUtility.when(() -> FileUtility.deleteLocalResourceByLink("link")).thenAnswer((Answer<Void>) invocation -> null);

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
}
