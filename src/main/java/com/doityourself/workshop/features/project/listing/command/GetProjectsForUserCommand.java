package com.doityourself.workshop.features.project.listing.command;

import com.doityourself.workshop.common.command.CommandDTO;
import com.doityourself.workshop.common.command.ICommand;
import com.doityourself.workshop.common.constants.ContextConstants;
import com.doityourself.workshop.common.constants.EntityConstants;
import com.doityourself.workshop.database.entities.DiyProject;
import com.doityourself.workshop.database.repositories.DiyUserProjectRepository;
import com.doityourself.workshop.features.login.representation.LoggedInUserRepresentation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Get Projects for user command
 */
@Component
public class GetProjectsForUserCommand implements ICommand<CommandDTO> {
  @Autowired
  DiyUserProjectRepository diyUserProjectRepository;

  /**
   * Get projects for user
   *
   * @param dto {@link CommandDTO}
   */
  @Override
  public void process(CommandDTO dto) {
    LoggedInUserRepresentation user = (LoggedInUserRepresentation) dto.get(ContextConstants.CONTEXT_LOGGED_IN_USER);

    List<DiyProject> projects = diyUserProjectRepository.findProjectsByUserName(user.getUserName());
    dto.add(EntityConstants.ENTITY_DIY_PROJECTS, projects);
  }
}
