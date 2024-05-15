package com.doityourself.workshop.features.project.media.command;

import com.doityourself.workshop.common.command.CommandDTO;
import com.doityourself.workshop.common.command.ICommand;
import com.doityourself.workshop.common.constants.ContextConstants;
import com.doityourself.workshop.common.constants.EntityConstants;
import com.doityourself.workshop.database.entities.DiyProject;
import com.doityourself.workshop.database.entities.DiyProjectWebResource;
import com.doityourself.workshop.features.project.media.dao.ProjectMediaDao;
import com.doityourself.workshop.features.project.media.representation.ProjectDetailWebResourceRepresentation;
import com.doityourself.workshop.features.project.media.validation.SaveProjectWebResourceValidations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Save Project Web Resource Command
 */
@Component
public class SaveProjectWebResourceCommand implements ICommand<CommandDTO> {
  @Autowired
  ProjectMediaDao projectMediaDao;

  @Autowired
  SaveProjectWebResourceValidations validations;

  /**
   * Save Project Web Resource to database
   *
   * @param dto {@link CommandDTO}
   */
  @Override
  public void process(CommandDTO dto) {
    DiyProject diyProject = (DiyProject) dto.get(EntityConstants.ENTITY_DIY_PROJECT);
    ProjectDetailWebResourceRepresentation projectDetailWebResourceRepresentation = (ProjectDetailWebResourceRepresentation) dto.get(ContextConstants.CONTEXT_PROJECT_WEB_RESOURCE);
    DiyProjectWebResource diyProjectWebResource = null;

    if (projectDetailWebResourceRepresentation.getId() != null) {
      diyProjectWebResource = projectMediaDao.findProjectWebResourceByWebResourceId(projectDetailWebResourceRepresentation.getId());
    } else {
      diyProjectWebResource = DiyProjectWebResource.builder().build();
    }

    DiyProjectWebResource updatedDiyProjectWebResource = projectMediaDao.saveProjectWebResource(
        diyProjectWebResource.toBuilder()
            .diyProject(diyProject)
            .title(projectDetailWebResourceRepresentation.getTitle())
            .link(projectDetailWebResourceRepresentation.getLink())
            .webResourceSequence(projectDetailWebResourceRepresentation.getWebResourceSequence())
            .type(projectDetailWebResourceRepresentation.getType())
            .description(projectDetailWebResourceRepresentation.getDescription())
            .resourceType(projectDetailWebResourceRepresentation.getResourceType())
            .build()
    );

    dto.add(EntityConstants.ENTITY_DIY_PROJECT_WEB_RESOURCE, updatedDiyProjectWebResource);
  }

  /**
   * Post-process save project web resource. Validate entity
   *
   * @param dto {@link CommandDTO}
   */
  @Override
  public void postProcess(CommandDTO dto) {
    DiyProjectWebResource diyProjectWebResource = (DiyProjectWebResource) dto.get(EntityConstants.ENTITY_DIY_PROJECT_WEB_RESOURCE);

    validations.validateDiyProjectWebResourceEntity(diyProjectWebResource);
  }
}
