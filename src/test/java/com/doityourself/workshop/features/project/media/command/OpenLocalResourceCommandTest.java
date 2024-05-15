package com.doityourself.workshop.features.project.media.command;

import com.doityourself.workshop.common.command.CommandDTO;
import com.doityourself.workshop.common.constants.EntityConstants;
import com.doityourself.workshop.common.utility.FileUtility;
import com.doityourself.workshop.database.entities.DiyProject;
import com.doityourself.workshop.database.entities.DiyProjectLocalResource;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.stubbing.Answer;
import org.springframework.boot.system.ApplicationHome;

import java.awt.*;
import java.io.File;

public class OpenLocalResourceCommandTest {
  @Test
  public void testProcess() {
    try (MockedStatic<FileUtility> mockFileUtility = Mockito.mockStatic(FileUtility.class);
         MockedStatic<Desktop> mockDesktop = Mockito.mockStatic(Desktop.class)) {
      // Initialize
      ApplicationHome mockApplicationHome = Mockito.mock(ApplicationHome.class);
      File mockFile = Mockito.mock(File.class);
      Desktop mockResponseDesktop = Mockito.mock(Desktop.class);

      DiyProjectLocalResource diyProjectLocalResource = DiyProjectLocalResource.builder().id(1L).originalFileName("file").build();
      DiyProject diyProject = DiyProject.builder().id(1L).build();
      CommandDTO commandDTO = CommandDTO
          .builder()
          .add(EntityConstants.ENTITY_DIY_PROJECT_LOCAL_RESOURCE, diyProjectLocalResource)
          .add(EntityConstants.ENTITY_DIY_PROJECT, diyProject)
          .build();

      OpenLocalResourceCommand command = new OpenLocalResourceCommand();
      command.applicationHome = mockApplicationHome;

      // Define Mocks
      Mockito.when(mockApplicationHome.getDir()).thenReturn(mockFile);
      Mockito.when(mockFile.getAbsolutePath()).thenReturn("path");
      mockDesktop.when(Desktop::getDesktop).thenReturn(mockResponseDesktop);
      mockFileUtility.when(() -> FileUtility.openFile(mockResponseDesktop, 1L, "file", "path")).thenAnswer((Answer<Void>) invocation -> null);

      // Execute
      Exception expectedException = null;
      try {
        command.process(commandDTO);
      } catch (Exception exception) {
        expectedException = exception;
      }

      // Verify
      Mockito.verify(mockApplicationHome, Mockito.times(1)).getDir();
      Mockito.verify(mockFile, Mockito.times(1)).getAbsolutePath();

      // Assertions
      assert expectedException == null;
    }
  }
}
