package com.doityourself.workshop.features.project.listing.dao;

import com.doityourself.workshop.database.entities.DiyProject;
import com.doityourself.workshop.database.entities.DiyUser;
import com.doityourself.workshop.database.entities.DiyUserProject;
import com.doityourself.workshop.database.entities.DiyUserProjectId;
import com.doityourself.workshop.database.repositories.DiyProjectRepository;
import com.doityourself.workshop.database.repositories.DiyUserProjectRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Optional;

public class ProjectListingDaoTest {
  @Test
  public void testSaveProject() {
    // Initialize
    DiyProject diyProject = new DiyProject();
    diyProject.setId(1L);

    DiyProjectRepository mockDiyProjectRepository = Mockito.mock(DiyProjectRepository.class);

    ProjectListingDao projectListingDao = new ProjectListingDao();
    projectListingDao.projectRepository = mockDiyProjectRepository;

    // Define Mocks
    Mockito.when(mockDiyProjectRepository.save(diyProject)).thenReturn(diyProject);

    // Execute
    Exception expectedException = null;
    DiyProject result = null;
    try {
      result = projectListingDao.saveProject(diyProject);
    } catch (Exception exception) {
      expectedException = exception;
    }

    // Verify
    Mockito.verify(mockDiyProjectRepository, Mockito.times(1)).save(diyProject);

    // Assertions
    assert expectedException == null;
    assert result == diyProject;
  }

  @Test
  public void testSaveUserProject() {
    // Initialize
    DiyUserProject diyUserProject = DiyUserProject
        .builder()
        .id(
            DiyUserProjectId
                .builder()
                .diyUser(
                    DiyUser.builder().id(1L).build()
                )
                .diyProject(
                    DiyProject.builder().id(1L).build()
                ).build()
        ).build();

    DiyUserProjectRepository mockDiyUserProjectRepository = Mockito.mock(DiyUserProjectRepository.class);

    ProjectListingDao projectListingDao = new ProjectListingDao();
    projectListingDao.userProjectRepository = mockDiyUserProjectRepository;

    // Define Mocks
    Mockito.when(mockDiyUserProjectRepository.save(diyUserProject)).thenReturn(diyUserProject);

    // Execute
    Exception expectedException = null;
    DiyUserProject result = null;
    try {
      result = projectListingDao.saveUserProject(diyUserProject);
    } catch (Exception exception) {
      expectedException = exception;
    }

    // Verify
    Mockito.verify(mockDiyUserProjectRepository, Mockito.times(1)).save(diyUserProject);

    // Assertions
    assert expectedException == null;
    assert result == diyUserProject;
  }

  @Test
  public void testDeleteByProjectId() {
    // Initialize
    DiyProject diyProject = new DiyProject();
    diyProject.setId(1L);

    DiyProjectRepository mockDiyProjectRepository = Mockito.mock(DiyProjectRepository.class);

    ProjectListingDao projectListingDao = new ProjectListingDao();
    projectListingDao.projectRepository = mockDiyProjectRepository;

    // Define Mocks
    Mockito.when(mockDiyProjectRepository.findById(1L)).thenReturn(Optional.of(diyProject));
    Mockito.doNothing().when(mockDiyProjectRepository).delete(diyProject);

    // Execute
    Exception expectedException = null;
    try {
      projectListingDao.deleteByProjectId(1L);
    } catch (Exception exception) {
      expectedException = exception;
    }

    // Verify
    Mockito.verify(mockDiyProjectRepository, Mockito.times(1)).findById(1L);
    Mockito.verify(mockDiyProjectRepository, Mockito.times(1)).delete(diyProject);

    // Assertions
    assert expectedException == null;
  }
}
