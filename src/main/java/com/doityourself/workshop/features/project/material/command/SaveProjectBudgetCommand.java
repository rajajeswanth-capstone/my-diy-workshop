package com.doityourself.workshop.features.project.material.command;

import com.doityourself.workshop.common.command.CommandDTO;
import com.doityourself.workshop.common.command.ICommand;
import com.doityourself.workshop.common.constants.ContextConstants;
import com.doityourself.workshop.common.constants.EntityConstants;
import com.doityourself.workshop.database.entities.DiyProject;
import com.doityourself.workshop.features.project.listing.dao.ProjectListingDao;
import com.doityourself.workshop.features.project.material.representation.ProjectDetailRepresentation;
import com.doityourself.workshop.features.project.material.validation.SaveProjectBudgetValidations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Save Project Budget Command
 */
@Component
public class SaveProjectBudgetCommand implements ICommand<CommandDTO> {
  @Autowired
  ProjectListingDao projectListingDao;

  @Autowired
  SaveProjectBudgetValidations validations;

  /**
   * Save project budget to database
   *
   * @param dto {@link CommandDTO}
   */
  @Override
  public void process(CommandDTO dto) {
    DiyProject diyProject = (DiyProject) dto.get(EntityConstants.ENTITY_DIY_PROJECT);
    ProjectDetailRepresentation projectDetailRepresentation = (ProjectDetailRepresentation) dto.get(ContextConstants.CONTEXT_PROJECT_DETAIL);

    DiyProject updatedDiyProject = projectListingDao.saveProject(
        diyProject.toBuilder().budget(projectDetailRepresentation.getBudget()).build()
    );

    dto.add(EntityConstants.ENTITY_DIY_PROJECT, updatedDiyProject);
  }

  /**
   * Post-process save project budget. Validate entity
   *
   * @param dto {@link CommandDTO}
   */
  @Override
  public void postProcess(CommandDTO dto) {
    DiyProject diyProject = (DiyProject) dto.get(EntityConstants.ENTITY_DIY_PROJECT);
    ProjectDetailRepresentation projectDetailRepresentation = (ProjectDetailRepresentation) dto.get(ContextConstants.CONTEXT_PROJECT_DETAIL);

    validations.validateDiyProjectBudget(diyProject, projectDetailRepresentation);
  }
}
