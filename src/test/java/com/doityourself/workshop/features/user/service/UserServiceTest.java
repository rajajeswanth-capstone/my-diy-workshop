package com.doityourself.workshop.features.user.service;

import com.doityourself.workshop.common.command.CommandChain;
import com.doityourself.workshop.common.command.CommandDTO;
import com.doityourself.workshop.common.command.ICommand;
import com.doityourself.workshop.features.user.command.GetShareUsersForProjectRequestValidationCommand;
import com.doityourself.workshop.features.user.command.GetShareUsersForProjectResponseCommand;
import com.doityourself.workshop.features.user.command.GetSharedUsersCommand;
import com.doityourself.workshop.features.user.exceptionHandler.GetSharedUsersForProjectGlobalExceptionHandler;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;

import java.util.List;

public class UserServiceTest {
  @Test
  @SuppressWarnings("unchecked")
  public void testGetSharedUsersForProject() {
    // Initialize
    CommandChain mockCommandChain = Mockito.mock(CommandChain.class);

    ArgumentCaptor<CommandDTO> getProjectsCommandDTOCaptor = ArgumentCaptor.forClass(CommandDTO.class);
    ArgumentCaptor<List<Class<? extends ICommand<CommandDTO>>>> getProjectsCommandsCaptor = ArgumentCaptor.forClass(List.class);
    ArgumentCaptor<Class<GetSharedUsersForProjectGlobalExceptionHandler>> getSharedUsersForProjectGlobalExceptionHandlerCaptor = ArgumentCaptor.forClass(Class.class);

    CommandDTO commandDTO = CommandDTO.builder().build();

    UserService userService = new UserService();
    userService.commandChain = mockCommandChain;

    // Define Mocks
    Mockito.doNothing().when(mockCommandChain).execute(Mockito.any(), Mockito.any(), Mockito.any());

    // Execute
    Exception expectedException = null;
    CommandDTO result = null;
    try {
      result = userService.getSharedUsersForProject(commandDTO);
    } catch (Exception exception) {
      expectedException = exception;
    }

    // Verify
    Mockito.verify(mockCommandChain, Mockito.times(1)).execute(getProjectsCommandDTOCaptor.capture(), getProjectsCommandsCaptor.capture(), getSharedUsersForProjectGlobalExceptionHandlerCaptor.capture());

    // Assertions
    assert expectedException == null;
    assert result == commandDTO;
    assert getProjectsCommandDTOCaptor.getValue() == commandDTO;
    assert getProjectsCommandsCaptor.getValue().get(0) == GetShareUsersForProjectRequestValidationCommand.class;
    assert getProjectsCommandsCaptor.getValue().get(1) == GetSharedUsersCommand.class;
    assert getProjectsCommandsCaptor.getValue().get(2) == GetShareUsersForProjectResponseCommand.class;
    assert getSharedUsersForProjectGlobalExceptionHandlerCaptor.getValue() == GetSharedUsersForProjectGlobalExceptionHandler.class;
  }
}
