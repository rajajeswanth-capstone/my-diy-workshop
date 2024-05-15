package com.doityourself.workshop.features.project.media.command;

import com.doityourself.workshop.common.command.CommandDTO;
import com.doityourself.workshop.common.constants.ContextConstants;
import com.doityourself.workshop.common.constants.EntityConstants;
import com.doityourself.workshop.database.entities.DiyProjectLocalResource;
import com.doityourself.workshop.features.project.media.dao.ProjectMediaDao;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;

public class GetProjectDetailLocalResourcesCommandTest {
  @Test
  public void testProcess() {
    // Initialize
    ProjectMediaDao mockProjectMediaDao = Mockito.mock(ProjectMediaDao.class);

    CommandDTO commandDTO = CommandDTO
        .builder()
        .add(ContextConstants.CONTEXT_PROJECT_ID, 1L)
        .build();

    DiyProjectLocalResource diyProjectLocalResource = DiyProjectLocalResource.builder().id(1L).build();
    List<DiyProjectLocalResource> diyProjectLocalResources = new ArrayList<>();
    diyProjectLocalResources.add(diyProjectLocalResource);

    List<String> localResourceTypes = new ArrayList<>();
    localResourceTypes.add("type1");

    GetProjectDetailLocalResourcesCommand command = new GetProjectDetailLocalResourcesCommand();
    command.projectMediaDao = mockProjectMediaDao;

    // Define Mocks
    Mockito.when(mockProjectMediaDao.findProjectImagesByProjectId(1L)).thenReturn(diyProjectLocalResources);
    Mockito.when(mockProjectMediaDao.findDistinctProjectLocalResourceTypesByProjectId(1L)).thenReturn(localResourceTypes);

    // Execute
    Exception expectedException = null;
    try {
      command.process(commandDTO);
    } catch (Exception exception) {
      expectedException = exception;
    }

    // Verify
    Mockito.verify(mockProjectMediaDao, Mockito.times(1)).findProjectImagesByProjectId(1L);

    // Assertions
    assert expectedException == null;
    assert commandDTO.get(EntityConstants.ENTITY_DIY_PROJECT_LOCAL_RESOURCES) == diyProjectLocalResources;
    assert commandDTO.get(ContextConstants.CONTEXT_LOCAL_RESOURCE_TYPES) == localResourceTypes;
  }
}
