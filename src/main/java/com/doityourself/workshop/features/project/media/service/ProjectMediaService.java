package com.doityourself.workshop.features.project.media.service;

import com.doityourself.workshop.common.command.CommandChain;
import com.doityourself.workshop.common.command.CommandDTO;
import com.doityourself.workshop.features.project.detail.command.GetProjectDetailByProjectIdCommand;
import com.doityourself.workshop.features.project.detail.command.GetProjectDetailRequestValidationCommand;
import com.doityourself.workshop.features.project.detail.exceptionHandler.GetProjectDetailGlobalExceptionHandler;
import com.doityourself.workshop.features.project.media.command.*;
import com.doityourself.workshop.features.project.media.exceptionHandler.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Arrays;

/**
 * Project Detail Service
 */
@Service
public class ProjectMediaService {
  @Autowired
  CommandChain commandChain;

  /**
   * Method to open file
   */
  public CommandDTO openFile(CommandDTO commandDTO) {
    commandChain.execute(commandDTO,
        Arrays.asList(
            GetProjectLocalResourceRequestValidationCommand.class,
            GetProjectDetailByProjectIdCommand.class,
            GetProjectLocalResourceByLocalResourceIdCommand.class,
            GetProjectLocalResourceByLocalResourceIdResponseCommand.class,
            OpenLocalResourceCommand.class
        ),
        GetProjectLocalResourceGlobalExceptionHandler.class
    );
    return commandDTO;
  }

  /**
   * Method to get project detail
   *
   * @param commandDTO {@link CommandDTO}
   * @return {@link CommandDTO}
   */
  @Transactional
  public CommandDTO getProjectDetail(CommandDTO commandDTO) {
    commandChain.execute(commandDTO,
        Arrays.asList(
            GetProjectDetailRequestValidationCommand.class,
            GetProjectDetailByProjectIdCommand.class,
            GetProjectDetailLocalResourcesCommand.class,
            GetProjectDetailWebResourcesCommand.class,
            GetProjectMediaDetailResponseCommand.class
        ),
        GetProjectDetailGlobalExceptionHandler.class
    );

    return commandDTO;
  }

  /**
   * Method to add project local resource
   *
   * @param commandDTO {@link CommandDTO}
   * @return {@link CommandDTO}
   */
  @Transactional
  public CommandDTO saveProjectLocalResource(CommandDTO commandDTO) {
    commandChain.execute(commandDTO,
        Arrays.asList(
            SaveProjectLocalResourceRequestValidationCommand.class,
            GetProjectDetailByProjectIdCommand.class,
            SaveProjectLocalResourceCommand.class,
            SaveProjectLocalResourceFileCommand.class
        ),
        SaveProjectLocalResourceGlobalExceptionHandler.class
    );

    return commandDTO;
  }

  /**
   * Method to add project web resource
   *
   * @param commandDTO {@link CommandDTO}
   * @return {@link CommandDTO}
   */
  @Transactional
  public CommandDTO saveProjectWebResource(CommandDTO commandDTO) {
    commandChain.execute(commandDTO,
        Arrays.asList(
            SaveProjectWebResourceRequestValidationCommand.class,
            GetProjectDetailByProjectIdCommand.class,
            SaveProjectWebResourceCommand.class
        ),
        SaveProjectWebResourceGlobalExceptionHandler.class
    );

    return commandDTO;
  }

  /**
   * Method to update project local resource
   *
   * @param commandDTO {@link CommandDTO}
   * @return {@link CommandDTO}
   */
  @Transactional
  public CommandDTO updateProjectLocalResource(CommandDTO commandDTO) {
    commandChain.execute(commandDTO,
        Arrays.asList(
            UpdateProjectLocalResourceRequestValidationCommand.class,
            GetProjectDetailByProjectIdCommand.class,
            GetProjectLocalResourceFromProjectLocalResourceRepresentationCommand.class,
            UpdateProjectLocalResourceFileCommand.class,
            UpdateProjectLocalResourceCommand.class
        ),
        UpdateProjectLocalResourceGlobalExceptionHandler.class
    );

    return commandDTO;
  }

  /**
   * Method to update project web resource
   *
   * @param commandDTO {@link CommandDTO}
   * @return {@link CommandDTO}
   */
  @Transactional
  public CommandDTO updateProjectWebResource(CommandDTO commandDTO) {
    commandChain.execute(commandDTO,
        Arrays.asList(
            UpdateProjectWebResourceRequestValidationCommand.class,
            GetProjectDetailByProjectIdCommand.class,
            GetProjectWebResourceFromProjectWebResourceRepresentationCommand.class,
            UpdateProjectWebResourceCommand.class
        ),
        UpdateProjectWebResourceGlobalExceptionHandler.class
    );

    return commandDTO;
  }

  /**
   * Method to get project local resource
   *
   * @param commandDTO {@link CommandDTO}
   * @return {@link CommandDTO}
   */
  @Transactional
  public CommandDTO getProjectLocalResource(CommandDTO commandDTO) {
    commandChain.execute(commandDTO,
        Arrays.asList(
            GetProjectLocalResourceRequestValidationCommand.class,
            GetProjectLocalResourceByLocalResourceIdCommand.class,
            GetProjectLocalResourceByLocalResourceIdResponseCommand.class
        ),
        GetProjectLocalResourceGlobalExceptionHandler.class
    );

    return commandDTO;
  }

  /**
   * Method to get project web resource
   *
   * @param commandDTO {@link CommandDTO}
   * @return {@link CommandDTO}
   */
  @Transactional
  public CommandDTO getProjectWebResource(CommandDTO commandDTO) {
    commandChain.execute(commandDTO,
        Arrays.asList(
            GetProjectWebResourceRequestValidationCommand.class,
            GetProjectWebResourceByWebResourceIdCommand.class,
            GetProjectWebResourceByWebResourceIdResponseCommand.class
        ),
        GetProjectWebResourceGlobalExceptionHandler.class
    );

    return commandDTO;
  }

  /**
   * Method to delete project local resource
   *
   * @param commandDTO {@link CommandDTO}
   * @return {@link CommandDTO}
   */
  @Transactional
  public CommandDTO deleteProjectLocalResource(CommandDTO commandDTO) {
    commandChain.execute(commandDTO,
        Arrays.asList(
            DeleteProjectLocalResourceRequestValidationCommand.class,
            GetProjectLocalResourceByLocalResourceIdCommand.class,
            DeleteProjectLocalResourceFileCommand.class,
            DeleteProjectLocalResourceByLocalResourceIdCommand.class
        ),
        DeleteProjectLocalResourceGlobalExceptionHandler.class
    );

    return commandDTO;
  }

  /**
   * Method to delete project web resource
   *
   * @param commandDTO {@link CommandDTO}
   * @return {@link CommandDTO}
   */
  @Transactional
  public CommandDTO deleteProjectWebResource(CommandDTO commandDTO) {
    commandChain.execute(commandDTO,
        Arrays.asList(
            DeleteProjectWebResourceRequestValidationCommand.class,
            DeleteProjectWebResourceByWebResourceIdCommand.class
        ),
        DeleteProjectWebResourceGlobalExceptionHandler.class
    );

    return commandDTO;
  }
}
