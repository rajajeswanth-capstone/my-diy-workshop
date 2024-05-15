package com.doityourself.workshop.features.project.detail.command;

import com.doityourself.workshop.common.command.CommandDTO;
import com.doityourself.workshop.common.command.ICommand;
import com.doityourself.workshop.common.constants.ContextConstants;
import com.doityourself.workshop.common.constants.EntityConstants;
import com.doityourself.workshop.database.entities.DiyProject;
import com.doityourself.workshop.database.entities.DiyProjectInstruction;
import com.doityourself.workshop.features.project.detail.dao.ProjectDetailDao;
import com.doityourself.workshop.features.project.detail.representation.ProjectDetailInstructionRepresentation;
import com.doityourself.workshop.features.project.detail.validation.SaveProjectInstructionValidations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Save Project Instruction Command
 */
@Component
public class SaveProjectInstructionCommand implements ICommand<CommandDTO> {
  @Autowired
  ProjectDetailDao projectDetailDao;

  @Autowired
  SaveProjectInstructionValidations validations;

  /**
   * Save Project Instruction to database
   *
   * @param dto {@link CommandDTO}
   */
  @Override
  public void process(CommandDTO dto) {
    DiyProject diyProject = (DiyProject) dto.get(EntityConstants.ENTITY_DIY_PROJECT);
    ProjectDetailInstructionRepresentation projectDetailInstructionRepresentation = (ProjectDetailInstructionRepresentation) dto.get(ContextConstants.CONTEXT_PROJECT_INSTRUCTION);
    DiyProjectInstruction diyProjectInstruction;

    if (projectDetailInstructionRepresentation.getId() != null) {
      diyProjectInstruction = projectDetailDao.findProjectInstructionByInstructionId(projectDetailInstructionRepresentation.getId());
    } else {
      diyProjectInstruction = new DiyProjectInstruction();
    }

    DiyProjectInstruction updatedDiyProjectInstruction = projectDetailDao.saveProjectInstruction(
        diyProjectInstruction.toBuilder()
            .diyProject(diyProject)
            .title(projectDetailInstructionRepresentation.getTitle())
            .instructionSequence(projectDetailInstructionRepresentation.getInstructionSequence())
            .instruction(projectDetailInstructionRepresentation.getInstruction())
            .build()
    );

    dto.add(EntityConstants.ENTITY_DIY_PROJECT_INSTRUCTION, updatedDiyProjectInstruction);
  }

  /**
   * Post-process save project instruction. Validate entity
   *
   * @param dto {@link CommandDTO}
   */
  @Override
  public void postProcess(CommandDTO dto) {
    DiyProjectInstruction diyProjectInstruction = (DiyProjectInstruction) dto.get(EntityConstants.ENTITY_DIY_PROJECT_INSTRUCTION);

    validations.validateDiyProjectInstructionEntity(diyProjectInstruction);
  }
}
