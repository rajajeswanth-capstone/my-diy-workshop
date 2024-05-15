package com.doityourself.workshop.features.project.media.command;

import com.doityourself.workshop.common.command.CommandDTO;
import com.doityourself.workshop.common.constants.ContextConstants;
import com.doityourself.workshop.features.project.media.dao.ProjectMediaDao;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

public class DeleteProjectWebResourceByWebResourceIdCommandTest {
  @Test
  public void testProcess() {
    // Initialize
    ProjectMediaDao mockProjectMediaDao = Mockito.mock(ProjectMediaDao.class);

    CommandDTO commandDTO = CommandDTO.builder().add(ContextConstants.CONTEXT_WEB_RESOURCE_ID, 1L).build();

    DeleteProjectWebResourceByWebResourceIdCommand command = new DeleteProjectWebResourceByWebResourceIdCommand();
    command.projectMediaDao = mockProjectMediaDao;

    // Define Mocks
    Mockito.doNothing().when(mockProjectMediaDao).deleteProjectWebResourceByWebResourceId(1L);

    // Execute
    Exception expectedException = null;
    try {
      command.process(commandDTO);
    } catch (Exception exception) {
      expectedException = exception;
    }

    // Verify
    Mockito.verify(mockProjectMediaDao, Mockito.times(1)).deleteProjectWebResourceByWebResourceId(1L);

    // Assertions
    assert expectedException == null;
  }
}
