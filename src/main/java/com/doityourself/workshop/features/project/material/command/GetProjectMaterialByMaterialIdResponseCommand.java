package com.doityourself.workshop.features.project.material.command;

import com.doityourself.workshop.common.command.CommandDTO;
import com.doityourself.workshop.common.command.ICommand;
import com.doityourself.workshop.common.constants.ContextConstants;
import com.doityourself.workshop.common.constants.EntityConstants;
import com.doityourself.workshop.database.entities.DiyProjectMaterial;
import com.doityourself.workshop.features.project.material.representation.ProjectDetailMaterialRepresentation;
import org.springframework.stereotype.Component;

/**
 * Get Project Material By Id Response Command
 */
@Component
public class GetProjectMaterialByMaterialIdResponseCommand implements ICommand<CommandDTO> {
  /**
   * Build Project Material by id response
   *
   * @param dto {@link CommandDTO}
   */
  @Override
  public void process(CommandDTO dto) {
    DiyProjectMaterial diyProjectMaterial = (DiyProjectMaterial) dto.get(EntityConstants.ENTITY_DIY_PROJECT_MATERIAL);
    ProjectDetailMaterialRepresentation projectDetailMaterialRepresentation = ProjectDetailMaterialRepresentation.builder()
        .id(diyProjectMaterial.getId())
        .materialDescription(diyProjectMaterial.getMaterialDescription())
        .materialSequence(diyProjectMaterial.getMaterialSequence())
        .units(diyProjectMaterial.getUnits())
        .pricePerUnit(diyProjectMaterial.getPricePerUnit())
        .vendor(diyProjectMaterial.getVendor())
        .build();

    dto.add(ContextConstants.CONTEXT_PROJECT_MATERIAL, projectDetailMaterialRepresentation);
  }
}
