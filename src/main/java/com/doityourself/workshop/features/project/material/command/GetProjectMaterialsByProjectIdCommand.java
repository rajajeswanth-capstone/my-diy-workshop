package com.doityourself.workshop.features.project.material.command;

import com.doityourself.workshop.common.command.CommandDTO;
import com.doityourself.workshop.common.command.ICommand;
import com.doityourself.workshop.common.constants.ContextConstants;
import com.doityourself.workshop.common.constants.EntityConstants;
import com.doityourself.workshop.database.entities.DiyProjectMaterial;
import com.doityourself.workshop.features.project.material.dao.ProjectMaterialDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Get Project Materials Command
 */
@Component
public class GetProjectMaterialsByProjectIdCommand implements ICommand<CommandDTO> {
  @Autowired
  ProjectMaterialDao projectMaterialDao;

  /**
   * Get Project Materials from database
   *
   * @param dto {@link CommandDTO}
   */
  @Override
  public void process(CommandDTO dto) {
    List<DiyProjectMaterial> diyProjectMaterials = projectMaterialDao.findProjectMaterialsByProjectId((Long) dto.get(ContextConstants.CONTEXT_PROJECT_ID));
    List<String> vendors = projectMaterialDao.findDistinctProjectVendorsByProjectId((Long) dto.get(ContextConstants.CONTEXT_PROJECT_ID));

    dto.add(EntityConstants.ENTITY_DIY_PROJECT_MATERIALS, diyProjectMaterials);
    dto.add(ContextConstants.CONTEXT_VENDORS, Optional.ofNullable(vendors).orElse(new ArrayList<>()));
  }
}
