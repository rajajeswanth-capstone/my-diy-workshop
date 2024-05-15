package com.doityourself.workshop.features.project.media.command;

import com.doityourself.workshop.common.command.CommandDTO;
import com.doityourself.workshop.common.command.ICommand;
import com.doityourself.workshop.common.constants.ContextConstants;
import com.doityourself.workshop.features.project.media.representation.ProjectDetailWebResourceRepresentation;
import com.doityourself.workshop.features.project.media.validation.UpdateProjectWebResourceValidations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Update Project Web Resource Request Validation Command
 */
@Component
public class UpdateProjectWebResourceRequestValidationCommand implements ICommand<CommandDTO> {
  @Autowired
  UpdateProjectWebResourceValidations validations;

  /**
   * Validate Update Project Image Request
   *
   * @param dto {@link CommandDTO}
   */
  @Override
  public void process(CommandDTO dto) {
    validations.validateUpdateWebResourceRequest((ProjectDetailWebResourceRepresentation) dto.get(ContextConstants.CONTEXT_PROJECT_WEB_RESOURCE));
  }
}
