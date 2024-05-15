package com.doityourself.workshop.features.project.detail.command;

import com.doityourself.workshop.common.command.CommandDTO;
import com.doityourself.workshop.common.command.ICommand;
import com.doityourself.workshop.common.constants.ContextConstants;
import com.doityourself.workshop.features.project.detail.representation.ProjectDetailInstructionRepresentation;
import com.doityourself.workshop.features.project.detail.validation.SaveProjectInstructionValidations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Save Project Instruction Request Validation Command
 */
@Component
public class SaveProjectInstructionRequestValidationCommand implements ICommand<CommandDTO> {
  @Autowired
  SaveProjectInstructionValidations validations;

  /**
   * Validate Save Project Instruction Request
   *
   * @param dto {@link CommandDTO}
   */
  @Override
  public void process(CommandDTO dto) {
    validations.validateSaveInstructionRequest((ProjectDetailInstructionRepresentation) dto.get(ContextConstants.CONTEXT_PROJECT_INSTRUCTION));
  }
}
