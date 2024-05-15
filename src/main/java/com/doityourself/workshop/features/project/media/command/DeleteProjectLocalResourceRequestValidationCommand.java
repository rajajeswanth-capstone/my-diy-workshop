package com.doityourself.workshop.features.project.media.command;

import com.doityourself.workshop.common.command.CommandDTO;
import com.doityourself.workshop.common.command.ICommand;
import com.doityourself.workshop.common.constants.ContextConstants;
import com.doityourself.workshop.features.project.media.validation.DeleteProjectLocalResourceValidations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Delete Project Local Resource Request Validation Command
 */
@Component
public class DeleteProjectLocalResourceRequestValidationCommand implements ICommand<CommandDTO> {
  @Autowired
  DeleteProjectLocalResourceValidations validations;

  /**
   * Validate Delete Project Local Resource Request
   *
   * @param dto {@link CommandDTO}
   */
  @Override
  public void process(CommandDTO dto) {
    validations.validateDeleteProjectLocalResourceRequest((Long) dto.get(ContextConstants.CONTEXT_LOCAL_RESOURCE_ID));
  }
}
