package com.doityourself.workshop.features.project.media.command;

import com.doityourself.workshop.common.command.CommandDTO;
import com.doityourself.workshop.common.command.ICommand;
import com.doityourself.workshop.common.constants.ContextConstants;
import com.doityourself.workshop.features.project.media.validation.GetProjectLocalResourceValidations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Get Project Local Resource Request Validation Command
 */
@Component
public class GetProjectLocalResourceRequestValidationCommand implements ICommand<CommandDTO> {
  @Autowired
  GetProjectLocalResourceValidations validations;

  /**
   * Validate Get Project Image Request
   *
   * @param dto {@link CommandDTO}
   */
  @Override
  public void process(CommandDTO dto) {
    validations.validateGetProjectLocalResourceRequest((Long) dto.get(ContextConstants.CONTEXT_LOCAL_RESOURCE_ID));
  }
}
