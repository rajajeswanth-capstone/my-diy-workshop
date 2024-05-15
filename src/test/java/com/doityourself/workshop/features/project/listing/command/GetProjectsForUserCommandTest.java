package com.doityourself.workshop.features.project.listing.command;

import com.doityourself.workshop.common.command.CommandDTO;
import com.doityourself.workshop.common.constants.ContextConstants;
import com.doityourself.workshop.common.constants.EntityConstants;
import com.doityourself.workshop.database.entities.DiyProject;
import com.doityourself.workshop.database.repositories.DiyUserProjectRepository;
import com.doityourself.workshop.features.login.representation.LoggedInUserRepresentation;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;

public class GetProjectsForUserCommandTest {
  @Test
  @SuppressWarnings("unchecked")
  public void testProcess() {
    // Initialize
    DiyUserProjectRepository mockDiyUserProjectRepository = Mockito.mock(DiyUserProjectRepository.class);

    CommandDTO commandDTO = CommandDTO
        .builder()
        .add(ContextConstants.CONTEXT_LOGGED_IN_USER, LoggedInUserRepresentation.builder().userName("username").build())
        .build();

    List<DiyProject> responseProjects = new ArrayList<>();
    responseProjects.add(DiyProject.builder().id(1L).build());

    GetProjectsForUserCommand command = new GetProjectsForUserCommand();
    command.diyUserProjectRepository = mockDiyUserProjectRepository;

    // Define Mocks
    Mockito.when(mockDiyUserProjectRepository.findProjectsByUserName("username")).thenReturn(responseProjects);

    // Execute
    Exception expectedException = null;
    try {
      command.process(commandDTO);
    } catch (Exception exception) {
      expectedException = exception;
    }

    // Verify
    Mockito.verify(mockDiyUserProjectRepository, Mockito.times(1)).findProjectsByUserName("username");

    // Assertions
    assert expectedException == null;
    assert ((List<DiyProject>) commandDTO.get(EntityConstants.ENTITY_DIY_PROJECTS)).get(0).getId() == 1L;
  }
}
