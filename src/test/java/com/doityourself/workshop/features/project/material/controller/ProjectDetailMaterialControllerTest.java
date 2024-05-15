package com.doityourself.workshop.features.project.material.controller;

import com.doityourself.workshop.common.command.CommandDTO;
import com.doityourself.workshop.common.constants.ContextConstants;
import com.doityourself.workshop.common.constants.ModelConstants;
import com.doityourself.workshop.common.constants.SessionConstants;
import com.doityourself.workshop.common.constants.ViewConstants;
import com.doityourself.workshop.common.representation.error.ErrorsRepresentation;
import com.doityourself.workshop.features.login.representation.LoggedInUserRepresentation;
import com.doityourself.workshop.features.project.detail.exception.GetProjectDetailFailedException;
import com.doityourself.workshop.features.project.material.exception.*;
import com.doityourself.workshop.features.project.material.representation.ProjectDetailMaterialRepresentation;
import com.doityourself.workshop.features.project.material.representation.ProjectDetailRepresentation;
import com.doityourself.workshop.features.project.material.service.ProjectMaterialService;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import org.springframework.ui.Model;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class ProjectDetailMaterialControllerTest {
  @Test
  public void testGetProjectDetail() {
    // Initialize
    Model mockModel = Mockito.mock(Model.class);
    HttpServletRequest mockHttpServletRequest = Mockito.mock(HttpServletRequest.class);
    HttpSession mockHttpSession = Mockito.mock(HttpSession.class);

    ProjectMaterialService mockProjectMaterialService = Mockito.mock(ProjectMaterialService.class);

    ArgumentCaptor<CommandDTO> projectDetailCommandDTOCaptor = ArgumentCaptor.forClass(CommandDTO.class);

    ProjectDetailMaterialController projectDetailMaterialController = new ProjectDetailMaterialController();
    projectDetailMaterialController.projectDetailService = mockProjectMaterialService;

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
    Mockito.when(mockProjectMaterialService.getProjectMaterials(Mockito.any())).thenReturn(responseCommandDTO);
    Mockito.when(mockModel.addAttribute(ModelConstants.MODEL_PROJECT_DETAIL, projectDetailRepresentation)).thenReturn(mockModel);
    Mockito.when(mockModel.addAttribute(Mockito.eq(ModelConstants.MODEL_ADD_PROJECT_MATERIAL), Mockito.any(ProjectDetailMaterialRepresentation.class))).thenReturn(mockModel);
    Mockito.when(mockModel.addAttribute(Mockito.eq(ModelConstants.MODEL_ADD_PROJECT_BUDGET), Mockito.any(ProjectDetailRepresentation.class))).thenReturn(mockModel);

    // Execute
    Exception expectedException = null;
    String result = null;
    try {
      result = projectDetailMaterialController.getProjectDetail(1L, mockModel, mockHttpServletRequest);
    } catch (Exception exception) {
      expectedException = exception;
    }

    // Verify
    Mockito.verify(mockModel, Mockito.times(1)).addAttribute(SessionConstants.SESSION_LOGGED_IN_USER, loggedInUserRepresentation);
    Mockito.verify(mockProjectMaterialService, Mockito.times(1)).getProjectMaterials(projectDetailCommandDTOCaptor.capture());
    Mockito.verify(mockModel, Mockito.times(1)).addAttribute(ModelConstants.MODEL_PROJECT_DETAIL, projectDetailRepresentation);
    Mockito.verify(mockModel, Mockito.times(1)).addAttribute(Mockito.eq(ModelConstants.MODEL_ADD_PROJECT_MATERIAL), Mockito.any(ProjectDetailMaterialRepresentation.class));
    Mockito.verify(mockModel, Mockito.times(1)).addAttribute(Mockito.eq(ModelConstants.MODEL_ADD_PROJECT_BUDGET), Mockito.any(ProjectDetailRepresentation.class));

    // Assertions
    assert expectedException == null;
    assert (Long) projectDetailCommandDTOCaptor.getValue().get(ContextConstants.CONTEXT_PROJECT_ID) == 1L;
    assert result.equals(ViewConstants.VIEW_MATERIAL);
  }

  @Test
  public void testGetProjectDetailException() {
    // Initialize
    Model mockModel = Mockito.mock(Model.class);
    HttpServletRequest mockHttpServletRequest = Mockito.mock(HttpServletRequest.class);
    HttpSession mockHttpSession = Mockito.mock(HttpSession.class);

    ProjectMaterialService mockProjectMaterialService = Mockito.mock(ProjectMaterialService.class);

    ArgumentCaptor<CommandDTO> projectDetailCommandDTOCaptor = ArgumentCaptor.forClass(CommandDTO.class);
    ArgumentCaptor<ErrorsRepresentation> errorsRepresentationCaptor = ArgumentCaptor.forClass(ErrorsRepresentation.class);

    ProjectDetailMaterialController projectDetailMaterialController = new ProjectDetailMaterialController();
    projectDetailMaterialController.projectDetailService = mockProjectMaterialService;

    LoggedInUserRepresentation loggedInUserRepresentation = LoggedInUserRepresentation.builder().userName("username").build();

    Map<String, String> fieldMessages = new HashMap<>();
    fieldMessages.put("field1", "message1");
    GetProjectDetailFailedException responseException = new GetProjectDetailFailedException();
    responseException.setFieldMessages(fieldMessages);
    responseException.setMessages(Collections.singletonList("message1"));

    // Define Mocks
    Mockito.when(mockHttpServletRequest.getSession()).thenReturn(mockHttpSession);
    Mockito.when(mockHttpSession.getAttribute(SessionConstants.SESSION_LOGGED_IN_USER)).thenReturn(loggedInUserRepresentation);
    Mockito.when(mockModel.addAttribute(SessionConstants.SESSION_LOGGED_IN_USER, loggedInUserRepresentation)).thenReturn(mockModel);
    Mockito.when(mockProjectMaterialService.getProjectMaterials(Mockito.any())).thenThrow(responseException);
    Mockito.when(mockModel.addAttribute(Mockito.eq(ModelConstants.MODEL_PROJECT_DETAIL), Mockito.any(ProjectDetailRepresentation.class))).thenReturn(mockModel);
    Mockito.when(mockModel.addAttribute(Mockito.eq(ModelConstants.MODEL_ADD_PROJECT_MATERIAL), Mockito.any(ProjectDetailMaterialRepresentation.class))).thenReturn(mockModel);
    Mockito.when(mockModel.addAttribute(Mockito.eq(ModelConstants.MODEL_ADD_PROJECT_BUDGET), Mockito.any(ProjectDetailRepresentation.class))).thenReturn(mockModel);
    Mockito.when(mockModel.addAttribute(Mockito.eq(ModelConstants.MODEL_GET_PROJECT_DETAIL_ERRORS), Mockito.any(ErrorsRepresentation.class))).thenReturn(mockModel);

    // Execute
    Exception expectedException = null;
    String result = null;
    try {
      result = projectDetailMaterialController.getProjectDetail(1L, mockModel, mockHttpServletRequest);
    } catch (Exception exception) {
      expectedException = exception;
    }

    // Verify
    Mockito.verify(mockModel, Mockito.times(1)).addAttribute(SessionConstants.SESSION_LOGGED_IN_USER, loggedInUserRepresentation);
    Mockito.verify(mockProjectMaterialService, Mockito.times(1)).getProjectMaterials(projectDetailCommandDTOCaptor.capture());
    Mockito.verify(mockModel, Mockito.times(1)).addAttribute(Mockito.eq(ModelConstants.MODEL_PROJECT_DETAIL), Mockito.any(ProjectDetailRepresentation.class));
    Mockito.verify(mockModel, Mockito.times(0)).addAttribute(Mockito.eq(ModelConstants.MODEL_ADD_PROJECT_MATERIAL), Mockito.any(ProjectDetailMaterialRepresentation.class));
    Mockito.verify(mockModel, Mockito.times(1)).addAttribute(Mockito.eq(ModelConstants.MODEL_ADD_PROJECT_BUDGET), Mockito.any(ProjectDetailRepresentation.class));
    Mockito.verify(mockModel, Mockito.times(1)).addAttribute(Mockito.eq(ModelConstants.MODEL_GET_PROJECT_DETAIL_ERRORS), errorsRepresentationCaptor.capture());

    // Assertions
    assert expectedException == null;
    assert (Long) projectDetailCommandDTOCaptor.getValue().get(ContextConstants.CONTEXT_PROJECT_ID) == 1L;
    assert result.equals(ViewConstants.VIEW_MATERIAL);
    assert errorsRepresentationCaptor.getValue().getErrorMessages().get(0).equals("message1");
    assert errorsRepresentationCaptor.getValue().getFieldErrorMessages().get("field1").equals("message1");
  }

  @Test
  public void testPrintProjectMaterials() {
    // Initialize
    Model mockModel = Mockito.mock(Model.class);
    HttpServletRequest mockHttpServletRequest = Mockito.mock(HttpServletRequest.class);
    HttpSession mockHttpSession = Mockito.mock(HttpSession.class);

    ProjectMaterialService mockProjectMaterialService = Mockito.mock(ProjectMaterialService.class);

    ArgumentCaptor<CommandDTO> projectDetailCommandDTOCaptor = ArgumentCaptor.forClass(CommandDTO.class);

    ProjectDetailMaterialController projectDetailMaterialController = new ProjectDetailMaterialController();
    projectDetailMaterialController.projectDetailService = mockProjectMaterialService;

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
    Mockito.when(mockProjectMaterialService.printProjectMaterials(Mockito.any())).thenReturn(responseCommandDTO);
    Mockito.when(mockModel.addAttribute(ModelConstants.MODEL_PROJECT_DETAIL, projectDetailRepresentation)).thenReturn(mockModel);

    // Execute
    Exception expectedException = null;
    String result = null;
    try {
      result = projectDetailMaterialController.printProjectMaterials(1L, mockModel, mockHttpServletRequest);
    } catch (Exception exception) {
      expectedException = exception;
    }

    // Verify
    Mockito.verify(mockModel, Mockito.times(1)).addAttribute(SessionConstants.SESSION_LOGGED_IN_USER, loggedInUserRepresentation);
    Mockito.verify(mockProjectMaterialService, Mockito.times(1)).printProjectMaterials(projectDetailCommandDTOCaptor.capture());
    Mockito.verify(mockModel, Mockito.times(1)).addAttribute(ModelConstants.MODEL_PROJECT_DETAIL, projectDetailRepresentation);

    // Assertions
    assert expectedException == null;
    assert (Long) projectDetailCommandDTOCaptor.getValue().get(ContextConstants.CONTEXT_PROJECT_ID) == 1L;
    assert result.equals(ViewConstants.VIEW_PRINT_MATERIAL);
  }

  @Test
  public void testPrintProjectMaterialsException() {
    // Initialize
    Model mockModel = Mockito.mock(Model.class);
    HttpServletRequest mockHttpServletRequest = Mockito.mock(HttpServletRequest.class);
    HttpSession mockHttpSession = Mockito.mock(HttpSession.class);

    ProjectMaterialService mockProjectMaterialService = Mockito.mock(ProjectMaterialService.class);

    ArgumentCaptor<CommandDTO> projectDetailCommandDTOCaptor = ArgumentCaptor.forClass(CommandDTO.class);
    ArgumentCaptor<ErrorsRepresentation> errorsRepresentationCaptor = ArgumentCaptor.forClass(ErrorsRepresentation.class);

    ProjectDetailMaterialController projectDetailMaterialController = new ProjectDetailMaterialController();
    projectDetailMaterialController.projectDetailService = mockProjectMaterialService;

    LoggedInUserRepresentation loggedInUserRepresentation = LoggedInUserRepresentation.builder().userName("username").build();

    Map<String, String> fieldMessages = new HashMap<>();
    fieldMessages.put("field1", "message1");
    GetProjectDetailFailedException responseException = new GetProjectDetailFailedException();
    responseException.setFieldMessages(fieldMessages);
    responseException.setMessages(Collections.singletonList("message1"));


    // Define Mocks
    Mockito.when(mockHttpServletRequest.getSession()).thenReturn(mockHttpSession);
    Mockito.when(mockHttpSession.getAttribute(SessionConstants.SESSION_LOGGED_IN_USER)).thenReturn(loggedInUserRepresentation);
    Mockito.when(mockModel.addAttribute(SessionConstants.SESSION_LOGGED_IN_USER, loggedInUserRepresentation)).thenReturn(mockModel);
    Mockito.when(mockProjectMaterialService.printProjectMaterials(Mockito.any())).thenThrow(responseException);
    Mockito.when(mockModel.addAttribute(Mockito.eq(ModelConstants.MODEL_PROJECT_DETAIL), Mockito.any(ProjectDetailRepresentation.class))).thenReturn(mockModel);
    Mockito.when(mockModel.addAttribute(Mockito.eq(ModelConstants.MODEL_GET_PROJECT_DETAIL_ERRORS), Mockito.any(ErrorsRepresentation.class))).thenReturn(mockModel);

    // Execute
    Exception expectedException = null;
    String result = null;
    try {
      result = projectDetailMaterialController.printProjectMaterials(1L, mockModel, mockHttpServletRequest);
    } catch (Exception exception) {
      expectedException = exception;
    }

    // Verify
    Mockito.verify(mockModel, Mockito.times(1)).addAttribute(SessionConstants.SESSION_LOGGED_IN_USER, loggedInUserRepresentation);
    Mockito.verify(mockProjectMaterialService, Mockito.times(1)).printProjectMaterials(projectDetailCommandDTOCaptor.capture());
    Mockito.verify(mockModel, Mockito.times(1)).addAttribute(Mockito.eq(ModelConstants.MODEL_PROJECT_DETAIL), Mockito.any(ProjectDetailRepresentation.class));
    Mockito.verify(mockModel, Mockito.times(1)).addAttribute(Mockito.eq(ModelConstants.MODEL_GET_PROJECT_DETAIL_ERRORS), errorsRepresentationCaptor.capture());

    // Assertions
    assert expectedException == null;
    assert (Long) projectDetailCommandDTOCaptor.getValue().get(ContextConstants.CONTEXT_PROJECT_ID) == 1L;
    assert result.equals(ViewConstants.VIEW_PRINT_MATERIAL);
    assert errorsRepresentationCaptor.getValue().getErrorMessages().get(0).equals("message1");
    assert errorsRepresentationCaptor.getValue().getFieldErrorMessages().get("field1").equals("message1");
  }

  @Test
  public void testAddProjectBudget() {
    // Initialize
    Model mockModel = Mockito.mock(Model.class);
    HttpServletRequest mockHttpServletRequest = Mockito.mock(HttpServletRequest.class);
    HttpSession mockHttpSession = Mockito.mock(HttpSession.class);

    ProjectMaterialService mockProjectMaterialService = Mockito.mock(ProjectMaterialService.class);

    ArgumentCaptor<CommandDTO> projectDetailCommandDTOCaptor = ArgumentCaptor.forClass(CommandDTO.class);
    ArgumentCaptor<CommandDTO> saveProjectDetailCommandDTOCaptor = ArgumentCaptor.forClass(CommandDTO.class);

    ProjectDetailMaterialController projectDetailMaterialController = new ProjectDetailMaterialController();
    projectDetailMaterialController.projectDetailService = mockProjectMaterialService;

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
    Mockito.when(mockProjectMaterialService.getProjectMaterials(Mockito.any())).thenReturn(responseCommandDTO);
    Mockito.when(mockProjectMaterialService.saveProjectBudget(Mockito.any())).thenReturn(responseCommandDTO);
    Mockito.when(mockModel.addAttribute(ModelConstants.MODEL_PROJECT_DETAIL, projectDetailRepresentation)).thenReturn(mockModel);
    Mockito.when(mockModel.addAttribute(Mockito.eq(ModelConstants.MODEL_ADD_PROJECT_MATERIAL), Mockito.any(ProjectDetailMaterialRepresentation.class))).thenReturn(mockModel);
    Mockito.when(mockModel.addAttribute(Mockito.eq(ModelConstants.MODEL_ADD_PROJECT_BUDGET), Mockito.any(ProjectDetailRepresentation.class))).thenReturn(mockModel);

    // Execute
    Exception expectedException = null;
    String result = null;
    try {
      result = projectDetailMaterialController.addProjectBudget(1L, projectDetailRepresentation, mockModel, mockHttpServletRequest);
    } catch (Exception exception) {
      expectedException = exception;
    }

    // Verify
    Mockito.verify(mockModel, Mockito.times(1)).addAttribute(SessionConstants.SESSION_LOGGED_IN_USER, loggedInUserRepresentation);
    Mockito.verify(mockProjectMaterialService, Mockito.times(1)).getProjectMaterials(projectDetailCommandDTOCaptor.capture());
    Mockito.verify(mockProjectMaterialService, Mockito.times(1)).saveProjectBudget(saveProjectDetailCommandDTOCaptor.capture());
    Mockito.verify(mockModel, Mockito.times(1)).addAttribute(ModelConstants.MODEL_PROJECT_DETAIL, projectDetailRepresentation);
    Mockito.verify(mockModel, Mockito.times(1)).addAttribute(Mockito.eq(ModelConstants.MODEL_ADD_PROJECT_MATERIAL), Mockito.any(ProjectDetailMaterialRepresentation.class));
    Mockito.verify(mockModel, Mockito.times(1)).addAttribute(Mockito.eq(ModelConstants.MODEL_ADD_PROJECT_BUDGET), Mockito.any(ProjectDetailRepresentation.class));

    // Assertions
    assert expectedException == null;
    assert (Long) projectDetailCommandDTOCaptor.getValue().get(ContextConstants.CONTEXT_PROJECT_ID) == 1L;
    assert saveProjectDetailCommandDTOCaptor.getValue().get(ContextConstants.CONTEXT_PROJECT_DETAIL) == projectDetailRepresentation;
    assert result.equals(ViewConstants.VIEW_MATERIAL);
  }

  @Test
  public void testAddProjectBudgetException() {
    // Initialize
    Model mockModel = Mockito.mock(Model.class);
    HttpServletRequest mockHttpServletRequest = Mockito.mock(HttpServletRequest.class);
    HttpSession mockHttpSession = Mockito.mock(HttpSession.class);

    ProjectMaterialService mockProjectMaterialService = Mockito.mock(ProjectMaterialService.class);

    ArgumentCaptor<CommandDTO> projectDetailCommandDTOCaptor = ArgumentCaptor.forClass(CommandDTO.class);
    ArgumentCaptor<CommandDTO> saveProjectDetailCommandDTOCaptor = ArgumentCaptor.forClass(CommandDTO.class);
    ArgumentCaptor<ErrorsRepresentation> errorsRepresentationCaptor = ArgumentCaptor.forClass(ErrorsRepresentation.class);

    ProjectDetailMaterialController projectDetailMaterialController = new ProjectDetailMaterialController();
    projectDetailMaterialController.projectDetailService = mockProjectMaterialService;

    LoggedInUserRepresentation loggedInUserRepresentation = LoggedInUserRepresentation.builder().userName("username").build();
    ProjectDetailRepresentation projectDetailRepresentation = ProjectDetailRepresentation.builder().id(1L).build();

    CommandDTO responseCommandDTO = CommandDTO
        .builder()
        .add(ContextConstants.CONTEXT_PROJECT_DETAIL, projectDetailRepresentation)
        .build();

    Map<String, String> fieldMessages = new HashMap<>();
    fieldMessages.put("field1", "message1");
    SaveProjectBudgetFailedException responseException = new SaveProjectBudgetFailedException();
    responseException.setFieldMessages(fieldMessages);
    responseException.setMessages(Collections.singletonList("message1"));

    // Define Mocks
    Mockito.when(mockHttpServletRequest.getSession()).thenReturn(mockHttpSession);
    Mockito.when(mockHttpSession.getAttribute(SessionConstants.SESSION_LOGGED_IN_USER)).thenReturn(loggedInUserRepresentation);
    Mockito.when(mockModel.addAttribute(SessionConstants.SESSION_LOGGED_IN_USER, loggedInUserRepresentation)).thenReturn(mockModel);
    Mockito.when(mockProjectMaterialService.getProjectMaterials(Mockito.any())).thenReturn(responseCommandDTO);
    Mockito.when(mockProjectMaterialService.saveProjectBudget(Mockito.any())).thenThrow(responseException);
    Mockito.when(mockModel.addAttribute(ModelConstants.MODEL_PROJECT_DETAIL, projectDetailRepresentation)).thenReturn(mockModel);
    Mockito.when(mockModel.addAttribute(Mockito.eq(ModelConstants.MODEL_ADD_PROJECT_MATERIAL), Mockito.any(ProjectDetailMaterialRepresentation.class))).thenReturn(mockModel);
    Mockito.when(mockModel.addAttribute(Mockito.eq(ModelConstants.MODEL_ADD_PROJECT_BUDGET), Mockito.any(ProjectDetailRepresentation.class))).thenReturn(mockModel);
    Mockito.when(mockModel.addAttribute(Mockito.eq(ModelConstants.MODEL_ADD_PROJECT_BUDGET_ERRORS), Mockito.any(ErrorsRepresentation.class))).thenReturn(mockModel);

    // Execute
    Exception expectedException = null;
    String result = null;
    try {
      result = projectDetailMaterialController.addProjectBudget(1L, projectDetailRepresentation, mockModel, mockHttpServletRequest);
    } catch (Exception exception) {
      expectedException = exception;
    }

    // Verify
    Mockito.verify(mockModel, Mockito.times(1)).addAttribute(SessionConstants.SESSION_LOGGED_IN_USER, loggedInUserRepresentation);
    Mockito.verify(mockProjectMaterialService, Mockito.times(1)).getProjectMaterials(projectDetailCommandDTOCaptor.capture());
    Mockito.verify(mockProjectMaterialService, Mockito.times(1)).saveProjectBudget(saveProjectDetailCommandDTOCaptor.capture());
    Mockito.verify(mockModel, Mockito.times(1)).addAttribute(ModelConstants.MODEL_PROJECT_DETAIL, projectDetailRepresentation);
    Mockito.verify(mockModel, Mockito.times(1)).addAttribute(Mockito.eq(ModelConstants.MODEL_ADD_PROJECT_MATERIAL), Mockito.any(ProjectDetailMaterialRepresentation.class));
    Mockito.verify(mockModel, Mockito.times(2)).addAttribute(Mockito.eq(ModelConstants.MODEL_ADD_PROJECT_BUDGET), Mockito.any(ProjectDetailRepresentation.class));
    Mockito.verify(mockModel, Mockito.times(1)).addAttribute(Mockito.eq(ModelConstants.MODEL_ADD_PROJECT_BUDGET_ERRORS), errorsRepresentationCaptor.capture());

    // Assertions
    assert expectedException == null;
    assert (Long) projectDetailCommandDTOCaptor.getValue().get(ContextConstants.CONTEXT_PROJECT_ID) == 1L;
    assert saveProjectDetailCommandDTOCaptor.getValue().get(ContextConstants.CONTEXT_PROJECT_DETAIL) == projectDetailRepresentation;
    assert result.equals(ViewConstants.VIEW_MATERIAL);
    assert errorsRepresentationCaptor.getValue().getErrorMessages().get(0).equals("message1");
    assert errorsRepresentationCaptor.getValue().getFieldErrorMessages().get("field1").equals("message1");
  }

  @Test
  public void testDeleteProjectBudget() {
    // Initialize
    Model mockModel = Mockito.mock(Model.class);
    HttpServletRequest mockHttpServletRequest = Mockito.mock(HttpServletRequest.class);
    HttpSession mockHttpSession = Mockito.mock(HttpSession.class);

    ProjectMaterialService mockProjectMaterialService = Mockito.mock(ProjectMaterialService.class);

    ArgumentCaptor<CommandDTO> projectDetailCommandDTOCaptor = ArgumentCaptor.forClass(CommandDTO.class);
    ArgumentCaptor<CommandDTO> deleteProjectDetailCommandDTOCaptor = ArgumentCaptor.forClass(CommandDTO.class);

    ProjectDetailMaterialController projectDetailMaterialController = new ProjectDetailMaterialController();
    projectDetailMaterialController.projectDetailService = mockProjectMaterialService;

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
    Mockito.when(mockProjectMaterialService.getProjectMaterials(Mockito.any())).thenReturn(responseCommandDTO);
    Mockito.when(mockProjectMaterialService.deleteProjectBudget(Mockito.any())).thenReturn(responseCommandDTO);
    Mockito.when(mockModel.addAttribute(ModelConstants.MODEL_PROJECT_DETAIL, projectDetailRepresentation)).thenReturn(mockModel);
    Mockito.when(mockModel.addAttribute(Mockito.eq(ModelConstants.MODEL_ADD_PROJECT_MATERIAL), Mockito.any(ProjectDetailMaterialRepresentation.class))).thenReturn(mockModel);
    Mockito.when(mockModel.addAttribute(Mockito.eq(ModelConstants.MODEL_ADD_PROJECT_BUDGET), Mockito.any(ProjectDetailRepresentation.class))).thenReturn(mockModel);

    // Execute
    Exception expectedException = null;
    String result = null;
    try {
      result = projectDetailMaterialController.deleteProjectBudget(1L, mockModel, mockHttpServletRequest);
    } catch (Exception exception) {
      expectedException = exception;
    }

    // Verify
    Mockito.verify(mockModel, Mockito.times(1)).addAttribute(SessionConstants.SESSION_LOGGED_IN_USER, loggedInUserRepresentation);
    Mockito.verify(mockProjectMaterialService, Mockito.times(1)).getProjectMaterials(projectDetailCommandDTOCaptor.capture());
    Mockito.verify(mockProjectMaterialService, Mockito.times(1)).deleteProjectBudget(deleteProjectDetailCommandDTOCaptor.capture());
    Mockito.verify(mockModel, Mockito.times(1)).addAttribute(ModelConstants.MODEL_PROJECT_DETAIL, projectDetailRepresentation);
    Mockito.verify(mockModel, Mockito.times(1)).addAttribute(Mockito.eq(ModelConstants.MODEL_ADD_PROJECT_MATERIAL), Mockito.any(ProjectDetailMaterialRepresentation.class));
    Mockito.verify(mockModel, Mockito.times(1)).addAttribute(Mockito.eq(ModelConstants.MODEL_ADD_PROJECT_BUDGET), Mockito.any(ProjectDetailRepresentation.class));

    // Assertions
    assert expectedException == null;
    assert (Long) projectDetailCommandDTOCaptor.getValue().get(ContextConstants.CONTEXT_PROJECT_ID) == 1L;
    assert (Long) deleteProjectDetailCommandDTOCaptor.getValue().get(ContextConstants.CONTEXT_PROJECT_ID) == 1L;
    assert result.equals(ViewConstants.VIEW_MATERIAL);
  }

  @Test
  public void testDeleteProjectBudgetException() {
    // Initialize
    Model mockModel = Mockito.mock(Model.class);
    HttpServletRequest mockHttpServletRequest = Mockito.mock(HttpServletRequest.class);
    HttpSession mockHttpSession = Mockito.mock(HttpSession.class);

    ProjectMaterialService mockProjectMaterialService = Mockito.mock(ProjectMaterialService.class);

    ArgumentCaptor<CommandDTO> projectDetailCommandDTOCaptor = ArgumentCaptor.forClass(CommandDTO.class);
    ArgumentCaptor<CommandDTO> deleteProjectDetailCommandDTOCaptor = ArgumentCaptor.forClass(CommandDTO.class);
    ArgumentCaptor<ErrorsRepresentation> errorsRepresentationCaptor = ArgumentCaptor.forClass(ErrorsRepresentation.class);

    ProjectDetailMaterialController projectDetailMaterialController = new ProjectDetailMaterialController();
    projectDetailMaterialController.projectDetailService = mockProjectMaterialService;

    LoggedInUserRepresentation loggedInUserRepresentation = LoggedInUserRepresentation.builder().userName("username").build();
    ProjectDetailRepresentation projectDetailRepresentation = ProjectDetailRepresentation.builder().id(1L).build();

    CommandDTO responseCommandDTO = CommandDTO
        .builder()
        .add(ContextConstants.CONTEXT_PROJECT_DETAIL, projectDetailRepresentation)
        .build();

    Map<String, String> fieldMessages = new HashMap<>();
    fieldMessages.put("field1", "message1");
    SaveProjectBudgetFailedException responseException = new SaveProjectBudgetFailedException();
    responseException.setFieldMessages(fieldMessages);
    responseException.setMessages(Collections.singletonList("message1"));

    // Define Mocks
    Mockito.when(mockHttpServletRequest.getSession()).thenReturn(mockHttpSession);
    Mockito.when(mockHttpSession.getAttribute(SessionConstants.SESSION_LOGGED_IN_USER)).thenReturn(loggedInUserRepresentation);
    Mockito.when(mockModel.addAttribute(SessionConstants.SESSION_LOGGED_IN_USER, loggedInUserRepresentation)).thenReturn(mockModel);
    Mockito.when(mockProjectMaterialService.getProjectMaterials(Mockito.any())).thenReturn(responseCommandDTO);
    Mockito.when(mockProjectMaterialService.deleteProjectBudget(Mockito.any())).thenThrow(responseException);
    Mockito.when(mockModel.addAttribute(ModelConstants.MODEL_PROJECT_DETAIL, projectDetailRepresentation)).thenReturn(mockModel);
    Mockito.when(mockModel.addAttribute(Mockito.eq(ModelConstants.MODEL_ADD_PROJECT_MATERIAL), Mockito.any(ProjectDetailMaterialRepresentation.class))).thenReturn(mockModel);
    Mockito.when(mockModel.addAttribute(Mockito.eq(ModelConstants.MODEL_ADD_PROJECT_BUDGET), Mockito.any(ProjectDetailRepresentation.class))).thenReturn(mockModel);
    Mockito.when(mockModel.addAttribute(Mockito.eq(ModelConstants.MODEL_ADD_PROJECT_BUDGET_ERRORS), Mockito.any(ErrorsRepresentation.class))).thenReturn(mockModel);

    // Execute
    Exception expectedException = null;
    String result = null;
    try {
      result = projectDetailMaterialController.deleteProjectBudget(1L, mockModel, mockHttpServletRequest);
    } catch (Exception exception) {
      expectedException = exception;
    }

    // Verify
    Mockito.verify(mockModel, Mockito.times(1)).addAttribute(SessionConstants.SESSION_LOGGED_IN_USER, loggedInUserRepresentation);
    Mockito.verify(mockProjectMaterialService, Mockito.times(1)).getProjectMaterials(projectDetailCommandDTOCaptor.capture());
    Mockito.verify(mockProjectMaterialService, Mockito.times(1)).deleteProjectBudget(deleteProjectDetailCommandDTOCaptor.capture());
    Mockito.verify(mockModel, Mockito.times(1)).addAttribute(ModelConstants.MODEL_PROJECT_DETAIL, projectDetailRepresentation);
    Mockito.verify(mockModel, Mockito.times(1)).addAttribute(Mockito.eq(ModelConstants.MODEL_ADD_PROJECT_MATERIAL), Mockito.any(ProjectDetailMaterialRepresentation.class));
    Mockito.verify(mockModel, Mockito.times(1)).addAttribute(Mockito.eq(ModelConstants.MODEL_ADD_PROJECT_BUDGET), Mockito.any(ProjectDetailRepresentation.class));
    Mockito.verify(mockModel, Mockito.times(1)).addAttribute(Mockito.eq(ModelConstants.MODEL_ADD_PROJECT_BUDGET_ERRORS), errorsRepresentationCaptor.capture());

    // Assertions
    assert expectedException == null;
    assert (Long) projectDetailCommandDTOCaptor.getValue().get(ContextConstants.CONTEXT_PROJECT_ID) == 1L;
    assert (Long) deleteProjectDetailCommandDTOCaptor.getValue().get(ContextConstants.CONTEXT_PROJECT_ID) == 1L;
    assert result.equals(ViewConstants.VIEW_MATERIAL);
    assert errorsRepresentationCaptor.getValue().getErrorMessages().get(0).equals("message1");
    assert errorsRepresentationCaptor.getValue().getFieldErrorMessages().get("field1").equals("message1");
  }

  @Test
  public void testEditProjectBudget() {
    // Initialize
    Model mockModel = Mockito.mock(Model.class);
    HttpServletRequest mockHttpServletRequest = Mockito.mock(HttpServletRequest.class);
    HttpSession mockHttpSession = Mockito.mock(HttpSession.class);

    ProjectMaterialService mockProjectMaterialService = Mockito.mock(ProjectMaterialService.class);

    ArgumentCaptor<CommandDTO> projectDetailCommandDTOCaptor = ArgumentCaptor.forClass(CommandDTO.class);

    ProjectDetailMaterialController projectDetailMaterialController = new ProjectDetailMaterialController();
    projectDetailMaterialController.projectDetailService = mockProjectMaterialService;

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
    Mockito.when(mockProjectMaterialService.getProjectMaterials(Mockito.any())).thenReturn(responseCommandDTO);
    Mockito.when(mockModel.addAttribute(ModelConstants.MODEL_PROJECT_DETAIL, projectDetailRepresentation)).thenReturn(mockModel);
    Mockito.when(mockModel.addAttribute(Mockito.eq(ModelConstants.MODEL_ADD_PROJECT_MATERIAL), Mockito.any(ProjectDetailMaterialRepresentation.class))).thenReturn(mockModel);
    Mockito.when(mockModel.addAttribute(Mockito.eq(ModelConstants.MODEL_ADD_PROJECT_BUDGET), Mockito.any(ProjectDetailRepresentation.class))).thenReturn(mockModel);
    Mockito.when(mockModel.addAttribute(Mockito.eq(ModelConstants.MODEL_EDIT_PROJECT_BUDGET), Mockito.any())).thenReturn(mockModel);

    // Execute
    Exception expectedException = null;
    String result = null;
    try {
      result = projectDetailMaterialController.editProjectBudget(1L, mockModel, mockHttpServletRequest);
    } catch (Exception exception) {
      expectedException = exception;
    }

    // Verify
    Mockito.verify(mockModel, Mockito.times(1)).addAttribute(SessionConstants.SESSION_LOGGED_IN_USER, loggedInUserRepresentation);
    Mockito.verify(mockProjectMaterialService, Mockito.times(1)).getProjectMaterials(projectDetailCommandDTOCaptor.capture());
    Mockito.verify(mockModel, Mockito.times(1)).addAttribute(ModelConstants.MODEL_PROJECT_DETAIL, projectDetailRepresentation);
    Mockito.verify(mockModel, Mockito.times(1)).addAttribute(Mockito.eq(ModelConstants.MODEL_ADD_PROJECT_MATERIAL), Mockito.any(ProjectDetailMaterialRepresentation.class));
    Mockito.verify(mockModel, Mockito.times(1)).addAttribute(Mockito.eq(ModelConstants.MODEL_ADD_PROJECT_BUDGET), Mockito.any(ProjectDetailRepresentation.class));
    Mockito.verify(mockModel, Mockito.times(1)).addAttribute(Mockito.eq(ModelConstants.MODEL_EDIT_PROJECT_BUDGET), Mockito.any());

    // Assertions
    assert expectedException == null;
    assert (Long) projectDetailCommandDTOCaptor.getValue().get(ContextConstants.CONTEXT_PROJECT_ID) == 1L;
    assert result.equals(ViewConstants.VIEW_MATERIAL);
  }

  @Test
  public void testAddProjectMaterial() {
    // Initialize
    Model mockModel = Mockito.mock(Model.class);
    HttpServletRequest mockHttpServletRequest = Mockito.mock(HttpServletRequest.class);
    HttpSession mockHttpSession = Mockito.mock(HttpSession.class);

    ProjectMaterialService mockProjectMaterialService = Mockito.mock(ProjectMaterialService.class);

    ArgumentCaptor<CommandDTO> projectDetailCommandDTOCaptor = ArgumentCaptor.forClass(CommandDTO.class);
    ArgumentCaptor<CommandDTO> saveProjectMaterialCommandDTOCaptor = ArgumentCaptor.forClass(CommandDTO.class);

    ProjectDetailMaterialController projectDetailMaterialController = new ProjectDetailMaterialController();
    projectDetailMaterialController.projectDetailService = mockProjectMaterialService;

    LoggedInUserRepresentation loggedInUserRepresentation = LoggedInUserRepresentation.builder().userName("username").build();
    ProjectDetailRepresentation projectDetailRepresentation = ProjectDetailRepresentation.builder().id(1L).build();
    ProjectDetailMaterialRepresentation projectDetailMaterialRepresentation = ProjectDetailMaterialRepresentation.builder().id(1L).build();

    CommandDTO responseCommandDTO = CommandDTO
        .builder()
        .add(ContextConstants.CONTEXT_PROJECT_DETAIL, projectDetailRepresentation)
        .build();

    // Define Mocks
    Mockito.when(mockHttpServletRequest.getSession()).thenReturn(mockHttpSession);
    Mockito.when(mockHttpSession.getAttribute(SessionConstants.SESSION_LOGGED_IN_USER)).thenReturn(loggedInUserRepresentation);
    Mockito.when(mockModel.addAttribute(SessionConstants.SESSION_LOGGED_IN_USER, loggedInUserRepresentation)).thenReturn(mockModel);
    Mockito.when(mockProjectMaterialService.getProjectMaterials(Mockito.any())).thenReturn(responseCommandDTO);
    Mockito.when(mockProjectMaterialService.saveProjectMaterial(Mockito.any())).thenReturn(responseCommandDTO);
    Mockito.when(mockModel.addAttribute(ModelConstants.MODEL_PROJECT_DETAIL, projectDetailRepresentation)).thenReturn(mockModel);
    Mockito.when(mockModel.addAttribute(Mockito.eq(ModelConstants.MODEL_ADD_PROJECT_MATERIAL), Mockito.any(ProjectDetailMaterialRepresentation.class))).thenReturn(mockModel);
    Mockito.when(mockModel.addAttribute(Mockito.eq(ModelConstants.MODEL_ADD_PROJECT_BUDGET), Mockito.any(ProjectDetailRepresentation.class))).thenReturn(mockModel);

    // Execute
    Exception expectedException = null;
    String result = null;
    try {
      result = projectDetailMaterialController.addProjectMaterial(projectDetailMaterialRepresentation, mockModel, mockHttpServletRequest, 1L);
    } catch (Exception exception) {
      expectedException = exception;
    }

    // Verify
    Mockito.verify(mockModel, Mockito.times(1)).addAttribute(SessionConstants.SESSION_LOGGED_IN_USER, loggedInUserRepresentation);
    Mockito.verify(mockProjectMaterialService, Mockito.times(1)).getProjectMaterials(projectDetailCommandDTOCaptor.capture());
    Mockito.verify(mockProjectMaterialService, Mockito.times(1)).saveProjectMaterial(saveProjectMaterialCommandDTOCaptor.capture());
    Mockito.verify(mockModel, Mockito.times(1)).addAttribute(ModelConstants.MODEL_PROJECT_DETAIL, projectDetailRepresentation);
    Mockito.verify(mockModel, Mockito.times(1)).addAttribute(Mockito.eq(ModelConstants.MODEL_ADD_PROJECT_MATERIAL), Mockito.any(ProjectDetailMaterialRepresentation.class));
    Mockito.verify(mockModel, Mockito.times(1)).addAttribute(Mockito.eq(ModelConstants.MODEL_ADD_PROJECT_BUDGET), Mockito.any(ProjectDetailRepresentation.class));

    // Assertions
    assert expectedException == null;
    assert (Long) projectDetailCommandDTOCaptor.getValue().get(ContextConstants.CONTEXT_PROJECT_ID) == 1L;
    assert (Long) saveProjectMaterialCommandDTOCaptor.getValue().get(ContextConstants.CONTEXT_PROJECT_ID) == 1L;
    assert saveProjectMaterialCommandDTOCaptor.getValue().get(ContextConstants.CONTEXT_PROJECT_MATERIAL) == projectDetailMaterialRepresentation;
    assert result.equals(ViewConstants.VIEW_MATERIAL);
  }

  @Test
  public void testAddProjectMaterialSaveProjectMaterialFailedException() {
    // Initialize
    Model mockModel = Mockito.mock(Model.class);
    HttpServletRequest mockHttpServletRequest = Mockito.mock(HttpServletRequest.class);
    HttpSession mockHttpSession = Mockito.mock(HttpSession.class);

    ProjectMaterialService mockProjectMaterialService = Mockito.mock(ProjectMaterialService.class);

    ArgumentCaptor<CommandDTO> projectDetailCommandDTOCaptor = ArgumentCaptor.forClass(CommandDTO.class);
    ArgumentCaptor<CommandDTO> saveProjectMaterialCommandDTOCaptor = ArgumentCaptor.forClass(CommandDTO.class);
    ArgumentCaptor<ErrorsRepresentation> errorsRepresentationCaptor = ArgumentCaptor.forClass(ErrorsRepresentation.class);


    ProjectDetailMaterialController projectDetailMaterialController = new ProjectDetailMaterialController();
    projectDetailMaterialController.projectDetailService = mockProjectMaterialService;

    LoggedInUserRepresentation loggedInUserRepresentation = LoggedInUserRepresentation.builder().userName("username").build();
    ProjectDetailRepresentation projectDetailRepresentation = ProjectDetailRepresentation.builder().id(1L).build();
    ProjectDetailMaterialRepresentation projectDetailMaterialRepresentation = ProjectDetailMaterialRepresentation.builder().id(1L).build();

    CommandDTO responseCommandDTO = CommandDTO
        .builder()
        .add(ContextConstants.CONTEXT_PROJECT_DETAIL, projectDetailRepresentation)
        .build();

    Map<String, String> fieldMessages = new HashMap<>();
    fieldMessages.put("field1", "message1");
    SaveProjectMaterialFailedException responseException = new SaveProjectMaterialFailedException();
    responseException.setFieldMessages(fieldMessages);
    responseException.setMessages(Collections.singletonList("message1"));

    // Define Mocks
    Mockito.when(mockHttpServletRequest.getSession()).thenReturn(mockHttpSession);
    Mockito.when(mockHttpSession.getAttribute(SessionConstants.SESSION_LOGGED_IN_USER)).thenReturn(loggedInUserRepresentation);
    Mockito.when(mockModel.addAttribute(SessionConstants.SESSION_LOGGED_IN_USER, loggedInUserRepresentation)).thenReturn(mockModel);
    Mockito.when(mockProjectMaterialService.getProjectMaterials(Mockito.any())).thenReturn(responseCommandDTO);
    Mockito.when(mockProjectMaterialService.saveProjectMaterial(Mockito.any())).thenThrow(responseException);
    Mockito.when(mockModel.addAttribute(ModelConstants.MODEL_PROJECT_DETAIL, projectDetailRepresentation)).thenReturn(mockModel);
    Mockito.when(mockModel.addAttribute(Mockito.eq(ModelConstants.MODEL_ADD_PROJECT_MATERIAL), Mockito.any(ProjectDetailMaterialRepresentation.class))).thenReturn(mockModel);
    Mockito.when(mockModel.addAttribute(Mockito.eq(ModelConstants.MODEL_ADD_PROJECT_BUDGET), Mockito.any(ProjectDetailRepresentation.class))).thenReturn(mockModel);
    Mockito.when(mockModel.addAttribute(Mockito.eq(ModelConstants.MODEL_ADD_PROJECT_MATERIAL_ERRORS), Mockito.any(ErrorsRepresentation.class))).thenReturn(mockModel);

    // Execute
    Exception expectedException = null;
    String result = null;
    try {
      result = projectDetailMaterialController.addProjectMaterial(projectDetailMaterialRepresentation, mockModel, mockHttpServletRequest, 1L);
    } catch (Exception exception) {
      expectedException = exception;
    }

    // Verify
    Mockito.verify(mockModel, Mockito.times(1)).addAttribute(SessionConstants.SESSION_LOGGED_IN_USER, loggedInUserRepresentation);
    Mockito.verify(mockProjectMaterialService, Mockito.times(1)).getProjectMaterials(projectDetailCommandDTOCaptor.capture());
    Mockito.verify(mockProjectMaterialService, Mockito.times(1)).saveProjectMaterial(saveProjectMaterialCommandDTOCaptor.capture());
    Mockito.verify(mockModel, Mockito.times(1)).addAttribute(ModelConstants.MODEL_PROJECT_DETAIL, projectDetailRepresentation);
    Mockito.verify(mockModel, Mockito.times(2)).addAttribute(Mockito.eq(ModelConstants.MODEL_ADD_PROJECT_MATERIAL), Mockito.any(ProjectDetailMaterialRepresentation.class));
    Mockito.verify(mockModel, Mockito.times(1)).addAttribute(Mockito.eq(ModelConstants.MODEL_ADD_PROJECT_BUDGET), Mockito.any(ProjectDetailRepresentation.class));
    Mockito.verify(mockModel, Mockito.times(1)).addAttribute(Mockito.eq(ModelConstants.MODEL_ADD_PROJECT_MATERIAL_ERRORS), errorsRepresentationCaptor.capture());

    // Assertions
    assert expectedException == null;
    assert (Long) projectDetailCommandDTOCaptor.getValue().get(ContextConstants.CONTEXT_PROJECT_ID) == 1L;
    assert (Long) saveProjectMaterialCommandDTOCaptor.getValue().get(ContextConstants.CONTEXT_PROJECT_ID) == 1L;
    assert saveProjectMaterialCommandDTOCaptor.getValue().get(ContextConstants.CONTEXT_PROJECT_MATERIAL) == projectDetailMaterialRepresentation;
    assert result.equals(ViewConstants.VIEW_MATERIAL);
    assert errorsRepresentationCaptor.getValue().getErrorMessages().get(0).equals("message1");
    assert errorsRepresentationCaptor.getValue().getFieldErrorMessages().get("field1").equals("message1");
  }

  @Test
  public void testAddProjectMaterialGetProjectDetailFailedException() {
    // Initialize
    Model mockModel = Mockito.mock(Model.class);
    HttpServletRequest mockHttpServletRequest = Mockito.mock(HttpServletRequest.class);
    HttpSession mockHttpSession = Mockito.mock(HttpSession.class);

    ProjectMaterialService mockProjectMaterialService = Mockito.mock(ProjectMaterialService.class);

    ArgumentCaptor<CommandDTO> projectDetailCommandDTOCaptor = ArgumentCaptor.forClass(CommandDTO.class);
    ArgumentCaptor<CommandDTO> saveProjectMaterialCommandDTOCaptor = ArgumentCaptor.forClass(CommandDTO.class);
    ArgumentCaptor<ErrorsRepresentation> errorsRepresentationCaptor = ArgumentCaptor.forClass(ErrorsRepresentation.class);


    ProjectDetailMaterialController projectDetailMaterialController = new ProjectDetailMaterialController();
    projectDetailMaterialController.projectDetailService = mockProjectMaterialService;

    LoggedInUserRepresentation loggedInUserRepresentation = LoggedInUserRepresentation.builder().userName("username").build();
    ProjectDetailRepresentation projectDetailRepresentation = ProjectDetailRepresentation.builder().id(1L).build();
    ProjectDetailMaterialRepresentation projectDetailMaterialRepresentation = ProjectDetailMaterialRepresentation.builder().id(1L).build();

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
    Mockito.when(mockProjectMaterialService.getProjectMaterials(Mockito.any())).thenReturn(responseCommandDTO);
    Mockito.when(mockProjectMaterialService.saveProjectMaterial(Mockito.any())).thenThrow(responseException);
    Mockito.when(mockModel.addAttribute(ModelConstants.MODEL_PROJECT_DETAIL, projectDetailRepresentation)).thenReturn(mockModel);
    Mockito.when(mockModel.addAttribute(Mockito.eq(ModelConstants.MODEL_ADD_PROJECT_MATERIAL), Mockito.any(ProjectDetailMaterialRepresentation.class))).thenReturn(mockModel);
    Mockito.when(mockModel.addAttribute(Mockito.eq(ModelConstants.MODEL_ADD_PROJECT_BUDGET), Mockito.any(ProjectDetailRepresentation.class))).thenReturn(mockModel);
    Mockito.when(mockModel.addAttribute(Mockito.eq(ModelConstants.MODEL_ADD_PROJECT_MATERIAL_ERRORS), Mockito.any(ErrorsRepresentation.class))).thenReturn(mockModel);

    // Execute
    Exception expectedException = null;
    String result = null;
    try {
      result = projectDetailMaterialController.addProjectMaterial(projectDetailMaterialRepresentation, mockModel, mockHttpServletRequest, 1L);
    } catch (Exception exception) {
      expectedException = exception;
    }

    // Verify
    Mockito.verify(mockModel, Mockito.times(1)).addAttribute(SessionConstants.SESSION_LOGGED_IN_USER, loggedInUserRepresentation);
    Mockito.verify(mockProjectMaterialService, Mockito.times(1)).getProjectMaterials(projectDetailCommandDTOCaptor.capture());
    Mockito.verify(mockProjectMaterialService, Mockito.times(1)).saveProjectMaterial(saveProjectMaterialCommandDTOCaptor.capture());
    Mockito.verify(mockModel, Mockito.times(1)).addAttribute(ModelConstants.MODEL_PROJECT_DETAIL, projectDetailRepresentation);
    Mockito.verify(mockModel, Mockito.times(2)).addAttribute(Mockito.eq(ModelConstants.MODEL_ADD_PROJECT_MATERIAL), Mockito.any(ProjectDetailMaterialRepresentation.class));
    Mockito.verify(mockModel, Mockito.times(1)).addAttribute(Mockito.eq(ModelConstants.MODEL_ADD_PROJECT_BUDGET), Mockito.any(ProjectDetailRepresentation.class));
    Mockito.verify(mockModel, Mockito.times(1)).addAttribute(Mockito.eq(ModelConstants.MODEL_ADD_PROJECT_MATERIAL_ERRORS), errorsRepresentationCaptor.capture());

    // Assertions
    assert expectedException == null;
    assert (Long) projectDetailCommandDTOCaptor.getValue().get(ContextConstants.CONTEXT_PROJECT_ID) == 1L;
    assert (Long) saveProjectMaterialCommandDTOCaptor.getValue().get(ContextConstants.CONTEXT_PROJECT_ID) == 1L;
    assert saveProjectMaterialCommandDTOCaptor.getValue().get(ContextConstants.CONTEXT_PROJECT_MATERIAL) == projectDetailMaterialRepresentation;
    assert result.equals(ViewConstants.VIEW_MATERIAL);
    assert errorsRepresentationCaptor.getValue().getErrorMessages().get(0).equals("message1");
    assert errorsRepresentationCaptor.getValue().getFieldErrorMessages().get("field1").equals("message1");
  }

  @Test
  public void testEditProjectMaterial() {
    // Initialize
    Model mockModel = Mockito.mock(Model.class);
    HttpServletRequest mockHttpServletRequest = Mockito.mock(HttpServletRequest.class);
    HttpSession mockHttpSession = Mockito.mock(HttpSession.class);

    ProjectMaterialService mockProjectMaterialService = Mockito.mock(ProjectMaterialService.class);

    ArgumentCaptor<CommandDTO> projectDetailCommandDTOCaptor = ArgumentCaptor.forClass(CommandDTO.class);
    ArgumentCaptor<CommandDTO> updateProjectMaterialCommandDTOCaptor = ArgumentCaptor.forClass(CommandDTO.class);

    ProjectDetailMaterialController projectDetailMaterialController = new ProjectDetailMaterialController();
    projectDetailMaterialController.projectDetailService = mockProjectMaterialService;

    LoggedInUserRepresentation loggedInUserRepresentation = LoggedInUserRepresentation.builder().userName("username").build();
    ProjectDetailRepresentation projectDetailRepresentation = ProjectDetailRepresentation.builder().id(1L).build();
    ProjectDetailMaterialRepresentation projectDetailMaterialRepresentation = ProjectDetailMaterialRepresentation.builder().id(1L).build();

    CommandDTO responseCommandDTO = CommandDTO
        .builder()
        .add(ContextConstants.CONTEXT_PROJECT_DETAIL, projectDetailRepresentation)
        .build();

    // Define Mocks
    Mockito.when(mockHttpServletRequest.getSession()).thenReturn(mockHttpSession);
    Mockito.when(mockHttpSession.getAttribute(SessionConstants.SESSION_LOGGED_IN_USER)).thenReturn(loggedInUserRepresentation);
    Mockito.when(mockModel.addAttribute(SessionConstants.SESSION_LOGGED_IN_USER, loggedInUserRepresentation)).thenReturn(mockModel);
    Mockito.when(mockProjectMaterialService.getProjectMaterials(Mockito.any())).thenReturn(responseCommandDTO);
    Mockito.when(mockProjectMaterialService.updateProjectMaterial(Mockito.any())).thenReturn(responseCommandDTO);
    Mockito.when(mockModel.addAttribute(ModelConstants.MODEL_PROJECT_DETAIL, projectDetailRepresentation)).thenReturn(mockModel);
    Mockito.when(mockModel.addAttribute(Mockito.eq(ModelConstants.MODEL_ADD_PROJECT_MATERIAL), Mockito.any(ProjectDetailMaterialRepresentation.class))).thenReturn(mockModel);
    Mockito.when(mockModel.addAttribute(Mockito.eq(ModelConstants.MODEL_ADD_PROJECT_BUDGET), Mockito.any(ProjectDetailRepresentation.class))).thenReturn(mockModel);

    // Execute
    Exception expectedException = null;
    String result = null;
    try {
      result = projectDetailMaterialController.editProjectMaterial(projectDetailMaterialRepresentation, mockModel, mockHttpServletRequest, 1L);
    } catch (Exception exception) {
      expectedException = exception;
    }

    // Verify
    Mockito.verify(mockModel, Mockito.times(1)).addAttribute(SessionConstants.SESSION_LOGGED_IN_USER, loggedInUserRepresentation);
    Mockito.verify(mockProjectMaterialService, Mockito.times(1)).getProjectMaterials(projectDetailCommandDTOCaptor.capture());
    Mockito.verify(mockProjectMaterialService, Mockito.times(1)).updateProjectMaterial(updateProjectMaterialCommandDTOCaptor.capture());
    Mockito.verify(mockModel, Mockito.times(1)).addAttribute(ModelConstants.MODEL_PROJECT_DETAIL, projectDetailRepresentation);
    Mockito.verify(mockModel, Mockito.times(1)).addAttribute(Mockito.eq(ModelConstants.MODEL_ADD_PROJECT_MATERIAL), Mockito.any(ProjectDetailMaterialRepresentation.class));
    Mockito.verify(mockModel, Mockito.times(1)).addAttribute(Mockito.eq(ModelConstants.MODEL_ADD_PROJECT_BUDGET), Mockito.any(ProjectDetailRepresentation.class));

    // Assertions
    assert expectedException == null;
    assert (Long) projectDetailCommandDTOCaptor.getValue().get(ContextConstants.CONTEXT_PROJECT_ID) == 1L;
    assert (Long) updateProjectMaterialCommandDTOCaptor.getValue().get(ContextConstants.CONTEXT_PROJECT_ID) == 1L;
    assert updateProjectMaterialCommandDTOCaptor.getValue().get(ContextConstants.CONTEXT_PROJECT_MATERIAL) == projectDetailMaterialRepresentation;
    assert result.equals(ViewConstants.VIEW_MATERIAL);
  }

  @Test
  public void testEditProjectMaterialUpdateProjectMaterialFailedException() {
    // Initialize
    Model mockModel = Mockito.mock(Model.class);
    HttpServletRequest mockHttpServletRequest = Mockito.mock(HttpServletRequest.class);
    HttpSession mockHttpSession = Mockito.mock(HttpSession.class);

    ProjectMaterialService mockProjectMaterialService = Mockito.mock(ProjectMaterialService.class);

    ArgumentCaptor<CommandDTO> projectDetailCommandDTOCaptor = ArgumentCaptor.forClass(CommandDTO.class);
    ArgumentCaptor<CommandDTO> updateProjectMaterialCommandDTOCaptor = ArgumentCaptor.forClass(CommandDTO.class);
    ArgumentCaptor<ErrorsRepresentation> errorsRepresentationCaptor = ArgumentCaptor.forClass(ErrorsRepresentation.class);

    ProjectDetailMaterialController projectDetailMaterialController = new ProjectDetailMaterialController();
    projectDetailMaterialController.projectDetailService = mockProjectMaterialService;

    LoggedInUserRepresentation loggedInUserRepresentation = LoggedInUserRepresentation.builder().userName("username").build();
    ProjectDetailRepresentation projectDetailRepresentation = ProjectDetailRepresentation.builder().id(1L).build();
    ProjectDetailMaterialRepresentation projectDetailMaterialRepresentation = ProjectDetailMaterialRepresentation.builder().id(1L).build();

    CommandDTO responseCommandDTO = CommandDTO
        .builder()
        .add(ContextConstants.CONTEXT_PROJECT_DETAIL, projectDetailRepresentation)
        .build();

    Map<String, String> fieldMessages = new HashMap<>();
    fieldMessages.put("field1", "message1");
    UpdateProjectMaterialFailedException responseException = new UpdateProjectMaterialFailedException();
    responseException.setFieldMessages(fieldMessages);
    responseException.setMessages(Collections.singletonList("message1"));

    // Define Mocks
    Mockito.when(mockHttpServletRequest.getSession()).thenReturn(mockHttpSession);
    Mockito.when(mockHttpSession.getAttribute(SessionConstants.SESSION_LOGGED_IN_USER)).thenReturn(loggedInUserRepresentation);
    Mockito.when(mockModel.addAttribute(SessionConstants.SESSION_LOGGED_IN_USER, loggedInUserRepresentation)).thenReturn(mockModel);
    Mockito.when(mockProjectMaterialService.getProjectMaterials(Mockito.any())).thenReturn(responseCommandDTO);
    Mockito.when(mockProjectMaterialService.updateProjectMaterial(Mockito.any())).thenThrow(responseException);
    Mockito.when(mockModel.addAttribute(ModelConstants.MODEL_PROJECT_DETAIL, projectDetailRepresentation)).thenReturn(mockModel);
    Mockito.when(mockModel.addAttribute(Mockito.eq(ModelConstants.MODEL_ADD_PROJECT_MATERIAL), Mockito.any(ProjectDetailMaterialRepresentation.class))).thenReturn(mockModel);
    Mockito.when(mockModel.addAttribute(ModelConstants.MODEL_EDIT_PROJECT_MATERIAL, projectDetailMaterialRepresentation)).thenReturn(mockModel);
    Mockito.when(mockModel.addAttribute(Mockito.eq(ModelConstants.MODEL_ADD_PROJECT_BUDGET), Mockito.any(ProjectDetailRepresentation.class))).thenReturn(mockModel);
    Mockito.when(mockModel.addAttribute(Mockito.eq(ModelConstants.MODEL_ADD_PROJECT_MATERIAL_ERRORS), Mockito.any(ErrorsRepresentation.class))).thenReturn(mockModel);

    // Execute
    Exception expectedException = null;
    String result = null;
    try {
      result = projectDetailMaterialController.editProjectMaterial(projectDetailMaterialRepresentation, mockModel, mockHttpServletRequest, 1L);
    } catch (Exception exception) {
      expectedException = exception;
    }

    // Verify
    Mockito.verify(mockModel, Mockito.times(1)).addAttribute(SessionConstants.SESSION_LOGGED_IN_USER, loggedInUserRepresentation);
    Mockito.verify(mockProjectMaterialService, Mockito.times(1)).getProjectMaterials(projectDetailCommandDTOCaptor.capture());
    Mockito.verify(mockProjectMaterialService, Mockito.times(1)).updateProjectMaterial(updateProjectMaterialCommandDTOCaptor.capture());
    Mockito.verify(mockModel, Mockito.times(1)).addAttribute(ModelConstants.MODEL_PROJECT_DETAIL, projectDetailRepresentation);
    Mockito.verify(mockModel, Mockito.times(2)).addAttribute(Mockito.eq(ModelConstants.MODEL_ADD_PROJECT_MATERIAL), Mockito.any(ProjectDetailMaterialRepresentation.class));
    Mockito.verify(mockModel, Mockito.times(1)).addAttribute(ModelConstants.MODEL_EDIT_PROJECT_MATERIAL, projectDetailMaterialRepresentation);
    Mockito.verify(mockModel, Mockito.times(1)).addAttribute(Mockito.eq(ModelConstants.MODEL_ADD_PROJECT_BUDGET), Mockito.any(ProjectDetailRepresentation.class));
    Mockito.verify(mockModel, Mockito.times(1)).addAttribute(Mockito.eq(ModelConstants.MODEL_ADD_PROJECT_MATERIAL_ERRORS), errorsRepresentationCaptor.capture());

    // Assertions
    assert expectedException == null;
    assert (Long) projectDetailCommandDTOCaptor.getValue().get(ContextConstants.CONTEXT_PROJECT_ID) == 1L;
    assert (Long) updateProjectMaterialCommandDTOCaptor.getValue().get(ContextConstants.CONTEXT_PROJECT_ID) == 1L;
    assert updateProjectMaterialCommandDTOCaptor.getValue().get(ContextConstants.CONTEXT_PROJECT_MATERIAL) == projectDetailMaterialRepresentation;
    assert result.equals(ViewConstants.VIEW_MATERIAL);
    assert errorsRepresentationCaptor.getValue().getErrorMessages().get(0).equals("message1");
    assert errorsRepresentationCaptor.getValue().getFieldErrorMessages().get("field1").equals("message1");
  }

  @Test
  public void testEditProjectMaterialGetProjectDetailFailedException() {
    // Initialize
    Model mockModel = Mockito.mock(Model.class);
    HttpServletRequest mockHttpServletRequest = Mockito.mock(HttpServletRequest.class);
    HttpSession mockHttpSession = Mockito.mock(HttpSession.class);

    ProjectMaterialService mockProjectMaterialService = Mockito.mock(ProjectMaterialService.class);

    ArgumentCaptor<CommandDTO> projectDetailCommandDTOCaptor = ArgumentCaptor.forClass(CommandDTO.class);
    ArgumentCaptor<CommandDTO> updateProjectMaterialCommandDTOCaptor = ArgumentCaptor.forClass(CommandDTO.class);
    ArgumentCaptor<ErrorsRepresentation> errorsRepresentationCaptor = ArgumentCaptor.forClass(ErrorsRepresentation.class);

    ProjectDetailMaterialController projectDetailMaterialController = new ProjectDetailMaterialController();
    projectDetailMaterialController.projectDetailService = mockProjectMaterialService;

    LoggedInUserRepresentation loggedInUserRepresentation = LoggedInUserRepresentation.builder().userName("username").build();
    ProjectDetailRepresentation projectDetailRepresentation = ProjectDetailRepresentation.builder().id(1L).build();
    ProjectDetailMaterialRepresentation projectDetailMaterialRepresentation = ProjectDetailMaterialRepresentation.builder().id(1L).build();

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
    Mockito.when(mockProjectMaterialService.getProjectMaterials(Mockito.any())).thenReturn(responseCommandDTO);
    Mockito.when(mockProjectMaterialService.updateProjectMaterial(Mockito.any())).thenThrow(responseException);
    Mockito.when(mockModel.addAttribute(ModelConstants.MODEL_PROJECT_DETAIL, projectDetailRepresentation)).thenReturn(mockModel);
    Mockito.when(mockModel.addAttribute(Mockito.eq(ModelConstants.MODEL_ADD_PROJECT_MATERIAL), Mockito.any(ProjectDetailMaterialRepresentation.class))).thenReturn(mockModel);
    Mockito.when(mockModel.addAttribute(ModelConstants.MODEL_EDIT_PROJECT_MATERIAL, projectDetailMaterialRepresentation)).thenReturn(mockModel);
    Mockito.when(mockModel.addAttribute(Mockito.eq(ModelConstants.MODEL_ADD_PROJECT_BUDGET), Mockito.any(ProjectDetailRepresentation.class))).thenReturn(mockModel);
    Mockito.when(mockModel.addAttribute(Mockito.eq(ModelConstants.MODEL_ADD_PROJECT_MATERIAL_ERRORS), Mockito.any(ErrorsRepresentation.class))).thenReturn(mockModel);

    // Execute
    Exception expectedException = null;
    String result = null;
    try {
      result = projectDetailMaterialController.editProjectMaterial(projectDetailMaterialRepresentation, mockModel, mockHttpServletRequest, 1L);
    } catch (Exception exception) {
      expectedException = exception;
    }

    // Verify
    Mockito.verify(mockModel, Mockito.times(1)).addAttribute(SessionConstants.SESSION_LOGGED_IN_USER, loggedInUserRepresentation);
    Mockito.verify(mockProjectMaterialService, Mockito.times(1)).getProjectMaterials(projectDetailCommandDTOCaptor.capture());
    Mockito.verify(mockProjectMaterialService, Mockito.times(1)).updateProjectMaterial(updateProjectMaterialCommandDTOCaptor.capture());
    Mockito.verify(mockModel, Mockito.times(1)).addAttribute(ModelConstants.MODEL_PROJECT_DETAIL, projectDetailRepresentation);
    Mockito.verify(mockModel, Mockito.times(2)).addAttribute(Mockito.eq(ModelConstants.MODEL_ADD_PROJECT_MATERIAL), Mockito.any(ProjectDetailMaterialRepresentation.class));
    Mockito.verify(mockModel, Mockito.times(1)).addAttribute(ModelConstants.MODEL_EDIT_PROJECT_MATERIAL, projectDetailMaterialRepresentation);
    Mockito.verify(mockModel, Mockito.times(1)).addAttribute(Mockito.eq(ModelConstants.MODEL_ADD_PROJECT_BUDGET), Mockito.any(ProjectDetailRepresentation.class));
    Mockito.verify(mockModel, Mockito.times(1)).addAttribute(Mockito.eq(ModelConstants.MODEL_ADD_PROJECT_MATERIAL_ERRORS), errorsRepresentationCaptor.capture());

    // Assertions
    assert expectedException == null;
    assert (Long) projectDetailCommandDTOCaptor.getValue().get(ContextConstants.CONTEXT_PROJECT_ID) == 1L;
    assert (Long) updateProjectMaterialCommandDTOCaptor.getValue().get(ContextConstants.CONTEXT_PROJECT_ID) == 1L;
    assert updateProjectMaterialCommandDTOCaptor.getValue().get(ContextConstants.CONTEXT_PROJECT_MATERIAL) == projectDetailMaterialRepresentation;
    assert result.equals(ViewConstants.VIEW_MATERIAL);
    assert errorsRepresentationCaptor.getValue().getErrorMessages().get(0).equals("message1");
    assert errorsRepresentationCaptor.getValue().getFieldErrorMessages().get("field1").equals("message1");
  }

  @Test
  public void testEditProjectMaterialById() {
    // Initialize
    Model mockModel = Mockito.mock(Model.class);
    HttpServletRequest mockHttpServletRequest = Mockito.mock(HttpServletRequest.class);
    HttpSession mockHttpSession = Mockito.mock(HttpSession.class);

    ProjectMaterialService mockProjectMaterialService = Mockito.mock(ProjectMaterialService.class);

    ArgumentCaptor<CommandDTO> projectDetailCommandDTOCaptor = ArgumentCaptor.forClass(CommandDTO.class);
    ArgumentCaptor<CommandDTO> updateProjectMaterialCommandDTOCaptor = ArgumentCaptor.forClass(CommandDTO.class);

    ProjectDetailMaterialController projectDetailMaterialController = new ProjectDetailMaterialController();
    projectDetailMaterialController.projectDetailService = mockProjectMaterialService;

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
    Mockito.when(mockProjectMaterialService.getProjectMaterials(Mockito.any())).thenReturn(responseCommandDTO);
    Mockito.when(mockProjectMaterialService.getProjectMaterial(Mockito.any())).thenReturn(responseCommandDTO);
    Mockito.when(mockModel.addAttribute(ModelConstants.MODEL_PROJECT_DETAIL, projectDetailRepresentation)).thenReturn(mockModel);
    Mockito.when(mockModel.addAttribute(Mockito.eq(ModelConstants.MODEL_ADD_PROJECT_MATERIAL), Mockito.any(ProjectDetailMaterialRepresentation.class))).thenReturn(mockModel);
    Mockito.when(mockModel.addAttribute(Mockito.eq(ModelConstants.MODEL_ADD_PROJECT_BUDGET), Mockito.any(ProjectDetailRepresentation.class))).thenReturn(mockModel);

    // Execute
    Exception expectedException = null;
    String result = null;
    try {
      result = projectDetailMaterialController.editProjectMaterial(1L, 1L, mockModel, mockHttpServletRequest);
    } catch (Exception exception) {
      expectedException = exception;
    }

    // Verify
    Mockito.verify(mockModel, Mockito.times(1)).addAttribute(SessionConstants.SESSION_LOGGED_IN_USER, loggedInUserRepresentation);
    Mockito.verify(mockProjectMaterialService, Mockito.times(1)).getProjectMaterials(projectDetailCommandDTOCaptor.capture());
    Mockito.verify(mockProjectMaterialService, Mockito.times(1)).getProjectMaterial(updateProjectMaterialCommandDTOCaptor.capture());
    Mockito.verify(mockModel, Mockito.times(1)).addAttribute(ModelConstants.MODEL_PROJECT_DETAIL, projectDetailRepresentation);
    Mockito.verify(mockModel, Mockito.times(1)).addAttribute(Mockito.eq(ModelConstants.MODEL_ADD_PROJECT_MATERIAL), Mockito.any(ProjectDetailMaterialRepresentation.class));
    Mockito.verify(mockModel, Mockito.times(1)).addAttribute(Mockito.eq(ModelConstants.MODEL_ADD_PROJECT_BUDGET), Mockito.any(ProjectDetailRepresentation.class));

    // Assertions
    assert expectedException == null;
    assert (Long) projectDetailCommandDTOCaptor.getValue().get(ContextConstants.CONTEXT_PROJECT_ID) == 1L;
    assert (Long) updateProjectMaterialCommandDTOCaptor.getValue().get(ContextConstants.CONTEXT_MATERIAL_ID) == 1L;
    assert result.equals(ViewConstants.VIEW_MATERIAL);
  }

  @Test
  public void testEditProjectMaterialByIdException() {
    // Initialize
    Model mockModel = Mockito.mock(Model.class);
    HttpServletRequest mockHttpServletRequest = Mockito.mock(HttpServletRequest.class);
    HttpSession mockHttpSession = Mockito.mock(HttpSession.class);

    ProjectMaterialService mockProjectMaterialService = Mockito.mock(ProjectMaterialService.class);

    ArgumentCaptor<CommandDTO> projectDetailCommandDTOCaptor = ArgumentCaptor.forClass(CommandDTO.class);
    ArgumentCaptor<CommandDTO> updateProjectMaterialCommandDTOCaptor = ArgumentCaptor.forClass(CommandDTO.class);
    ArgumentCaptor<ErrorsRepresentation> errorsRepresentationCaptor = ArgumentCaptor.forClass(ErrorsRepresentation.class);

    ProjectDetailMaterialController projectDetailMaterialController = new ProjectDetailMaterialController();
    projectDetailMaterialController.projectDetailService = mockProjectMaterialService;

    LoggedInUserRepresentation loggedInUserRepresentation = LoggedInUserRepresentation.builder().userName("username").build();
    ProjectDetailRepresentation projectDetailRepresentation = ProjectDetailRepresentation.builder().id(1L).build();

    CommandDTO responseCommandDTO = CommandDTO
        .builder()
        .add(ContextConstants.CONTEXT_PROJECT_DETAIL, projectDetailRepresentation)
        .build();

    Map<String, String> fieldMessages = new HashMap<>();
    fieldMessages.put("field1", "message1");
    GetProjectMaterialFailedException responseException = new GetProjectMaterialFailedException();
    responseException.setFieldMessages(fieldMessages);
    responseException.setMessages(Collections.singletonList("message1"));

    // Define Mocks
    Mockito.when(mockHttpServletRequest.getSession()).thenReturn(mockHttpSession);
    Mockito.when(mockHttpSession.getAttribute(SessionConstants.SESSION_LOGGED_IN_USER)).thenReturn(loggedInUserRepresentation);
    Mockito.when(mockModel.addAttribute(SessionConstants.SESSION_LOGGED_IN_USER, loggedInUserRepresentation)).thenReturn(mockModel);
    Mockito.when(mockProjectMaterialService.getProjectMaterials(Mockito.any())).thenReturn(responseCommandDTO);
    Mockito.when(mockProjectMaterialService.getProjectMaterial(Mockito.any())).thenThrow(responseException);
    Mockito.when(mockModel.addAttribute(ModelConstants.MODEL_PROJECT_DETAIL, projectDetailRepresentation)).thenReturn(mockModel);
    Mockito.when(mockModel.addAttribute(Mockito.eq(ModelConstants.MODEL_ADD_PROJECT_MATERIAL), Mockito.any(ProjectDetailMaterialRepresentation.class))).thenReturn(mockModel);
    Mockito.when(mockModel.addAttribute(Mockito.eq(ModelConstants.MODEL_ADD_PROJECT_BUDGET), Mockito.any(ProjectDetailRepresentation.class))).thenReturn(mockModel);
    Mockito.when(mockModel.addAttribute(Mockito.eq(ModelConstants.MODEL_EDIT_PROJECT_MATERIAL_ERRORS), Mockito.any(ErrorsRepresentation.class))).thenReturn(mockModel);

    // Execute
    Exception expectedException = null;
    String result = null;
    try {
      result = projectDetailMaterialController.editProjectMaterial(1L, 1L, mockModel, mockHttpServletRequest);
    } catch (Exception exception) {
      expectedException = exception;
    }

    // Verify
    Mockito.verify(mockModel, Mockito.times(1)).addAttribute(SessionConstants.SESSION_LOGGED_IN_USER, loggedInUserRepresentation);
    Mockito.verify(mockProjectMaterialService, Mockito.times(1)).getProjectMaterials(projectDetailCommandDTOCaptor.capture());
    Mockito.verify(mockProjectMaterialService, Mockito.times(1)).getProjectMaterial(updateProjectMaterialCommandDTOCaptor.capture());
    Mockito.verify(mockModel, Mockito.times(1)).addAttribute(ModelConstants.MODEL_PROJECT_DETAIL, projectDetailRepresentation);
    Mockito.verify(mockModel, Mockito.times(1)).addAttribute(Mockito.eq(ModelConstants.MODEL_ADD_PROJECT_MATERIAL), Mockito.any(ProjectDetailMaterialRepresentation.class));
    Mockito.verify(mockModel, Mockito.times(1)).addAttribute(Mockito.eq(ModelConstants.MODEL_ADD_PROJECT_BUDGET), Mockito.any(ProjectDetailRepresentation.class));
    Mockito.verify(mockModel, Mockito.times(1)).addAttribute(Mockito.eq(ModelConstants.MODEL_EDIT_PROJECT_MATERIAL_ERRORS), errorsRepresentationCaptor.capture());

    // Assertions
    assert expectedException == null;
    assert (Long) projectDetailCommandDTOCaptor.getValue().get(ContextConstants.CONTEXT_PROJECT_ID) == 1L;
    assert (Long) updateProjectMaterialCommandDTOCaptor.getValue().get(ContextConstants.CONTEXT_MATERIAL_ID) == 1L;
    assert result.equals(ViewConstants.VIEW_MATERIAL);
    assert errorsRepresentationCaptor.getValue().getErrorMessages().get(0).equals("message1");
    assert errorsRepresentationCaptor.getValue().getFieldErrorMessages().get("field1").equals("message1");
  }

  @Test
  public void testDeleteProjectMaterialById() {
    // Initialize
    Model mockModel = Mockito.mock(Model.class);
    HttpServletRequest mockHttpServletRequest = Mockito.mock(HttpServletRequest.class);
    HttpSession mockHttpSession = Mockito.mock(HttpSession.class);

    ProjectMaterialService mockProjectMaterialService = Mockito.mock(ProjectMaterialService.class);

    ArgumentCaptor<CommandDTO> projectDetailCommandDTOCaptor = ArgumentCaptor.forClass(CommandDTO.class);
    ArgumentCaptor<CommandDTO> deleteProjectMaterialCommandDTOCaptor = ArgumentCaptor.forClass(CommandDTO.class);

    ProjectDetailMaterialController projectDetailMaterialController = new ProjectDetailMaterialController();
    projectDetailMaterialController.projectDetailService = mockProjectMaterialService;

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
    Mockito.when(mockProjectMaterialService.getProjectMaterials(Mockito.any())).thenReturn(responseCommandDTO);
    Mockito.when(mockProjectMaterialService.deleteProjectMaterial(Mockito.any())).thenReturn(responseCommandDTO);
    Mockito.when(mockModel.addAttribute(ModelConstants.MODEL_PROJECT_DETAIL, projectDetailRepresentation)).thenReturn(mockModel);
    Mockito.when(mockModel.addAttribute(Mockito.eq(ModelConstants.MODEL_ADD_PROJECT_MATERIAL), Mockito.any(ProjectDetailMaterialRepresentation.class))).thenReturn(mockModel);
    Mockito.when(mockModel.addAttribute(Mockito.eq(ModelConstants.MODEL_ADD_PROJECT_BUDGET), Mockito.any(ProjectDetailRepresentation.class))).thenReturn(mockModel);

    // Execute
    Exception expectedException = null;
    String result = null;
    try {
      result = projectDetailMaterialController.deleteProjectMaterial(1L, 1L, mockModel, mockHttpServletRequest);
    } catch (Exception exception) {
      expectedException = exception;
    }

    // Verify
    Mockito.verify(mockModel, Mockito.times(1)).addAttribute(SessionConstants.SESSION_LOGGED_IN_USER, loggedInUserRepresentation);
    Mockito.verify(mockProjectMaterialService, Mockito.times(1)).getProjectMaterials(projectDetailCommandDTOCaptor.capture());
    Mockito.verify(mockProjectMaterialService, Mockito.times(1)).deleteProjectMaterial(deleteProjectMaterialCommandDTOCaptor.capture());
    Mockito.verify(mockModel, Mockito.times(1)).addAttribute(ModelConstants.MODEL_PROJECT_DETAIL, projectDetailRepresentation);
    Mockito.verify(mockModel, Mockito.times(1)).addAttribute(Mockito.eq(ModelConstants.MODEL_ADD_PROJECT_MATERIAL), Mockito.any(ProjectDetailMaterialRepresentation.class));
    Mockito.verify(mockModel, Mockito.times(1)).addAttribute(Mockito.eq(ModelConstants.MODEL_ADD_PROJECT_BUDGET), Mockito.any(ProjectDetailRepresentation.class));

    // Assertions
    assert expectedException == null;
    assert (Long) projectDetailCommandDTOCaptor.getValue().get(ContextConstants.CONTEXT_PROJECT_ID) == 1L;
    assert (Long) deleteProjectMaterialCommandDTOCaptor.getValue().get(ContextConstants.CONTEXT_MATERIAL_ID) == 1L;
    assert result.equals(ViewConstants.VIEW_MATERIAL);
  }

  @Test
  public void testDeleteProjectMaterialByIdException() {
    // Initialize
    Model mockModel = Mockito.mock(Model.class);
    HttpServletRequest mockHttpServletRequest = Mockito.mock(HttpServletRequest.class);
    HttpSession mockHttpSession = Mockito.mock(HttpSession.class);

    ProjectMaterialService mockProjectMaterialService = Mockito.mock(ProjectMaterialService.class);

    ArgumentCaptor<CommandDTO> projectDetailCommandDTOCaptor = ArgumentCaptor.forClass(CommandDTO.class);
    ArgumentCaptor<CommandDTO> updateProjectMaterialCommandDTOCaptor = ArgumentCaptor.forClass(CommandDTO.class);
    ArgumentCaptor<ErrorsRepresentation> errorsRepresentationCaptor = ArgumentCaptor.forClass(ErrorsRepresentation.class);

    ProjectDetailMaterialController projectDetailMaterialController = new ProjectDetailMaterialController();
    projectDetailMaterialController.projectDetailService = mockProjectMaterialService;

    LoggedInUserRepresentation loggedInUserRepresentation = LoggedInUserRepresentation.builder().userName("username").build();
    ProjectDetailRepresentation projectDetailRepresentation = ProjectDetailRepresentation.builder().id(1L).build();

    CommandDTO responseCommandDTO = CommandDTO
        .builder()
        .add(ContextConstants.CONTEXT_PROJECT_DETAIL, projectDetailRepresentation)
        .build();

    Map<String, String> fieldMessages = new HashMap<>();
    fieldMessages.put("field1", "message1");
    DeleteProjectMaterialFailedException responseException = new DeleteProjectMaterialFailedException();
    responseException.setFieldMessages(fieldMessages);
    responseException.setMessages(Collections.singletonList("message1"));

    // Define Mocks
    Mockito.when(mockHttpServletRequest.getSession()).thenReturn(mockHttpSession);
    Mockito.when(mockHttpSession.getAttribute(SessionConstants.SESSION_LOGGED_IN_USER)).thenReturn(loggedInUserRepresentation);
    Mockito.when(mockModel.addAttribute(SessionConstants.SESSION_LOGGED_IN_USER, loggedInUserRepresentation)).thenReturn(mockModel);
    Mockito.when(mockProjectMaterialService.getProjectMaterials(Mockito.any())).thenReturn(responseCommandDTO);
    Mockito.when(mockProjectMaterialService.deleteProjectMaterial(Mockito.any())).thenThrow(responseException);
    Mockito.when(mockModel.addAttribute(ModelConstants.MODEL_PROJECT_DETAIL, projectDetailRepresentation)).thenReturn(mockModel);
    Mockito.when(mockModel.addAttribute(Mockito.eq(ModelConstants.MODEL_ADD_PROJECT_MATERIAL), Mockito.any(ProjectDetailMaterialRepresentation.class))).thenReturn(mockModel);
    Mockito.when(mockModel.addAttribute(Mockito.eq(ModelConstants.MODEL_ADD_PROJECT_BUDGET), Mockito.any(ProjectDetailRepresentation.class))).thenReturn(mockModel);
    Mockito.when(mockModel.addAttribute(Mockito.eq(ModelConstants.MODEL_DELETE_PROJECT_MATERIAL_ERRORS), Mockito.any(ErrorsRepresentation.class))).thenReturn(mockModel);

    // Execute
    Exception expectedException = null;
    String result = null;
    try {
      result = projectDetailMaterialController.deleteProjectMaterial(1L, 1L, mockModel, mockHttpServletRequest);
    } catch (Exception exception) {
      expectedException = exception;
    }

    // Verify
    Mockito.verify(mockModel, Mockito.times(1)).addAttribute(SessionConstants.SESSION_LOGGED_IN_USER, loggedInUserRepresentation);
    Mockito.verify(mockProjectMaterialService, Mockito.times(1)).getProjectMaterials(projectDetailCommandDTOCaptor.capture());
    Mockito.verify(mockProjectMaterialService, Mockito.times(1)).deleteProjectMaterial(updateProjectMaterialCommandDTOCaptor.capture());
    Mockito.verify(mockModel, Mockito.times(1)).addAttribute(ModelConstants.MODEL_PROJECT_DETAIL, projectDetailRepresentation);
    Mockito.verify(mockModel, Mockito.times(1)).addAttribute(Mockito.eq(ModelConstants.MODEL_ADD_PROJECT_MATERIAL), Mockito.any(ProjectDetailMaterialRepresentation.class));
    Mockito.verify(mockModel, Mockito.times(1)).addAttribute(Mockito.eq(ModelConstants.MODEL_ADD_PROJECT_BUDGET), Mockito.any(ProjectDetailRepresentation.class));
    Mockito.verify(mockModel, Mockito.times(1)).addAttribute(Mockito.eq(ModelConstants.MODEL_DELETE_PROJECT_MATERIAL_ERRORS), errorsRepresentationCaptor.capture());

    // Assertions
    assert expectedException == null;
    assert (Long) projectDetailCommandDTOCaptor.getValue().get(ContextConstants.CONTEXT_PROJECT_ID) == 1L;
    assert (Long) updateProjectMaterialCommandDTOCaptor.getValue().get(ContextConstants.CONTEXT_MATERIAL_ID) == 1L;
    assert result.equals(ViewConstants.VIEW_MATERIAL);
    assert errorsRepresentationCaptor.getValue().getErrorMessages().get(0).equals("message1");
    assert errorsRepresentationCaptor.getValue().getFieldErrorMessages().get("field1").equals("message1");
  }
}
