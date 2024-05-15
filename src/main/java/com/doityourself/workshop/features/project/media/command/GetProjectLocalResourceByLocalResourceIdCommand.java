package com.doityourself.workshop.features.project.media.command;

import com.doityourself.workshop.common.command.CommandDTO;
import com.doityourself.workshop.common.command.ICommand;
import com.doityourself.workshop.common.constants.ContextConstants;
import com.doityourself.workshop.common.constants.EntityConstants;
import com.doityourself.workshop.database.entities.DiyProjectLocalResource;
import com.doityourself.workshop.features.project.media.dao.ProjectMediaDao;
import com.doityourself.workshop.features.project.media.validation.GetProjectLocalResourceValidations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Get Project Local Resource By Id Command
 */
@Component
public class GetProjectLocalResourceByLocalResourceIdCommand implements ICommand<CommandDTO> {
  @Autowired
  ProjectMediaDao projectMediaDao;

  @Autowired
  GetProjectLocalResourceValidations validations;

  /**
   * Get Project local resource by id from database
   *
   * @param dto {@link CommandDTO}
   */
  @Override
  public void process(CommandDTO dto) {
    DiyProjectLocalResource diyProjectLocalResource = projectMediaDao.findProjectLocalResourceByLocalResourceId((Long) dto.get(ContextConstants.CONTEXT_LOCAL_RESOURCE_ID));

    dto.add(EntityConstants.ENTITY_DIY_PROJECT_LOCAL_RESOURCE, diyProjectLocalResource);
  }

  /**
   * Post-process get project local resource operation. Validate entity
   *
   * @param dto {@link CommandDTO}
   */
  @Override
  public void postProcess(CommandDTO dto) {
    validations.validateDiyProjectLocalResourceEntity((DiyProjectLocalResource) dto.get(EntityConstants.ENTITY_DIY_PROJECT_LOCAL_RESOURCE));
  }
}
