package com.doityourself.workshop.features.project.media.command;

import com.doityourself.workshop.common.command.CommandDTO;
import com.doityourself.workshop.common.command.ICommand;
import com.doityourself.workshop.common.constants.ContextConstants;
import com.doityourself.workshop.features.project.media.dao.ProjectMediaDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Delete Project Local Resource By Id Command
 */
@Component
public class DeleteProjectLocalResourceByLocalResourceIdCommand implements ICommand<CommandDTO> {
  @Autowired
  ProjectMediaDao projectMediaDao;

  /**
   * Delete Project local resource by id from database
   *
   * @param dto {@link CommandDTO}
   */
  @Override
  public void process(CommandDTO dto) {
    projectMediaDao.deleteProjectLocalResourceByLocalResourceId((Long) dto.get(ContextConstants.CONTEXT_LOCAL_RESOURCE_ID));
  }
}
