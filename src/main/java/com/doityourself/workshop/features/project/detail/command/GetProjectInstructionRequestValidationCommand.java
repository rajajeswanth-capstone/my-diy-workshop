package com.doityourself.workshop.features.project.detail.command;

import com.doityourself.workshop.common.command.CommandDTO;
import com.doityourself.workshop.common.command.ICommand;
import com.doityourself.workshop.common.constants.ContextConstants;
import com.doityourself.workshop.features.project.detail.validation.GetProjectInstructionValidations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Get Project Instruction Request Validation Command
 */
@Component
public class GetProjectInstructionRequestValidationCommand implements ICommand<CommandDTO> {
  @Autowired
  GetProjectInstructionValidations validations;

  /**
   * Validate Get Project Instruction Request
   *
   * @param dto {@link CommandDTO}
   */
  @Override
  public void process(CommandDTO dto) {
    validations.validateGetProjectInstructionRequest((Long) dto.get(ContextConstants.CONTEXT_INSTRUCTION_ID));
  }
}
