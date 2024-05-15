package com.doityourself.workshop.features.project.media.command;

import com.doityourself.workshop.common.command.CommandDTO;
import com.doityourself.workshop.common.command.ICommand;
import com.doityourself.workshop.common.constants.ContextConstants;
import com.doityourself.workshop.common.constants.EntityConstants;
import com.doityourself.workshop.database.entities.DiyProjectLocalResource;
import com.doityourself.workshop.features.project.media.dao.ProjectMediaDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Get Project Detail Local Resources Command
 */
@Component
public class GetProjectDetailLocalResourcesCommand implements ICommand<CommandDTO> {
  @Autowired
  ProjectMediaDao projectMediaDao;

  /**
   * Get Project Images from database
   *
   * @param dto {@link CommandDTO}
   */
  @Override
  public void process(CommandDTO dto) {
    List<DiyProjectLocalResource> diyProjectLocalResources = projectMediaDao.findProjectImagesByProjectId((Long) dto.get(ContextConstants.CONTEXT_PROJECT_ID));
    List<String> localResourceTypes = projectMediaDao.findDistinctProjectLocalResourceTypesByProjectId((Long) dto.get(ContextConstants.CONTEXT_PROJECT_ID));

    dto.add(EntityConstants.ENTITY_DIY_PROJECT_LOCAL_RESOURCES, diyProjectLocalResources);
    dto.add(ContextConstants.CONTEXT_LOCAL_RESOURCE_TYPES, Optional.ofNullable(localResourceTypes).orElse(new ArrayList<>()));
  }
}
