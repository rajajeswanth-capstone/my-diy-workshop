package com.doityourself.workshop.features.project.material.service;

import com.doityourself.workshop.common.command.CommandChain;
import com.doityourself.workshop.common.command.CommandDTO;
import com.doityourself.workshop.features.project.detail.command.GetProjectDetailByProjectIdCommand;
import com.doityourself.workshop.features.project.detail.command.GetProjectDetailRequestValidationCommand;
import com.doityourself.workshop.features.project.detail.exceptionHandler.GetProjectDetailGlobalExceptionHandler;
import com.doityourself.workshop.features.project.material.command.*;
import com.doityourself.workshop.features.project.material.exceptionHandler.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Arrays;

/**
 * Project material service
 *
 */
@Service
public class ProjectMaterialService {
  @Autowired
  CommandChain commandChain;

  /**
   * Method to get project materials
   *
   * @param commandDTO {@link CommandDTO}
   * @return {@link CommandDTO}
   */
  @Transactional
  public CommandDTO getProjectMaterials(CommandDTO commandDTO) {
    commandChain.execute(commandDTO,
        Arrays.asList(
            GetProjectDetailRequestValidationCommand.class,
            GetProjectDetailByProjectIdCommand.class,
            GetProjectMaterialsByProjectIdCommand.class,
            GetProjectMaterialsResponseCommand.class
        ),
        GetProjectDetailGlobalExceptionHandler.class
    );

    return commandDTO;
  }

  /**
   * Method to get project materials for print
   *
   * @param commandDTO {@link CommandDTO}
   * @return {@link CommandDTO}
   */
  @Transactional
  public CommandDTO printProjectMaterials(CommandDTO commandDTO) {
    commandChain.execute(commandDTO,
        Arrays.asList(
            GetProjectDetailRequestValidationCommand.class,
            GetProjectDetailByProjectIdCommand.class,
            GetProjectMaterialsByProjectIdCommand.class,
            GetProjectMaterialsPrintResponseCommand.class
        ),
        GetProjectDetailGlobalExceptionHandler.class
    );

    return commandDTO;
  }

  /**
   * Method to add project budget
   *
   * @param commandDTO {@link CommandDTO}
   * @return {@link CommandDTO}
   */
  @Transactional
  public CommandDTO saveProjectBudget(CommandDTO commandDTO) {
    commandChain.execute(commandDTO,
        Arrays.asList(
            GetProjectMaterialDetailFromProjectDetailRepresentationCommand.class,
            SaveProjectBudgetCommand.class
        ),
        SaveProjectBudgetGlobalExceptionHandler.class
    );

    return commandDTO;
  }

  /**
   * Method to delete project budget
   *
   * @param commandDTO {@link CommandDTO}
   * @return {@link CommandDTO}
   */
  @Transactional
  public CommandDTO deleteProjectBudget(CommandDTO commandDTO) {
    commandChain.execute(commandDTO,
        Arrays.asList(
            GetProjectDetailByProjectIdCommand.class,
            DeleteProjectBudgetCommand.class
        ),
        SaveProjectBudgetGlobalExceptionHandler.class
    );

    return commandDTO;
  }

  /**
   * Method to save project material
   *
   * @param commandDTO {@link CommandDTO}
   * @return {@link CommandDTO}
   */
  @Transactional
  public CommandDTO saveProjectMaterial(CommandDTO commandDTO) {
    commandChain.execute(commandDTO,
        Arrays.asList(
            SaveProjectMaterialRequestValidationCommand.class,
            GetProjectDetailByProjectIdCommand.class,
            SaveProjectMaterialCommand.class
        ),
        SaveProjectMaterialGlobalExceptionHandler.class
    );

    return commandDTO;
  }

  /**
   * Method to update project material
   *
   * @param commandDTO {@link CommandDTO}
   * @return {@link CommandDTO}
   */
  @Transactional
  public CommandDTO updateProjectMaterial(CommandDTO commandDTO) {
    commandChain.execute(commandDTO,
        Arrays.asList(
            UpdateProjectMaterialRequestValidationCommand.class,
            GetProjectMaterialFromProjectMaterialRepresentationCommand.class,
            UpdateProjectMaterialCommand.class
        ),
        UpdateProjectMaterialGlobalExceptionHandler.class
    );

    return commandDTO;
  }

  /**
   * Method to get project material
   *
   * @param commandDTO {@link CommandDTO}
   * @return {@link CommandDTO}
   */
  @Transactional
  public CommandDTO getProjectMaterial(CommandDTO commandDTO) {
    commandChain.execute(commandDTO,
        Arrays.asList(
            GetProjectMaterialRequestValidationCommand.class,
            GetProjectMaterialByMaterialIdCommand.class,
            GetProjectMaterialByMaterialIdResponseCommand.class
        ),
        GetProjectMaterialGlobalExceptionHandler.class
    );

    return commandDTO;
  }

  /**
   * Method to delete project material
   *
   * @param commandDTO {@link CommandDTO}
   * @return {@link CommandDTO}
   */
  @Transactional
  public CommandDTO deleteProjectMaterial(CommandDTO commandDTO) {
    commandChain.execute(commandDTO,
        Arrays.asList(
            DeleteProjectMaterialRequestValidationCommand.class,
            DeleteProjectMaterialByMaterialIdCommand.class
        ),
        DeleteProjectMaterialGlobalExceptionHandler.class
    );

    return commandDTO;
  }
}
