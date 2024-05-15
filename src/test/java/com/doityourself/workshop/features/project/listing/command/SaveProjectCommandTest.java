package com.doityourself.workshop.features.project.listing.command;

import com.doityourself.workshop.common.command.CommandDTO;
import com.doityourself.workshop.common.constants.ContextConstants;
import com.doityourself.workshop.common.constants.EntityConstants;
import com.doityourself.workshop.database.entities.DiyProject;
import com.doityourself.workshop.features.project.detail.dao.ProjectDetailDao;
import com.doityourself.workshop.features.project.listing.dao.ProjectListingDao;
import com.doityourself.workshop.features.project.listing.representation.ProjectListingRepresentation;
import com.doityourself.workshop.features.project.listing.validation.SaveProjectValidations;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;

public class SaveProjectCommandTest {
  @Test
  public void testProcessExistingProject() {
    // Initialize
    ProjectListingDao mockProjectListingDao = Mockito.mock(ProjectListingDao.class);
    ProjectDetailDao mockProjectDetailDao = Mockito.mock(ProjectDetailDao.class);

    ArgumentCaptor<DiyProject> diyProjectCaptor = ArgumentCaptor.forClass(DiyProject.class);

    ProjectListingRepresentation projectListingRepresentation = ProjectListingRepresentation
        .builder()
        .id(1L).title("title").shortDescription("short")
        .build();

    CommandDTO commandDTO = CommandDTO
        .builder()
        .add(ContextConstants.CONTEXT_PROJECT, projectListingRepresentation)
        .build();

    DiyProject diyProject = DiyProject.builder().id(1L).build();

    SaveProjectCommand command = new SaveProjectCommand();
    command.projectListingDao = mockProjectListingDao;
    command.projectDetailDao = mockProjectDetailDao;

    // Define Mocks
    Mockito.when(mockProjectDetailDao.findProjectById(1L)).thenReturn(diyProject);
    Mockito.when(mockProjectListingDao.saveProject(Mockito.any())).thenReturn(diyProject);

    // Execute
    Exception expectedException = null;
    try {
      command.process(commandDTO);
    } catch (Exception exception) {
      expectedException = exception;
    }

    // Verify
    Mockito.verify(mockProjectDetailDao, Mockito.times(1)).findProjectById(1L);
    Mockito.verify(mockProjectListingDao, Mockito.times(1)).saveProject(diyProjectCaptor.capture());

    // Assertions
    assert expectedException == null;
    assert commandDTO.get(EntityConstants.ENTITY_DIY_PROJECT) == diyProject;
    assert diyProjectCaptor.getValue().getId() == 1L;
    assert diyProjectCaptor.getValue().getTitle().equals("title");
    assert diyProjectCaptor.getValue().getShortDescription().equals("short");
  }

  @Test
  public void testProcessNewProject() {
    // Initialize
    ProjectListingDao mockProjectListingDao = Mockito.mock(ProjectListingDao.class);
    ProjectDetailDao mockProjectDetailDao = Mockito.mock(ProjectDetailDao.class);

    ArgumentCaptor<DiyProject> diyProjectCaptor = ArgumentCaptor.forClass(DiyProject.class);

    ProjectListingRepresentation projectListingRepresentation = ProjectListingRepresentation
        .builder()
        .title("title").shortDescription("short")
        .build();

    CommandDTO commandDTO = CommandDTO
        .builder()
        .add(ContextConstants.CONTEXT_PROJECT, projectListingRepresentation)
        .build();

    DiyProject diyProject = DiyProject.builder().id(1L).build();

    SaveProjectCommand command = new SaveProjectCommand();
    command.projectListingDao = mockProjectListingDao;
    command.projectDetailDao = mockProjectDetailDao;

    // Define Mocks
    Mockito.when(mockProjectDetailDao.findProjectById(1L)).thenReturn(diyProject);
    Mockito.when(mockProjectListingDao.saveProject(Mockito.any())).thenReturn(diyProject);

    // Execute
    Exception expectedException = null;
    try {
      command.process(commandDTO);
    } catch (Exception exception) {
      expectedException = exception;
    }

    // Verify
    Mockito.verify(mockProjectDetailDao, Mockito.times(0)).findProjectById(1L);
    Mockito.verify(mockProjectListingDao, Mockito.times(1)).saveProject(diyProjectCaptor.capture());

    // Assertions
    assert expectedException == null;
    assert commandDTO.get(EntityConstants.ENTITY_DIY_PROJECT) == diyProject;
    assert diyProjectCaptor.getValue().getTitle().equals("title");
    assert diyProjectCaptor.getValue().getShortDescription().equals("short");
  }

  @Test
  public void testPostProcess() {
    // Initialize
    SaveProjectValidations mockSaveProjectValidations = Mockito.mock(SaveProjectValidations.class);

    DiyProject diyProject = DiyProject.builder().id(1L).build();
    CommandDTO commandDTO = CommandDTO
        .builder()
        .add(EntityConstants.ENTITY_DIY_PROJECT, diyProject)
        .build();

    SaveProjectCommand command = new SaveProjectCommand();
    command.validations = mockSaveProjectValidations;

    // Define Mocks
    Mockito.doNothing().when(mockSaveProjectValidations).validateDiyProjectEntity(diyProject);

    // Execute
    Exception expectedException = null;
    try {
      command.postProcess(commandDTO);
    } catch (Exception exception) {
      expectedException = exception;
    }

    // Verify
    Mockito.verify(mockSaveProjectValidations, Mockito.times(1)).validateDiyProjectEntity(diyProject);

    // Assertions
    assert expectedException == null;
  }
}
