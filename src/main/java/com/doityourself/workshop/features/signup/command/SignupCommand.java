package com.doityourself.workshop.features.signup.command;

import com.doityourself.workshop.common.command.CommandDTO;
import com.doityourself.workshop.common.command.ICommand;
import com.doityourself.workshop.common.constants.ContextConstants;
import com.doityourself.workshop.common.constants.EntityConstants;
import com.doityourself.workshop.database.entities.DiyUser;
import com.doityourself.workshop.features.signup.dao.SignupDao;
import com.doityourself.workshop.features.signup.representation.SignupUserRepresentation;
import com.doityourself.workshop.features.signup.validation.SignupValidations;
import org.jasypt.util.password.BasicPasswordEncryptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Signup Command
 */
@Component
public class SignupCommand implements ICommand<CommandDTO> {
  @Autowired
  SignupValidations signupValidations;

  @Autowired
  BasicPasswordEncryptor passwordEncryptor;

  @Autowired
  SignupDao signupDao;

  /**
   * Process signup operation. Save user to database
   *
   * @param dto {@link CommandDTO}
   */
  @Override
  public void process(CommandDTO dto) {
    SignupUserRepresentation user = (SignupUserRepresentation) dto.get(ContextConstants.CONTEXT_LOGIN_USER);

    String encryptedPassword = passwordEncryptor.encryptPassword(user.getPassword());

    DiyUser diyUser = signupDao.saveUser(
        DiyUser.builder()
            .userName(user.getUserName())
            .password(encryptedPassword)
            .name(user.getName())
            .build()
    );
    dto.add(EntityConstants.ENTITY_DIY_USER, diyUser);
  }

  /**
   * Post-process signup operation. Validate entity
   *
   * @param dto {@link CommandDTO}
   */
  @Override
  public void postProcess(CommandDTO dto) {
    signupValidations.validateDiyUserEntity((DiyUser) dto.get(EntityConstants.ENTITY_DIY_USER));
  }
}
