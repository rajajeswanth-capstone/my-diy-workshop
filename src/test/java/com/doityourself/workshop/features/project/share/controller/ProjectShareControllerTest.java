package com.doityourself.workshop.features.project.share.controller;

import com.doityourself.workshop.common.command.CommandDTO;
import com.doityourself.workshop.common.constants.ContextConstants;
import com.doityourself.workshop.common.constants.ModelConstants;
import com.doityourself.workshop.common.constants.SessionConstants;
import com.doityourself.workshop.common.representation.error.ErrorsRepresentation;
import com.doityourself.workshop.features.login.representation.LoggedInUserRepresentation;
import com.doityourself.workshop.features.project.listing.controller.ProjectListingController;
import com.doityourself.workshop.features.project.share.exception.ProjectShareFailedException;
import com.doityourself.workshop.features.project.share.representation.ShareProjectRepresentation;
import com.doityourself.workshop.features.project.share.service.ProjectShareService;
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

public class ProjectShareControllerTest {
  @Test
  public void testGetProjectDetail() {
    // Initialize
    Model mockModel = Mockito.mock(Model.class);
    HttpServletRequest mockHttpServletRequest = Mockito.mock(HttpServletRequest.class);

    ProjectShareService mockProjectShareService = Mockito.mock(ProjectShareService.class);
    ProjectListingController mockProjectListingController = Mockito.mock(ProjectListingController.class);

    ArgumentCaptor<CommandDTO> shareProjectCommandDTOCaptor = ArgumentCaptor.forClass(CommandDTO.class);

    ProjectShareController projectShareController = new ProjectShareController();
    projectShareController.projectShareService = mockProjectShareService;
    projectShareController.projectListingController = mockProjectListingController;

    ShareProjectRepresentation shareProjectRepresentation = ShareProjectRepresentation.builder().projectId(1L).build();

    CommandDTO responseCommandDTO = CommandDTO
        .builder()
        .build();

    // Define Mocks
    Mockito.when(mockProjectShareService.shareProject(Mockito.any())).thenReturn(responseCommandDTO);
    Mockito.when(mockProjectListingController.projects(Mockito.any(), Mockito.any(), Mockito.any())).thenReturn("test");
    Mockito.when(mockModel.addAttribute(ModelConstants.MODEL_SHARE_PROJECT_SUCCESS, "Project Shared Successfully")).thenReturn(mockModel);
    Mockito.when(mockModel.addAttribute(ModelConstants.MODEL_LISTING_OPERATION, ModelConstants.MODEL_LISTING_OPERATION_SHARE)).thenReturn(mockModel);

    // Execute
    Exception expectedException = null;
    String result = null;
    try {
      result = projectShareController.shareProject(shareProjectRepresentation, mockModel, mockHttpServletRequest);
    } catch (Exception exception) {
      expectedException = exception;
    }

    // Verify
    Mockito.verify(mockProjectShareService, Mockito.times(1)).shareProject(shareProjectCommandDTOCaptor.capture());
    Mockito.verify(mockProjectListingController, Mockito.times(1)).projects(Mockito.any(), Mockito.any(), Mockito.any());
    Mockito.verify(mockModel, Mockito.times(1)).addAttribute(ModelConstants.MODEL_SHARE_PROJECT_SUCCESS, "Project Shared Successfully");
    Mockito.verify(mockModel, Mockito.times(1)).addAttribute(ModelConstants.MODEL_LISTING_OPERATION, ModelConstants.MODEL_LISTING_OPERATION_SHARE);

    // Assertions
    assert expectedException == null;
    assert shareProjectCommandDTOCaptor.getValue().get(ContextConstants.CONTEXT_SHARE_PROJECT) == shareProjectRepresentation;
    assert result.equals("test");
  }

