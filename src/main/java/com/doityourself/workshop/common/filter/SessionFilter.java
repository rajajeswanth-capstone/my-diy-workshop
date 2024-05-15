package com.doityourself.workshop.common.filter;

import lombok.extern.slf4j.Slf4j;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Objects;

/**
 * Filter responsible to check the session
 */
@Slf4j
public class SessionFilter implements Filter {
  /**
   * Check user session. If session not created, then redirect to login page
   *
   * @param servletRequest {@link ServletRequest}
   * @param servletResponse {@link ServletResponse}
   * @param filterChain {@link FilterChain}
   * @throws IOException on IO errors
   * @throws ServletException on Servlet errors
   */
  @Override
  public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
    HttpServletRequest request = (HttpServletRequest) servletRequest;
    HttpServletResponse response = (HttpServletResponse) servletResponse;
    HttpSession session = request.getSession();

    if (!Objects.isNull(session) && Objects.isNull(session.getAttribute("loggedInUser"))) {
      response.sendRedirect(request.getContextPath() + "/login");
      return;
    }
    filterChain.doFilter(servletRequest, servletResponse);
  }
}
