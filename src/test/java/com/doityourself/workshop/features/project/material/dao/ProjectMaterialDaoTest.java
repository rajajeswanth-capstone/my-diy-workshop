package com.doityourself.workshop.features.project.material.dao;

import com.doityourself.workshop.database.entities.DiyProjectMaterial;
import com.doityourself.workshop.database.repositories.DiyProjectMaterialRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class ProjectMaterialDaoTest {
  @Test
  public void testFindProjectMaterialsByProjectId() {
    // Initialize
    DiyProjectMaterial diyProjectMaterial = DiyProjectMaterial.builder().id(1L).build();

    List<DiyProjectMaterial> diyProjectMaterials = new ArrayList<>();
    diyProjectMaterials.add(diyProjectMaterial);

    DiyProjectMaterialRepository mockDiyProjectMaterialRepository = Mockito.mock(DiyProjectMaterialRepository.class);

    ProjectMaterialDao projectMaterialDao = new ProjectMaterialDao();
    projectMaterialDao.projectInstructionRepository = mockDiyProjectMaterialRepository;

    // Define Mocks
    Mockito.when(mockDiyProjectMaterialRepository.findProjectMaterialsByProjectId(1L)).thenReturn(diyProjectMaterials);

    // Execute
    Exception expectedException = null;
    List<DiyProjectMaterial> result = null;
    try {
      result = projectMaterialDao.findProjectMaterialsByProjectId(1L);
    } catch (Exception exception) {
      expectedException = exception;
    }

    // Verify
    Mockito.verify(mockDiyProjectMaterialRepository, Mockito.times(1)).findProjectMaterialsByProjectId(1L);

    // Assertions
    assert expectedException == null;
    assert result == diyProjectMaterials;
  }

  @Test
  public void testSaveProjectMaterial() {
    // Initialize
    DiyProjectMaterial diyProjectMaterial = DiyProjectMaterial.builder().id(1L).build();

    DiyProjectMaterialRepository mockDiyProjectMaterialRepository = Mockito.mock(DiyProjectMaterialRepository.class);

    ProjectMaterialDao projectMaterialDao = new ProjectMaterialDao();
    projectMaterialDao.projectInstructionRepository = mockDiyProjectMaterialRepository;

    // Define Mocks
    Mockito.when(mockDiyProjectMaterialRepository.save(diyProjectMaterial)).thenReturn(diyProjectMaterial);

    // Execute
    Exception expectedException = null;
    DiyProjectMaterial result = null;
    try {
      result = projectMaterialDao.saveProjectMaterial(diyProjectMaterial);
    } catch (Exception exception) {
      expectedException = exception;
    }

    // Verify
    Mockito.verify(mockDiyProjectMaterialRepository, Mockito.times(1)).save(diyProjectMaterial);

    // Assertions
    assert expectedException == null;
    assert result == diyProjectMaterial;
  }

  @Test
  public void testFindProjectMaterialByMaterialId() {
    // Initialize
    DiyProjectMaterial diyProjectMaterial = DiyProjectMaterial.builder().id(1L).build();

    DiyProjectMaterialRepository mockDiyProjectMaterialRepository = Mockito.mock(DiyProjectMaterialRepository.class);

    ProjectMaterialDao projectMaterialDao = new ProjectMaterialDao();
    projectMaterialDao.projectInstructionRepository = mockDiyProjectMaterialRepository;

    // Define Mocks
    Mockito.when(mockDiyProjectMaterialRepository.findById(1L)).thenReturn(Optional.of(diyProjectMaterial));

    // Execute
    Exception expectedException = null;
    DiyProjectMaterial result = null;
    try {
      result = projectMaterialDao.findProjectMaterialByMaterialId(1L);
    } catch (Exception exception) {
      expectedException = exception;
    }

    // Verify
    Mockito.verify(mockDiyProjectMaterialRepository, Mockito.times(1)).findById(1L);

    // Assertions
    assert expectedException == null;
    assert result == diyProjectMaterial;
  }

  @Test
  public void testDeleteProjectMaterialByMaterialId() {
    // Initialize
    DiyProjectMaterial diyProjectMaterial = DiyProjectMaterial.builder().id(1L).build();

    DiyProjectMaterialRepository mockDiyProjectMaterialRepository = Mockito.mock(DiyProjectMaterialRepository.class);

    ProjectMaterialDao projectMaterialDao = new ProjectMaterialDao();
    projectMaterialDao.projectInstructionRepository = mockDiyProjectMaterialRepository;

    // Define Mocks
    Mockito.doNothing().when(mockDiyProjectMaterialRepository).deleteById(1L);

    // Execute
    Exception expectedException = null;
    try {
      projectMaterialDao.deleteProjectMaterialByMaterialId(1L);
    } catch (Exception exception) {
      expectedException = exception;
    }

    // Verify
    Mockito.verify(mockDiyProjectMaterialRepository, Mockito.times(1)).deleteById(1L);

    // Assertions
    assert expectedException == null;
  }

  @Test
  public void testFindDistinctProjectVendorsByProjectId() {
    // Initialize
    List<String> vendors = Collections.singletonList("vendor");

    DiyProjectMaterialRepository mockDiyProjectMaterialRepository = Mockito.mock(DiyProjectMaterialRepository.class);

    ProjectMaterialDao projectMaterialDao = new ProjectMaterialDao();
    projectMaterialDao.projectInstructionRepository = mockDiyProjectMaterialRepository;

    // Define Mocks
    Mockito.when(mockDiyProjectMaterialRepository.findDistinctProjectVendorsByProjectId(1L)).thenReturn(vendors);

    // Execute
    Exception expectedException = null;
    List<String> result = null;
    try {
      result = projectMaterialDao.findDistinctProjectVendorsByProjectId(1L);
    } catch (Exception exception) {
      expectedException = exception;
    }

    // Verify
    Mockito.verify(mockDiyProjectMaterialRepository, Mockito.times(1)).findDistinctProjectVendorsByProjectId(1L);

    // Assertions
    assert expectedException == null;
    assert result == vendors;
  }
}
