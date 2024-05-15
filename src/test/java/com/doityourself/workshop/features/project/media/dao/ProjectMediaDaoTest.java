package com.doityourself.workshop.features.project.media.dao;

import com.doityourself.workshop.database.entities.DiyProjectLocalResource;
import com.doityourself.workshop.database.entities.DiyProjectWebResource;
import com.doityourself.workshop.database.repositories.DiyProjectLocalResourceRepository;
import com.doityourself.workshop.database.repositories.DiyProjectWebResourceRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class ProjectMediaDaoTest {
  @Test
  public void testFindProjectImagesByProjectId() {
    // Initialize
    DiyProjectLocalResource diyProjectLocalResource = DiyProjectLocalResource.builder().id(1L).build();

    List<DiyProjectLocalResource> diyProjectLocalResources = new ArrayList<>();
    diyProjectLocalResources.add(diyProjectLocalResource);

    DiyProjectLocalResourceRepository mockDiyProjectLocalResourceRepository = Mockito.mock(DiyProjectLocalResourceRepository.class);

    ProjectMediaDao projectMediaDao = new ProjectMediaDao();
    projectMediaDao.projectLocalResourceRepository = mockDiyProjectLocalResourceRepository;

    // Define Mocks
    Mockito.when(mockDiyProjectLocalResourceRepository.findProjectLocalResourcesByProjectId(1L)).thenReturn(diyProjectLocalResources);

    // Execute
    Exception expectedException = null;
    List<DiyProjectLocalResource> result = null;
    try {
      result = projectMediaDao.findProjectImagesByProjectId(1L);
    } catch (Exception exception) {
      expectedException = exception;
    }

    // Verify
    Mockito.verify(mockDiyProjectLocalResourceRepository, Mockito.times(1)).findProjectLocalResourcesByProjectId(1L);

    // Assertions
    assert expectedException == null;
    assert result == diyProjectLocalResources;
  }

  @Test
  public void testFindProjectWebResourcesByProjectId() {
    // Initialize
    DiyProjectWebResource diyProjectWebResource = DiyProjectWebResource.builder().id(1L).build();

    List<DiyProjectWebResource> diyProjectWebResources = new ArrayList<>();
    diyProjectWebResources.add(diyProjectWebResource);

    DiyProjectWebResourceRepository mockDiyProjectWebResourceRepository = Mockito.mock(DiyProjectWebResourceRepository.class);

    ProjectMediaDao projectMediaDao = new ProjectMediaDao();
    projectMediaDao.projectWebResourceRepository = mockDiyProjectWebResourceRepository;

    // Define Mocks
    Mockito.when(mockDiyProjectWebResourceRepository.findProjectWebResourcesByProjectId(1L)).thenReturn(diyProjectWebResources);

    // Execute
    Exception expectedException = null;
    List<DiyProjectWebResource> result = null;
    try {
      result = projectMediaDao.findProjectWebResourcesByProjectId(1L);
    } catch (Exception exception) {
      expectedException = exception;
    }

    // Verify
    Mockito.verify(mockDiyProjectWebResourceRepository, Mockito.times(1)).findProjectWebResourcesByProjectId(1L);

    // Assertions
    assert expectedException == null;
    assert result == diyProjectWebResources;
  }

  @Test
  public void testSaveProjectLocalResource() {
    // Initialize
    DiyProjectLocalResource diyProjectLocalResource = DiyProjectLocalResource.builder().id(1L).build();

    DiyProjectLocalResourceRepository mockDiyProjectLocalResourceRepository = Mockito.mock(DiyProjectLocalResourceRepository.class);

    ProjectMediaDao projectMediaDao = new ProjectMediaDao();
    projectMediaDao.projectLocalResourceRepository = mockDiyProjectLocalResourceRepository;

    // Define Mocks
    Mockito.when(mockDiyProjectLocalResourceRepository.save(diyProjectLocalResource)).thenReturn(diyProjectLocalResource);

    // Execute
    Exception expectedException = null;
    DiyProjectLocalResource result = null;
    try {
      result = projectMediaDao.saveProjectLocalResource(diyProjectLocalResource);
    } catch (Exception exception) {
      expectedException = exception;
    }

    // Verify
    Mockito.verify(mockDiyProjectLocalResourceRepository, Mockito.times(1)).save(diyProjectLocalResource);

    // Assertions
    assert expectedException == null;
    assert result == diyProjectLocalResource;
  }

  @Test
  public void testSaveProjectWebResource() {
    // Initialize
    DiyProjectWebResource diyProjectWebResource = DiyProjectWebResource.builder().id(1L).build();

    DiyProjectWebResourceRepository mockDiyProjectWebResourceRepository = Mockito.mock(DiyProjectWebResourceRepository.class);

    ProjectMediaDao projectMediaDao = new ProjectMediaDao();
    projectMediaDao.projectWebResourceRepository = mockDiyProjectWebResourceRepository;

    // Define Mocks
    Mockito.when(mockDiyProjectWebResourceRepository.save(diyProjectWebResource)).thenReturn(diyProjectWebResource);

    // Execute
    Exception expectedException = null;
    DiyProjectWebResource result = null;
    try {
      result = projectMediaDao.saveProjectWebResource(diyProjectWebResource);
    } catch (Exception exception) {
      expectedException = exception;
    }

    // Verify
    Mockito.verify(mockDiyProjectWebResourceRepository, Mockito.times(1)).save(diyProjectWebResource);

    // Assertions
    assert expectedException == null;
    assert result == diyProjectWebResource;
  }

  @Test
  public void testFindProjectLocalResourceByLocalResourceId() {
    // Initialize
    DiyProjectLocalResource diyProjectLocalResource = DiyProjectLocalResource.builder().id(1L).build();

    DiyProjectLocalResourceRepository mockDiyProjectLocalResourceRepository = Mockito.mock(DiyProjectLocalResourceRepository.class);

    ProjectMediaDao projectMediaDao = new ProjectMediaDao();
    projectMediaDao.projectLocalResourceRepository = mockDiyProjectLocalResourceRepository;

    // Define Mocks
    Mockito.when(mockDiyProjectLocalResourceRepository.findById(1L)).thenReturn(Optional.of(diyProjectLocalResource));

    // Execute
    Exception expectedException = null;
    DiyProjectLocalResource result = null;
    try {
      result = projectMediaDao.findProjectLocalResourceByLocalResourceId(1L);
    } catch (Exception exception) {
      expectedException = exception;
    }

    // Verify
    Mockito.verify(mockDiyProjectLocalResourceRepository, Mockito.times(1)).findById(1L);

    // Assertions
    assert expectedException == null;
    assert result == diyProjectLocalResource;
  }

  @Test
  public void testFindProjectWebResourceByWebResourceId() {
    // Initialize
    DiyProjectWebResource diyProjectWebResource = DiyProjectWebResource.builder().id(1L).build();

    DiyProjectWebResourceRepository mockDiyProjectWebResourceRepository = Mockito.mock(DiyProjectWebResourceRepository.class);

    ProjectMediaDao projectMediaDao = new ProjectMediaDao();
    projectMediaDao.projectWebResourceRepository = mockDiyProjectWebResourceRepository;

    // Define Mocks
    Mockito.when(mockDiyProjectWebResourceRepository.findById(1L)).thenReturn(Optional.of(diyProjectWebResource));

    // Execute
    Exception expectedException = null;
    DiyProjectWebResource result = null;
    try {
      result = projectMediaDao.findProjectWebResourceByWebResourceId(1L);
    } catch (Exception exception) {
      expectedException = exception;
    }

    // Verify
    Mockito.verify(mockDiyProjectWebResourceRepository, Mockito.times(1)).findById(1L);

    // Assertions
    assert expectedException == null;
    assert result == diyProjectWebResource;
  }

  @Test
  public void testDeleteProjectLocalResourceByLocalResourceId() {
    // Initialize
    DiyProjectLocalResourceRepository mockDiyProjectLocalResourceRepository = Mockito.mock(DiyProjectLocalResourceRepository.class);

    ProjectMediaDao projectMediaDao = new ProjectMediaDao();
    projectMediaDao.projectLocalResourceRepository = mockDiyProjectLocalResourceRepository;

    // Define Mocks
    Mockito.doNothing().when(mockDiyProjectLocalResourceRepository).deleteById(1L);

    // Execute
    Exception expectedException = null;
    try {
      projectMediaDao.deleteProjectLocalResourceByLocalResourceId(1L);
    } catch (Exception exception) {
      expectedException = exception;
    }

    // Verify
    Mockito.verify(mockDiyProjectLocalResourceRepository, Mockito.times(1)).deleteById(1L);

    // Assertions
    assert expectedException == null;
  }

  @Test
  public void testDeleteProjectWebResourceByWebResourceId() {
    // Initialize
    DiyProjectWebResourceRepository mockDiyProjectWebResourceRepository = Mockito.mock(DiyProjectWebResourceRepository.class);

    ProjectMediaDao projectMediaDao = new ProjectMediaDao();
    projectMediaDao.projectWebResourceRepository = mockDiyProjectWebResourceRepository;

    // Define Mocks
    Mockito.doNothing().when(mockDiyProjectWebResourceRepository).deleteById(1L);

    // Execute
    Exception expectedException = null;
    try {
      projectMediaDao.deleteProjectWebResourceByWebResourceId(1L);
    } catch (Exception exception) {
      expectedException = exception;
    }

    // Verify
    Mockito.verify(mockDiyProjectWebResourceRepository, Mockito.times(1)).deleteById(1L);

    // Assertions
    assert expectedException == null;
  }

  @Test
  public void testFindDistinctProjectWebResourceTypesByProjectId() {
    // Initialize
    List<String> types = Collections.singletonList("type1");

    DiyProjectWebResourceRepository mockDiyProjectWebResourceRepository = Mockito.mock(DiyProjectWebResourceRepository.class);

    ProjectMediaDao projectMediaDao = new ProjectMediaDao();
    projectMediaDao.projectWebResourceRepository = mockDiyProjectWebResourceRepository;

    // Define Mocks
    Mockito.when(mockDiyProjectWebResourceRepository.findDistinctProjectWebResourceTypesByProjectId(1L)).thenReturn(types);

    // Execute
    Exception expectedException = null;
    List<String> result = null;
    try {
      result = projectMediaDao.findDistinctProjectWebResourceTypesByProjectId(1L);
    } catch (Exception exception) {
      expectedException = exception;
    }

    // Verify
    Mockito.verify(mockDiyProjectWebResourceRepository, Mockito.times(1)).findDistinctProjectWebResourceTypesByProjectId(1L);

    // Assertions
    assert expectedException == null;
    assert result == types;
  }

  @Test
  public void testFindDistinctProjectLocalResourceTypesByProjectId() {
    // Initialize
    List<String> types = Collections.singletonList("type1");

    DiyProjectLocalResourceRepository mockDiyProjectLocalResourceRepository = Mockito.mock(DiyProjectLocalResourceRepository.class);

    ProjectMediaDao projectMediaDao = new ProjectMediaDao();
    projectMediaDao.projectLocalResourceRepository = mockDiyProjectLocalResourceRepository;

    // Define Mocks
    Mockito.when(mockDiyProjectLocalResourceRepository.findDistinctProjectLocalResourceTypesByProjectId(1L)).thenReturn(types);

    // Execute
    Exception expectedException = null;
    List<String> result = null;
    try {
      result = projectMediaDao.findDistinctProjectLocalResourceTypesByProjectId(1L);
    } catch (Exception exception) {
      expectedException = exception;
    }

    // Verify
    Mockito.verify(mockDiyProjectLocalResourceRepository, Mockito.times(1)).findDistinctProjectLocalResourceTypesByProjectId(1L);

    // Assertions
    assert expectedException == null;
    assert result == types;
  }
}
