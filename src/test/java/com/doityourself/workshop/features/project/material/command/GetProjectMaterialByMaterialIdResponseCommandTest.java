package com.doityourself.workshop.features.project.material.command;

import com.doityourself.workshop.common.command.CommandDTO;
import com.doityourself.workshop.common.constants.ContextConstants;
import com.doityourself.workshop.common.constants.EntityConstants;
import com.doityourself.workshop.database.entities.DiyProjectMaterial;
import com.doityourself.workshop.features.project.material.representation.ProjectDetailMaterialRepresentation;
import org.junit.jupiter.api.Test;

public class GetProjectMaterialByMaterialIdResponseCommandTest {
  @Test
  public void testProcess() {
    // Initialize
    DiyProjectMaterial diyProjectMaterial = DiyProjectMaterial
        .builder()
        .id(1L).materialDescription("desc").materialSequence(1L).units(1L).pricePerUnit(100D).vendor("vendor")
        .build();
    CommandDTO commandDTO = CommandDTO.builder().add(EntityConstants.ENTITY_DIY_PROJECT_MATERIAL, diyProjectMaterial).build();

    GetProjectMaterialByMaterialIdResponseCommand command = new GetProjectMaterialByMaterialIdResponseCommand();

    // Execute
    Exception expectedException = null;
    try {
      command.process(commandDTO);
    } catch (Exception exception) {
      expectedException = exception;
    }

    // Assertions
    ProjectDetailMaterialRepresentation projectDetailMaterialRepresentation = (ProjectDetailMaterialRepresentation) commandDTO.get(ContextConstants.CONTEXT_PROJECT_MATERIAL);

    assert expectedException == null;
    assert projectDetailMaterialRepresentation.getId() == 1L;
    assert projectDetailMaterialRepresentation.getMaterialDescription().equals("desc");
    assert projectDetailMaterialRepresentation.getMaterialSequence() == 1L;
    assert projectDetailMaterialRepresentation.getUnits() == 1L;
    assert projectDetailMaterialRepresentation.getPricePerUnit() == 100D;
    assert projectDetailMaterialRepresentation.getVendor().equals("vendor");
  }
}
