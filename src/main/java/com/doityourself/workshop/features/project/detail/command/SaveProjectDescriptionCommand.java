package com.doityourself.workshop.features.project.detail.command;

import com.doityourself.workshop.common.command.CommandDTO;
import com.doityourself.workshop.common.command.ICommand;
import com.doityourself.workshop.common.constants.ContextConstants;
import com.doityourself.workshop.common.constants.EntityConstants;
import com.doityourself.workshop.database.entities.DiyProject;
import com.doityourself.workshop.features.project.detail.representation.ProjectDetailRepresentation;
import com.doityourself.workshop.features.project.detail.validation.SaveProjectDescriptionValidations;
import com.doityourself.workshop.features.project.listing.dao.ProjectListingDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Save Project Description Command
 */
@Component
public class SaveProjectDescriptionCommand implements ICommand<CommandDTO> {
  @Autowired
  ProjectListingDao projectListingDao;

  @Autowired
  SaveProjectDescriptionValidations validations;

  /**
   * Save project description to database
   *
   * @param dto {@link CommandDTO}
   */
  @Override
  public void process(CommandDTO dto) {
    DiyProject diyProject = (DiyProject) dto.get(EntityConstants.ENTITY_DIY_PROJECT);
    ProjectDetailRepresentation projectDetailRepresentation = (ProjectDetailRepresentation) dto.get(ContextConstants.CONTEXT_PROJECT_DETAIL);

    DiyProject updatedDiyProject = projectListingDao.saveProject(
        diyProject.toBuilder().description(projectDetailRepresentation.getDescription()).build()
    );

    dto.add(EntityConstants.ENTITY_DIY_PROJECT, updatedDiyProject);
  }

  /**
   * Post-process save project description. Validate entity
   *
   * @param dto {@link CommandDTO}
   */
  @Override
  public void postProcess(CommandDTO dto) {
    DiyProject diyProject = (DiyProject) dto.get(EntityConstants.ENTITY_DIY_PROJECT);
    ProjectDetailRepresentation projectDetailRepresentation = (ProjectDetailRepresentation) dto.get(ContextConstants.CONTEXT_PROJECT_DETAIL);

    validations.validateDiyProjectDescription(diyProject, projectDetailRepresentation);
  }
}
