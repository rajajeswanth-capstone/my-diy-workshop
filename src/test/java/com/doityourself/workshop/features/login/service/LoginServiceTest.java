package com.doityourself.workshop.features.login.service;

import com.doityourself.workshop.common.command.CommandChain;
import com.doityourself.workshop.common.command.CommandDTO;
import com.doityourself.workshop.common.command.ICommand;
import com.doityourself.workshop.features.login.command.LoginCommand;
import com.doityourself.workshop.features.login.command.LoginRequestValidationCommand;
import com.doityourself.workshop.features.login.command.LoginResponseCommand;
import com.doityourself.workshop.features.login.exceptionHandler.LoginGlobalExceptionHandler;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;

import java.util.List;

public class LoginServiceTest {
  @Test
  @SuppressWarnings("unchecked")
  public void testLogin() {
    // Initialize
    CommandChain mockCommandChain = Mockito.mock(CommandChain.class);

    ArgumentCaptor<CommandDTO> loginCommandDTOCaptor = ArgumentCaptor.forClass(CommandDTO.class);
    ArgumentCaptor<List<Class<? extends ICommand<CommandDTO>>>> loginCommandsCaptor = ArgumentCaptor.forClass(List.class);
    ArgumentCaptor<Class<LoginGlobalExceptionHandler>> loginGlobalExceptionHandlerCaptor = ArgumentCaptor.forClass(Class.class);

    CommandDTO commandDTO = CommandDTO.builder().build();

    LoginService loginService = new LoginService();
    loginService.commandChain = mockCommandChain;

    // Define Mocks
    Mockito.doNothing().when(mockCommandChain).execute(Mockito.any(), Mockito.any(), Mockito.any());

    // Execute
    Exception expectedException = null;
    CommandDTO result = null;
    try {
      result = loginService.login(commandDTO);
    } catch (Exception exception) {
      expectedException = exception;
    }

    // Verify
    Mockito.verify(mockCommandChain, Mockito.times(1)).execute(loginCommandDTOCaptor.capture(), loginCommandsCaptor.capture(), loginGlobalExceptionHandlerCaptor.capture());

    // Assertions
    assert expectedException == null;
    assert result == commandDTO;
    assert loginCommandDTOCaptor.getValue() == commandDTO;
    assert loginCommandsCaptor.getValue().get(0) == LoginRequestValidationCommand.class;
    assert loginCommandsCaptor.getValue().get(1) == LoginCommand.class;
    assert loginCommandsCaptor.getValue().get(2) == LoginResponseCommand.class;
    assert loginGlobalExceptionHandlerCaptor.getValue() == LoginGlobalExceptionHandler.class;
  }
}