  @Test
  public void testGetProjectDetailException() {
    // Initialize
    Model mockModel = Mockito.mock(Model.class);
    HttpServletRequest mockHttpServletRequest = Mockito.mock(HttpServletRequest.class);

    ProjectShareService mockProjectShareService = Mockito.mock(ProjectShareService.class);
    ProjectListingController mockProjectListingController = Mockito.mock(ProjectListingController.class);

    ArgumentCaptor<CommandDTO> shareProjectCommandDTOCaptor = ArgumentCaptor.forClass(CommandDTO.class);
    ArgumentCaptor<ErrorsRepresentation> errorsRepresentationCaptor = ArgumentCaptor.forClass(ErrorsRepresentation.class);

    ProjectShareController projectShareController = new ProjectShareController();
    projectShareController.projectShareService = mockProjectShareService;
    projectShareController.projectListingController = mockProjectListingController;

    ShareProjectRepresentation shareProjectRepresentation = ShareProjectRepresentation.builder().projectId(1L).build();

    Map<String, String> fieldMessages = new HashMap<>();
    fieldMessages.put("field1", "message1");
    ProjectShareFailedException responseException = new ProjectShareFailedException();
    responseException.setFieldMessages(fieldMessages);
    responseException.setMessages(Collections.singletonList("message1"));

    // Define Mocks
    Mockito.when(mockProjectShareService.shareProject(Mockito.any())).thenThrow(responseException);
    Mockito.when(mockProjectListingController.projects(Mockito.any(), Mockito.any(), Mockito.any())).thenReturn("test");
    Mockito.when(mockModel.addAttribute(ModelConstants.MODEL_SHARE_PROJECT_SUCCESS, "Project Shared Successfully")).thenReturn(mockModel);
    Mockito.when(mockModel.addAttribute(ModelConstants.MODEL_LISTING_OPERATION, ModelConstants.MODEL_LISTING_OPERATION_SHARE)).thenReturn(mockModel);

    // Execute
    Exception expectedException = null;
    String result = null;
    try {
      result = projectShareController.shareProject(shareProjectRepresentation, mockModel, mockHttpServletRequest);
    } catch (Exception exception) {
      expectedException = exception;
    }

    // Verify
    Mockito.verify(mockProjectShareService, Mockito.times(1)).shareProject(shareProjectCommandDTOCaptor.capture());
    Mockito.verify(mockProjectListingController, Mockito.times(1)).projects(Mockito.any(), Mockito.any(), Mockito.any());
    Mockito.verify(mockModel, Mockito.times(0)).addAttribute(ModelConstants.MODEL_SHARE_PROJECT_SUCCESS, "Project Shared Successfully");
    Mockito.verify(mockModel, Mockito.times(1)).addAttribute(ModelConstants.MODEL_LISTING_OPERATION, ModelConstants.MODEL_LISTING_OPERATION_SHARE);
    Mockito.verify(mockModel, Mockito.times(1)).addAttribute(Mockito.eq(ModelConstants.MODEL_SHARE_PROJECT_ERRORS), errorsRepresentationCaptor.capture());

    // Assertions
    assert expectedException == null;
    assert shareProjectCommandDTOCaptor.getValue().get(ContextConstants.CONTEXT_SHARE_PROJECT) == shareProjectRepresentation;
    assert errorsRepresentationCaptor.getValue().getErrorMessages().get(0).equals("message1");
    assert errorsRepresentationCaptor.getValue().getFieldErrorMessages().get("field1").equals("message1");
    assert result.equals("test");
  }

  @Test
  public void testGetSharedUsersForProject() {
    // Initialize
    Model mockModel = Mockito.mock(Model.class);
    HttpServletRequest mockHttpServletRequest = Mockito.mock(HttpServletRequest.class);
    HttpSession mockHttpSession = Mockito.mock(HttpSession.class);

    UserService mockUserService = Mockito.mock(UserService.class);
    ProjectListingController mockProjectListingController = Mockito.mock(ProjectListingController.class);

    ArgumentCaptor<CommandDTO> shareProjectCommandDTOCaptor = ArgumentCaptor.forClass(CommandDTO.class);

    ProjectShareController projectShareController = new ProjectShareController();
    projectShareController.userService = mockUserService;
    projectShareController.projectListingController = mockProjectListingController;

    LoggedInUserRepresentation loggedInUserRepresentation = LoggedInUserRepresentation.builder().userName("username").build();

    SharedUserRepresentation sharedUserRepresentation = SharedUserRepresentation.builder().id(1L).build();
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
    Mockito.when(mockProjectListingController.projects(Mockito.any(), Mockito.any(), Mockito.any())).thenReturn("test");
    Mockito.when(mockModel.addAttribute(Mockito.eq(ModelConstants.MODEL_SHARE_PROJECT), Mockito.any(ShareProjectRepresentation.class))).thenReturn(mockModel);
    Mockito.when(mockModel.addAttribute(ModelConstants.MODEL_SHARED_USERS_FOR_PROJECT, sharedUserRepresentations)).thenReturn(mockModel);

    // Execute
    Exception expectedException = null;
    String result = null;
    try {
      result = projectShareController.getSharedUsersForProject(1L, mockModel, mockHttpServletRequest);
    } catch (Exception exception) {
      expectedException = exception;
    }

    // Verify
    Mockito.verify(mockUserService, Mockito.times(1)).getSharedUsersForProject(shareProjectCommandDTOCaptor.capture());
    Mockito.verify(mockProjectListingController, Mockito.times(1)).projects(Mockito.any(), Mockito.any(), Mockito.any());
    Mockito.verify(mockModel, Mockito.times(1)).addAttribute(Mockito.eq(ModelConstants.MODEL_SHARE_PROJECT), Mockito.any(ShareProjectRepresentation.class));
    Mockito.verify(mockModel, Mockito.times(1)).addAttribute(ModelConstants.MODEL_SHARED_USERS_FOR_PROJECT, sharedUserRepresentations);

    // Assertions
    assert expectedException == null;
    assert shareProjectCommandDTOCaptor.getValue().get(ContextConstants.CONTEXT_LOGGED_IN_USER) == loggedInUserRepresentation;
    assert (Long) shareProjectCommandDTOCaptor.getValue().get(ContextConstants.CONTEXT_PROJECT_ID) == 1L;
    assert result.equals("test");
  }

