package com.doityourself.workshop.features.project.media.command;

import com.doityourself.workshop.common.command.CommandDTO;
import com.doityourself.workshop.common.command.ICommand;
import com.doityourself.workshop.common.constants.ContextConstants;
import com.doityourself.workshop.features.project.media.validation.DeleteProjectWebResourceValidations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Delete Project Web Resource Request Validation Command
 */
@Component
public class DeleteProjectWebResourceRequestValidationCommand implements ICommand<CommandDTO> {
  @Autowired
  DeleteProjectWebResourceValidations validations;

  /**
   * Validate Delete Project Web Resource Request
   *
   * @param dto {@link CommandDTO}
   */
  @Override
  public void process(CommandDTO dto) {
    validations.validateDeleteProjectWebResourceRequest((Long) dto.get(ContextConstants.CONTEXT_WEB_RESOURCE_ID));
  }
}
