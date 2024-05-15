package com.doityourself.workshop.features.project.media.command;

import com.doityourself.workshop.common.command.CommandDTO;
import com.doityourself.workshop.common.constants.ContextConstants;
import com.doityourself.workshop.common.constants.EntityConstants;
import com.doityourself.workshop.database.entities.DiyProjectLocalResource;
import com.doityourself.workshop.features.project.media.dao.ProjectMediaDao;
import com.doityourself.workshop.features.project.media.representation.ProjectDetailLocalResourceRepresentation;
import com.doityourself.workshop.features.project.media.validation.UpdateProjectLocalResourceValidations;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

public class GetProjectLocalResourceFromProjectLocalResourceRepresentationCommandTest {
  @Test
  public void testProcess() {
    // Initialize
    ProjectMediaDao mockProjectMediaDao = Mockito.mock(ProjectMediaDao.class);

    DiyProjectLocalResource diyProjectLocalResource = DiyProjectLocalResource.builder().id(1L).build();
    ProjectDetailLocalResourceRepresentation projectDetailLocalResourceRepresentation = ProjectDetailLocalResourceRepresentation.builder().id(1L).build();
    CommandDTO commandDTO = CommandDTO.builder().add(ContextConstants.CONTEXT_PROJECT_LOCAL_RESOURCE, projectDetailLocalResourceRepresentation).build();

    GetProjectLocalResourceFromProjectLocalResourceRepresentationCommand command = new GetProjectLocalResourceFromProjectLocalResourceRepresentationCommand();
    command.projectMediaDao = mockProjectMediaDao;

    // Define Mocks
    Mockito.when(mockProjectMediaDao.findProjectLocalResourceByLocalResourceId(1L)).thenReturn(diyProjectLocalResource);

    // Execute
    Exception expectedException = null;
    try {
      command.process(commandDTO);
    } catch (Exception exception) {
      expectedException = exception;
    }

    // Verify
    Mockito.verify(mockProjectMediaDao, Mockito.times(1)).findProjectLocalResourceByLocalResourceId(1L);

    // Assertions
    assert expectedException == null;
    assert commandDTO.get(EntityConstants.ENTITY_DIY_PROJECT_LOCAL_RESOURCE) == diyProjectLocalResource;
  }

  @Test
  public void testPostProcess() {
    // Initialize
    UpdateProjectLocalResourceValidations mockUpdateProjectLocalResourceValidations = Mockito.mock(UpdateProjectLocalResourceValidations.class);

    DiyProjectLocalResource diyProjectLocalResource = DiyProjectLocalResource.builder().id(1L).build();
    CommandDTO commandDTO = CommandDTO.builder().add(EntityConstants.ENTITY_DIY_PROJECT_LOCAL_RESOURCE, diyProjectLocalResource).build();

    GetProjectLocalResourceFromProjectLocalResourceRepresentationCommand command = new GetProjectLocalResourceFromProjectLocalResourceRepresentationCommand();
    command.validations = mockUpdateProjectLocalResourceValidations;

    // Define Mocks
    Mockito.doNothing().when(mockUpdateProjectLocalResourceValidations).validateDiyProjectLocalResourceEntity(diyProjectLocalResource);

    // Execute
    Exception expectedException = null;
    try {
      command.postProcess(commandDTO);
    } catch (Exception exception) {
      expectedException = exception;
    }

    // Verify
    Mockito.verify(mockUpdateProjectLocalResourceValidations, Mockito.times(1)).validateDiyProjectLocalResourceEntity(diyProjectLocalResource);

    // Assertions
    assert expectedException == null;
  }
}
