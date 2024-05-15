package com.doityourself.workshop.features.signup.command;

import com.doityourself.workshop.common.command.CommandDTO;
import com.doityourself.workshop.common.constants.ContextConstants;
import com.doityourself.workshop.features.signup.representation.SignupUserRepresentation;
import com.doityourself.workshop.features.signup.validation.SignupValidations;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

public class SignupRequestValidationCommandTest {
  @Test
  public void testProcess() {
    // Initialize
    SignupValidations mockSignupValidations = Mockito.mock(SignupValidations.class);

    SignupUserRepresentation signupUserRepresentation = SignupUserRepresentation.builder().build();
    CommandDTO commandDTO = CommandDTO.builder().add(ContextConstants.CONTEXT_LOGIN_USER, signupUserRepresentation).build();

    SignupRequestValidationCommand command = new SignupRequestValidationCommand();
    command.signupValidations = mockSignupValidations;

    // Define Mocks
    Mockito.doNothing().when(mockSignupValidations).validateSignupRequest(signupUserRepresentation);

    // Execute
    Exception expectedException = null;
    try {
      command.process(commandDTO);
    } catch (Exception exception) {
      expectedException = exception;
    }

    // Verify
    Mockito.verify(mockSignupValidations, Mockito.times(1)).validateSignupRequest(signupUserRepresentation);

    // Assertions
    assert expectedException == null;
  }
}
