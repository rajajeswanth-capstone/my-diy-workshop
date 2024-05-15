package com.doityourself.workshop.features.signup.command;

import com.doityourself.workshop.common.command.CommandDTO;
import com.doityourself.workshop.common.command.ICommand;
import com.doityourself.workshop.common.constants.ContextConstants;
import com.doityourself.workshop.features.signup.representation.SignupUserRepresentation;
import com.doityourself.workshop.features.signup.validation.SignupValidations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Signup request validation command
 */
@Component
public class SignupRequestValidationCommand implements ICommand<CommandDTO> {
  @Autowired
  SignupValidations signupValidations;

  /**
   * Process signup request validations
   *
   * @param dto {@link CommandDTO}
   */
  @Override
  public void process(CommandDTO dto) {
    signupValidations.validateSignupRequest((SignupUserRepresentation) dto.get(ContextConstants.CONTEXT_LOGIN_USER));
  }
}
