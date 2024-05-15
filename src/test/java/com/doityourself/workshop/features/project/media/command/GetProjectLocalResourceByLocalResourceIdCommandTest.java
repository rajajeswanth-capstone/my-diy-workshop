package com.doityourself.workshop.features.project.media.command;

import com.doityourself.workshop.common.command.CommandDTO;
import com.doityourself.workshop.common.constants.ContextConstants;
import com.doityourself.workshop.common.constants.EntityConstants;
import com.doityourself.workshop.database.entities.DiyProjectLocalResource;
import com.doityourself.workshop.features.project.media.dao.ProjectMediaDao;
import com.doityourself.workshop.features.project.media.validation.GetProjectLocalResourceValidations;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

public class GetProjectLocalResourceByLocalResourceIdCommandTest {
  @Test
  public void testProcess() {
    // Initialize
    ProjectMediaDao mockProjectMediaDao = Mockito.mock(ProjectMediaDao.class);

    DiyProjectLocalResource diyProjectLocalResource = DiyProjectLocalResource.builder().id(1L).build();
    CommandDTO commandDTO = CommandDTO.builder().add(ContextConstants.CONTEXT_LOCAL_RESOURCE_ID, 1L).build();

    GetProjectLocalResourceByLocalResourceIdCommand command = new GetProjectLocalResourceByLocalResourceIdCommand();
    command.projectMediaDao = mockProjectMediaDao;

    // Define Mocks
    Mockito.when(mockProjectMediaDao.findProjectLocalResourceByLocalResourceId(1L)).thenReturn(diyProjectLocalResource);

    // Execute
    Exception expectedException = null;
    try {
      command.process(commandDTO);
    } catch (Exception exception) {
      expectedException = exception;
    }

    // Verify
    Mockito.verify(mockProjectMediaDao, Mockito.times(1)).findProjectLocalResourceByLocalResourceId(1L);

    // Assertions
    assert expectedException == null;
    assert commandDTO.get(EntityConstants.ENTITY_DIY_PROJECT_LOCAL_RESOURCE) == diyProjectLocalResource;
  }

  @Test
  public void testPostProcess() {
    // Initialize
    GetProjectLocalResourceValidations mockGetProjectLocalResourceValidations = Mockito.mock(GetProjectLocalResourceValidations.class);

    DiyProjectLocalResource diyProjectLocalResource = DiyProjectLocalResource.builder().id(1L).build();
    CommandDTO commandDTO = CommandDTO.builder().add(EntityConstants.ENTITY_DIY_PROJECT_LOCAL_RESOURCE, diyProjectLocalResource).build();

    GetProjectLocalResourceByLocalResourceIdCommand command = new GetProjectLocalResourceByLocalResourceIdCommand();
    command.validations = mockGetProjectLocalResourceValidations;

    // Define Mocks
    Mockito.doNothing().when(mockGetProjectLocalResourceValidations).validateDiyProjectLocalResourceEntity(diyProjectLocalResource);

    // Execute
    Exception expectedException = null;
    try {
      command.postProcess(commandDTO);
    } catch (Exception exception) {
      expectedException = exception;
    }

    // Verify
    Mockito.verify(mockGetProjectLocalResourceValidations, Mockito.times(1)).validateDiyProjectLocalResourceEntity(diyProjectLocalResource);

    // Assertions
    assert expectedException == null;
  }
}
