package com.doityourself.workshop.features.project.material.command;

import com.doityourself.workshop.common.command.CommandDTO;
import com.doityourself.workshop.common.command.ICommand;
import com.doityourself.workshop.common.constants.ContextConstants;
import com.doityourself.workshop.features.project.material.representation.ProjectDetailMaterialRepresentation;
import com.doityourself.workshop.features.project.material.validation.SaveProjectMaterialValidations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Save Project Material Request Validation Command
 */
@Component
public class SaveProjectMaterialRequestValidationCommand implements ICommand<CommandDTO> {
  @Autowired
  SaveProjectMaterialValidations validations;

  /**
   * Validate Save Project Material Request
   *
   * @param dto {@link CommandDTO}
   */
  @Override
  public void process(CommandDTO dto) {
    validations.validateSaveMaterialRequest((ProjectDetailMaterialRepresentation) dto.get(ContextConstants.CONTEXT_PROJECT_MATERIAL));
  }
}
