package com.doityourself.workshop.features.project.detail.command;

import com.doityourself.workshop.common.command.CommandDTO;
import com.doityourself.workshop.common.command.ICommand;
import com.doityourself.workshop.common.constants.ContextConstants;
import com.doityourself.workshop.common.constants.EntityConstants;
import com.doityourself.workshop.database.entities.DiyProject;
import com.doityourself.workshop.features.project.detail.dao.ProjectDetailDao;
import com.doityourself.workshop.features.project.detail.representation.ProjectDetailRepresentation;
import com.doityourself.workshop.features.project.detail.validation.GetProjectDetailValidations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Get Project Detail Command
 */
@Component
public class GetProjectDetailFromProjectDetailRepresentationCommand implements ICommand<CommandDTO> {
  @Autowired
  ProjectDetailDao projectDetailDao;

  @Autowired
  GetProjectDetailValidations validations;

  /**
   * Get Project from database
   *
   * @param dto {@link CommandDTO}
   */
  @Override
  public void process(CommandDTO dto) {
    ProjectDetailRepresentation project = (ProjectDetailRepresentation) dto.get(ContextConstants.CONTEXT_PROJECT_DETAIL);
    DiyProject diyProject = projectDetailDao.findProjectById(project.getId());

    dto.add(EntityConstants.ENTITY_DIY_PROJECT, diyProject);
  }

  /**
   * Post-process get project operation. Validate entity
   *
   * @param dto {@link CommandDTO}
   */
  @Override
  public void postProcess(CommandDTO dto) {
    validations.validateDiyProjectEntity((DiyProject) dto.get(EntityConstants.ENTITY_DIY_PROJECT));
  }
}
