package com.doityourself.workshop.features.project.listing.service;

import com.doityourself.workshop.common.command.CommandChain;
import com.doityourself.workshop.common.command.CommandDTO;
import com.doityourself.workshop.common.command.ICommand;
import com.doityourself.workshop.features.project.listing.command.*;
import com.doityourself.workshop.features.project.listing.exceptionHandler.DeleteProjectGlobalExceptionHandler;
import com.doityourself.workshop.features.project.listing.exceptionHandler.GetProjectsForUserGlobalExceptionHandler;
import com.doityourself.workshop.features.project.listing.exceptionHandler.SaveProjectGlobalExceptionHandler;
import com.doityourself.workshop.features.project.share.command.GetSharedProjectsForUserCommand;
import com.doityourself.workshop.features.project.share.command.GetSharedProjectsForUserResponseCommand;
import com.doityourself.workshop.features.user.command.GetSharedUsersCommand;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;

import java.util.List;

public class ProjectListingServiceTest {
  @Test
  @SuppressWarnings("unchecked")
  public void testGetProjects() {
    // Initialize
    CommandChain mockCommandChain = Mockito.mock(CommandChain.class);

    ArgumentCaptor<CommandDTO> getProjectsCommandDTOCaptor = ArgumentCaptor.forClass(CommandDTO.class);
    ArgumentCaptor<List<Class<? extends ICommand<CommandDTO>>>> getProjectsCommandsCaptor = ArgumentCaptor.forClass(List.class);
    ArgumentCaptor<Class<GetProjectsForUserGlobalExceptionHandler>> getProjectsGlobalExceptionHandlerCaptor = ArgumentCaptor.forClass(Class.class);

    CommandDTO commandDTO = CommandDTO.builder().build();

    ProjectListingService projectListingService = new ProjectListingService();
    projectListingService.commandChain = mockCommandChain;

    // Define Mocks
    Mockito.doNothing().when(mockCommandChain).execute(Mockito.any(), Mockito.any(), Mockito.any());

    // Execute
    Exception expectedException = null;
    CommandDTO result = null;
    try {
      result = projectListingService.getProjects(commandDTO);
    } catch (Exception exception) {
      expectedException = exception;
    }

    // Verify
    Mockito.verify(mockCommandChain, Mockito.times(1)).execute(getProjectsCommandDTOCaptor.capture(), getProjectsCommandsCaptor.capture(), getProjectsGlobalExceptionHandlerCaptor.capture());

    // Assertions
    assert expectedException == null;
    assert result == commandDTO;
    assert getProjectsCommandDTOCaptor.getValue() == commandDTO;
    assert getProjectsCommandsCaptor.getValue().get(0) == GetProjectsForUserCommand.class;
    assert getProjectsCommandsCaptor.getValue().get(1) == GetSharedProjectsForUserCommand.class;
    assert getProjectsCommandsCaptor.getValue().get(2) == GetSharedUsersCommand.class;
    assert getProjectsCommandsCaptor.getValue().get(3) == GetProjectsForUserResponseCommand.class;
    assert getProjectsCommandsCaptor.getValue().get(4) == GetSharedUsersResponseCommand.class;
    assert getProjectsCommandsCaptor.getValue().get(5) == GetSharedProjectsForUserResponseCommand.class;
    assert getProjectsGlobalExceptionHandlerCaptor.getValue() == GetProjectsForUserGlobalExceptionHandler.class;
  }

