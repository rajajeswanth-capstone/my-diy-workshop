package com.doityourself.workshop.features.home.controller;

import com.doityourself.workshop.common.constants.SessionConstants;
import com.doityourself.workshop.common.constants.ViewConstants;
import com.doityourself.workshop.features.login.representation.LoggedInUserRepresentation;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.ui.Model;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class HomeControllerTest {
  @Test
  public void testIndex() {
    // Initialize
    HttpServletRequest mockHttpServletRequest = Mockito.mock(HttpServletRequest.class);
    HttpSession mockHttpSession = Mockito.mock(HttpSession.class);
    Model mockModel = Mockito.mock(Model.class);
    LoggedInUserRepresentation loggedInUserRepresentation = LoggedInUserRepresentation.builder().userName("test").build();
    HomeController homeController = new HomeController();

    // Define Mocks
    Mockito.when(mockHttpServletRequest.getSession()).thenReturn(mockHttpSession);
    Mockito.when(mockHttpSession.getAttribute(SessionConstants.SESSION_LOGGED_IN_USER)).thenReturn(loggedInUserRepresentation);
    Mockito.when(mockModel.addAttribute(SessionConstants.SESSION_LOGGED_IN_USER, loggedInUserRepresentation)).thenReturn(mockModel);

    // Execute
    Exception expectedException = null;
    String result = null;
    try {
      result = homeController.index(mockModel, mockHttpServletRequest);
    } catch (Exception exception) {
      expectedException = exception;
    }

    // Verify
    Mockito.verify(mockHttpServletRequest, Mockito.times(1)).getSession();
    Mockito.verify(mockModel, Mockito.times(1)).addAttribute(SessionConstants.SESSION_LOGGED_IN_USER, loggedInUserRepresentation);

    // Assertions
    assert expectedException == null;
    assert result.equals(ViewConstants.VIEW_INDEX);
  }
}
