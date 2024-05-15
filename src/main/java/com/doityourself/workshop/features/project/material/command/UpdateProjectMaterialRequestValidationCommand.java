package com.doityourself.workshop.features.project.material.command;

import com.doityourself.workshop.common.command.CommandDTO;
import com.doityourself.workshop.common.command.ICommand;
import com.doityourself.workshop.common.constants.ContextConstants;
import com.doityourself.workshop.features.project.material.representation.ProjectDetailMaterialRepresentation;
import com.doityourself.workshop.features.project.material.validation.UpdateProjectMaterialValidations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Update Project Material Request Validation Command
 */
@Component
public class UpdateProjectMaterialRequestValidationCommand implements ICommand<CommandDTO> {
  @Autowired
  UpdateProjectMaterialValidations validations;

  /**
   * Validate Update Project Material Request
   *
   * @param dto {@link CommandDTO}
   */
  @Override
  public void process(CommandDTO dto) {
    validations.validateUpdateMaterialRequest((ProjectDetailMaterialRepresentation) dto.get(ContextConstants.CONTEXT_PROJECT_MATERIAL));
  }
}
