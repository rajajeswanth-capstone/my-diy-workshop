package com.doityourself.workshop.features.project.media.command;

import com.doityourself.workshop.common.command.CommandDTO;
import com.doityourself.workshop.common.constants.ContextConstants;
import com.doityourself.workshop.common.constants.EntityConstants;
import com.doityourself.workshop.database.entities.DiyProjectLocalResource;
import com.doityourself.workshop.features.project.media.dao.ProjectMediaDao;
import com.doityourself.workshop.features.project.media.representation.ProjectDetailLocalResourceRepresentation;
import com.doityourself.workshop.features.project.media.validation.UpdateProjectLocalResourceValidations;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;

public class UpdateProjectLocalResourceCommandTest {
  @Test
  public void testProcess() {
    // Initialize
    ProjectMediaDao mockProjectMediaDao = Mockito.mock(ProjectMediaDao.class);

    ArgumentCaptor<DiyProjectLocalResource> diyProjectLocalResourceCaptor = ArgumentCaptor.forClass(DiyProjectLocalResource.class);

    DiyProjectLocalResource diyProjectLocalResource = DiyProjectLocalResource.builder().id(1L).build();
    ProjectDetailLocalResourceRepresentation projectDetailLocalResourceRepresentation = ProjectDetailLocalResourceRepresentation
        .builder()
        .id(1L).title("title").localResourceSequence(1L).type("type").resourceType("resourceType").description("description")
        .build();
    CommandDTO commandDTO = CommandDTO
        .builder()
        .add(EntityConstants.ENTITY_DIY_PROJECT_LOCAL_RESOURCE, diyProjectLocalResource)
        .add(ContextConstants.CONTEXT_PROJECT_LOCAL_RESOURCE, projectDetailLocalResourceRepresentation)
        .build();

    UpdateProjectLocalResourceCommand command = new UpdateProjectLocalResourceCommand();
    command.projectMediaDao = mockProjectMediaDao;

    // Define Mocks
    Mockito.when(mockProjectMediaDao.saveProjectLocalResource(Mockito.any())).thenReturn(diyProjectLocalResource);

    // Execute
    Exception expectedException = null;
    try {
      command.process(commandDTO);
    } catch (Exception exception) {
      expectedException = exception;
    }

    // Verify
    Mockito.verify(mockProjectMediaDao, Mockito.times(1)).saveProjectLocalResource(diyProjectLocalResourceCaptor.capture());

    // Assertions
    assert expectedException == null;
    assert commandDTO.get(EntityConstants.ENTITY_DIY_PROJECT_LOCAL_RESOURCE) == diyProjectLocalResource;
    assert commandDTO.get(ContextConstants.CONTEXT_PROJECT_LOCAL_RESOURCE_ARTIFACT).equals("resourceType");
    assert diyProjectLocalResourceCaptor.getValue().getId() == 1L;
    assert diyProjectLocalResourceCaptor.getValue().getTitle().equals("title");
    assert diyProjectLocalResourceCaptor.getValue().getResourceType().equals("resourceType");
    assert diyProjectLocalResourceCaptor.getValue().getDescription().equals("description");
  }

  @Test
  public void testPostProcess() {
    // Initialize
    UpdateProjectLocalResourceValidations mockUpdateProjectLocalResourceValidations = Mockito.mock(UpdateProjectLocalResourceValidations.class);

    DiyProjectLocalResource diyProjectLocalResource = DiyProjectLocalResource.builder().id(1L).build();
    CommandDTO commandDTO = CommandDTO.builder().add(EntityConstants.ENTITY_DIY_PROJECT_LOCAL_RESOURCE, diyProjectLocalResource).build();

    UpdateProjectLocalResourceCommand command = new UpdateProjectLocalResourceCommand();
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
