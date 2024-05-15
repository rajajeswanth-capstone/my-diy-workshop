package com.doityourself.workshop.features.project.material.command;

import com.doityourself.workshop.common.command.CommandDTO;
import com.doityourself.workshop.common.command.ICommand;
import com.doityourself.workshop.common.constants.ContextConstants;
import com.doityourself.workshop.features.project.material.validation.DeleteProjectMaterialValidations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Delete Project Material Request Validation Command
 */
@Component
public class DeleteProjectMaterialRequestValidationCommand implements ICommand<CommandDTO> {
  @Autowired
  DeleteProjectMaterialValidations validations;

  /**
   * Validate Delete Project Material Request
   *
   * @param dto {@link CommandDTO}
   */
  @Override
  public void process(CommandDTO dto) {
    validations.validateDeleteProjectMaterialRequest((Long) dto.get(ContextConstants.CONTEXT_MATERIAL_ID));
  }
}
