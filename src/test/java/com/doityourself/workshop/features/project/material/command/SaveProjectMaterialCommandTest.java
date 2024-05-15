package com.doityourself.workshop.features.project.material.command;

import com.doityourself.workshop.common.command.CommandDTO;
import com.doityourself.workshop.common.constants.ContextConstants;
import com.doityourself.workshop.common.constants.EntityConstants;
import com.doityourself.workshop.database.entities.DiyProject;
import com.doityourself.workshop.database.entities.DiyProjectMaterial;
import com.doityourself.workshop.features.project.material.dao.ProjectMaterialDao;
import com.doityourself.workshop.features.project.material.representation.ProjectDetailMaterialRepresentation;
import com.doityourself.workshop.features.project.material.validation.SaveProjectMaterialValidations;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;

public class SaveProjectMaterialCommandTest {
  @Test
  public void testProcess() {
    // Initialize
    ProjectMaterialDao mockProjectMaterialDao = Mockito.mock(ProjectMaterialDao.class);

    ArgumentCaptor<DiyProjectMaterial> diyProjectMaterialCaptor = ArgumentCaptor.forClass(DiyProjectMaterial.class);

    DiyProject diyProject = DiyProject.builder().id(1L).build();
    DiyProjectMaterial diyProjectMaterial = DiyProjectMaterial.builder().id(1L).build();
    ProjectDetailMaterialRepresentation projectDetailMaterialRepresentation = ProjectDetailMaterialRepresentation
        .builder()
        .id(1L).materialDescription("description").materialSequence(1L).units(1L).vendor("vendor").pricePerUnit(10D)
        .build();

    CommandDTO commandDTO = CommandDTO
        .builder()
        .add(EntityConstants.ENTITY_DIY_PROJECT, diyProject)
        .add(ContextConstants.CONTEXT_PROJECT_MATERIAL, projectDetailMaterialRepresentation)
        .build();

    SaveProjectMaterialCommand command = new SaveProjectMaterialCommand();
    command.projectMaterialDao = mockProjectMaterialDao;

    // Define Mocks
    Mockito.when(mockProjectMaterialDao.findProjectMaterialByMaterialId(1L)).thenReturn(diyProjectMaterial);
    Mockito.when(mockProjectMaterialDao.saveProjectMaterial(Mockito.any())).thenReturn(diyProjectMaterial);

    // Execute
    Exception expectedException = null;
    try {
      command.process(commandDTO);
    } catch (Exception exception) {
      expectedException = exception;
    }

    // Verify
    Mockito.verify(mockProjectMaterialDao, Mockito.times(1)).findProjectMaterialByMaterialId(1L);
    Mockito.verify(mockProjectMaterialDao, Mockito.times(1)).saveProjectMaterial(diyProjectMaterialCaptor.capture());

    // Assertions
    assert expectedException == null;
    DiyProjectMaterial resultDiyProjectMaterial = diyProjectMaterialCaptor.getValue();
    assert resultDiyProjectMaterial.getDiyProject() == diyProject;
    assert resultDiyProjectMaterial.getMaterialDescription().equals("description");
    assert resultDiyProjectMaterial.getMaterialSequence() == 1L;
    assert resultDiyProjectMaterial.getUnits() == 1L;
    assert resultDiyProjectMaterial.getVendor().equals("vendor");
    assert resultDiyProjectMaterial.getPricePerUnit() == 10D;
  }

  @Test
  public void testProcessNewMaterial() {
    // Initialize
    ProjectMaterialDao mockProjectMaterialDao = Mockito.mock(ProjectMaterialDao.class);

    ArgumentCaptor<DiyProjectMaterial> diyProjectMaterialCaptor = ArgumentCaptor.forClass(DiyProjectMaterial.class);

    DiyProject diyProject = DiyProject.builder().id(1L).build();
    DiyProjectMaterial diyProjectMaterial = DiyProjectMaterial.builder().id(1L).build();
    ProjectDetailMaterialRepresentation projectDetailMaterialRepresentation = ProjectDetailMaterialRepresentation
        .builder()
        .materialDescription("description").materialSequence(1L).units(1L).vendor("vendor").pricePerUnit(10D)
        .build();

    CommandDTO commandDTO = CommandDTO
        .builder()
        .add(EntityConstants.ENTITY_DIY_PROJECT, diyProject)
        .add(ContextConstants.CONTEXT_PROJECT_MATERIAL, projectDetailMaterialRepresentation)
        .build();

    SaveProjectMaterialCommand command = new SaveProjectMaterialCommand();
    command.projectMaterialDao = mockProjectMaterialDao;

    // Define Mocks
    Mockito.when(mockProjectMaterialDao.findProjectMaterialByMaterialId(1L)).thenReturn(diyProjectMaterial);
    Mockito.when(mockProjectMaterialDao.saveProjectMaterial(Mockito.any())).thenReturn(diyProjectMaterial);

    // Execute
    Exception expectedException = null;
    try {
      command.process(commandDTO);
    } catch (Exception exception) {
      expectedException = exception;
    }

    // Verify
    Mockito.verify(mockProjectMaterialDao, Mockito.times(0)).findProjectMaterialByMaterialId(1L);
    Mockito.verify(mockProjectMaterialDao, Mockito.times(1)).saveProjectMaterial(diyProjectMaterialCaptor.capture());

    // Assertions
    assert expectedException == null;
    DiyProjectMaterial resultDiyProjectMaterial = diyProjectMaterialCaptor.getValue();
    assert resultDiyProjectMaterial.getDiyProject() == diyProject;
    assert resultDiyProjectMaterial.getMaterialDescription().equals("description");
    assert resultDiyProjectMaterial.getMaterialSequence() == 1L;
    assert resultDiyProjectMaterial.getUnits() == 1L;
    assert resultDiyProjectMaterial.getVendor().equals("vendor");
    assert resultDiyProjectMaterial.getPricePerUnit() == 10D;
  }

  @Test
  public void testPostProcess() {
    // Initialize
    SaveProjectMaterialValidations mockSaveProjectMaterialValidations = Mockito.mock(SaveProjectMaterialValidations.class);

    DiyProjectMaterial diyProjectMaterial = DiyProjectMaterial.builder().id(1L).build();

    CommandDTO commandDTO = CommandDTO
        .builder()
        .add(EntityConstants.ENTITY_DIY_PROJECT_MATERIAL, diyProjectMaterial)
        .build();

    SaveProjectMaterialCommand command = new SaveProjectMaterialCommand();
    command.validations = mockSaveProjectMaterialValidations;

    // Define Mocks
    Mockito.doNothing().when(mockSaveProjectMaterialValidations).validateDiyProjectMaterialEntity(diyProjectMaterial);

    // Execute
    Exception expectedException = null;
    try {
      command.postProcess(commandDTO);
    } catch (Exception exception) {
      expectedException = exception;
    }

    // Verify
    Mockito.verify(mockSaveProjectMaterialValidations, Mockito.times(1)).validateDiyProjectMaterialEntity(diyProjectMaterial);

    // Assertions
    assert expectedException == null;
  }
}
