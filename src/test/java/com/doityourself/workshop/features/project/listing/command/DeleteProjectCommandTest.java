package com.doityourself.workshop.features.project.listing.command;

import com.doityourself.workshop.common.command.CommandDTO;
import com.doityourself.workshop.common.constants.ContextConstants;
import com.doityourself.workshop.features.project.listing.dao.ProjectListingDao;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

public class DeleteProjectCommandTest {
  @Test
  public void testProcess() {
    // Initialize
    ProjectListingDao mockProjectListingDao = Mockito.mock(ProjectListingDao.class);

    CommandDTO commandDTO = CommandDTO.builder().add(ContextConstants.CONTEXT_PROJECT_ID, 1L).build();

    DeleteProjectCommand command = new DeleteProjectCommand();
    command.projectListingDao = mockProjectListingDao;

    // Define Mocks
    Mockito.doNothing().when(mockProjectListingDao).deleteByProjectId(1L);

    // Execute
    Exception expectedException = null;
    try {
      command.process(commandDTO);
    } catch (Exception exception) {
      expectedException = exception;
    }

    // Verify
    Mockito.verify(mockProjectListingDao, Mockito.times(1)).deleteByProjectId(1L);

    // Assertions
    assert expectedException == null;
  }
}
