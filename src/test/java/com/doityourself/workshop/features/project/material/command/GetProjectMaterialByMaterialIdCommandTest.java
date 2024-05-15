package com.doityourself.workshop.features.project.material.command;

import com.doityourself.workshop.common.command.CommandDTO;
import com.doityourself.workshop.common.constants.ContextConstants;
import com.doityourself.workshop.common.constants.EntityConstants;
import com.doityourself.workshop.database.entities.DiyProjectMaterial;
import com.doityourself.workshop.features.project.material.dao.ProjectMaterialDao;
import com.doityourself.workshop.features.project.material.validation.GetProjectMaterialValidations;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

public class GetProjectMaterialByMaterialIdCommandTest {
  @Test
  public void testProcess() {
    // Initialize
    ProjectMaterialDao mockProjectMaterialDao = Mockito.mock(ProjectMaterialDao.class);

    DiyProjectMaterial responseDiyProjectMaterial = DiyProjectMaterial.builder().id(1L).build();
    CommandDTO commandDTO = CommandDTO.builder().add(ContextConstants.CONTEXT_MATERIAL_ID, 1L).build();

    GetProjectMaterialByMaterialIdCommand command = new GetProjectMaterialByMaterialIdCommand();
    command.projectMaterialDao = mockProjectMaterialDao;

    // Define Mocks
    Mockito.when(mockProjectMaterialDao.findProjectMaterialByMaterialId(1L)).thenReturn(responseDiyProjectMaterial);

    // Execute
    Exception expectedException = null;
    try {
      command.process(commandDTO);
    } catch (Exception exception) {
      expectedException = exception;
    }

    // Verify
    Mockito.verify(mockProjectMaterialDao, Mockito.times(1)).findProjectMaterialByMaterialId(1L);

    // Assertions
    assert expectedException == null;
    assert commandDTO.get(EntityConstants.ENTITY_DIY_PROJECT_MATERIAL) == responseDiyProjectMaterial;
  }

  @Test
  public void testPostProcess() {
    // Initialize
    GetProjectMaterialValidations mockGetProjectMaterialValidations = Mockito.mock(GetProjectMaterialValidations.class);

    DiyProjectMaterial diyProjectMaterial = DiyProjectMaterial.builder().id(1L).build();
    CommandDTO commandDTO = CommandDTO.builder().add(EntityConstants.ENTITY_DIY_PROJECT_MATERIAL, diyProjectMaterial).build();

    GetProjectMaterialByMaterialIdCommand command = new GetProjectMaterialByMaterialIdCommand();
    command.validations = mockGetProjectMaterialValidations;

    // Define Mocks
    Mockito.doNothing().when(mockGetProjectMaterialValidations).validateDiyProjectMaterialEntity(diyProjectMaterial);

    // Execute
    Exception expectedException = null;
    try {
      command.postProcess(commandDTO);
    } catch (Exception exception) {
      expectedException = exception;
    }

    // Verify
    Mockito.verify(mockGetProjectMaterialValidations, Mockito.times(1)).validateDiyProjectMaterialEntity(diyProjectMaterial);

    // Assertions
    assert expectedException == null;
  }
}
