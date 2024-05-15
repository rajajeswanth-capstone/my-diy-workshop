package com.doityourself.workshop.features.project.media.command;

import com.doityourself.workshop.common.command.CommandDTO;
import com.doityourself.workshop.common.command.ICommand;
import com.doityourself.workshop.common.constants.ContextConstants;
import com.doityourself.workshop.common.constants.EntityConstants;
import com.doityourself.workshop.common.utility.FileUtility;
import com.doityourself.workshop.database.entities.DiyProject;
import com.doityourself.workshop.database.entities.DiyProjectLocalResource;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

/**
 * Update Project Local Resource File Command
 */
@Component
public class UpdateProjectLocalResourceFileCommand implements ICommand<CommandDTO> {
  /**
   * Update Project Local Resource to filesystem
   *
   * @param dto {@link CommandDTO}
   */
  @Override
  public void process(CommandDTO dto) {
    DiyProject diyProject = (DiyProject) dto.get(EntityConstants.ENTITY_DIY_PROJECT);
    DiyProjectLocalResource diyProjectLocalResource = (DiyProjectLocalResource) dto.get(EntityConstants.ENTITY_DIY_PROJECT_LOCAL_RESOURCE);
    MultipartFile localResourceFile = (MultipartFile) dto.get(ContextConstants.CONTEXT_PROJECT_LOCAL_RESOURCE_FILE);
    String filePath = FileUtility.buildLocalResourceFilePath(diyProjectLocalResource.getId(), diyProject.getId(), localResourceFile);

    FileUtility.deleteLocalResourceByLink(diyProjectLocalResource.getLink());
    FileUtility.saveLocalResource(localResourceFile, filePath);
    diyProjectLocalResource.setLink(FileUtility.buildFileLink(diyProjectLocalResource.getId(), diyProject.getId(), localResourceFile));
    diyProjectLocalResource.setOriginalFileName(FileUtility.buildFileName(diyProjectLocalResource.getId(), localResourceFile.getOriginalFilename()));
  }
}
