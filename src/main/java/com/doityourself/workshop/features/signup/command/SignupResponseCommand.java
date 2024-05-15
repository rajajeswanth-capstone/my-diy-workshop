package com.doityourself.workshop.features.signup.command;

import com.doityourself.workshop.common.command.CommandDTO;
import com.doityourself.workshop.common.command.ICommand;
import com.doityourself.workshop.common.constants.ContextConstants;
import com.doityourself.workshop.common.constants.EntityConstants;
import com.doityourself.workshop.database.entities.DiyUser;
import com.doityourself.workshop.features.login.representation.LoggedInUserRepresentation;
import org.springframework.stereotype.Component;

/**
 * Signup Response Command
 */
@Component
public class SignupResponseCommand implements ICommand<CommandDTO> {
  /**
   * Process signup response. Build response object
   *
   * @param dto {@link CommandDTO}
   */
  @Override
  public void process(CommandDTO dto) {
    DiyUser diyUser = (DiyUser) dto.get(EntityConstants.ENTITY_DIY_USER);

    LoggedInUserRepresentation response = LoggedInUserRepresentation.builder()
        .name(diyUser.getName())
        .userName(diyUser.getUserName())
        .build();

    dto.add(ContextConstants.CONTEXT_LOGGED_IN_USER, response);
  }
}
