package com.doityourself.workshop.features.project.detail.command;

import com.doityourself.workshop.common.command.CommandDTO;
import com.doityourself.workshop.common.command.ICommand;
import com.doityourself.workshop.common.constants.ContextConstants;
import com.doityourself.workshop.common.constants.EntityConstants;
import com.doityourself.workshop.database.entities.DiyProjectInstruction;
import com.doityourself.workshop.features.project.detail.dao.ProjectDetailDao;
import com.doityourself.workshop.features.project.detail.representation.ProjectDetailInstructionRepresentation;
import com.doityourself.workshop.features.project.detail.validation.GetProjectInstructionValidations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Get Project Instruction By Id Command
 */
@Component
public class GetProjectInstructionFromProjectInstructionRepresentationCommand implements ICommand<CommandDTO> {
  @Autowired
  ProjectDetailDao projectDetailDao;

  @Autowired
  GetProjectInstructionValidations validations;

  /**
   * Get Project instruction by id from database
   *
   * @param dto {@link CommandDTO}
   */
  @Override
  public void process(CommandDTO dto) {
    ProjectDetailInstructionRepresentation projectDetailInstructionRepresentation = (ProjectDetailInstructionRepresentation) dto.get(ContextConstants.CONTEXT_PROJECT_INSTRUCTION);
    DiyProjectInstruction diyProjectInstruction = projectDetailDao.findProjectInstructionByInstructionId(projectDetailInstructionRepresentation.getId());

    dto.add(EntityConstants.ENTITY_DIY_PROJECT_INSTRUCTION, diyProjectInstruction);
  }

  /**
   * Post-process get project operation. Validate entity
   *
   * @param dto {@link CommandDTO}
   */
  @Override
  public void postProcess(CommandDTO dto) {
    validations.validateDiyProjectInstructionEntity((DiyProjectInstruction) dto.get(EntityConstants.ENTITY_DIY_PROJECT_INSTRUCTION));
  }
}
