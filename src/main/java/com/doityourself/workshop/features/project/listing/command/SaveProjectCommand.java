package com.doityourself.workshop.features.project.listing.command;

import com.doityourself.workshop.common.command.CommandDTO;
import com.doityourself.workshop.common.command.ICommand;
import com.doityourself.workshop.common.constants.ContextConstants;
import com.doityourself.workshop.common.constants.EntityConstants;
import com.doityourself.workshop.database.entities.DiyProject;
import com.doityourself.workshop.features.project.detail.dao.ProjectDetailDao;
import com.doityourself.workshop.features.project.listing.dao.ProjectListingDao;
import com.doityourself.workshop.features.project.listing.representation.ProjectListingRepresentation;
import com.doityourself.workshop.features.project.listing.validation.SaveProjectValidations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Save Project Command
 */
@Component
public class SaveProjectCommand implements ICommand<CommandDTO> {
  @Autowired
  ProjectListingDao projectListingDao;

  @Autowired
  ProjectDetailDao projectDetailDao;

  @Autowired
  SaveProjectValidations validations;

  /**
   * Save Project to database
   *
   * @param dto {@link CommandDTO}
   */
  @Override
  public void process(CommandDTO dto) {
    ProjectListingRepresentation projectListingRepresentation = (ProjectListingRepresentation) dto.get(ContextConstants.CONTEXT_PROJECT);
    DiyProject saveDiyProject;

    if (projectListingRepresentation.getId() != null) {
      saveDiyProject = projectDetailDao.findProjectById(projectListingRepresentation.getId());
    } else {
      saveDiyProject = DiyProject.builder().build();
    }

    DiyProject diyProject = projectListingDao.saveProject(
        saveDiyProject.toBuilder()
            .title(projectListingRepresentation.getTitle())
            .shortDescription(projectListingRepresentation.getShortDescription())
            .build()
    );

    dto.add(EntityConstants.ENTITY_DIY_PROJECT, diyProject);
  }

  /**
   * Post-process save project operation. Validate entity
   *
   * @param dto {@link CommandDTO}
   */
  @Override
  public void postProcess(CommandDTO dto) {
    validations.validateDiyProjectEntity((DiyProject) dto.get(EntityConstants.ENTITY_DIY_PROJECT));
  }
}
