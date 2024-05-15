package com.doityourself.workshop.features.project.detail.command;

import com.doityourself.workshop.common.command.CommandDTO;
import com.doityourself.workshop.common.constants.ContextConstants;
import com.doityourself.workshop.common.constants.EntityConstants;
import com.doityourself.workshop.database.entities.DiyProject;
import com.doityourself.workshop.features.project.detail.representation.ProjectDetailRepresentation;
import com.doityourself.workshop.features.project.detail.validation.SaveProjectDescriptionValidations;
import com.doityourself.workshop.features.project.listing.dao.ProjectListingDao;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;

public class SaveProjectDescriptionCommandTest {
  @Test
  public void testProcess() {
    // Initialize
    ProjectListingDao mockProjectListingDao = Mockito.mock(ProjectListingDao.class);

    ArgumentCaptor<DiyProject> diyProjectCaptor = ArgumentCaptor.forClass(DiyProject.class);

    SaveProjectDescriptionCommand command = new SaveProjectDescriptionCommand();
    command.projectListingDao = mockProjectListingDao;

    DiyProject diyProject = new DiyProject();
    diyProject.setId(1L);

    ProjectDetailRepresentation projectDetailRepresentation = ProjectDetailRepresentation
        .builder()
        .id(1L)
        .description("description")
        .build();

    CommandDTO commandDTO = CommandDTO
        .builder()
        .add(EntityConstants.ENTITY_DIY_PROJECT, diyProject)
        .add(ContextConstants.CONTEXT_PROJECT_DETAIL, projectDetailRepresentation)
        .build();

    // Define Mocks
    Mockito.when(mockProjectListingDao.saveProject(Mockito.any())).thenReturn(diyProject);

    // Execute
    Exception expectedException = null;
    try {
      command.process(commandDTO);
    } catch (Exception exception) {
      expectedException = exception;
    }

    // Verify
    Mockito.verify(mockProjectListingDao, Mockito.times(1)).saveProject(diyProjectCaptor.capture());

    // Assertions
    assert expectedException == null;
    assert diyProjectCaptor.getValue().getDescription().equals("description");
    assert commandDTO.get(EntityConstants.ENTITY_DIY_PROJECT) == diyProject;
  }

  @Test
  public void testPostProcess() {
    // Initialize
    SaveProjectDescriptionValidations mockSaveProjectDescriptionValidations = Mockito.mock(SaveProjectDescriptionValidations.class);

    SaveProjectDescriptionCommand command = new SaveProjectDescriptionCommand();
    command.validations = mockSaveProjectDescriptionValidations;

    DiyProject diyProject = new DiyProject();
    diyProject.setId(1L);

    ProjectDetailRepresentation projectDetailRepresentation = ProjectDetailRepresentation
        .builder()
        .id(1L)
        .description("description")
        .build();

    CommandDTO commandDTO = CommandDTO
        .builder()
        .add(EntityConstants.ENTITY_DIY_PROJECT, diyProject)
        .add(ContextConstants.CONTEXT_PROJECT_DETAIL, projectDetailRepresentation)
        .build();

    // Define Mocks
    Mockito.doNothing().when(mockSaveProjectDescriptionValidations).validateDiyProjectDescription(diyProject, projectDetailRepresentation);

    // Execute
    Exception expectedException = null;
    try {
      command.postProcess(commandDTO);
    } catch (Exception exception) {
      expectedException = exception;
    }

    // Verify
    Mockito.verify(mockSaveProjectDescriptionValidations, Mockito.times(1)).validateDiyProjectDescription(diyProject, projectDetailRepresentation);

    // Assertions
    assert expectedException == null;
  }
}
