package com.doityourself.workshop.features.login.controller;

import com.doityourself.workshop.common.command.CommandDTO;
import com.doityourself.workshop.common.constants.ContextConstants;
import com.doityourself.workshop.common.constants.ModelConstants;
import com.doityourself.workshop.common.constants.SessionConstants;
import com.doityourself.workshop.common.constants.ViewConstants;
import com.doityourself.workshop.common.representation.error.ErrorsRepresentation;
import com.doityourself.workshop.features.login.exception.LoginFailedException;
import com.doityourself.workshop.features.login.representation.LoggedInUserRepresentation;
import com.doityourself.workshop.features.login.representation.LoginUserRepresentation;
import com.doityourself.workshop.features.login.service.LoginService;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import org.springframework.ui.Model;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class LoginControllerTest {
  @Test
  public void testShowLogin() {
    // Initialize
    Model mockModel = Mockito.mock(Model.class);
    LoginUserRepresentation loginUserRepresentation = new LoginUserRepresentation();
    loginUserRepresentation.setUserName("username");
    LoginController loginController = new LoginController();

    // Define Mocks
    Mockito.when(mockModel.addAttribute(ModelConstants.MODEL_LOGIN_USER, loginUserRepresentation)).thenReturn(mockModel);

    // Execute
    Exception expectedException = null;
    String result = null;
    try {
      result = loginController.showLogin(loginUserRepresentation, mockModel);
    } catch (Exception exception) {
      expectedException = exception;
    }

    // Verify
    Mockito.verify(mockModel, Mockito.times(1)).addAttribute(ModelConstants.MODEL_LOGIN_USER, loginUserRepresentation);

    // Assertions
    assert expectedException == null;
    assert result.equals(ViewConstants.VIEW_LOGIN);
  }

  @Test
  public void testLogout() {
    // Initialize
    HttpServletRequest mockHttpServletRequest = Mockito.mock(HttpServletRequest.class);
    HttpSession mockHttpSession = Mockito.mock(HttpSession.class);
    Model mockModel = Mockito.mock(Model.class);
    LoginController loginController = new LoginController();

    // Define Mocks
    Mockito.when(mockHttpServletRequest.getSession(true)).thenReturn(mockHttpSession);
    Mockito.doNothing().when(mockHttpSession).removeAttribute(SessionConstants.SESSION_LOGGED_IN_USER);
    Mockito.when(mockModel.addAttribute(Mockito.eq(ModelConstants.MODEL_LOGIN_USER), Mockito.any(LoginUserRepresentation.class))).thenReturn(mockModel);

    // Execute
    Exception expectedException = null;
    String result = null;
    try {
      result = loginController.logout(mockModel, mockHttpServletRequest);
    } catch (Exception exception) {
      expectedException = exception;
    }

    // Verify
    Mockito.verify(mockHttpServletRequest, Mockito.times(1)).getSession(true);
    Mockito.verify(mockHttpSession, Mockito.times(1)).removeAttribute(SessionConstants.SESSION_LOGGED_IN_USER);
    Mockito.verify(mockModel, Mockito.times(1)).addAttribute(Mockito.eq(ModelConstants.MODEL_LOGIN_USER), Mockito.any());

    // Assertions
    assert expectedException == null;
    assert result.equals(ViewConstants.VIEW_LOGIN);
  }

  @Test
  public void testLoginRedirect() {
    // Initialize
    Model mockModel = Mockito.mock(Model.class);
    HttpServletRequest mockHttpServletRequest = Mockito.mock(HttpServletRequest.class);
    HttpSession mockHttpSession = Mockito.mock(HttpSession.class);

    ArgumentCaptor<CommandDTO> loginCommandDTOCaptor = ArgumentCaptor.forClass(CommandDTO.class);
    ArgumentCaptor<LoggedInUserRepresentation> loggedInUserRepresentationCaptor = ArgumentCaptor.forClass(LoggedInUserRepresentation.class);

    LoginUserRepresentation loginUserRepresentation = new LoginUserRepresentation();
    loginUserRepresentation.setUserName("username");

    LoginService mockLoginService = Mockito.mock(LoginService.class);

    LoginController loginController = new LoginController();
    loginController.loginService = mockLoginService;
    CommandDTO responseCommandDTO = CommandDTO.builder().add(ContextConstants.CONTEXT_LOGGED_IN_USER,
        LoggedInUserRepresentation.builder().userName("loggedinuser").build());

    // Define Mocks
    Mockito.when(mockModel.addAttribute(ModelConstants.MODEL_LOGIN_USER, loginUserRepresentation)).thenReturn(mockModel);
    Mockito.when(mockLoginService.login(Mockito.any())).thenReturn(responseCommandDTO);
    Mockito.when(mockHttpServletRequest.getSession(true)).thenReturn(mockHttpSession);
    Mockito.doNothing().when(mockHttpSession).setAttribute(SessionConstants.SESSION_LOGGED_IN_USER, responseCommandDTO);

    // Execute
    Exception expectedException = null;
    String result = null;
    try {
      result = loginController.login(loginUserRepresentation, mockModel, mockHttpServletRequest);
    } catch (Exception exception) {
      expectedException = exception;
    }

    // Verify
    Mockito.verify(mockLoginService, Mockito.times(1)).login(loginCommandDTOCaptor.capture());
    Mockito.verify(mockHttpSession, Mockito.times(1)).setAttribute(Mockito.any(), loggedInUserRepresentationCaptor.capture());

    // Assertions
    assert expectedException == null;
    assert result.equals(ViewConstants.VIEW_HOME_REDIRECT);
    assert ((LoginUserRepresentation) loginCommandDTOCaptor.getValue().get(ContextConstants.CONTEXT_LOGIN_USER)).getUserName().equals("username");
    assert loggedInUserRepresentationCaptor.getValue().getUserName().equals("loggedinuser");
  }

  @Test
  public void testLoginFailed() {
    // Initialize
    Model mockModel = Mockito.mock(Model.class);
    HttpServletRequest mockHttpServletRequest = Mockito.mock(HttpServletRequest.class);

    ArgumentCaptor<ErrorsRepresentation> errorsRepresentationCaptor = ArgumentCaptor.forClass(ErrorsRepresentation.class);
    ArgumentCaptor<LoginUserRepresentation> loginUserRepresentationCaptor = ArgumentCaptor.forClass(LoginUserRepresentation.class);

    LoginUserRepresentation loginUserRepresentation = new LoginUserRepresentation();
    loginUserRepresentation.setUserName("username");

    LoginService mockLoginService = Mockito.mock(LoginService.class);

    LoginController loginController = new LoginController();
    loginController.loginService = mockLoginService;

    Map<String, String> fieldMessages = new HashMap<>();
    fieldMessages.put("attr1", "message2");
    LoginFailedException loginFailedException = new LoginFailedException();
    loginFailedException.setMessages(Collections.singletonList("message1"));
    loginFailedException.setFieldMessages(fieldMessages);

    // Define Mocks
    Mockito.when(mockLoginService.login(Mockito.any())).thenThrow(loginFailedException);
    Mockito.when(mockModel.addAttribute(Mockito.eq(ModelConstants.MODEL_LOGIN_USER), loginUserRepresentationCaptor.capture())).thenReturn(mockModel);
    Mockito.when(mockModel.addAttribute(Mockito.eq(ModelConstants.MODEL_LOGIN_ERRORS), errorsRepresentationCaptor.capture())).thenReturn(mockModel);

    // Execute
    Exception expectedException = null;
    String result = null;
    try {
      result = loginController.login(loginUserRepresentation, mockModel, mockHttpServletRequest);
    } catch (Exception exception) {
      expectedException = exception;
    }

    // Assertions
    assert expectedException == null;
    assert result.equals(ViewConstants.VIEW_LOGIN);
    assert loginUserRepresentationCaptor.getValue().getUserName().equals("username");
    assert errorsRepresentationCaptor.getValue().getErrorMessages().get(0).equals("message1");
    assert errorsRepresentationCaptor.getValue().getFieldErrorMessages().get("attr1").equals("message2");
  }
}
