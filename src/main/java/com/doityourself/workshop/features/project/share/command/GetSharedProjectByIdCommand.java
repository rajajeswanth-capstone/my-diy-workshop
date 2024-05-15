package com.doityourself.workshop.features.project.share.command;

import com.doityourself.workshop.common.command.CommandDTO;
import com.doityourself.workshop.common.command.ICommand;
import com.doityourself.workshop.common.constants.ContextConstants;
import com.doityourself.workshop.common.constants.EntityConstants;
import com.doityourself.workshop.database.entities.DiyProject;
import com.doityourself.workshop.features.project.detail.dao.ProjectDetailDao;
import com.doityourself.workshop.features.project.share.representation.ShareProjectRepresentation;
import com.doityourself.workshop.features.project.share.validation.ShareProjectValidations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Get Shared Project Command
 */
@Component
public class GetSharedProjectByIdCommand implements ICommand<CommandDTO> {
  @Autowired
  ProjectDetailDao projectDetailDao;

  @Autowired
  ShareProjectValidations validations;

  /**
   * Get Shared Project from database
   *
   * @param dto {@link CommandDTO}
   */
  @Override
  public void process(CommandDTO dto) {
    ShareProjectRepresentation shareProject = (ShareProjectRepresentation) dto.get(ContextConstants.CONTEXT_SHARE_PROJECT);
    DiyProject diyProject = projectDetailDao.findProjectById(shareProject.getProjectId());

    dto.add(EntityConstants.ENTITY_DIY_PROJECT, diyProject);
  }

  /**
   * Post-process get shared project operation. Validate entity
   *
   * @param dto {@link CommandDTO}
   */
  @Override
  public void postProcess(CommandDTO dto) {
    validations.validateDiyProjectEntity((DiyProject) dto.get(EntityConstants.ENTITY_DIY_PROJECT));
  }
}
