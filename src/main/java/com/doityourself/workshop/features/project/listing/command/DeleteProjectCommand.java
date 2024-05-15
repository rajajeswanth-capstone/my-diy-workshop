package com.doityourself.workshop.features.project.listing.command;

import com.doityourself.workshop.common.command.CommandDTO;
import com.doityourself.workshop.common.command.ICommand;
import com.doityourself.workshop.common.constants.ContextConstants;
import com.doityourself.workshop.features.project.listing.dao.ProjectListingDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Delete Project Command
 */
@Component
public class DeleteProjectCommand implements ICommand<CommandDTO> {
  @Autowired
  ProjectListingDao projectListingDao;

  /**
   * Delete Project from database
   *
   * @param dto {@link CommandDTO}
   */
  @Override
  public void process(CommandDTO dto) {
    projectListingDao.deleteByProjectId(
        (Long) dto.get(ContextConstants.CONTEXT_PROJECT_ID)
    );
  }
}
