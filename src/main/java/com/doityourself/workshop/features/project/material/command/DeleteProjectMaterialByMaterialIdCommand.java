package com.doityourself.workshop.features.project.material.command;

import com.doityourself.workshop.common.command.CommandDTO;
import com.doityourself.workshop.common.command.ICommand;
import com.doityourself.workshop.common.constants.ContextConstants;
import com.doityourself.workshop.features.project.material.dao.ProjectMaterialDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Delete Project Material By Id Command
 */
@Component
public class DeleteProjectMaterialByMaterialIdCommand implements ICommand<CommandDTO> {
  @Autowired
  ProjectMaterialDao projectMaterialDao;

  /**
   * Delete Project Material by id from database
   *
   * @param dto {@link CommandDTO}
   */
  @Override
  public void process(CommandDTO dto) {
    projectMaterialDao.deleteProjectMaterialByMaterialId((Long) dto.get(ContextConstants.CONTEXT_MATERIAL_ID));
  }
}
