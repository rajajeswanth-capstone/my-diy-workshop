package com.doityourself.workshop.features.login.command;

import com.doityourself.workshop.common.command.CommandDTO;
import com.doityourself.workshop.common.command.ICommand;
import com.doityourself.workshop.common.constants.ContextConstants;
import com.doityourself.workshop.common.constants.EntityConstants;
import com.doityourself.workshop.database.entities.DiyUser;
import com.doityourself.workshop.features.login.dao.LoginDao;
import com.doityourself.workshop.features.login.representation.LoginUserRepresentation;
import com.doityourself.workshop.features.login.validation.LoginValidations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Login Command
 */
@Component
public class LoginCommand implements ICommand<CommandDTO> {
  @Autowired
  LoginValidations loginValidations;

  @Autowired
  LoginDao loginDao;

  /**
   * Process login operation. Lookup user from database
   *
   * @param dto {@link CommandDTO}
   */
  @Override
  public void process(CommandDTO dto) {
    LoginUserRepresentation user = (LoginUserRepresentation) dto.get(ContextConstants.CONTEXT_LOGIN_USER);

    DiyUser diyUser = loginDao.findMatchingUser(user);
    dto.add(EntityConstants.ENTITY_DIY_USER, diyUser);
  }

  /**
   * Post-process login operation. Validate entity
   *
   * @param dto {@link CommandDTO}
   */
  @Override
  public void postProcess(CommandDTO dto) {
    loginValidations.validateDiyUserEntity((DiyUser) dto.get(EntityConstants.ENTITY_DIY_USER));
  }
}
