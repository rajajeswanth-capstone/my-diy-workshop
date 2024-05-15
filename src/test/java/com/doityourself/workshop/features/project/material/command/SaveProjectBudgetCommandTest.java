package com.doityourself.workshop.features.project.material.command;

import com.doityourself.workshop.common.command.CommandDTO;
import com.doityourself.workshop.common.constants.ContextConstants;
import com.doityourself.workshop.common.constants.EntityConstants;
import com.doityourself.workshop.database.entities.DiyProject;
import com.doityourself.workshop.features.project.listing.dao.ProjectListingDao;
import com.doityourself.workshop.features.project.material.representation.ProjectDetailRepresentation;
import com.doityourself.workshop.features.project.material.validation.SaveProjectBudgetValidations;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;

public class SaveProjectBudgetCommandTest {
  @Test
  public void testProcess() {
    // Initialize
    ProjectListingDao mockProjectListingDao = Mockito.mock(ProjectListingDao.class);

    ArgumentCaptor<DiyProject> diyProjectCaptor = ArgumentCaptor.forClass(DiyProject.class);

    DiyProject diyProject = DiyProject.builder().id(1L).build();
    ProjectDetailRepresentation projectDetailRepresentation = ProjectDetailRepresentation.builder().id(1L).budget(10D).build();

    CommandDTO commandDTO = CommandDTO
        .builder()
        .add(EntityConstants.ENTITY_DIY_PROJECT, diyProject)
        .add(ContextConstants.CONTEXT_PROJECT_DETAIL, projectDetailRepresentation)
        .build();

    SaveProjectBudgetCommand command = new SaveProjectBudgetCommand();
    command.projectListingDao = mockProjectListingDao;

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
    assert diyProjectCaptor.getValue().getBudget() == 10D;
    assert commandDTO.get(EntityConstants.ENTITY_DIY_PROJECT) == diyProject;
  }

  @Test
  public void testPostProcess() {
    // Initialize
    SaveProjectBudgetValidations mockSaveProjectBudgetValidations = Mockito.mock(SaveProjectBudgetValidations.class);

    DiyProject diyProject = DiyProject.builder().id(1L).build();
    ProjectDetailRepresentation projectDetailRepresentation = ProjectDetailRepresentation.builder().id(1L).budget(10D).build();

    CommandDTO commandDTO = CommandDTO
        .builder()
        .add(EntityConstants.ENTITY_DIY_PROJECT, diyProject)
        .add(ContextConstants.CONTEXT_PROJECT_DETAIL, projectDetailRepresentation)
        .build();

    SaveProjectBudgetCommand command = new SaveProjectBudgetCommand();
    command.validations = mockSaveProjectBudgetValidations;

    // Define Mocks
    Mockito.doNothing().when(mockSaveProjectBudgetValidations).validateDiyProjectBudget(diyProject, projectDetailRepresentation);

    // Execute
    Exception expectedException = null;
    try {
      command.postProcess(commandDTO);
    } catch (Exception exception) {
      expectedException = exception;
    }

    // Verify
    Mockito.verify(mockSaveProjectBudgetValidations, Mockito.times(1)).validateDiyProjectBudget(diyProject, projectDetailRepresentation);

    // Assertions
    assert expectedException == null;
  }
}
