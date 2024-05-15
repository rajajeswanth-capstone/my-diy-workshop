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
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletRequest;

/**
 * Controller to handle signup operations
 */
@Slf4j
@Controller
public class SignupController {
  @Autowired
  SignupService signupService;

  /**
   * Show signup page
   *
   * @param user {@link SignupUserRepresentation}
   * @param model {@link Model}
   * @return {@link String}
   */
  @GetMapping("/signup")
  public String showSignup(SignupUserRepresentation user, Model model, HttpServletRequest request) {
    LoggedInUserRepresentation loggedInUserRepresentation = (LoggedInUserRepresentation) request.getSession().getAttribute(SessionConstants.SESSION_LOGGED_IN_USER);
    model.addAttribute(SessionConstants.SESSION_LOGGED_IN_USER, loggedInUserRepresentation);
    model.addAttribute(ModelConstants.MODEL_LOGIN_USER, user);
    return ViewConstants.VIEW_SIGNUP;
  }

  /**
   * Submit signup information
   *
   * @param user {@link SignupUserRepresentation}
   * @param model {@link Model}
   * @return {@link String}
   */
  @PostMapping("/signup")
  public String signup(SignupUserRepresentation user, Model model, HttpServletRequest request) {
    log.info(user.toString());
    try {
      CommandDTO commandDTO = signupService.signup(
          CommandDTO.builder()
              .add(ContextConstants.CONTEXT_LOGIN_USER, user)
              .build()
      );
      request.getSession(true).setAttribute(SessionConstants.SESSION_LOGGED_IN_USER, commandDTO.get(ContextConstants.CONTEXT_LOGGED_IN_USER));
    } catch (SignupFailedException exception) {
      log.error("an error occurred while signing up", exception);
      model.addAttribute(ModelConstants.MODEL_LOGIN_USER, user);
      model.addAttribute(ModelConstants.MODEL_SIGNUP_ERRORS,
          ErrorsRepresentation.builder()
              .fieldErrorMessages(exception.getFieldMessages())
              .errorMessages(exception.getMessages())
              .build()
      );
      return ViewConstants.VIEW_SIGNUP;
    }
    return ViewConstants.VIEW_LOGIN_REDIRECT;
  }
}
