package com.doityourself.workshop.features.user.command;

import com.doityourself.workshop.common.command.CommandDTO;
import com.doityourself.workshop.common.command.ICommand;
import com.doityourself.workshop.common.constants.ContextConstants;
import com.doityourself.workshop.features.login.representation.LoggedInUserRepresentation;
import com.doityourself.workshop.features.user.validation.GetSharedUsersForProjectValidations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Get shared users for project request validation command
 */
@Component
public class GetShareUsersForProjectRequestValidationCommand implements ICommand<CommandDTO> {
  @Autowired
  GetSharedUsersForProjectValidations validations;

  /**
   * Get shared users for project request validation
   *
   * @param dto {@link CommandDTO}
   */
  @Override
  public void process(CommandDTO dto) {
    LoggedInUserRepresentation user = (LoggedInUserRepresentation) dto.get(ContextConstants.CONTEXT_LOGGED_IN_USER);
    validations.validateCurrentUserName(user.getUserName());
  }
}
