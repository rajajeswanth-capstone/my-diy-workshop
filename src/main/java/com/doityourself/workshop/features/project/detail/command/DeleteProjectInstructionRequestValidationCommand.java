package com.doityourself.workshop.features.project.detail.command;

import com.doityourself.workshop.common.command.CommandDTO;
import com.doityourself.workshop.common.command.ICommand;
import com.doityourself.workshop.common.constants.ContextConstants;
import com.doityourself.workshop.features.project.detail.validation.DeleteProjectInstructionValidations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Delete Project Instruction Request Validation Command
 */
@Component
public class DeleteProjectInstructionRequestValidationCommand implements ICommand<CommandDTO> {
  @Autowired
  DeleteProjectInstructionValidations validations;

  /**
   * Validate Delete Project Instruction Request
   *
   * @param dto {@link CommandDTO}
   */
  @Override
  public void process(CommandDTO dto) {
    validations.validateDeleteProjectInstructionRequest((Long) dto.get(ContextConstants.CONTEXT_INSTRUCTION_ID));
  }
}
