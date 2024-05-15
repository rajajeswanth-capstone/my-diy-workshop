package com.doityourself.workshop.features.project.listing.command;

import com.doityourself.workshop.common.command.CommandDTO;
import com.doityourself.workshop.common.command.ICommand;
import com.doityourself.workshop.common.constants.EntityConstants;
import com.doityourself.workshop.common.utility.FileUtility;
import com.doityourself.workshop.database.entities.DiyProject;
import com.doityourself.workshop.features.project.listing.dao.ProjectListingDao;
import com.doityourself.workshop.features.project.listing.validation.SaveProjectValidations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Create Project Folder Command
 */
@Component
public class CreateProjectFolderCommand implements ICommand<CommandDTO> {
  @Autowired
  SaveProjectValidations validations;

  @Autowired
  ProjectListingDao projectListingDao;

  /**
   * Create Project Folder to upload static resources
   *
   * @param dto {@link CommandDTO}
   */
  @Override
  public void process(CommandDTO dto) {
    DiyProject diyProject = (DiyProject) dto.get(EntityConstants.ENTITY_DIY_PROJECT);
    FileUtility.createProjectFolders(diyProject.getId());
  }

  /**
   * Post-process create project folder operation. Validate folder
   *
   * @param dto {@link CommandDTO}
   */
  @Override
  public void postProcess(CommandDTO dto) {
    DiyProject diyProject = (DiyProject) dto.get(EntityConstants.ENTITY_DIY_PROJECT);
    if (!FileUtility.isProjectFoldersCreated(diyProject.getId())) {
      projectListingDao.deleteByProjectId(diyProject.getId());
    }
    validations.validateProjectFolderCreate(diyProject);
  }
}
