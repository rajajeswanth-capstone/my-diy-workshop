package com.doityourself.workshop.features.project.material.command;

import com.doityourself.workshop.common.command.CommandDTO;
import com.doityourself.workshop.common.constants.ContextConstants;
import com.doityourself.workshop.common.constants.EntityConstants;
import com.doityourself.workshop.database.entities.DiyProjectMaterial;
import com.doityourself.workshop.features.project.material.dao.ProjectMaterialDao;
import com.doityourself.workshop.features.project.material.representation.ProjectDetailMaterialRepresentation;
import com.doityourself.workshop.features.project.material.validation.GetProjectMaterialValidations;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

public class GetProjectMaterialFromProjectMaterialRepresentationCommandTest {
  @Test
  public void testProcess() {
    // Initialize
    ProjectMaterialDao mockProjectMaterialDao = Mockito.mock(ProjectMaterialDao.class);

    ProjectDetailMaterialRepresentation projectDetailMaterialRepresentation = ProjectDetailMaterialRepresentation.builder().id(1L).build();
    DiyProjectMaterial diyProjectMaterial = DiyProjectMaterial.builder().id(1L).build();
    CommandDTO commandDTO = CommandDTO.builder().add(ContextConstants.CONTEXT_PROJECT_MATERIAL, projectDetailMaterialRepresentation).build();

    GetProjectMaterialFromProjectMaterialRepresentationCommand command = new GetProjectMaterialFromProjectMaterialRepresentationCommand();
    command.projectMaterialDao = mockProjectMaterialDao;

    // Define Mocks
    Mockito.when(mockProjectMaterialDao.findProjectMaterialByMaterialId(1L)).thenReturn(diyProjectMaterial);

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
    assert ((DiyProjectMaterial) commandDTO.get(EntityConstants.ENTITY_DIY_PROJECT_MATERIAL)).getId() == 1L;
  }

  @Test
  public void testPostProcess() {
    // Initialize
    GetProjectMaterialValidations mockGetProjectMaterialValidations = Mockito.mock(GetProjectMaterialValidations.class);

    DiyProjectMaterial diyProjectMaterial = DiyProjectMaterial.builder().id(1L).build();
    CommandDTO commandDTO = CommandDTO.builder().add(EntityConstants.ENTITY_DIY_PROJECT_MATERIAL, diyProjectMaterial).build();

    GetProjectMaterialFromProjectMaterialRepresentationCommand command = new GetProjectMaterialFromProjectMaterialRepresentationCommand();
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
