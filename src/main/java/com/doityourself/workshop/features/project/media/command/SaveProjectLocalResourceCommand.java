package com.doityourself.workshop.features.project.media.command;

import com.doityourself.workshop.common.command.CommandDTO;
import com.doityourself.workshop.common.command.ICommand;
import com.doityourself.workshop.common.constants.ContextConstants;
import com.doityourself.workshop.common.constants.EntityConstants;
import com.doityourself.workshop.database.entities.DiyProject;
import com.doityourself.workshop.database.entities.DiyProjectLocalResource;
import com.doityourself.workshop.features.project.media.dao.ProjectMediaDao;
import com.doityourself.workshop.features.project.media.representation.ProjectDetailLocalResourceRepresentation;
import com.doityourself.workshop.features.project.media.validation.SaveProjectLocalResourceValidations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Save Project Local Resource Command
 */
@Component
public class SaveProjectLocalResourceCommand implements ICommand<CommandDTO> {
  @Autowired
  ProjectMediaDao projectMediaDao;

  @Autowired
  SaveProjectLocalResourceValidations validations;

  /**
   * Save Project Local Resource to database
   *
   * @param dto {@link CommandDTO}
   */
  @Override
  public void process(CommandDTO dto) {
    DiyProject diyProject = (DiyProject) dto.get(EntityConstants.ENTITY_DIY_PROJECT);
    ProjectDetailLocalResourceRepresentation projectDetailLocalResourceRepresentation = (ProjectDetailLocalResourceRepresentation) dto.get(ContextConstants.CONTEXT_PROJECT_LOCAL_RESOURCE);
    DiyProjectLocalResource diyProjectLocalResource = null;

    if (projectDetailLocalResourceRepresentation.getId() != null) {
      diyProjectLocalResource = projectMediaDao.findProjectLocalResourceByLocalResourceId(projectDetailLocalResourceRepresentation.getId());
    } else {
      diyProjectLocalResource = DiyProjectLocalResource.builder().build();
    }

    DiyProjectLocalResource updatedDiyProjectLocalResource = projectMediaDao.saveProjectLocalResource(
        diyProjectLocalResource.toBuilder()
            .diyProject(diyProject)
            .title(projectDetailLocalResourceRepresentation.getTitle())
            .localResourceSequence(projectDetailLocalResourceRepresentation.getLocalResourceSequence())
            .type(projectDetailLocalResourceRepresentation.getType())
            .resourceType(projectDetailLocalResourceRepresentation.getResourceType())
            .description(projectDetailLocalResourceRepresentation.getDescription())
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
    DiyProjectLocalResource diyProjectLocalResource = (DiyProjectLocalResource) dto.get(EntityConstants.ENTITY_DIY_PROJECT_LOCAL_RESOURCE);

    validations.validateDiyProjectLocalResourceEntity(diyProjectLocalResource);
  }
}
