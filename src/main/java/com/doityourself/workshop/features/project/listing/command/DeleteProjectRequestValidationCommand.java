package com.doityourself.workshop.features.project.listing.command;

import com.doityourself.workshop.common.command.CommandDTO;
import com.doityourself.workshop.common.command.ICommand;
import com.doityourself.workshop.common.constants.ContextConstants;
import com.doityourself.workshop.features.project.listing.validation.DeleteProjectValidations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Delete Project Request Validation Command
 */
@Component
public class DeleteProjectRequestValidationCommand implements ICommand<CommandDTO> {
  @Autowired
  DeleteProjectValidations validations;

  /**
   * Validate Delete Project request
   *
   * @param dto {@link CommandDTO}
   */
  @Override
  public void process(CommandDTO dto) {
    validations.validateDeleteRequest((Long) dto.get(ContextConstants.CONTEXT_PROJECT_ID));
  }
}
