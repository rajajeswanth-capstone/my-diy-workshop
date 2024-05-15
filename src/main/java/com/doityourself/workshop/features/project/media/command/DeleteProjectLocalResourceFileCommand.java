package com.doityourself.workshop.features.project.media.command;

import com.doityourself.workshop.common.command.CommandDTO;
import com.doityourself.workshop.common.command.ICommand;
import com.doityourself.workshop.common.constants.EntityConstants;
import com.doityourself.workshop.common.utility.FileUtility;
import com.doityourself.workshop.database.entities.DiyProjectLocalResource;
import org.springframework.stereotype.Component;

/**
 * Update Project Local Resource File Command
 */
@Component
public class DeleteProjectLocalResourceFileCommand implements ICommand<CommandDTO> {
  /**
   * Delete Project Local Resource from filesystem
   *
   * @param dto {@link CommandDTO}
   */
  @Override
  public void process(CommandDTO dto) {
    DiyProjectLocalResource diyProjectLocalResource = (DiyProjectLocalResource) dto.get(EntityConstants.ENTITY_DIY_PROJECT_LOCAL_RESOURCE);

    FileUtility.deleteLocalResourceByLink(diyProjectLocalResource.getLink());
  }
}
