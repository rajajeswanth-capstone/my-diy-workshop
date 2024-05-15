package com.doityourself.workshop.features.project.media.command;

import com.doityourself.workshop.common.command.CommandDTO;
import com.doityourself.workshop.common.constants.ContextConstants;
import com.doityourself.workshop.common.constants.EntityConstants;
import com.doityourself.workshop.common.exception.DiyWorkshopException;
import com.doityourself.workshop.common.utility.FileUtility;
import com.doityourself.workshop.database.entities.DiyProject;
import com.doityourself.workshop.database.entities.DiyProjectLocalResource;
import com.doityourself.workshop.features.project.media.dao.ProjectMediaDao;
import com.doityourself.workshop.features.project.media.representation.ProjectDetailLocalResourceRepresentation;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.stubbing.Answer;
import org.springframework.web.multipart.MultipartFile;

public class SaveProjectLocalResourceFileCommandTest {
  @Test
  public void testProcess() {
    try (MockedStatic<FileUtility> mockFileUtility = Mockito.mockStatic(FileUtility.class)) {
      // Initialize
      ProjectMediaDao mockProjectMediaDao = Mockito.mock(ProjectMediaDao.class);
      MultipartFile mockMultipartFile = Mockito.mock(MultipartFile.class);

      ArgumentCaptor<DiyProjectLocalResource> diyProjectLocalResourceCaptor = ArgumentCaptor.forClass(DiyProjectLocalResource.class);

      DiyProjectLocalResource diyProjectLocalResource = DiyProjectLocalResource.builder().id(1L).build();
      DiyProject diyProject = DiyProject.builder().id(1L).build();
      ProjectDetailLocalResourceRepresentation projectDetailWebResourceRepresentation = ProjectDetailLocalResourceRepresentation
          .builder()
          .id(1L).title("title").localResourceSequence(1L).type("type").resourceType("resourceType").description("description")
          .build();
      CommandDTO commandDTO = CommandDTO
          .builder()
          .add(EntityConstants.ENTITY_DIY_PROJECT, diyProject)
          .add(EntityConstants.ENTITY_DIY_PROJECT_LOCAL_RESOURCE, diyProjectLocalResource)
          .add(ContextConstants.CONTEXT_PROJECT_LOCAL_RESOURCE_FILE, mockMultipartFile)
          .add(ContextConstants.CONTEXT_PROJECT_LOCAL_RESOURCE, projectDetailWebResourceRepresentation)
          .build();

      SaveProjectLocalResourceFileCommand command = new SaveProjectLocalResourceFileCommand();
      command.projectMediaDao = mockProjectMediaDao;

      // Define Mocks
      Mockito.when(mockProjectMediaDao.findProjectLocalResourceByLocalResourceId(1L)).thenReturn(diyProjectLocalResource);
      Mockito.when(mockProjectMediaDao.saveProjectLocalResource(Mockito.any())).thenReturn(diyProjectLocalResource);
      mockFileUtility.when(() -> FileUtility.buildLocalResourceFilePath( 1L, 1L, mockMultipartFile)).thenReturn("path");
      mockFileUtility.when(() -> FileUtility.saveLocalResource(mockMultipartFile, "path")).thenAnswer((Answer<Void>) invocation -> null);
      mockFileUtility.when(() -> FileUtility.buildFileLink(1L, 1L, mockMultipartFile)).thenReturn("link");
      mockFileUtility.when(() -> FileUtility.buildFileName(1L, "originalFileName")).thenReturn("fileName");
      mockFileUtility.when(() -> FileUtility.getFileBytes("path")).thenReturn("content".getBytes());
      Mockito.when(mockMultipartFile.isEmpty()).thenReturn(false);
      Mockito.when(mockMultipartFile.getOriginalFilename()).thenReturn("originalFileName");

      // Execute
      Exception expectedException = null;
      try {
        command.process(commandDTO);
      } catch (Exception exception) {
        expectedException = exception;
      }

      // Verify
      Mockito.verify(mockProjectMediaDao, Mockito.times(1)).saveProjectLocalResource(diyProjectLocalResourceCaptor.capture());

      // Assertions
      assert expectedException == null;
      assert commandDTO.get(EntityConstants.ENTITY_DIY_PROJECT_LOCAL_RESOURCE) == diyProjectLocalResource;
      assert diyProjectLocalResourceCaptor.getValue().getId() == 1L;
      assert diyProjectLocalResourceCaptor.getValue().getLink().equals("link");
      assert diyProjectLocalResourceCaptor.getValue().getOriginalFileName().equals("fileName");
      assert new String(diyProjectLocalResourceCaptor.getValue().getResourceContent()).equals("content");
    }
  }

