package com.doityourself.workshop.features.project.media.command;

import com.doityourself.workshop.common.command.CommandDTO;
import com.doityourself.workshop.common.command.ICommand;
import com.doityourself.workshop.common.constants.ContextConstants;
import com.doityourself.workshop.features.project.media.representation.ProjectDetailLocalResourceRepresentation;
import com.doityourself.workshop.features.project.media.validation.UpdateProjectLocalResourceValidations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Update Project Local Resource Request Validation Command
 */
@Component
public class UpdateProjectLocalResourceRequestValidationCommand implements ICommand<CommandDTO> {
  @Autowired
  UpdateProjectLocalResourceValidations validations;

  /**
   * Validate Update Project Image Request
   *
   * @param dto {@link CommandDTO}
   */
  @Override
  public void process(CommandDTO dto) {
    validations.validateUpdateLocalResourceRequest((ProjectDetailLocalResourceRepresentation) dto.get(ContextConstants.CONTEXT_PROJECT_LOCAL_RESOURCE));
  }
}
