package com.doityourself.workshop.features.user.controller;

import com.doityourself.workshop.common.command.CommandDTO;
import com.doityourself.workshop.common.constants.ContextConstants;
import com.doityourself.workshop.common.constants.ModelConstants;
import com.doityourself.workshop.common.constants.SessionConstants;
import com.doityourself.workshop.common.representation.error.ErrorsRepresentation;
import com.doityourself.workshop.features.login.representation.LoggedInUserRepresentation;
import com.doityourself.workshop.features.project.listing.controller.ProjectListingController;
import com.doityourself.workshop.features.project.share.representation.ShareProjectRepresentation;
import com.doityourself.workshop.features.user.exception.GetSharedUsersForProjectFailedException;
import com.doityourself.workshop.features.user.representation.SharedUserRepresentation;
import com.doityourself.workshop.features.user.service.UserService;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import org.springframework.ui.Model;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.*;

public class UserControllerTest {
  @Test
  public void testSignup() {
    // Initialize
    Model mockModel = Mockito.mock(Model.class);
    HttpServletRequest mockHttpServletRequest = Mockito.mock(HttpServletRequest.class);
    HttpSession mockHttpSession = Mockito.mock(HttpSession.class);

    ArgumentCaptor<CommandDTO> getSharedUsersForProjectDTOCaptor = ArgumentCaptor.forClass(CommandDTO.class);

    LoggedInUserRepresentation loggedInUserRepresentation = LoggedInUserRepresentation.builder().userName("username").build();

    UserService mockUserService = Mockito.mock(UserService.class);
    ProjectListingController mockProjectListingController = Mockito.mock(ProjectListingController.class);

    UserController userController = new UserController();
    userController.userService = mockUserService;
    userController.projectListingController = mockProjectListingController;

    SharedUserRepresentation sharedUserRepresentation = SharedUserRepresentation.builder().userName("username").build();
    List<SharedUserRepresentation> sharedUserRepresentations = new ArrayList<>();
    sharedUserRepresentations.add(sharedUserRepresentation);

    CommandDTO responseCommandDTO = CommandDTO
        .builder()
        .add(ContextConstants.CONTEXT_SHARED_USERS_FOR_PROJECT, sharedUserRepresentations)
        .build();

    // Define Mocks
    Mockito.when(mockHttpServletRequest.getSession()).thenReturn(mockHttpSession);
    Mockito.when(mockHttpSession.getAttribute(SessionConstants.SESSION_LOGGED_IN_USER)).thenReturn(loggedInUserRepresentation);
    Mockito.when(mockUserService.getSharedUsersForProject(Mockito.any())).thenReturn(responseCommandDTO);
    Mockito.when(mockModel.addAttribute(Mockito.eq(ModelConstants.MODEL_SHARE_PROJECT), Mockito.any(ShareProjectRepresentation.class))).thenReturn(mockModel);
    Mockito.when(mockModel.addAttribute(ModelConstants.MODEL_SHARED_USERS_FOR_PROJECT, sharedUserRepresentations)).thenReturn(mockModel);
    Mockito.when(mockProjectListingController.projects(Mockito.any(), Mockito.any(), Mockito.any())).thenReturn("test");

    // Execute
    Exception expectedException = null;
    String result = null;
    try {
      result = userController.getSharedUsersForProject(1L, mockModel, mockHttpServletRequest);
    } catch (Exception exception) {
      expectedException = exception;
    }

    // Verify
    Mockito.verify(mockUserService, Mockito.times(1)).getSharedUsersForProject(getSharedUsersForProjectDTOCaptor.capture());
    Mockito.verify(mockProjectListingController, Mockito.times(1)).projects(Mockito.any(), Mockito.any(), Mockito.any());
    Mockito.verify(mockModel, Mockito.times(1)).addAttribute(Mockito.eq(ModelConstants.MODEL_SHARE_PROJECT), Mockito.any(ShareProjectRepresentation.class));
    Mockito.verify(mockModel, Mockito.times(1)).addAttribute(ModelConstants.MODEL_SHARED_USERS_FOR_PROJECT, sharedUserRepresentations);

    // Assertions
    assert expectedException == null;
    assert getSharedUsersForProjectDTOCaptor.getValue().get(ContextConstants.CONTEXT_LOGGED_IN_USER) == loggedInUserRepresentation;
    assert (Long) getSharedUsersForProjectDTOCaptor.getValue().get(ContextConstants.CONTEXT_PROJECT_ID) == 1L;
    assert result.equals("test");
  }

