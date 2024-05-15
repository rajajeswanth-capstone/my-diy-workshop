package com.doityourself.workshop.features.project.material.command;

import com.doityourself.workshop.common.command.CommandDTO;
import com.doityourself.workshop.common.constants.ContextConstants;
import com.doityourself.workshop.features.project.material.dao.ProjectMaterialDao;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

public class DeleteProjectMaterialByMaterialIdCommandTest {
  @Test
  public void testProcess() {
    // Initialize
    ProjectMaterialDao mockProjectMaterialDao = Mockito.mock(ProjectMaterialDao.class);

    CommandDTO commandDTO = CommandDTO.builder().add(ContextConstants.CONTEXT_MATERIAL_ID, 1L).build();

    DeleteProjectMaterialByMaterialIdCommand command = new DeleteProjectMaterialByMaterialIdCommand();
    command.projectMaterialDao = mockProjectMaterialDao;

    // Define Mocks
    Mockito.doNothing().when(mockProjectMaterialDao).deleteProjectMaterialByMaterialId(1L);

    // Execute
    Exception expectedException = null;
    try {
      command.process(commandDTO);
    } catch (Exception exception) {
      expectedException = exception;
    }

    // Verify
    Mockito.verify(mockProjectMaterialDao, Mockito.times(1)).deleteProjectMaterialByMaterialId(1L);

    // Assertions
    assert expectedException == null;
  }
}
