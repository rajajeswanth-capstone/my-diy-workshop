package com.doityourself.workshop.features.project.media.command;

import com.doityourself.workshop.common.command.CommandDTO;
import com.doityourself.workshop.common.command.ICommand;
import com.doityourself.workshop.common.constants.ContextConstants;
import com.doityourself.workshop.features.project.media.representation.ProjectDetailLocalResourceRepresentation;
import com.doityourself.workshop.features.project.media.validation.SaveProjectLocalResourceValidations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Save Project Local Resource Request Validation Command
 */
@Component
public class SaveProjectLocalResourceRequestValidationCommand implements ICommand<CommandDTO> {
  @Autowired
  SaveProjectLocalResourceValidations validations;

  /**
   * Validate Save Project Image Request
   *
   * @param dto {@link CommandDTO}
   */
  @Override
  public void process(CommandDTO dto) {
    validations.validateSaveLocalResourceRequest((ProjectDetailLocalResourceRepresentation) dto.get(ContextConstants.CONTEXT_PROJECT_LOCAL_RESOURCE));
  }
}