  @Test
  @SuppressWarnings("unchecked")
  public void testSaveProject() {
    // Initialize
    CommandChain mockCommandChain = Mockito.mock(CommandChain.class);

    ArgumentCaptor<CommandDTO> saveProjectCommandDTOCaptor = ArgumentCaptor.forClass(CommandDTO.class);
    ArgumentCaptor<List<Class<? extends ICommand<CommandDTO>>>> saveProjectCommandsCaptor = ArgumentCaptor.forClass(List.class);
    ArgumentCaptor<Class<SaveProjectGlobalExceptionHandler>> saveProjectGlobalExceptionHandlerCaptor = ArgumentCaptor.forClass(Class.class);

    CommandDTO commandDTO = CommandDTO.builder().build();

    ProjectListingService projectListingService = new ProjectListingService();
    projectListingService.commandChain = mockCommandChain;

    // Define Mocks
    Mockito.doNothing().when(mockCommandChain).execute(Mockito.any(), Mockito.any(), Mockito.any());

    // Execute
    Exception expectedException = null;
    CommandDTO result = null;
    try {
      result = projectListingService.saveProject(commandDTO);
    } catch (Exception exception) {
      expectedException = exception;
    }

    // Verify
    Mockito.verify(mockCommandChain, Mockito.times(1)).execute(saveProjectCommandDTOCaptor.capture(), saveProjectCommandsCaptor.capture(), saveProjectGlobalExceptionHandlerCaptor.capture());

    // Assertions
    assert expectedException == null;
    assert result == commandDTO;
    assert saveProjectCommandDTOCaptor.getValue() == commandDTO;
    assert saveProjectCommandsCaptor.getValue().get(0) == SaveProjectRequestValidationCommand.class;
    assert saveProjectCommandsCaptor.getValue().get(1) == SaveProjectCommand.class;
    assert saveProjectCommandsCaptor.getValue().get(2) == CreateProjectFolderCommand.class;
    assert saveProjectCommandsCaptor.getValue().get(3) == SaveProjectImageFileCommand.class;
    assert saveProjectCommandsCaptor.getValue().get(4) == SaveUserProjectAssociationCommand.class;
    assert saveProjectGlobalExceptionHandlerCaptor.getValue() == SaveProjectGlobalExceptionHandler.class;
  }

  @Test
  @SuppressWarnings("unchecked")
  public void testDeleteProject() {
    // Initialize
    CommandChain mockCommandChain = Mockito.mock(CommandChain.class);

    ArgumentCaptor<CommandDTO> deleteProjectCommandDTOCaptor = ArgumentCaptor.forClass(CommandDTO.class);
    ArgumentCaptor<List<Class<? extends ICommand<CommandDTO>>>> deleteProjectCommandsCaptor = ArgumentCaptor.forClass(List.class);
    ArgumentCaptor<Class<DeleteProjectGlobalExceptionHandler>> deleteProjectGlobalExceptionHandlerCaptor = ArgumentCaptor.forClass(Class.class);

    CommandDTO commandDTO = CommandDTO.builder().build();

    ProjectListingService projectListingService = new ProjectListingService();
    projectListingService.commandChain = mockCommandChain;

    // Define Mocks
    Mockito.doNothing().when(mockCommandChain).execute(Mockito.any(), Mockito.any(), Mockito.any());

    // Execute
    Exception expectedException = null;
    CommandDTO result = null;
    try {
      result = projectListingService.deleteProject(commandDTO);
    } catch (Exception exception) {
      expectedException = exception;
    }

    // Verify
    Mockito.verify(mockCommandChain, Mockito.times(1)).execute(deleteProjectCommandDTOCaptor.capture(), deleteProjectCommandsCaptor.capture(), deleteProjectGlobalExceptionHandlerCaptor.capture());

    // Assertions
    assert expectedException == null;
    assert result == commandDTO;
    assert deleteProjectCommandDTOCaptor.getValue() == commandDTO;
    assert deleteProjectCommandsCaptor.getValue().get(0) == DeleteProjectRequestValidationCommand.class;
    assert deleteProjectCommandsCaptor.getValue().get(1) == DeleteProjectFolderCommand.class;
    assert deleteProjectCommandsCaptor.getValue().get(2) == DeleteProjectCommand.class;
    assert deleteProjectGlobalExceptionHandlerCaptor.getValue() == DeleteProjectGlobalExceptionHandler.class;
  }
}
