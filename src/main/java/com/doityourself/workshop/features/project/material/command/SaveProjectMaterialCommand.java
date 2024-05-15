package com.doityourself.workshop.features.project.material.command;

import com.doityourself.workshop.common.command.CommandDTO;
import com.doityourself.workshop.common.command.ICommand;
import com.doityourself.workshop.common.constants.ContextConstants;
import com.doityourself.workshop.common.constants.EntityConstants;
import com.doityourself.workshop.database.entities.DiyProject;
import com.doityourself.workshop.database.entities.DiyProjectMaterial;
import com.doityourself.workshop.features.project.material.dao.ProjectMaterialDao;
import com.doityourself.workshop.features.project.material.representation.ProjectDetailMaterialRepresentation;
import com.doityourself.workshop.features.project.material.validation.SaveProjectMaterialValidations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Save Project Instruction Command
 */
@Component
public class SaveProjectMaterialCommand implements ICommand<CommandDTO> {
  @Autowired
  ProjectMaterialDao projectMaterialDao;

  @Autowired
  SaveProjectMaterialValidations validations;

  /**
   * Save Project Instruction to database
   *
   * @param dto {@link CommandDTO}
   */
  @Override
  public void process(CommandDTO dto) {
    DiyProject diyProject = (DiyProject) dto.get(EntityConstants.ENTITY_DIY_PROJECT);
    ProjectDetailMaterialRepresentation projectDetailMaterialRepresentation = (ProjectDetailMaterialRepresentation) dto.get(ContextConstants.CONTEXT_PROJECT_MATERIAL);
    DiyProjectMaterial diyProjectMaterial;

    if (projectDetailMaterialRepresentation.getId() != null) {
      diyProjectMaterial = projectMaterialDao.findProjectMaterialByMaterialId(projectDetailMaterialRepresentation.getId());
    } else {
      diyProjectMaterial = DiyProjectMaterial.builder().build();
    }

    DiyProjectMaterial updatedDiyProjectMaterial = projectMaterialDao.saveProjectMaterial(
        diyProjectMaterial.toBuilder()
            .diyProject(diyProject)
            .materialDescription(projectDetailMaterialRepresentation.getMaterialDescription())
            .materialSequence(projectDetailMaterialRepresentation.getMaterialSequence())
            .units(projectDetailMaterialRepresentation.getUnits())
            .vendor(projectDetailMaterialRepresentation.getVendor())
            .pricePerUnit(projectDetailMaterialRepresentation.getPricePerUnit())
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
