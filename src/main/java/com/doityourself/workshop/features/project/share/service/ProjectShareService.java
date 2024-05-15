package com.doityourself.workshop.features.project.share.service;

import com.doityourself.workshop.common.command.CommandChain;
import com.doityourself.workshop.common.command.CommandDTO;
import com.doityourself.workshop.features.project.share.command.GetSharedProjectByIdCommand;
import com.doityourself.workshop.features.project.share.command.GetSharedUserCommand;
import com.doityourself.workshop.features.project.share.command.SaveShareUserProjectAssociationCommand;
import com.doityourself.workshop.features.project.share.command.ShareProjectRequestValidationCommand;
import com.doityourself.workshop.features.project.share.exceptionHandler.ShareProjectGlobalExceptionHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Arrays;

/**
 * Share Project Service
 */
@Service
public class ProjectShareService {
  @Autowired
  CommandChain commandChain;

  /**
   * Method to share project
   *
   * @param commandDTO {@link CommandDTO}
   * @return {@link CommandDTO}
   */
  @Transactional
  public CommandDTO shareProject(CommandDTO commandDTO) {
    commandChain.execute(commandDTO,
        Arrays.asList(
            ShareProjectRequestValidationCommand.class,
            GetSharedProjectByIdCommand.class,
            GetSharedUserCommand.class,
            SaveShareUserProjectAssociationCommand.class
        ),
        ShareProjectGlobalExceptionHandler.class
    );

    return commandDTO;
  }
}
