package com.doityourself.workshop.features.project.media.command;

import com.doityourself.workshop.common.command.CommandDTO;
import com.doityourself.workshop.common.command.ICommand;
import com.doityourself.workshop.common.constants.ContextConstants;
import com.doityourself.workshop.common.constants.EntityConstants;
import com.doityourself.workshop.common.exception.DiyWorkshopException;
import com.doityourself.workshop.common.utility.FileUtility;
import com.doityourself.workshop.database.entities.DiyProject;
import com.doityourself.workshop.database.entities.DiyProjectLocalResource;
import com.doityourself.workshop.features.project.media.dao.ProjectMediaDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

/**
 * Save Project Local Resource File Command
 */
@Component
public class SaveProjectLocalResourceFileCommand implements ICommand<CommandDTO> {
  @Autowired
  ProjectMediaDao projectMediaDao;

  /**
   * Save Project Local Resource to filesystem
   *
   * @param dto {@link CommandDTO}
   */
  @Override
  public void process(CommandDTO dto) {
    DiyProject diyProject = (DiyProject) dto.get(EntityConstants.ENTITY_DIY_PROJECT);
    DiyProjectLocalResource diyProjectLocalResource = (DiyProjectLocalResource) dto.get(EntityConstants.ENTITY_DIY_PROJECT_LOCAL_RESOURCE);
    MultipartFile localFile = (MultipartFile) dto.get(ContextConstants.CONTEXT_PROJECT_LOCAL_RESOURCE_FILE);
    String filePath = FileUtility.buildLocalResourceFilePath(diyProjectLocalResource.getId(), diyProject.getId(), localFile);

    if (!localFile.isEmpty()) {
      try {
        FileUtility.saveLocalResource(localFile, filePath);
        diyProjectLocalResource.setLink(FileUtility.buildFileLink(diyProjectLocalResource.getId(), diyProject.getId(), localFile));
        diyProjectLocalResource.setOriginalFileName(FileUtility.buildFileName(diyProjectLocalResource.getId(), localFile.getOriginalFilename()));
        diyProjectLocalResource.setResourceContent(FileUtility.getFileBytes(filePath));
        projectMediaDao.saveProjectLocalResource(diyProjectLocalResource);
      } catch (DiyWorkshopException exception) {
        projectMediaDao.deleteProjectLocalResourceByLocalResourceId(diyProjectLocalResource.getId());
        throw exception;
      }
    }
  }
}
