package com.doityourself.workshop.features.project.listing.controller;

import com.doityourself.workshop.common.command.CommandDTO;
import com.doityourself.workshop.common.constants.ContextConstants;
import com.doityourself.workshop.common.constants.ModelConstants;
import com.doityourself.workshop.common.constants.SessionConstants;
import com.doityourself.workshop.common.constants.ViewConstants;
import com.doityourself.workshop.common.representation.error.ErrorsRepresentation;
import com.doityourself.workshop.features.login.representation.LoggedInUserRepresentation;
import com.doityourself.workshop.features.project.listing.exception.ProjectDeleteFailedException;
import com.doityourself.workshop.features.project.listing.exception.ProjectSaveFailedException;
import com.doityourself.workshop.features.project.listing.exception.ProjectsForUserGetFailedException;
import com.doityourself.workshop.features.project.listing.representation.ProjectListingRepresentation;
import com.doityourself.workshop.features.project.listing.service.ProjectListingService;
import com.doityourself.workshop.features.project.share.representation.ShareProjectRepresentation;
import com.doityourself.workshop.features.user.representation.SharedUserRepresentation;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import org.springframework.ui.Model;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.*;

public class ProjectListingControllerTest {
  @Test
  public void testProjects() {
    // Initialize
    Model mockModel = Mockito.mock(Model.class);
    HttpServletRequest mockHttpServletRequest = Mockito.mock(HttpServletRequest.class);
    HttpSession mockHttpSession = Mockito.mock(HttpSession.class);

    ArgumentCaptor<CommandDTO> projectDetailCommandDTOCaptor = ArgumentCaptor.forClass(CommandDTO.class);

    LoggedInUserRepresentation loggedInUserRepresentation = LoggedInUserRepresentation.builder().userName("username").build();
    ProjectListingRepresentation projectListingRepresentation = ProjectListingRepresentation.builder().id(1L).build();

    ProjectListingService mockProjectListingService = Mockito.mock(ProjectListingService.class);

    ProjectListingController projectListingController = new ProjectListingController();
    projectListingController.projectListingService = mockProjectListingService;

    List<ProjectListingRepresentation> projects = new ArrayList<>();
    projects.add(ProjectListingRepresentation.builder().id(1L).build());

    List<ProjectListingRepresentation> sharedProjects = new ArrayList<>();
    projects.add(ProjectListingRepresentation.builder().id(1L).build());

    List<SharedUserRepresentation> sharedUsers = new ArrayList<>();
    sharedUsers.add(SharedUserRepresentation.builder().id(1L).build());

    CommandDTO responseCommandDTO = CommandDTO
        .builder()
        .add(ContextConstants.CONTEXT_PROJECTS, projects)
        .add(ModelConstants.MODEL_SHARED_PROJECTS, sharedProjects)
        .add(ContextConstants.CONTEXT_SHARED_USERS, sharedUsers)
        .build();

    // Define Mocks
    Mockito.when(mockHttpServletRequest.getSession()).thenReturn(mockHttpSession);
    Mockito.when(mockHttpSession.getAttribute(SessionConstants.SESSION_LOGGED_IN_USER)).thenReturn(loggedInUserRepresentation);
    Mockito.when(mockModel.addAttribute(SessionConstants.SESSION_LOGGED_IN_USER, loggedInUserRepresentation)).thenReturn(mockModel);
    Mockito.when(mockModel.addAttribute(ModelConstants.MODEL_PROJECT, projectListingRepresentation)).thenReturn(mockModel);
    Mockito.when(mockModel.addAttribute(ModelConstants.MODEL_PROJECTS, projects)).thenReturn(mockModel);
    Mockito.when(mockModel.addAttribute(ModelConstants.MODEL_SHARED_PROJECTS, sharedProjects)).thenReturn(mockModel);
    Mockito.when(mockModel.addAttribute(ModelConstants.MODEL_SHARED_USERS, sharedUsers)).thenReturn(mockModel);
    Mockito.when(mockModel.addAttribute(Mockito.eq(ModelConstants.MODEL_SHARE_PROJECT), Mockito.any(ShareProjectRepresentation.class))).thenReturn(mockModel);
    Mockito.when(mockProjectListingService.getProjects(Mockito.any())).thenReturn(responseCommandDTO);
    Mockito.doNothing().when(mockHttpSession).setAttribute(ModelConstants.MODEL_SHARED_PROJECTS, sharedProjects);

    // Execute
    Exception expectedException = null;
    String result = null;
    try {
      result = projectListingController.projects(mockModel, mockHttpServletRequest, projectListingRepresentation);
    } catch (Exception exception) {
      expectedException = exception;
    }

    // Verify
    Mockito.verify(mockModel, Mockito.times(1)).addAttribute(SessionConstants.SESSION_LOGGED_IN_USER, loggedInUserRepresentation);
    Mockito.verify(mockModel, Mockito.times(1)).addAttribute(ModelConstants.MODEL_PROJECT, projectListingRepresentation);
    Mockito.verify(mockModel, Mockito.times(1)).addAttribute(ModelConstants.MODEL_PROJECTS, projects);
    Mockito.verify(mockModel, Mockito.times(1)).addAttribute(Mockito.eq(ModelConstants.MODEL_SHARED_PROJECTS), Mockito.any());
    Mockito.verify(mockModel, Mockito.times(1)).addAttribute(Mockito.eq(ModelConstants.MODEL_SHARED_USERS), Mockito.any());
    Mockito.verify(mockModel, Mockito.times(1)).addAttribute(Mockito.eq(ModelConstants.MODEL_SHARE_PROJECT), Mockito.any(ShareProjectRepresentation.class));
    Mockito.verify(mockHttpSession, Mockito.times(1)).setAttribute(Mockito.eq(ModelConstants.MODEL_SHARED_PROJECTS), Mockito.any());
    Mockito.verify(mockProjectListingService, Mockito.times(1)).getProjects(projectDetailCommandDTOCaptor.capture());

    // Assertions
    assert expectedException == null;
    assert projectDetailCommandDTOCaptor.getValue().get(ContextConstants.CONTEXT_LOGGED_IN_USER) == loggedInUserRepresentation;
    assert result.equals(ViewConstants.VIEW_HOME);
  }

