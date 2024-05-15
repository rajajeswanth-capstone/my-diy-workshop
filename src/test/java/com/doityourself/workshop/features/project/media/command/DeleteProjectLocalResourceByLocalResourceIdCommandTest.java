package com.doityourself.workshop.features.project.media.command;

import com.doityourself.workshop.common.command.CommandDTO;
import com.doityourself.workshop.common.constants.ContextConstants;
import com.doityourself.workshop.features.project.media.dao.ProjectMediaDao;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

public class DeleteProjectLocalResourceByLocalResourceIdCommandTest {
  @Test
  public void testProcess() {
    // Initialize
    ProjectMediaDao mockProjectMediaDao = Mockito.mock(ProjectMediaDao.class);

    CommandDTO commandDTO = CommandDTO.builder().add(ContextConstants.CONTEXT_LOCAL_RESOURCE_ID, 1L).build();

    DeleteProjectLocalResourceByLocalResourceIdCommand command = new DeleteProjectLocalResourceByLocalResourceIdCommand();
    command.projectMediaDao = mockProjectMediaDao;

    // Define Mocks
    Mockito.doNothing().when(mockProjectMediaDao).deleteProjectLocalResourceByLocalResourceId(1L);

    // Execute
    Exception expectedException = null;
    try {
      command.process(commandDTO);
    } catch (Exception exception) {
      expectedException = exception;
    }

    // Verify
    Mockito.verify(mockProjectMediaDao, Mockito.times(1)).deleteProjectLocalResourceByLocalResourceId(1L);

    // Assertions
    assert expectedException == null;
  }
}