  @Test
  public void testProcessException() {
    try (MockedStatic<FileUtility> mockFileUtility = Mockito.mockStatic(FileUtility.class)) {
      // Initialize
      ProjectMediaDao mockProjectMediaDao = Mockito.mock(ProjectMediaDao.class);
      MultipartFile mockMultipartFile = Mockito.mock(MultipartFile.class);

      ArgumentCaptor<DiyProjectLocalResource> diyProjectLocalResourceCaptor = ArgumentCaptor.forClass(DiyProjectLocalResource.class);

      DiyProjectLocalResource diyProjectLocalResource = DiyProjectLocalResource.builder().id(1L).build();
      DiyProject diyProject = DiyProject.builder().id(1L).build();
      ProjectDetailLocalResourceRepresentation projectDetailWebResourceRepresentation = ProjectDetailLocalResourceRepresentation
          .builder()
          .id(1L).title("title").localResourceSequence(1L).type("type").resourceType("resourceType").description("description")
          .build();
      CommandDTO commandDTO = CommandDTO
          .builder()
          .add(EntityConstants.ENTITY_DIY_PROJECT, diyProject)
          .add(EntityConstants.ENTITY_DIY_PROJECT_LOCAL_RESOURCE, diyProjectLocalResource)
          .add(ContextConstants.CONTEXT_PROJECT_LOCAL_RESOURCE_FILE, mockMultipartFile)
          .add(ContextConstants.CONTEXT_PROJECT_LOCAL_RESOURCE, projectDetailWebResourceRepresentation)
          .build();

      SaveProjectLocalResourceFileCommand command = new SaveProjectLocalResourceFileCommand();
      command.projectMediaDao = mockProjectMediaDao;

      // Define Mocks
      Mockito.when(mockProjectMediaDao.findProjectLocalResourceByLocalResourceId(1L)).thenReturn(diyProjectLocalResource);
      Mockito.doNothing().when(mockProjectMediaDao).deleteProjectLocalResourceByLocalResourceId(1L);
      Mockito.when(mockProjectMediaDao.saveProjectLocalResource(Mockito.any())).thenThrow(new DiyWorkshopException());
      mockFileUtility.when(() -> FileUtility.buildLocalResourceFilePath( 1L, 1L, mockMultipartFile)).thenReturn("path");
      mockFileUtility.when(() -> FileUtility.saveLocalResource(mockMultipartFile, "path")).thenAnswer((Answer<Void>) invocation -> null);
      mockFileUtility.when(() -> FileUtility.buildFileLink(1L, 1L, mockMultipartFile)).thenReturn("link");
      mockFileUtility.when(() -> FileUtility.buildFileName(1L, "originalFileName")).thenReturn("fileName");
      mockFileUtility.when(() -> FileUtility.getFileBytes("path")).thenReturn("content".getBytes());
      Mockito.when(mockMultipartFile.isEmpty()).thenReturn(false);
      Mockito.when(mockMultipartFile.getOriginalFilename()).thenReturn("originalFileName");

      // Execute
      Exception expectedException = null;
      try {
        command.process(commandDTO);
      } catch (Exception exception) {
        expectedException = exception;
      }

      // Verify
      Mockito.verify(mockProjectMediaDao, Mockito.times(1)).saveProjectLocalResource(diyProjectLocalResourceCaptor.capture());

      // Assertions
      assert expectedException != null;
      assert expectedException instanceof DiyWorkshopException;
      assert commandDTO.get(EntityConstants.ENTITY_DIY_PROJECT_LOCAL_RESOURCE) == diyProjectLocalResource;
      assert diyProjectLocalResourceCaptor.getValue().getId() == 1L;
      assert diyProjectLocalResourceCaptor.getValue().getLink().equals("link");
      assert diyProjectLocalResourceCaptor.getValue().getOriginalFileName().equals("fileName");
      assert new String(diyProjectLocalResourceCaptor.getValue().getResourceContent()).equals("content");
    }
  }

}
