package com.doityourself.workshop.features.project.media.command;

import com.doityourself.workshop.common.command.CommandDTO;
import com.doityourself.workshop.common.command.ICommand;
import com.doityourself.workshop.common.constants.ContextConstants;
import com.doityourself.workshop.common.constants.EntityConstants;
import com.doityourself.workshop.database.entities.DiyProjectWebResource;
import com.doityourself.workshop.features.project.media.dao.ProjectMediaDao;
import com.doityourself.workshop.features.project.media.representation.ProjectDetailWebResourceRepresentation;
import com.doityourself.workshop.features.project.media.validation.UpdateProjectWebResourceValidations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Update Project Web Resource Command
 */
@Component
public class UpdateProjectWebResourceCommand implements ICommand<CommandDTO> {
  @Autowired
  ProjectMediaDao projectMediaDao;

  @Autowired
  UpdateProjectWebResourceValidations validations;

  /**
   * Save Project Web Resource to database
   *
   * @param dto {@link CommandDTO}
   */
  @Override
  public void process(CommandDTO dto) {
    DiyProjectWebResource diyProjectWebResource = (DiyProjectWebResource) dto.get(EntityConstants.ENTITY_DIY_PROJECT_WEB_RESOURCE);
    ProjectDetailWebResourceRepresentation projectDetailWebResourceRepresentation = (ProjectDetailWebResourceRepresentation) dto.get(ContextConstants.CONTEXT_PROJECT_WEB_RESOURCE);

    DiyProjectWebResource updatedDiyProjectWebResource = projectMediaDao.saveProjectWebResource(
        diyProjectWebResource.toBuilder()
            .link(projectDetailWebResourceRepresentation.getLink())
            .title(projectDetailWebResourceRepresentation.getTitle())
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
