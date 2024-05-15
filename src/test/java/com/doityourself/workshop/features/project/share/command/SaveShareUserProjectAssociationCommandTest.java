package com.doityourself.workshop.features.project.share.command;

import com.doityourself.workshop.common.command.CommandDTO;
import com.doityourself.workshop.common.constants.EntityConstants;
import com.doityourself.workshop.database.entities.DiyProject;
import com.doityourself.workshop.database.entities.DiyUser;
import com.doityourself.workshop.database.entities.DiyUserProject;
import com.doityourself.workshop.database.entities.DiyUserProjectId;
import com.doityourself.workshop.features.project.listing.dao.ProjectListingDao;
import com.doityourself.workshop.features.project.share.validation.ShareProjectValidations;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;

public class SaveShareUserProjectAssociationCommandTest {
  @Test
  public void testProcess() {
    // Initialize
    ProjectListingDao mockProjectListingDao = Mockito.mock(ProjectListingDao.class);
    ArgumentCaptor<DiyUserProject> diyUserProjectCaptor = ArgumentCaptor.forClass(DiyUserProject.class);

    DiyProject diyProject = DiyProject.builder().id(1L).build();
    DiyUser diyUser = DiyUser.builder().id(1L).build();
    DiyUserProject responseDiyUserProject = DiyUserProject
        .builder()
        .id(
            DiyUserProjectId.builder().diyProject(diyProject).diyUser(diyUser).build()
        ).build();
    CommandDTO commandDTO = CommandDTO
        .builder()
        .add(EntityConstants.ENTITY_DIY_PROJECT, diyProject)
        .add(EntityConstants.ENTITY_DIY_USER, diyUser)
        .build();

    SaveShareUserProjectAssociationCommand command = new SaveShareUserProjectAssociationCommand();
    command.projectListingDao = mockProjectListingDao;

    // Define Mocks
    Mockito.when(mockProjectListingDao.saveUserProject(Mockito.any())).thenReturn(responseDiyUserProject);

    // Execute
    Exception expectedException = null;
    try {
      command.process(commandDTO);
    } catch (Exception exception) {
      expectedException = exception;
    }

    // Verify
    Mockito.verify(mockProjectListingDao, Mockito.times(1)).saveUserProject(diyUserProjectCaptor.capture());

    // Assertions
    assert expectedException == null;
    assert diyUserProjectCaptor.getValue().getId().getDiyProject() == diyProject;
    assert diyUserProjectCaptor.getValue().getId().getDiyUser() == diyUser;
    assert diyUserProjectCaptor.getValue().isShared();
    assert commandDTO.get(EntityConstants.ENTITY_DIY_USER_PROJECT) == responseDiyUserProject;
  }

  @Test
  public void testPostProcess() {
    // Initialize
    ShareProjectValidations mockShareProjectValidations = Mockito.mock(ShareProjectValidations.class);

    DiyProject diyProject = DiyProject.builder().id(1L).build();
    DiyUser diyUser = DiyUser.builder().id(1L).build();
    DiyUserProject diyUserProject = DiyUserProject
        .builder()
        .id(
            DiyUserProjectId.builder().diyProject(diyProject).diyUser(diyUser).build()
        ).build();
    CommandDTO commandDTO = CommandDTO.builder().add(EntityConstants.ENTITY_DIY_USER_PROJECT, diyUserProject).build();

    SaveShareUserProjectAssociationCommand command = new SaveShareUserProjectAssociationCommand();
    command.validations = mockShareProjectValidations;

    // Define Mocks
    Mockito.doNothing().when(mockShareProjectValidations).validateDiyUserProjectAssociation(diyUserProject);

    // Execute
    Exception expectedException = null;
    try {
      command.postProcess(commandDTO);
    } catch (Exception exception) {
      expectedException = exception;
    }

    // Verify
    Mockito.verify(mockShareProjectValidations, Mockito.times(1)).validateDiyUserProjectAssociation(diyUserProject);

    // Assertions
    assert expectedException == null;
  }
}
