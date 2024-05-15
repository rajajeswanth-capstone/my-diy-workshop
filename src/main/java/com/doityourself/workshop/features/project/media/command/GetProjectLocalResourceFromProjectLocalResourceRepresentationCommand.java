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
 * Get Project Local Resource By Local Resource Representation Command
 */
@Component
public class GetProjectLocalResourceFromProjectLocalResourceRepresentationCommand implements ICommand<CommandDTO> {
  @Autowired
  ProjectMediaDao projectMediaDao;

  @Autowired
  UpdateProjectLocalResourceValidations validations;

  /**
   * Get Project image by id from database
   *
   * @param dto {@link CommandDTO}
   */
  @Override
  public void process(CommandDTO dto) {
    ProjectDetailLocalResourceRepresentation projectDetailLocalResourceRepresentation = (ProjectDetailLocalResourceRepresentation) dto.get(ContextConstants.CONTEXT_PROJECT_LOCAL_RESOURCE);
    DiyProjectLocalResource diyProjectLocalResource = projectMediaDao.findProjectLocalResourceByLocalResourceId(projectDetailLocalResourceRepresentation.getId());

    dto.add(EntityConstants.ENTITY_DIY_PROJECT_LOCAL_RESOURCE, diyProjectLocalResource);
  }

  /**
   * Post-process update project image operation. Validate entity
   *
   * @param dto {@link CommandDTO}
   */
  @Override
  public void postProcess(CommandDTO dto) {
    validations.validateDiyProjectLocalResourceEntity((DiyProjectLocalResource) dto.get(EntityConstants.ENTITY_DIY_PROJECT_LOCAL_RESOURCE));
  }
}
