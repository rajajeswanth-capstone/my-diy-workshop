package com.doityourself.workshop.features.project.material.command;

import com.doityourself.workshop.common.command.CommandDTO;
import com.doityourself.workshop.common.command.ICommand;
import com.doityourself.workshop.common.constants.ContextConstants;
import com.doityourself.workshop.common.constants.EntityConstants;
import com.doityourself.workshop.database.entities.DiyProjectMaterial;
import com.doityourself.workshop.features.project.material.dao.ProjectMaterialDao;
import com.doityourself.workshop.features.project.material.representation.ProjectDetailMaterialRepresentation;
import com.doityourself.workshop.features.project.material.validation.UpdateProjectMaterialValidations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Update Project Material Command
 */
@Component
public class UpdateProjectMaterialCommand implements ICommand<CommandDTO> {
  @Autowired
  ProjectMaterialDao projectMaterialDao;

  @Autowired
  UpdateProjectMaterialValidations validations;

  /**
   * Update Project Material to database
   *
   * @param dto {@link CommandDTO}
   */
  @Override
  public void process(CommandDTO dto) {
    DiyProjectMaterial diyProjectMaterial = (DiyProjectMaterial) dto.get(EntityConstants.ENTITY_DIY_PROJECT_MATERIAL);
    ProjectDetailMaterialRepresentation projectDetailMaterialRepresentation = (ProjectDetailMaterialRepresentation) dto.get(ContextConstants.CONTEXT_PROJECT_MATERIAL);

    DiyProjectMaterial updatedDiyProjectMaterial = projectMaterialDao.saveProjectMaterial(
        diyProjectMaterial.toBuilder()
            .materialDescription(projectDetailMaterialRepresentation.getMaterialDescription())
            .materialSequence(projectDetailMaterialRepresentation.getMaterialSequence())
            .units(projectDetailMaterialRepresentation.getUnits())
            .pricePerUnit(projectDetailMaterialRepresentation.getPricePerUnit())
            .vendor(projectDetailMaterialRepresentation.getVendor())
            .build()
    );

    dto.add(EntityConstants.ENTITY_DIY_PROJECT_MATERIAL, updatedDiyProjectMaterial);
  }

  /**
   * Post-process save project material. Validate entity
   *
   * @param dto {@link CommandDTO}
   */
  @Override
  public void postProcess(CommandDTO dto) {
    DiyProjectMaterial diyProjectMaterial = (DiyProjectMaterial) dto.get(EntityConstants.ENTITY_DIY_PROJECT_MATERIAL);

    validations.validateDiyProjectMaterialEntity(diyProjectMaterial);
  }
}