  @Test
  public void testProjectsException() {
    // Initialize
    Model mockModel = Mockito.mock(Model.class);
    HttpServletRequest mockHttpServletRequest = Mockito.mock(HttpServletRequest.class);
    HttpSession mockHttpSession = Mockito.mock(HttpSession.class);

    ArgumentCaptor<CommandDTO> projectDetailCommandDTOCaptor = ArgumentCaptor.forClass(CommandDTO.class);
    ArgumentCaptor<ErrorsRepresentation> errorsRepresentationCaptor = ArgumentCaptor.forClass(ErrorsRepresentation.class);

    LoggedInUserRepresentation loggedInUserRepresentation = LoggedInUserRepresentation.builder().userName("username").build();
    ProjectListingRepresentation projectListingRepresentation = ProjectListingRepresentation.builder().id(1L).build();

    ProjectListingService mockProjectListingService = Mockito.mock(ProjectListingService.class);

    ProjectListingController projectListingController = new ProjectListingController();
    projectListingController.projectListingService = mockProjectListingService;

    List<ProjectListingRepresentation> projects = new ArrayList<>();
    projects.add(ProjectListingRepresentation.builder().id(1L).build());

    List<ProjectListingRepresentation> sharedProjects = new ArrayList<>();
    projects.add(ProjectListingRepresentation.builder().id(1L).build());

    List<SharedUserRepresentation> sharedUsers = new ArrayList<>();
    sharedUsers.add(SharedUserRepresentation.builder().id(1L).build());

    Map<String, String> fieldMessages = new HashMap<>();
    fieldMessages.put("field1", "message1");
    ProjectsForUserGetFailedException responseException = new ProjectsForUserGetFailedException();
    responseException.setMessages(Collections.singletonList("message"));
    responseException.setFieldMessages(fieldMessages);

    // Define Mocks
    Mockito.when(mockHttpServletRequest.getSession()).thenReturn(mockHttpSession);
    Mockito.when(mockHttpSession.getAttribute(SessionConstants.SESSION_LOGGED_IN_USER)).thenReturn(loggedInUserRepresentation);
    Mockito.when(mockModel.addAttribute(SessionConstants.SESSION_LOGGED_IN_USER, loggedInUserRepresentation)).thenReturn(mockModel);
    Mockito.when(mockModel.addAttribute(ModelConstants.MODEL_PROJECT, projectListingRepresentation)).thenReturn(mockModel);
    Mockito.when(mockModel.addAttribute(ModelConstants.MODEL_PROJECTS, projects)).thenReturn(mockModel);
    Mockito.when(mockModel.addAttribute(ModelConstants.MODEL_SHARED_PROJECTS, sharedProjects)).thenReturn(mockModel);
    Mockito.when(mockModel.addAttribute(ModelConstants.MODEL_SHARED_USERS, sharedUsers)).thenReturn(mockModel);
    Mockito.when(mockModel.addAttribute(Mockito.eq(ModelConstants.MODEL_SHARE_PROJECT), Mockito.any(ShareProjectRepresentation.class))).thenReturn(mockModel);
    Mockito.when(mockModel.addAttribute(Mockito.eq(ModelConstants.MODEL_PROJECTS_FOR_USER_GET_ERRORS), Mockito.any(ErrorsRepresentation.class))).thenReturn(mockModel);
    Mockito.when(mockProjectListingService.getProjects(Mockito.any())).thenThrow(responseException);
    Mockito.doNothing().when(mockHttpSession).setAttribute(ModelConstants.MODEL_SHARED_PROJECTS, sharedProjects);

    // Execute
    Exception expectedException = null;
    String result = null;
    try {
      result = projectListingController.projects(mockModel, mockHttpServletRequest, projectListingRepresentation);
    } catch (Exception exception) {
      expectedException = exception;
    }

    // Verify
    Mockito.verify(mockModel, Mockito.times(1)).addAttribute(SessionConstants.SESSION_LOGGED_IN_USER, loggedInUserRepresentation);
    Mockito.verify(mockModel, Mockito.times(1)).addAttribute(ModelConstants.MODEL_PROJECT, projectListingRepresentation);
    Mockito.verify(mockModel, Mockito.times(0)).addAttribute(ModelConstants.MODEL_PROJECTS, projects);
    Mockito.verify(mockModel, Mockito.times(0)).addAttribute(Mockito.eq(ModelConstants.MODEL_SHARED_PROJECTS), Mockito.any());
    Mockito.verify(mockModel, Mockito.times(0)).addAttribute(Mockito.eq(ModelConstants.MODEL_SHARED_USERS), Mockito.any());
    Mockito.verify(mockModel, Mockito.times(0)).addAttribute(Mockito.eq(ModelConstants.MODEL_SHARE_PROJECT), Mockito.any(ShareProjectRepresentation.class));
    Mockito.verify(mockModel, Mockito.times(1)).addAttribute(Mockito.eq(ModelConstants.MODEL_PROJECTS_FOR_USER_GET_ERRORS), errorsRepresentationCaptor.capture());
    Mockito.verify(mockHttpSession, Mockito.times(0)).setAttribute(Mockito.eq(ModelConstants.MODEL_SHARED_PROJECTS), Mockito.any());
    Mockito.verify(mockProjectListingService, Mockito.times(1)).getProjects(projectDetailCommandDTOCaptor.capture());

    // Assertions
    assert expectedException == null;
    assert projectDetailCommandDTOCaptor.getValue().get(ContextConstants.CONTEXT_LOGGED_IN_USER) == loggedInUserRepresentation;
    assert errorsRepresentationCaptor.getValue().getErrorMessages().get(0).equals("message");
    assert errorsRepresentationCaptor.getValue().getFieldErrorMessages().get("field1").equals("message1");
    assert result.equals(ViewConstants.VIEW_HOME);
  }

