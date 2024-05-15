package com.doityourself.workshop.common.filter;

import com.doityourself.workshop.features.login.representation.LoginUserRepresentation;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class SessionFilterTest {
  @Test
  public void testDoFilter() throws ServletException, IOException {
    // Initialize
    HttpServletRequest mockHttpServletRequest = Mockito.mock(HttpServletRequest.class);
    HttpServletResponse mockHttpServletResponse = Mockito.mock(HttpServletResponse.class);
    HttpSession mockHttpSession = Mockito.mock(HttpSession.class);
    FilterChain mockFilterChain = Mockito.mock(FilterChain.class);
    SessionFilter sessionFilter = new SessionFilter();

    // Define Mocks
    Mockito.when(mockHttpServletRequest.getSession()).thenReturn(mockHttpSession);
    Mockito.when(mockHttpSession.getAttribute("loggedInUser")).thenReturn(new LoginUserRepresentation());
    Mockito.doNothing().when(mockFilterChain).doFilter(mockHttpServletRequest, mockHttpServletResponse);

    // Execute
    Exception expectedException = null;
    try {
      sessionFilter.doFilter(mockHttpServletRequest, mockHttpServletResponse, mockFilterChain);
    } catch (Exception exception) {
      expectedException = exception;
    }

    // Verify
    Mockito.verify(mockFilterChain, Mockito.times(1)).doFilter(Mockito.any(), Mockito.any());
    Mockito.verify(mockHttpServletResponse, Mockito.times(0)).sendRedirect(Mockito.any());

    // Assertions
    assert expectedException == null;
  }

  @Test
  public void testDoFilterRedirect() throws ServletException, IOException {
    // Initialize
    HttpServletRequest mockHttpServletRequest = Mockito.mock(HttpServletRequest.class);
    HttpServletResponse mockHttpServletResponse = Mockito.mock(HttpServletResponse.class);
    HttpSession mockHttpSession = Mockito.mock(HttpSession.class);
    FilterChain mockFilterChain = Mockito.mock(FilterChain.class);
    SessionFilter sessionFilter = new SessionFilter();

    // Define Mocks
    Mockito.when(mockHttpServletRequest.getSession()).thenReturn(mockHttpSession);
    Mockito.when(mockHttpSession.getAttribute("loggedInUser")).thenReturn(null);
    Mockito.doNothing().when(mockFilterChain).doFilter(mockHttpServletRequest, mockHttpServletResponse);

    // Execute
    Exception expectedException = null;
    try {
      sessionFilter.doFilter(mockHttpServletRequest, mockHttpServletResponse, mockFilterChain);
    } catch (Exception exception) {
      expectedException = exception;
    }

    // Verify
    Mockito.verify(mockFilterChain, Mockito.times(0)).doFilter(Mockito.any(), Mockito.any());
    Mockito.verify(mockHttpServletResponse, Mockito.times(1)).sendRedirect(Mockito.any());

    // Assertions
    assert expectedException == null;
  }
}
