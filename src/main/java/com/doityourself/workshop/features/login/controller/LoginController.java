package com.doityourself.workshop.features.login.controller;

import com.doityourself.workshop.common.command.CommandDTO;
import com.doityourself.workshop.common.constants.ContextConstants;
import com.doityourself.workshop.common.constants.ModelConstants;
import com.doityourself.workshop.common.constants.SessionConstants;
import com.doityourself.workshop.common.constants.ViewConstants;
import com.doityourself.workshop.common.representation.error.ErrorsRepresentation;
import com.doityourself.workshop.features.login.exception.LoginFailedException;
import com.doityourself.workshop.features.login.representation.LoginUserRepresentation;
import com.doityourself.workshop.features.login.service.LoginService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletRequest;

/**
 * Controller to handle login operations
 */
@Slf4j
@Controller
public class LoginController {
  @Autowired
  LoginService loginService;

  /**
   * Show login page
   *
   * @param user {@link LoginUserRepresentation}
   * @param model {@link Model}
   * @return {@link String}
   */
  @GetMapping("/login")
  public String showLogin(LoginUserRepresentation user, Model model) {
    model.addAttribute(ModelConstants.MODEL_LOGIN_USER, user);
    return ViewConstants.VIEW_LOGIN;
  }

  /**
   * Logout
   *
   * @param model {@link Model}
   * @param request {@link HttpServletRequest}
   * @return {@link String}
   */
  @GetMapping("/logout")
  public String logout(Model model, HttpServletRequest request) {
    request.getSession(true).removeAttribute(SessionConstants.SESSION_LOGGED_IN_USER);
    return showLogin(new LoginUserRepresentation(), model);
  }

  /**
   * Submit login information
   *
   * @param user {@link LoginUserRepresentation}
   * @param model {@link Model}
   * @param request {@link HttpServletRequest}
   * @return {@link String}
   */
  @PostMapping("/login")
  public String login(LoginUserRepresentation user, Model model, HttpServletRequest request) {
    log.info(user.toString());
    try {
      CommandDTO commandDTO = loginService.login(
          CommandDTO.builder()
              .add(ContextConstants.CONTEXT_LOGIN_USER, user)
              .build()
      );
      request.getSession(true).setAttribute(SessionConstants.SESSION_LOGGED_IN_USER, commandDTO.get(ContextConstants.CONTEXT_LOGGED_IN_USER));
    } catch (LoginFailedException exception) {
      log.error("an error occurred while logging in", exception);
      model.addAttribute(ModelConstants.MODEL_LOGIN_USER, user);
      model.addAttribute(ModelConstants.MODEL_LOGIN_ERRORS,
          ErrorsRepresentation.builder()
              .errorMessages(exception.getMessages())
              .fieldErrorMessages(exception.getFieldMessages())
              .build()
      );
      return ViewConstants.VIEW_LOGIN;
    }
    return ViewConstants.VIEW_HOME_REDIRECT;
  }
}
