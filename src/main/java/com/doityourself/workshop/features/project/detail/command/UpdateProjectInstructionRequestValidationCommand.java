package com.doityourself.workshop.features.project.detail.command;

import com.doityourself.workshop.common.command.CommandDTO;
import com.doityourself.workshop.common.command.ICommand;
import com.doityourself.workshop.common.constants.ContextConstants;
import com.doityourself.workshop.features.project.detail.representation.ProjectDetailInstructionRepresentation;
import com.doityourself.workshop.features.project.detail.validation.UpdateProjectInstructionValidations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Update Project Instruction Request Validation Command
 */
@Component
public class UpdateProjectInstructionRequestValidationCommand implements ICommand<CommandDTO> {
  @Autowired
  UpdateProjectInstructionValidations validations;

  /**
   * Validate Update Project Instruction Request
   *
   * @param dto {@link CommandDTO}
   */
  @Override
  public void process(CommandDTO dto) {
    validations.validateUpdateInstructionRequest((ProjectDetailInstructionRepresentation) dto.get(ContextConstants.CONTEXT_PROJECT_INSTRUCTION));
  }
}
