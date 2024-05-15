package com.doityourself.workshop.features.user.command;

import com.doityourself.workshop.common.command.CommandDTO;
import com.doityourself.workshop.common.command.ICommand;
import com.doityourself.workshop.common.constants.ContextConstants;
import com.doityourself.workshop.common.constants.EntityConstants;
import com.doityourself.workshop.database.entities.DiyUser;
import com.doityourself.workshop.features.login.representation.LoggedInUserRepresentation;
import com.doityourself.workshop.features.user.dao.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Get Shared Users Command
 */
@Component
public class GetSharedUsersCommand implements ICommand<CommandDTO> {
  @Autowired
  UserDao userDao;

  /**
   * Get shared users from database
   *
   * @param dto {@link CommandDTO}
   */
  @Override
  public void process(CommandDTO dto) {
    LoggedInUserRepresentation user = (LoggedInUserRepresentation) dto.get(ContextConstants.CONTEXT_LOGGED_IN_USER);

    List<DiyUser> users = userDao.getSharedUsers(user.getUserName());
    dto.add(EntityConstants.ENTITY_DIY_USERS, users);
  }
}
