package com.doityourself.workshop.features.project.material.command;

import com.doityourself.workshop.common.command.CommandDTO;
import com.doityourself.workshop.common.command.ICommand;
import com.doityourself.workshop.common.constants.ContextConstants;
import com.doityourself.workshop.common.constants.EntityConstants;
import com.doityourself.workshop.database.entities.DiyProjectMaterial;
import com.doityourself.workshop.features.project.material.dao.ProjectMaterialDao;
import com.doityourself.workshop.features.project.material.validation.GetProjectMaterialValidations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Get Project Material By Id Command
 */
@Component
public class GetProjectMaterialByMaterialIdCommand implements ICommand<CommandDTO> {
  @Autowired
  ProjectMaterialDao projectMaterialDao;

  @Autowired
  GetProjectMaterialValidations validations;

  /**
   * Get Project material by id from database
   *
   * @param dto {@link CommandDTO}
   */
  @Override
  public void process(CommandDTO dto) {
    DiyProjectMaterial diyProjectMaterial = projectMaterialDao.findProjectMaterialByMaterialId((Long) dto.get(ContextConstants.CONTEXT_MATERIAL_ID));

    dto.add(EntityConstants.ENTITY_DIY_PROJECT_MATERIAL, diyProjectMaterial);
  }

  /**
   * Post-process get project material operation. Validate entity
   *
   * @param dto {@link CommandDTO}
   */
  @Override
  public void postProcess(CommandDTO dto) {
    validations.validateDiyProjectMaterialEntity((DiyProjectMaterial) dto.get(EntityConstants.ENTITY_DIY_PROJECT_MATERIAL));
  }
}
