package com.doityourself.workshop.features.project.media.command;

import com.doityourself.workshop.common.command.CommandDTO;
import com.doityourself.workshop.common.command.ICommand;
import com.doityourself.workshop.common.constants.EntityConstants;
import com.doityourself.workshop.common.utility.FileUtility;
import com.doityourself.workshop.database.entities.DiyProject;
import com.doityourself.workshop.database.entities.DiyProjectLocalResource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.system.ApplicationHome;
import org.springframework.stereotype.Component;

import java.awt.*;

/**
 * Open Local Resource
 */
@Component
public class OpenLocalResourceCommand implements ICommand<CommandDTO> {
  @Autowired
  ApplicationHome applicationHome;

  /**
   * Open Local Resource
   *
   * @param dto {@link CommandDTO}
   */
  @Override
  public void process(CommandDTO dto) {
    DiyProjectLocalResource diyProjectLocalResource = (DiyProjectLocalResource) dto.get(EntityConstants.ENTITY_DIY_PROJECT_LOCAL_RESOURCE);
    DiyProject diyProject = (DiyProject) dto.get(EntityConstants.ENTITY_DIY_PROJECT);

    FileUtility.openFile(Desktop.getDesktop(), diyProject.getId(), diyProjectLocalResource.getOriginalFileName(), applicationHome.getDir().getAbsolutePath());
  }
}
