package com.doityourself.workshop.features.project.listing.command;

import com.doityourself.workshop.common.command.CommandDTO;
import com.doityourself.workshop.common.command.ICommand;
import com.doityourself.workshop.common.constants.ContextConstants;
import com.doityourself.workshop.common.utility.FileUtility;
import com.doityourself.workshop.features.project.listing.validation.DeleteProjectValidations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Delete Project Folder Command
 */
@Component
public class DeleteProjectFolderCommand implements ICommand<CommandDTO> {
  @Autowired
  DeleteProjectValidations validations;

  /**
   * Delete Project Folder to upload static resources
   *
   * @param dto {@link CommandDTO}
   */
  @Override
  public void process(CommandDTO dto) {
    Long projectId = (Long) dto.get(ContextConstants.CONTEXT_PROJECT_ID);
    FileUtility.deleteProjectFolder(projectId);
  }

  /**
   * Post-process delete project folder operation. Validate folder
   *
   * @param dto {@link CommandDTO}
   */
  @Override
  public void postProcess(CommandDTO dto) {
    Long projectId = (Long) dto.get(ContextConstants.CONTEXT_PROJECT_ID);
    validations.validateProjectFolderDelete(projectId);
  }
}
