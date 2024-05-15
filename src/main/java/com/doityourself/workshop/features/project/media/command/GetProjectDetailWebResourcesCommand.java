package com.doityourself.workshop.features.project.media.command;

import com.doityourself.workshop.common.command.CommandDTO;
import com.doityourself.workshop.common.command.ICommand;
import com.doityourself.workshop.common.constants.ContextConstants;
import com.doityourself.workshop.common.constants.EntityConstants;
import com.doityourself.workshop.database.entities.DiyProjectWebResource;
import com.doityourself.workshop.features.project.media.dao.ProjectMediaDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Get Project Detail Web Resources Command
 */
@Component
public class GetProjectDetailWebResourcesCommand implements ICommand<CommandDTO> {
  @Autowired
  ProjectMediaDao projectMediaDao;

  /**
   * Get Project Web Resources from database
   *
   * @param dto {@link CommandDTO}
   */
  @Override
  public void process(CommandDTO dto) {
    List<DiyProjectWebResource> diyProjectWebResources = projectMediaDao.findProjectWebResourcesByProjectId((Long) dto.get(ContextConstants.CONTEXT_PROJECT_ID));
    List<String> webResourceTypes = projectMediaDao.findDistinctProjectWebResourceTypesByProjectId((Long) dto.get(ContextConstants.CONTEXT_PROJECT_ID));

    dto.add(EntityConstants.ENTITY_DIY_PROJECT_WEB_RESOURCES, diyProjectWebResources);
    dto.add(ContextConstants.CONTEXT_WEB_RESOURCE_TYPES, Optional.ofNullable(webResourceTypes).orElse(new ArrayList<>()));
  }
}
