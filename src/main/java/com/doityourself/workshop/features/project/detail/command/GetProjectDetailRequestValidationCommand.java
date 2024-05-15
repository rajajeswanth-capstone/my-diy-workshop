package com.doityourself.workshop.features.project.detail.command;

import com.doityourself.workshop.common.command.CommandDTO;
import com.doityourself.workshop.common.command.ICommand;
import com.doityourself.workshop.common.constants.ContextConstants;
import com.doityourself.workshop.features.project.detail.validation.GetProjectDetailValidations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Get Project Detail Request Validation Command
 */
@Component
public class GetProjectDetailRequestValidationCommand implements ICommand<CommandDTO> {
  @Autowired
  GetProjectDetailValidations validations;

  /**
   * Validate Get Project Detail Request
   *
   * @param dto {@link CommandDTO}
   */
  @Override
  public void process(CommandDTO dto) {
    validations.validateGetProjectDetailRequest((Long) dto.get(ContextConstants.CONTEXT_PROJECT_ID));
  }
}
