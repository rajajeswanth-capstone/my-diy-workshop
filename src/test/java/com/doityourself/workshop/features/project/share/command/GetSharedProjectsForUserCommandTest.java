package com.doityourself.workshop.features.project.share.command;

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

public class GetSharedProjectsForUserCommandTest {
  @Test
  public void testProcess() {
    // Initialize
    DiyUserProjectRepository mockDiyUserProjectRepository = Mockito.mock(DiyUserProjectRepository.class);

    LoggedInUserRepresentation loggedInUserRepresentation = LoggedInUserRepresentation.builder().userName("username").build();

    DiyProject responseDiyProject = DiyProject.builder().id(1L).build();
    List<DiyProject> responseDiyProjects = new ArrayList<>();
    responseDiyProjects.add(responseDiyProject);

    CommandDTO commandDTO = CommandDTO.builder().add(ContextConstants.CONTEXT_LOGGED_IN_USER, loggedInUserRepresentation).build();

    GetSharedProjectsForUserCommand command = new GetSharedProjectsForUserCommand();
    command.diyUserProjectRepository = mockDiyUserProjectRepository;

    // Define Mocks
    Mockito.when(mockDiyUserProjectRepository.findSharedProjectsByUserName("username")).thenReturn(responseDiyProjects);

    // Execute
    Exception expectedException = null;
    try {
      command.process(commandDTO);
    } catch (Exception exception) {
      expectedException = exception;
    }

    // Verify
    Mockito.verify(mockDiyUserProjectRepository, Mockito.times(1)).findSharedProjectsByUserName("username");

    // Assertions
    assert expectedException == null;
    assert commandDTO.get(EntityConstants.ENTITY_DIY_SHARED_PROJECTS) == responseDiyProjects;
  }
}
