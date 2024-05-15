package com.doityourself.workshop.features.project.media.command;

import com.doityourself.workshop.common.command.CommandDTO;
import com.doityourself.workshop.common.command.ICommand;
import com.doityourself.workshop.common.constants.ContextConstants;
import com.doityourself.workshop.common.constants.EntityConstants;
import com.doityourself.workshop.database.entities.DiyProjectLocalResource;
import com.doityourself.workshop.features.project.media.dao.ProjectMediaDao;
import com.doityourself.workshop.features.project.media.representation.ProjectDetailLocalResourceRepresentation;
import com.doityourself.workshop.features.project.media.validation.UpdateProjectLocalResourceValidations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Update Project Local Resource Command
 */
@Component
public class UpdateProjectLocalResourceCommand implements ICommand<CommandDTO> {
  @Autowired
  ProjectMediaDao projectMediaDao;

  @Autowired
  UpdateProjectLocalResourceValidations validations;

  /**
   * Save Project Local Resource to database
   *
   * @param dto {@link CommandDTO}
   */
  @Override
  public void process(CommandDTO dto) {
    DiyProjectLocalResource diyProjectLocalResource = (DiyProjectLocalResource) dto.get(EntityConstants.ENTITY_DIY_PROJECT_LOCAL_RESOURCE);
    ProjectDetailLocalResourceRepresentation projectDetailLocalResourceRepresentation = (ProjectDetailLocalResourceRepresentation) dto.get(ContextConstants.CONTEXT_PROJECT_LOCAL_RESOURCE);

    DiyProjectLocalResource updatedDiyProjectLocalResource = projectMediaDao.saveProjectLocalResource(
        diyProjectLocalResource.toBuilder()
            .title(projectDetailLocalResourceRepresentation.getTitle())
            .description(projectDetailLocalResourceRepresentation.getDescription())
            .resourceType(projectDetailLocalResourceRepresentation.getResourceType())
            .build()
    );

    dto.add(EntityConstants.ENTITY_DIY_PROJECT_LOCAL_RESOURCE, updatedDiyProjectLocalResource);
    dto.add(ContextConstants.CONTEXT_PROJECT_LOCAL_RESOURCE_ARTIFACT, projectDetailLocalResourceRepresentation.getResourceType());
  }

  /**
   * Post-process save project local resource. Validate entity
   *
   * @param dto {@link CommandDTO}
   */
  @Override
  public void postProcess(CommandDTO dto) {
    DiyProjectLocalResource diyProjectInstruction = (DiyProjectLocalResource) dto.get(EntityConstants.ENTITY_DIY_PROJECT_LOCAL_RESOURCE);

    validations.validateDiyProjectLocalResourceEntity(diyProjectInstruction);
  }
}
