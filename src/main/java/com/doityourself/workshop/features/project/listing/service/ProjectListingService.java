package com.doityourself.workshop.features.project.listing.service;

import com.doityourself.workshop.common.command.CommandChain;
import com.doityourself.workshop.common.command.CommandDTO;
import com.doityourself.workshop.features.project.listing.command.*;
import com.doityourself.workshop.features.project.listing.exception.ProjectSaveFailedException;
import com.doityourself.workshop.features.project.listing.exceptionHandler.DeleteProjectGlobalExceptionHandler;
import com.doityourself.workshop.features.project.listing.exceptionHandler.GetProjectsForUserGlobalExceptionHandler;
import com.doityourself.workshop.features.project.listing.exceptionHandler.SaveProjectGlobalExceptionHandler;
import com.doityourself.workshop.features.project.share.command.GetSharedProjectsForUserCommand;
import com.doityourself.workshop.features.project.share.command.GetSharedProjectsForUserResponseCommand;
import com.doityourself.workshop.features.user.command.GetSharedUsersCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Arrays;

/**
 * Project Listing Service
 */
@Service
public class ProjectListingService {
  @Autowired
  CommandChain commandChain;

  /**
   * Method to get projects for current user
   *
   * @param commandDTO {@link CommandDTO}
   * @return {@link CommandDTO}
   */
  @SuppressWarnings("unchecked")
  public CommandDTO getProjects(CommandDTO commandDTO) {
    commandChain.execute(commandDTO,
        Arrays.asList(
            GetProjectsForUserCommand.class,
            GetSharedProjectsForUserCommand.class,
            GetSharedUsersCommand.class,
            GetProjectsForUserResponseCommand.class,
            GetSharedUsersResponseCommand.class,
            GetSharedProjectsForUserResponseCommand.class
        ),
        GetProjectsForUserGlobalExceptionHandler.class
    );

    return commandDTO;
  }

  /**
   * Method to save project
   *
   * @param commandDTO {@link CommandDTO}
   * @return {@link CommandDTO}
   */
  @Transactional
  public CommandDTO saveProject(CommandDTO commandDTO) throws ProjectSaveFailedException {
    commandChain.execute(commandDTO,
        Arrays.asList(
            SaveProjectRequestValidationCommand.class,
            SaveProjectCommand.class,
            CreateProjectFolderCommand.class,
            SaveProjectImageFileCommand.class,
            SaveUserProjectAssociationCommand.class
        ),
        SaveProjectGlobalExceptionHandler.class
    );

    return commandDTO;
  }

  /**
   * Method to delete project
   *
   * @param commandDTO {@link CommandDTO}
   * @return {@link CommandDTO}
   */
  @Transactional
  public CommandDTO deleteProject(CommandDTO commandDTO) {
    commandChain.execute(commandDTO,
        Arrays.asList(
            DeleteProjectRequestValidationCommand.class,
            DeleteProjectFolderCommand.class,
            DeleteProjectCommand.class
        ),
        DeleteProjectGlobalExceptionHandler.class
    );
    return commandDTO;
  }
}
