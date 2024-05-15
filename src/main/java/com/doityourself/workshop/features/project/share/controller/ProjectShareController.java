package com.doityourself.workshop.features.project.share.controller;

import com.doityourself.workshop.common.command.CommandDTO;
import com.doityourself.workshop.common.constants.ContextConstants;
import com.doityourself.workshop.common.constants.ModelConstants;
import com.doityourself.workshop.common.constants.SessionConstants;
import com.doityourself.workshop.common.representation.error.ErrorsRepresentation;
import com.doityourself.workshop.features.login.representation.LoggedInUserRepresentation;
import com.doityourself.workshop.features.project.listing.controller.ProjectListingController;
import com.doityourself.workshop.features.project.listing.representation.ProjectListingRepresentation;
import com.doityourself.workshop.features.project.share.exception.ProjectShareFailedException;
import com.doityourself.workshop.features.project.share.representation.ShareProjectRepresentation;
import com.doityourself.workshop.features.project.share.service.ProjectShareService;
import com.doityourself.workshop.features.user.exception.GetSharedUsersForProjectFailedException;
import com.doityourself.workshop.features.user.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletRequest;

/**
 * Controller to handle project share operations
 */
@Slf4j
@Controller
public class ProjectShareController {
  @Autowired
  ProjectListingController projectListingController;

  @Autowired
  ProjectShareService projectShareService;

  @Autowired
  UserService userService;

  /**
   * Share project information
   *
   * @param shareProject {@link ShareProjectRepresentation}
   * @param model {@link Model}
   * @param request {@link HttpServletRequest}
   * @return {@link String}
   */
  @PostMapping("/workshop/project/share")
  public String shareProject(ShareProjectRepresentation shareProject, Model model, HttpServletRequest request) {
    try {
      projectShareService.shareProject(
          CommandDTO.builder()
              .add(ContextConstants.CONTEXT_SHARE_PROJECT, shareProject)
              .build()
      );
      model.addAttribute(ModelConstants.MODEL_SHARE_PROJECT_SUCCESS, "Project Shared Successfully");
    } catch (ProjectShareFailedException exception) {
      log.error("an error occurred while sharing project", exception);
      model.addAttribute(ModelConstants.MODEL_SHARE_PROJECT_ERRORS,
          ErrorsRepresentation.builder()
              .errorMessages(exception.getMessages())
              .fieldErrorMessages(exception.getFieldMessages())
              .build()
      );
    }
    model.addAttribute(ModelConstants.MODEL_LISTING_OPERATION, ModelConstants.MODEL_LISTING_OPERATION_SHARE);
    return projectListingController.projects(model, request, new ProjectListingRepresentation());
  }

  /**
   * Get shared users for project
   *
   * @param projectId {@link Long}
   * @param model {@link Model}
   * @param request {@link HttpServletRequest}
   * @return {@link String}
   */
  @GetMapping("/workshop/user/shared/{projectId}")
  public String getSharedUsersForProject(@PathVariable("projectId") Long projectId, Model model, HttpServletRequest request) {
    LoggedInUserRepresentation user = (LoggedInUserRepresentation) request.getSession().getAttribute(SessionConstants.SESSION_LOGGED_IN_USER);
    try {
      CommandDTO commandDTO = userService.getSharedUsersForProject(
          CommandDTO.builder()
              .add(ContextConstants.CONTEXT_LOGGED_IN_USER, user)
              .add(ContextConstants.CONTEXT_PROJECT_ID, projectId)
              .build()
      );
      model.addAttribute(ModelConstants.MODEL_SHARE_PROJECT, new ShareProjectRepresentation());
      model.addAttribute(ModelConstants.MODEL_SHARED_USERS_FOR_PROJECT, commandDTO.get(ContextConstants.CONTEXT_SHARED_USERS_FOR_PROJECT));
    } catch (GetSharedUsersForProjectFailedException exception) {
      log.error("an error occurred while getting shared users", exception);
      model.addAttribute(ModelConstants.MODEL_SHARED_USER_ERRORS,
          ErrorsRepresentation.builder()
              .errorMessages(exception.getMessages())
              .fieldErrorMessages(exception.getFieldMessages())
              .build()
      );
    }
    return projectListingController.projects(model, request, new ProjectListingRepresentation());
  }
}
