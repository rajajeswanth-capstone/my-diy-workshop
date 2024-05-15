package com.doityourself.workshop.features.project.media.command;

import com.doityourself.workshop.common.command.CommandDTO;
import com.doityourself.workshop.common.constants.ContextConstants;
import com.doityourself.workshop.common.constants.EntityConstants;
import com.doityourself.workshop.database.entities.DiyProjectWebResource;
import com.doityourself.workshop.features.project.media.dao.ProjectMediaDao;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;

public class GetProjectDetailWebResourcesCommandTest {
  @Test
  public void testProcess() {
    // Initialize
    ProjectMediaDao mockProjectMediaDao = Mockito.mock(ProjectMediaDao.class);

    CommandDTO commandDTO = CommandDTO
        .builder()
        .add(ContextConstants.CONTEXT_PROJECT_ID, 1L)
        .build();

    DiyProjectWebResource diyProjectWebResource = DiyProjectWebResource.builder().id(1L).build();
    List<DiyProjectWebResource> diyProjectWebResources = new ArrayList<>();
    diyProjectWebResources.add(diyProjectWebResource);

    List<String> webResourceTypes = new ArrayList<>();
    webResourceTypes.add("type1");

    GetProjectDetailWebResourcesCommand command = new GetProjectDetailWebResourcesCommand();
    command.projectMediaDao = mockProjectMediaDao;

    // Define Mocks
    Mockito.when(mockProjectMediaDao.findProjectWebResourcesByProjectId(1L)).thenReturn(diyProjectWebResources);
    Mockito.when(mockProjectMediaDao.findDistinctProjectWebResourceTypesByProjectId(1L)).thenReturn(webResourceTypes);

    // Execute
    Exception expectedException = null;
    try {
      command.process(commandDTO);
    } catch (Exception exception) {
      expectedException = exception;
    }

    // Verify
    Mockito.verify(mockProjectMediaDao, Mockito.times(1)).findProjectWebResourcesByProjectId(1L);
    Mockito.verify(mockProjectMediaDao, Mockito.times(1)).findDistinctProjectWebResourceTypesByProjectId(1L);

    // Assertions
    assert expectedException == null;
    assert commandDTO.get(EntityConstants.ENTITY_DIY_PROJECT_WEB_RESOURCES) == diyProjectWebResources;
    assert commandDTO.get(ContextConstants.CONTEXT_WEB_RESOURCE_TYPES) == webResourceTypes;
  }
}
