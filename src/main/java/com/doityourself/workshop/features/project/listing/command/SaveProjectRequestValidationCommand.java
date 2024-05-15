package com.doityourself.workshop.features.project.listing.command;

import com.doityourself.workshop.common.command.CommandDTO;
import com.doityourself.workshop.common.command.ICommand;
import com.doityourself.workshop.common.constants.ContextConstants;
import com.doityourself.workshop.features.project.listing.exception.ProjectSaveFailedException;
import com.doityourself.workshop.features.project.listing.representation.ProjectListingRepresentation;
import com.doityourself.workshop.features.project.listing.validation.SaveProjectValidations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Save Project Request validation Command
 */
@Component
public class SaveProjectRequestValidationCommand implements ICommand<CommandDTO> {
  @Autowired
  SaveProjectValidations validations;

  /**
   * Validate Save Project request
   *
   * @param dto {@link CommandDTO}
   */
  @Override
  public void process(CommandDTO dto) throws ProjectSaveFailedException {
    validations.validateSaveRequest((ProjectListingRepresentation) dto.get(ContextConstants.CONTEXT_PROJECT));
  }
}
