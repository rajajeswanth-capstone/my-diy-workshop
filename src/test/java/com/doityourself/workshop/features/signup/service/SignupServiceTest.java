package com.doityourself.workshop.features.signup.service;

import com.doityourself.workshop.common.command.CommandChain;
import com.doityourself.workshop.common.command.CommandDTO;
import com.doityourself.workshop.common.command.ICommand;
import com.doityourself.workshop.features.signup.command.SignupCommand;
import com.doityourself.workshop.features.signup.command.SignupRequestValidationCommand;
import com.doityourself.workshop.features.signup.command.SignupResponseCommand;
import com.doityourself.workshop.features.signup.exceptionHandler.SignupGlobalExceptionHandler;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;

import java.util.List;

public class SignupServiceTest {
  @Test
  @SuppressWarnings("unchecked")
  public void testSignup() {
    // Initialize
    CommandChain mockCommandChain = Mockito.mock(CommandChain.class);

    ArgumentCaptor<CommandDTO> getProjectsCommandDTOCaptor = ArgumentCaptor.forClass(CommandDTO.class);
    ArgumentCaptor<List<Class<? extends ICommand<CommandDTO>>>> getProjectsCommandsCaptor = ArgumentCaptor.forClass(List.class);
    ArgumentCaptor<Class<SignupGlobalExceptionHandler>> signupGlobalExceptionHandlerCaptor = ArgumentCaptor.forClass(Class.class);

    CommandDTO commandDTO = CommandDTO.builder().build();

    SignupService signupService = new SignupService();
    signupService.commandChain = mockCommandChain;

    // Define Mocks
    Mockito.doNothing().when(mockCommandChain).execute(Mockito.any(), Mockito.any(), Mockito.any());

    // Execute
    Exception expectedException = null;
    CommandDTO result = null;
    try {
      result = signupService.signup(commandDTO);
    } catch (Exception exception) {
      expectedException = exception;
    }

    // Verify
    Mockito.verify(mockCommandChain, Mockito.times(1)).execute(getProjectsCommandDTOCaptor.capture(), getProjectsCommandsCaptor.capture(), signupGlobalExceptionHandlerCaptor.capture());

    // Assertions
    assert expectedException == null;
    assert result == commandDTO;
    assert getProjectsCommandDTOCaptor.getValue() == commandDTO;
    assert getProjectsCommandsCaptor.getValue().get(0) == SignupRequestValidationCommand.class;
    assert getProjectsCommandsCaptor.getValue().get(1) == SignupCommand.class;
    assert getProjectsCommandsCaptor.getValue().get(2) == SignupResponseCommand.class;
    assert signupGlobalExceptionHandlerCaptor.getValue() == SignupGlobalExceptionHandler.class;
  }
}