  @Test
  public void testSignupException() {
    // Initialize
    Model mockModel = Mockito.mock(Model.class);
    HttpServletRequest mockHttpServletRequest = Mockito.mock(HttpServletRequest.class);
    HttpSession mockHttpSession = Mockito.mock(HttpSession.class);

    ArgumentCaptor<CommandDTO> getSharedUsersForProjectDTOCaptor = ArgumentCaptor.forClass(CommandDTO.class);
    ArgumentCaptor<ErrorsRepresentation> errorsRepresentationCaptor = ArgumentCaptor.forClass(ErrorsRepresentation.class);

    LoggedInUserRepresentation loggedInUserRepresentation = LoggedInUserRepresentation.builder().userName("username").build();

    UserService mockUserService = Mockito.mock(UserService.class);
    ProjectListingController mockProjectListingController = Mockito.mock(ProjectListingController.class);

    UserController userController = new UserController();
    userController.userService = mockUserService;
    userController.projectListingController = mockProjectListingController;

    SharedUserRepresentation sharedUserRepresentation = SharedUserRepresentation.builder().userName("username").build();
    List<SharedUserRepresentation> sharedUserRepresentations = new ArrayList<>();
    sharedUserRepresentations.add(sharedUserRepresentation);

    Map<String, String> fieldMessages = new HashMap<>();
    fieldMessages.put("key1", "value1");
    GetSharedUsersForProjectFailedException getSharedUsersForProjectFailedException = new GetSharedUsersForProjectFailedException();
    getSharedUsersForProjectFailedException.setMessages(Collections.singletonList("message"));
    getSharedUsersForProjectFailedException.setFieldMessages(fieldMessages);

    // Define Mocks
    Mockito.when(mockHttpServletRequest.getSession()).thenReturn(mockHttpSession);
    Mockito.when(mockHttpSession.getAttribute(SessionConstants.SESSION_LOGGED_IN_USER)).thenReturn(loggedInUserRepresentation);
    Mockito.when(mockUserService.getSharedUsersForProject(Mockito.any())).thenThrow(getSharedUsersForProjectFailedException);
    Mockito.when(mockModel.addAttribute(Mockito.eq(ModelConstants.MODEL_SHARE_PROJECT), Mockito.any(ShareProjectRepresentation.class))).thenReturn(mockModel);
    Mockito.when(mockModel.addAttribute(Mockito.eq(ModelConstants.MODEL_SHARED_USER_ERRORS), errorsRepresentationCaptor.capture())).thenReturn(mockModel);
    Mockito.when(mockModel.addAttribute(ModelConstants.MODEL_SHARED_USERS_FOR_PROJECT, sharedUserRepresentations)).thenReturn(mockModel);
    Mockito.when(mockProjectListingController.projects(Mockito.any(), Mockito.any(), Mockito.any())).thenReturn("test");

    // Execute
    Exception expectedException = null;
    String result = null;
    try {
      result = userController.getSharedUsersForProject(1L, mockModel, mockHttpServletRequest);
    } catch (Exception exception) {
      expectedException = exception;
    }

    // Verify
    Mockito.verify(mockUserService, Mockito.times(1)).getSharedUsersForProject(getSharedUsersForProjectDTOCaptor.capture());
    Mockito.verify(mockProjectListingController, Mockito.times(1)).projects(Mockito.any(), Mockito.any(), Mockito.any());
    Mockito.verify(mockModel, Mockito.times(0)).addAttribute(Mockito.eq(ModelConstants.MODEL_SHARE_PROJECT), Mockito.any(ShareProjectRepresentation.class));
    Mockito.verify(mockModel, Mockito.times(0)).addAttribute(ModelConstants.MODEL_SHARED_USERS_FOR_PROJECT, sharedUserRepresentations);

    // Assertions
    assert expectedException == null;
    assert getSharedUsersForProjectDTOCaptor.getValue().get(ContextConstants.CONTEXT_LOGGED_IN_USER) == loggedInUserRepresentation;
    assert (Long) getSharedUsersForProjectDTOCaptor.getValue().get(ContextConstants.CONTEXT_PROJECT_ID) == 1L;
    assert errorsRepresentationCaptor.getValue().getErrorMessages().get(0).equals("message");
    assert errorsRepresentationCaptor.getValue().getFieldErrorMessages().get("key1").equals("value1");
    assert result.equals("test");
  }
}
