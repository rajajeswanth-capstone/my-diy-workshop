package com.doityourself.workshop.features.project.material.command;

import com.doityourself.workshop.common.command.CommandDTO;
import com.doityourself.workshop.common.command.ICommand;
import com.doityourself.workshop.common.constants.EntityConstants;
import com.doityourself.workshop.database.entities.DiyProject;
import com.doityourself.workshop.features.project.listing.dao.ProjectListingDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Delete Project Budget Command
 */
@Component
public class DeleteProjectBudgetCommand implements ICommand<CommandDTO> {
  @Autowired
  ProjectListingDao projectListingDao;

  /**
   * Delete project budget to database
   *
   * @param dto {@link CommandDTO}
   */
  @Override
  public void process(CommandDTO dto) {
    DiyProject diyProject = (DiyProject) dto.get(EntityConstants.ENTITY_DIY_PROJECT);
    diyProject.setBudget(null);

    DiyProject updatedDiyProject = projectListingDao.saveProject(
        diyProject
    );

    dto.add(EntityConstants.ENTITY_DIY_PROJECT, updatedDiyProject);
  }
}