  @Test
  public void testCreateProject() {
    // Initialize
    Model mockModel = Mockito.mock(Model.class);
    HttpServletRequest mockHttpServletRequest = Mockito.mock(HttpServletRequest.class);
    HttpSession mockHttpSession = Mockito.mock(HttpSession.class);
    MultipartFile mockMultipartFile = Mockito.mock(MultipartFile.class);

    ArgumentCaptor<CommandDTO> projectDetailCommandDTOCaptor = ArgumentCaptor.forClass(CommandDTO.class);
    ArgumentCaptor<CommandDTO> saveProjectCommandDTOCaptor = ArgumentCaptor.forClass(CommandDTO.class);

    LoggedInUserRepresentation loggedInUserRepresentation = LoggedInUserRepresentation.builder().userName("username").build();
    ProjectListingRepresentation projectListingRepresentation = ProjectListingRepresentation.builder().id(1L).displaySequence(1).build();

    ProjectListingService mockProjectListingService = Mockito.mock(ProjectListingService.class);

    ProjectListingController projectListingController = new ProjectListingController();
    projectListingController.projectListingService = mockProjectListingService;

    List<ProjectListingRepresentation> projects = new ArrayList<>();
    projects.add(ProjectListingRepresentation.builder().id(1L).build());

    List<ProjectListingRepresentation> sharedProjects = new ArrayList<>();
    projects.add(ProjectListingRepresentation.builder().id(1L).build());

    List<SharedUserRepresentation> sharedUsers = new ArrayList<>();
    sharedUsers.add(SharedUserRepresentation.builder().id(1L).build());

    CommandDTO responseCommandDTO = CommandDTO
        .builder()
        .add(ContextConstants.CONTEXT_PROJECTS, projects)
        .add(ModelConstants.MODEL_SHARED_PROJECTS, sharedProjects)
        .add(ContextConstants.CONTEXT_SHARED_USERS, sharedUsers)
        .build();

    // Define Mocks
    Mockito.when(mockHttpServletRequest.getSession()).thenReturn(mockHttpSession);
    Mockito.when(mockHttpSession.getAttribute(SessionConstants.SESSION_LOGGED_IN_USER)).thenReturn(loggedInUserRepresentation);
    Mockito.when(mockModel.addAttribute(SessionConstants.SESSION_LOGGED_IN_USER, loggedInUserRepresentation)).thenReturn(mockModel);
    Mockito.when(mockModel.addAttribute(Mockito.eq(ModelConstants.MODEL_PROJECT), Mockito.any(ProjectListingRepresentation.class))).thenReturn(mockModel);
    Mockito.when(mockModel.addAttribute(ModelConstants.MODEL_PROJECTS, projects)).thenReturn(mockModel);
    Mockito.when(mockModel.addAttribute(ModelConstants.MODEL_SHARED_PROJECTS, sharedProjects)).thenReturn(mockModel);
    Mockito.when(mockModel.addAttribute(ModelConstants.MODEL_SHARED_USERS, sharedUsers)).thenReturn(mockModel);
    Mockito.when(mockModel.addAttribute(ModelConstants.MODEL_SAVE_PROJECT_DISPLAY_SEQUENCE, 1)).thenReturn(mockModel);
    Mockito.when(mockModel.addAttribute(Mockito.eq(ModelConstants.MODEL_SHARE_PROJECT), Mockito.any(ShareProjectRepresentation.class))).thenReturn(mockModel);
    Mockito.when(mockProjectListingService.getProjects(Mockito.any())).thenReturn(responseCommandDTO);
    Mockito.when(mockProjectListingService.saveProject(Mockito.any())).thenReturn(responseCommandDTO);
    Mockito.doNothing().when(mockHttpSession).setAttribute(ModelConstants.MODEL_SHARED_PROJECTS, sharedProjects);

    // Execute
    Exception expectedException = null;
    String result = null;
    try {
      result = projectListingController.createProject(projectListingRepresentation, mockMultipartFile, mockModel, mockHttpServletRequest);
    } catch (Exception exception) {
      expectedException = exception;
    }

    // Verify
    Mockito.verify(mockModel, Mockito.times(1)).addAttribute(SessionConstants.SESSION_LOGGED_IN_USER, loggedInUserRepresentation);
    Mockito.verify(mockModel, Mockito.times(1)).addAttribute(Mockito.eq(ModelConstants.MODEL_PROJECT), Mockito.any(ProjectListingRepresentation.class));
    Mockito.verify(mockModel, Mockito.times(1)).addAttribute(ModelConstants.MODEL_PROJECTS, projects);
    Mockito.verify(mockModel, Mockito.times(1)).addAttribute(Mockito.eq(ModelConstants.MODEL_SHARED_PROJECTS), Mockito.any());
    Mockito.verify(mockModel, Mockito.times(1)).addAttribute(Mockito.eq(ModelConstants.MODEL_SHARED_USERS), Mockito.any());
    Mockito.verify(mockModel, Mockito.times(1)).addAttribute(ModelConstants.MODEL_SAVE_PROJECT_DISPLAY_SEQUENCE, 1);
    Mockito.verify(mockModel, Mockito.times(1)).addAttribute(Mockito.eq(ModelConstants.MODEL_SHARE_PROJECT), Mockito.any(ShareProjectRepresentation.class));
    Mockito.verify(mockHttpSession, Mockito.times(1)).setAttribute(Mockito.eq(ModelConstants.MODEL_SHARED_PROJECTS), Mockito.any());
    Mockito.verify(mockProjectListingService, Mockito.times(1)).getProjects(projectDetailCommandDTOCaptor.capture());
    Mockito.verify(mockProjectListingService, Mockito.times(1)).saveProject(saveProjectCommandDTOCaptor.capture());

    // Assertions
    assert expectedException == null;
    assert projectDetailCommandDTOCaptor.getValue().get(ContextConstants.CONTEXT_LOGGED_IN_USER) == loggedInUserRepresentation;
    assert saveProjectCommandDTOCaptor.getValue().get(ContextConstants.CONTEXT_LOGGED_IN_USER) == loggedInUserRepresentation;
    assert saveProjectCommandDTOCaptor.getValue().get(ContextConstants.CONTEXT_PROJECT) == projectListingRepresentation;
    assert saveProjectCommandDTOCaptor.getValue().get(ContextConstants.CONTEXT_PROJECT_PROJECT_IMAGE_FILE) == mockMultipartFile;
    assert result.equals(ViewConstants.VIEW_HOME);
  }

