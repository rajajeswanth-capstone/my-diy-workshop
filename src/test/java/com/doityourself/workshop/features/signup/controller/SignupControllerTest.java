package com.doityourself.workshop.features.signup.controller;

import com.doityourself.workshop.common.command.CommandDTO;
import com.doityourself.workshop.common.constants.ContextConstants;
import com.doityourself.workshop.common.constants.ModelConstants;
import com.doityourself.workshop.common.constants.SessionConstants;
import com.doityourself.workshop.common.constants.ViewConstants;
import com.doityourself.workshop.common.representation.error.ErrorsRepresentation;
import com.doityourself.workshop.features.login.representation.LoggedInUserRepresentation;
import com.doityourself.workshop.features.signup.exception.SignupFailedException;
import com.doityourself.workshop.features.signup.representation.SignupUserRepresentation;
import com.doityourself.workshop.features.signup.service.SignupService;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import org.springframework.ui.Model;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class SignupControllerTest {
  @Test
  public void testShowSignup() {
    // Initialize
    Model mockModel = Mockito.mock(Model.class);
    HttpServletRequest mockHttpServletRequest = Mockito.mock(HttpServletRequest.class);
    HttpSession mockHttpSession = Mockito.mock(HttpSession.class);

    LoggedInUserRepresentation loggedInUserRepresentation = LoggedInUserRepresentation.builder().userName("username").build();
    SignupUserRepresentation signupUserRepresentation = SignupUserRepresentation.builder().userName("username").build();

    SignupController signupController = new SignupController();

    // Define Mocks
    Mockito.when(mockHttpServletRequest.getSession()).thenReturn(mockHttpSession);
    Mockito.when(mockHttpSession.getAttribute(SessionConstants.SESSION_LOGGED_IN_USER)).thenReturn(loggedInUserRepresentation);
    Mockito.when(mockModel.addAttribute(SessionConstants.SESSION_LOGGED_IN_USER, loggedInUserRepresentation)).thenReturn(mockModel);
    Mockito.when(mockModel.addAttribute(ModelConstants.MODEL_LOGIN_USER, signupUserRepresentation)).thenReturn(mockModel);

    // Execute
    Exception expectedException = null;
    String result = null;
    try {
      result = signupController.showSignup(signupUserRepresentation, mockModel, mockHttpServletRequest);
    } catch (Exception exception) {
      expectedException = exception;
    }

    // Verify
    Mockito.verify(mockModel, Mockito.times(1)).addAttribute(SessionConstants.SESSION_LOGGED_IN_USER, loggedInUserRepresentation);
    Mockito.verify(mockModel, Mockito.times(1)).addAttribute(ModelConstants.MODEL_LOGIN_USER, signupUserRepresentation);

    // Assertions
    assert expectedException == null;
    assert result.equals(ViewConstants.VIEW_SIGNUP);
  }

  @Test
  public void testSignup() {
    // Initialize
    Model mockModel = Mockito.mock(Model.class);
    HttpServletRequest mockHttpServletRequest = Mockito.mock(HttpServletRequest.class);
    HttpSession mockHttpSession = Mockito.mock(HttpSession.class);

    ArgumentCaptor<CommandDTO> signUpCommandDTOCaptor = ArgumentCaptor.forClass(CommandDTO.class);

    LoggedInUserRepresentation loggedInUserRepresentation = LoggedInUserRepresentation.builder().userName("username").build();
    SignupUserRepresentation signupUserRepresentation = SignupUserRepresentation.builder().userName("username").build();

    SignupService mockSignupService = Mockito.mock(SignupService.class);

    SignupController signupController = new SignupController();
    signupController.signupService = mockSignupService;
    CommandDTO responseCommandDTO = CommandDTO
        .builder()
        .add(ContextConstants.CONTEXT_LOGGED_IN_USER, loggedInUserRepresentation)
        .build();

    // Define Mocks
    Mockito.when(mockHttpServletRequest.getSession(true)).thenReturn(mockHttpSession);
    Mockito.when(mockHttpSession.getAttribute(SessionConstants.SESSION_LOGGED_IN_USER)).thenReturn(loggedInUserRepresentation);
    Mockito.when(mockSignupService.signup(Mockito.any())).thenReturn(responseCommandDTO);

    // Execute
    Exception expectedException = null;
    String result = null;
    try {
      result = signupController.signup(signupUserRepresentation, mockModel, mockHttpServletRequest);
    } catch (Exception exception) {
      expectedException = exception;
    }

    // Verify
    Mockito.verify(mockHttpSession, Mockito.times(1)).setAttribute(SessionConstants.SESSION_LOGGED_IN_USER, loggedInUserRepresentation);
    Mockito.verify(mockSignupService, Mockito.times(1)).signup(signUpCommandDTOCaptor.capture());

    // Assertions
    assert expectedException == null;
    assert signUpCommandDTOCaptor.getValue().get(ContextConstants.CONTEXT_LOGIN_USER) == signupUserRepresentation;
    assert result.equals(ViewConstants.VIEW_LOGIN_REDIRECT);
  }

  @Test
  public void testSignupException() {
    // Initialize
    Model mockModel = Mockito.mock(Model.class);
    HttpServletRequest mockHttpServletRequest = Mockito.mock(HttpServletRequest.class);
    HttpSession mockHttpSession = Mockito.mock(HttpSession.class);

    ArgumentCaptor<CommandDTO> signUpCommandDTOCaptor = ArgumentCaptor.forClass(CommandDTO.class);
    ArgumentCaptor<ErrorsRepresentation> errorsRepresentationCaptor = ArgumentCaptor.forClass(ErrorsRepresentation.class);

    LoggedInUserRepresentation loggedInUserRepresentation = LoggedInUserRepresentation.builder().userName("username").build();
    SignupUserRepresentation signupUserRepresentation = SignupUserRepresentation.builder().userName("username").build();

    SignupService mockSignupService = Mockito.mock(SignupService.class);

    SignupController signupController = new SignupController();
    signupController.signupService = mockSignupService;

    Map<String, String> fieldMessages = new HashMap<>();
    fieldMessages.put("key1", "value1");
    SignupFailedException signupFailedException = new SignupFailedException();
    signupFailedException.setMessages(Collections.singletonList("message"));
    signupFailedException.setFieldMessages(fieldMessages);

    // Define Mocks
    Mockito.when(mockHttpServletRequest.getSession(true)).thenReturn(mockHttpSession);
    Mockito.when(mockHttpSession.getAttribute(SessionConstants.SESSION_LOGGED_IN_USER)).thenReturn(loggedInUserRepresentation);
    Mockito.when(mockModel.addAttribute(Mockito.eq(ModelConstants.MODEL_SIGNUP_ERRORS), errorsRepresentationCaptor.capture())).thenReturn(mockModel);
    Mockito.when(mockModel.addAttribute(ModelConstants.MODEL_LOGIN_USER, signupUserRepresentation)).thenReturn(mockModel);
    Mockito.when(mockSignupService.signup(Mockito.any())).thenThrow(signupFailedException);

    // Execute
    Exception expectedException = null;
    String result = null;
    try {
      result = signupController.signup(signupUserRepresentation, mockModel, mockHttpServletRequest);
    } catch (Exception exception) {
      expectedException = exception;
    }

    // Verify
    Mockito.verify(mockHttpSession, Mockito.times(0)).setAttribute(SessionConstants.SESSION_LOGGED_IN_USER, loggedInUserRepresentation);
    Mockito.verify(mockSignupService, Mockito.times(1)).signup(signUpCommandDTOCaptor.capture());
    Mockito.verify(mockModel, Mockito.times(1)).addAttribute(ModelConstants.MODEL_LOGIN_USER, signupUserRepresentation);

    // Assertions
    assert expectedException == null;
    assert signUpCommandDTOCaptor.getValue().get(ContextConstants.CONTEXT_LOGIN_USER) == signupUserRepresentation;
    assert result.equals(ViewConstants.VIEW_SIGNUP);
    assert errorsRepresentationCaptor.getValue().getErrorMessages().get(0).equals("message");
    assert errorsRepresentationCaptor.getValue().getFieldErrorMessages().get("key1").equals("value1");
  }
}
