package com.doityourself.workshop.features.project.material.command;

import com.doityourself.workshop.common.command.CommandDTO;
import com.doityourself.workshop.common.constants.EntityConstants;
import com.doityourself.workshop.database.entities.DiyProject;
import com.doityourself.workshop.features.project.listing.dao.ProjectListingDao;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;

public class DeleteProjectBudgetCommandTest {
  @Test
  public void testProcess() {
    // Initialize
    ProjectListingDao mockProjectListingDao = Mockito.mock(ProjectListingDao.class);

    ArgumentCaptor<DiyProject> diyProjectCaptor = ArgumentCaptor.forClass(DiyProject.class);

    DiyProject diyProject = DiyProject.builder().id(1L).budget(100D).build();
    CommandDTO commandDTO = CommandDTO.builder().add(EntityConstants.ENTITY_DIY_PROJECT, diyProject).build();

    DeleteProjectBudgetCommand command = new DeleteProjectBudgetCommand();
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
    assert diyProjectCaptor.getValue().getBudget() == null;
    assert commandDTO.get(EntityConstants.ENTITY_DIY_PROJECT) == diyProject;
  }
}
