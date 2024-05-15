package com.doityourself.workshop.features.project.media.command;

import com.doityourself.workshop.common.command.CommandDTO;
import com.doityourself.workshop.common.constants.ContextConstants;
import com.doityourself.workshop.common.constants.EntityConstants;
import com.doityourself.workshop.database.entities.DiyProject;
import com.doityourself.workshop.database.entities.DiyProjectWebResource;
import com.doityourself.workshop.features.project.media.dao.ProjectMediaDao;
import com.doityourself.workshop.features.project.media.representation.ProjectDetailWebResourceRepresentation;
import com.doityourself.workshop.features.project.media.validation.SaveProjectWebResourceValidations;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;

public class SaveProjectWebResourceCommandTest {
  @Test
  public void testProcess() {
    // Initialize
    ProjectMediaDao mockProjectMediaDao = Mockito.mock(ProjectMediaDao.class);

    ArgumentCaptor<DiyProjectWebResource> DiyProjectWebResourceCaptor = ArgumentCaptor.forClass(DiyProjectWebResource.class);

    DiyProjectWebResource diyProjectWebResource = DiyProjectWebResource.builder().id(1L).build();
    DiyProject diyProject = DiyProject.builder().id(1L).build();
    ProjectDetailWebResourceRepresentation projectDetailWebResourceRepresentation = ProjectDetailWebResourceRepresentation
        .builder()
        .id(1L).title("title").webResourceSequence(1L).type("type").resourceType("resourceType").description("description").link("link")
        .build();
    CommandDTO commandDTO = CommandDTO
        .builder()
        .add(EntityConstants.ENTITY_DIY_PROJECT, diyProject)
        .add(ContextConstants.CONTEXT_PROJECT_WEB_RESOURCE, projectDetailWebResourceRepresentation)
        .build();

    SaveProjectWebResourceCommand command = new SaveProjectWebResourceCommand();
    command.projectMediaDao = mockProjectMediaDao;

    // Define Mocks
    Mockito.when(mockProjectMediaDao.findProjectWebResourceByWebResourceId(1L)).thenReturn(diyProjectWebResource);
    Mockito.when(mockProjectMediaDao.saveProjectWebResource(Mockito.any())).thenReturn(diyProjectWebResource);

    // Execute
    Exception expectedException = null;
    try {
      command.process(commandDTO);
    } catch (Exception exception) {
      expectedException = exception;
    }

    // Verify
    Mockito.verify(mockProjectMediaDao, Mockito.times(1)).findProjectWebResourceByWebResourceId(1L);
    Mockito.verify(mockProjectMediaDao, Mockito.times(1)).saveProjectWebResource(DiyProjectWebResourceCaptor.capture());

    // Assertions
    assert expectedException == null;
    assert commandDTO.get(EntityConstants.ENTITY_DIY_PROJECT_WEB_RESOURCE) == diyProjectWebResource;
    assert DiyProjectWebResourceCaptor.getValue().getId() == 1L;
    assert DiyProjectWebResourceCaptor.getValue().getDiyProject() == diyProject;
    assert DiyProjectWebResourceCaptor.getValue().getTitle().equals("title");
    assert DiyProjectWebResourceCaptor.getValue().getLink().equals("link");
    assert DiyProjectWebResourceCaptor.getValue().getWebResourceSequence() == 1L;
    assert DiyProjectWebResourceCaptor.getValue().getType().equals("type");
    assert DiyProjectWebResourceCaptor.getValue().getResourceType().equals("resourceType");
    assert DiyProjectWebResourceCaptor.getValue().getDescription().equals("description");
  }

  @Test
  public void testProcessNoId() {
    // Initialize
    ProjectMediaDao mockProjectMediaDao = Mockito.mock(ProjectMediaDao.class);

    ArgumentCaptor<DiyProjectWebResource> DiyProjectWebResourceCaptor = ArgumentCaptor.forClass(DiyProjectWebResource.class);

    DiyProjectWebResource diyProjectWebResource = DiyProjectWebResource.builder().id(1L).build();
    DiyProject diyProject = DiyProject.builder().id(1L).build();
    ProjectDetailWebResourceRepresentation projectDetailWebResourceRepresentation = ProjectDetailWebResourceRepresentation
        .builder()
        .title("title").webResourceSequence(1L).type("type").resourceType("resourceType").description("description").link("link")
        .build();
    CommandDTO commandDTO = CommandDTO
        .builder()
        .add(EntityConstants.ENTITY_DIY_PROJECT, diyProject)
        .add(ContextConstants.CONTEXT_PROJECT_WEB_RESOURCE, projectDetailWebResourceRepresentation)
        .build();

    SaveProjectWebResourceCommand command = new SaveProjectWebResourceCommand();
    command.projectMediaDao = mockProjectMediaDao;

    // Define Mocks
    Mockito.when(mockProjectMediaDao.findProjectWebResourceByWebResourceId(1L)).thenReturn(diyProjectWebResource);
    Mockito.when(mockProjectMediaDao.saveProjectWebResource(Mockito.any())).thenReturn(diyProjectWebResource);

    // Execute
    Exception expectedException = null;
    try {
      command.process(commandDTO);
    } catch (Exception exception) {
      expectedException = exception;
    }

    // Verify
    Mockito.verify(mockProjectMediaDao, Mockito.times(0)).findProjectWebResourceByWebResourceId(1L);
    Mockito.verify(mockProjectMediaDao, Mockito.times(1)).saveProjectWebResource(DiyProjectWebResourceCaptor.capture());

    // Assertions
    assert expectedException == null;
    assert commandDTO.get(EntityConstants.ENTITY_DIY_PROJECT_WEB_RESOURCE) == diyProjectWebResource;
    assert DiyProjectWebResourceCaptor.getValue().getDiyProject() == diyProject;
    assert DiyProjectWebResourceCaptor.getValue().getTitle().equals("title");
    assert DiyProjectWebResourceCaptor.getValue().getLink().equals("link");
    assert DiyProjectWebResourceCaptor.getValue().getWebResourceSequence() == 1L;
    assert DiyProjectWebResourceCaptor.getValue().getType().equals("type");
    assert DiyProjectWebResourceCaptor.getValue().getResourceType().equals("resourceType");
    assert DiyProjectWebResourceCaptor.getValue().getDescription().equals("description");
  }

  @Test
  public void testPostProcess() {
    // Initialize
    SaveProjectWebResourceValidations mockSaveProjectWebResourceValidations = Mockito.mock(SaveProjectWebResourceValidations.class);

    DiyProjectWebResource diyProjectWebResource = DiyProjectWebResource.builder().id(1L).build();
    CommandDTO commandDTO = CommandDTO.builder().add(EntityConstants.ENTITY_DIY_PROJECT_WEB_RESOURCE, diyProjectWebResource).build();

    SaveProjectWebResourceCommand command = new SaveProjectWebResourceCommand();
    command.validations = mockSaveProjectWebResourceValidations;

    // Define Mocks
    Mockito.doNothing().when(mockSaveProjectWebResourceValidations).validateDiyProjectWebResourceEntity(diyProjectWebResource);

    // Execute
    Exception expectedException = null;
    try {
      command.postProcess(commandDTO);
    } catch (Exception exception) {
      expectedException = exception;
    }

    // Verify
    Mockito.verify(mockSaveProjectWebResourceValidations, Mockito.times(1)).validateDiyProjectWebResourceEntity(diyProjectWebResource);

    // Assertions
    assert expectedException == null;
  }
}
