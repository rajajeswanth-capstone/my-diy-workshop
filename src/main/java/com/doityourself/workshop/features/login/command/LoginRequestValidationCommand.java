package com.doityourself.workshop.features.login.command;

import com.doityourself.workshop.common.command.CommandDTO;
import com.doityourself.workshop.common.command.ICommand;
import com.doityourself.workshop.common.constants.ContextConstants;
import com.doityourself.workshop.features.login.representation.LoginUserRepresentation;
import com.doityourself.workshop.features.login.validation.LoginValidations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Login Request Validation Command
 */
@Component
public class LoginRequestValidationCommand implements ICommand<CommandDTO> {
  @Autowired
  LoginValidations loginValidations;

  /**
   * Process login request validations
   *
   * @param dto {@link CommandDTO}
   */
  @Override
  public void process(CommandDTO dto) {
    loginValidations.validateLoginRequest((LoginUserRepresentation) dto.get(ContextConstants.CONTEXT_LOGIN_USER));
  }
}