  @Test
  public void testCreateProjectException() {
    // Initialize
    Model mockModel = Mockito.mock(Model.class);
    HttpServletRequest mockHttpServletRequest = Mockito.mock(HttpServletRequest.class);
    HttpSession mockHttpSession = Mockito.mock(HttpSession.class);
    MultipartFile mockMultipartFile = Mockito.mock(MultipartFile.class);

    ArgumentCaptor<CommandDTO> projectDetailCommandDTOCaptor = ArgumentCaptor.forClass(CommandDTO.class);
    ArgumentCaptor<CommandDTO> saveProjectCommandDTOCaptor = ArgumentCaptor.forClass(CommandDTO.class);
    ArgumentCaptor<ErrorsRepresentation> errorsRepresentationCaptor = ArgumentCaptor.forClass(ErrorsRepresentation.class);

    LoggedInUserRepresentation loggedInUserRepresentation = LoggedInUserRepresentation.builder().userName("username").build();
    ProjectListingRepresentation projectListingRepresentation = ProjectListingRepresentation.builder().id(1L).displaySequence(1).build();

    ProjectListingService mockProjectListingService = Mockito.mock(ProjectListingService.class);

    Map<String, String> fieldMessages = new HashMap<>();
    fieldMessages.put("field1", "message1");
    ProjectSaveFailedException responseException = new ProjectSaveFailedException();
    responseException.setMessages(Collections.singletonList("message"));
    responseException.setFieldMessages(fieldMessages);

    ProjectListingController projectListingController = new ProjectListingController();
    projectListingController.projectListingService = mockProjectListingService;

    List<ProjectListingRepresentation> projects = new ArrayList<>();
    projects.add(ProjectListingRepresentation.builder().id(1L).build());

    List<ProjectListingRepresentation> sharedProjects = new ArrayList<>();
    projects.add(ProjectListingRepresentation.builder().id(1L).build());

    List<SharedUserRepresentation> sharedUsers = new ArrayList<>();
    sharedUsers.add(SharedUserRepresentation.builder().id(1L).build());

    CommandDTO responseCommandDTO = CommandDTO
        .builder()
        .add(ContextConstants.CONTEXT_PROJECTS, projects)
        .add(ModelConstants.MODEL_SHARED_PROJECTS, sharedProjects)
        .add(ContextConstants.CONTEXT_SHARED_USERS, sharedUsers)
        .build();

    // Define Mocks
    Mockito.when(mockHttpServletRequest.getSession()).thenReturn(mockHttpSession);
    Mockito.when(mockHttpSession.getAttribute(SessionConstants.SESSION_LOGGED_IN_USER)).thenReturn(loggedInUserRepresentation);
    Mockito.when(mockModel.addAttribute(SessionConstants.SESSION_LOGGED_IN_USER, loggedInUserRepresentation)).thenReturn(mockModel);
    Mockito.when(mockModel.addAttribute(Mockito.eq(ModelConstants.MODEL_PROJECT), Mockito.any(ProjectListingRepresentation.class))).thenReturn(mockModel);
    Mockito.when(mockModel.addAttribute(ModelConstants.MODEL_PROJECTS, projects)).thenReturn(mockModel);
    Mockito.when(mockModel.addAttribute(ModelConstants.MODEL_SHARED_PROJECTS, sharedProjects)).thenReturn(mockModel);
    Mockito.when(mockModel.addAttribute(ModelConstants.MODEL_SHARED_USERS, sharedUsers)).thenReturn(mockModel);
    Mockito.when(mockModel.addAttribute(ModelConstants.MODEL_SAVE_PROJECT_DISPLAY_SEQUENCE, 1)).thenReturn(mockModel);
    Mockito.when(mockModel.addAttribute(Mockito.eq(ModelConstants.MODEL_SAVE_PROJECT_ERRORS), Mockito.any(ErrorsRepresentation.class))).thenReturn(mockModel);
    Mockito.when(mockModel.addAttribute(Mockito.eq(ModelConstants.MODEL_SHARE_PROJECT), Mockito.any(ShareProjectRepresentation.class))).thenReturn(mockModel);
    Mockito.when(mockModel.addAttribute(ModelConstants.MODEL_LISTING_OPERATION, ModelConstants.MODEL_LISTING_OPERATION_UPDATE)).thenReturn(mockModel);
    Mockito.when(mockProjectListingService.getProjects(Mockito.any())).thenReturn(responseCommandDTO);
    Mockito.when(mockProjectListingService.saveProject(Mockito.any())).thenThrow(responseException);
    Mockito.doNothing().when(mockHttpSession).setAttribute(ModelConstants.MODEL_SHARED_PROJECTS, sharedProjects);

    // Execute
    Exception expectedException = null;
    String result = null;
    try {
      result = projectListingController.createProject(projectListingRepresentation, mockMultipartFile, mockModel, mockHttpServletRequest);
    } catch (Exception exception) {
      expectedException = exception;
    }

    // Verify
    Mockito.verify(mockModel, Mockito.times(1)).addAttribute(SessionConstants.SESSION_LOGGED_IN_USER, loggedInUserRepresentation);
    Mockito.verify(mockModel, Mockito.times(1)).addAttribute(Mockito.eq(ModelConstants.MODEL_PROJECT), Mockito.any(ProjectListingRepresentation.class));
    Mockito.verify(mockModel, Mockito.times(1)).addAttribute(ModelConstants.MODEL_PROJECTS, projects);
    Mockito.verify(mockModel, Mockito.times(1)).addAttribute(Mockito.eq(ModelConstants.MODEL_SHARED_PROJECTS), Mockito.any());
    Mockito.verify(mockModel, Mockito.times(1)).addAttribute(Mockito.eq(ModelConstants.MODEL_SHARED_USERS), Mockito.any());
    Mockito.verify(mockModel, Mockito.times(0)).addAttribute(ModelConstants.MODEL_SAVE_PROJECT_DISPLAY_SEQUENCE, 1);
    Mockito.verify(mockModel, Mockito.times(1)).addAttribute(ModelConstants.MODEL_LISTING_OPERATION, ModelConstants.MODEL_LISTING_OPERATION_UPDATE);
    Mockito.verify(mockModel, Mockito.times(1)).addAttribute(Mockito.eq(ModelConstants.MODEL_SAVE_PROJECT_ERRORS), errorsRepresentationCaptor.capture());
    Mockito.verify(mockModel, Mockito.times(1)).addAttribute(Mockito.eq(ModelConstants.MODEL_SHARE_PROJECT), Mockito.any(ShareProjectRepresentation.class));
    Mockito.verify(mockHttpSession, Mockito.times(1)).setAttribute(Mockito.eq(ModelConstants.MODEL_SHARED_PROJECTS), Mockito.any());
    Mockito.verify(mockProjectListingService, Mockito.times(1)).getProjects(projectDetailCommandDTOCaptor.capture());
    Mockito.verify(mockProjectListingService, Mockito.times(1)).saveProject(saveProjectCommandDTOCaptor.capture());

    // Assertions
    assert expectedException == null;
    assert projectDetailCommandDTOCaptor.getValue().get(ContextConstants.CONTEXT_LOGGED_IN_USER) == loggedInUserRepresentation;
    assert saveProjectCommandDTOCaptor.getValue().get(ContextConstants.CONTEXT_LOGGED_IN_USER) == loggedInUserRepresentation;
    assert saveProjectCommandDTOCaptor.getValue().get(ContextConstants.CONTEXT_PROJECT) == projectListingRepresentation;
    assert saveProjectCommandDTOCaptor.getValue().get(ContextConstants.CONTEXT_PROJECT_PROJECT_IMAGE_FILE) == mockMultipartFile;
    assert errorsRepresentationCaptor.getValue().getErrorMessages().get(0).equals("message");
    assert errorsRepresentationCaptor.getValue().getFieldErrorMessages().get("field1").equals("message1");
    assert result.equals(ViewConstants.VIEW_HOME);
  }

