package com.doityourself.workshop.features.project.listing.command;

import com.doityourself.workshop.common.command.CommandDTO;
import com.doityourself.workshop.common.command.ICommand;
import com.doityourself.workshop.common.constants.ContextConstants;
import com.doityourself.workshop.common.constants.EntityConstants;
import com.doityourself.workshop.database.entities.DiyProject;
import com.doityourself.workshop.database.entities.DiyUser;
import com.doityourself.workshop.database.entities.DiyUserProject;
import com.doityourself.workshop.database.entities.DiyUserProjectId;
import com.doityourself.workshop.features.login.dao.LoginDao;
import com.doityourself.workshop.features.login.representation.LoggedInUserRepresentation;
import com.doityourself.workshop.features.project.listing.dao.ProjectListingDao;
import com.doityourself.workshop.features.project.listing.validation.SaveProjectValidations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Save User Project Command
 */
@Component
public class SaveUserProjectAssociationCommand implements ICommand<CommandDTO> {
  @Autowired
  ProjectListingDao projectListingDao;

  @Autowired
  LoginDao loginDao;

  @Autowired
  SaveProjectValidations validations;

  /**
   * Find matching logged-in user from database
   *
   * @param dto {@link CommandDTO}
   */
  @Override
  public void preProcess(CommandDTO dto) {
    LoggedInUserRepresentation user = (LoggedInUserRepresentation) dto.get(ContextConstants.CONTEXT_LOGGED_IN_USER);

    DiyUser diyUser = loginDao.findMatchingLoggedInUser(user);
    dto.add(EntityConstants.ENTITY_DIY_USER, diyUser);
  }

  /**
   * Save User Project association to database
   *
   * @param dto {@link CommandDTO}
   */
  @Override
  public void process(CommandDTO dto) {
    DiyProject diyProject = (DiyProject) dto.get(EntityConstants.ENTITY_DIY_PROJECT);
    DiyUser diyUser = (DiyUser) dto.get(EntityConstants.ENTITY_DIY_USER);

    DiyUserProject diyUserProject = projectListingDao.saveUserProject(
        DiyUserProject.builder()
            .id(
                DiyUserProjectId.builder()
                    .diyProject(diyProject)
                    .diyUser(diyUser)
                    .build()
            )
            .shared(false)
            .build()
    );

    dto.add(EntityConstants.ENTITY_DIY_USER_PROJECT, diyUserProject);
  }

  /**
   * Post-process save project association operation. Validate entity
   *
   * @param dto {@link CommandDTO}
   */
  @Override
  public void postProcess(CommandDTO dto) {
    validations.validateDiyUserProjectAssociation((DiyUserProject) dto.get(EntityConstants.ENTITY_DIY_USER_PROJECT));
  }
}
