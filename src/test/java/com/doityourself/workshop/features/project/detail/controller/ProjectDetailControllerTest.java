package com.doityourself.workshop.features.project.detail.controller;

import com.doityourself.workshop.common.command.CommandDTO;
import com.doityourself.workshop.common.constants.ContextConstants;
import com.doityourself.workshop.common.constants.ModelConstants;
import com.doityourself.workshop.common.constants.SessionConstants;
import com.doityourself.workshop.common.constants.ViewConstants;
import com.doityourself.workshop.common.representation.error.ErrorsRepresentation;
import com.doityourself.workshop.features.login.representation.LoggedInUserRepresentation;
import com.doityourself.workshop.features.project.detail.exception.*;
import com.doityourself.workshop.features.project.detail.representation.ProjectDetailInstructionRepresentation;
import com.doityourself.workshop.features.project.detail.representation.ProjectDetailRepresentation;
import com.doityourself.workshop.features.project.detail.service.ProjectDetailService;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import org.springframework.ui.Model;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class ProjectDetailControllerTest {
  @Test
  public void testGetProjectDetail() {
    // Initialize
    Model mockModel = Mockito.mock(Model.class);
    HttpServletRequest mockHttpServletRequest = Mockito.mock(HttpServletRequest.class);
    HttpSession mockHttpSession = Mockito.mock(HttpSession.class);

    ArgumentCaptor<CommandDTO> projectDetailCommandDTOCaptor = ArgumentCaptor.forClass(CommandDTO.class);

    LoggedInUserRepresentation loggedInUserRepresentation = LoggedInUserRepresentation.builder().userName("username").build();
    ProjectDetailRepresentation projectDetailRepresentation = ProjectDetailRepresentation.builder().id(1L).build();

    ProjectDetailService mockProjectDetailService = Mockito.mock(ProjectDetailService.class);

    ProjectDetailController projectDetailController = new ProjectDetailController();
    projectDetailController.projectDetailService = mockProjectDetailService;
    CommandDTO responseCommandDTO = CommandDTO
        .builder()
        .add(ContextConstants.CONTEXT_PROJECT_DETAIL, projectDetailRepresentation)
        .build();

    // Define Mocks
    Mockito.when(mockHttpServletRequest.getSession()).thenReturn(mockHttpSession);
    Mockito.when(mockHttpSession.getAttribute(SessionConstants.SESSION_LOGGED_IN_USER)).thenReturn(loggedInUserRepresentation);
    Mockito.when(mockModel.addAttribute(SessionConstants.SESSION_LOGGED_IN_USER, loggedInUserRepresentation)).thenReturn(mockModel);
    Mockito.when(mockModel.addAttribute(ModelConstants.MODEL_PROJECT_DETAIL, projectDetailRepresentation)).thenReturn(mockModel);
    Mockito.when(mockModel.addAttribute(Mockito.eq(ModelConstants.MODEL_ADD_PROJECT_DESCRIPTION), Mockito.any(ProjectDetailRepresentation.class))).thenReturn(mockModel);
    Mockito.when(mockModel.addAttribute(Mockito.eq(ModelConstants.MODEL_ADD_PROJECT_INSTRUCTION), Mockito.any(ProjectDetailInstructionRepresentation.class))).thenReturn(mockModel);
    Mockito.when(mockModel.addAttribute(ModelConstants.MODEL_PROJECT_DETAIL, projectDetailRepresentation)).thenReturn(mockModel);
    Mockito.when(mockProjectDetailService.getProjectDetail(Mockito.any())).thenReturn(responseCommandDTO);
    Mockito.doNothing().when(mockHttpSession).setAttribute(ModelConstants.MODEL_IS_SHARED_PROJECT, false);

    // Execute
    Exception expectedException = null;
    String result = null;
    try {
      result = projectDetailController.getProjectDetail(1L, mockModel, mockHttpServletRequest);
    } catch (Exception exception) {
      expectedException = exception;
    }

    // Verify
    Mockito.verify(mockProjectDetailService, Mockito.times(1)).getProjectDetail(projectDetailCommandDTOCaptor.capture());
    Mockito.verify(mockHttpSession, Mockito.times(1)).setAttribute(ModelConstants.MODEL_IS_SHARED_PROJECT, false);
    Mockito.verify(mockModel, Mockito.times(1)).addAttribute(SessionConstants.SESSION_LOGGED_IN_USER, loggedInUserRepresentation);
    Mockito.verify(mockModel, Mockito.times(1)).addAttribute(ModelConstants.MODEL_PROJECT_DETAIL, projectDetailRepresentation);
    Mockito.verify(mockModel, Mockito.times(1)).addAttribute(Mockito.eq(ModelConstants.MODEL_ADD_PROJECT_DESCRIPTION), Mockito.any(ProjectDetailRepresentation.class));
    Mockito.verify(mockModel, Mockito.times(1)).addAttribute(Mockito.eq(ModelConstants.MODEL_ADD_PROJECT_INSTRUCTION), Mockito.any(ProjectDetailInstructionRepresentation.class));

    // Assertions
    assert expectedException == null;
    assert ((Long) projectDetailCommandDTOCaptor.getValue().get(ContextConstants.CONTEXT_PROJECT_ID)) == 1L;
    assert result.equals(ViewConstants.VIEW_PROJECT);
  }

  @Test
  public void testGetProjectDetailException() {
    // Initialize
    Model mockModel = Mockito.mock(Model.class);
    HttpServletRequest mockHttpServletRequest = Mockito.mock(HttpServletRequest.class);
    HttpSession mockHttpSession = Mockito.mock(HttpSession.class);

    ArgumentCaptor<CommandDTO> projectDetailCommandDTOCaptor = ArgumentCaptor.forClass(CommandDTO.class);
    ArgumentCaptor<ErrorsRepresentation> errorsRepresentationCaptor = ArgumentCaptor.forClass(ErrorsRepresentation.class);

    LoggedInUserRepresentation loggedInUserRepresentation = LoggedInUserRepresentation.builder().userName("username").build();
    ProjectDetailRepresentation projectDetailRepresentation = ProjectDetailRepresentation.builder().id(1L).build();

    ProjectDetailService mockProjectDetailService = Mockito.mock(ProjectDetailService.class);

    ProjectDetailController projectDetailController = new ProjectDetailController();
    projectDetailController.projectDetailService = mockProjectDetailService;

    Map<String, String> fieldMessages = new HashMap<>();
    fieldMessages.put("key1", "value1");
    GetProjectDetailFailedException getProjectDetailFailedException = new GetProjectDetailFailedException();
    getProjectDetailFailedException.setMessages(Collections.singletonList("message"));
    getProjectDetailFailedException.setFieldMessages(fieldMessages);

    // Define Mocks
    Mockito.when(mockHttpServletRequest.getSession()).thenReturn(mockHttpSession);
    Mockito.when(mockHttpSession.getAttribute(SessionConstants.SESSION_LOGGED_IN_USER)).thenReturn(loggedInUserRepresentation);
    Mockito.when(mockModel.addAttribute(SessionConstants.SESSION_LOGGED_IN_USER, loggedInUserRepresentation)).thenReturn(mockModel);
    Mockito.when(mockModel.addAttribute(Mockito.eq(ModelConstants.MODEL_PROJECT_DETAIL), Mockito.any(ProjectDetailRepresentation.class))).thenReturn(mockModel);
    Mockito.when(mockModel.addAttribute(Mockito.eq(ModelConstants.MODEL_ADD_PROJECT_DESCRIPTION), Mockito.any(ProjectDetailRepresentation.class))).thenReturn(mockModel);
    Mockito.when(mockModel.addAttribute(Mockito.eq(ModelConstants.MODEL_ADD_PROJECT_INSTRUCTION), Mockito.any(ProjectDetailInstructionRepresentation.class))).thenReturn(mockModel);
    Mockito.when(mockModel.addAttribute(ModelConstants.MODEL_PROJECT_DETAIL, projectDetailRepresentation)).thenReturn(mockModel);
    Mockito.when(mockModel.addAttribute(Mockito.eq(ModelConstants.MODEL_GET_PROJECT_DETAIL_ERRORS), errorsRepresentationCaptor.capture())).thenReturn(mockModel);
    Mockito.when(mockProjectDetailService.getProjectDetail(Mockito.any())).thenThrow(getProjectDetailFailedException);
    Mockito.doNothing().when(mockHttpSession).setAttribute(ModelConstants.MODEL_IS_SHARED_PROJECT, false);

    // Execute
    Exception expectedException = null;
    String result = null;
    try {
      result = projectDetailController.getProjectDetail(1L, mockModel, mockHttpServletRequest);
    } catch (Exception exception) {
      expectedException = exception;
    }

    // Verify
    Mockito.verify(mockProjectDetailService, Mockito.times(1)).getProjectDetail(projectDetailCommandDTOCaptor.capture());
    Mockito.verify(mockHttpSession, Mockito.times(0)).setAttribute(ModelConstants.MODEL_IS_SHARED_PROJECT, false);
    Mockito.verify(mockModel, Mockito.times(1)).addAttribute(SessionConstants.SESSION_LOGGED_IN_USER, loggedInUserRepresentation);
    Mockito.verify(mockModel, Mockito.times(1)).addAttribute(Mockito.eq(ModelConstants.MODEL_PROJECT_DETAIL), Mockito.any(ProjectDetailRepresentation.class));
    Mockito.verify(mockModel, Mockito.times(0)).addAttribute(Mockito.eq(ModelConstants.MODEL_ADD_PROJECT_DESCRIPTION), Mockito.any(ProjectDetailRepresentation.class));
    Mockito.verify(mockModel, Mockito.times(0)).addAttribute(Mockito.eq(ModelConstants.MODEL_ADD_PROJECT_INSTRUCTION), Mockito.any(ProjectDetailInstructionRepresentation.class));

    // Assertions
    assert expectedException == null;
    assert ((Long) projectDetailCommandDTOCaptor.getValue().get(ContextConstants.CONTEXT_PROJECT_ID)) == 1L;
    assert result.equals(ViewConstants.VIEW_PROJECT);
    assert errorsRepresentationCaptor.getValue().getErrorMessages().get(0).equals("message");
    assert errorsRepresentationCaptor.getValue().getFieldErrorMessages().get("key1").equals("value1");
  }

  @Test
  public void testSaveProjectDescription() {
    // Initialize
    Model mockModel = Mockito.mock(Model.class);
    HttpServletRequest mockHttpServletRequest = Mockito.mock(HttpServletRequest.class);
    HttpSession mockHttpSession = Mockito.mock(HttpSession.class);

    ArgumentCaptor<CommandDTO> projectDetailCommandDTOCaptor = ArgumentCaptor.forClass(CommandDTO.class);
    ArgumentCaptor<CommandDTO> saveProjectDetailCommandDTOCaptor = ArgumentCaptor.forClass(CommandDTO.class);

    LoggedInUserRepresentation loggedInUserRepresentation = LoggedInUserRepresentation.builder().userName("username").build();
    ProjectDetailRepresentation projectDetailRepresentation = ProjectDetailRepresentation.builder().id(1L).build();

    ProjectDetailService mockProjectDetailService = Mockito.mock(ProjectDetailService.class);

    ProjectDetailController projectDetailController = new ProjectDetailController();
    projectDetailController.projectDetailService = mockProjectDetailService;
    CommandDTO responseCommandDTO = CommandDTO
        .builder()
        .add(ContextConstants.CONTEXT_PROJECT_DETAIL, projectDetailRepresentation)
        .build();

    // Define Mocks
    Mockito.when(mockHttpServletRequest.getSession()).thenReturn(mockHttpSession);
    Mockito.when(mockHttpSession.getAttribute(SessionConstants.SESSION_LOGGED_IN_USER)).thenReturn(loggedInUserRepresentation);
    Mockito.when(mockModel.addAttribute(SessionConstants.SESSION_LOGGED_IN_USER, loggedInUserRepresentation)).thenReturn(mockModel);
    Mockito.when(mockModel.addAttribute(ModelConstants.MODEL_PROJECT_DETAIL, projectDetailRepresentation)).thenReturn(mockModel);
    Mockito.when(mockModel.addAttribute(Mockito.eq(ModelConstants.MODEL_ADD_PROJECT_DESCRIPTION), Mockito.any(ProjectDetailRepresentation.class))).thenReturn(mockModel);
    Mockito.when(mockModel.addAttribute(Mockito.eq(ModelConstants.MODEL_ADD_PROJECT_INSTRUCTION), Mockito.any(ProjectDetailInstructionRepresentation.class))).thenReturn(mockModel);
    Mockito.when(mockModel.addAttribute(ModelConstants.MODEL_PROJECT_DETAIL, projectDetailRepresentation)).thenReturn(mockModel);
    Mockito.when(mockProjectDetailService.getProjectDetail(Mockito.any())).thenReturn(responseCommandDTO);
    Mockito.when(mockProjectDetailService.saveProjectDescription(Mockito.any())).thenReturn(responseCommandDTO);
    Mockito.doNothing().when(mockHttpSession).setAttribute(ModelConstants.MODEL_IS_SHARED_PROJECT, false);

    // Execute
    Exception expectedException = null;
    String result = null;
    try {
      result = projectDetailController.saveProjectDescription(1L, projectDetailRepresentation, mockModel, mockHttpServletRequest);
    } catch (Exception exception) {
      expectedException = exception;
    }

    // Verify
    Mockito.verify(mockProjectDetailService, Mockito.times(1)).getProjectDetail(projectDetailCommandDTOCaptor.capture());
    Mockito.verify(mockProjectDetailService, Mockito.times(1)).saveProjectDescription(saveProjectDetailCommandDTOCaptor.capture());
    Mockito.verify(mockHttpSession, Mockito.times(1)).setAttribute(ModelConstants.MODEL_IS_SHARED_PROJECT, false);
    Mockito.verify(mockModel, Mockito.times(1)).addAttribute(SessionConstants.SESSION_LOGGED_IN_USER, loggedInUserRepresentation);
    Mockito.verify(mockModel, Mockito.times(1)).addAttribute(ModelConstants.MODEL_PROJECT_DETAIL, projectDetailRepresentation);
    Mockito.verify(mockModel, Mockito.times(1)).addAttribute(Mockito.eq(ModelConstants.MODEL_ADD_PROJECT_DESCRIPTION), Mockito.any(ProjectDetailRepresentation.class));
    Mockito.verify(mockModel, Mockito.times(1)).addAttribute(Mockito.eq(ModelConstants.MODEL_ADD_PROJECT_INSTRUCTION), Mockito.any(ProjectDetailInstructionRepresentation.class));

    // Assertions
    assert expectedException == null;
    assert ((Long) projectDetailCommandDTOCaptor.getValue().get(ContextConstants.CONTEXT_PROJECT_ID)) == 1L;
    assert ((ProjectDetailRepresentation) saveProjectDetailCommandDTOCaptor.getValue().get(ContextConstants.CONTEXT_PROJECT_DETAIL)).getId() == 1L;
    assert result.equals(ViewConstants.VIEW_PROJECT);
  }

  @Test
  public void testSaveProjectDescriptionException() {
    // Initialize
    Model mockModel = Mockito.mock(Model.class);
    HttpServletRequest mockHttpServletRequest = Mockito.mock(HttpServletRequest.class);
    HttpSession mockHttpSession = Mockito.mock(HttpSession.class);

    ArgumentCaptor<CommandDTO> projectDetailCommandDTOCaptor = ArgumentCaptor.forClass(CommandDTO.class);
    ArgumentCaptor<CommandDTO> saveProjectDetailCommandDTOCaptor = ArgumentCaptor.forClass(CommandDTO.class);
    ArgumentCaptor<ErrorsRepresentation> errorsRepresentationCaptor = ArgumentCaptor.forClass(ErrorsRepresentation.class);

    LoggedInUserRepresentation loggedInUserRepresentation = LoggedInUserRepresentation.builder().userName("username").build();
    ProjectDetailRepresentation projectDetailRepresentation = ProjectDetailRepresentation.builder().id(1L).build();

    ProjectDetailService mockProjectDetailService = Mockito.mock(ProjectDetailService.class);

    Map<String, String> fieldMessages = new HashMap<>();
    fieldMessages.put("key1", "value1");
    SaveProjectDescriptionFailedException saveProjectDescriptionFailedException = new SaveProjectDescriptionFailedException();
    saveProjectDescriptionFailedException.setMessages(Collections.singletonList("message"));
    saveProjectDescriptionFailedException.setFieldMessages(fieldMessages);

    ProjectDetailController projectDetailController = new ProjectDetailController();
    projectDetailController.projectDetailService = mockProjectDetailService;
    CommandDTO responseCommandDTO = CommandDTO
        .builder()
        .add(ContextConstants.CONTEXT_PROJECT_DETAIL, projectDetailRepresentation)
        .build();

    // Define Mocks
    Mockito.when(mockHttpServletRequest.getSession()).thenReturn(mockHttpSession);
    Mockito.when(mockHttpSession.getAttribute(SessionConstants.SESSION_LOGGED_IN_USER)).thenReturn(loggedInUserRepresentation);
    Mockito.when(mockModel.addAttribute(SessionConstants.SESSION_LOGGED_IN_USER, loggedInUserRepresentation)).thenReturn(mockModel);
    Mockito.when(mockModel.addAttribute(ModelConstants.MODEL_PROJECT_DETAIL, projectDetailRepresentation)).thenReturn(mockModel);
    Mockito.when(mockModel.addAttribute(Mockito.eq(ModelConstants.MODEL_ADD_PROJECT_DESCRIPTION), Mockito.any(ProjectDetailRepresentation.class))).thenReturn(mockModel);
    Mockito.when(mockModel.addAttribute(Mockito.eq(ModelConstants.MODEL_ADD_PROJECT_DESCRIPTION_ERRORS), errorsRepresentationCaptor.capture())).thenReturn(mockModel);
    Mockito.when(mockModel.addAttribute(Mockito.eq(ModelConstants.MODEL_ADD_PROJECT_INSTRUCTION), Mockito.any(ProjectDetailInstructionRepresentation.class))).thenReturn(mockModel);
    Mockito.when(mockModel.addAttribute(ModelConstants.MODEL_PROJECT_DETAIL, projectDetailRepresentation)).thenReturn(mockModel);
    Mockito.when(mockProjectDetailService.getProjectDetail(Mockito.any())).thenReturn(responseCommandDTO);
    Mockito.when(mockProjectDetailService.saveProjectDescription(Mockito.any())).thenThrow(saveProjectDescriptionFailedException);
    Mockito.doNothing().when(mockHttpSession).setAttribute(ModelConstants.MODEL_IS_SHARED_PROJECT, false);

    // Execute
    Exception expectedException = null;
    String result = null;
    try {
      result = projectDetailController.saveProjectDescription(1L, projectDetailRepresentation, mockModel, mockHttpServletRequest);
    } catch (Exception exception) {
      expectedException = exception;
    }

    // Verify
    Mockito.verify(mockProjectDetailService, Mockito.times(1)).getProjectDetail(projectDetailCommandDTOCaptor.capture());
    Mockito.verify(mockProjectDetailService, Mockito.times(1)).saveProjectDescription(saveProjectDetailCommandDTOCaptor.capture());
    Mockito.verify(mockHttpSession, Mockito.times(1)).setAttribute(ModelConstants.MODEL_IS_SHARED_PROJECT, false);
    Mockito.verify(mockModel, Mockito.times(1)).addAttribute(SessionConstants.SESSION_LOGGED_IN_USER, loggedInUserRepresentation);
    Mockito.verify(mockModel, Mockito.times(1)).addAttribute(ModelConstants.MODEL_PROJECT_DETAIL, projectDetailRepresentation);
    Mockito.verify(mockModel, Mockito.times(2)).addAttribute(Mockito.eq(ModelConstants.MODEL_ADD_PROJECT_DESCRIPTION), Mockito.any(ProjectDetailRepresentation.class));
    Mockito.verify(mockModel, Mockito.times(1)).addAttribute(Mockito.eq(ModelConstants.MODEL_ADD_PROJECT_DESCRIPTION_ERRORS), Mockito.any(ErrorsRepresentation.class));
    Mockito.verify(mockModel, Mockito.times(1)).addAttribute(Mockito.eq(ModelConstants.MODEL_ADD_PROJECT_INSTRUCTION), Mockito.any(ProjectDetailInstructionRepresentation.class));

    // Assertions
    assert expectedException == null;
    assert ((Long) projectDetailCommandDTOCaptor.getValue().get(ContextConstants.CONTEXT_PROJECT_ID)) == 1L;
    assert ((ProjectDetailRepresentation) saveProjectDetailCommandDTOCaptor.getValue().get(ContextConstants.CONTEXT_PROJECT_DETAIL)).getId() == 1L;
    assert result.equals(ViewConstants.VIEW_PROJECT);
    assert errorsRepresentationCaptor.getValue().getErrorMessages().get(0).equals("message");
    assert errorsRepresentationCaptor.getValue().getFieldErrorMessages().get("key1").equals("value1");
  }

  @Test
  public void testEditProjectDescription() {
    // Initialize
    Model mockModel = Mockito.mock(Model.class);
    HttpServletRequest mockHttpServletRequest = Mockito.mock(HttpServletRequest.class);
    HttpSession mockHttpSession = Mockito.mock(HttpSession.class);

    ArgumentCaptor<CommandDTO> projectDetailCommandDTOCaptor = ArgumentCaptor.forClass(CommandDTO.class);

    LoggedInUserRepresentation loggedInUserRepresentation = LoggedInUserRepresentation.builder().userName("username").build();
    ProjectDetailRepresentation projectDetailRepresentation = ProjectDetailRepresentation.builder().id(1L).build();

    ProjectDetailService mockProjectDetailService = Mockito.mock(ProjectDetailService.class);

    ProjectDetailController projectDetailController = new ProjectDetailController();
    projectDetailController.projectDetailService = mockProjectDetailService;
    CommandDTO responseCommandDTO = CommandDTO
        .builder()
        .add(ContextConstants.CONTEXT_PROJECT_DETAIL, projectDetailRepresentation)
        .build();

    // Define Mocks
    Mockito.when(mockHttpServletRequest.getSession()).thenReturn(mockHttpSession);
    Mockito.when(mockHttpSession.getAttribute(SessionConstants.SESSION_LOGGED_IN_USER)).thenReturn(loggedInUserRepresentation);
    Mockito.when(mockModel.addAttribute(SessionConstants.SESSION_LOGGED_IN_USER, loggedInUserRepresentation)).thenReturn(mockModel);
    Mockito.when(mockModel.addAttribute(ModelConstants.MODEL_PROJECT_DETAIL, projectDetailRepresentation)).thenReturn(mockModel);
    Mockito.when(mockModel.addAttribute(ModelConstants.MODEL_ADD_PROJECT_DESCRIPTION, projectDetailRepresentation)).thenReturn(mockModel);
    Mockito.when(mockModel.addAttribute(ModelConstants.MODEL_EDIT_PROJECT_DESCRIPTION, projectDetailRepresentation)).thenReturn(mockModel);
    Mockito.when(mockModel.getAttribute(ModelConstants.MODEL_PROJECT_DETAIL)).thenReturn(projectDetailRepresentation);
    Mockito.when(mockModel.addAttribute(Mockito.eq(ModelConstants.MODEL_ADD_PROJECT_DESCRIPTION), Mockito.any(ProjectDetailRepresentation.class))).thenReturn(mockModel);
    Mockito.when(mockModel.addAttribute(Mockito.eq(ModelConstants.MODEL_ADD_PROJECT_INSTRUCTION), Mockito.any(ProjectDetailInstructionRepresentation.class))).thenReturn(mockModel);
    Mockito.when(mockModel.addAttribute(ModelConstants.MODEL_PROJECT_DETAIL, projectDetailRepresentation)).thenReturn(mockModel);
    Mockito.when(mockProjectDetailService.getProjectDetail(Mockito.any())).thenReturn(responseCommandDTO);
    Mockito.when(mockProjectDetailService.saveProjectDescription(Mockito.any())).thenReturn(responseCommandDTO);
    Mockito.doNothing().when(mockHttpSession).setAttribute(ModelConstants.MODEL_IS_SHARED_PROJECT, false);

    // Execute
    Exception expectedException = null;
    String result = null;
    try {
      result = projectDetailController.editProjectDescription(1L, mockModel, mockHttpServletRequest);
    } catch (Exception exception) {
      expectedException = exception;
    }

    // Verify
    Mockito.verify(mockProjectDetailService, Mockito.times(1)).getProjectDetail(projectDetailCommandDTOCaptor.capture());
    Mockito.verify(mockHttpSession, Mockito.times(1)).setAttribute(ModelConstants.MODEL_IS_SHARED_PROJECT, false);
    Mockito.verify(mockModel, Mockito.times(1)).addAttribute(SessionConstants.SESSION_LOGGED_IN_USER, loggedInUserRepresentation);
    Mockito.verify(mockModel, Mockito.times(1)).addAttribute(ModelConstants.MODEL_PROJECT_DETAIL, projectDetailRepresentation);
    Mockito.verify(mockModel, Mockito.times(1)).addAttribute(ModelConstants.MODEL_EDIT_PROJECT_DESCRIPTION, projectDetailRepresentation);
    Mockito.verify(mockModel, Mockito.times(2)).addAttribute(Mockito.eq(ModelConstants.MODEL_ADD_PROJECT_DESCRIPTION), Mockito.any(ProjectDetailRepresentation.class));
    Mockito.verify(mockModel, Mockito.times(1)).addAttribute(Mockito.eq(ModelConstants.MODEL_ADD_PROJECT_INSTRUCTION), Mockito.any(ProjectDetailInstructionRepresentation.class));

    // Assertions
    assert expectedException == null;
    assert ((Long) projectDetailCommandDTOCaptor.getValue().get(ContextConstants.CONTEXT_PROJECT_ID)) == 1L;
    assert result.equals(ViewConstants.VIEW_PROJECT);
  }

  @Test
  public void testAddProjectInstruction() {
    // Initialize
    Model mockModel = Mockito.mock(Model.class);
    HttpServletRequest mockHttpServletRequest = Mockito.mock(HttpServletRequest.class);
    HttpSession mockHttpSession = Mockito.mock(HttpSession.class);

    ArgumentCaptor<CommandDTO> projectDetailCommandDTOCaptor = ArgumentCaptor.forClass(CommandDTO.class);
    ArgumentCaptor<CommandDTO> saveProjectInstructionCommandDTOCaptor = ArgumentCaptor.forClass(CommandDTO.class);

    LoggedInUserRepresentation loggedInUserRepresentation = LoggedInUserRepresentation.builder().userName("username").build();
    ProjectDetailRepresentation projectDetailRepresentation = ProjectDetailRepresentation.builder().id(1L).build();
    ProjectDetailInstructionRepresentation projectDetailInstructionRepresentation = ProjectDetailInstructionRepresentation.builder().id(1L).build();

    ProjectDetailService mockProjectDetailService = Mockito.mock(ProjectDetailService.class);

    ProjectDetailController projectDetailController = new ProjectDetailController();
    projectDetailController.projectDetailService = mockProjectDetailService;
    CommandDTO responseCommandDTO = CommandDTO
        .builder()
        .add(ContextConstants.CONTEXT_PROJECT_DETAIL, projectDetailRepresentation)
        .build();

    // Define Mocks
    Mockito.when(mockHttpServletRequest.getSession()).thenReturn(mockHttpSession);
    Mockito.when(mockHttpSession.getAttribute(SessionConstants.SESSION_LOGGED_IN_USER)).thenReturn(loggedInUserRepresentation);
    Mockito.when(mockModel.addAttribute(SessionConstants.SESSION_LOGGED_IN_USER, loggedInUserRepresentation)).thenReturn(mockModel);
    Mockito.when(mockModel.addAttribute(ModelConstants.MODEL_PROJECT_DETAIL, projectDetailRepresentation)).thenReturn(mockModel);
    Mockito.when(mockModel.addAttribute(Mockito.eq(ModelConstants.MODEL_ADD_PROJECT_DESCRIPTION), Mockito.any(ProjectDetailRepresentation.class))).thenReturn(mockModel);
    Mockito.when(mockModel.addAttribute(Mockito.eq(ModelConstants.MODEL_ADD_PROJECT_INSTRUCTION), Mockito.any(ProjectDetailInstructionRepresentation.class))).thenReturn(mockModel);
    Mockito.when(mockModel.addAttribute(ModelConstants.MODEL_PROJECT_DETAIL, projectDetailRepresentation)).thenReturn(mockModel);
    Mockito.when(mockProjectDetailService.getProjectDetail(Mockito.any())).thenReturn(responseCommandDTO);
    Mockito.when(mockProjectDetailService.saveProjectInstruction(Mockito.any())).thenReturn(responseCommandDTO);
    Mockito.doNothing().when(mockHttpSession).setAttribute(ModelConstants.MODEL_IS_SHARED_PROJECT, false);

    // Execute
    Exception expectedException = null;
    String result = null;
    try {
      result = projectDetailController.addProjectInstruction(projectDetailInstructionRepresentation, mockModel, mockHttpServletRequest, 1L);
    } catch (Exception exception) {
      expectedException = exception;
    }

    // Verify
    Mockito.verify(mockProjectDetailService, Mockito.times(1)).getProjectDetail(projectDetailCommandDTOCaptor.capture());
    Mockito.verify(mockProjectDetailService, Mockito.times(1)).saveProjectInstruction(saveProjectInstructionCommandDTOCaptor.capture());
    Mockito.verify(mockHttpSession, Mockito.times(1)).setAttribute(ModelConstants.MODEL_IS_SHARED_PROJECT, false);
    Mockito.verify(mockModel, Mockito.times(1)).addAttribute(SessionConstants.SESSION_LOGGED_IN_USER, loggedInUserRepresentation);
    Mockito.verify(mockModel, Mockito.times(1)).addAttribute(ModelConstants.MODEL_PROJECT_DETAIL, projectDetailRepresentation);
    Mockito.verify(mockModel, Mockito.times(1)).addAttribute(Mockito.eq(ModelConstants.MODEL_ADD_PROJECT_DESCRIPTION), Mockito.any(ProjectDetailRepresentation.class));
    Mockito.verify(mockModel, Mockito.times(1)).addAttribute(Mockito.eq(ModelConstants.MODEL_ADD_PROJECT_INSTRUCTION), Mockito.any(ProjectDetailInstructionRepresentation.class));

    // Assertions
    assert expectedException == null;
    assert ((Long) projectDetailCommandDTOCaptor.getValue().get(ContextConstants.CONTEXT_PROJECT_ID)) == 1L;
    assert ((Long) saveProjectInstructionCommandDTOCaptor.getValue().get(ContextConstants.CONTEXT_PROJECT_ID)) == 1L;
    assert saveProjectInstructionCommandDTOCaptor.getValue().get(ContextConstants.CONTEXT_PROJECT_INSTRUCTION) == projectDetailInstructionRepresentation;
    assert result.equals(ViewConstants.VIEW_PROJECT);
  }

  @Test
  public void testAddProjectInstructionException() {
    // Initialize
    Model mockModel = Mockito.mock(Model.class);
    HttpServletRequest mockHttpServletRequest = Mockito.mock(HttpServletRequest.class);
    HttpSession mockHttpSession = Mockito.mock(HttpSession.class);

    ArgumentCaptor<CommandDTO> projectDetailCommandDTOCaptor = ArgumentCaptor.forClass(CommandDTO.class);
    ArgumentCaptor<ErrorsRepresentation> errorsRepresentationCaptor = ArgumentCaptor.forClass(ErrorsRepresentation.class);

    LoggedInUserRepresentation loggedInUserRepresentation = LoggedInUserRepresentation.builder().userName("username").build();
    ProjectDetailRepresentation projectDetailRepresentation = ProjectDetailRepresentation.builder().id(1L).build();
    ProjectDetailInstructionRepresentation projectDetailInstructionRepresentation = ProjectDetailInstructionRepresentation.builder().id(1L).build();

    ProjectDetailService mockProjectDetailService = Mockito.mock(ProjectDetailService.class);

    Map<String, String> fieldMessages = new HashMap<>();
    fieldMessages.put("key1", "value1");
    SaveProjectInstructionFailedException saveProjectInstructionFailedException = new SaveProjectInstructionFailedException();
    saveProjectInstructionFailedException.setMessages(Collections.singletonList("message"));
    saveProjectInstructionFailedException.setFieldMessages(fieldMessages);

    ProjectDetailController projectDetailController = new ProjectDetailController();
    projectDetailController.projectDetailService = mockProjectDetailService;
    CommandDTO responseCommandDTO = CommandDTO
        .builder()
        .add(ContextConstants.CONTEXT_PROJECT_DETAIL, projectDetailRepresentation)
        .build();

    // Define Mocks
    Mockito.when(mockHttpServletRequest.getSession()).thenReturn(mockHttpSession);
    Mockito.when(mockHttpSession.getAttribute(SessionConstants.SESSION_LOGGED_IN_USER)).thenReturn(loggedInUserRepresentation);
    Mockito.when(mockModel.addAttribute(SessionConstants.SESSION_LOGGED_IN_USER, loggedInUserRepresentation)).thenReturn(mockModel);
    Mockito.when(mockModel.addAttribute(ModelConstants.MODEL_PROJECT_DETAIL, projectDetailRepresentation)).thenReturn(mockModel);
    Mockito.when(mockModel.addAttribute(Mockito.eq(ModelConstants.MODEL_ADD_PROJECT_DESCRIPTION), Mockito.any(ProjectDetailRepresentation.class))).thenReturn(mockModel);
    Mockito.when(mockModel.addAttribute(Mockito.eq(ModelConstants.MODEL_ADD_PROJECT_INSTRUCTION_ERRORS), errorsRepresentationCaptor.capture())).thenReturn(mockModel);
    Mockito.when(mockModel.addAttribute(Mockito.eq(ModelConstants.MODEL_ADD_PROJECT_INSTRUCTION), Mockito.any(ProjectDetailInstructionRepresentation.class))).thenReturn(mockModel);
    Mockito.when(mockModel.addAttribute(ModelConstants.MODEL_PROJECT_DETAIL, projectDetailRepresentation)).thenReturn(mockModel);
    Mockito.when(mockProjectDetailService.getProjectDetail(Mockito.any())).thenReturn(responseCommandDTO);
    Mockito.when(mockProjectDetailService.saveProjectInstruction(Mockito.any())).thenThrow(saveProjectInstructionFailedException);
    Mockito.doNothing().when(mockHttpSession).setAttribute(ModelConstants.MODEL_IS_SHARED_PROJECT, false);

    // Execute
    Exception expectedException = null;
    String result = null;
    try {
      result = projectDetailController.addProjectInstruction(projectDetailInstructionRepresentation, mockModel, mockHttpServletRequest, 1L);
    } catch (Exception exception) {
      expectedException = exception;
    }

    // Verify
    Mockito.verify(mockProjectDetailService, Mockito.times(1)).getProjectDetail(projectDetailCommandDTOCaptor.capture());
    Mockito.verify(mockHttpSession, Mockito.times(1)).setAttribute(ModelConstants.MODEL_IS_SHARED_PROJECT, false);
    Mockito.verify(mockModel, Mockito.times(1)).addAttribute(SessionConstants.SESSION_LOGGED_IN_USER, loggedInUserRepresentation);
    Mockito.verify(mockModel, Mockito.times(1)).addAttribute(ModelConstants.MODEL_PROJECT_DETAIL, projectDetailRepresentation);
    Mockito.verify(mockModel, Mockito.times(1)).addAttribute(Mockito.eq(ModelConstants.MODEL_ADD_PROJECT_DESCRIPTION), Mockito.any(ProjectDetailRepresentation.class));
    Mockito.verify(mockModel, Mockito.times(1)).addAttribute(Mockito.eq(ModelConstants.MODEL_ADD_PROJECT_INSTRUCTION_ERRORS), Mockito.any(ErrorsRepresentation.class));
    Mockito.verify(mockModel, Mockito.times(2)).addAttribute(Mockito.eq(ModelConstants.MODEL_ADD_PROJECT_INSTRUCTION), Mockito.any(ProjectDetailInstructionRepresentation.class));

    // Assertions
    assert expectedException == null;
    assert ((Long) projectDetailCommandDTOCaptor.getValue().get(ContextConstants.CONTEXT_PROJECT_ID)) == 1L;
    assert result.equals(ViewConstants.VIEW_PROJECT);
    assert errorsRepresentationCaptor.getValue().getErrorMessages().get(0).equals("message");
    assert errorsRepresentationCaptor.getValue().getFieldErrorMessages().get("key1").equals("value1");
  }

  @Test
  public void testEditProjectInstruction() {
    // Initialize
    Model mockModel = Mockito.mock(Model.class);
    HttpServletRequest mockHttpServletRequest = Mockito.mock(HttpServletRequest.class);
    HttpSession mockHttpSession = Mockito.mock(HttpSession.class);

    ArgumentCaptor<CommandDTO> projectDetailCommandDTOCaptor = ArgumentCaptor.forClass(CommandDTO.class);
    ArgumentCaptor<CommandDTO> updateProjectInstructionCommandDTOCaptor = ArgumentCaptor.forClass(CommandDTO.class);

    LoggedInUserRepresentation loggedInUserRepresentation = LoggedInUserRepresentation.builder().userName("username").build();
    ProjectDetailRepresentation projectDetailRepresentation = ProjectDetailRepresentation.builder().id(1L).build();
    ProjectDetailInstructionRepresentation projectDetailInstructionRepresentation = ProjectDetailInstructionRepresentation.builder().id(1L).build();

    ProjectDetailService mockProjectDetailService = Mockito.mock(ProjectDetailService.class);

    ProjectDetailController projectDetailController = new ProjectDetailController();
    projectDetailController.projectDetailService = mockProjectDetailService;
    CommandDTO responseCommandDTO = CommandDTO
        .builder()
        .add(ContextConstants.CONTEXT_PROJECT_DETAIL, projectDetailRepresentation)
        .build();

    // Define Mocks
    Mockito.when(mockHttpServletRequest.getSession()).thenReturn(mockHttpSession);
    Mockito.when(mockHttpSession.getAttribute(SessionConstants.SESSION_LOGGED_IN_USER)).thenReturn(loggedInUserRepresentation);
    Mockito.when(mockModel.addAttribute(SessionConstants.SESSION_LOGGED_IN_USER, loggedInUserRepresentation)).thenReturn(mockModel);
    Mockito.when(mockModel.addAttribute(ModelConstants.MODEL_PROJECT_DETAIL, projectDetailRepresentation)).thenReturn(mockModel);
    Mockito.when(mockModel.addAttribute(Mockito.eq(ModelConstants.MODEL_ADD_PROJECT_DESCRIPTION), Mockito.any(ProjectDetailRepresentation.class))).thenReturn(mockModel);
    Mockito.when(mockModel.addAttribute(Mockito.eq(ModelConstants.MODEL_ADD_PROJECT_INSTRUCTION), Mockito.any(ProjectDetailInstructionRepresentation.class))).thenReturn(mockModel);
    Mockito.when(mockModel.addAttribute(ModelConstants.MODEL_PROJECT_DETAIL, projectDetailRepresentation)).thenReturn(mockModel);
    Mockito.when(mockProjectDetailService.getProjectDetail(Mockito.any())).thenReturn(responseCommandDTO);
    Mockito.when(mockProjectDetailService.updateProjectInstruction(Mockito.any())).thenReturn(responseCommandDTO);
    Mockito.doNothing().when(mockHttpSession).setAttribute(ModelConstants.MODEL_IS_SHARED_PROJECT, false);

    // Execute
    Exception expectedException = null;
    String result = null;
    try {
      result = projectDetailController.editProjectInstruction(projectDetailInstructionRepresentation, mockModel, mockHttpServletRequest, 1L);
    } catch (Exception exception) {
      expectedException = exception;
    }

    // Verify
    Mockito.verify(mockProjectDetailService, Mockito.times(1)).getProjectDetail(projectDetailCommandDTOCaptor.capture());
    Mockito.verify(mockProjectDetailService, Mockito.times(1)).updateProjectInstruction(updateProjectInstructionCommandDTOCaptor.capture());
    Mockito.verify(mockHttpSession, Mockito.times(1)).setAttribute(ModelConstants.MODEL_IS_SHARED_PROJECT, false);
    Mockito.verify(mockModel, Mockito.times(1)).addAttribute(SessionConstants.SESSION_LOGGED_IN_USER, loggedInUserRepresentation);
    Mockito.verify(mockModel, Mockito.times(1)).addAttribute(ModelConstants.MODEL_PROJECT_DETAIL, projectDetailRepresentation);
    Mockito.verify(mockModel, Mockito.times(1)).addAttribute(Mockito.eq(ModelConstants.MODEL_ADD_PROJECT_DESCRIPTION), Mockito.any(ProjectDetailRepresentation.class));
    Mockito.verify(mockModel, Mockito.times(1)).addAttribute(Mockito.eq(ModelConstants.MODEL_ADD_PROJECT_INSTRUCTION), Mockito.any(ProjectDetailInstructionRepresentation.class));

    // Assertions
    assert expectedException == null;
    assert ((Long) projectDetailCommandDTOCaptor.getValue().get(ContextConstants.CONTEXT_PROJECT_ID)) == 1L;
    assert ((Long) updateProjectInstructionCommandDTOCaptor.getValue().get(ContextConstants.CONTEXT_PROJECT_ID)) == 1L;
    assert updateProjectInstructionCommandDTOCaptor.getValue().get(ContextConstants.CONTEXT_PROJECT_INSTRUCTION) == projectDetailInstructionRepresentation;
    assert result.equals(ViewConstants.VIEW_PROJECT);
  }

  @Test
  public void testEditProjectInstructionException() {
    // Initialize
    Model mockModel = Mockito.mock(Model.class);
    HttpServletRequest mockHttpServletRequest = Mockito.mock(HttpServletRequest.class);
    HttpSession mockHttpSession = Mockito.mock(HttpSession.class);

    ArgumentCaptor<CommandDTO> projectDetailCommandDTOCaptor = ArgumentCaptor.forClass(CommandDTO.class);
    ArgumentCaptor<ErrorsRepresentation> errorsRepresentationCaptor = ArgumentCaptor.forClass(ErrorsRepresentation.class);

    LoggedInUserRepresentation loggedInUserRepresentation = LoggedInUserRepresentation.builder().userName("username").build();
    ProjectDetailRepresentation projectDetailRepresentation = ProjectDetailRepresentation.builder().id(1L).build();
    ProjectDetailInstructionRepresentation projectDetailInstructionRepresentation = ProjectDetailInstructionRepresentation.builder().id(1L).build();

    ProjectDetailService mockProjectDetailService = Mockito.mock(ProjectDetailService.class);

    Map<String, String> fieldMessages = new HashMap<>();
    fieldMessages.put("key1", "value1");
    UpdateProjectInstructionFailedException updateProjectInstructionFailedException = new UpdateProjectInstructionFailedException();
    updateProjectInstructionFailedException.setMessages(Collections.singletonList("message"));
    updateProjectInstructionFailedException.setFieldMessages(fieldMessages);

    ProjectDetailController projectDetailController = new ProjectDetailController();
    projectDetailController.projectDetailService = mockProjectDetailService;
    CommandDTO responseCommandDTO = CommandDTO
        .builder()
        .add(ContextConstants.CONTEXT_PROJECT_DETAIL, projectDetailRepresentation)
        .build();

    // Define Mocks
    Mockito.when(mockHttpServletRequest.getSession()).thenReturn(mockHttpSession);
    Mockito.when(mockHttpSession.getAttribute(SessionConstants.SESSION_LOGGED_IN_USER)).thenReturn(loggedInUserRepresentation);
    Mockito.when(mockModel.addAttribute(SessionConstants.SESSION_LOGGED_IN_USER, loggedInUserRepresentation)).thenReturn(mockModel);
    Mockito.when(mockModel.addAttribute(ModelConstants.MODEL_PROJECT_DETAIL, projectDetailRepresentation)).thenReturn(mockModel);
    Mockito.when(mockModel.addAttribute(Mockito.eq(ModelConstants.MODEL_ADD_PROJECT_DESCRIPTION), Mockito.any(ProjectDetailRepresentation.class))).thenReturn(mockModel);
    Mockito.when(mockModel.addAttribute(Mockito.eq(ModelConstants.MODEL_ADD_PROJECT_INSTRUCTION_ERRORS), errorsRepresentationCaptor.capture())).thenReturn(mockModel);
    Mockito.when(mockModel.addAttribute(Mockito.eq(ModelConstants.MODEL_ADD_PROJECT_INSTRUCTION), Mockito.any(ProjectDetailInstructionRepresentation.class))).thenReturn(mockModel);
    Mockito.when(mockModel.addAttribute(ModelConstants.MODEL_PROJECT_DETAIL, projectDetailRepresentation)).thenReturn(mockModel);
    Mockito.when(mockProjectDetailService.getProjectDetail(Mockito.any())).thenReturn(responseCommandDTO);
    Mockito.when(mockProjectDetailService.updateProjectInstruction(Mockito.any())).thenThrow(updateProjectInstructionFailedException);
    Mockito.doNothing().when(mockHttpSession).setAttribute(ModelConstants.MODEL_IS_SHARED_PROJECT, false);

    // Execute
    Exception expectedException = null;
    String result = null;
    try {
      result = projectDetailController.editProjectInstruction(projectDetailInstructionRepresentation, mockModel, mockHttpServletRequest, 1L);
    } catch (Exception exception) {
      expectedException = exception;
    }

    // Verify
    Mockito.verify(mockProjectDetailService, Mockito.times(1)).getProjectDetail(projectDetailCommandDTOCaptor.capture());
    Mockito.verify(mockHttpSession, Mockito.times(1)).setAttribute(ModelConstants.MODEL_IS_SHARED_PROJECT, false);
    Mockito.verify(mockModel, Mockito.times(1)).addAttribute(SessionConstants.SESSION_LOGGED_IN_USER, loggedInUserRepresentation);
    Mockito.verify(mockModel, Mockito.times(1)).addAttribute(ModelConstants.MODEL_PROJECT_DETAIL, projectDetailRepresentation);
    Mockito.verify(mockModel, Mockito.times(1)).addAttribute(Mockito.eq(ModelConstants.MODEL_ADD_PROJECT_DESCRIPTION), Mockito.any(ProjectDetailRepresentation.class));
    Mockito.verify(mockModel, Mockito.times(1)).addAttribute(Mockito.eq(ModelConstants.MODEL_ADD_PROJECT_INSTRUCTION_ERRORS), Mockito.any(ErrorsRepresentation.class));
    Mockito.verify(mockModel, Mockito.times(2)).addAttribute(Mockito.eq(ModelConstants.MODEL_ADD_PROJECT_INSTRUCTION), Mockito.any(ProjectDetailInstructionRepresentation.class));

    // Assertions
    assert expectedException == null;
    assert ((Long) projectDetailCommandDTOCaptor.getValue().get(ContextConstants.CONTEXT_PROJECT_ID)) == 1L;
    assert result.equals(ViewConstants.VIEW_PROJECT);
    assert errorsRepresentationCaptor.getValue().getErrorMessages().get(0).equals("message");
    assert errorsRepresentationCaptor.getValue().getFieldErrorMessages().get("key1").equals("value1");
  }

  @Test
  public void testShowEditProjectInstruction() {
    // Initialize
    Model mockModel = Mockito.mock(Model.class);
    HttpServletRequest mockHttpServletRequest = Mockito.mock(HttpServletRequest.class);
    HttpSession mockHttpSession = Mockito.mock(HttpSession.class);

    ArgumentCaptor<CommandDTO> projectDetailCommandDTOCaptor = ArgumentCaptor.forClass(CommandDTO.class);
    ArgumentCaptor<CommandDTO> getProjectInstructionCommandDTOCaptor = ArgumentCaptor.forClass(CommandDTO.class);

    LoggedInUserRepresentation loggedInUserRepresentation = LoggedInUserRepresentation.builder().userName("username").build();
    ProjectDetailRepresentation projectDetailRepresentation = ProjectDetailRepresentation.builder().id(1L).build();
    ProjectDetailInstructionRepresentation projectDetailInstructionRepresentation = ProjectDetailInstructionRepresentation.builder().id(1L).build();

    ProjectDetailService mockProjectDetailService = Mockito.mock(ProjectDetailService.class);

    ProjectDetailController projectDetailController = new ProjectDetailController();
    projectDetailController.projectDetailService = mockProjectDetailService;
    CommandDTO responseCommandDTO = CommandDTO
        .builder()
        .add(ContextConstants.CONTEXT_PROJECT_DETAIL, projectDetailRepresentation)
        .build();
    CommandDTO instructionResponseCommandDTO = CommandDTO
        .builder()
        .add(ContextConstants.CONTEXT_PROJECT_INSTRUCTION, projectDetailInstructionRepresentation)
        .build();

    // Define Mocks
    Mockito.when(mockHttpServletRequest.getSession()).thenReturn(mockHttpSession);
    Mockito.when(mockHttpSession.getAttribute(SessionConstants.SESSION_LOGGED_IN_USER)).thenReturn(loggedInUserRepresentation);
    Mockito.when(mockModel.addAttribute(SessionConstants.SESSION_LOGGED_IN_USER, loggedInUserRepresentation)).thenReturn(mockModel);
    Mockito.when(mockModel.addAttribute(ModelConstants.MODEL_PROJECT_DETAIL, projectDetailRepresentation)).thenReturn(mockModel);
    Mockito.when(mockModel.addAttribute(Mockito.eq(ModelConstants.MODEL_ADD_PROJECT_DESCRIPTION), Mockito.any(ProjectDetailRepresentation.class))).thenReturn(mockModel);
    Mockito.when(mockModel.addAttribute(Mockito.eq(ModelConstants.MODEL_ADD_PROJECT_INSTRUCTION), Mockito.any(ProjectDetailInstructionRepresentation.class))).thenReturn(mockModel);
    Mockito.when(mockModel.addAttribute(ModelConstants.MODEL_PROJECT_DETAIL, projectDetailRepresentation)).thenReturn(mockModel);
    Mockito.when(mockModel.addAttribute(ModelConstants.MODEL_EDIT_PROJECT_INSTRUCTION, projectDetailInstructionRepresentation)).thenReturn(mockModel);
    Mockito.when(mockProjectDetailService.getProjectDetail(Mockito.any())).thenReturn(responseCommandDTO);
    Mockito.when(mockProjectDetailService.getProjectInstruction(Mockito.any())).thenReturn(instructionResponseCommandDTO);
    Mockito.doNothing().when(mockHttpSession).setAttribute(ModelConstants.MODEL_IS_SHARED_PROJECT, false);

    // Execute
    Exception expectedException = null;
    String result = null;
    try {
      result = projectDetailController.editProjectInstruction(1L, 1L, mockModel, mockHttpServletRequest);
    } catch (Exception exception) {
      expectedException = exception;
    }

    // Verify
    Mockito.verify(mockProjectDetailService, Mockito.times(1)).getProjectDetail(projectDetailCommandDTOCaptor.capture());
    Mockito.verify(mockProjectDetailService, Mockito.times(1)).getProjectInstruction(getProjectInstructionCommandDTOCaptor.capture());
    Mockito.verify(mockHttpSession, Mockito.times(1)).setAttribute(ModelConstants.MODEL_IS_SHARED_PROJECT, false);
    Mockito.verify(mockModel, Mockito.times(1)).addAttribute(SessionConstants.SESSION_LOGGED_IN_USER, loggedInUserRepresentation);
    Mockito.verify(mockModel, Mockito.times(1)).addAttribute(ModelConstants.MODEL_PROJECT_DETAIL, projectDetailRepresentation);
    Mockito.verify(mockModel, Mockito.times(1)).addAttribute(ModelConstants.MODEL_EDIT_PROJECT_INSTRUCTION, projectDetailInstructionRepresentation);
    Mockito.verify(mockModel, Mockito.times(1)).addAttribute(Mockito.eq(ModelConstants.MODEL_ADD_PROJECT_DESCRIPTION), Mockito.any(ProjectDetailRepresentation.class));
    Mockito.verify(mockModel, Mockito.times(1)).addAttribute(Mockito.eq(ModelConstants.MODEL_ADD_PROJECT_INSTRUCTION), Mockito.any(ProjectDetailInstructionRepresentation.class));

    // Assertions
    assert expectedException == null;
    assert ((Long) projectDetailCommandDTOCaptor.getValue().get(ContextConstants.CONTEXT_PROJECT_ID)) == 1L;
    assert ((Long) getProjectInstructionCommandDTOCaptor.getValue().get(ContextConstants.CONTEXT_INSTRUCTION_ID)) == 1L;
    assert result.equals(ViewConstants.VIEW_PROJECT);
  }

  @Test
  public void testShowEditProjectInstructionException() {
    // Initialize
    Model mockModel = Mockito.mock(Model.class);
    HttpServletRequest mockHttpServletRequest = Mockito.mock(HttpServletRequest.class);
    HttpSession mockHttpSession = Mockito.mock(HttpSession.class);

    ArgumentCaptor<CommandDTO> projectDetailCommandDTOCaptor = ArgumentCaptor.forClass(CommandDTO.class);
    ArgumentCaptor<ErrorsRepresentation> errorsRepresentationCaptor = ArgumentCaptor.forClass(ErrorsRepresentation.class);

    LoggedInUserRepresentation loggedInUserRepresentation = LoggedInUserRepresentation.builder().userName("username").build();
    ProjectDetailRepresentation projectDetailRepresentation = ProjectDetailRepresentation.builder().id(1L).build();

    ProjectDetailService mockProjectDetailService = Mockito.mock(ProjectDetailService.class);

    Map<String, String> fieldMessages = new HashMap<>();
    fieldMessages.put("key1", "value1");
    GetProjectInstructionFailedException getProjectInstructionFailedException = new GetProjectInstructionFailedException();
    getProjectInstructionFailedException.setMessages(Collections.singletonList("message"));
    getProjectInstructionFailedException.setFieldMessages(fieldMessages);

    ProjectDetailController projectDetailController = new ProjectDetailController();
    projectDetailController.projectDetailService = mockProjectDetailService;
    CommandDTO responseCommandDTO = CommandDTO
        .builder()
        .add(ContextConstants.CONTEXT_PROJECT_DETAIL, projectDetailRepresentation)
        .build();

    // Define Mocks
    Mockito.when(mockHttpServletRequest.getSession()).thenReturn(mockHttpSession);
    Mockito.when(mockHttpSession.getAttribute(SessionConstants.SESSION_LOGGED_IN_USER)).thenReturn(loggedInUserRepresentation);
    Mockito.when(mockModel.addAttribute(SessionConstants.SESSION_LOGGED_IN_USER, loggedInUserRepresentation)).thenReturn(mockModel);
    Mockito.when(mockModel.addAttribute(ModelConstants.MODEL_PROJECT_DETAIL, projectDetailRepresentation)).thenReturn(mockModel);
    Mockito.when(mockModel.addAttribute(Mockito.eq(ModelConstants.MODEL_ADD_PROJECT_DESCRIPTION), Mockito.any(ProjectDetailRepresentation.class))).thenReturn(mockModel);
    Mockito.when(mockModel.addAttribute(Mockito.eq(ModelConstants.MODEL_EDIT_PROJECT_INSTRUCTION_ERRORS), errorsRepresentationCaptor.capture())).thenReturn(mockModel);
    Mockito.when(mockModel.addAttribute(Mockito.eq(ModelConstants.MODEL_ADD_PROJECT_INSTRUCTION), Mockito.any(ProjectDetailInstructionRepresentation.class))).thenReturn(mockModel);
    Mockito.when(mockModel.addAttribute(ModelConstants.MODEL_PROJECT_DETAIL, projectDetailRepresentation)).thenReturn(mockModel);
    Mockito.when(mockProjectDetailService.getProjectDetail(Mockito.any())).thenReturn(responseCommandDTO);
    Mockito.when(mockProjectDetailService.getProjectInstruction(Mockito.any())).thenThrow(getProjectInstructionFailedException);
    Mockito.doNothing().when(mockHttpSession).setAttribute(ModelConstants.MODEL_IS_SHARED_PROJECT, false);

    // Execute
    Exception expectedException = null;
    String result = null;
    try {
      result = projectDetailController.editProjectInstruction(1L, 1L, mockModel, mockHttpServletRequest);
    } catch (Exception exception) {
      expectedException = exception;
    }

    // Verify
    Mockito.verify(mockProjectDetailService, Mockito.times(1)).getProjectDetail(projectDetailCommandDTOCaptor.capture());
    Mockito.verify(mockHttpSession, Mockito.times(1)).setAttribute(ModelConstants.MODEL_IS_SHARED_PROJECT, false);
    Mockito.verify(mockModel, Mockito.times(1)).addAttribute(SessionConstants.SESSION_LOGGED_IN_USER, loggedInUserRepresentation);
    Mockito.verify(mockModel, Mockito.times(1)).addAttribute(ModelConstants.MODEL_PROJECT_DETAIL, projectDetailRepresentation);
    Mockito.verify(mockModel, Mockito.times(1)).addAttribute(Mockito.eq(ModelConstants.MODEL_ADD_PROJECT_DESCRIPTION), Mockito.any(ProjectDetailRepresentation.class));
    Mockito.verify(mockModel, Mockito.times(1)).addAttribute(Mockito.eq(ModelConstants.MODEL_EDIT_PROJECT_INSTRUCTION_ERRORS), Mockito.any(ErrorsRepresentation.class));
    Mockito.verify(mockModel, Mockito.times(1)).addAttribute(Mockito.eq(ModelConstants.MODEL_ADD_PROJECT_INSTRUCTION), Mockito.any(ProjectDetailInstructionRepresentation.class));

    // Assertions
    assert expectedException == null;
    assert ((Long) projectDetailCommandDTOCaptor.getValue().get(ContextConstants.CONTEXT_PROJECT_ID)) == 1L;
    assert result.equals(ViewConstants.VIEW_PROJECT);
    assert errorsRepresentationCaptor.getValue().getErrorMessages().get(0).equals("message");
    assert errorsRepresentationCaptor.getValue().getFieldErrorMessages().get("key1").equals("value1");
  }

  @Test
  public void testDeleteProjectInstruction() {
    // Initialize
    Model mockModel = Mockito.mock(Model.class);
    HttpServletRequest mockHttpServletRequest = Mockito.mock(HttpServletRequest.class);
    HttpSession mockHttpSession = Mockito.mock(HttpSession.class);

    ArgumentCaptor<CommandDTO> projectDetailCommandDTOCaptor = ArgumentCaptor.forClass(CommandDTO.class);
    ArgumentCaptor<CommandDTO> deleteProjectInstructionCommandDTOCaptor = ArgumentCaptor.forClass(CommandDTO.class);

    LoggedInUserRepresentation loggedInUserRepresentation = LoggedInUserRepresentation.builder().userName("username").build();
    ProjectDetailRepresentation projectDetailRepresentation = ProjectDetailRepresentation.builder().id(1L).build();
    ProjectDetailInstructionRepresentation projectDetailInstructionRepresentation = ProjectDetailInstructionRepresentation.builder().id(1L).build();

    ProjectDetailService mockProjectDetailService = Mockito.mock(ProjectDetailService.class);

    ProjectDetailController projectDetailController = new ProjectDetailController();
    projectDetailController.projectDetailService = mockProjectDetailService;
    CommandDTO responseCommandDTO = CommandDTO
        .builder()
        .add(ContextConstants.CONTEXT_PROJECT_DETAIL, projectDetailRepresentation)
        .build();
    CommandDTO instructionResponseCommandDTO = CommandDTO
        .builder()
        .add(ContextConstants.CONTEXT_PROJECT_INSTRUCTION, projectDetailInstructionRepresentation)
        .build();

    // Define Mocks
    Mockito.when(mockHttpServletRequest.getSession()).thenReturn(mockHttpSession);
    Mockito.when(mockHttpSession.getAttribute(SessionConstants.SESSION_LOGGED_IN_USER)).thenReturn(loggedInUserRepresentation);
    Mockito.when(mockModel.addAttribute(SessionConstants.SESSION_LOGGED_IN_USER, loggedInUserRepresentation)).thenReturn(mockModel);
    Mockito.when(mockModel.addAttribute(ModelConstants.MODEL_PROJECT_DETAIL, projectDetailRepresentation)).thenReturn(mockModel);
    Mockito.when(mockModel.addAttribute(Mockito.eq(ModelConstants.MODEL_ADD_PROJECT_DESCRIPTION), Mockito.any(ProjectDetailRepresentation.class))).thenReturn(mockModel);
    Mockito.when(mockModel.addAttribute(Mockito.eq(ModelConstants.MODEL_ADD_PROJECT_INSTRUCTION), Mockito.any(ProjectDetailInstructionRepresentation.class))).thenReturn(mockModel);
    Mockito.when(mockModel.addAttribute(ModelConstants.MODEL_PROJECT_DETAIL, projectDetailRepresentation)).thenReturn(mockModel);
    Mockito.when(mockProjectDetailService.getProjectDetail(Mockito.any())).thenReturn(responseCommandDTO);
    Mockito.when(mockProjectDetailService.deleteProjectInstruction(Mockito.any())).thenReturn(instructionResponseCommandDTO);
    Mockito.doNothing().when(mockHttpSession).setAttribute(ModelConstants.MODEL_IS_SHARED_PROJECT, false);

    // Execute
    Exception expectedException = null;
    String result = null;
    try {
      result = projectDetailController.deleteProjectInstruction(1L, 1L, mockModel, mockHttpServletRequest);
    } catch (Exception exception) {
      expectedException = exception;
    }

    // Verify
    Mockito.verify(mockProjectDetailService, Mockito.times(1)).getProjectDetail(projectDetailCommandDTOCaptor.capture());
    Mockito.verify(mockProjectDetailService, Mockito.times(1)).deleteProjectInstruction(deleteProjectInstructionCommandDTOCaptor.capture());
    Mockito.verify(mockHttpSession, Mockito.times(1)).setAttribute(ModelConstants.MODEL_IS_SHARED_PROJECT, false);
    Mockito.verify(mockModel, Mockito.times(1)).addAttribute(SessionConstants.SESSION_LOGGED_IN_USER, loggedInUserRepresentation);
    Mockito.verify(mockModel, Mockito.times(1)).addAttribute(ModelConstants.MODEL_PROJECT_DETAIL, projectDetailRepresentation);
    Mockito.verify(mockModel, Mockito.times(1)).addAttribute(Mockito.eq(ModelConstants.MODEL_ADD_PROJECT_DESCRIPTION), Mockito.any(ProjectDetailRepresentation.class));
    Mockito.verify(mockModel, Mockito.times(1)).addAttribute(Mockito.eq(ModelConstants.MODEL_ADD_PROJECT_INSTRUCTION), Mockito.any(ProjectDetailInstructionRepresentation.class));

    // Assertions
    assert expectedException == null;
    assert ((Long) projectDetailCommandDTOCaptor.getValue().get(ContextConstants.CONTEXT_PROJECT_ID)) == 1L;
    assert ((Long) deleteProjectInstructionCommandDTOCaptor.getValue().get(ContextConstants.CONTEXT_INSTRUCTION_ID)) == 1L;
    assert result.equals(ViewConstants.VIEW_PROJECT);
  }

  @Test
  public void testDeleteProjectInstructionException() {
    // Initialize
    Model mockModel = Mockito.mock(Model.class);
    HttpServletRequest mockHttpServletRequest = Mockito.mock(HttpServletRequest.class);
    HttpSession mockHttpSession = Mockito.mock(HttpSession.class);

    ArgumentCaptor<CommandDTO> projectDetailCommandDTOCaptor = ArgumentCaptor.forClass(CommandDTO.class);
    ArgumentCaptor<ErrorsRepresentation> errorsRepresentationCaptor = ArgumentCaptor.forClass(ErrorsRepresentation.class);

    LoggedInUserRepresentation loggedInUserRepresentation = LoggedInUserRepresentation.builder().userName("username").build();
    ProjectDetailRepresentation projectDetailRepresentation = ProjectDetailRepresentation.builder().id(1L).build();

    ProjectDetailService mockProjectDetailService = Mockito.mock(ProjectDetailService.class);

    Map<String, String> fieldMessages = new HashMap<>();
    fieldMessages.put("key1", "value1");
    DeleteProjectInstructionFailedException deleteProjectInstructionFailedException = new DeleteProjectInstructionFailedException();
    deleteProjectInstructionFailedException.setMessages(Collections.singletonList("message"));
    deleteProjectInstructionFailedException.setFieldMessages(fieldMessages);

    ProjectDetailController projectDetailController = new ProjectDetailController();
    projectDetailController.projectDetailService = mockProjectDetailService;
    CommandDTO responseCommandDTO = CommandDTO
        .builder()
        .add(ContextConstants.CONTEXT_PROJECT_DETAIL, projectDetailRepresentation)
        .build();

    // Define Mocks
    Mockito.when(mockHttpServletRequest.getSession()).thenReturn(mockHttpSession);
    Mockito.when(mockHttpSession.getAttribute(SessionConstants.SESSION_LOGGED_IN_USER)).thenReturn(loggedInUserRepresentation);
    Mockito.when(mockModel.addAttribute(SessionConstants.SESSION_LOGGED_IN_USER, loggedInUserRepresentation)).thenReturn(mockModel);
    Mockito.when(mockModel.addAttribute(ModelConstants.MODEL_PROJECT_DETAIL, projectDetailRepresentation)).thenReturn(mockModel);
    Mockito.when(mockModel.addAttribute(Mockito.eq(ModelConstants.MODEL_ADD_PROJECT_DESCRIPTION), Mockito.any(ProjectDetailRepresentation.class))).thenReturn(mockModel);
    Mockito.when(mockModel.addAttribute(Mockito.eq(ModelConstants.MODEL_DELETE_PROJECT_INSTRUCTION_ERRORS), errorsRepresentationCaptor.capture())).thenReturn(mockModel);
    Mockito.when(mockModel.addAttribute(Mockito.eq(ModelConstants.MODEL_ADD_PROJECT_INSTRUCTION), Mockito.any(ProjectDetailInstructionRepresentation.class))).thenReturn(mockModel);
    Mockito.when(mockModel.addAttribute(ModelConstants.MODEL_PROJECT_DETAIL, projectDetailRepresentation)).thenReturn(mockModel);
    Mockito.when(mockProjectDetailService.getProjectDetail(Mockito.any())).thenReturn(responseCommandDTO);
    Mockito.when(mockProjectDetailService.deleteProjectInstruction(Mockito.any())).thenThrow(deleteProjectInstructionFailedException);
    Mockito.doNothing().when(mockHttpSession).setAttribute(ModelConstants.MODEL_IS_SHARED_PROJECT, false);

    // Execute
    Exception expectedException = null;
    String result = null;
    try {
      result = projectDetailController.deleteProjectInstruction(1L, 1L, mockModel, mockHttpServletRequest);
    } catch (Exception exception) {
      expectedException = exception;
    }

    // Verify
    Mockito.verify(mockProjectDetailService, Mockito.times(1)).getProjectDetail(projectDetailCommandDTOCaptor.capture());
    Mockito.verify(mockHttpSession, Mockito.times(1)).setAttribute(ModelConstants.MODEL_IS_SHARED_PROJECT, false);
    Mockito.verify(mockModel, Mockito.times(1)).addAttribute(SessionConstants.SESSION_LOGGED_IN_USER, loggedInUserRepresentation);
    Mockito.verify(mockModel, Mockito.times(1)).addAttribute(ModelConstants.MODEL_PROJECT_DETAIL, projectDetailRepresentation);
    Mockito.verify(mockModel, Mockito.times(1)).addAttribute(Mockito.eq(ModelConstants.MODEL_ADD_PROJECT_DESCRIPTION), Mockito.any(ProjectDetailRepresentation.class));
    Mockito.verify(mockModel, Mockito.times(1)).addAttribute(Mockito.eq(ModelConstants.MODEL_DELETE_PROJECT_INSTRUCTION_ERRORS), Mockito.any(ErrorsRepresentation.class));
    Mockito.verify(mockModel, Mockito.times(1)).addAttribute(Mockito.eq(ModelConstants.MODEL_ADD_PROJECT_INSTRUCTION), Mockito.any(ProjectDetailInstructionRepresentation.class));

    // Assertions
    assert expectedException == null;
    assert ((Long) projectDetailCommandDTOCaptor.getValue().get(ContextConstants.CONTEXT_PROJECT_ID)) == 1L;
    assert result.equals(ViewConstants.VIEW_PROJECT);
    assert errorsRepresentationCaptor.getValue().getErrorMessages().get(0).equals("message");
    assert errorsRepresentationCaptor.getValue().getFieldErrorMessages().get("key1").equals("value1");
  }
}
