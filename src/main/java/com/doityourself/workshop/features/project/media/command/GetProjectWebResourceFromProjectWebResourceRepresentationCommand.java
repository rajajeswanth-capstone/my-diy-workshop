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
 * Get Project Web Resource By Id Command
 */
@Component
public class GetProjectWebResourceFromProjectWebResourceRepresentationCommand implements ICommand<CommandDTO> {
  @Autowired
  ProjectMediaDao projectMediaDao;

  @Autowired
  UpdateProjectWebResourceValidations validations;

  /**
   * Get Project web resource by id from database
   *
   * @param dto {@link CommandDTO}
   */
  @Override
  public void process(CommandDTO dto) {
    ProjectDetailWebResourceRepresentation projectDetailWebResourceRepresentation = (ProjectDetailWebResourceRepresentation) dto.get(ContextConstants.CONTEXT_PROJECT_WEB_RESOURCE);
    DiyProjectWebResource diyProjectWebResource = projectMediaDao.findProjectWebResourceByWebResourceId(projectDetailWebResourceRepresentation.getId());

    dto.add(EntityConstants.ENTITY_DIY_PROJECT_WEB_RESOURCE, diyProjectWebResource);
  }

  /**
   * Post-process update project web resource operation. Validate entity
   *
   * @param dto {@link CommandDTO}
   */
  @Override
  public void postProcess(CommandDTO dto) {
    validations.validateDiyProjectWebResourceEntity((DiyProjectWebResource) dto.get(EntityConstants.ENTITY_DIY_PROJECT_WEB_RESOURCE));
  }
}
