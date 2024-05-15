package com.doityourself.workshop.features.project.material.command;

import com.doityourself.workshop.common.command.CommandDTO;
import com.doityourself.workshop.common.command.ICommand;
import com.doityourself.workshop.common.constants.ContextConstants;
import com.doityourself.workshop.common.constants.EntityConstants;
import com.doityourself.workshop.database.entities.DiyProject;
import com.doityourself.workshop.database.entities.DiyProjectMaterial;
import com.doityourself.workshop.features.project.material.helper.ProjectMaterialServiceHelper;
import com.doityourself.workshop.features.project.material.representation.ProjectDetailMaterialRepresentation;
import com.doityourself.workshop.features.project.material.representation.ProjectDetailRepresentation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Get Project Materials Response Command
 */
@Component
public class GetProjectMaterialsResponseCommand implements ICommand<CommandDTO> {
  @Autowired
  ProjectMaterialServiceHelper projectMaterialServiceHelper;

  /**
   * Process get project materials response. Build response object
   *
   * @param dto {@link CommandDTO}
   */
  @Override
  @SuppressWarnings("unchecked")
  public void process(CommandDTO dto) {
    DiyProject diyProject = (DiyProject) dto.get(EntityConstants.ENTITY_DIY_PROJECT);
    List<DiyProjectMaterial> diyProjectMaterials = (List<DiyProjectMaterial>) dto.get(EntityConstants.ENTITY_DIY_PROJECT_MATERIALS);
    List<ProjectDetailMaterialRepresentation> materialRepresentations = projectMaterialServiceHelper.buildMaterials(diyProjectMaterials);
    Double totalProjectCost = projectMaterialServiceHelper.calculateTotalProjectCost(diyProjectMaterials);
    Double overBudgetAmount = projectMaterialServiceHelper.overBudgetAmount(totalProjectCost, diyProject.getBudget());
    List<String> vendors = (List<String>) dto.get(ContextConstants.CONTEXT_VENDORS);

    ProjectDetailRepresentation projectDetail = ProjectDetailRepresentation.builder()
        .id(diyProject.getId())
        .title(diyProject.getTitle())
        .description(diyProject.getDescription())
        .budget(diyProject.getBudget())
        .materials(materialRepresentations)
        .totalProjectCost(totalProjectCost)
        .overBudgetAmount(overBudgetAmount)
        .overBudget(overBudgetAmount > 0)
        .vendors(vendors)
        .build();

    dto.add(ContextConstants.CONTEXT_PROJECT_DETAIL, projectDetail);
  }
}
