package com.doityourself.workshop.features.project.material.command;

import com.doityourself.workshop.common.command.CommandDTO;
import com.doityourself.workshop.common.constants.ContextConstants;
import com.doityourself.workshop.common.constants.EntityConstants;
import com.doityourself.workshop.database.entities.DiyProject;
import com.doityourself.workshop.features.project.detail.dao.ProjectDetailDao;
import com.doityourself.workshop.features.project.detail.validation.GetProjectDetailValidations;
import com.doityourself.workshop.features.project.material.representation.ProjectDetailRepresentation;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

public class GetProjectMaterialDetailFromProjectDetailRepresentationCommandTest {
  @Test
  public void testProcess() {
    // Initialize
    ProjectDetailDao mockProjectDetailDao = Mockito.mock(ProjectDetailDao.class);

    ProjectDetailRepresentation projectDetailRepresentation = ProjectDetailRepresentation.builder().id(1L).build();
    DiyProject diyProject = DiyProject.builder().id(1L).build();
    CommandDTO commandDTO = CommandDTO.builder().add(ContextConstants.CONTEXT_PROJECT_DETAIL, projectDetailRepresentation).build();

    GetProjectMaterialDetailFromProjectDetailRepresentationCommand command = new GetProjectMaterialDetailFromProjectDetailRepresentationCommand();
    command.projectDetailDao = mockProjectDetailDao;

    // Define Mocks
    Mockito.when(mockProjectDetailDao.findProjectById(1L)).thenReturn(diyProject);

    // Execute
    Exception expectedException = null;
    try {
      command.process(commandDTO);
    } catch (Exception exception) {
      expectedException = exception;
    }

    // Verify
    Mockito.verify(mockProjectDetailDao, Mockito.times(1)).findProjectById(1L);

    // Assertions
    assert expectedException == null;
    assert ((DiyProject) commandDTO.get(EntityConstants.ENTITY_DIY_PROJECT)).getId() == 1L;
  }

  @Test
  public void testPostProcess() {
    // Initialize
    GetProjectDetailValidations mockGetProjectDetailValidations = Mockito.mock(GetProjectDetailValidations.class);

    DiyProject diyProject = DiyProject.builder().id(1L).build();
    CommandDTO commandDTO = CommandDTO.builder().add(EntityConstants.ENTITY_DIY_PROJECT, diyProject).build();

    GetProjectMaterialDetailFromProjectDetailRepresentationCommand command = new GetProjectMaterialDetailFromProjectDetailRepresentationCommand();
    command.validations = mockGetProjectDetailValidations;

    // Define Mocks
    Mockito.doNothing().when(mockGetProjectDetailValidations).validateDiyProjectEntity(diyProject);

    // Execute
    Exception expectedException = null;
    try {
      command.postProcess(commandDTO);
    } catch (Exception exception) {
      expectedException = exception;
    }

    // Verify
    Mockito.verify(mockGetProjectDetailValidations, Mockito.times(1)).validateDiyProjectEntity(diyProject);

    // Assertions
    assert expectedException == null;
  }
}