  @Test
  public void testDeleteProject() {
    // Initialize
    Model mockModel = Mockito.mock(Model.class);
    HttpServletRequest mockHttpServletRequest = Mockito.mock(HttpServletRequest.class);
    HttpSession mockHttpSession = Mockito.mock(HttpSession.class);

    ArgumentCaptor<CommandDTO> projectDetailCommandDTOCaptor = ArgumentCaptor.forClass(CommandDTO.class);
    ArgumentCaptor<CommandDTO> deleteProjectCommandDTOCaptor = ArgumentCaptor.forClass(CommandDTO.class);

    LoggedInUserRepresentation loggedInUserRepresentation = LoggedInUserRepresentation.builder().userName("username").build();

    ProjectListingService mockProjectListingService = Mockito.mock(ProjectListingService.class);

    ProjectListingController projectListingController = new ProjectListingController();
    projectListingController.projectListingService = mockProjectListingService;

    List<ProjectListingRepresentation> projects = new ArrayList<>();
    projects.add(ProjectListingRepresentation.builder().id(1L).build());

    List<ProjectListingRepresentation> sharedProjects = new ArrayList<>();
    projects.add(ProjectListingRepresentation.builder().id(1L).build());

    List<SharedUserRepresentation> sharedUsers = new ArrayList<>();
    sharedUsers.add(SharedUserRepresentation.builder().id(1L).build());

    CommandDTO responseCommandDTO = CommandDTO
        .builder()
        .add(ContextConstants.CONTEXT_PROJECTS, projects)
        .add(ModelConstants.MODEL_SHARED_PROJECTS, sharedProjects)
        .add(ContextConstants.CONTEXT_SHARED_USERS, sharedUsers)
        .build();

    // Define Mocks
    Mockito.when(mockHttpServletRequest.getSession()).thenReturn(mockHttpSession);
    Mockito.when(mockHttpSession.getAttribute(SessionConstants.SESSION_LOGGED_IN_USER)).thenReturn(loggedInUserRepresentation);
    Mockito.when(mockModel.addAttribute(SessionConstants.SESSION_LOGGED_IN_USER, loggedInUserRepresentation)).thenReturn(mockModel);
    Mockito.when(mockModel.addAttribute(Mockito.eq(ModelConstants.MODEL_PROJECT), Mockito.any(ProjectListingRepresentation.class))).thenReturn(mockModel);
    Mockito.when(mockModel.addAttribute(ModelConstants.MODEL_PROJECTS, projects)).thenReturn(mockModel);
    Mockito.when(mockModel.addAttribute(ModelConstants.MODEL_SHARED_PROJECTS, sharedProjects)).thenReturn(mockModel);
    Mockito.when(mockModel.addAttribute(ModelConstants.MODEL_SHARED_USERS, sharedUsers)).thenReturn(mockModel);
    Mockito.when(mockModel.addAttribute(Mockito.eq(ModelConstants.MODEL_SHARE_PROJECT), Mockito.any(ShareProjectRepresentation.class))).thenReturn(mockModel);
    Mockito.when(mockProjectListingService.getProjects(Mockito.any())).thenReturn(responseCommandDTO);
    Mockito.when(mockProjectListingService.deleteProject(Mockito.any())).thenReturn(responseCommandDTO);
    Mockito.doNothing().when(mockHttpSession).setAttribute(ModelConstants.MODEL_SHARED_PROJECTS, sharedProjects);

    // Execute
    Exception expectedException = null;
    String result = null;
    try {
      result = projectListingController.deleteProject(1L, mockModel, mockHttpServletRequest);
    } catch (Exception exception) {
      expectedException = exception;
    }

    // Verify
    Mockito.verify(mockModel, Mockito.times(1)).addAttribute(SessionConstants.SESSION_LOGGED_IN_USER, loggedInUserRepresentation);
    Mockito.verify(mockModel, Mockito.times(1)).addAttribute(Mockito.eq(ModelConstants.MODEL_PROJECT), Mockito.any(ProjectListingRepresentation.class));
    Mockito.verify(mockModel, Mockito.times(1)).addAttribute(ModelConstants.MODEL_PROJECTS, projects);
    Mockito.verify(mockModel, Mockito.times(1)).addAttribute(Mockito.eq(ModelConstants.MODEL_SHARED_PROJECTS), Mockito.any());
    Mockito.verify(mockModel, Mockito.times(1)).addAttribute(Mockito.eq(ModelConstants.MODEL_SHARED_USERS), Mockito.any());
    Mockito.verify(mockModel, Mockito.times(1)).addAttribute(Mockito.eq(ModelConstants.MODEL_SHARE_PROJECT), Mockito.any(ShareProjectRepresentation.class));
    Mockito.verify(mockHttpSession, Mockito.times(1)).setAttribute(Mockito.eq(ModelConstants.MODEL_SHARED_PROJECTS), Mockito.any());
    Mockito.verify(mockProjectListingService, Mockito.times(1)).getProjects(projectDetailCommandDTOCaptor.capture());
    Mockito.verify(mockProjectListingService, Mockito.times(1)).deleteProject(deleteProjectCommandDTOCaptor.capture());

    // Assertions
    assert expectedException == null;
    assert projectDetailCommandDTOCaptor.getValue().get(ContextConstants.CONTEXT_LOGGED_IN_USER) == loggedInUserRepresentation;
    assert ((Long) deleteProjectCommandDTOCaptor.getValue().get(ContextConstants.CONTEXT_PROJECT_ID)) == 1L;
    assert result.equals(ViewConstants.VIEW_HOME);
  }

