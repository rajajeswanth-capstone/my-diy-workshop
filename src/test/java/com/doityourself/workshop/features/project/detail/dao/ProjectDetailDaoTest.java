package com.doityourself.workshop.features.project.detail.dao;

import com.doityourself.workshop.database.entities.DiyProject;
import com.doityourself.workshop.database.entities.DiyProjectInstruction;
import com.doityourself.workshop.database.repositories.DiyProjectInstructionRepository;
import com.doityourself.workshop.database.repositories.DiyProjectRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ProjectDetailDaoTest {
  @Test
  public void testFindProjectById() {
    // Initialize
    DiyProject responseDiyProject = new DiyProject();
    responseDiyProject.setId(1L);

    DiyProjectRepository mockDiyProjectRepository = Mockito.mock(DiyProjectRepository.class);

    ProjectDetailDao projectDetailDao = new ProjectDetailDao();
    projectDetailDao.projectRepository = mockDiyProjectRepository;

    // Define Mocks
    Mockito.when(mockDiyProjectRepository.findById(1L)).thenReturn(Optional.of(responseDiyProject));

    // Execute
    Exception expectedException = null;
    DiyProject result = null;
    try {
      result = projectDetailDao.findProjectById(1L);
    } catch (Exception exception) {
      expectedException = exception;
    }

    // Verify
    Mockito.verify(mockDiyProjectRepository, Mockito.times(1)).findById(1L);

    // Assertions
    assert expectedException == null;
    assert result.getId() == 1L;
  }

  @Test
  public void testFindProjectInstructionsByProjectId() {
    // Initialize
    DiyProject responseDiyProject = new DiyProject();
    responseDiyProject.setId(1L);

    DiyProjectInstructionRepository mockDiyProjectInstructionRepository = Mockito.mock(DiyProjectInstructionRepository.class);

    ProjectDetailDao projectDetailDao = new ProjectDetailDao();
    projectDetailDao.projectInstructionRepository = mockDiyProjectInstructionRepository;

    List<DiyProjectInstruction> responseInstructions = new ArrayList<>();
    responseInstructions.add(DiyProjectInstruction.builder().id(1L).build());

    // Define Mocks
    Mockito.when(mockDiyProjectInstructionRepository.findProjectInstructionsByProjectId(1L)).thenReturn(responseInstructions);

    // Execute
    Exception expectedException = null;
    List<DiyProjectInstruction> result = null;
    try {
      result = projectDetailDao.findProjectInstructionsByProjectId(1L);
    } catch (Exception exception) {
      expectedException = exception;
    }

    // Verify
    Mockito.verify(mockDiyProjectInstructionRepository, Mockito.times(1)).findProjectInstructionsByProjectId(1L);

    // Assertions
    assert expectedException == null;
    assert result.size() == 1;
    assert result.get(0).getId() == 1L;
  }

  @Test
  public void testSaveProjectInstruction() {
    // Initialize
    DiyProjectInstructionRepository mockDiyProjectInstructionRepository = Mockito.mock(DiyProjectInstructionRepository.class);

    ProjectDetailDao projectDetailDao = new ProjectDetailDao();
    projectDetailDao.projectInstructionRepository = mockDiyProjectInstructionRepository;

    DiyProjectInstruction instruction = DiyProjectInstruction.builder().id(1L).build();

    // Define Mocks
    Mockito.when(mockDiyProjectInstructionRepository.save(instruction)).thenReturn(instruction);

    // Execute
    Exception expectedException = null;
    DiyProjectInstruction result = null;
    try {
      result = projectDetailDao.saveProjectInstruction(instruction);
    } catch (Exception exception) {
      expectedException = exception;
    }

    // Verify
    Mockito.verify(mockDiyProjectInstructionRepository, Mockito.times(1)).save(instruction);

    // Assertions
    assert expectedException == null;
    assert result == instruction;
  }

  @Test
  public void testFindProjectInstructionByInstructionId() {
    // Initialize
    DiyProjectInstructionRepository mockDiyProjectInstructionRepository = Mockito.mock(DiyProjectInstructionRepository.class);

    ProjectDetailDao projectDetailDao = new ProjectDetailDao();
    projectDetailDao.projectInstructionRepository = mockDiyProjectInstructionRepository;

    DiyProjectInstruction instruction = DiyProjectInstruction.builder().id(1L).build();

    // Define Mocks
    Mockito.when(mockDiyProjectInstructionRepository.findById(1L)).thenReturn(Optional.of(instruction));

    // Execute
    Exception expectedException = null;
    DiyProjectInstruction result = null;
    try {
      result = projectDetailDao.findProjectInstructionByInstructionId(1L);
    } catch (Exception exception) {
      expectedException = exception;
    }

    // Verify
    Mockito.verify(mockDiyProjectInstructionRepository, Mockito.times(1)).findById(1L);

    // Assertions
    assert expectedException == null;
    assert result == instruction;
  }

  @Test
  public void testDeleteProjectInstructionByInstructionId() {
    // Initialize
    DiyProjectInstructionRepository mockDiyProjectInstructionRepository = Mockito.mock(DiyProjectInstructionRepository.class);

    ProjectDetailDao projectDetailDao = new ProjectDetailDao();
    projectDetailDao.projectInstructionRepository = mockDiyProjectInstructionRepository;

    DiyProjectInstruction instruction = DiyProjectInstruction.builder().id(1L).build();

    // Define Mocks
    Mockito.doNothing().when(mockDiyProjectInstructionRepository).deleteById(1L);

    // Execute
    Exception expectedException = null;
    try {
      projectDetailDao.deleteProjectInstructionByInstructionId(1L);
    } catch (Exception exception) {
      expectedException = exception;
    }

    // Verify
    Mockito.verify(mockDiyProjectInstructionRepository, Mockito.times(1)).deleteById(1L);

    // Assertions
    assert expectedException == null;
  }
}
