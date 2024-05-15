package com.doityourself.workshop.features.project.listing.command;

import com.doityourself.workshop.common.command.CommandDTO;
import com.doityourself.workshop.common.constants.ContextConstants;
import com.doityourself.workshop.common.constants.EntityConstants;
import com.doityourself.workshop.common.utility.FileUtility;
import com.doityourself.workshop.database.entities.DiyProject;
import com.doityourself.workshop.features.project.listing.dao.ProjectListingDao;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.stubbing.Answer;
import org.springframework.web.multipart.MultipartFile;

public class SaveProjectImageFileCommandTest {
  @Test
  public void testProcessNonEmptyFile() {
    try (MockedStatic<FileUtility> mockFileUtility = Mockito.mockStatic(FileUtility.class)) {
      // Initialize
      ArgumentCaptor<DiyProject> diyProjectCaptor = ArgumentCaptor.forClass(DiyProject.class);

      ProjectListingDao mockProjectListingDao = Mockito.mock(ProjectListingDao.class);
      MultipartFile mockMultipartFile = Mockito.mock(MultipartFile.class);

      DiyProject diyProject = DiyProject.builder().id(1L).build();
      CommandDTO commandDTO = CommandDTO
          .builder()
          .add(EntityConstants.ENTITY_DIY_PROJECT, diyProject)
          .add(ContextConstants.CONTEXT_PROJECT_PROJECT_IMAGE_FILE, mockMultipartFile)
          .build();

      SaveProjectImageFileCommand command = new SaveProjectImageFileCommand();
      command.projectListingDao = mockProjectListingDao;

      // Define Mocks
      mockFileUtility.when(() -> FileUtility.buildProjectImageFilePath(1L, mockMultipartFile)).thenReturn("/path");
      mockFileUtility.when(() -> FileUtility.saveLocalResource(mockMultipartFile, "/path")).thenAnswer((Answer<Void>) invocation -> null);
      mockFileUtility.when(() -> FileUtility.buildProjectImageFileLink(1L, mockMultipartFile)).thenReturn("link");
      mockFileUtility.when(() -> FileUtility.getFileBytes("/path")).thenReturn("content".getBytes());
      Mockito.when(mockMultipartFile.isEmpty()).thenReturn(false);
      Mockito.when(mockProjectListingDao.saveProject(Mockito.any())).thenReturn(diyProject);

      // Execute
      Exception expectedException = null;
      try {
        command.process(commandDTO);
      } catch (Exception exception) {
        expectedException = exception;
      }

      // Verify
      Mockito.verify(mockMultipartFile, Mockito.times(1)).isEmpty();
      Mockito.verify(mockProjectListingDao, Mockito.times(1)).saveProject(diyProjectCaptor.capture());

      // Assertions
      assert expectedException == null;
      assert diyProjectCaptor.getValue().getId() == 1L;
      assert diyProjectCaptor.getValue().getImageLink().equals("link");
      assert new String(diyProjectCaptor.getValue().getImageContent()).equals("content");
    }
  }

  @Test
  public void testProcessEmptyFile() {
    try (MockedStatic<FileUtility> mockFileUtility = Mockito.mockStatic(FileUtility.class)) {
      // Initialize
      ArgumentCaptor<DiyProject> diyProjectCaptor = ArgumentCaptor.forClass(DiyProject.class);

      ProjectListingDao mockProjectListingDao = Mockito.mock(ProjectListingDao.class);
      MultipartFile mockMultipartFile = Mockito.mock(MultipartFile.class);

      DiyProject diyProject = DiyProject.builder().id(1L).build();
      CommandDTO commandDTO = CommandDTO
          .builder()
          .add(EntityConstants.ENTITY_DIY_PROJECT, diyProject)
          .add(ContextConstants.CONTEXT_PROJECT_PROJECT_IMAGE_FILE, mockMultipartFile)
          .build();

      SaveProjectImageFileCommand command = new SaveProjectImageFileCommand();
      command.projectListingDao = mockProjectListingDao;

      // Define Mocks
      mockFileUtility.when(() -> FileUtility.buildProjectImageFilePath(1L, mockMultipartFile)).thenReturn("/path");
      mockFileUtility.when(() -> FileUtility.saveLocalResource(mockMultipartFile, "/path")).thenAnswer((Answer<Void>) invocation -> null);
      mockFileUtility.when(() -> FileUtility.buildDefaultProjectImageFileLink()).thenReturn("link");
      mockFileUtility.when(() -> FileUtility.getFileBytes("/path")).thenReturn("content".getBytes());
      Mockito.when(mockMultipartFile.isEmpty()).thenReturn(true);
      Mockito.when(mockProjectListingDao.saveProject(Mockito.any())).thenReturn(diyProject);

      // Execute
      Exception expectedException = null;
      try {
        command.process(commandDTO);
      } catch (Exception exception) {
        expectedException = exception;
      }

      // Verify
      Mockito.verify(mockMultipartFile, Mockito.times(1)).isEmpty();
      Mockito.verify(mockProjectListingDao, Mockito.times(1)).saveProject(diyProjectCaptor.capture());

      // Assertions
      assert expectedException == null;
      assert diyProjectCaptor.getValue().getId() == 1L;
      assert diyProjectCaptor.getValue().getImageLink().equals("link");
    }
  }
}
