package com.doityourself.workshop.features.project.share.command;

import com.doityourself.workshop.common.command.CommandDTO;
import com.doityourself.workshop.common.command.ICommand;
import com.doityourself.workshop.common.constants.EntityConstants;
import com.doityourself.workshop.database.entities.DiyProject;
import com.doityourself.workshop.database.entities.DiyUser;
import com.doityourself.workshop.database.entities.DiyUserProject;
import com.doityourself.workshop.database.entities.DiyUserProjectId;
import com.doityourself.workshop.features.project.listing.dao.ProjectListingDao;
import com.doityourself.workshop.features.project.share.validation.ShareProjectValidations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Save Shared User Project association Command
 */
@Component
public class SaveShareUserProjectAssociationCommand implements ICommand<CommandDTO> {
  @Autowired
  ProjectListingDao projectListingDao;

  @Autowired
  ShareProjectValidations validations;


  /**
   * Save Shared User Project association to database
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
            .shared(true)
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
