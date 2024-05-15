package com.doityourself.workshop.features.home.controller;

import com.doityourself.workshop.common.constants.SessionConstants;
import com.doityourself.workshop.common.constants.ViewConstants;
import com.doityourself.workshop.features.login.representation.LoggedInUserRepresentation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletRequest;

/**
 * Home Controller
 */
@Slf4j
@Controller
public class HomeController {
  @GetMapping("/")
  public String index(Model model, HttpServletRequest request) {
    LoggedInUserRepresentation user = (LoggedInUserRepresentation) request.getSession().getAttribute(SessionConstants.SESSION_LOGGED_IN_USER);
    model.addAttribute(SessionConstants.SESSION_LOGGED_IN_USER, user);
    return ViewConstants.VIEW_INDEX;
  }
}
