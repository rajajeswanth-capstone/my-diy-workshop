package com.doityourself.workshop.features.login.command;

import com.doityourself.workshop.common.command.CommandDTO;
import com.doityourself.workshop.common.constants.ContextConstants;
import com.doityourself.workshop.features.login.representation.LoginUserRepresentation;
import com.doityourself.workshop.features.login.validation.LoginValidations;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

public class LoginRequestValidationCommandTest {
  @Test
  public void testProcess() {
    // Initialize
    LoginValidations mockLoginValidations = Mockito.mock(LoginValidations.class);
    LoginUserRepresentation loginUserRepresentation = new LoginUserRepresentation();
    loginUserRepresentation.setUserName("test");
    CommandDTO commandDTO = CommandDTO.builder().add(ContextConstants.CONTEXT_LOGIN_USER, loginUserRepresentation).build();
    LoginRequestValidationCommand loginRequestValidationCommand = new LoginRequestValidationCommand();
    loginRequestValidationCommand.loginValidations = mockLoginValidations;

    // Define Mocks
    Mockito.doNothing().when(mockLoginValidations).validateLoginRequest(loginUserRepresentation);

    // Execute
    Exception expectedException = null;
    try {
      loginRequestValidationCommand.process(commandDTO);
    } catch (Exception exception) {
      expectedException = exception;
    }

    // Verify
    Mockito.verify(mockLoginValidations, Mockito.times(1)).validateLoginRequest(loginUserRepresentation);

    // Assertions
    assert expectedException == null;
  }
}
