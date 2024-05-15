package com.doityourself.workshop.features.project.media.command;

import com.doityourself.workshop.common.command.CommandDTO;
import com.doityourself.workshop.common.constants.ContextConstants;
import com.doityourself.workshop.common.constants.EntityConstants;
import com.doityourself.workshop.database.entities.DiyProjectWebResource;
import com.doityourself.workshop.features.project.media.dao.ProjectMediaDao;
import com.doityourself.workshop.features.project.media.representation.ProjectDetailWebResourceRepresentation;
import com.doityourself.workshop.features.project.media.validation.UpdateProjectWebResourceValidations;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

public class GetProjectWebResourceFromProjectWebResourceRepresentationCommandTest {
  @Test
  public void testProcess() {
    // Initialize
    ProjectMediaDao mockProjectMediaDao = Mockito.mock(ProjectMediaDao.class);

    DiyProjectWebResource diyProjectWebResource = DiyProjectWebResource.builder().id(1L).build();
    ProjectDetailWebResourceRepresentation projectDetailWebResourceRepresentation = ProjectDetailWebResourceRepresentation.builder().id(1L).build();
    CommandDTO commandDTO = CommandDTO.builder().add(ContextConstants.CONTEXT_PROJECT_WEB_RESOURCE, projectDetailWebResourceRepresentation).build();

    GetProjectWebResourceFromProjectWebResourceRepresentationCommand command = new GetProjectWebResourceFromProjectWebResourceRepresentationCommand();
    command.projectMediaDao = mockProjectMediaDao;

    // Define Mocks
    Mockito.when(mockProjectMediaDao.findProjectWebResourceByWebResourceId(1L)).thenReturn(diyProjectWebResource);

    // Execute
    Exception expectedException = null;
    try {
      command.process(commandDTO);
    } catch (Exception exception) {
      expectedException = exception;
    }

    // Verify
    Mockito.verify(mockProjectMediaDao, Mockito.times(1)).findProjectWebResourceByWebResourceId(1L);

    // Assertions
    assert expectedException == null;
    assert commandDTO.get(EntityConstants.ENTITY_DIY_PROJECT_WEB_RESOURCE) == diyProjectWebResource;
  }

  @Test
  public void testPostProcess() {
    // Initialize
    UpdateProjectWebResourceValidations mockUpdateProjectWebResourceValidations = Mockito.mock(UpdateProjectWebResourceValidations.class);

    DiyProjectWebResource diyProjectWebResource = DiyProjectWebResource.builder().id(1L).build();
    CommandDTO commandDTO = CommandDTO.builder().add(EntityConstants.ENTITY_DIY_PROJECT_WEB_RESOURCE, diyProjectWebResource).build();

    GetProjectWebResourceFromProjectWebResourceRepresentationCommand command = new GetProjectWebResourceFromProjectWebResourceRepresentationCommand();
    command.validations = mockUpdateProjectWebResourceValidations;

    // Define Mocks
    Mockito.doNothing().when(mockUpdateProjectWebResourceValidations).validateDiyProjectWebResourceEntity(diyProjectWebResource);

    // Execute
    Exception expectedException = null;
    try {
      command.postProcess(commandDTO);
    } catch (Exception exception) {
      expectedException = exception;
    }

    // Verify
    Mockito.verify(mockUpdateProjectWebResourceValidations, Mockito.times(1)).validateDiyProjectWebResourceEntity(diyProjectWebResource);

    // Assertions
    assert expectedException == null;
  }
}
