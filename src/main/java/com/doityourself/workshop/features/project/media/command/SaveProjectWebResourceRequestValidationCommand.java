package com.doityourself.workshop.features.project.media.command;

import com.doityourself.workshop.common.command.CommandDTO;
import com.doityourself.workshop.common.command.ICommand;
import com.doityourself.workshop.common.constants.ContextConstants;
import com.doityourself.workshop.features.project.media.representation.ProjectDetailWebResourceRepresentation;
import com.doityourself.workshop.features.project.media.validation.SaveProjectWebResourceValidations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Save Project Web Resource Request Validation Command
 */
@Component
public class SaveProjectWebResourceRequestValidationCommand implements ICommand<CommandDTO> {
  @Autowired
  SaveProjectWebResourceValidations validations;

  /**
   * Validate Save Project Image Request
   *
   * @param dto {@link CommandDTO}
   */
  @Override
  public void process(CommandDTO dto) {
    validations.validateSaveWebResourceRequest((ProjectDetailWebResourceRepresentation) dto.get(ContextConstants.CONTEXT_PROJECT_WEB_RESOURCE));
  }
}