  @Test
  public void testGetSharedUsersForProjectException() {
    // Initialize
    Model mockModel = Mockito.mock(Model.class);
    HttpServletRequest mockHttpServletRequest = Mockito.mock(HttpServletRequest.class);
    HttpSession mockHttpSession = Mockito.mock(HttpSession.class);

    UserService mockUserService = Mockito.mock(UserService.class);
    ProjectListingController mockProjectListingController = Mockito.mock(ProjectListingController.class);

    ArgumentCaptor<CommandDTO> shareProjectCommandDTOCaptor = ArgumentCaptor.forClass(CommandDTO.class);
    ArgumentCaptor<ErrorsRepresentation> errorsRepresentationCaptor = ArgumentCaptor.forClass(ErrorsRepresentation.class);

    ProjectShareController projectShareController = new ProjectShareController();
    projectShareController.userService = mockUserService;
    projectShareController.projectListingController = mockProjectListingController;

    LoggedInUserRepresentation loggedInUserRepresentation = LoggedInUserRepresentation.builder().userName("username").build();

    SharedUserRepresentation sharedUserRepresentation = SharedUserRepresentation.builder().id(1L).build();
    List<SharedUserRepresentation> sharedUserRepresentations = new ArrayList<>();
    sharedUserRepresentations.add(sharedUserRepresentation);

    Map<String, String> fieldMessages = new HashMap<>();
    fieldMessages.put("field1", "message1");
    GetSharedUsersForProjectFailedException responseException = new GetSharedUsersForProjectFailedException();
    responseException.setFieldMessages(fieldMessages);
    responseException.setMessages(Collections.singletonList("message1"));

    // Define Mocks
    Mockito.when(mockHttpServletRequest.getSession()).thenReturn(mockHttpSession);
    Mockito.when(mockHttpSession.getAttribute(SessionConstants.SESSION_LOGGED_IN_USER)).thenReturn(loggedInUserRepresentation);
    Mockito.when(mockUserService.getSharedUsersForProject(Mockito.any())).thenThrow(responseException);
    Mockito.when(mockProjectListingController.projects(Mockito.any(), Mockito.any(), Mockito.any())).thenReturn("test");
    Mockito.when(mockModel.addAttribute(Mockito.eq(ModelConstants.MODEL_SHARE_PROJECT), Mockito.any(ShareProjectRepresentation.class))).thenReturn(mockModel);
    Mockito.when(mockModel.addAttribute(ModelConstants.MODEL_SHARED_USERS_FOR_PROJECT, sharedUserRepresentations)).thenReturn(mockModel);

    // Execute
    Exception expectedException = null;
    String result = null;
    try {
      result = projectShareController.getSharedUsersForProject(1L, mockModel, mockHttpServletRequest);
    } catch (Exception exception) {
      expectedException = exception;
    }

    // Verify
    Mockito.verify(mockUserService, Mockito.times(1)).getSharedUsersForProject(shareProjectCommandDTOCaptor.capture());
    Mockito.verify(mockProjectListingController, Mockito.times(1)).projects(Mockito.any(), Mockito.any(), Mockito.any());
    Mockito.verify(mockModel, Mockito.times(0)).addAttribute(Mockito.eq(ModelConstants.MODEL_SHARE_PROJECT), Mockito.any(ShareProjectRepresentation.class));
    Mockito.verify(mockModel, Mockito.times(0)).addAttribute(ModelConstants.MODEL_SHARED_USERS_FOR_PROJECT, sharedUserRepresentations);
    Mockito.verify(mockModel, Mockito.times(1)).addAttribute(Mockito.eq(ModelConstants.MODEL_SHARED_USER_ERRORS), errorsRepresentationCaptor.capture());

    // Assertions
    assert expectedException == null;
    assert shareProjectCommandDTOCaptor.getValue().get(ContextConstants.CONTEXT_LOGGED_IN_USER) == loggedInUserRepresentation;
    assert (Long) shareProjectCommandDTOCaptor.getValue().get(ContextConstants.CONTEXT_PROJECT_ID) == 1L;
    assert errorsRepresentationCaptor.getValue().getErrorMessages().get(0).equals("message1");
    assert errorsRepresentationCaptor.getValue().getFieldErrorMessages().get("field1").equals("message1");
    assert result.equals("test");
  }
}
