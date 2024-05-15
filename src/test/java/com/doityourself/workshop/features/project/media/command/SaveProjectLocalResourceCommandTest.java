package com.doityourself.workshop.features.project.media.command;

import com.doityourself.workshop.common.command.CommandDTO;
import com.doityourself.workshop.common.constants.ContextConstants;
import com.doityourself.workshop.common.constants.EntityConstants;
import com.doityourself.workshop.database.entities.DiyProject;
import com.doityourself.workshop.database.entities.DiyProjectLocalResource;
import com.doityourself.workshop.features.project.media.dao.ProjectMediaDao;
import com.doityourself.workshop.features.project.media.representation.ProjectDetailLocalResourceRepresentation;
import com.doityourself.workshop.features.project.media.validation.SaveProjectLocalResourceValidations;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;

public class SaveProjectLocalResourceCommandTest {
  @Test
  public void testProcess() {
    // Initialize
    ProjectMediaDao mockProjectMediaDao = Mockito.mock(ProjectMediaDao.class);

    ArgumentCaptor<DiyProjectLocalResource> diyProjectLocalResourceCaptor = ArgumentCaptor.forClass(DiyProjectLocalResource.class);

    DiyProjectLocalResource diyProjectLocalResource = DiyProjectLocalResource.builder().id(1L).build();
    DiyProject diyProject = DiyProject.builder().id(1L).build();
    ProjectDetailLocalResourceRepresentation projectDetailWebResourceRepresentation = ProjectDetailLocalResourceRepresentation
        .builder()
        .id(1L).title("title").localResourceSequence(1L).type("type").resourceType("resourceType").description("description")
        .build();
    CommandDTO commandDTO = CommandDTO
        .builder()
        .add(EntityConstants.ENTITY_DIY_PROJECT, diyProject)
        .add(ContextConstants.CONTEXT_PROJECT_LOCAL_RESOURCE, projectDetailWebResourceRepresentation)
        .build();

    SaveProjectLocalResourceCommand command = new SaveProjectLocalResourceCommand();
    command.projectMediaDao = mockProjectMediaDao;

    // Define Mocks
    Mockito.when(mockProjectMediaDao.findProjectLocalResourceByLocalResourceId(1L)).thenReturn(diyProjectLocalResource);
    Mockito.when(mockProjectMediaDao.saveProjectLocalResource(Mockito.any())).thenReturn(diyProjectLocalResource);

    // Execute
    Exception expectedException = null;
    try {
      command.process(commandDTO);
    } catch (Exception exception) {
      expectedException = exception;
    }

    // Verify
    Mockito.verify(mockProjectMediaDao, Mockito.times(1)).findProjectLocalResourceByLocalResourceId(1L);
    Mockito.verify(mockProjectMediaDao, Mockito.times(1)).saveProjectLocalResource(diyProjectLocalResourceCaptor.capture());

    // Assertions
    assert expectedException == null;
    assert commandDTO.get(EntityConstants.ENTITY_DIY_PROJECT_LOCAL_RESOURCE) == diyProjectLocalResource;
    assert diyProjectLocalResourceCaptor.getValue().getId() == 1L;
    assert diyProjectLocalResourceCaptor.getValue().getDiyProject() == diyProject;
    assert diyProjectLocalResourceCaptor.getValue().getTitle().equals("title");
    assert diyProjectLocalResourceCaptor.getValue().getLocalResourceSequence() == 1L;
    assert diyProjectLocalResourceCaptor.getValue().getType().equals("type");
    assert diyProjectLocalResourceCaptor.getValue().getResourceType().equals("resourceType");
    assert diyProjectLocalResourceCaptor.getValue().getDescription().equals("description");
  }

  @Test
  public void testProcessNoId() {
    // Initialize
    ProjectMediaDao mockProjectMediaDao = Mockito.mock(ProjectMediaDao.class);

    ArgumentCaptor<DiyProjectLocalResource> diyProjectLocalResourceCaptor = ArgumentCaptor.forClass(DiyProjectLocalResource.class);

    DiyProjectLocalResource diyProjectLocalResource = DiyProjectLocalResource.builder().id(1L).build();
    DiyProject diyProject = DiyProject.builder().id(1L).build();
    ProjectDetailLocalResourceRepresentation projectDetailWebResourceRepresentation = ProjectDetailLocalResourceRepresentation
        .builder()
        .title("title").localResourceSequence(1L).type("type").resourceType("resourceType").description("description")
        .build();
    CommandDTO commandDTO = CommandDTO
        .builder()
        .add(EntityConstants.ENTITY_DIY_PROJECT, diyProject)
        .add(ContextConstants.CONTEXT_PROJECT_LOCAL_RESOURCE, projectDetailWebResourceRepresentation)
        .build();

    SaveProjectLocalResourceCommand command = new SaveProjectLocalResourceCommand();
    command.projectMediaDao = mockProjectMediaDao;

    // Define Mocks
    Mockito.when(mockProjectMediaDao.findProjectLocalResourceByLocalResourceId(1L)).thenReturn(diyProjectLocalResource);
    Mockito.when(mockProjectMediaDao.saveProjectLocalResource(Mockito.any())).thenReturn(diyProjectLocalResource);

    // Execute
    Exception expectedException = null;
    try {
      command.process(commandDTO);
    } catch (Exception exception) {
      expectedException = exception;
    }

    // Verify
    Mockito.verify(mockProjectMediaDao, Mockito.times(0)).findProjectLocalResourceByLocalResourceId(1L);
    Mockito.verify(mockProjectMediaDao, Mockito.times(1)).saveProjectLocalResource(diyProjectLocalResourceCaptor.capture());

    // Assertions
    assert expectedException == null;
    assert commandDTO.get(EntityConstants.ENTITY_DIY_PROJECT_LOCAL_RESOURCE) == diyProjectLocalResource;
    assert diyProjectLocalResourceCaptor.getValue().getDiyProject() == diyProject;
    assert diyProjectLocalResourceCaptor.getValue().getTitle().equals("title");
    assert diyProjectLocalResourceCaptor.getValue().getLocalResourceSequence() == 1L;
    assert diyProjectLocalResourceCaptor.getValue().getType().equals("type");
    assert diyProjectLocalResourceCaptor.getValue().getResourceType().equals("resourceType");
    assert diyProjectLocalResourceCaptor.getValue().getDescription().equals("description");
  }

  @Test
  public void testPostProcess() {
    // Initialize
    SaveProjectLocalResourceValidations mockSaveProjectLocalResourceValidations = Mockito.mock(SaveProjectLocalResourceValidations.class);

    DiyProjectLocalResource diyProjectLocalResource = DiyProjectLocalResource.builder().id(1L).build();
    CommandDTO commandDTO = CommandDTO
        .builder()
        .add(EntityConstants.ENTITY_DIY_PROJECT_LOCAL_RESOURCE, diyProjectLocalResource)
        .build();

    SaveProjectLocalResourceCommand command = new SaveProjectLocalResourceCommand();
    command.validations = mockSaveProjectLocalResourceValidations;

    // Define Mocks
    Mockito.doNothing().when(mockSaveProjectLocalResourceValidations).validateDiyProjectLocalResourceEntity(diyProjectLocalResource);

    // Execute
    Exception expectedException = null;
    try {
      command.postProcess(commandDTO);
    } catch (Exception exception) {
      expectedException = exception;
    }

    // Verify
    Mockito.verify(mockSaveProjectLocalResourceValidations, Mockito.times(1)).validateDiyProjectLocalResourceEntity(diyProjectLocalResource);

    // Assertions
    assert expectedException == null;
  }
}
