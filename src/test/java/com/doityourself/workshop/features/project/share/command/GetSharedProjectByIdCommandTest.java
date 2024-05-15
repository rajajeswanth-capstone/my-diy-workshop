package com.doityourself.workshop.features.project.share.command;

import com.doityourself.workshop.common.command.CommandDTO;
import com.doityourself.workshop.common.constants.ContextConstants;
import com.doityourself.workshop.common.constants.EntityConstants;
import com.doityourself.workshop.database.entities.DiyProject;
import com.doityourself.workshop.features.project.detail.dao.ProjectDetailDao;
import com.doityourself.workshop.features.project.share.representation.ShareProjectRepresentation;
import com.doityourself.workshop.features.project.share.validation.ShareProjectValidations;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

public class GetSharedProjectByIdCommandTest {
  @Test
  public void testProcess() {
    // Initialize
    ProjectDetailDao mockProjectDetailDao = Mockito.mock(ProjectDetailDao.class);

    ShareProjectRepresentation shareProjectRepresentation = ShareProjectRepresentation.builder().projectId(1L).build();
    DiyProject responseDiyProject = DiyProject.builder().id(1L).build();
    CommandDTO commandDTO = CommandDTO.builder().add(ContextConstants.CONTEXT_SHARE_PROJECT, shareProjectRepresentation).build();

    GetSharedProjectByIdCommand command = new GetSharedProjectByIdCommand();
    command.projectDetailDao = mockProjectDetailDao;

    // Define Mocks
    Mockito.when(mockProjectDetailDao.findProjectById(1L)).thenReturn(responseDiyProject);

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
    assert commandDTO.get(EntityConstants.ENTITY_DIY_PROJECT) == responseDiyProject;
  }

  @Test
  public void testPostProcess() {
    // Initialize
    ShareProjectValidations mockShareProjectValidations = Mockito.mock(ShareProjectValidations.class);

    DiyProject diyProject = DiyProject.builder().id(1L).build();
    CommandDTO commandDTO = CommandDTO.builder().add(EntityConstants.ENTITY_DIY_PROJECT, diyProject).build();

    GetSharedProjectByIdCommand command = new GetSharedProjectByIdCommand();
    command.validations = mockShareProjectValidations;

    // Define Mocks
    Mockito.doNothing().when(mockShareProjectValidations).validateDiyProjectEntity(diyProject);

    // Execute
    Exception expectedException = null;
    try {
      command.postProcess(commandDTO);
    } catch (Exception exception) {
      expectedException = exception;
    }

    // Verify
    Mockito.verify(mockShareProjectValidations, Mockito.times(1)).validateDiyProjectEntity(diyProject);

    // Assertions
    assert expectedException == null;
  }
}
