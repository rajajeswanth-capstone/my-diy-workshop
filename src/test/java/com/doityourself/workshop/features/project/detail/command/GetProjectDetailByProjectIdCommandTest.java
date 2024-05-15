package com.doityourself.workshop.features.project.detail.command;

import com.doityourself.workshop.common.command.CommandDTO;
import com.doityourself.workshop.common.constants.ContextConstants;
import com.doityourself.workshop.common.constants.EntityConstants;
import com.doityourself.workshop.database.entities.DiyProject;
import com.doityourself.workshop.features.project.detail.dao.ProjectDetailDao;
import com.doityourself.workshop.features.project.detail.validation.GetProjectDetailValidations;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

public class GetProjectDetailByProjectIdCommandTest {
  @Test
  public void testProcess() {
    // Initialize
    ProjectDetailDao mockProjectDetailDao = Mockito.mock(ProjectDetailDao.class);

    CommandDTO commandDTO = CommandDTO.builder().add(ContextConstants.CONTEXT_PROJECT_ID, 1L).build();

    GetProjectDetailByProjectIdCommand command = new GetProjectDetailByProjectIdCommand();
    command.projectDetailDao = mockProjectDetailDao;

    DiyProject diyProject = new DiyProject();
    diyProject.setId(1L);

    // Define Mocks
    Mockito.when(mockProjectDetailDao.findProjectById(1L)).thenReturn(diyProject);

    // Execute
    Exception expectedException = null;
    try {
      command.process(commandDTO);
    } catch (Exception exception) {
      expectedException = exception;
    }

    // Verify
    Mockito.verify(mockProjectDetailDao, Mockito.times(1)).findProjectById(1L);

    // Assertions
    assert expectedException == null;
    assert ((DiyProject)commandDTO.get(EntityConstants.ENTITY_DIY_PROJECT)).getId() == 1L;
  }

  @Test
  public void testPostProcess() {
    // Initialize
    GetProjectDetailValidations mockGetProjectDetailValidations = Mockito.mock(GetProjectDetailValidations.class);

    GetProjectDetailByProjectIdCommand command = new GetProjectDetailByProjectIdCommand();
    command.validations = mockGetProjectDetailValidations;

    DiyProject diyProject = new DiyProject();
    diyProject.setId(1L);

    CommandDTO commandDTO = CommandDTO.builder().add(EntityConstants.ENTITY_DIY_PROJECT, diyProject).build();

    // Define Mocks
    Mockito.doNothing().when(mockGetProjectDetailValidations).validateDiyProjectEntity(diyProject);

    // Execute
    Exception expectedException = null;
    try {
      command.postProcess(commandDTO);
    } catch (Exception exception) {
      expectedException = exception;
    }

    // Verify
    Mockito.verify(mockGetProjectDetailValidations, Mockito.times(1)).validateDiyProjectEntity(diyProject);

    // Assertions
    assert expectedException == null;
  }
}
