package com.doityourself.workshop.features.project.media.controller;

import com.doityourself.workshop.common.command.CommandDTO;
import com.doityourself.workshop.common.constants.ContextConstants;
import com.doityourself.workshop.common.constants.ModelConstants;
import com.doityourself.workshop.common.constants.SessionConstants;
import com.doityourself.workshop.common.constants.ViewConstants;
import com.doityourself.workshop.common.enums.WebResourceType;
import com.doityourself.workshop.common.representation.error.ErrorsRepresentation;
import com.doityourself.workshop.features.login.representation.LoggedInUserRepresentation;
import com.doityourself.workshop.features.project.detail.exception.GetProjectDetailFailedException;
import com.doityourself.workshop.features.project.material.representation.ProjectDetailRepresentation;
import com.doityourself.workshop.features.project.media.exception.*;
import com.doityourself.workshop.features.project.media.representation.ProjectDetailLocalResourceRepresentation;
import com.doityourself.workshop.features.project.media.representation.ProjectDetailWebResourceRepresentation;
import com.doityourself.workshop.features.project.media.service.ProjectMediaService;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import org.springframework.ui.Model;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class ProjectMediaControllerTest {
  @Test
  public void testGetProjectDetail() {
    // Initialize
    Model mockModel = Mockito.mock(Model.class);
    HttpServletRequest mockHttpServletRequest = Mockito.mock(HttpServletRequest.class);
    HttpSession mockHttpSession = Mockito.mock(HttpSession.class);

    ProjectMediaService mockProjectMediaService = Mockito.mock(ProjectMediaService.class);

    ArgumentCaptor<CommandDTO> projectDetailCommandDTOCaptor = ArgumentCaptor.forClass(CommandDTO.class);

    ProjectMediaController projectMediaController = new ProjectMediaController();
    projectMediaController.projectMediaService = mockProjectMediaService;

    LoggedInUserRepresentation loggedInUserRepresentation = LoggedInUserRepresentation.builder().userName("username").build();
    ProjectDetailRepresentation projectDetailRepresentation = ProjectDetailRepresentation.builder().id(1L).build();

    CommandDTO responseCommandDTO = CommandDTO
        .builder()
        .add(ContextConstants.CONTEXT_PROJECT_DETAIL, projectDetailRepresentation)
        .build();

    // Define Mocks
    Mockito.when(mockHttpServletRequest.getSession()).thenReturn(mockHttpSession);
    Mockito.when(mockHttpSession.getAttribute(SessionConstants.SESSION_LOGGED_IN_USER)).thenReturn(loggedInUserRepresentation);
    Mockito.when(mockModel.addAttribute(SessionConstants.SESSION_LOGGED_IN_USER, loggedInUserRepresentation)).thenReturn(mockModel);
    Mockito.when(mockProjectMediaService.getProjectDetail(Mockito.any())).thenReturn(responseCommandDTO);
    Mockito.when(mockModel.addAttribute(ModelConstants.MODEL_PROJECT_DETAIL, projectDetailRepresentation)).thenReturn(mockModel);
    Mockito.when(mockModel.addAttribute(Mockito.eq(ModelConstants.MODEL_ADD_PROJECT_LOCAL_RESOURCE), Mockito.any(ProjectDetailLocalResourceRepresentation.class))).thenReturn(mockModel);
    Mockito.when(mockModel.addAttribute(Mockito.eq(ModelConstants.MODEL_ADD_PROJECT_WEB_RESOURCE), Mockito.any(ProjectDetailWebResourceRepresentation.class))).thenReturn(mockModel);
    Mockito.when(mockModel.addAttribute(ModelConstants.MODEL_MEDIA_LISTING_OPERATION, ModelConstants.MODEL_MEDIA_LISTING_OPERATION_PICTURES)).thenReturn(mockModel);

    // Execute
    Exception expectedException = null;
    String result = null;
    try {
      result = projectMediaController.getProjectDetail(1L, mockModel, mockHttpServletRequest);
    } catch (Exception exception) {
      expectedException = exception;
    }

    // Verify
    Mockito.verify(mockModel, Mockito.times(1)).addAttribute(SessionConstants.SESSION_LOGGED_IN_USER, loggedInUserRepresentation);
    Mockito.verify(mockProjectMediaService, Mockito.times(1)).getProjectDetail(projectDetailCommandDTOCaptor.capture());
    Mockito.verify(mockModel, Mockito.times(1)).addAttribute(ModelConstants.MODEL_PROJECT_DETAIL, projectDetailRepresentation);
    Mockito.verify(mockModel, Mockito.times(1)).addAttribute(Mockito.eq(ModelConstants.MODEL_ADD_PROJECT_LOCAL_RESOURCE), Mockito.any(ProjectDetailLocalResourceRepresentation.class));
    Mockito.verify(mockModel, Mockito.times(1)).addAttribute(Mockito.eq(ModelConstants.MODEL_ADD_PROJECT_WEB_RESOURCE), Mockito.any(ProjectDetailWebResourceRepresentation.class));
    Mockito.verify(mockModel, Mockito.times(1)).addAttribute(ModelConstants.MODEL_MEDIA_LISTING_OPERATION, ModelConstants.MODEL_MEDIA_LISTING_OPERATION_PICTURES);

    // Assertions
    assert expectedException == null;
    assert (Long) projectDetailCommandDTOCaptor.getValue().get(ContextConstants.CONTEXT_PROJECT_ID) == 1L;
    assert result.equals(ViewConstants.VIEW_MEDIA);
  }

  @Test
  public void testGetProjectDetailException() {
    // Initialize
    Model mockModel = Mockito.mock(Model.class);
    HttpServletRequest mockHttpServletRequest = Mockito.mock(HttpServletRequest.class);
    HttpSession mockHttpSession = Mockito.mock(HttpSession.class);

    ProjectMediaService mockProjectMediaService = Mockito.mock(ProjectMediaService.class);

    ArgumentCaptor<CommandDTO> projectDetailCommandDTOCaptor = ArgumentCaptor.forClass(CommandDTO.class);
    ArgumentCaptor<ErrorsRepresentation> errorsRepresentationCaptor = ArgumentCaptor.forClass(ErrorsRepresentation.class);

    ProjectMediaController projectMediaController = new ProjectMediaController();
    projectMediaController.projectMediaService = mockProjectMediaService;

    LoggedInUserRepresentation loggedInUserRepresentation = LoggedInUserRepresentation.builder().userName("username").build();
    ProjectDetailRepresentation projectDetailRepresentation = ProjectDetailRepresentation.builder().id(1L).build();

    Map<String, String> fieldMessages = new HashMap<>();
    fieldMessages.put("field1", "message1");
    GetProjectDetailFailedException responseException = new GetProjectDetailFailedException();
    responseException.setFieldMessages(fieldMessages);
    responseException.setMessages(Collections.singletonList("message1"));

    CommandDTO responseCommandDTO = CommandDTO
        .builder()
        .add(ContextConstants.CONTEXT_PROJECT_DETAIL, projectDetailRepresentation)
        .build();

    // Define Mocks
    Mockito.when(mockHttpServletRequest.getSession()).thenReturn(mockHttpSession);
    Mockito.when(mockHttpSession.getAttribute(SessionConstants.SESSION_LOGGED_IN_USER)).thenReturn(loggedInUserRepresentation);
    Mockito.when(mockModel.addAttribute(SessionConstants.SESSION_LOGGED_IN_USER, loggedInUserRepresentation)).thenReturn(mockModel);
    Mockito.when(mockProjectMediaService.getProjectDetail(Mockito.any())).thenThrow(responseException);
    Mockito.when(mockModel.addAttribute(Mockito.eq(ModelConstants.MODEL_PROJECT_DETAIL), Mockito.any(ProjectDetailRepresentation.class))).thenReturn(mockModel);
    Mockito.when(mockModel.addAttribute(Mockito.eq(ModelConstants.MODEL_ADD_PROJECT_LOCAL_RESOURCE), Mockito.any(ProjectDetailLocalResourceRepresentation.class))).thenReturn(mockModel);
    Mockito.when(mockModel.addAttribute(Mockito.eq(ModelConstants.MODEL_ADD_PROJECT_WEB_RESOURCE), Mockito.any(ProjectDetailWebResourceRepresentation.class))).thenReturn(mockModel);
    Mockito.when(mockModel.addAttribute(Mockito.eq(ModelConstants.MODEL_GET_PROJECT_DETAIL_ERRORS), Mockito.any(ErrorsRepresentation.class))).thenReturn(mockModel);
    Mockito.when(mockModel.addAttribute(ModelConstants.MODEL_MEDIA_LISTING_OPERATION, ModelConstants.MODEL_MEDIA_LISTING_OPERATION_PICTURES)).thenReturn(mockModel);

    // Execute
    Exception expectedException = null;
    String result = null;
    try {
      result = projectMediaController.getProjectDetail(1L, mockModel, mockHttpServletRequest);
    } catch (Exception exception) {
      expectedException = exception;
    }

    // Verify
    Mockito.verify(mockModel, Mockito.times(1)).addAttribute(SessionConstants.SESSION_LOGGED_IN_USER, loggedInUserRepresentation);
    Mockito.verify(mockProjectMediaService, Mockito.times(1)).getProjectDetail(projectDetailCommandDTOCaptor.capture());
    Mockito.verify(mockModel, Mockito.times(0)).addAttribute(Mockito.eq(ModelConstants.MODEL_PROJECT_DETAIL), Mockito.any(ProjectDetailRepresentation.class));
    Mockito.verify(mockModel, Mockito.times(0)).addAttribute(Mockito.eq(ModelConstants.MODEL_ADD_PROJECT_LOCAL_RESOURCE), Mockito.any(ProjectDetailLocalResourceRepresentation.class));
    Mockito.verify(mockModel, Mockito.times(0)).addAttribute(Mockito.eq(ModelConstants.MODEL_ADD_PROJECT_WEB_RESOURCE), Mockito.any(ProjectDetailWebResourceRepresentation.class));
    Mockito.verify(mockModel, Mockito.times(1)).addAttribute(Mockito.eq(ModelConstants.MODEL_GET_PROJECT_DETAIL_ERRORS), errorsRepresentationCaptor.capture());
    Mockito.verify(mockModel, Mockito.times(0)).addAttribute(ModelConstants.MODEL_MEDIA_LISTING_OPERATION, ModelConstants.MODEL_MEDIA_LISTING_OPERATION_PICTURES);

    // Assertions
    assert expectedException == null;
    assert (Long) projectDetailCommandDTOCaptor.getValue().get(ContextConstants.CONTEXT_PROJECT_ID) == 1L;
    assert errorsRepresentationCaptor.getValue().getErrorMessages().get(0).equals("message1");
    assert errorsRepresentationCaptor.getValue().getFieldErrorMessages().get("field1").equals("message1");
    assert result.equals(ViewConstants.VIEW_MEDIA);
  }

  @Test
  public void testAddProjectLocalResource() {
    // Initialize
    Model mockModel = Mockito.mock(Model.class);
    HttpServletRequest mockHttpServletRequest = Mockito.mock(HttpServletRequest.class);
    HttpSession mockHttpSession = Mockito.mock(HttpSession.class);
    MultipartFile mockMultipartFile = Mockito.mock(MultipartFile.class);

    ProjectMediaService mockProjectMediaService = Mockito.mock(ProjectMediaService.class);

    ArgumentCaptor<CommandDTO> projectDetailCommandDTOCaptor = ArgumentCaptor.forClass(CommandDTO.class);
    ArgumentCaptor<CommandDTO> saveProjectLocalResourceCommandDTOCaptor = ArgumentCaptor.forClass(CommandDTO.class);

    ProjectMediaController projectMediaController = new ProjectMediaController();
    projectMediaController.projectMediaService = mockProjectMediaService;

    LoggedInUserRepresentation loggedInUserRepresentation = LoggedInUserRepresentation.builder().userName("username").build();
    ProjectDetailRepresentation projectDetailRepresentation = ProjectDetailRepresentation.builder().id(1L).build();
    ProjectDetailLocalResourceRepresentation projectDetailLocalResourceRepresentation = ProjectDetailLocalResourceRepresentation
        .builder()
        .id(1L).resourceType("IMAGE").displaySequence(1)
        .build();

    CommandDTO responseCommandDTO = CommandDTO
        .builder()
        .add(ContextConstants.CONTEXT_PROJECT_DETAIL, projectDetailRepresentation)
        .build();

    CommandDTO saveResponseCommandDTO = CommandDTO
        .builder()
        .add(ContextConstants.CONTEXT_PROJECT_LOCAL_RESOURCE_ARTIFACT, "IMAGE")
        .build();

    // Define Mocks
    Mockito.when(mockHttpServletRequest.getSession()).thenReturn(mockHttpSession);
    Mockito.when(mockHttpSession.getAttribute(SessionConstants.SESSION_LOGGED_IN_USER)).thenReturn(loggedInUserRepresentation);
    Mockito.when(mockModel.addAttribute(SessionConstants.SESSION_LOGGED_IN_USER, loggedInUserRepresentation)).thenReturn(mockModel);
    Mockito.when(mockProjectMediaService.getProjectDetail(Mockito.any())).thenReturn(responseCommandDTO);
    Mockito.when(mockProjectMediaService.saveProjectLocalResource(Mockito.any())).thenReturn(saveResponseCommandDTO);
    Mockito.when(mockModel.addAttribute(ModelConstants.MODEL_PROJECT_DETAIL, projectDetailRepresentation)).thenReturn(mockModel);
    Mockito.when(mockModel.addAttribute(Mockito.eq(ModelConstants.MODEL_ADD_PROJECT_LOCAL_RESOURCE), Mockito.any(ProjectDetailLocalResourceRepresentation.class))).thenReturn(mockModel);
    Mockito.when(mockModel.addAttribute(Mockito.eq(ModelConstants.MODEL_ADD_PROJECT_WEB_RESOURCE), Mockito.any(ProjectDetailWebResourceRepresentation.class))).thenReturn(mockModel);
    Mockito.when(mockModel.addAttribute(ModelConstants.MODEL_MEDIA_LISTING_OPERATION, ModelConstants.MODEL_MEDIA_LISTING_OPERATION_PICTURES)).thenReturn(mockModel);
    Mockito.when(mockModel.addAttribute(ModelConstants.MODEL_PROJECT_IMAGE_RESOURCE_ARTIFACT, true)).thenReturn(mockModel);
    Mockito.when(mockModel.addAttribute(ModelConstants.MODEL_SAVE_PICTURE_DISPLAY_SEQUENCE, 1)).thenReturn(mockModel);

    // Execute
    Exception expectedException = null;
    String result = null;
    try {
      result = projectMediaController.addProjectLocalResource(projectDetailLocalResourceRepresentation, mockMultipartFile, mockModel, mockHttpServletRequest, 1L);
    } catch (Exception exception) {
      expectedException = exception;
    }

    // Verify
    Mockito.verify(mockModel, Mockito.times(1)).addAttribute(SessionConstants.SESSION_LOGGED_IN_USER, loggedInUserRepresentation);
    Mockito.verify(mockProjectMediaService, Mockito.times(1)).getProjectDetail(projectDetailCommandDTOCaptor.capture());
    Mockito.verify(mockProjectMediaService, Mockito.times(1)).saveProjectLocalResource(saveProjectLocalResourceCommandDTOCaptor.capture());
    Mockito.verify(mockModel, Mockito.times(1)).addAttribute(ModelConstants.MODEL_PROJECT_DETAIL, projectDetailRepresentation);
    Mockito.verify(mockModel, Mockito.times(1)).addAttribute(Mockito.eq(ModelConstants.MODEL_ADD_PROJECT_LOCAL_RESOURCE), Mockito.any(ProjectDetailLocalResourceRepresentation.class));
    Mockito.verify(mockModel, Mockito.times(1)).addAttribute(Mockito.eq(ModelConstants.MODEL_ADD_PROJECT_WEB_RESOURCE), Mockito.any(ProjectDetailWebResourceRepresentation.class));
    Mockito.verify(mockModel, Mockito.times(1)).addAttribute(ModelConstants.MODEL_MEDIA_LISTING_OPERATION, ModelConstants.MODEL_MEDIA_LISTING_OPERATION_PICTURES);
    Mockito.verify(mockModel, Mockito.times(1)).addAttribute(ModelConstants.MODEL_PROJECT_IMAGE_RESOURCE_ARTIFACT, true);
    Mockito.verify(mockModel, Mockito.times(1)).addAttribute(ModelConstants.MODEL_SAVE_PICTURE_DISPLAY_SEQUENCE, 1);

    // Assertions
    assert expectedException == null;
    assert (Long) projectDetailCommandDTOCaptor.getValue().get(ContextConstants.CONTEXT_PROJECT_ID) == 1L;
    assert (Long) saveProjectLocalResourceCommandDTOCaptor.getValue().get(ContextConstants.CONTEXT_PROJECT_ID) == 1L;
    assert saveProjectLocalResourceCommandDTOCaptor.getValue().get(ContextConstants.CONTEXT_PROJECT_LOCAL_RESOURCE) == projectDetailLocalResourceRepresentation;
    assert saveProjectLocalResourceCommandDTOCaptor.getValue().get(ContextConstants.CONTEXT_PROJECT_LOCAL_RESOURCE_FILE) == mockMultipartFile;
    assert result.equals(ViewConstants.VIEW_MEDIA);
  }

  @Test
  public void testAddProjectLocalResourceGetProjectDetailFailedException() {
    // Initialize
    Model mockModel = Mockito.mock(Model.class);
    HttpServletRequest mockHttpServletRequest = Mockito.mock(HttpServletRequest.class);
    HttpSession mockHttpSession = Mockito.mock(HttpSession.class);
    MultipartFile mockMultipartFile = Mockito.mock(MultipartFile.class);

    ProjectMediaService mockProjectMediaService = Mockito.mock(ProjectMediaService.class);

    ArgumentCaptor<CommandDTO> projectDetailCommandDTOCaptor = ArgumentCaptor.forClass(CommandDTO.class);
    ArgumentCaptor<CommandDTO> saveProjectLocalResourceCommandDTOCaptor = ArgumentCaptor.forClass(CommandDTO.class);
    ArgumentCaptor<ErrorsRepresentation> errorsRepresentationCaptor = ArgumentCaptor.forClass(ErrorsRepresentation.class);

    ProjectMediaController projectMediaController = new ProjectMediaController();
    projectMediaController.projectMediaService = mockProjectMediaService;

    LoggedInUserRepresentation loggedInUserRepresentation = LoggedInUserRepresentation.builder().userName("username").build();
    ProjectDetailRepresentation projectDetailRepresentation = ProjectDetailRepresentation.builder().id(1L).build();
    ProjectDetailLocalResourceRepresentation projectDetailLocalResourceRepresentation = ProjectDetailLocalResourceRepresentation
        .builder()
        .id(1L).resourceType("IMAGE").displaySequence(1)
        .build();

    CommandDTO responseCommandDTO = CommandDTO
        .builder()
        .add(ContextConstants.CONTEXT_PROJECT_DETAIL, projectDetailRepresentation)
        .build();

    Map<String, String> fieldMessages = new HashMap<>();
    fieldMessages.put("field1", "message1");
    GetProjectDetailFailedException responseException = new GetProjectDetailFailedException();
    responseException.setFieldMessages(fieldMessages);
    responseException.setMessages(Collections.singletonList("message1"));

    // Define Mocks
    Mockito.when(mockHttpServletRequest.getSession()).thenReturn(mockHttpSession);
    Mockito.when(mockHttpSession.getAttribute(SessionConstants.SESSION_LOGGED_IN_USER)).thenReturn(loggedInUserRepresentation);
    Mockito.when(mockModel.addAttribute(SessionConstants.SESSION_LOGGED_IN_USER, loggedInUserRepresentation)).thenReturn(mockModel);
    Mockito.when(mockProjectMediaService.getProjectDetail(Mockito.any())).thenReturn(responseCommandDTO);
    Mockito.when(mockProjectMediaService.saveProjectLocalResource(Mockito.any())).thenThrow(responseException);
    Mockito.when(mockModel.addAttribute(ModelConstants.MODEL_PROJECT_DETAIL, projectDetailRepresentation)).thenReturn(mockModel);
    Mockito.when(mockModel.addAttribute(Mockito.eq(ModelConstants.MODEL_ADD_PROJECT_LOCAL_RESOURCE), Mockito.any(ProjectDetailLocalResourceRepresentation.class))).thenReturn(mockModel);
    Mockito.when(mockModel.addAttribute(Mockito.eq(ModelConstants.MODEL_ADD_PROJECT_WEB_RESOURCE), Mockito.any(ProjectDetailWebResourceRepresentation.class))).thenReturn(mockModel);
    Mockito.when(mockModel.addAttribute(ModelConstants.MODEL_MEDIA_LISTING_OPERATION, ModelConstants.MODEL_MEDIA_LISTING_OPERATION_PICTURES)).thenReturn(mockModel);
    Mockito.when(mockModel.addAttribute(ModelConstants.MODEL_PROJECT_IMAGE_RESOURCE_ARTIFACT, true)).thenReturn(mockModel);
    Mockito.when(mockModel.addAttribute(ModelConstants.MODEL_SAVE_PICTURE_DISPLAY_SEQUENCE, 1)).thenReturn(mockModel);
    Mockito.when(mockModel.addAttribute(ModelConstants.MODEL_ADD_PROJECT_LOCAL_RESOURCE, projectDetailLocalResourceRepresentation)).thenReturn(mockModel);
    Mockito.when(mockModel.addAttribute(ModelConstants.MODEL_MEDIA_LISTING_OPERATION, "pictures_save_errors")).thenReturn(mockModel);
    Mockito.when(mockModel.addAttribute(Mockito.eq(ModelConstants.MODEL_ADD_PROJECT_LOCAL_RESOURCE_ERRORS), Mockito.any(ErrorsRepresentation.class))).thenReturn(mockModel);

    // Execute
    Exception expectedException = null;
    String result = null;
    try {
      result = projectMediaController.addProjectLocalResource(projectDetailLocalResourceRepresentation, mockMultipartFile, mockModel, mockHttpServletRequest, 1L);
    } catch (Exception exception) {
      expectedException = exception;
    }

    // Verify
    Mockito.verify(mockModel, Mockito.times(1)).addAttribute(SessionConstants.SESSION_LOGGED_IN_USER, loggedInUserRepresentation);
    Mockito.verify(mockProjectMediaService, Mockito.times(1)).getProjectDetail(projectDetailCommandDTOCaptor.capture());
    Mockito.verify(mockProjectMediaService, Mockito.times(1)).saveProjectLocalResource(saveProjectLocalResourceCommandDTOCaptor.capture());
    Mockito.verify(mockModel, Mockito.times(1)).addAttribute(ModelConstants.MODEL_PROJECT_DETAIL, projectDetailRepresentation);
    Mockito.verify(mockModel, Mockito.times(1)).addAttribute(Mockito.eq(ModelConstants.MODEL_ADD_PROJECT_WEB_RESOURCE), Mockito.any(ProjectDetailWebResourceRepresentation.class));
    Mockito.verify(mockModel, Mockito.times(1)).addAttribute(ModelConstants.MODEL_MEDIA_LISTING_OPERATION, ModelConstants.MODEL_MEDIA_LISTING_OPERATION_PICTURES);
    Mockito.verify(mockModel, Mockito.times(1)).addAttribute(ModelConstants.MODEL_PROJECT_IMAGE_RESOURCE_ARTIFACT, true);
    Mockito.verify(mockModel, Mockito.times(1)).addAttribute(ModelConstants.MODEL_SAVE_PICTURE_DISPLAY_SEQUENCE, 1);
    Mockito.verify(mockModel, Mockito.times(1)).addAttribute(ModelConstants.MODEL_ADD_PROJECT_LOCAL_RESOURCE, projectDetailLocalResourceRepresentation);
    Mockito.verify(mockModel, Mockito.times(1)).addAttribute(ModelConstants.MODEL_MEDIA_LISTING_OPERATION, "pictures_save_errors");
    Mockito.verify(mockModel, Mockito.times(1)).addAttribute(Mockito.eq(ModelConstants.MODEL_ADD_PROJECT_LOCAL_RESOURCE_ERRORS), errorsRepresentationCaptor.capture());

    // Assertions
    assert expectedException == null;
    assert (Long) projectDetailCommandDTOCaptor.getValue().get(ContextConstants.CONTEXT_PROJECT_ID) == 1L;
    assert (Long) saveProjectLocalResourceCommandDTOCaptor.getValue().get(ContextConstants.CONTEXT_PROJECT_ID) == 1L;
    assert saveProjectLocalResourceCommandDTOCaptor.getValue().get(ContextConstants.CONTEXT_PROJECT_LOCAL_RESOURCE) == projectDetailLocalResourceRepresentation;
    assert saveProjectLocalResourceCommandDTOCaptor.getValue().get(ContextConstants.CONTEXT_PROJECT_LOCAL_RESOURCE_FILE) == mockMultipartFile;
    assert errorsRepresentationCaptor.getValue().getErrorMessages().get(0).equals("message1");
    assert errorsRepresentationCaptor.getValue().getFieldErrorMessages().get("field1").equals("message1");
    assert result.equals(ViewConstants.VIEW_MEDIA);
  }

  @Test
  public void testAddProjectLocalResourceSaveProjectLocalResourceFailedException() {
    // Initialize
    Model mockModel = Mockito.mock(Model.class);
    HttpServletRequest mockHttpServletRequest = Mockito.mock(HttpServletRequest.class);
    HttpSession mockHttpSession = Mockito.mock(HttpSession.class);
    MultipartFile mockMultipartFile = Mockito.mock(MultipartFile.class);

    ProjectMediaService mockProjectMediaService = Mockito.mock(ProjectMediaService.class);

    ArgumentCaptor<CommandDTO> projectDetailCommandDTOCaptor = ArgumentCaptor.forClass(CommandDTO.class);
    ArgumentCaptor<CommandDTO> saveProjectLocalResourceCommandDTOCaptor = ArgumentCaptor.forClass(CommandDTO.class);
    ArgumentCaptor<ErrorsRepresentation> errorsRepresentationCaptor = ArgumentCaptor.forClass(ErrorsRepresentation.class);

    ProjectMediaController projectMediaController = new ProjectMediaController();
    projectMediaController.projectMediaService = mockProjectMediaService;

    LoggedInUserRepresentation loggedInUserRepresentation = LoggedInUserRepresentation.builder().userName("username").build();
    ProjectDetailRepresentation projectDetailRepresentation = ProjectDetailRepresentation.builder().id(1L).build();
    ProjectDetailLocalResourceRepresentation projectDetailLocalResourceRepresentation = ProjectDetailLocalResourceRepresentation
        .builder()
        .id(1L).resourceType("IMAGE").displaySequence(1)
        .build();

    CommandDTO responseCommandDTO = CommandDTO
        .builder()
        .add(ContextConstants.CONTEXT_PROJECT_DETAIL, projectDetailRepresentation)
        .build();

    Map<String, String> fieldMessages = new HashMap<>();
    fieldMessages.put("field1", "message1");
    SaveProjectLocalResourceFailedException responseException = new SaveProjectLocalResourceFailedException();
    responseException.setFieldMessages(fieldMessages);
    responseException.setMessages(Collections.singletonList("message1"));

    // Define Mocks
    Mockito.when(mockHttpServletRequest.getSession()).thenReturn(mockHttpSession);
    Mockito.when(mockHttpSession.getAttribute(SessionConstants.SESSION_LOGGED_IN_USER)).thenReturn(loggedInUserRepresentation);
    Mockito.when(mockModel.addAttribute(SessionConstants.SESSION_LOGGED_IN_USER, loggedInUserRepresentation)).thenReturn(mockModel);
    Mockito.when(mockProjectMediaService.getProjectDetail(Mockito.any())).thenReturn(responseCommandDTO);
    Mockito.when(mockProjectMediaService.saveProjectLocalResource(Mockito.any())).thenThrow(responseException);
    Mockito.when(mockModel.addAttribute(ModelConstants.MODEL_PROJECT_DETAIL, projectDetailRepresentation)).thenReturn(mockModel);
    Mockito.when(mockModel.addAttribute(Mockito.eq(ModelConstants.MODEL_ADD_PROJECT_LOCAL_RESOURCE), Mockito.any(ProjectDetailLocalResourceRepresentation.class))).thenReturn(mockModel);
    Mockito.when(mockModel.addAttribute(Mockito.eq(ModelConstants.MODEL_ADD_PROJECT_WEB_RESOURCE), Mockito.any(ProjectDetailWebResourceRepresentation.class))).thenReturn(mockModel);
    Mockito.when(mockModel.addAttribute(ModelConstants.MODEL_MEDIA_LISTING_OPERATION, ModelConstants.MODEL_MEDIA_LISTING_OPERATION_PICTURES)).thenReturn(mockModel);
    Mockito.when(mockModel.addAttribute(ModelConstants.MODEL_PROJECT_IMAGE_RESOURCE_ARTIFACT, true)).thenReturn(mockModel);
    Mockito.when(mockModel.addAttribute(ModelConstants.MODEL_SAVE_PICTURE_DISPLAY_SEQUENCE, 1)).thenReturn(mockModel);
    Mockito.when(mockModel.addAttribute(ModelConstants.MODEL_ADD_PROJECT_LOCAL_RESOURCE, projectDetailLocalResourceRepresentation)).thenReturn(mockModel);
    Mockito.when(mockModel.addAttribute(ModelConstants.MODEL_MEDIA_LISTING_OPERATION, "pictures_save_errors")).thenReturn(mockModel);
    Mockito.when(mockModel.addAttribute(Mockito.eq(ModelConstants.MODEL_ADD_PROJECT_LOCAL_RESOURCE_ERRORS), Mockito.any(ErrorsRepresentation.class))).thenReturn(mockModel);

    // Execute
    Exception expectedException = null;
    String result = null;
    try {
      result = projectMediaController.addProjectLocalResource(projectDetailLocalResourceRepresentation, mockMultipartFile, mockModel, mockHttpServletRequest, 1L);
    } catch (Exception exception) {
      expectedException = exception;
    }

    // Verify
    Mockito.verify(mockModel, Mockito.times(1)).addAttribute(SessionConstants.SESSION_LOGGED_IN_USER, loggedInUserRepresentation);
    Mockito.verify(mockProjectMediaService, Mockito.times(1)).getProjectDetail(projectDetailCommandDTOCaptor.capture());
    Mockito.verify(mockProjectMediaService, Mockito.times(1)).saveProjectLocalResource(saveProjectLocalResourceCommandDTOCaptor.capture());
    Mockito.verify(mockModel, Mockito.times(1)).addAttribute(ModelConstants.MODEL_PROJECT_DETAIL, projectDetailRepresentation);
    Mockito.verify(mockModel, Mockito.times(1)).addAttribute(Mockito.eq(ModelConstants.MODEL_ADD_PROJECT_WEB_RESOURCE), Mockito.any(ProjectDetailWebResourceRepresentation.class));
    Mockito.verify(mockModel, Mockito.times(1)).addAttribute(ModelConstants.MODEL_MEDIA_LISTING_OPERATION, ModelConstants.MODEL_MEDIA_LISTING_OPERATION_PICTURES);
    Mockito.verify(mockModel, Mockito.times(1)).addAttribute(ModelConstants.MODEL_PROJECT_IMAGE_RESOURCE_ARTIFACT, true);
    Mockito.verify(mockModel, Mockito.times(1)).addAttribute(ModelConstants.MODEL_SAVE_PICTURE_DISPLAY_SEQUENCE, 1);
    Mockito.verify(mockModel, Mockito.times(1)).addAttribute(ModelConstants.MODEL_ADD_PROJECT_LOCAL_RESOURCE, projectDetailLocalResourceRepresentation);
    Mockito.verify(mockModel, Mockito.times(1)).addAttribute(ModelConstants.MODEL_MEDIA_LISTING_OPERATION, "pictures_save_errors");
    Mockito.verify(mockModel, Mockito.times(1)).addAttribute(Mockito.eq(ModelConstants.MODEL_ADD_PROJECT_LOCAL_RESOURCE_ERRORS), errorsRepresentationCaptor.capture());

    // Assertions
    assert expectedException == null;
    assert (Long) projectDetailCommandDTOCaptor.getValue().get(ContextConstants.CONTEXT_PROJECT_ID) == 1L;
    assert (Long) saveProjectLocalResourceCommandDTOCaptor.getValue().get(ContextConstants.CONTEXT_PROJECT_ID) == 1L;
    assert saveProjectLocalResourceCommandDTOCaptor.getValue().get(ContextConstants.CONTEXT_PROJECT_LOCAL_RESOURCE) == projectDetailLocalResourceRepresentation;
    assert saveProjectLocalResourceCommandDTOCaptor.getValue().get(ContextConstants.CONTEXT_PROJECT_LOCAL_RESOURCE_FILE) == mockMultipartFile;
    assert errorsRepresentationCaptor.getValue().getErrorMessages().get(0).equals("message1");
    assert errorsRepresentationCaptor.getValue().getFieldErrorMessages().get("field1").equals("message1");
    assert result.equals(ViewConstants.VIEW_MEDIA);
  }

  @Test
  public void testAddProjectWebResource() {
    // Initialize
    Model mockModel = Mockito.mock(Model.class);
    HttpServletRequest mockHttpServletRequest = Mockito.mock(HttpServletRequest.class);
    HttpSession mockHttpSession = Mockito.mock(HttpSession.class);

    ProjectMediaService mockProjectMediaService = Mockito.mock(ProjectMediaService.class);

    ArgumentCaptor<CommandDTO> projectDetailCommandDTOCaptor = ArgumentCaptor.forClass(CommandDTO.class);
    ArgumentCaptor<CommandDTO> saveProjectLocalResourceCommandDTOCaptor = ArgumentCaptor.forClass(CommandDTO.class);

    ProjectMediaController projectMediaController = new ProjectMediaController();
    projectMediaController.projectMediaService = mockProjectMediaService;

    LoggedInUserRepresentation loggedInUserRepresentation = LoggedInUserRepresentation.builder().userName("username").build();
    ProjectDetailRepresentation projectDetailRepresentation = ProjectDetailRepresentation.builder().id(1L).build();
    ProjectDetailWebResourceRepresentation projectDetailWebResourceRepresentation = ProjectDetailWebResourceRepresentation
        .builder()
        .id(1L).resourceType("IMAGE").displaySequence(1)
        .build();

    CommandDTO responseCommandDTO = CommandDTO
        .builder()
        .add(ContextConstants.CONTEXT_PROJECT_DETAIL, projectDetailRepresentation)
        .build();

    CommandDTO saveResponseCommandDTO = CommandDTO.builder().build();

    // Define Mocks
    Mockito.when(mockHttpServletRequest.getSession()).thenReturn(mockHttpSession);
    Mockito.when(mockHttpSession.getAttribute(SessionConstants.SESSION_LOGGED_IN_USER)).thenReturn(loggedInUserRepresentation);
    Mockito.when(mockModel.addAttribute(SessionConstants.SESSION_LOGGED_IN_USER, loggedInUserRepresentation)).thenReturn(mockModel);
    Mockito.when(mockProjectMediaService.getProjectDetail(Mockito.any())).thenReturn(responseCommandDTO);
    Mockito.when(mockProjectMediaService.saveProjectWebResource(Mockito.any())).thenReturn(saveResponseCommandDTO);
    Mockito.when(mockModel.addAttribute(ModelConstants.MODEL_PROJECT_DETAIL, projectDetailRepresentation)).thenReturn(mockModel);
    Mockito.when(mockModel.addAttribute(Mockito.eq(ModelConstants.MODEL_ADD_PROJECT_LOCAL_RESOURCE), Mockito.any(ProjectDetailLocalResourceRepresentation.class))).thenReturn(mockModel);
    Mockito.when(mockModel.addAttribute(Mockito.eq(ModelConstants.MODEL_ADD_PROJECT_WEB_RESOURCE), Mockito.any(ProjectDetailWebResourceRepresentation.class))).thenReturn(mockModel);
    Mockito.when(mockModel.addAttribute(ModelConstants.MODEL_MEDIA_LISTING_OPERATION, "weblinks_save_success")).thenReturn(mockModel);
    Mockito.when(mockModel.addAttribute(ModelConstants.MODEL_PROJECT_WEB_RESOURCE_ARTIFACT, true)).thenReturn(mockModel);
    Mockito.when(mockModel.addAttribute(ModelConstants.MODEL_SAVE_WEBLINK_DISPLAY_SEQUENCE, 1)).thenReturn(mockModel);

    // Execute
    Exception expectedException = null;
    String result = null;
    try {
      result = projectMediaController.addProjectWebResource(projectDetailWebResourceRepresentation, mockModel, mockHttpServletRequest, 1L);
    } catch (Exception exception) {
      expectedException = exception;
    }

    // Verify
    Mockito.verify(mockModel, Mockito.times(1)).addAttribute(SessionConstants.SESSION_LOGGED_IN_USER, loggedInUserRepresentation);
    Mockito.verify(mockProjectMediaService, Mockito.times(1)).getProjectDetail(projectDetailCommandDTOCaptor.capture());
    Mockito.verify(mockProjectMediaService, Mockito.times(1)).saveProjectWebResource(saveProjectLocalResourceCommandDTOCaptor.capture());
    Mockito.verify(mockModel, Mockito.times(1)).addAttribute(ModelConstants.MODEL_PROJECT_DETAIL, projectDetailRepresentation);
    Mockito.verify(mockModel, Mockito.times(1)).addAttribute(Mockito.eq(ModelConstants.MODEL_ADD_PROJECT_LOCAL_RESOURCE), Mockito.any(ProjectDetailLocalResourceRepresentation.class));
    Mockito.verify(mockModel, Mockito.times(1)).addAttribute(Mockito.eq(ModelConstants.MODEL_ADD_PROJECT_WEB_RESOURCE), Mockito.any(ProjectDetailWebResourceRepresentation.class));
    Mockito.verify(mockModel, Mockito.times(1)).addAttribute(ModelConstants.MODEL_MEDIA_LISTING_OPERATION, "weblinks_save_success");
    Mockito.verify(mockModel, Mockito.times(1)).addAttribute(ModelConstants.MODEL_PROJECT_WEB_RESOURCE_ARTIFACT, true);
    Mockito.verify(mockModel, Mockito.times(1)).addAttribute(ModelConstants.MODEL_SAVE_WEBLINK_DISPLAY_SEQUENCE, 1);

    // Assertions
    assert expectedException == null;
    assert (Long) projectDetailCommandDTOCaptor.getValue().get(ContextConstants.CONTEXT_PROJECT_ID) == 1L;
    assert (Long) saveProjectLocalResourceCommandDTOCaptor.getValue().get(ContextConstants.CONTEXT_PROJECT_ID) == 1L;
    assert saveProjectLocalResourceCommandDTOCaptor.getValue().get(ContextConstants.CONTEXT_PROJECT_WEB_RESOURCE) == projectDetailWebResourceRepresentation;
    assert result.equals(ViewConstants.VIEW_MEDIA);
  }

  @Test
  public void testAddProjectWebResourceGetProjectDetailFailedException() {
    // Initialize
    Model mockModel = Mockito.mock(Model.class);
    HttpServletRequest mockHttpServletRequest = Mockito.mock(HttpServletRequest.class);
    HttpSession mockHttpSession = Mockito.mock(HttpSession.class);

    ProjectMediaService mockProjectMediaService = Mockito.mock(ProjectMediaService.class);

    ArgumentCaptor<CommandDTO> projectDetailCommandDTOCaptor = ArgumentCaptor.forClass(CommandDTO.class);
    ArgumentCaptor<CommandDTO> saveProjectLocalResourceCommandDTOCaptor = ArgumentCaptor.forClass(CommandDTO.class);
    ArgumentCaptor<ErrorsRepresentation> errorsRepresentationCaptor = ArgumentCaptor.forClass(ErrorsRepresentation.class);

    ProjectMediaController projectMediaController = new ProjectMediaController();
    projectMediaController.projectMediaService = mockProjectMediaService;

    LoggedInUserRepresentation loggedInUserRepresentation = LoggedInUserRepresentation.builder().userName("username").build();
    ProjectDetailRepresentation projectDetailRepresentation = ProjectDetailRepresentation.builder().id(1L).build();
    ProjectDetailWebResourceRepresentation projectDetailWebResourceRepresentation = ProjectDetailWebResourceRepresentation
        .builder()
        .id(1L).resourceType("IMAGE").displaySequence(1)
        .build();

    CommandDTO responseCommandDTO = CommandDTO
        .builder()
        .add(ContextConstants.CONTEXT_PROJECT_DETAIL, projectDetailRepresentation)
        .build();

    Map<String, String> fieldMessages = new HashMap<>();
    fieldMessages.put("field1", "message1");
    GetProjectDetailFailedException responseException = new GetProjectDetailFailedException();
    responseException.setFieldMessages(fieldMessages);
    responseException.setMessages(Collections.singletonList("message1"));

    // Define Mocks
    Mockito.when(mockHttpServletRequest.getSession()).thenReturn(mockHttpSession);
    Mockito.when(mockHttpSession.getAttribute(SessionConstants.SESSION_LOGGED_IN_USER)).thenReturn(loggedInUserRepresentation);
    Mockito.when(mockModel.addAttribute(SessionConstants.SESSION_LOGGED_IN_USER, loggedInUserRepresentation)).thenReturn(mockModel);
    Mockito.when(mockProjectMediaService.getProjectDetail(Mockito.any())).thenReturn(responseCommandDTO);
    Mockito.when(mockProjectMediaService.saveProjectWebResource(Mockito.any())).thenThrow(responseException);
    Mockito.when(mockModel.addAttribute(ModelConstants.MODEL_PROJECT_DETAIL, projectDetailRepresentation)).thenReturn(mockModel);
    Mockito.when(mockModel.addAttribute(Mockito.eq(ModelConstants.MODEL_ADD_PROJECT_LOCAL_RESOURCE), Mockito.any(ProjectDetailLocalResourceRepresentation.class))).thenReturn(mockModel);
    Mockito.when(mockModel.addAttribute(Mockito.eq(ModelConstants.MODEL_ADD_PROJECT_WEB_RESOURCE), Mockito.any(ProjectDetailWebResourceRepresentation.class))).thenReturn(mockModel);
    Mockito.when(mockModel.addAttribute(ModelConstants.MODEL_MEDIA_LISTING_OPERATION, "weblinks_save_errors")).thenReturn(mockModel);
    Mockito.when(mockModel.addAttribute(ModelConstants.MODEL_PROJECT_WEB_RESOURCE_ARTIFACT, true)).thenReturn(mockModel);
    Mockito.when(mockModel.addAttribute(ModelConstants.MODEL_SAVE_WEBLINK_DISPLAY_SEQUENCE, 1)).thenReturn(mockModel);
    Mockito.when(mockModel.addAttribute(Mockito.eq(ModelConstants.MODEL_ADD_PROJECT_WEB_RESOURCE_ERRORS), Mockito.any(ErrorsRepresentation.class))).thenReturn(mockModel);

    // Execute
    Exception expectedException = null;
    String result = null;
    try {
      result = projectMediaController.addProjectWebResource(projectDetailWebResourceRepresentation, mockModel, mockHttpServletRequest, 1L);
    } catch (Exception exception) {
      expectedException = exception;
    }

    // Verify
    Mockito.verify(mockModel, Mockito.times(1)).addAttribute(SessionConstants.SESSION_LOGGED_IN_USER, loggedInUserRepresentation);
    Mockito.verify(mockProjectMediaService, Mockito.times(1)).getProjectDetail(projectDetailCommandDTOCaptor.capture());
    Mockito.verify(mockProjectMediaService, Mockito.times(1)).saveProjectWebResource(saveProjectLocalResourceCommandDTOCaptor.capture());
    Mockito.verify(mockModel, Mockito.times(1)).addAttribute(ModelConstants.MODEL_PROJECT_DETAIL, projectDetailRepresentation);
    Mockito.verify(mockModel, Mockito.times(1)).addAttribute(Mockito.eq(ModelConstants.MODEL_ADD_PROJECT_LOCAL_RESOURCE), Mockito.any(ProjectDetailLocalResourceRepresentation.class));
    Mockito.verify(mockModel, Mockito.times(1)).addAttribute(ModelConstants.MODEL_MEDIA_LISTING_OPERATION, "weblinks_save_errors");
    Mockito.verify(mockModel, Mockito.times(1)).addAttribute(ModelConstants.MODEL_PROJECT_WEB_RESOURCE_ARTIFACT, true);
    Mockito.verify(mockModel, Mockito.times(1)).addAttribute(ModelConstants.MODEL_SAVE_WEBLINK_DISPLAY_SEQUENCE, 1);
    Mockito.verify(mockModel, Mockito.times(1)).addAttribute(Mockito.eq(ModelConstants.MODEL_ADD_PROJECT_WEB_RESOURCE_ERRORS), errorsRepresentationCaptor.capture());

    // Assertions
    assert expectedException == null;
    assert (Long) projectDetailCommandDTOCaptor.getValue().get(ContextConstants.CONTEXT_PROJECT_ID) == 1L;
    assert (Long) saveProjectLocalResourceCommandDTOCaptor.getValue().get(ContextConstants.CONTEXT_PROJECT_ID) == 1L;
    assert saveProjectLocalResourceCommandDTOCaptor.getValue().get(ContextConstants.CONTEXT_PROJECT_WEB_RESOURCE) == projectDetailWebResourceRepresentation;
    assert errorsRepresentationCaptor.getValue().getErrorMessages().get(0).equals("message1");
    assert errorsRepresentationCaptor.getValue().getFieldErrorMessages().get("field1").equals("message1");
    assert result.equals(ViewConstants.VIEW_MEDIA);
  }

  @Test
  public void testAddProjectWebResourceSaveProjectWebResourceFailedException() {
    // Initialize
    Model mockModel = Mockito.mock(Model.class);
    HttpServletRequest mockHttpServletRequest = Mockito.mock(HttpServletRequest.class);
    HttpSession mockHttpSession = Mockito.mock(HttpSession.class);

    ProjectMediaService mockProjectMediaService = Mockito.mock(ProjectMediaService.class);

    ArgumentCaptor<CommandDTO> projectDetailCommandDTOCaptor = ArgumentCaptor.forClass(CommandDTO.class);
    ArgumentCaptor<CommandDTO> saveProjectLocalResourceCommandDTOCaptor = ArgumentCaptor.forClass(CommandDTO.class);
    ArgumentCaptor<ErrorsRepresentation> errorsRepresentationCaptor = ArgumentCaptor.forClass(ErrorsRepresentation.class);

    ProjectMediaController projectMediaController = new ProjectMediaController();
    projectMediaController.projectMediaService = mockProjectMediaService;

    LoggedInUserRepresentation loggedInUserRepresentation = LoggedInUserRepresentation.builder().userName("username").build();
    ProjectDetailRepresentation projectDetailRepresentation = ProjectDetailRepresentation.builder().id(1L).build();
    ProjectDetailWebResourceRepresentation projectDetailWebResourceRepresentation = ProjectDetailWebResourceRepresentation
        .builder()
        .id(1L).resourceType("IMAGE").displaySequence(1)
        .build();

    CommandDTO responseCommandDTO = CommandDTO
        .builder()
        .add(ContextConstants.CONTEXT_PROJECT_DETAIL, projectDetailRepresentation)
        .build();

    Map<String, String> fieldMessages = new HashMap<>();
    fieldMessages.put("field1", "message1");
    SaveProjectWebResourceFailedException responseException = new SaveProjectWebResourceFailedException();
    responseException.setFieldMessages(fieldMessages);
    responseException.setMessages(Collections.singletonList("message1"));

    // Define Mocks
    Mockito.when(mockHttpServletRequest.getSession()).thenReturn(mockHttpSession);
    Mockito.when(mockHttpSession.getAttribute(SessionConstants.SESSION_LOGGED_IN_USER)).thenReturn(loggedInUserRepresentation);
    Mockito.when(mockModel.addAttribute(SessionConstants.SESSION_LOGGED_IN_USER, loggedInUserRepresentation)).thenReturn(mockModel);
    Mockito.when(mockProjectMediaService.getProjectDetail(Mockito.any())).thenReturn(responseCommandDTO);
    Mockito.when(mockProjectMediaService.saveProjectWebResource(Mockito.any())).thenThrow(responseException);
    Mockito.when(mockModel.addAttribute(ModelConstants.MODEL_PROJECT_DETAIL, projectDetailRepresentation)).thenReturn(mockModel);
    Mockito.when(mockModel.addAttribute(Mockito.eq(ModelConstants.MODEL_ADD_PROJECT_LOCAL_RESOURCE), Mockito.any(ProjectDetailLocalResourceRepresentation.class))).thenReturn(mockModel);
    Mockito.when(mockModel.addAttribute(Mockito.eq(ModelConstants.MODEL_ADD_PROJECT_WEB_RESOURCE), Mockito.any(ProjectDetailWebResourceRepresentation.class))).thenReturn(mockModel);
    Mockito.when(mockModel.addAttribute(ModelConstants.MODEL_MEDIA_LISTING_OPERATION, "weblinks_save_errors")).thenReturn(mockModel);
    Mockito.when(mockModel.addAttribute(ModelConstants.MODEL_PROJECT_WEB_RESOURCE_ARTIFACT, true)).thenReturn(mockModel);
    Mockito.when(mockModel.addAttribute(ModelConstants.MODEL_SAVE_WEBLINK_DISPLAY_SEQUENCE, 1)).thenReturn(mockModel);
    Mockito.when(mockModel.addAttribute(Mockito.eq(ModelConstants.MODEL_ADD_PROJECT_WEB_RESOURCE_ERRORS), Mockito.any(ErrorsRepresentation.class))).thenReturn(mockModel);

    // Execute
    Exception expectedException = null;
    String result = null;
    try {
      result = projectMediaController.addProjectWebResource(projectDetailWebResourceRepresentation, mockModel, mockHttpServletRequest, 1L);
    } catch (Exception exception) {
      expectedException = exception;
    }

    // Verify
    Mockito.verify(mockModel, Mockito.times(1)).addAttribute(SessionConstants.SESSION_LOGGED_IN_USER, loggedInUserRepresentation);
    Mockito.verify(mockProjectMediaService, Mockito.times(1)).getProjectDetail(projectDetailCommandDTOCaptor.capture());
    Mockito.verify(mockProjectMediaService, Mockito.times(1)).saveProjectWebResource(saveProjectLocalResourceCommandDTOCaptor.capture());
    Mockito.verify(mockModel, Mockito.times(1)).addAttribute(ModelConstants.MODEL_PROJECT_DETAIL, projectDetailRepresentation);
    Mockito.verify(mockModel, Mockito.times(1)).addAttribute(Mockito.eq(ModelConstants.MODEL_ADD_PROJECT_LOCAL_RESOURCE), Mockito.any(ProjectDetailLocalResourceRepresentation.class));
    Mockito.verify(mockModel, Mockito.times(1)).addAttribute(ModelConstants.MODEL_MEDIA_LISTING_OPERATION, "weblinks_save_errors");
    Mockito.verify(mockModel, Mockito.times(1)).addAttribute(ModelConstants.MODEL_PROJECT_WEB_RESOURCE_ARTIFACT, true);
    Mockito.verify(mockModel, Mockito.times(1)).addAttribute(ModelConstants.MODEL_SAVE_WEBLINK_DISPLAY_SEQUENCE, 1);
    Mockito.verify(mockModel, Mockito.times(1)).addAttribute(Mockito.eq(ModelConstants.MODEL_ADD_PROJECT_WEB_RESOURCE_ERRORS), errorsRepresentationCaptor.capture());

    // Assertions
    assert expectedException == null;
    assert (Long) projectDetailCommandDTOCaptor.getValue().get(ContextConstants.CONTEXT_PROJECT_ID) == 1L;
    assert (Long) saveProjectLocalResourceCommandDTOCaptor.getValue().get(ContextConstants.CONTEXT_PROJECT_ID) == 1L;
    assert saveProjectLocalResourceCommandDTOCaptor.getValue().get(ContextConstants.CONTEXT_PROJECT_WEB_RESOURCE) == projectDetailWebResourceRepresentation;
    assert errorsRepresentationCaptor.getValue().getErrorMessages().get(0).equals("message1");
    assert errorsRepresentationCaptor.getValue().getFieldErrorMessages().get("field1").equals("message1");
    assert result.equals(ViewConstants.VIEW_MEDIA);
  }

  @Test
  public void testUpdateProjectLocalResource() {
    // Initialize
    Model mockModel = Mockito.mock(Model.class);
    HttpServletRequest mockHttpServletRequest = Mockito.mock(HttpServletRequest.class);
    HttpSession mockHttpSession = Mockito.mock(HttpSession.class);
    MultipartFile mockMultipartFile = Mockito.mock(MultipartFile.class);

    ProjectMediaService mockProjectMediaService = Mockito.mock(ProjectMediaService.class);

    ArgumentCaptor<CommandDTO> projectDetailCommandDTOCaptor = ArgumentCaptor.forClass(CommandDTO.class);
    ArgumentCaptor<CommandDTO> saveProjectLocalResourceCommandDTOCaptor = ArgumentCaptor.forClass(CommandDTO.class);

    ProjectMediaController projectMediaController = new ProjectMediaController();
    projectMediaController.projectMediaService = mockProjectMediaService;

    LoggedInUserRepresentation loggedInUserRepresentation = LoggedInUserRepresentation.builder().userName("username").build();
    ProjectDetailRepresentation projectDetailRepresentation = ProjectDetailRepresentation.builder().id(1L).build();
    ProjectDetailLocalResourceRepresentation projectDetailLocalResourceRepresentation = ProjectDetailLocalResourceRepresentation
        .builder()
        .id(1L).resourceType("IMAGE").displaySequence(1)
        .build();

    CommandDTO responseCommandDTO = CommandDTO
        .builder()
        .add(ContextConstants.CONTEXT_PROJECT_DETAIL, projectDetailRepresentation)
        .build();

    CommandDTO saveResponseCommandDTO = CommandDTO
        .builder()
        .add(ContextConstants.CONTEXT_PROJECT_LOCAL_RESOURCE_ARTIFACT, "IMAGE")
        .build();

    // Define Mocks
    Mockito.when(mockHttpServletRequest.getSession()).thenReturn(mockHttpSession);
    Mockito.when(mockHttpSession.getAttribute(SessionConstants.SESSION_LOGGED_IN_USER)).thenReturn(loggedInUserRepresentation);
    Mockito.when(mockModel.addAttribute(SessionConstants.SESSION_LOGGED_IN_USER, loggedInUserRepresentation)).thenReturn(mockModel);
    Mockito.when(mockProjectMediaService.getProjectDetail(Mockito.any())).thenReturn(responseCommandDTO);
    Mockito.when(mockProjectMediaService.updateProjectLocalResource(Mockito.any())).thenReturn(saveResponseCommandDTO);
    Mockito.when(mockModel.addAttribute(ModelConstants.MODEL_PROJECT_DETAIL, projectDetailRepresentation)).thenReturn(mockModel);
    Mockito.when(mockModel.addAttribute(Mockito.eq(ModelConstants.MODEL_ADD_PROJECT_LOCAL_RESOURCE), Mockito.any(ProjectDetailLocalResourceRepresentation.class))).thenReturn(mockModel);
    Mockito.when(mockModel.addAttribute(Mockito.eq(ModelConstants.MODEL_ADD_PROJECT_WEB_RESOURCE), Mockito.any(ProjectDetailWebResourceRepresentation.class))).thenReturn(mockModel);
    Mockito.when(mockModel.addAttribute(ModelConstants.MODEL_MEDIA_LISTING_OPERATION, ModelConstants.MODEL_MEDIA_LISTING_OPERATION_PICTURES)).thenReturn(mockModel);
    Mockito.when(mockModel.addAttribute(ModelConstants.MODEL_PROJECT_IMAGE_RESOURCE_ARTIFACT, true)).thenReturn(mockModel);
    Mockito.when(mockModel.addAttribute(ModelConstants.MODEL_SAVE_PICTURE_DISPLAY_SEQUENCE, 1)).thenReturn(mockModel);

    // Execute
    Exception expectedException = null;
    String result = null;
    try {
      result = projectMediaController.updateProjectLocalResource(projectDetailLocalResourceRepresentation, mockMultipartFile, mockModel, mockHttpServletRequest, 1L);
    } catch (Exception exception) {
      expectedException = exception;
    }

    // Verify
    Mockito.verify(mockModel, Mockito.times(1)).addAttribute(SessionConstants.SESSION_LOGGED_IN_USER, loggedInUserRepresentation);
    Mockito.verify(mockProjectMediaService, Mockito.times(1)).getProjectDetail(projectDetailCommandDTOCaptor.capture());
    Mockito.verify(mockProjectMediaService, Mockito.times(1)).updateProjectLocalResource(saveProjectLocalResourceCommandDTOCaptor.capture());
    Mockito.verify(mockModel, Mockito.times(1)).addAttribute(ModelConstants.MODEL_PROJECT_DETAIL, projectDetailRepresentation);
    Mockito.verify(mockModel, Mockito.times(1)).addAttribute(Mockito.eq(ModelConstants.MODEL_ADD_PROJECT_LOCAL_RESOURCE), Mockito.any(ProjectDetailLocalResourceRepresentation.class));
    Mockito.verify(mockModel, Mockito.times(1)).addAttribute(Mockito.eq(ModelConstants.MODEL_ADD_PROJECT_WEB_RESOURCE), Mockito.any(ProjectDetailWebResourceRepresentation.class));
    Mockito.verify(mockModel, Mockito.times(1)).addAttribute(ModelConstants.MODEL_MEDIA_LISTING_OPERATION, ModelConstants.MODEL_MEDIA_LISTING_OPERATION_PICTURES);
    Mockito.verify(mockModel, Mockito.times(1)).addAttribute(ModelConstants.MODEL_PROJECT_IMAGE_RESOURCE_ARTIFACT, true);

    // Assertions
    assert expectedException == null;
    assert (Long) projectDetailCommandDTOCaptor.getValue().get(ContextConstants.CONTEXT_PROJECT_ID) == 1L;
    assert (Long) saveProjectLocalResourceCommandDTOCaptor.getValue().get(ContextConstants.CONTEXT_PROJECT_ID) == 1L;
    assert saveProjectLocalResourceCommandDTOCaptor.getValue().get(ContextConstants.CONTEXT_PROJECT_LOCAL_RESOURCE) == projectDetailLocalResourceRepresentation;
    assert saveProjectLocalResourceCommandDTOCaptor.getValue().get(ContextConstants.CONTEXT_PROJECT_LOCAL_RESOURCE_FILE) == mockMultipartFile;
    assert result.equals(ViewConstants.VIEW_MEDIA);
  }

  @Test
  public void testUpdateProjectLocalResourceGetProjectDetailFailedException() {
    // Initialize
    Model mockModel = Mockito.mock(Model.class);
    HttpServletRequest mockHttpServletRequest = Mockito.mock(HttpServletRequest.class);
    HttpSession mockHttpSession = Mockito.mock(HttpSession.class);
    MultipartFile mockMultipartFile = Mockito.mock(MultipartFile.class);

    ProjectMediaService mockProjectMediaService = Mockito.mock(ProjectMediaService.class);

    ArgumentCaptor<CommandDTO> projectDetailCommandDTOCaptor = ArgumentCaptor.forClass(CommandDTO.class);
    ArgumentCaptor<CommandDTO> saveProjectLocalResourceCommandDTOCaptor = ArgumentCaptor.forClass(CommandDTO.class);
    ArgumentCaptor<ErrorsRepresentation> errorsRepresentationCaptor = ArgumentCaptor.forClass(ErrorsRepresentation.class);

    ProjectMediaController projectMediaController = new ProjectMediaController();
    projectMediaController.projectMediaService = mockProjectMediaService;

    LoggedInUserRepresentation loggedInUserRepresentation = LoggedInUserRepresentation.builder().userName("username").build();
    ProjectDetailRepresentation projectDetailRepresentation = ProjectDetailRepresentation.builder().id(1L).build();
    ProjectDetailLocalResourceRepresentation projectDetailLocalResourceRepresentation = ProjectDetailLocalResourceRepresentation
        .builder()
        .id(1L).resourceType("IMAGE").displaySequence(1)
        .build();

    CommandDTO responseCommandDTO = CommandDTO
        .builder()
        .add(ContextConstants.CONTEXT_PROJECT_DETAIL, projectDetailRepresentation)
        .build();

    Map<String, String> fieldMessages = new HashMap<>();
    fieldMessages.put("field1", "message1");
    GetProjectDetailFailedException responseException = new GetProjectDetailFailedException();
    responseException.setFieldMessages(fieldMessages);
    responseException.setMessages(Collections.singletonList("message1"));

    // Define Mocks
    Mockito.when(mockHttpServletRequest.getSession()).thenReturn(mockHttpSession);
    Mockito.when(mockHttpSession.getAttribute(SessionConstants.SESSION_LOGGED_IN_USER)).thenReturn(loggedInUserRepresentation);
    Mockito.when(mockModel.addAttribute(SessionConstants.SESSION_LOGGED_IN_USER, loggedInUserRepresentation)).thenReturn(mockModel);
    Mockito.when(mockProjectMediaService.getProjectDetail(Mockito.any())).thenReturn(responseCommandDTO);
    Mockito.when(mockProjectMediaService.updateProjectLocalResource(Mockito.any())).thenThrow(responseException);
    Mockito.when(mockModel.addAttribute(ModelConstants.MODEL_PROJECT_DETAIL, projectDetailRepresentation)).thenReturn(mockModel);
    Mockito.when(mockModel.addAttribute(Mockito.eq(ModelConstants.MODEL_ADD_PROJECT_LOCAL_RESOURCE), Mockito.any(ProjectDetailLocalResourceRepresentation.class))).thenReturn(mockModel);
    Mockito.when(mockModel.addAttribute(Mockito.eq(ModelConstants.MODEL_ADD_PROJECT_WEB_RESOURCE), Mockito.any(ProjectDetailWebResourceRepresentation.class))).thenReturn(mockModel);
    Mockito.when(mockModel.addAttribute(ModelConstants.MODEL_MEDIA_LISTING_OPERATION, ModelConstants.MODEL_MEDIA_LISTING_OPERATION_PICTURES)).thenReturn(mockModel);
    Mockito.when(mockModel.addAttribute(ModelConstants.MODEL_PROJECT_IMAGE_RESOURCE_ARTIFACT, true)).thenReturn(mockModel);
    Mockito.when(mockModel.addAttribute(ModelConstants.MODEL_SAVE_PICTURE_DISPLAY_SEQUENCE, 1)).thenReturn(mockModel);
    Mockito.when(mockModel.addAttribute(ModelConstants.MODEL_ADD_PROJECT_LOCAL_RESOURCE, projectDetailLocalResourceRepresentation)).thenReturn(mockModel);
    Mockito.when(mockModel.addAttribute(ModelConstants.MODEL_MEDIA_LISTING_OPERATION, "pictures_save_errors")).thenReturn(mockModel);
    Mockito.when(mockModel.addAttribute(Mockito.eq(ModelConstants.MODEL_ADD_PROJECT_LOCAL_RESOURCE_ERRORS), Mockito.any(ErrorsRepresentation.class))).thenReturn(mockModel);

    // Execute
    Exception expectedException = null;
    String result = null;
    try {
      result = projectMediaController.updateProjectLocalResource(projectDetailLocalResourceRepresentation, mockMultipartFile, mockModel, mockHttpServletRequest, 1L);
    } catch (Exception exception) {
      expectedException = exception;
    }

    // Verify
    Mockito.verify(mockModel, Mockito.times(1)).addAttribute(SessionConstants.SESSION_LOGGED_IN_USER, loggedInUserRepresentation);
    Mockito.verify(mockProjectMediaService, Mockito.times(1)).getProjectDetail(projectDetailCommandDTOCaptor.capture());
    Mockito.verify(mockProjectMediaService, Mockito.times(1)).updateProjectLocalResource(saveProjectLocalResourceCommandDTOCaptor.capture());
    Mockito.verify(mockModel, Mockito.times(1)).addAttribute(ModelConstants.MODEL_PROJECT_DETAIL, projectDetailRepresentation);
    Mockito.verify(mockModel, Mockito.times(1)).addAttribute(Mockito.eq(ModelConstants.MODEL_ADD_PROJECT_WEB_RESOURCE), Mockito.any(ProjectDetailWebResourceRepresentation.class));
    Mockito.verify(mockModel, Mockito.times(1)).addAttribute(ModelConstants.MODEL_MEDIA_LISTING_OPERATION, ModelConstants.MODEL_MEDIA_LISTING_OPERATION_PICTURES);
    Mockito.verify(mockModel, Mockito.times(1)).addAttribute(ModelConstants.MODEL_PROJECT_IMAGE_RESOURCE_ARTIFACT, true);
    Mockito.verify(mockModel, Mockito.times(1)).addAttribute(ModelConstants.MODEL_ADD_PROJECT_LOCAL_RESOURCE, projectDetailLocalResourceRepresentation);
    Mockito.verify(mockModel, Mockito.times(1)).addAttribute(Mockito.eq(ModelConstants.MODEL_ADD_PROJECT_LOCAL_RESOURCE_ERRORS), errorsRepresentationCaptor.capture());

    // Assertions
    assert expectedException == null;
    assert (Long) projectDetailCommandDTOCaptor.getValue().get(ContextConstants.CONTEXT_PROJECT_ID) == 1L;
    assert (Long) saveProjectLocalResourceCommandDTOCaptor.getValue().get(ContextConstants.CONTEXT_PROJECT_ID) == 1L;
    assert saveProjectLocalResourceCommandDTOCaptor.getValue().get(ContextConstants.CONTEXT_PROJECT_LOCAL_RESOURCE) == projectDetailLocalResourceRepresentation;
    assert saveProjectLocalResourceCommandDTOCaptor.getValue().get(ContextConstants.CONTEXT_PROJECT_LOCAL_RESOURCE_FILE) == mockMultipartFile;
    assert errorsRepresentationCaptor.getValue().getErrorMessages().get(0).equals("message1");
    assert errorsRepresentationCaptor.getValue().getFieldErrorMessages().get("field1").equals("message1");
    assert result.equals(ViewConstants.VIEW_MEDIA);
  }

  @Test
  public void testUpdateProjectLocalResourceUpdateProjectLocalResourceFailedException() {
    // Initialize
    Model mockModel = Mockito.mock(Model.class);
    HttpServletRequest mockHttpServletRequest = Mockito.mock(HttpServletRequest.class);
    HttpSession mockHttpSession = Mockito.mock(HttpSession.class);
    MultipartFile mockMultipartFile = Mockito.mock(MultipartFile.class);

    ProjectMediaService mockProjectMediaService = Mockito.mock(ProjectMediaService.class);

    ArgumentCaptor<CommandDTO> projectDetailCommandDTOCaptor = ArgumentCaptor.forClass(CommandDTO.class);
    ArgumentCaptor<CommandDTO> saveProjectLocalResourceCommandDTOCaptor = ArgumentCaptor.forClass(CommandDTO.class);
    ArgumentCaptor<ErrorsRepresentation> errorsRepresentationCaptor = ArgumentCaptor.forClass(ErrorsRepresentation.class);

    ProjectMediaController projectMediaController = new ProjectMediaController();
    projectMediaController.projectMediaService = mockProjectMediaService;

    LoggedInUserRepresentation loggedInUserRepresentation = LoggedInUserRepresentation.builder().userName("username").build();
    ProjectDetailRepresentation projectDetailRepresentation = ProjectDetailRepresentation.builder().id(1L).build();
    ProjectDetailLocalResourceRepresentation projectDetailLocalResourceRepresentation = ProjectDetailLocalResourceRepresentation
        .builder()
        .id(1L).resourceType("IMAGE").displaySequence(1)
        .build();

    CommandDTO responseCommandDTO = CommandDTO
        .builder()
        .add(ContextConstants.CONTEXT_PROJECT_DETAIL, projectDetailRepresentation)
        .build();

    Map<String, String> fieldMessages = new HashMap<>();
    fieldMessages.put("field1", "message1");
    UpdateProjectLocalResourceFailedException responseException = new UpdateProjectLocalResourceFailedException();
    responseException.setFieldMessages(fieldMessages);
    responseException.setMessages(Collections.singletonList("message1"));

    // Define Mocks
    Mockito.when(mockHttpServletRequest.getSession()).thenReturn(mockHttpSession);
    Mockito.when(mockHttpSession.getAttribute(SessionConstants.SESSION_LOGGED_IN_USER)).thenReturn(loggedInUserRepresentation);
    Mockito.when(mockModel.addAttribute(SessionConstants.SESSION_LOGGED_IN_USER, loggedInUserRepresentation)).thenReturn(mockModel);
    Mockito.when(mockProjectMediaService.getProjectDetail(Mockito.any())).thenReturn(responseCommandDTO);
    Mockito.when(mockProjectMediaService.updateProjectLocalResource(Mockito.any())).thenThrow(responseException);
    Mockito.when(mockModel.addAttribute(ModelConstants.MODEL_PROJECT_DETAIL, projectDetailRepresentation)).thenReturn(mockModel);
    Mockito.when(mockModel.addAttribute(Mockito.eq(ModelConstants.MODEL_ADD_PROJECT_LOCAL_RESOURCE), Mockito.any(ProjectDetailLocalResourceRepresentation.class))).thenReturn(mockModel);
    Mockito.when(mockModel.addAttribute(Mockito.eq(ModelConstants.MODEL_ADD_PROJECT_WEB_RESOURCE), Mockito.any(ProjectDetailWebResourceRepresentation.class))).thenReturn(mockModel);
    Mockito.when(mockModel.addAttribute(ModelConstants.MODEL_MEDIA_LISTING_OPERATION, ModelConstants.MODEL_MEDIA_LISTING_OPERATION_PICTURES)).thenReturn(mockModel);
    Mockito.when(mockModel.addAttribute(ModelConstants.MODEL_PROJECT_IMAGE_RESOURCE_ARTIFACT, true)).thenReturn(mockModel);
    Mockito.when(mockModel.addAttribute(ModelConstants.MODEL_SAVE_PICTURE_DISPLAY_SEQUENCE, 1)).thenReturn(mockModel);
    Mockito.when(mockModel.addAttribute(ModelConstants.MODEL_ADD_PROJECT_LOCAL_RESOURCE, projectDetailLocalResourceRepresentation)).thenReturn(mockModel);
    Mockito.when(mockModel.addAttribute(ModelConstants.MODEL_MEDIA_LISTING_OPERATION, "pictures_save_errors")).thenReturn(mockModel);
    Mockito.when(mockModel.addAttribute(Mockito.eq(ModelConstants.MODEL_ADD_PROJECT_LOCAL_RESOURCE_ERRORS), Mockito.any(ErrorsRepresentation.class))).thenReturn(mockModel);

    // Execute
    Exception expectedException = null;
    String result = null;
    try {
      result = projectMediaController.updateProjectLocalResource(projectDetailLocalResourceRepresentation, mockMultipartFile, mockModel, mockHttpServletRequest, 1L);
    } catch (Exception exception) {
      expectedException = exception;
    }

    // Verify
    Mockito.verify(mockModel, Mockito.times(1)).addAttribute(SessionConstants.SESSION_LOGGED_IN_USER, loggedInUserRepresentation);
    Mockito.verify(mockProjectMediaService, Mockito.times(1)).getProjectDetail(projectDetailCommandDTOCaptor.capture());
    Mockito.verify(mockProjectMediaService, Mockito.times(1)).updateProjectLocalResource(saveProjectLocalResourceCommandDTOCaptor.capture());
    Mockito.verify(mockModel, Mockito.times(1)).addAttribute(ModelConstants.MODEL_PROJECT_DETAIL, projectDetailRepresentation);
    Mockito.verify(mockModel, Mockito.times(1)).addAttribute(Mockito.eq(ModelConstants.MODEL_ADD_PROJECT_WEB_RESOURCE), Mockito.any(ProjectDetailWebResourceRepresentation.class));
    Mockito.verify(mockModel, Mockito.times(1)).addAttribute(ModelConstants.MODEL_MEDIA_LISTING_OPERATION, ModelConstants.MODEL_MEDIA_LISTING_OPERATION_PICTURES);
    Mockito.verify(mockModel, Mockito.times(1)).addAttribute(ModelConstants.MODEL_PROJECT_IMAGE_RESOURCE_ARTIFACT, true);
    Mockito.verify(mockModel, Mockito.times(1)).addAttribute(ModelConstants.MODEL_ADD_PROJECT_LOCAL_RESOURCE, projectDetailLocalResourceRepresentation);
    Mockito.verify(mockModel, Mockito.times(1)).addAttribute(Mockito.eq(ModelConstants.MODEL_ADD_PROJECT_LOCAL_RESOURCE_ERRORS), errorsRepresentationCaptor.capture());

    // Assertions
    assert expectedException == null;
    assert (Long) projectDetailCommandDTOCaptor.getValue().get(ContextConstants.CONTEXT_PROJECT_ID) == 1L;
    assert (Long) saveProjectLocalResourceCommandDTOCaptor.getValue().get(ContextConstants.CONTEXT_PROJECT_ID) == 1L;
    assert saveProjectLocalResourceCommandDTOCaptor.getValue().get(ContextConstants.CONTEXT_PROJECT_LOCAL_RESOURCE) == projectDetailLocalResourceRepresentation;
    assert saveProjectLocalResourceCommandDTOCaptor.getValue().get(ContextConstants.CONTEXT_PROJECT_LOCAL_RESOURCE_FILE) == mockMultipartFile;
    assert errorsRepresentationCaptor.getValue().getErrorMessages().get(0).equals("message1");
    assert errorsRepresentationCaptor.getValue().getFieldErrorMessages().get("field1").equals("message1");
    assert result.equals(ViewConstants.VIEW_MEDIA);
  }

  @Test
  public void testUpdateProjectWebResource() {
    // Initialize
    Model mockModel = Mockito.mock(Model.class);
    HttpServletRequest mockHttpServletRequest = Mockito.mock(HttpServletRequest.class);
    HttpSession mockHttpSession = Mockito.mock(HttpSession.class);

    ProjectMediaService mockProjectMediaService = Mockito.mock(ProjectMediaService.class);

    ArgumentCaptor<CommandDTO> projectDetailCommandDTOCaptor = ArgumentCaptor.forClass(CommandDTO.class);
    ArgumentCaptor<CommandDTO> saveProjectLocalResourceCommandDTOCaptor = ArgumentCaptor.forClass(CommandDTO.class);

    ProjectMediaController projectMediaController = new ProjectMediaController();
    projectMediaController.projectMediaService = mockProjectMediaService;

    LoggedInUserRepresentation loggedInUserRepresentation = LoggedInUserRepresentation.builder().userName("username").build();
    ProjectDetailRepresentation projectDetailRepresentation = ProjectDetailRepresentation.builder().id(1L).build();
    ProjectDetailWebResourceRepresentation projectDetailWebResourceRepresentation = ProjectDetailWebResourceRepresentation
        .builder()
        .id(1L).resourceType("IMAGE").displaySequence(1)
        .build();

    CommandDTO responseCommandDTO = CommandDTO
        .builder()
        .add(ContextConstants.CONTEXT_PROJECT_DETAIL, projectDetailRepresentation)
        .build();

    CommandDTO saveResponseCommandDTO = CommandDTO.builder().build();

    // Define Mocks
    Mockito.when(mockHttpServletRequest.getSession()).thenReturn(mockHttpSession);
    Mockito.when(mockHttpSession.getAttribute(SessionConstants.SESSION_LOGGED_IN_USER)).thenReturn(loggedInUserRepresentation);
    Mockito.when(mockModel.addAttribute(SessionConstants.SESSION_LOGGED_IN_USER, loggedInUserRepresentation)).thenReturn(mockModel);
    Mockito.when(mockProjectMediaService.getProjectDetail(Mockito.any())).thenReturn(responseCommandDTO);
    Mockito.when(mockProjectMediaService.updateProjectWebResource(Mockito.any())).thenReturn(saveResponseCommandDTO);
    Mockito.when(mockModel.addAttribute(ModelConstants.MODEL_PROJECT_DETAIL, projectDetailRepresentation)).thenReturn(mockModel);
    Mockito.when(mockModel.addAttribute(Mockito.eq(ModelConstants.MODEL_ADD_PROJECT_LOCAL_RESOURCE), Mockito.any(ProjectDetailLocalResourceRepresentation.class))).thenReturn(mockModel);
    Mockito.when(mockModel.addAttribute(Mockito.eq(ModelConstants.MODEL_ADD_PROJECT_WEB_RESOURCE), Mockito.any(ProjectDetailWebResourceRepresentation.class))).thenReturn(mockModel);
    Mockito.when(mockModel.addAttribute(ModelConstants.MODEL_MEDIA_LISTING_OPERATION, "weblinks_save_success")).thenReturn(mockModel);
    Mockito.when(mockModel.addAttribute(ModelConstants.MODEL_PROJECT_WEB_RESOURCE_ARTIFACT, true)).thenReturn(mockModel);
    Mockito.when(mockModel.addAttribute(ModelConstants.MODEL_SAVE_WEBLINK_DISPLAY_SEQUENCE, 1)).thenReturn(mockModel);

    // Execute
    Exception expectedException = null;
    String result = null;
    try {
      result = projectMediaController.updateProjectWebResource(projectDetailWebResourceRepresentation, mockModel, mockHttpServletRequest, 1L);
    } catch (Exception exception) {
      expectedException = exception;
    }

    // Verify
    Mockito.verify(mockModel, Mockito.times(1)).addAttribute(SessionConstants.SESSION_LOGGED_IN_USER, loggedInUserRepresentation);
    Mockito.verify(mockProjectMediaService, Mockito.times(1)).getProjectDetail(projectDetailCommandDTOCaptor.capture());
    Mockito.verify(mockProjectMediaService, Mockito.times(1)).updateProjectWebResource(saveProjectLocalResourceCommandDTOCaptor.capture());
    Mockito.verify(mockModel, Mockito.times(1)).addAttribute(ModelConstants.MODEL_PROJECT_DETAIL, projectDetailRepresentation);
    Mockito.verify(mockModel, Mockito.times(1)).addAttribute(Mockito.eq(ModelConstants.MODEL_ADD_PROJECT_LOCAL_RESOURCE), Mockito.any(ProjectDetailLocalResourceRepresentation.class));
    Mockito.verify(mockModel, Mockito.times(1)).addAttribute(Mockito.eq(ModelConstants.MODEL_ADD_PROJECT_WEB_RESOURCE), Mockito.any(ProjectDetailWebResourceRepresentation.class));
    Mockito.verify(mockModel, Mockito.times(1)).addAttribute(ModelConstants.MODEL_PROJECT_WEB_RESOURCE_ARTIFACT, true);

    // Assertions
    assert expectedException == null;
    assert (Long) projectDetailCommandDTOCaptor.getValue().get(ContextConstants.CONTEXT_PROJECT_ID) == 1L;
    assert (Long) saveProjectLocalResourceCommandDTOCaptor.getValue().get(ContextConstants.CONTEXT_PROJECT_ID) == 1L;
    assert saveProjectLocalResourceCommandDTOCaptor.getValue().get(ContextConstants.CONTEXT_PROJECT_WEB_RESOURCE) == projectDetailWebResourceRepresentation;
    assert result.equals(ViewConstants.VIEW_MEDIA);
  }

  @Test
  public void testUpdateProjectWebResourceGetProjectDetailFailedException() {
    // Initialize
    Model mockModel = Mockito.mock(Model.class);
    HttpServletRequest mockHttpServletRequest = Mockito.mock(HttpServletRequest.class);
    HttpSession mockHttpSession = Mockito.mock(HttpSession.class);

    ProjectMediaService mockProjectMediaService = Mockito.mock(ProjectMediaService.class);

    ArgumentCaptor<CommandDTO> projectDetailCommandDTOCaptor = ArgumentCaptor.forClass(CommandDTO.class);
    ArgumentCaptor<CommandDTO> saveProjectLocalResourceCommandDTOCaptor = ArgumentCaptor.forClass(CommandDTO.class);
    ArgumentCaptor<ErrorsRepresentation> errorsRepresentationCaptor = ArgumentCaptor.forClass(ErrorsRepresentation.class);

    ProjectMediaController projectMediaController = new ProjectMediaController();
    projectMediaController.projectMediaService = mockProjectMediaService;

    LoggedInUserRepresentation loggedInUserRepresentation = LoggedInUserRepresentation.builder().userName("username").build();
    ProjectDetailRepresentation projectDetailRepresentation = ProjectDetailRepresentation.builder().id(1L).build();
    ProjectDetailWebResourceRepresentation projectDetailWebResourceRepresentation = ProjectDetailWebResourceRepresentation
        .builder()
        .id(1L).resourceType("IMAGE").displaySequence(1)
        .build();

    CommandDTO responseCommandDTO = CommandDTO
        .builder()
        .add(ContextConstants.CONTEXT_PROJECT_DETAIL, projectDetailRepresentation)
        .build();

    Map<String, String> fieldMessages = new HashMap<>();
    fieldMessages.put("field1", "message1");
    GetProjectDetailFailedException responseException = new GetProjectDetailFailedException();
    responseException.setFieldMessages(fieldMessages);
    responseException.setMessages(Collections.singletonList("message1"));

    // Define Mocks
    Mockito.when(mockHttpServletRequest.getSession()).thenReturn(mockHttpSession);
    Mockito.when(mockHttpSession.getAttribute(SessionConstants.SESSION_LOGGED_IN_USER)).thenReturn(loggedInUserRepresentation);
    Mockito.when(mockModel.addAttribute(SessionConstants.SESSION_LOGGED_IN_USER, loggedInUserRepresentation)).thenReturn(mockModel);
    Mockito.when(mockProjectMediaService.getProjectDetail(Mockito.any())).thenReturn(responseCommandDTO);
    Mockito.when(mockProjectMediaService.updateProjectWebResource(Mockito.any())).thenThrow(responseException);
    Mockito.when(mockModel.addAttribute(ModelConstants.MODEL_PROJECT_DETAIL, projectDetailRepresentation)).thenReturn(mockModel);
    Mockito.when(mockModel.addAttribute(Mockito.eq(ModelConstants.MODEL_ADD_PROJECT_LOCAL_RESOURCE), Mockito.any(ProjectDetailLocalResourceRepresentation.class))).thenReturn(mockModel);
    Mockito.when(mockModel.addAttribute(Mockito.eq(ModelConstants.MODEL_ADD_PROJECT_WEB_RESOURCE), Mockito.any(ProjectDetailWebResourceRepresentation.class))).thenReturn(mockModel);
    Mockito.when(mockModel.addAttribute(ModelConstants.MODEL_MEDIA_LISTING_OPERATION, "weblinks_save_errors")).thenReturn(mockModel);
    Mockito.when(mockModel.addAttribute(ModelConstants.MODEL_PROJECT_WEB_RESOURCE_ARTIFACT, true)).thenReturn(mockModel);
    Mockito.when(mockModel.addAttribute(ModelConstants.MODEL_SAVE_WEBLINK_DISPLAY_SEQUENCE, 1)).thenReturn(mockModel);
    Mockito.when(mockModel.addAttribute(Mockito.eq(ModelConstants.MODEL_ADD_PROJECT_WEB_RESOURCE_ERRORS), Mockito.any(ErrorsRepresentation.class))).thenReturn(mockModel);

    // Execute
    Exception expectedException = null;
    String result = null;
    try {
      result = projectMediaController.updateProjectWebResource(projectDetailWebResourceRepresentation, mockModel, mockHttpServletRequest, 1L);
    } catch (Exception exception) {
      expectedException = exception;
    }

    // Verify
    Mockito.verify(mockModel, Mockito.times(1)).addAttribute(SessionConstants.SESSION_LOGGED_IN_USER, loggedInUserRepresentation);
    Mockito.verify(mockProjectMediaService, Mockito.times(1)).getProjectDetail(projectDetailCommandDTOCaptor.capture());
    Mockito.verify(mockProjectMediaService, Mockito.times(1)).updateProjectWebResource(saveProjectLocalResourceCommandDTOCaptor.capture());
    Mockito.verify(mockModel, Mockito.times(1)).addAttribute(ModelConstants.MODEL_PROJECT_DETAIL, projectDetailRepresentation);
    Mockito.verify(mockModel, Mockito.times(1)).addAttribute(Mockito.eq(ModelConstants.MODEL_ADD_PROJECT_LOCAL_RESOURCE), Mockito.any(ProjectDetailLocalResourceRepresentation.class));
    Mockito.verify(mockModel, Mockito.times(1)).addAttribute(ModelConstants.MODEL_PROJECT_WEB_RESOURCE_ARTIFACT, true);
    Mockito.verify(mockModel, Mockito.times(1)).addAttribute(Mockito.eq(ModelConstants.MODEL_ADD_PROJECT_WEB_RESOURCE_ERRORS), errorsRepresentationCaptor.capture());

    // Assertions
    assert expectedException == null;
    assert (Long) projectDetailCommandDTOCaptor.getValue().get(ContextConstants.CONTEXT_PROJECT_ID) == 1L;
    assert (Long) saveProjectLocalResourceCommandDTOCaptor.getValue().get(ContextConstants.CONTEXT_PROJECT_ID) == 1L;
    assert saveProjectLocalResourceCommandDTOCaptor.getValue().get(ContextConstants.CONTEXT_PROJECT_WEB_RESOURCE) == projectDetailWebResourceRepresentation;
    assert errorsRepresentationCaptor.getValue().getErrorMessages().get(0).equals("message1");
    assert errorsRepresentationCaptor.getValue().getFieldErrorMessages().get("field1").equals("message1");
    assert result.equals(ViewConstants.VIEW_MEDIA);
  }

  @Test
  public void testUpdateProjectWebResourceUpdateProjectWebResourceFailedException() {
    // Initialize
    Model mockModel = Mockito.mock(Model.class);
    HttpServletRequest mockHttpServletRequest = Mockito.mock(HttpServletRequest.class);
    HttpSession mockHttpSession = Mockito.mock(HttpSession.class);

    ProjectMediaService mockProjectMediaService = Mockito.mock(ProjectMediaService.class);

    ArgumentCaptor<CommandDTO> projectDetailCommandDTOCaptor = ArgumentCaptor.forClass(CommandDTO.class);
    ArgumentCaptor<CommandDTO> saveProjectLocalResourceCommandDTOCaptor = ArgumentCaptor.forClass(CommandDTO.class);
    ArgumentCaptor<ErrorsRepresentation> errorsRepresentationCaptor = ArgumentCaptor.forClass(ErrorsRepresentation.class);

    ProjectMediaController projectMediaController = new ProjectMediaController();
    projectMediaController.projectMediaService = mockProjectMediaService;

    LoggedInUserRepresentation loggedInUserRepresentation = LoggedInUserRepresentation.builder().userName("username").build();
    ProjectDetailRepresentation projectDetailRepresentation = ProjectDetailRepresentation.builder().id(1L).build();
    ProjectDetailWebResourceRepresentation projectDetailWebResourceRepresentation = ProjectDetailWebResourceRepresentation
        .builder()
        .id(1L).resourceType("IMAGE").displaySequence(1)
        .build();

    CommandDTO responseCommandDTO = CommandDTO
        .builder()
        .add(ContextConstants.CONTEXT_PROJECT_DETAIL, projectDetailRepresentation)
        .build();

    Map<String, String> fieldMessages = new HashMap<>();
    fieldMessages.put("field1", "message1");
    UpdateProjectWebResourceFailedException responseException = new UpdateProjectWebResourceFailedException();
    responseException.setFieldMessages(fieldMessages);
    responseException.setMessages(Collections.singletonList("message1"));

    // Define Mocks
    Mockito.when(mockHttpServletRequest.getSession()).thenReturn(mockHttpSession);
    Mockito.when(mockHttpSession.getAttribute(SessionConstants.SESSION_LOGGED_IN_USER)).thenReturn(loggedInUserRepresentation);
    Mockito.when(mockModel.addAttribute(SessionConstants.SESSION_LOGGED_IN_USER, loggedInUserRepresentation)).thenReturn(mockModel);
    Mockito.when(mockProjectMediaService.getProjectDetail(Mockito.any())).thenReturn(responseCommandDTO);
    Mockito.when(mockProjectMediaService.updateProjectWebResource(Mockito.any())).thenThrow(responseException);
    Mockito.when(mockModel.addAttribute(ModelConstants.MODEL_PROJECT_DETAIL, projectDetailRepresentation)).thenReturn(mockModel);
    Mockito.when(mockModel.addAttribute(Mockito.eq(ModelConstants.MODEL_ADD_PROJECT_LOCAL_RESOURCE), Mockito.any(ProjectDetailLocalResourceRepresentation.class))).thenReturn(mockModel);
    Mockito.when(mockModel.addAttribute(Mockito.eq(ModelConstants.MODEL_ADD_PROJECT_WEB_RESOURCE), Mockito.any(ProjectDetailWebResourceRepresentation.class))).thenReturn(mockModel);
    Mockito.when(mockModel.addAttribute(ModelConstants.MODEL_MEDIA_LISTING_OPERATION, "weblinks_save_errors")).thenReturn(mockModel);
    Mockito.when(mockModel.addAttribute(ModelConstants.MODEL_PROJECT_WEB_RESOURCE_ARTIFACT, true)).thenReturn(mockModel);
    Mockito.when(mockModel.addAttribute(ModelConstants.MODEL_SAVE_WEBLINK_DISPLAY_SEQUENCE, 1)).thenReturn(mockModel);
    Mockito.when(mockModel.addAttribute(Mockito.eq(ModelConstants.MODEL_ADD_PROJECT_WEB_RESOURCE_ERRORS), Mockito.any(ErrorsRepresentation.class))).thenReturn(mockModel);

    // Execute
    Exception expectedException = null;
    String result = null;
    try {
      result = projectMediaController.updateProjectWebResource(projectDetailWebResourceRepresentation, mockModel, mockHttpServletRequest, 1L);
    } catch (Exception exception) {
      expectedException = exception;
    }

    // Verify
    Mockito.verify(mockModel, Mockito.times(1)).addAttribute(SessionConstants.SESSION_LOGGED_IN_USER, loggedInUserRepresentation);
    Mockito.verify(mockProjectMediaService, Mockito.times(1)).getProjectDetail(projectDetailCommandDTOCaptor.capture());
    Mockito.verify(mockProjectMediaService, Mockito.times(1)).updateProjectWebResource(saveProjectLocalResourceCommandDTOCaptor.capture());
    Mockito.verify(mockModel, Mockito.times(1)).addAttribute(ModelConstants.MODEL_PROJECT_DETAIL, projectDetailRepresentation);
    Mockito.verify(mockModel, Mockito.times(1)).addAttribute(Mockito.eq(ModelConstants.MODEL_ADD_PROJECT_LOCAL_RESOURCE), Mockito.any(ProjectDetailLocalResourceRepresentation.class));
    Mockito.verify(mockModel, Mockito.times(1)).addAttribute(ModelConstants.MODEL_PROJECT_WEB_RESOURCE_ARTIFACT, true);
    Mockito.verify(mockModel, Mockito.times(1)).addAttribute(Mockito.eq(ModelConstants.MODEL_ADD_PROJECT_WEB_RESOURCE_ERRORS), errorsRepresentationCaptor.capture());

    // Assertions
    assert expectedException == null;
    assert (Long) projectDetailCommandDTOCaptor.getValue().get(ContextConstants.CONTEXT_PROJECT_ID) == 1L;
    assert (Long) saveProjectLocalResourceCommandDTOCaptor.getValue().get(ContextConstants.CONTEXT_PROJECT_ID) == 1L;
    assert saveProjectLocalResourceCommandDTOCaptor.getValue().get(ContextConstants.CONTEXT_PROJECT_WEB_RESOURCE) == projectDetailWebResourceRepresentation;
    assert errorsRepresentationCaptor.getValue().getErrorMessages().get(0).equals("message1");
    assert errorsRepresentationCaptor.getValue().getFieldErrorMessages().get("field1").equals("message1");
    assert result.equals(ViewConstants.VIEW_MEDIA);
  }

  @Test
  public void testEditProjectLocalResource() {
    // Initialize
    Model mockModel = Mockito.mock(Model.class);
    HttpServletRequest mockHttpServletRequest = Mockito.mock(HttpServletRequest.class);
    HttpSession mockHttpSession = Mockito.mock(HttpSession.class);

    ProjectMediaService mockProjectMediaService = Mockito.mock(ProjectMediaService.class);

    ArgumentCaptor<CommandDTO> projectDetailCommandDTOCaptor = ArgumentCaptor.forClass(CommandDTO.class);
    ArgumentCaptor<CommandDTO> editCommandDTOCaptor = ArgumentCaptor.forClass(CommandDTO.class);

    ProjectMediaController projectMediaController = new ProjectMediaController();
    projectMediaController.projectMediaService = mockProjectMediaService;

    LoggedInUserRepresentation loggedInUserRepresentation = LoggedInUserRepresentation.builder().userName("username").build();
    ProjectDetailRepresentation projectDetailRepresentation = ProjectDetailRepresentation.builder().id(1L).build();
    ProjectDetailLocalResourceRepresentation projectDetailLocalResourceRepresentation = ProjectDetailLocalResourceRepresentation.builder().id(1L).build();

    CommandDTO responseCommandDTO = CommandDTO
        .builder()
        .add(ContextConstants.CONTEXT_PROJECT_DETAIL, projectDetailRepresentation)
        .build();
    CommandDTO editResponseCommandDTO = CommandDTO
        .builder()
        .add(ContextConstants.CONTEXT_PROJECT_LOCAL_RESOURCE, projectDetailLocalResourceRepresentation)
        .add(ContextConstants.CONTEXT_PROJECT_LOCAL_RESOURCE_ARTIFACT, "IMAGE")
        .build();

    // Define Mocks
    Mockito.when(mockHttpServletRequest.getSession()).thenReturn(mockHttpSession);
    Mockito.when(mockHttpSession.getAttribute(SessionConstants.SESSION_LOGGED_IN_USER)).thenReturn(loggedInUserRepresentation);
    Mockito.when(mockModel.addAttribute(SessionConstants.SESSION_LOGGED_IN_USER, loggedInUserRepresentation)).thenReturn(mockModel);
    Mockito.when(mockProjectMediaService.getProjectDetail(Mockito.any())).thenReturn(responseCommandDTO);
    Mockito.when(mockProjectMediaService.getProjectLocalResource(Mockito.any())).thenReturn(editResponseCommandDTO);
    Mockito.when(mockModel.addAttribute(ModelConstants.MODEL_PROJECT_DETAIL, projectDetailRepresentation)).thenReturn(mockModel);
    Mockito.when(mockModel.addAttribute(Mockito.eq(ModelConstants.MODEL_ADD_PROJECT_LOCAL_RESOURCE), Mockito.any(ProjectDetailLocalResourceRepresentation.class))).thenReturn(mockModel);
    Mockito.when(mockModel.addAttribute(Mockito.eq(ModelConstants.MODEL_ADD_PROJECT_WEB_RESOURCE), Mockito.any(ProjectDetailWebResourceRepresentation.class))).thenReturn(mockModel);
    Mockito.when(mockModel.addAttribute(Mockito.eq(ModelConstants.MODEL_EDIT_PROJECT_LOCAL_RESOURCE), Mockito.any(ProjectDetailLocalResourceRepresentation.class))).thenReturn(mockModel);
    Mockito.when(mockModel.addAttribute(ModelConstants.MODEL_MEDIA_LISTING_OPERATION, ModelConstants.MODEL_MEDIA_LISTING_OPERATION_PICTURES)).thenReturn(mockModel);

    // Execute
    Exception expectedException = null;
    String result = null;
    try {
      result = projectMediaController.editProjectLocalResource(1L, 1L, "IMAGE" ,mockModel, mockHttpServletRequest);
    } catch (Exception exception) {
      expectedException = exception;
    }

    // Verify
    Mockito.verify(mockModel, Mockito.times(1)).addAttribute(SessionConstants.SESSION_LOGGED_IN_USER, loggedInUserRepresentation);
    Mockito.verify(mockProjectMediaService, Mockito.times(1)).getProjectDetail(projectDetailCommandDTOCaptor.capture());
    Mockito.verify(mockProjectMediaService, Mockito.times(1)).getProjectLocalResource(editCommandDTOCaptor.capture());
    Mockito.verify(mockModel, Mockito.times(1)).addAttribute(ModelConstants.MODEL_PROJECT_DETAIL, projectDetailRepresentation);
    Mockito.verify(mockModel, Mockito.times(1)).addAttribute(Mockito.eq(ModelConstants.MODEL_ADD_PROJECT_LOCAL_RESOURCE), Mockito.any(ProjectDetailLocalResourceRepresentation.class));
    Mockito.verify(mockModel, Mockito.times(1)).addAttribute(Mockito.eq(ModelConstants.MODEL_EDIT_PROJECT_LOCAL_RESOURCE), Mockito.any(ProjectDetailLocalResourceRepresentation.class));
    Mockito.verify(mockModel, Mockito.times(1)).addAttribute(Mockito.eq(ModelConstants.MODEL_ADD_PROJECT_WEB_RESOURCE), Mockito.any(ProjectDetailWebResourceRepresentation.class));
    Mockito.verify(mockModel, Mockito.times(1)).addAttribute(ModelConstants.MODEL_MEDIA_LISTING_OPERATION, ModelConstants.MODEL_MEDIA_LISTING_OPERATION_PICTURES);

    // Assertions
    assert expectedException == null;
    assert (Long) projectDetailCommandDTOCaptor.getValue().get(ContextConstants.CONTEXT_PROJECT_ID) == 1L;
    assert (Long) editCommandDTOCaptor.getValue().get(ContextConstants.CONTEXT_LOCAL_RESOURCE_ID) == 1L;
    assert result.equals(ViewConstants.VIEW_MEDIA);
  }

  @Test
  public void testEditProjectLocalResourceException() {
    // Initialize
    Model mockModel = Mockito.mock(Model.class);
    HttpServletRequest mockHttpServletRequest = Mockito.mock(HttpServletRequest.class);
    HttpSession mockHttpSession = Mockito.mock(HttpSession.class);

    ProjectMediaService mockProjectMediaService = Mockito.mock(ProjectMediaService.class);

    ArgumentCaptor<CommandDTO> projectDetailCommandDTOCaptor = ArgumentCaptor.forClass(CommandDTO.class);
    ArgumentCaptor<CommandDTO> editCommandDTOCaptor = ArgumentCaptor.forClass(CommandDTO.class);
    ArgumentCaptor<ErrorsRepresentation> errorsRepresentationCaptor = ArgumentCaptor.forClass(ErrorsRepresentation.class);

    ProjectMediaController projectMediaController = new ProjectMediaController();
    projectMediaController.projectMediaService = mockProjectMediaService;

    LoggedInUserRepresentation loggedInUserRepresentation = LoggedInUserRepresentation.builder().userName("username").build();
    ProjectDetailRepresentation projectDetailRepresentation = ProjectDetailRepresentation.builder().id(1L).build();

    CommandDTO responseCommandDTO = CommandDTO
        .builder()
        .add(ContextConstants.CONTEXT_PROJECT_DETAIL, projectDetailRepresentation)
        .build();

    Map<String, String> fieldMessages = new HashMap<>();
    fieldMessages.put("field1", "message1");
    GetProjectLocalResourceFailedException responseException = new GetProjectLocalResourceFailedException();
    responseException.setFieldMessages(fieldMessages);
    responseException.setMessages(Collections.singletonList("message1"));

    // Define Mocks
    Mockito.when(mockHttpServletRequest.getSession()).thenReturn(mockHttpSession);
    Mockito.when(mockHttpSession.getAttribute(SessionConstants.SESSION_LOGGED_IN_USER)).thenReturn(loggedInUserRepresentation);
    Mockito.when(mockModel.addAttribute(SessionConstants.SESSION_LOGGED_IN_USER, loggedInUserRepresentation)).thenReturn(mockModel);
    Mockito.when(mockProjectMediaService.getProjectDetail(Mockito.any())).thenReturn(responseCommandDTO);
    Mockito.when(mockProjectMediaService.getProjectLocalResource(Mockito.any())).thenThrow(responseException);
    Mockito.when(mockModel.addAttribute(ModelConstants.MODEL_PROJECT_DETAIL, projectDetailRepresentation)).thenReturn(mockModel);
    Mockito.when(mockModel.addAttribute(Mockito.eq(ModelConstants.MODEL_ADD_PROJECT_LOCAL_RESOURCE), Mockito.any(ProjectDetailLocalResourceRepresentation.class))).thenReturn(mockModel);
    Mockito.when(mockModel.addAttribute(Mockito.eq(ModelConstants.MODEL_ADD_PROJECT_WEB_RESOURCE), Mockito.any(ProjectDetailWebResourceRepresentation.class))).thenReturn(mockModel);
    Mockito.when(mockModel.addAttribute(Mockito.eq(ModelConstants.MODEL_EDIT_PROJECT_LOCAL_RESOURCE), Mockito.any(ProjectDetailLocalResourceRepresentation.class))).thenReturn(mockModel);
    Mockito.when(mockModel.addAttribute(ModelConstants.MODEL_MEDIA_LISTING_OPERATION, ModelConstants.MODEL_MEDIA_LISTING_OPERATION_PICTURES)).thenReturn(mockModel);

    // Execute
    Exception expectedException = null;
    String result = null;
    try {
      result = projectMediaController.editProjectLocalResource(1L, 1L, "IMAGE" ,mockModel, mockHttpServletRequest);
    } catch (Exception exception) {
      expectedException = exception;
    }

    // Verify
    Mockito.verify(mockModel, Mockito.times(1)).addAttribute(SessionConstants.SESSION_LOGGED_IN_USER, loggedInUserRepresentation);
    Mockito.verify(mockProjectMediaService, Mockito.times(1)).getProjectDetail(projectDetailCommandDTOCaptor.capture());
    Mockito.verify(mockProjectMediaService, Mockito.times(1)).getProjectLocalResource(editCommandDTOCaptor.capture());
    Mockito.verify(mockModel, Mockito.times(1)).addAttribute(ModelConstants.MODEL_PROJECT_DETAIL, projectDetailRepresentation);
    Mockito.verify(mockModel, Mockito.times(1)).addAttribute(Mockito.eq(ModelConstants.MODEL_ADD_PROJECT_LOCAL_RESOURCE), Mockito.any(ProjectDetailLocalResourceRepresentation.class));
    Mockito.verify(mockModel, Mockito.times(0)).addAttribute(Mockito.eq(ModelConstants.MODEL_EDIT_PROJECT_LOCAL_RESOURCE), Mockito.any(ProjectDetailLocalResourceRepresentation.class));
    Mockito.verify(mockModel, Mockito.times(1)).addAttribute(Mockito.eq(ModelConstants.MODEL_ADD_PROJECT_WEB_RESOURCE), Mockito.any(ProjectDetailWebResourceRepresentation.class));
    Mockito.verify(mockModel, Mockito.times(1)).addAttribute(ModelConstants.MODEL_MEDIA_LISTING_OPERATION, ModelConstants.MODEL_MEDIA_LISTING_OPERATION_PICTURES);
    Mockito.verify(mockModel, Mockito.times(1)).addAttribute(Mockito.eq(ModelConstants.MODEL_EDIT_PROJECT_LOCAL_RESOURCE_ERRORS), errorsRepresentationCaptor.capture());

    // Assertions
    assert expectedException == null;
    assert (Long) projectDetailCommandDTOCaptor.getValue().get(ContextConstants.CONTEXT_PROJECT_ID) == 1L;
    assert (Long) editCommandDTOCaptor.getValue().get(ContextConstants.CONTEXT_LOCAL_RESOURCE_ID) == 1L;
    assert errorsRepresentationCaptor.getValue().getErrorMessages().get(0).equals("message1");
    assert errorsRepresentationCaptor.getValue().getFieldErrorMessages().get("field1").equals("message1");
    assert result.equals(ViewConstants.VIEW_MEDIA);
  }

  @Test
  public void testOpenLocalResource() {
    // Initialize
    Model mockModel = Mockito.mock(Model.class);
    HttpServletRequest mockHttpServletRequest = Mockito.mock(HttpServletRequest.class);
    HttpSession mockHttpSession = Mockito.mock(HttpSession.class);

    ProjectMediaService mockProjectMediaService = Mockito.mock(ProjectMediaService.class);

    ArgumentCaptor<CommandDTO> projectDetailCommandDTOCaptor = ArgumentCaptor.forClass(CommandDTO.class);
    ArgumentCaptor<CommandDTO> openCommandDTOCaptor = ArgumentCaptor.forClass(CommandDTO.class);

    ProjectMediaController projectMediaController = new ProjectMediaController();
    projectMediaController.projectMediaService = mockProjectMediaService;

    LoggedInUserRepresentation loggedInUserRepresentation = LoggedInUserRepresentation.builder().userName("username").build();
    ProjectDetailRepresentation projectDetailRepresentation = ProjectDetailRepresentation.builder().id(1L).build();
    ProjectDetailLocalResourceRepresentation projectDetailLocalResourceRepresentation = ProjectDetailLocalResourceRepresentation
        .builder()
        .id(1L).resourceType("IMAGE")
        .build();

    CommandDTO responseCommandDTO = CommandDTO
        .builder()
        .add(ContextConstants.CONTEXT_PROJECT_DETAIL, projectDetailRepresentation)
        .build();
    CommandDTO openResponseCommandDTO = CommandDTO
        .builder()
        .add(ContextConstants.CONTEXT_PROJECT_LOCAL_RESOURCE, projectDetailLocalResourceRepresentation)
        .add(ContextConstants.CONTEXT_PROJECT_LOCAL_RESOURCE_ARTIFACT, "IMAGE")
        .build();

    // Define Mocks
    Mockito.when(mockHttpServletRequest.getSession()).thenReturn(mockHttpSession);
    Mockito.when(mockHttpSession.getAttribute(SessionConstants.SESSION_LOGGED_IN_USER)).thenReturn(loggedInUserRepresentation);
    Mockito.when(mockModel.addAttribute(SessionConstants.SESSION_LOGGED_IN_USER, loggedInUserRepresentation)).thenReturn(mockModel);
    Mockito.when(mockProjectMediaService.getProjectDetail(Mockito.any())).thenReturn(responseCommandDTO);
    Mockito.when(mockProjectMediaService.openFile(Mockito.any())).thenReturn(openResponseCommandDTO);
    Mockito.when(mockModel.addAttribute(ModelConstants.MODEL_PROJECT_DETAIL, projectDetailRepresentation)).thenReturn(mockModel);
    Mockito.when(mockModel.addAttribute(Mockito.eq(ModelConstants.MODEL_ADD_PROJECT_LOCAL_RESOURCE), Mockito.any(ProjectDetailLocalResourceRepresentation.class))).thenReturn(mockModel);
    Mockito.when(mockModel.addAttribute(Mockito.eq(ModelConstants.MODEL_ADD_PROJECT_WEB_RESOURCE), Mockito.any(ProjectDetailWebResourceRepresentation.class))).thenReturn(mockModel);
    Mockito.when(mockModel.addAttribute(Mockito.eq(ModelConstants.MODEL_EDIT_PROJECT_LOCAL_RESOURCE), Mockito.any(ProjectDetailLocalResourceRepresentation.class))).thenReturn(mockModel);
    Mockito.when(mockModel.addAttribute(ModelConstants.MODEL_MEDIA_LISTING_OPERATION, ModelConstants.MODEL_MEDIA_LISTING_OPERATION_PICTURES)).thenReturn(mockModel);
    Mockito.when(mockModel.addAttribute(ModelConstants.MODEL_MEDIA_LISTING_OPERATION, "pictures_save_success")).thenReturn(mockModel);
    Mockito.when(mockModel.addAttribute(ModelConstants.MODEL_SAVE_PICTURE_DISPLAY_SEQUENCE, 1)).thenReturn(mockModel);
    Mockito.when(mockModel.addAttribute(ModelConstants.MODEL_PROJECT_IMAGE_RESOURCE_ARTIFACT, true)).thenReturn(mockModel);

    // Execute
    Exception expectedException = null;
    String result = null;
    try {
      result = projectMediaController.openLocalResource(1L, 1L, "IMAGE", 1, mockModel, mockHttpServletRequest);
    } catch (Exception exception) {
      expectedException = exception;
    }

    // Verify
    Mockito.verify(mockModel, Mockito.times(1)).addAttribute(SessionConstants.SESSION_LOGGED_IN_USER, loggedInUserRepresentation);
    Mockito.verify(mockProjectMediaService, Mockito.times(1)).getProjectDetail(projectDetailCommandDTOCaptor.capture());
    Mockito.verify(mockProjectMediaService, Mockito.times(1)).openFile(openCommandDTOCaptor.capture());
    Mockito.verify(mockModel, Mockito.times(1)).addAttribute(ModelConstants.MODEL_PROJECT_DETAIL, projectDetailRepresentation);
    Mockito.verify(mockModel, Mockito.times(1)).addAttribute(Mockito.eq(ModelConstants.MODEL_ADD_PROJECT_LOCAL_RESOURCE), Mockito.any(ProjectDetailLocalResourceRepresentation.class));
    Mockito.verify(mockModel, Mockito.times(1)).addAttribute(Mockito.eq(ModelConstants.MODEL_ADD_PROJECT_WEB_RESOURCE), Mockito.any(ProjectDetailWebResourceRepresentation.class));
    Mockito.verify(mockModel, Mockito.times(1)).addAttribute(ModelConstants.MODEL_MEDIA_LISTING_OPERATION, ModelConstants.MODEL_MEDIA_LISTING_OPERATION_PICTURES);
    Mockito.verify(mockModel, Mockito.times(1)).addAttribute(ModelConstants.MODEL_MEDIA_LISTING_OPERATION, "pictures_save_success");
    Mockito.verify(mockModel, Mockito.times(1)).addAttribute(ModelConstants.MODEL_SAVE_PICTURE_DISPLAY_SEQUENCE, 1);
    Mockito.verify(mockModel, Mockito.times(1)).addAttribute(ModelConstants.MODEL_PROJECT_IMAGE_RESOURCE_ARTIFACT, true);

    // Assertions
    assert expectedException == null;
    assert (Long) projectDetailCommandDTOCaptor.getValue().get(ContextConstants.CONTEXT_PROJECT_ID) == 1L;
    assert (Long) openCommandDTOCaptor.getValue().get(ContextConstants.CONTEXT_PROJECT_ID) == 1L;
    assert (Long) openCommandDTOCaptor.getValue().get(ContextConstants.CONTEXT_LOCAL_RESOURCE_ID) == 1L;
    assert result.equals(ViewConstants.VIEW_MEDIA);
  }

  @Test
  public void testOpenLocalResourceException() {
    // Initialize
    Model mockModel = Mockito.mock(Model.class);
    HttpServletRequest mockHttpServletRequest = Mockito.mock(HttpServletRequest.class);
    HttpSession mockHttpSession = Mockito.mock(HttpSession.class);

    ProjectMediaService mockProjectMediaService = Mockito.mock(ProjectMediaService.class);

    ArgumentCaptor<CommandDTO> projectDetailCommandDTOCaptor = ArgumentCaptor.forClass(CommandDTO.class);
    ArgumentCaptor<CommandDTO> openCommandDTOCaptor = ArgumentCaptor.forClass(CommandDTO.class);
    ArgumentCaptor<ErrorsRepresentation> errorsRepresentationCaptor = ArgumentCaptor.forClass(ErrorsRepresentation.class);

    ProjectMediaController projectMediaController = new ProjectMediaController();
    projectMediaController.projectMediaService = mockProjectMediaService;

    LoggedInUserRepresentation loggedInUserRepresentation = LoggedInUserRepresentation.builder().userName("username").build();
    ProjectDetailRepresentation projectDetailRepresentation = ProjectDetailRepresentation.builder().id(1L).build();

    CommandDTO responseCommandDTO = CommandDTO
        .builder()
        .add(ContextConstants.CONTEXT_PROJECT_DETAIL, projectDetailRepresentation)
        .build();

    Map<String, String> fieldMessages = new HashMap<>();
    fieldMessages.put("field1", "message1");
    GetProjectLocalResourceFailedException responseException = new GetProjectLocalResourceFailedException();
    responseException.setFieldMessages(fieldMessages);
    responseException.setMessages(Collections.singletonList("message1"));

    // Define Mocks
    Mockito.when(mockHttpServletRequest.getSession()).thenReturn(mockHttpSession);
    Mockito.when(mockHttpSession.getAttribute(SessionConstants.SESSION_LOGGED_IN_USER)).thenReturn(loggedInUserRepresentation);
    Mockito.when(mockModel.addAttribute(SessionConstants.SESSION_LOGGED_IN_USER, loggedInUserRepresentation)).thenReturn(mockModel);
    Mockito.when(mockProjectMediaService.getProjectDetail(Mockito.any())).thenReturn(responseCommandDTO);
    Mockito.when(mockProjectMediaService.openFile(Mockito.any())).thenThrow(responseException);
    Mockito.when(mockModel.addAttribute(ModelConstants.MODEL_PROJECT_DETAIL, projectDetailRepresentation)).thenReturn(mockModel);
    Mockito.when(mockModel.addAttribute(Mockito.eq(ModelConstants.MODEL_ADD_PROJECT_LOCAL_RESOURCE), Mockito.any(ProjectDetailLocalResourceRepresentation.class))).thenReturn(mockModel);
    Mockito.when(mockModel.addAttribute(Mockito.eq(ModelConstants.MODEL_ADD_PROJECT_WEB_RESOURCE), Mockito.any(ProjectDetailWebResourceRepresentation.class))).thenReturn(mockModel);
    Mockito.when(mockModel.addAttribute(Mockito.eq(ModelConstants.MODEL_EDIT_PROJECT_LOCAL_RESOURCE), Mockito.any(ProjectDetailLocalResourceRepresentation.class))).thenReturn(mockModel);
    Mockito.when(mockModel.addAttribute(ModelConstants.MODEL_MEDIA_LISTING_OPERATION, ModelConstants.MODEL_MEDIA_LISTING_OPERATION_PICTURES)).thenReturn(mockModel);
    Mockito.when(mockModel.addAttribute(ModelConstants.MODEL_MEDIA_LISTING_OPERATION, "pictures_save_success")).thenReturn(mockModel);
    Mockito.when(mockModel.addAttribute(ModelConstants.MODEL_SAVE_PICTURE_DISPLAY_SEQUENCE, 1)).thenReturn(mockModel);
    Mockito.when(mockModel.addAttribute(ModelConstants.MODEL_PROJECT_IMAGE_RESOURCE_ARTIFACT, true)).thenReturn(mockModel);

    // Execute
    Exception expectedException = null;
    String result = null;
    try {
      result = projectMediaController.openLocalResource(1L, 1L, "IMAGE", 1, mockModel, mockHttpServletRequest);
    } catch (Exception exception) {
      expectedException = exception;
    }

    // Verify
    Mockito.verify(mockModel, Mockito.times(1)).addAttribute(SessionConstants.SESSION_LOGGED_IN_USER, loggedInUserRepresentation);
    Mockito.verify(mockProjectMediaService, Mockito.times(1)).getProjectDetail(projectDetailCommandDTOCaptor.capture());
    Mockito.verify(mockProjectMediaService, Mockito.times(1)).openFile(openCommandDTOCaptor.capture());
    Mockito.verify(mockModel, Mockito.times(1)).addAttribute(ModelConstants.MODEL_PROJECT_DETAIL, projectDetailRepresentation);
    Mockito.verify(mockModel, Mockito.times(1)).addAttribute(Mockito.eq(ModelConstants.MODEL_ADD_PROJECT_LOCAL_RESOURCE), Mockito.any(ProjectDetailLocalResourceRepresentation.class));
    Mockito.verify(mockModel, Mockito.times(1)).addAttribute(Mockito.eq(ModelConstants.MODEL_ADD_PROJECT_WEB_RESOURCE), Mockito.any(ProjectDetailWebResourceRepresentation.class));
    Mockito.verify(mockModel, Mockito.times(1)).addAttribute(ModelConstants.MODEL_MEDIA_LISTING_OPERATION, ModelConstants.MODEL_MEDIA_LISTING_OPERATION_PICTURES);
    Mockito.verify(mockModel, Mockito.times(2)).addAttribute(ModelConstants.MODEL_PROJECT_IMAGE_RESOURCE_ARTIFACT, true);
    Mockito.verify(mockModel, Mockito.times(1)).addAttribute(Mockito.eq(ModelConstants.MODEL_EDIT_PROJECT_LOCAL_RESOURCE_ERRORS), errorsRepresentationCaptor.capture());
    // Assertions
    assert expectedException == null;
    assert (Long) projectDetailCommandDTOCaptor.getValue().get(ContextConstants.CONTEXT_PROJECT_ID) == 1L;
    assert (Long) openCommandDTOCaptor.getValue().get(ContextConstants.CONTEXT_PROJECT_ID) == 1L;
    assert (Long) openCommandDTOCaptor.getValue().get(ContextConstants.CONTEXT_LOCAL_RESOURCE_ID) == 1L;
    assert errorsRepresentationCaptor.getValue().getErrorMessages().get(0).equals("message1");
    assert errorsRepresentationCaptor.getValue().getFieldErrorMessages().get("field1").equals("message1");
    assert result.equals(ViewConstants.VIEW_MEDIA);
  }

  @Test
  public void testEditProjectWebResource() {
    // Initialize
    Model mockModel = Mockito.mock(Model.class);
    HttpServletRequest mockHttpServletRequest = Mockito.mock(HttpServletRequest.class);
    HttpSession mockHttpSession = Mockito.mock(HttpSession.class);

    ProjectMediaService mockProjectMediaService = Mockito.mock(ProjectMediaService.class);

    ArgumentCaptor<CommandDTO> projectDetailCommandDTOCaptor = ArgumentCaptor.forClass(CommandDTO.class);
    ArgumentCaptor<CommandDTO> editCommandDTOCaptor = ArgumentCaptor.forClass(CommandDTO.class);

    ProjectMediaController projectMediaController = new ProjectMediaController();
    projectMediaController.projectMediaService = mockProjectMediaService;

    LoggedInUserRepresentation loggedInUserRepresentation = LoggedInUserRepresentation.builder().userName("username").build();
    ProjectDetailRepresentation projectDetailRepresentation = ProjectDetailRepresentation.builder().id(1L).build();
    ProjectDetailWebResourceRepresentation projectDetailWebResourceRepresentation = ProjectDetailWebResourceRepresentation.builder().id(1L).build();

    CommandDTO responseCommandDTO = CommandDTO
        .builder()
        .add(ContextConstants.CONTEXT_PROJECT_DETAIL, projectDetailRepresentation)
        .build();
    CommandDTO editResponseCommandDTO = CommandDTO
        .builder()
        .add(ContextConstants.CONTEXT_PROJECT_WEB_RESOURCE, projectDetailWebResourceRepresentation)
        .build();

    // Define Mocks
    Mockito.when(mockHttpServletRequest.getSession()).thenReturn(mockHttpSession);
    Mockito.when(mockHttpSession.getAttribute(SessionConstants.SESSION_LOGGED_IN_USER)).thenReturn(loggedInUserRepresentation);
    Mockito.when(mockModel.addAttribute(SessionConstants.SESSION_LOGGED_IN_USER, loggedInUserRepresentation)).thenReturn(mockModel);
    Mockito.when(mockProjectMediaService.getProjectDetail(Mockito.any())).thenReturn(responseCommandDTO);
    Mockito.when(mockProjectMediaService.getProjectWebResource(Mockito.any())).thenReturn(editResponseCommandDTO);
    Mockito.when(mockModel.addAttribute(ModelConstants.MODEL_PROJECT_DETAIL, projectDetailRepresentation)).thenReturn(mockModel);
    Mockito.when(mockModel.addAttribute(Mockito.eq(ModelConstants.MODEL_ADD_PROJECT_LOCAL_RESOURCE), Mockito.any(ProjectDetailLocalResourceRepresentation.class))).thenReturn(mockModel);
    Mockito.when(mockModel.addAttribute(Mockito.eq(ModelConstants.MODEL_ADD_PROJECT_WEB_RESOURCE), Mockito.any(ProjectDetailWebResourceRepresentation.class))).thenReturn(mockModel);
    Mockito.when(mockModel.addAttribute(Mockito.eq(ModelConstants.MODEL_EDIT_PROJECT_LOCAL_RESOURCE), Mockito.any(ProjectDetailLocalResourceRepresentation.class))).thenReturn(mockModel);
    Mockito.when(mockModel.addAttribute(ModelConstants.MODEL_PROJECT_WEB_RESOURCE_ARTIFACT, true)).thenReturn(mockModel);
    Mockito.when(mockModel.addAttribute(ModelConstants.MODEL_MEDIA_LISTING_OPERATION, ModelConstants.MODEL_MEDIA_LISTING_OPERATION_PICTURES)).thenReturn(mockModel);

    // Execute
    Exception expectedException = null;
    String result = null;
    try {
      result = projectMediaController.editProjectWebResource(1L, 1L, mockModel, mockHttpServletRequest);
    } catch (Exception exception) {
      expectedException = exception;
    }

    // Verify
    Mockito.verify(mockModel, Mockito.times(1)).addAttribute(SessionConstants.SESSION_LOGGED_IN_USER, loggedInUserRepresentation);
    Mockito.verify(mockProjectMediaService, Mockito.times(1)).getProjectDetail(projectDetailCommandDTOCaptor.capture());
    Mockito.verify(mockProjectMediaService, Mockito.times(1)).getProjectWebResource(editCommandDTOCaptor.capture());
    Mockito.verify(mockModel, Mockito.times(1)).addAttribute(ModelConstants.MODEL_PROJECT_DETAIL, projectDetailRepresentation);
    Mockito.verify(mockModel, Mockito.times(1)).addAttribute(Mockito.eq(ModelConstants.MODEL_ADD_PROJECT_LOCAL_RESOURCE), Mockito.any(ProjectDetailLocalResourceRepresentation.class));
    Mockito.verify(mockModel, Mockito.times(1)).addAttribute(Mockito.eq(ModelConstants.MODEL_EDIT_PROJECT_WEB_RESOURCE), Mockito.any(ProjectDetailWebResourceRepresentation.class));
    Mockito.verify(mockModel, Mockito.times(1)).addAttribute(Mockito.eq(ModelConstants.MODEL_ADD_PROJECT_WEB_RESOURCE), Mockito.any(ProjectDetailWebResourceRepresentation.class));
    Mockito.verify(mockModel, Mockito.times(1)).addAttribute(ModelConstants.MODEL_MEDIA_LISTING_OPERATION, ModelConstants.MODEL_MEDIA_LISTING_OPERATION_PICTURES);
    Mockito.verify(mockModel, Mockito.times(1)).addAttribute(ModelConstants.MODEL_PROJECT_WEB_RESOURCE_ARTIFACT, true);

    // Assertions
    assert expectedException == null;
    assert (Long) projectDetailCommandDTOCaptor.getValue().get(ContextConstants.CONTEXT_PROJECT_ID) == 1L;
    assert (Long) editCommandDTOCaptor.getValue().get(ContextConstants.CONTEXT_WEB_RESOURCE_ID) == 1L;
    assert result.equals(ViewConstants.VIEW_MEDIA);
  }

  @Test
  public void testEditProjectWebResourceException() {
    // Initialize
    Model mockModel = Mockito.mock(Model.class);
    HttpServletRequest mockHttpServletRequest = Mockito.mock(HttpServletRequest.class);
    HttpSession mockHttpSession = Mockito.mock(HttpSession.class);

    ProjectMediaService mockProjectMediaService = Mockito.mock(ProjectMediaService.class);

    ArgumentCaptor<CommandDTO> projectDetailCommandDTOCaptor = ArgumentCaptor.forClass(CommandDTO.class);
    ArgumentCaptor<CommandDTO> editCommandDTOCaptor = ArgumentCaptor.forClass(CommandDTO.class);
    ArgumentCaptor<ErrorsRepresentation> errorsRepresentationCaptor = ArgumentCaptor.forClass(ErrorsRepresentation.class);

    ProjectMediaController projectMediaController = new ProjectMediaController();
    projectMediaController.projectMediaService = mockProjectMediaService;

    LoggedInUserRepresentation loggedInUserRepresentation = LoggedInUserRepresentation.builder().userName("username").build();
    ProjectDetailRepresentation projectDetailRepresentation = ProjectDetailRepresentation.builder().id(1L).build();

    CommandDTO responseCommandDTO = CommandDTO
        .builder()
        .add(ContextConstants.CONTEXT_PROJECT_DETAIL, projectDetailRepresentation)
        .build();

    Map<String, String> fieldMessages = new HashMap<>();
    fieldMessages.put("field1", "message1");
    GetProjectWebResourceFailedException responseException = new GetProjectWebResourceFailedException();
    responseException.setFieldMessages(fieldMessages);
    responseException.setMessages(Collections.singletonList("message1"));

    // Define Mocks
    Mockito.when(mockHttpServletRequest.getSession()).thenReturn(mockHttpSession);
    Mockito.when(mockHttpSession.getAttribute(SessionConstants.SESSION_LOGGED_IN_USER)).thenReturn(loggedInUserRepresentation);
    Mockito.when(mockModel.addAttribute(SessionConstants.SESSION_LOGGED_IN_USER, loggedInUserRepresentation)).thenReturn(mockModel);
    Mockito.when(mockProjectMediaService.getProjectDetail(Mockito.any())).thenReturn(responseCommandDTO);
    Mockito.when(mockProjectMediaService.getProjectWebResource(Mockito.any())).thenThrow(responseException);
    Mockito.when(mockModel.addAttribute(ModelConstants.MODEL_PROJECT_DETAIL, projectDetailRepresentation)).thenReturn(mockModel);
    Mockito.when(mockModel.addAttribute(Mockito.eq(ModelConstants.MODEL_ADD_PROJECT_LOCAL_RESOURCE), Mockito.any(ProjectDetailLocalResourceRepresentation.class))).thenReturn(mockModel);
    Mockito.when(mockModel.addAttribute(Mockito.eq(ModelConstants.MODEL_ADD_PROJECT_WEB_RESOURCE), Mockito.any(ProjectDetailWebResourceRepresentation.class))).thenReturn(mockModel);
    Mockito.when(mockModel.addAttribute(Mockito.eq(ModelConstants.MODEL_EDIT_PROJECT_LOCAL_RESOURCE), Mockito.any(ProjectDetailLocalResourceRepresentation.class))).thenReturn(mockModel);
    Mockito.when(mockModel.addAttribute(ModelConstants.MODEL_PROJECT_WEB_RESOURCE_ARTIFACT, true)).thenReturn(mockModel);
    Mockito.when(mockModel.addAttribute(ModelConstants.MODEL_MEDIA_LISTING_OPERATION, ModelConstants.MODEL_MEDIA_LISTING_OPERATION_PICTURES)).thenReturn(mockModel);

    // Execute
    Exception expectedException = null;
    String result = null;
    try {
      result = projectMediaController.editProjectWebResource(1L, 1L, mockModel, mockHttpServletRequest);
    } catch (Exception exception) {
      expectedException = exception;
    }

    // Verify
    Mockito.verify(mockModel, Mockito.times(1)).addAttribute(SessionConstants.SESSION_LOGGED_IN_USER, loggedInUserRepresentation);
    Mockito.verify(mockProjectMediaService, Mockito.times(1)).getProjectDetail(projectDetailCommandDTOCaptor.capture());
    Mockito.verify(mockProjectMediaService, Mockito.times(1)).getProjectWebResource(editCommandDTOCaptor.capture());
    Mockito.verify(mockModel, Mockito.times(1)).addAttribute(ModelConstants.MODEL_PROJECT_DETAIL, projectDetailRepresentation);
    Mockito.verify(mockModel, Mockito.times(1)).addAttribute(Mockito.eq(ModelConstants.MODEL_ADD_PROJECT_LOCAL_RESOURCE), Mockito.any(ProjectDetailLocalResourceRepresentation.class));
    Mockito.verify(mockModel, Mockito.times(1)).addAttribute(Mockito.eq(ModelConstants.MODEL_ADD_PROJECT_WEB_RESOURCE), Mockito.any(ProjectDetailWebResourceRepresentation.class));
    Mockito.verify(mockModel, Mockito.times(1)).addAttribute(ModelConstants.MODEL_MEDIA_LISTING_OPERATION, ModelConstants.MODEL_MEDIA_LISTING_OPERATION_PICTURES);
    Mockito.verify(mockModel, Mockito.times(1)).addAttribute(ModelConstants.MODEL_PROJECT_WEB_RESOURCE_ARTIFACT, true);
    Mockito.verify(mockModel, Mockito.times(1)).addAttribute(Mockito.eq(ModelConstants.MODEL_EDIT_PROJECT_WEB_RESOURCE_ERRORS), errorsRepresentationCaptor.capture());

    // Assertions
    assert expectedException == null;
    assert (Long) projectDetailCommandDTOCaptor.getValue().get(ContextConstants.CONTEXT_PROJECT_ID) == 1L;
    assert (Long) editCommandDTOCaptor.getValue().get(ContextConstants.CONTEXT_WEB_RESOURCE_ID) == 1L;
    assert errorsRepresentationCaptor.getValue().getErrorMessages().get(0).equals("message1");
    assert errorsRepresentationCaptor.getValue().getFieldErrorMessages().get("field1").equals("message1");
    assert result.equals(ViewConstants.VIEW_MEDIA);
  }

  @Test
  public void testDeleteProjectLocalResource() {
    // Initialize
    Model mockModel = Mockito.mock(Model.class);
    HttpServletRequest mockHttpServletRequest = Mockito.mock(HttpServletRequest.class);
    HttpSession mockHttpSession = Mockito.mock(HttpSession.class);

    ProjectMediaService mockProjectMediaService = Mockito.mock(ProjectMediaService.class);

    ArgumentCaptor<CommandDTO> projectDetailCommandDTOCaptor = ArgumentCaptor.forClass(CommandDTO.class);
    ArgumentCaptor<CommandDTO> deleteCommandDTOCaptor = ArgumentCaptor.forClass(CommandDTO.class);

    ProjectMediaController projectMediaController = new ProjectMediaController();
    projectMediaController.projectMediaService = mockProjectMediaService;

    LoggedInUserRepresentation loggedInUserRepresentation = LoggedInUserRepresentation.builder().userName("username").build();
    ProjectDetailRepresentation projectDetailRepresentation = ProjectDetailRepresentation.builder().id(1L).build();
    ProjectDetailLocalResourceRepresentation projectDetailLocalResourceRepresentation = ProjectDetailLocalResourceRepresentation
        .builder()
        .id(1L).resourceType("IMAGE")
        .build();

    CommandDTO responseCommandDTO = CommandDTO
        .builder()
        .add(ContextConstants.CONTEXT_PROJECT_DETAIL, projectDetailRepresentation)
        .build();
    CommandDTO deleteResponseCommandDTO = CommandDTO
        .builder()
        .add(ContextConstants.CONTEXT_PROJECT_LOCAL_RESOURCE, projectDetailLocalResourceRepresentation)
        .add(ContextConstants.CONTEXT_PROJECT_LOCAL_RESOURCE_ARTIFACT, "IMAGE")
        .build();

    // Define Mocks
    Mockito.when(mockHttpServletRequest.getSession()).thenReturn(mockHttpSession);
    Mockito.when(mockHttpSession.getAttribute(SessionConstants.SESSION_LOGGED_IN_USER)).thenReturn(loggedInUserRepresentation);
    Mockito.when(mockModel.addAttribute(SessionConstants.SESSION_LOGGED_IN_USER, loggedInUserRepresentation)).thenReturn(mockModel);
    Mockito.when(mockProjectMediaService.getProjectDetail(Mockito.any())).thenReturn(responseCommandDTO);
    Mockito.when(mockProjectMediaService.deleteProjectLocalResource(Mockito.any())).thenReturn(deleteResponseCommandDTO);
    Mockito.when(mockModel.addAttribute(ModelConstants.MODEL_PROJECT_DETAIL, projectDetailRepresentation)).thenReturn(mockModel);
    Mockito.when(mockModel.addAttribute(Mockito.eq(ModelConstants.MODEL_ADD_PROJECT_LOCAL_RESOURCE), Mockito.any(ProjectDetailLocalResourceRepresentation.class))).thenReturn(mockModel);
    Mockito.when(mockModel.addAttribute(Mockito.eq(ModelConstants.MODEL_ADD_PROJECT_WEB_RESOURCE), Mockito.any(ProjectDetailWebResourceRepresentation.class))).thenReturn(mockModel);
    Mockito.when(mockModel.addAttribute(Mockito.eq(ModelConstants.MODEL_EDIT_PROJECT_LOCAL_RESOURCE), Mockito.any(ProjectDetailLocalResourceRepresentation.class))).thenReturn(mockModel);
    Mockito.when(mockModel.addAttribute(ModelConstants.MODEL_MEDIA_LISTING_OPERATION, ModelConstants.MODEL_MEDIA_LISTING_OPERATION_PICTURES)).thenReturn(mockModel);
    Mockito.when(mockModel.addAttribute(ModelConstants.MODEL_MEDIA_LISTING_OPERATION, "pictures_save_success")).thenReturn(mockModel);
    Mockito.when(mockModel.addAttribute(ModelConstants.MODEL_SAVE_PICTURE_DISPLAY_SEQUENCE, 1)).thenReturn(mockModel);
    Mockito.when(mockModel.addAttribute(ModelConstants.MODEL_PROJECT_IMAGE_RESOURCE_ARTIFACT, true)).thenReturn(mockModel);

    // Execute
    Exception expectedException = null;
    String result = null;
    try {
      result = projectMediaController.deleteProjectLocalResource(1L, 1L, "IMAGE", mockModel, mockHttpServletRequest);
    } catch (Exception exception) {
      expectedException = exception;
    }

    // Verify
    Mockito.verify(mockModel, Mockito.times(1)).addAttribute(SessionConstants.SESSION_LOGGED_IN_USER, loggedInUserRepresentation);
    Mockito.verify(mockProjectMediaService, Mockito.times(1)).getProjectDetail(projectDetailCommandDTOCaptor.capture());
    Mockito.verify(mockProjectMediaService, Mockito.times(1)).deleteProjectLocalResource(deleteCommandDTOCaptor.capture());
    Mockito.verify(mockModel, Mockito.times(1)).addAttribute(ModelConstants.MODEL_PROJECT_DETAIL, projectDetailRepresentation);
    Mockito.verify(mockModel, Mockito.times(1)).addAttribute(Mockito.eq(ModelConstants.MODEL_ADD_PROJECT_LOCAL_RESOURCE), Mockito.any(ProjectDetailLocalResourceRepresentation.class));
    Mockito.verify(mockModel, Mockito.times(1)).addAttribute(Mockito.eq(ModelConstants.MODEL_ADD_PROJECT_WEB_RESOURCE), Mockito.any(ProjectDetailWebResourceRepresentation.class));
    Mockito.verify(mockModel, Mockito.times(1)).addAttribute(ModelConstants.MODEL_MEDIA_LISTING_OPERATION, ModelConstants.MODEL_MEDIA_LISTING_OPERATION_PICTURES);
    Mockito.verify(mockModel, Mockito.times(1)).addAttribute(ModelConstants.MODEL_MEDIA_LISTING_OPERATION, "pictures_save_success");
    Mockito.verify(mockModel, Mockito.times(1)).addAttribute(ModelConstants.MODEL_SAVE_PICTURE_DISPLAY_SEQUENCE, 1);
    Mockito.verify(mockModel, Mockito.times(1)).addAttribute(ModelConstants.MODEL_PROJECT_IMAGE_RESOURCE_ARTIFACT, true);

    // Assertions
    assert expectedException == null;
    assert (Long) projectDetailCommandDTOCaptor.getValue().get(ContextConstants.CONTEXT_PROJECT_ID) == 1L;
    assert (Long) deleteCommandDTOCaptor.getValue().get(ContextConstants.CONTEXT_LOCAL_RESOURCE_ID) == 1L;
    assert result.equals(ViewConstants.VIEW_MEDIA);
  }

  @Test
  public void testDeleteProjectLocalResourceGetProjectLocalResourceFailedException() {
    // Initialize
    Model mockModel = Mockito.mock(Model.class);
    HttpServletRequest mockHttpServletRequest = Mockito.mock(HttpServletRequest.class);
    HttpSession mockHttpSession = Mockito.mock(HttpSession.class);

    ProjectMediaService mockProjectMediaService = Mockito.mock(ProjectMediaService.class);

    ArgumentCaptor<CommandDTO> projectDetailCommandDTOCaptor = ArgumentCaptor.forClass(CommandDTO.class);
    ArgumentCaptor<CommandDTO> deleteCommandDTOCaptor = ArgumentCaptor.forClass(CommandDTO.class);
    ArgumentCaptor<ErrorsRepresentation> errorsRepresentationCaptor = ArgumentCaptor.forClass(ErrorsRepresentation.class);

    ProjectMediaController projectMediaController = new ProjectMediaController();
    projectMediaController.projectMediaService = mockProjectMediaService;

    LoggedInUserRepresentation loggedInUserRepresentation = LoggedInUserRepresentation.builder().userName("username").build();
    ProjectDetailRepresentation projectDetailRepresentation = ProjectDetailRepresentation.builder().id(1L).build();

    CommandDTO responseCommandDTO = CommandDTO
        .builder()
        .add(ContextConstants.CONTEXT_PROJECT_DETAIL, projectDetailRepresentation)
        .build();

    Map<String, String> fieldMessages = new HashMap<>();
    fieldMessages.put("field1", "message1");
    GetProjectLocalResourceFailedException responseException = new GetProjectLocalResourceFailedException();
    responseException.setFieldMessages(fieldMessages);
    responseException.setMessages(Collections.singletonList("message1"));

    // Define Mocks
    Mockito.when(mockHttpServletRequest.getSession()).thenReturn(mockHttpSession);
    Mockito.when(mockHttpSession.getAttribute(SessionConstants.SESSION_LOGGED_IN_USER)).thenReturn(loggedInUserRepresentation);
    Mockito.when(mockModel.addAttribute(SessionConstants.SESSION_LOGGED_IN_USER, loggedInUserRepresentation)).thenReturn(mockModel);
    Mockito.when(mockProjectMediaService.getProjectDetail(Mockito.any())).thenReturn(responseCommandDTO);
    Mockito.when(mockProjectMediaService.deleteProjectLocalResource(Mockito.any())).thenThrow(responseException);
    Mockito.when(mockModel.addAttribute(ModelConstants.MODEL_PROJECT_DETAIL, projectDetailRepresentation)).thenReturn(mockModel);
    Mockito.when(mockModel.addAttribute(Mockito.eq(ModelConstants.MODEL_ADD_PROJECT_LOCAL_RESOURCE), Mockito.any(ProjectDetailLocalResourceRepresentation.class))).thenReturn(mockModel);
    Mockito.when(mockModel.addAttribute(Mockito.eq(ModelConstants.MODEL_ADD_PROJECT_WEB_RESOURCE), Mockito.any(ProjectDetailWebResourceRepresentation.class))).thenReturn(mockModel);
    Mockito.when(mockModel.addAttribute(Mockito.eq(ModelConstants.MODEL_EDIT_PROJECT_LOCAL_RESOURCE), Mockito.any(ProjectDetailLocalResourceRepresentation.class))).thenReturn(mockModel);
    Mockito.when(mockModel.addAttribute(ModelConstants.MODEL_MEDIA_LISTING_OPERATION, ModelConstants.MODEL_MEDIA_LISTING_OPERATION_PICTURES)).thenReturn(mockModel);
    Mockito.when(mockModel.addAttribute(ModelConstants.MODEL_MEDIA_LISTING_OPERATION, "pictures_save_success")).thenReturn(mockModel);
    Mockito.when(mockModel.addAttribute(ModelConstants.MODEL_SAVE_PICTURE_DISPLAY_SEQUENCE, 1)).thenReturn(mockModel);
    Mockito.when(mockModel.addAttribute(ModelConstants.MODEL_PROJECT_IMAGE_RESOURCE_ARTIFACT, true)).thenReturn(mockModel);

    // Execute
    Exception expectedException = null;
    String result = null;
    try {
      result = projectMediaController.deleteProjectLocalResource(1L, 1L, "IMAGE", mockModel, mockHttpServletRequest);
    } catch (Exception exception) {
      expectedException = exception;
    }

    // Verify
    Mockito.verify(mockModel, Mockito.times(1)).addAttribute(SessionConstants.SESSION_LOGGED_IN_USER, loggedInUserRepresentation);
    Mockito.verify(mockProjectMediaService, Mockito.times(1)).getProjectDetail(projectDetailCommandDTOCaptor.capture());
    Mockito.verify(mockProjectMediaService, Mockito.times(1)).deleteProjectLocalResource(deleteCommandDTOCaptor.capture());
    Mockito.verify(mockModel, Mockito.times(1)).addAttribute(ModelConstants.MODEL_PROJECT_DETAIL, projectDetailRepresentation);
    Mockito.verify(mockModel, Mockito.times(1)).addAttribute(Mockito.eq(ModelConstants.MODEL_ADD_PROJECT_LOCAL_RESOURCE), Mockito.any(ProjectDetailLocalResourceRepresentation.class));
    Mockito.verify(mockModel, Mockito.times(1)).addAttribute(Mockito.eq(ModelConstants.MODEL_ADD_PROJECT_WEB_RESOURCE), Mockito.any(ProjectDetailWebResourceRepresentation.class));
    Mockito.verify(mockModel, Mockito.times(1)).addAttribute(ModelConstants.MODEL_MEDIA_LISTING_OPERATION, ModelConstants.MODEL_MEDIA_LISTING_OPERATION_PICTURES);
    Mockito.verify(mockModel, Mockito.times(1)).addAttribute(ModelConstants.MODEL_MEDIA_LISTING_OPERATION, "pictures_save_success");
    Mockito.verify(mockModel, Mockito.times(1)).addAttribute(ModelConstants.MODEL_SAVE_PICTURE_DISPLAY_SEQUENCE, 1);
    Mockito.verify(mockModel, Mockito.times(1)).addAttribute(ModelConstants.MODEL_PROJECT_IMAGE_RESOURCE_ARTIFACT, true);
    Mockito.verify(mockModel, Mockito.times(1)).addAttribute(Mockito.eq(ModelConstants.MODEL_DELETE_PROJECT_LOCAL_RESOURCE_ERRORS), errorsRepresentationCaptor.capture());

    // Assertions
    assert expectedException == null;
    assert (Long) projectDetailCommandDTOCaptor.getValue().get(ContextConstants.CONTEXT_PROJECT_ID) == 1L;
    assert (Long) deleteCommandDTOCaptor.getValue().get(ContextConstants.CONTEXT_LOCAL_RESOURCE_ID) == 1L;
    assert errorsRepresentationCaptor.getValue().getErrorMessages().get(0).equals("message1");
    assert errorsRepresentationCaptor.getValue().getFieldErrorMessages().get("field1").equals("message1");
    assert result.equals(ViewConstants.VIEW_MEDIA);
  }

  @Test
  public void testDeleteProjectLocalResourceDeleteProjectLocalResourceFailedException() {
    // Initialize
    Model mockModel = Mockito.mock(Model.class);
    HttpServletRequest mockHttpServletRequest = Mockito.mock(HttpServletRequest.class);
    HttpSession mockHttpSession = Mockito.mock(HttpSession.class);

    ProjectMediaService mockProjectMediaService = Mockito.mock(ProjectMediaService.class);

    ArgumentCaptor<CommandDTO> projectDetailCommandDTOCaptor = ArgumentCaptor.forClass(CommandDTO.class);
    ArgumentCaptor<CommandDTO> deleteCommandDTOCaptor = ArgumentCaptor.forClass(CommandDTO.class);
    ArgumentCaptor<ErrorsRepresentation> errorsRepresentationCaptor = ArgumentCaptor.forClass(ErrorsRepresentation.class);

    ProjectMediaController projectMediaController = new ProjectMediaController();
    projectMediaController.projectMediaService = mockProjectMediaService;

    LoggedInUserRepresentation loggedInUserRepresentation = LoggedInUserRepresentation.builder().userName("username").build();
    ProjectDetailRepresentation projectDetailRepresentation = ProjectDetailRepresentation.builder().id(1L).build();

    CommandDTO responseCommandDTO = CommandDTO
        .builder()
        .add(ContextConstants.CONTEXT_PROJECT_DETAIL, projectDetailRepresentation)
        .build();

    Map<String, String> fieldMessages = new HashMap<>();
    fieldMessages.put("field1", "message1");
    DeleteProjectLocalResourceFailedException responseException = new DeleteProjectLocalResourceFailedException();
    responseException.setFieldMessages(fieldMessages);
    responseException.setMessages(Collections.singletonList("message1"));

    // Define Mocks
    Mockito.when(mockHttpServletRequest.getSession()).thenReturn(mockHttpSession);
    Mockito.when(mockHttpSession.getAttribute(SessionConstants.SESSION_LOGGED_IN_USER)).thenReturn(loggedInUserRepresentation);
    Mockito.when(mockModel.addAttribute(SessionConstants.SESSION_LOGGED_IN_USER, loggedInUserRepresentation)).thenReturn(mockModel);
    Mockito.when(mockProjectMediaService.getProjectDetail(Mockito.any())).thenReturn(responseCommandDTO);
    Mockito.when(mockProjectMediaService.deleteProjectLocalResource(Mockito.any())).thenThrow(responseException);
    Mockito.when(mockModel.addAttribute(ModelConstants.MODEL_PROJECT_DETAIL, projectDetailRepresentation)).thenReturn(mockModel);
    Mockito.when(mockModel.addAttribute(Mockito.eq(ModelConstants.MODEL_ADD_PROJECT_LOCAL_RESOURCE), Mockito.any(ProjectDetailLocalResourceRepresentation.class))).thenReturn(mockModel);
    Mockito.when(mockModel.addAttribute(Mockito.eq(ModelConstants.MODEL_ADD_PROJECT_WEB_RESOURCE), Mockito.any(ProjectDetailWebResourceRepresentation.class))).thenReturn(mockModel);
    Mockito.when(mockModel.addAttribute(Mockito.eq(ModelConstants.MODEL_EDIT_PROJECT_LOCAL_RESOURCE), Mockito.any(ProjectDetailLocalResourceRepresentation.class))).thenReturn(mockModel);
    Mockito.when(mockModel.addAttribute(ModelConstants.MODEL_MEDIA_LISTING_OPERATION, ModelConstants.MODEL_MEDIA_LISTING_OPERATION_PICTURES)).thenReturn(mockModel);
    Mockito.when(mockModel.addAttribute(ModelConstants.MODEL_MEDIA_LISTING_OPERATION, "pictures_save_success")).thenReturn(mockModel);
    Mockito.when(mockModel.addAttribute(ModelConstants.MODEL_SAVE_PICTURE_DISPLAY_SEQUENCE, 1)).thenReturn(mockModel);
    Mockito.when(mockModel.addAttribute(ModelConstants.MODEL_PROJECT_IMAGE_RESOURCE_ARTIFACT, true)).thenReturn(mockModel);

    // Execute
    Exception expectedException = null;
    String result = null;
    try {
      result = projectMediaController.deleteProjectLocalResource(1L, 1L, "IMAGE", mockModel, mockHttpServletRequest);
    } catch (Exception exception) {
      expectedException = exception;
    }

    // Verify
    Mockito.verify(mockModel, Mockito.times(1)).addAttribute(SessionConstants.SESSION_LOGGED_IN_USER, loggedInUserRepresentation);
    Mockito.verify(mockProjectMediaService, Mockito.times(1)).getProjectDetail(projectDetailCommandDTOCaptor.capture());
    Mockito.verify(mockProjectMediaService, Mockito.times(1)).deleteProjectLocalResource(deleteCommandDTOCaptor.capture());
    Mockito.verify(mockModel, Mockito.times(1)).addAttribute(ModelConstants.MODEL_PROJECT_DETAIL, projectDetailRepresentation);
    Mockito.verify(mockModel, Mockito.times(1)).addAttribute(Mockito.eq(ModelConstants.MODEL_ADD_PROJECT_LOCAL_RESOURCE), Mockito.any(ProjectDetailLocalResourceRepresentation.class));
    Mockito.verify(mockModel, Mockito.times(1)).addAttribute(Mockito.eq(ModelConstants.MODEL_ADD_PROJECT_WEB_RESOURCE), Mockito.any(ProjectDetailWebResourceRepresentation.class));
    Mockito.verify(mockModel, Mockito.times(1)).addAttribute(ModelConstants.MODEL_MEDIA_LISTING_OPERATION, ModelConstants.MODEL_MEDIA_LISTING_OPERATION_PICTURES);
    Mockito.verify(mockModel, Mockito.times(1)).addAttribute(ModelConstants.MODEL_MEDIA_LISTING_OPERATION, "pictures_save_success");
    Mockito.verify(mockModel, Mockito.times(1)).addAttribute(ModelConstants.MODEL_SAVE_PICTURE_DISPLAY_SEQUENCE, 1);
    Mockito.verify(mockModel, Mockito.times(1)).addAttribute(ModelConstants.MODEL_PROJECT_IMAGE_RESOURCE_ARTIFACT, true);
    Mockito.verify(mockModel, Mockito.times(1)).addAttribute(Mockito.eq(ModelConstants.MODEL_DELETE_PROJECT_LOCAL_RESOURCE_ERRORS), errorsRepresentationCaptor.capture());

    // Assertions
    assert expectedException == null;
    assert (Long) projectDetailCommandDTOCaptor.getValue().get(ContextConstants.CONTEXT_PROJECT_ID) == 1L;
    assert (Long) deleteCommandDTOCaptor.getValue().get(ContextConstants.CONTEXT_LOCAL_RESOURCE_ID) == 1L;
    assert errorsRepresentationCaptor.getValue().getErrorMessages().get(0).equals("message1");
    assert errorsRepresentationCaptor.getValue().getFieldErrorMessages().get("field1").equals("message1");
    assert result.equals(ViewConstants.VIEW_MEDIA);
  }

  @Test
  public void testDeleteProjectWebResource() {
    // Initialize
    Model mockModel = Mockito.mock(Model.class);
    HttpServletRequest mockHttpServletRequest = Mockito.mock(HttpServletRequest.class);
    HttpSession mockHttpSession = Mockito.mock(HttpSession.class);

    ProjectMediaService mockProjectMediaService = Mockito.mock(ProjectMediaService.class);

    ArgumentCaptor<CommandDTO> projectDetailCommandDTOCaptor = ArgumentCaptor.forClass(CommandDTO.class);
    ArgumentCaptor<CommandDTO> deleteCommandDTOCaptor = ArgumentCaptor.forClass(CommandDTO.class);

    ProjectMediaController projectMediaController = new ProjectMediaController();
    projectMediaController.projectMediaService = mockProjectMediaService;

    LoggedInUserRepresentation loggedInUserRepresentation = LoggedInUserRepresentation.builder().userName("username").build();
    ProjectDetailRepresentation projectDetailRepresentation = ProjectDetailRepresentation.builder().id(1L).build();
    ProjectDetailWebResourceRepresentation projectDetailWebResourceRepresentation = ProjectDetailWebResourceRepresentation.builder().id(1L).build();

    CommandDTO responseCommandDTO = CommandDTO
        .builder()
        .add(ContextConstants.CONTEXT_PROJECT_DETAIL, projectDetailRepresentation)
        .build();
    CommandDTO deleteResponseCommandDTO = CommandDTO
        .builder()
        .add(ContextConstants.CONTEXT_PROJECT_WEB_RESOURCE, projectDetailWebResourceRepresentation)
        .build();

    // Define Mocks
    Mockito.when(mockHttpServletRequest.getSession()).thenReturn(mockHttpSession);
    Mockito.when(mockHttpSession.getAttribute(SessionConstants.SESSION_LOGGED_IN_USER)).thenReturn(loggedInUserRepresentation);
    Mockito.when(mockModel.addAttribute(SessionConstants.SESSION_LOGGED_IN_USER, loggedInUserRepresentation)).thenReturn(mockModel);
    Mockito.when(mockProjectMediaService.getProjectDetail(Mockito.any())).thenReturn(responseCommandDTO);
    Mockito.when(mockProjectMediaService.deleteProjectWebResource(Mockito.any())).thenReturn(deleteResponseCommandDTO);
    Mockito.when(mockModel.addAttribute(ModelConstants.MODEL_PROJECT_DETAIL, projectDetailRepresentation)).thenReturn(mockModel);
    Mockito.when(mockModel.addAttribute(Mockito.eq(ModelConstants.MODEL_ADD_PROJECT_LOCAL_RESOURCE), Mockito.any(ProjectDetailLocalResourceRepresentation.class))).thenReturn(mockModel);
    Mockito.when(mockModel.addAttribute(Mockito.eq(ModelConstants.MODEL_ADD_PROJECT_WEB_RESOURCE), Mockito.any(ProjectDetailWebResourceRepresentation.class))).thenReturn(mockModel);
    Mockito.when(mockModel.addAttribute(Mockito.eq(ModelConstants.MODEL_EDIT_PROJECT_LOCAL_RESOURCE), Mockito.any(ProjectDetailLocalResourceRepresentation.class))).thenReturn(mockModel);
    Mockito.when(mockModel.addAttribute(ModelConstants.MODEL_PROJECT_WEB_RESOURCE_ARTIFACT, true)).thenReturn(mockModel);
    Mockito.when(mockModel.addAttribute(ModelConstants.MODEL_MEDIA_LISTING_OPERATION, WebResourceType.WEB.getValue())).thenReturn(mockModel);
    Mockito.when(mockModel.addAttribute(ModelConstants.MODEL_SAVE_WEBLINK_DISPLAY_SEQUENCE, 1)).thenReturn(mockModel);

    // Execute
    Exception expectedException = null;
    String result = null;
    try {
      result = projectMediaController.deleteProjectWebResource(1L, 1L, mockModel, mockHttpServletRequest);
    } catch (Exception exception) {
      expectedException = exception;
    }

    // Verify
    Mockito.verify(mockModel, Mockito.times(1)).addAttribute(SessionConstants.SESSION_LOGGED_IN_USER, loggedInUserRepresentation);
    Mockito.verify(mockProjectMediaService, Mockito.times(1)).getProjectDetail(projectDetailCommandDTOCaptor.capture());
    Mockito.verify(mockProjectMediaService, Mockito.times(1)).deleteProjectWebResource(deleteCommandDTOCaptor.capture());
    Mockito.verify(mockModel, Mockito.times(1)).addAttribute(ModelConstants.MODEL_PROJECT_DETAIL, projectDetailRepresentation);
    Mockito.verify(mockModel, Mockito.times(1)).addAttribute(Mockito.eq(ModelConstants.MODEL_ADD_PROJECT_LOCAL_RESOURCE), Mockito.any(ProjectDetailLocalResourceRepresentation.class));
    Mockito.verify(mockModel, Mockito.times(1)).addAttribute(Mockito.eq(ModelConstants.MODEL_ADD_PROJECT_WEB_RESOURCE), Mockito.any(ProjectDetailWebResourceRepresentation.class));
    Mockito.verify(mockModel, Mockito.times(1)).addAttribute(ModelConstants.MODEL_MEDIA_LISTING_OPERATION, WebResourceType.WEB.getValue());
    Mockito.verify(mockModel, Mockito.times(1)).addAttribute(ModelConstants.MODEL_PROJECT_WEB_RESOURCE_ARTIFACT, true);
    Mockito.verify(mockModel, Mockito.times(1)).addAttribute(ModelConstants.MODEL_SAVE_WEBLINK_DISPLAY_SEQUENCE, 1);

    // Assertions
    assert expectedException == null;
    assert (Long) projectDetailCommandDTOCaptor.getValue().get(ContextConstants.CONTEXT_PROJECT_ID) == 1L;
    assert (Long) deleteCommandDTOCaptor.getValue().get(ContextConstants.CONTEXT_WEB_RESOURCE_ID) == 1L;
    assert result.equals(ViewConstants.VIEW_MEDIA);
  }

  @Test
  public void testDeleteProjectWebResourceGetProjectLocalResourceFailedException() {
    // Initialize
    Model mockModel = Mockito.mock(Model.class);
    HttpServletRequest mockHttpServletRequest = Mockito.mock(HttpServletRequest.class);
    HttpSession mockHttpSession = Mockito.mock(HttpSession.class);

    ProjectMediaService mockProjectMediaService = Mockito.mock(ProjectMediaService.class);

    ArgumentCaptor<CommandDTO> projectDetailCommandDTOCaptor = ArgumentCaptor.forClass(CommandDTO.class);
    ArgumentCaptor<CommandDTO> deleteCommandDTOCaptor = ArgumentCaptor.forClass(CommandDTO.class);
    ArgumentCaptor<ErrorsRepresentation> errorsRepresentationCaptor = ArgumentCaptor.forClass(ErrorsRepresentation.class);

    ProjectMediaController projectMediaController = new ProjectMediaController();
    projectMediaController.projectMediaService = mockProjectMediaService;

    LoggedInUserRepresentation loggedInUserRepresentation = LoggedInUserRepresentation.builder().userName("username").build();
    ProjectDetailRepresentation projectDetailRepresentation = ProjectDetailRepresentation.builder().id(1L).build();
    ProjectDetailWebResourceRepresentation projectDetailWebResourceRepresentation = ProjectDetailWebResourceRepresentation.builder().id(1L).build();

    CommandDTO responseCommandDTO = CommandDTO
        .builder()
        .add(ContextConstants.CONTEXT_PROJECT_DETAIL, projectDetailRepresentation)
        .build();

    Map<String, String> fieldMessages = new HashMap<>();
    fieldMessages.put("field1", "message1");
    GetProjectLocalResourceFailedException responseException = new GetProjectLocalResourceFailedException();
    responseException.setFieldMessages(fieldMessages);
    responseException.setMessages(Collections.singletonList("message1"));

    // Define Mocks
    Mockito.when(mockHttpServletRequest.getSession()).thenReturn(mockHttpSession);
    Mockito.when(mockHttpSession.getAttribute(SessionConstants.SESSION_LOGGED_IN_USER)).thenReturn(loggedInUserRepresentation);
    Mockito.when(mockModel.addAttribute(SessionConstants.SESSION_LOGGED_IN_USER, loggedInUserRepresentation)).thenReturn(mockModel);
    Mockito.when(mockProjectMediaService.getProjectDetail(Mockito.any())).thenReturn(responseCommandDTO);
    Mockito.when(mockProjectMediaService.deleteProjectWebResource(Mockito.any())).thenThrow(responseException);
    Mockito.when(mockModel.addAttribute(ModelConstants.MODEL_PROJECT_DETAIL, projectDetailRepresentation)).thenReturn(mockModel);
    Mockito.when(mockModel.addAttribute(Mockito.eq(ModelConstants.MODEL_ADD_PROJECT_LOCAL_RESOURCE), Mockito.any(ProjectDetailLocalResourceRepresentation.class))).thenReturn(mockModel);
    Mockito.when(mockModel.addAttribute(Mockito.eq(ModelConstants.MODEL_ADD_PROJECT_WEB_RESOURCE), Mockito.any(ProjectDetailWebResourceRepresentation.class))).thenReturn(mockModel);
    Mockito.when(mockModel.addAttribute(Mockito.eq(ModelConstants.MODEL_EDIT_PROJECT_LOCAL_RESOURCE), Mockito.any(ProjectDetailLocalResourceRepresentation.class))).thenReturn(mockModel);
    Mockito.when(mockModel.addAttribute(ModelConstants.MODEL_PROJECT_WEB_RESOURCE_ARTIFACT, true)).thenReturn(mockModel);
    Mockito.when(mockModel.addAttribute(ModelConstants.MODEL_MEDIA_LISTING_OPERATION, WebResourceType.WEB.getValue())).thenReturn(mockModel);
    Mockito.when(mockModel.addAttribute(ModelConstants.MODEL_SAVE_WEBLINK_DISPLAY_SEQUENCE, 1)).thenReturn(mockModel);

    // Execute
    Exception expectedException = null;
    String result = null;
    try {
      result = projectMediaController.deleteProjectWebResource(1L, 1L, mockModel, mockHttpServletRequest);
    } catch (Exception exception) {
      expectedException = exception;
    }

    // Verify
    Mockito.verify(mockModel, Mockito.times(1)).addAttribute(SessionConstants.SESSION_LOGGED_IN_USER, loggedInUserRepresentation);
    Mockito.verify(mockProjectMediaService, Mockito.times(1)).getProjectDetail(projectDetailCommandDTOCaptor.capture());
    Mockito.verify(mockProjectMediaService, Mockito.times(1)).deleteProjectWebResource(deleteCommandDTOCaptor.capture());
    Mockito.verify(mockModel, Mockito.times(1)).addAttribute(ModelConstants.MODEL_PROJECT_DETAIL, projectDetailRepresentation);
    Mockito.verify(mockModel, Mockito.times(1)).addAttribute(Mockito.eq(ModelConstants.MODEL_ADD_PROJECT_LOCAL_RESOURCE), Mockito.any(ProjectDetailLocalResourceRepresentation.class));
    Mockito.verify(mockModel, Mockito.times(1)).addAttribute(Mockito.eq(ModelConstants.MODEL_ADD_PROJECT_WEB_RESOURCE), Mockito.any(ProjectDetailWebResourceRepresentation.class));
    Mockito.verify(mockModel, Mockito.times(1)).addAttribute(ModelConstants.MODEL_MEDIA_LISTING_OPERATION, WebResourceType.WEB.getValue());
    Mockito.verify(mockModel, Mockito.times(1)).addAttribute(ModelConstants.MODEL_PROJECT_WEB_RESOURCE_ARTIFACT, true);
    Mockito.verify(mockModel, Mockito.times(1)).addAttribute(ModelConstants.MODEL_SAVE_WEBLINK_DISPLAY_SEQUENCE, 1);
    Mockito.verify(mockModel, Mockito.times(1)).addAttribute(Mockito.eq(ModelConstants.MODEL_DELETE_PROJECT_WEB_RESOURCE_ERRORS), errorsRepresentationCaptor.capture());

    // Assertions
    assert expectedException == null;
    assert (Long) projectDetailCommandDTOCaptor.getValue().get(ContextConstants.CONTEXT_PROJECT_ID) == 1L;
    assert (Long) deleteCommandDTOCaptor.getValue().get(ContextConstants.CONTEXT_WEB_RESOURCE_ID) == 1L;
    assert errorsRepresentationCaptor.getValue().getErrorMessages().get(0).equals("message1");
    assert errorsRepresentationCaptor.getValue().getFieldErrorMessages().get("field1").equals("message1");
    assert result.equals(ViewConstants.VIEW_MEDIA);
  }

  @Test
  public void testDeleteProjectWebResourceDeleteProjectWebResourceFailedException() {
    // Initialize
    Model mockModel = Mockito.mock(Model.class);
    HttpServletRequest mockHttpServletRequest = Mockito.mock(HttpServletRequest.class);
    HttpSession mockHttpSession = Mockito.mock(HttpSession.class);

    ProjectMediaService mockProjectMediaService = Mockito.mock(ProjectMediaService.class);

    ArgumentCaptor<CommandDTO> projectDetailCommandDTOCaptor = ArgumentCaptor.forClass(CommandDTO.class);
    ArgumentCaptor<CommandDTO> deleteCommandDTOCaptor = ArgumentCaptor.forClass(CommandDTO.class);
    ArgumentCaptor<ErrorsRepresentation> errorsRepresentationCaptor = ArgumentCaptor.forClass(ErrorsRepresentation.class);

    ProjectMediaController projectMediaController = new ProjectMediaController();
    projectMediaController.projectMediaService = mockProjectMediaService;

    LoggedInUserRepresentation loggedInUserRepresentation = LoggedInUserRepresentation.builder().userName("username").build();
    ProjectDetailRepresentation projectDetailRepresentation = ProjectDetailRepresentation.builder().id(1L).build();
    ProjectDetailWebResourceRepresentation projectDetailWebResourceRepresentation = ProjectDetailWebResourceRepresentation.builder().id(1L).build();

    CommandDTO responseCommandDTO = CommandDTO
        .builder()
        .add(ContextConstants.CONTEXT_PROJECT_DETAIL, projectDetailRepresentation)
        .build();

    Map<String, String> fieldMessages = new HashMap<>();
    fieldMessages.put("field1", "message1");
    DeleteProjectWebResourceFailedException responseException = new DeleteProjectWebResourceFailedException();
    responseException.setFieldMessages(fieldMessages);
    responseException.setMessages(Collections.singletonList("message1"));

    // Define Mocks
    Mockito.when(mockHttpServletRequest.getSession()).thenReturn(mockHttpSession);
    Mockito.when(mockHttpSession.getAttribute(SessionConstants.SESSION_LOGGED_IN_USER)).thenReturn(loggedInUserRepresentation);
    Mockito.when(mockModel.addAttribute(SessionConstants.SESSION_LOGGED_IN_USER, loggedInUserRepresentation)).thenReturn(mockModel);
    Mockito.when(mockProjectMediaService.getProjectDetail(Mockito.any())).thenReturn(responseCommandDTO);
    Mockito.when(mockProjectMediaService.deleteProjectWebResource(Mockito.any())).thenThrow(responseException);
    Mockito.when(mockModel.addAttribute(ModelConstants.MODEL_PROJECT_DETAIL, projectDetailRepresentation)).thenReturn(mockModel);
    Mockito.when(mockModel.addAttribute(Mockito.eq(ModelConstants.MODEL_ADD_PROJECT_LOCAL_RESOURCE), Mockito.any(ProjectDetailLocalResourceRepresentation.class))).thenReturn(mockModel);
    Mockito.when(mockModel.addAttribute(Mockito.eq(ModelConstants.MODEL_ADD_PROJECT_WEB_RESOURCE), Mockito.any(ProjectDetailWebResourceRepresentation.class))).thenReturn(mockModel);
    Mockito.when(mockModel.addAttribute(Mockito.eq(ModelConstants.MODEL_EDIT_PROJECT_LOCAL_RESOURCE), Mockito.any(ProjectDetailLocalResourceRepresentation.class))).thenReturn(mockModel);
    Mockito.when(mockModel.addAttribute(ModelConstants.MODEL_PROJECT_WEB_RESOURCE_ARTIFACT, true)).thenReturn(mockModel);
    Mockito.when(mockModel.addAttribute(ModelConstants.MODEL_MEDIA_LISTING_OPERATION, WebResourceType.WEB.getValue())).thenReturn(mockModel);
    Mockito.when(mockModel.addAttribute(ModelConstants.MODEL_SAVE_WEBLINK_DISPLAY_SEQUENCE, 1)).thenReturn(mockModel);

    // Execute
    Exception expectedException = null;
    String result = null;
    try {
      result = projectMediaController.deleteProjectWebResource(1L, 1L, mockModel, mockHttpServletRequest);
    } catch (Exception exception) {
      expectedException = exception;
    }

    // Verify
    Mockito.verify(mockModel, Mockito.times(1)).addAttribute(SessionConstants.SESSION_LOGGED_IN_USER, loggedInUserRepresentation);
    Mockito.verify(mockProjectMediaService, Mockito.times(1)).getProjectDetail(projectDetailCommandDTOCaptor.capture());
    Mockito.verify(mockProjectMediaService, Mockito.times(1)).deleteProjectWebResource(deleteCommandDTOCaptor.capture());
    Mockito.verify(mockModel, Mockito.times(1)).addAttribute(ModelConstants.MODEL_PROJECT_DETAIL, projectDetailRepresentation);
    Mockito.verify(mockModel, Mockito.times(1)).addAttribute(Mockito.eq(ModelConstants.MODEL_ADD_PROJECT_LOCAL_RESOURCE), Mockito.any(ProjectDetailLocalResourceRepresentation.class));
    Mockito.verify(mockModel, Mockito.times(1)).addAttribute(Mockito.eq(ModelConstants.MODEL_ADD_PROJECT_WEB_RESOURCE), Mockito.any(ProjectDetailWebResourceRepresentation.class));
    Mockito.verify(mockModel, Mockito.times(1)).addAttribute(ModelConstants.MODEL_MEDIA_LISTING_OPERATION, WebResourceType.WEB.getValue());
    Mockito.verify(mockModel, Mockito.times(1)).addAttribute(ModelConstants.MODEL_PROJECT_WEB_RESOURCE_ARTIFACT, true);
    Mockito.verify(mockModel, Mockito.times(1)).addAttribute(ModelConstants.MODEL_SAVE_WEBLINK_DISPLAY_SEQUENCE, 1);
    Mockito.verify(mockModel, Mockito.times(1)).addAttribute(Mockito.eq(ModelConstants.MODEL_DELETE_PROJECT_WEB_RESOURCE_ERRORS), errorsRepresentationCaptor.capture());

    // Assertions
    assert expectedException == null;
    assert (Long) projectDetailCommandDTOCaptor.getValue().get(ContextConstants.CONTEXT_PROJECT_ID) == 1L;
    assert (Long) deleteCommandDTOCaptor.getValue().get(ContextConstants.CONTEXT_WEB_RESOURCE_ID) == 1L;
    assert errorsRepresentationCaptor.getValue().getErrorMessages().get(0).equals("message1");
    assert errorsRepresentationCaptor.getValue().getFieldErrorMessages().get("field1").equals("message1");
    assert result.equals(ViewConstants.VIEW_MEDIA);
  }
}
