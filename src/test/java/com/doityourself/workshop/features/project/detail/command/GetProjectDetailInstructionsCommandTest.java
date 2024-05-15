package com.doityourself.workshop.features.project.detail.command;

import com.doityourself.workshop.common.command.CommandDTO;
import com.doityourself.workshop.common.constants.ContextConstants;
import com.doityourself.workshop.common.constants.EntityConstants;
import com.doityourself.workshop.database.entities.DiyProjectInstruction;
import com.doityourself.workshop.features.project.detail.dao.ProjectDetailDao;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;

public class GetProjectDetailInstructionsCommandTest {
  @Test
  @SuppressWarnings("unchecked")
  public void testProcess() {
    // Initialize
    ProjectDetailDao mockProjectDetailDao = Mockito.mock(ProjectDetailDao.class);

    CommandDTO commandDTO = CommandDTO.builder().add(ContextConstants.CONTEXT_PROJECT_ID, 1L).build();

    GetProjectDetailInstructionsCommand command = new GetProjectDetailInstructionsCommand();
    command.projectDetailDao = mockProjectDetailDao;

    List<DiyProjectInstruction> projectInstructions = new ArrayList<>();
    projectInstructions.add(DiyProjectInstruction.builder().id(1L).build());

    // Define Mocks
    Mockito.when(mockProjectDetailDao.findProjectInstructionsByProjectId(1L)).thenReturn(projectInstructions);

    // Execute
    Exception expectedException = null;
    try {
      command.process(commandDTO);
    } catch (Exception exception) {
      expectedException = exception;
    }

    // Verify
    Mockito.verify(mockProjectDetailDao, Mockito.times(1)).findProjectInstructionsByProjectId(1L);

    // Assertions
    assert expectedException == null;
    assert ((List<DiyProjectInstruction>)commandDTO.get(EntityConstants.ENTITY_DIY_PROJECT_INSTRUCTIONS)).get(0).getId() == 1L;
  }
}
