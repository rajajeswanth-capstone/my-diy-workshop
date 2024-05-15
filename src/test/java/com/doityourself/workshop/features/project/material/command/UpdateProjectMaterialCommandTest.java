package com.doityourself.workshop.features.project.material.command;

import com.doityourself.workshop.common.command.CommandDTO;
import com.doityourself.workshop.common.constants.ContextConstants;
import com.doityourself.workshop.common.constants.EntityConstants;
import com.doityourself.workshop.database.entities.DiyProjectMaterial;
import com.doityourself.workshop.features.project.material.dao.ProjectMaterialDao;
import com.doityourself.workshop.features.project.material.representation.ProjectDetailMaterialRepresentation;
import com.doityourself.workshop.features.project.material.validation.UpdateProjectMaterialValidations;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;

public class UpdateProjectMaterialCommandTest {
  @Test
  public void testProcess() {
    // Initialize
    ProjectMaterialDao mockProjectMaterialDao = Mockito.mock(ProjectMaterialDao.class);

    ArgumentCaptor<DiyProjectMaterial> diyProjectMaterialCaptor = ArgumentCaptor.forClass(DiyProjectMaterial.class);

    DiyProjectMaterial diyProjectMaterial = DiyProjectMaterial.builder().id(1L).build();
    ProjectDetailMaterialRepresentation projectDetailMaterialRepresentation = ProjectDetailMaterialRepresentation
        .builder()
        .id(1L).materialDescription("description").materialSequence(1L).units(1L).pricePerUnit(10D).vendor("vendor")
        .build();

    CommandDTO commandDTO = CommandDTO
        .builder()
        .add(EntityConstants.ENTITY_DIY_PROJECT_MATERIAL, diyProjectMaterial)
        .add(ContextConstants.CONTEXT_PROJECT_MATERIAL, projectDetailMaterialRepresentation)
        .build();

    UpdateProjectMaterialCommand command = new UpdateProjectMaterialCommand();
    command.projectMaterialDao = mockProjectMaterialDao;

    // Define Mocks
    Mockito.when(mockProjectMaterialDao.saveProjectMaterial(Mockito.any())).thenReturn(diyProjectMaterial);

    // Execute
    Exception expectedException = null;
    try {
      command.process(commandDTO);
    } catch (Exception exception) {
      expectedException = exception;
    }

    // Verify
    Mockito.verify(mockProjectMaterialDao, Mockito.times(1)).saveProjectMaterial(diyProjectMaterialCaptor.capture());

    // Assertions
    assert expectedException == null;
    DiyProjectMaterial resultDiyProjectMaterial = diyProjectMaterialCaptor.getValue();
    assert resultDiyProjectMaterial.getMaterialDescription().equals("description");
    assert resultDiyProjectMaterial.getMaterialSequence() == 1L;
    assert resultDiyProjectMaterial.getUnits() == 1L;
    assert resultDiyProjectMaterial.getPricePerUnit() == 10D;
    assert resultDiyProjectMaterial.getVendor().equals("vendor");
  }

  @Test
  public void testPostProcess() {
    // Initialize
    UpdateProjectMaterialValidations mockUpdateProjectMaterialValidations = Mockito.mock(UpdateProjectMaterialValidations.class);

    DiyProjectMaterial diyProjectMaterial = DiyProjectMaterial.builder().id(1L).build();
    CommandDTO commandDTO = CommandDTO
        .builder()
        .add(EntityConstants.ENTITY_DIY_PROJECT_MATERIAL, diyProjectMaterial)
        .build();

    UpdateProjectMaterialCommand command = new UpdateProjectMaterialCommand();
    command.validations = mockUpdateProjectMaterialValidations;

    // Define Mocks
    Mockito.doNothing().when(mockUpdateProjectMaterialValidations).validateDiyProjectMaterialEntity(diyProjectMaterial);

    // Execute
    Exception expectedException = null;
    try {
      command.postProcess(commandDTO);
    } catch (Exception exception) {
      expectedException = exception;
    }

    // Verify
    Mockito.verify(mockUpdateProjectMaterialValidations, Mockito.times(1)).validateDiyProjectMaterialEntity(diyProjectMaterial);

    // Assertions
    assert expectedException == null;
  }
}
