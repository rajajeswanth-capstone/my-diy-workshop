package com.doityourself.workshop.features.project.listing.command;

import com.doityourself.workshop.common.command.CommandDTO;
import com.doityourself.workshop.common.constants.EntityConstants;
import com.doityourself.workshop.common.utility.FileUtility;
import com.doityourself.workshop.database.entities.DiyProject;
import com.doityourself.workshop.features.project.listing.dao.ProjectListingDao;
import com.doityourself.workshop.features.project.listing.validation.SaveProjectValidations;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.stubbing.Answer;

public class CreateProjectFolderCommandTest {
  @Test
  public void testProcess() {
    try (MockedStatic<FileUtility> mockFileUtility = Mockito.mockStatic(FileUtility.class)) {
      // Initialize
      CommandDTO commandDTO = CommandDTO
          .builder()
          .add(EntityConstants.ENTITY_DIY_PROJECT, DiyProject.builder().id(1L).build())
          .build();

      CreateProjectFolderCommand command = new CreateProjectFolderCommand();

      // Define Mocks
      mockFileUtility.when(() -> FileUtility.createProjectFolders(1L)).thenAnswer((Answer<Void>) invocation -> null);

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
  public void testPostProcessDelete() {
    try (MockedStatic<FileUtility> mockFileUtility = Mockito.mockStatic(FileUtility.class)) {
      // Initialize
      ProjectListingDao mockProjectListingDao = Mockito.mock(ProjectListingDao.class);
      SaveProjectValidations mockSaveProjectValidations = Mockito.mock(SaveProjectValidations.class);

      CommandDTO commandDTO = CommandDTO
          .builder()
          .add(EntityConstants.ENTITY_DIY_PROJECT, DiyProject.builder().id(1L).build())
          .build();

      CreateProjectFolderCommand command = new CreateProjectFolderCommand();
      command.projectListingDao = mockProjectListingDao;
      command.validations = mockSaveProjectValidations;

      // Define Mocks
      mockFileUtility.when(() -> FileUtility.isProjectFoldersCreated(1L)).thenReturn(false);
      Mockito.doNothing().when(mockProjectListingDao).deleteByProjectId(1L);

      // Execute
      Exception expectedException = null;
      try {
        command.postProcess(commandDTO);
      } catch (Exception exception) {
        expectedException = exception;
      }

      // Verify
      Mockito.verify(mockProjectListingDao, Mockito.times(1)).deleteByProjectId(1L);

      // Assertions
      assert expectedException == null;
    }
  }

  @Test
  public void testPostProcessValidate() {
    try (MockedStatic<FileUtility> mockFileUtility = Mockito.mockStatic(FileUtility.class)) {
      // Initialize
      ProjectListingDao mockProjectListingDao = Mockito.mock(ProjectListingDao.class);
      SaveProjectValidations mockSaveProjectValidations = Mockito.mock(SaveProjectValidations.class);

      DiyProject diyProject = DiyProject.builder().id(1L).build();
      CommandDTO commandDTO = CommandDTO
          .builder()
          .add(EntityConstants.ENTITY_DIY_PROJECT, diyProject)
          .build();

      CreateProjectFolderCommand command = new CreateProjectFolderCommand();
      command.projectListingDao = mockProjectListingDao;
      command.validations = mockSaveProjectValidations;

      // Define Mocks
      mockFileUtility.when(() -> FileUtility.isProjectFoldersCreated(1L)).thenReturn(true);
      Mockito.doNothing().when(mockSaveProjectValidations).validateProjectFolderCreate(diyProject);

      // Execute
      Exception expectedException = null;
      try {
        command.postProcess(commandDTO);
      } catch (Exception exception) {
        expectedException = exception;
      }

      // Verify
      Mockito.verify(mockSaveProjectValidations, Mockito.times(1)).validateProjectFolderCreate(diyProject);

      // Assertions
      assert expectedException == null;
    }
  }
}
