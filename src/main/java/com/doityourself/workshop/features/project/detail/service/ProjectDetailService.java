package com.doityourself.workshop.features.project.detail.service;

import com.doityourself.workshop.common.command.CommandChain;
import com.doityourself.workshop.common.command.CommandDTO;
import com.doityourself.workshop.features.project.detail.command.*;
import com.doityourself.workshop.features.project.detail.exceptionHandler.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Arrays;

/**
 * Project Detail Service
 */
@Service
public class ProjectDetailService {
  @Autowired
  CommandChain commandChain;

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
            GetProjectDetailInstructionsCommand.class,
            GetProjectDetailResponseCommand.class
        ),
        GetProjectDetailGlobalExceptionHandler.class
    );

    return commandDTO;
  }

  /**
   * Method to add project description
   *
   * @param commandDTO {@link CommandDTO}
   * @return {@link CommandDTO}
   */
  @Transactional
  public CommandDTO saveProjectDescription(CommandDTO commandDTO) {
    commandChain.execute(commandDTO,
        Arrays.asList(
            GetProjectDetailFromProjectDetailRepresentationCommand.class,
            SaveProjectDescriptionCommand.class
        ),
        SaveProjectDescriptionGlobalExceptionHandler.class
    );

    return commandDTO;
  }

  /**
   * Method to add project instruction
   *
   * @param commandDTO {@link CommandDTO}
   * @return {@link CommandDTO}
   */
  @Transactional
  public CommandDTO saveProjectInstruction(CommandDTO commandDTO) {
    commandChain.execute(commandDTO,
        Arrays.asList(
            SaveProjectInstructionRequestValidationCommand.class,
            GetProjectDetailByProjectIdCommand.class,
            SaveProjectInstructionCommand.class
        ),
        SaveProjectInstructionGlobalExceptionHandler.class
    );

    return commandDTO;
  }

  /**
   * Method to update project instruction
   *
   * @param commandDTO {@link CommandDTO}
   * @return {@link CommandDTO}
   */
  @Transactional
  public CommandDTO updateProjectInstruction(CommandDTO commandDTO) {
    commandChain.execute(commandDTO,
        Arrays.asList(
            UpdateProjectInstructionRequestValidationCommand.class,
            GetProjectDetailByProjectIdCommand.class,
            GetProjectInstructionFromProjectInstructionRepresentationCommand.class,
            UpdateProjectInstructionCommand.class
        ),
        UpdateProjectInstructionGlobalExceptionHandler.class
    );

    return commandDTO;
  }

  /**
   * Method to get project instruction
   *
   * @param commandDTO {@link CommandDTO}
   * @return {@link CommandDTO}
   */
  @Transactional
  public CommandDTO getProjectInstruction(CommandDTO commandDTO) {
    commandChain.execute(commandDTO,
        Arrays.asList(
            GetProjectInstructionRequestValidationCommand.class,
            GetProjectInstructionByInstructionIdCommand.class,
            GetProjectInstructionByInstructionIdResponseCommand.class
        ),
        GetProjectInstructionGlobalExceptionHandler.class
    );

    return commandDTO;
  }

  /**
   * Method to delete project instruction
   *
   * @param commandDTO {@link CommandDTO}
   * @return {@link CommandDTO}
   */
  @Transactional
  public CommandDTO deleteProjectInstruction(CommandDTO commandDTO) {
    commandChain.execute(commandDTO,
        Arrays.asList(
            DeleteProjectInstructionRequestValidationCommand.class,
            DeleteProjectInstructionByInstructionIdCommand.class
        ),
        DeleteProjectInstructionGlobalExceptionHandler.class
    );

    return commandDTO;
  }
}
