package com.doityourself.workshop.features.project.listing.command;

import com.doityourself.workshop.common.command.CommandDTO;
import com.doityourself.workshop.common.command.ICommand;
import com.doityourself.workshop.common.constants.ContextConstants;
import com.doityourself.workshop.common.constants.EntityConstants;
import com.doityourself.workshop.common.utility.FileUtility;
import com.doityourself.workshop.common.utility.StringUtility;
import com.doityourself.workshop.database.entities.DiyProject;
import com.doityourself.workshop.features.project.listing.dao.ProjectListingDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

/**
 * Save Project Image Resource File Command
 */
@Component
public class SaveProjectImageFileCommand implements ICommand<CommandDTO> {
  @Autowired
  ProjectListingDao projectListingDao;

  /**
   * Save Project Image Resource to filesystem
   *
   * @param dto {@link CommandDTO}
   */
  @Override
  public void process(CommandDTO dto) {
    DiyProject diyProject = (DiyProject) dto.get(EntityConstants.ENTITY_DIY_PROJECT);
    MultipartFile localFile = (MultipartFile) dto.get(ContextConstants.CONTEXT_PROJECT_PROJECT_IMAGE_FILE);
    String filePath = FileUtility.buildProjectImageFilePath(diyProject.getId(), localFile);

    if (!localFile.isEmpty()) {
      FileUtility.saveLocalResource(localFile, filePath);
      diyProject.setImageLink(FileUtility.buildProjectImageFileLink(diyProject.getId(), localFile));
      diyProject.setImageContent(FileUtility.getFileBytes(filePath));
      projectListingDao.saveProject(diyProject);
    } else if (StringUtility.isEmpty(diyProject.getImageLink())) {
      diyProject.setImageLink(FileUtility.buildDefaultProjectImageFileLink());
      projectListingDao.saveProject(diyProject);
    }
  }
}
