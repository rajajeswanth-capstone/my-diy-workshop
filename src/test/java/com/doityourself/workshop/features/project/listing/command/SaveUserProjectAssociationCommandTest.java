package com.doityourself.workshop.features.project.listing.command;

import com.doityourself.workshop.common.command.CommandDTO;
import com.doityourself.workshop.common.constants.ContextConstants;
import com.doityourself.workshop.common.constants.EntityConstants;
import com.doityourself.workshop.database.entities.DiyProject;
import com.doityourself.workshop.database.entities.DiyUser;
import com.doityourself.workshop.database.entities.DiyUserProject;
import com.doityourself.workshop.features.login.dao.LoginDao;
import com.doityourself.workshop.features.login.representation.LoggedInUserRepresentation;
import com.doityourself.workshop.features.project.listing.dao.ProjectListingDao;
import com.doityourself.workshop.features.project.listing.validation.SaveProjectValidations;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;

public class SaveUserProjectAssociationCommandTest {
  @Test
  public void testPreProcess() {
    // Initialize
    LoginDao mockLoginDao = Mockito.mock(LoginDao.class);

    DiyUser responseDiyUser = DiyUser.builder().id(1L).build();
    LoggedInUserRepresentation loggedInUserRepresentation = LoggedInUserRepresentation.builder().userName("username").build();
    CommandDTO commandDTO = CommandDTO
        .builder()
        .add(ContextConstants.CONTEXT_LOGGED_IN_USER, loggedInUserRepresentation)
        .build();

    SaveUserProjectAssociationCommand command = new SaveUserProjectAssociationCommand();
    command.loginDao = mockLoginDao;

    // Define Mocks
    Mockito.when(mockLoginDao.findMatchingLoggedInUser(loggedInUserRepresentation)).thenReturn(responseDiyUser);

    // Execute
    Exception expectedException = null;
    try {
      command.preProcess(commandDTO);
    } catch (Exception exception) {
      expectedException = exception;
    }

    // Verify
    Mockito.verify(mockLoginDao, Mockito.times(1)).findMatchingLoggedInUser(loggedInUserRepresentation);

    // Assertions
    assert expectedException == null;
    assert commandDTO.get(EntityConstants.ENTITY_DIY_USER) == responseDiyUser;
  }

  @Test
  public void testProcess() {
    // Initialize
    ProjectListingDao mockProjectListingDao = Mockito.mock(ProjectListingDao.class);

    ArgumentCaptor<DiyUserProject> diyUserProjectCaptor = ArgumentCaptor.forClass(DiyUserProject.class);

    DiyUser diyUser = DiyUser.builder().id(1L).build();
    DiyProject diyProject = DiyProject.builder().id(1L).build();
    DiyUserProject responseDiyUserProject = DiyUserProject.builder().build();
    CommandDTO commandDTO = CommandDTO
        .builder()
        .add(EntityConstants.ENTITY_DIY_PROJECT, diyProject)
        .add(EntityConstants.ENTITY_DIY_USER, diyUser)
        .build();

    SaveUserProjectAssociationCommand command = new SaveUserProjectAssociationCommand();
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
    assert commandDTO.get(EntityConstants.ENTITY_DIY_USER_PROJECT) == responseDiyUserProject;
    assert diyUserProjectCaptor.getValue().getId().getDiyUser() == diyUser;
    assert diyUserProjectCaptor.getValue().getId().getDiyProject() == diyProject;
    assert !diyUserProjectCaptor.getValue().isShared();
  }

  @Test
  public void testPostProcess() {
    // Initialize
    SaveProjectValidations mockSaveProjectValidations = Mockito.mock(SaveProjectValidations.class);

    DiyUserProject diyUserProject = DiyUserProject.builder().build();
    CommandDTO commandDTO = CommandDTO
        .builder()
        .add(EntityConstants.ENTITY_DIY_USER_PROJECT, diyUserProject)
        .build();

    SaveUserProjectAssociationCommand command = new SaveUserProjectAssociationCommand();
    command.validations = mockSaveProjectValidations;

    // Define Mocks
    Mockito.doNothing().when(mockSaveProjectValidations).validateDiyUserProjectAssociation(diyUserProject);

    // Execute
    Exception expectedException = null;
    try {
      command.postProcess(commandDTO);
    } catch (Exception exception) {
      expectedException = exception;
    }

    // Verify
    Mockito.verify(mockSaveProjectValidations, Mockito.times(1)).validateDiyUserProjectAssociation(diyUserProject);

    // Assertions
    assert expectedException == null;
  }
}
