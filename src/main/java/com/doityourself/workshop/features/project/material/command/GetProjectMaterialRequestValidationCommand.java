package com.doityourself.workshop.features.project.material.command;

import com.doityourself.workshop.common.command.CommandDTO;
import com.doityourself.workshop.common.command.ICommand;
import com.doityourself.workshop.common.constants.ContextConstants;
import com.doityourself.workshop.features.project.material.validation.GetProjectMaterialValidations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Get Project Material Request Validation Command
 */
@Component
public class GetProjectMaterialRequestValidationCommand implements ICommand<CommandDTO> {
  @Autowired
  GetProjectMaterialValidations validations;

  /**
   * Validate Get Project Material Request
   *
   * @param dto {@link CommandDTO}
   */
  @Override
  public void process(CommandDTO dto) {
    validations.validateGetProjectMaterialRequest((Long) dto.get(ContextConstants.CONTEXT_MATERIAL_ID));
  }
}
