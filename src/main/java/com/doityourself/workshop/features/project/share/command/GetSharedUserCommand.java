package com.doityourself.workshop.features.project.share.command;

import com.doityourself.workshop.common.command.CommandDTO;
import com.doityourself.workshop.common.command.ICommand;
import com.doityourself.workshop.common.constants.ContextConstants;
import com.doityourself.workshop.common.constants.EntityConstants;
import com.doityourself.workshop.database.entities.DiyUser;
import com.doityourself.workshop.features.project.share.representation.ShareProjectRepresentation;
import com.doityourself.workshop.features.project.share.validation.ShareProjectValidations;
import com.doityourself.workshop.features.user.dao.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Get Shared User Command
 */
@Component
public class GetSharedUserCommand implements ICommand<CommandDTO> {
  @Autowired
  UserDao userDao;

  @Autowired
  ShareProjectValidations validations;

  /**
   * Get shared user from database
   *
   * @param dto {@link CommandDTO}
   */
  @Override
  public void process(CommandDTO dto) {
    ShareProjectRepresentation shareProject = (ShareProjectRepresentation) dto.get(ContextConstants.CONTEXT_SHARE_PROJECT);
    DiyUser diyUser = userDao.findByUserId(shareProject.getSharedUserId());

    dto.add(EntityConstants.ENTITY_DIY_USER, diyUser);
  }

  /**
   * Post-process get shared user operation. Validate entity
   *
   * @param dto {@link CommandDTO}
   */
  @Override
  public void postProcess(CommandDTO dto) {
    validations.validateSharedDiyUserEntity((DiyUser) dto.get(EntityConstants.ENTITY_DIY_USER));
  }
}
