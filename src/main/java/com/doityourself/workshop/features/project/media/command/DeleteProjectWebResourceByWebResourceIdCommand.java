package com.doityourself.workshop.features.project.media.command;

import com.doityourself.workshop.common.command.CommandDTO;
import com.doityourself.workshop.common.command.ICommand;
import com.doityourself.workshop.common.constants.ContextConstants;
import com.doityourself.workshop.features.project.media.dao.ProjectMediaDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Delete Project Web Resource By Id Command
 */
@Component
public class DeleteProjectWebResourceByWebResourceIdCommand implements ICommand<CommandDTO> {
  @Autowired
  ProjectMediaDao projectMediaDao;

  /**
   * Delete Project web resource by id from database
   *
   * @param dto {@link CommandDTO}
   */
  @Override
  public void process(CommandDTO dto) {
    projectMediaDao.deleteProjectWebResourceByWebResourceId((Long) dto.get(ContextConstants.CONTEXT_WEB_RESOURCE_ID));
  }
}