  @Test
  public void testDeleteProjectException() {
    // Initialize
    Model mockModel = Mockito.mock(Model.class);
    HttpServletRequest mockHttpServletRequest = Mockito.mock(HttpServletRequest.class);
    HttpSession mockHttpSession = Mockito.mock(HttpSession.class);

    ArgumentCaptor<CommandDTO> projectDetailCommandDTOCaptor = ArgumentCaptor.forClass(CommandDTO.class);
    ArgumentCaptor<CommandDTO> deleteProjectCommandDTOCaptor = ArgumentCaptor.forClass(CommandDTO.class);
    ArgumentCaptor<ErrorsRepresentation> errorsRepresentationCaptor = ArgumentCaptor.forClass(ErrorsRepresentation.class);

    LoggedInUserRepresentation loggedInUserRepresentation = LoggedInUserRepresentation.builder().userName("username").build();

    ProjectListingService mockProjectListingService = Mockito.mock(ProjectListingService.class);

    ProjectListingController projectListingController = new ProjectListingController();
    projectListingController.projectListingService = mockProjectListingService;

    List<ProjectListingRepresentation> projects = new ArrayList<>();
    projects.add(ProjectListingRepresentation.builder().id(1L).build());

    List<ProjectListingRepresentation> sharedProjects = new ArrayList<>();
    projects.add(ProjectListingRepresentation.builder().id(1L).build());

    List<SharedUserRepresentation> sharedUsers = new ArrayList<>();
    sharedUsers.add(SharedUserRepresentation.builder().id(1L).build());

    Map<String, String> fieldMessages = new HashMap<>();
    fieldMessages.put("field1", "message1");
    ProjectDeleteFailedException responseException = new ProjectDeleteFailedException();
    responseException.setMessages(Collections.singletonList("message"));
    responseException.setFieldMessages(fieldMessages);

    CommandDTO responseCommandDTO = CommandDTO
        .builder()
        .add(ContextConstants.CONTEXT_PROJECTS, projects)
        .add(ModelConstants.MODEL_SHARED_PROJECTS, sharedProjects)
        .add(ContextConstants.CONTEXT_SHARED_USERS, sharedUsers)
        .build();

    // Define Mocks
    Mockito.when(mockHttpServletRequest.getSession()).thenReturn(mockHttpSession);
    Mockito.when(mockHttpSession.getAttribute(SessionConstants.SESSION_LOGGED_IN_USER)).thenReturn(loggedInUserRepresentation);
    Mockito.when(mockModel.addAttribute(SessionConstants.SESSION_LOGGED_IN_USER, loggedInUserRepresentation)).thenReturn(mockModel);
    Mockito.when(mockModel.addAttribute(Mockito.eq(ModelConstants.MODEL_PROJECT), Mockito.any(ProjectListingRepresentation.class))).thenReturn(mockModel);
    Mockito.when(mockModel.addAttribute(ModelConstants.MODEL_PROJECTS, projects)).thenReturn(mockModel);
    Mockito.when(mockModel.addAttribute(ModelConstants.MODEL_SHARED_PROJECTS, sharedProjects)).thenReturn(mockModel);
    Mockito.when(mockModel.addAttribute(ModelConstants.MODEL_SHARED_USERS, sharedUsers)).thenReturn(mockModel);
    Mockito.when(mockModel.addAttribute(Mockito.eq(ModelConstants.MODEL_SHARE_PROJECT), Mockito.any(ShareProjectRepresentation.class))).thenReturn(mockModel);
    Mockito.when(mockModel.addAttribute(Mockito.eq(ModelConstants.MODEL_DELETE_PROJECT_ERRORS), Mockito.any(ErrorsRepresentation.class))).thenReturn(mockModel);
    Mockito.when(mockProjectListingService.getProjects(Mockito.any())).thenReturn(responseCommandDTO);
    Mockito.when(mockProjectListingService.deleteProject(Mockito.any())).thenThrow(responseException);
    Mockito.doNothing().when(mockHttpSession).setAttribute(ModelConstants.MODEL_SHARED_PROJECTS, sharedProjects);

    // Execute
    Exception expectedException = null;
    String result = null;
    try {
      result = projectListingController.deleteProject(1L, mockModel, mockHttpServletRequest);
    } catch (Exception exception) {
      expectedException = exception;
    }

    // Verify
    Mockito.verify(mockModel, Mockito.times(1)).addAttribute(SessionConstants.SESSION_LOGGED_IN_USER, loggedInUserRepresentation);
    Mockito.verify(mockModel, Mockito.times(1)).addAttribute(Mockito.eq(ModelConstants.MODEL_PROJECT), Mockito.any(ProjectListingRepresentation.class));
    Mockito.verify(mockModel, Mockito.times(1)).addAttribute(ModelConstants.MODEL_PROJECTS, projects);
    Mockito.verify(mockModel, Mockito.times(1)).addAttribute(Mockito.eq(ModelConstants.MODEL_SHARED_PROJECTS), Mockito.any());
    Mockito.verify(mockModel, Mockito.times(1)).addAttribute(Mockito.eq(ModelConstants.MODEL_SHARED_USERS), Mockito.any());
    Mockito.verify(mockModel, Mockito.times(1)).addAttribute(Mockito.eq(ModelConstants.MODEL_SHARE_PROJECT), Mockito.any(ShareProjectRepresentation.class));
    Mockito.verify(mockModel, Mockito.times(1)).addAttribute(Mockito.eq(ModelConstants.MODEL_DELETE_PROJECT_ERRORS), errorsRepresentationCaptor.capture());
    Mockito.verify(mockHttpSession, Mockito.times(1)).setAttribute(Mockito.eq(ModelConstants.MODEL_SHARED_PROJECTS), Mockito.any());
    Mockito.verify(mockProjectListingService, Mockito.times(1)).getProjects(projectDetailCommandDTOCaptor.capture());
    Mockito.verify(mockProjectListingService, Mockito.times(1)).deleteProject(deleteProjectCommandDTOCaptor.capture());

    // Assertions
    assert expectedException == null;
    assert projectDetailCommandDTOCaptor.getValue().get(ContextConstants.CONTEXT_LOGGED_IN_USER) == loggedInUserRepresentation;
    assert ((Long) deleteProjectCommandDTOCaptor.getValue().get(ContextConstants.CONTEXT_PROJECT_ID)) == 1L;
    assert errorsRepresentationCaptor.getValue().getErrorMessages().get(0).equals("message");
    assert errorsRepresentationCaptor.getValue().getFieldErrorMessages().get("field1").equals("message1");
    assert result.equals(ViewConstants.VIEW_HOME);
  }
}
