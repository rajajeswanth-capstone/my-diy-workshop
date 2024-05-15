package com.doityourself.workshop.features.project.detail.command;

import com.doityourself.workshop.common.command.CommandDTO;
import com.doityourself.workshop.common.constants.ContextConstants;
import com.doityourself.workshop.features.project.detail.dao.ProjectDetailDao;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

public class DeleteProjectInstructionByInstructionIdCommandTest {
  @Test
  public void testProcess() {
    // Initialize
    ProjectDetailDao mockProjectDetailDao = Mockito.mock(ProjectDetailDao.class);

    CommandDTO commandDTO = CommandDTO.builder().add(ContextConstants.CONTEXT_INSTRUCTION_ID, 1L).build();

    DeleteProjectInstructionByInstructionIdCommand command = new DeleteProjectInstructionByInstructionIdCommand();
    command.projectDetailDao = mockProjectDetailDao;

    // Define Mocks
    Mockito.doNothing().when(mockProjectDetailDao).deleteProjectInstructionByInstructionId(1L);

    // Execute
    Exception expectedException = null;
    try {
      command.process(commandDTO);
    } catch (Exception exception) {
      expectedException = exception;
    }

    // Verify
    Mockito.verify(mockProjectDetailDao, Mockito.times(1)).deleteProjectInstructionByInstructionId(1L);

    // Assertions
    assert expectedException == null;
  }
}
