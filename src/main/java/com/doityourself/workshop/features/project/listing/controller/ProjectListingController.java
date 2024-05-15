package com.doityourself.workshop.features.project.listing.controller;

import com.doityourself.workshop.common.command.CommandDTO;
import com.doityourself.workshop.common.constants.ContextConstants;
import com.doityourself.workshop.common.constants.ModelConstants;
import com.doityourself.workshop.common.constants.SessionConstants;
import com.doityourself.workshop.common.constants.ViewConstants;
import com.doityourself.workshop.common.representation.error.ErrorsRepresentation;
import com.doityourself.workshop.features.login.representation.LoggedInUserRepresentation;
import com.doityourself.workshop.features.project.listing.exception.ProjectDeleteFailedException;
import com.doityourself.workshop.features.project.listing.exception.ProjectSaveFailedException;
import com.doityourself.workshop.features.project.listing.exception.ProjectsForUserGetFailedException;
import com.doityourself.workshop.features.project.listing.helper.ProjectListingHelper;
import com.doityourself.workshop.features.project.listing.representation.ProjectListingRepresentation;
import com.doityourself.workshop.features.project.listing.service.ProjectListingService;
import com.doityourself.workshop.features.project.share.representation.ShareProjectRepresentation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;

/**
 * Controller to handle project listing operations
 */
@Slf4j
@Controller
public class ProjectListingController {
  @Autowired
  ProjectListingService projectListingService;

  /**
   * Method to display home page
   *
   * @param model {@link Model}
   * @param request {@link HttpServletRequest}
   * @param project {@link ProjectListingRepresentation}
   * @return {@link String}
   */
  @GetMapping("/workshop/home")
  public String projects(Model model, HttpServletRequest request, ProjectListingRepresentation project) {
    LoggedInUserRepresentation user = (LoggedInUserRepresentation) request.getSession().getAttribute(SessionConstants.SESSION_LOGGED_IN_USER);
    model.addAttribute(SessionConstants.SESSION_LOGGED_IN_USER, user);
    model.addAttribute(ModelConstants.MODEL_PROJECT, project);
    try {
      CommandDTO commandDTO = projectListingService.getProjects(
          CommandDTO.builder()
              .add(ContextConstants.CONTEXT_LOGGED_IN_USER, user)
              .build()
      );
      model.addAttribute(ModelConstants.MODEL_PROJECTS, commandDTO.get(ContextConstants.CONTEXT_PROJECTS));
      model.addAttribute(ModelConstants.MODEL_SHARED_PROJECTS, commandDTO.get(ContextConstants.CONTEXT_SHARED_PROJECTS));
      model.addAttribute(ModelConstants.MODEL_SHARE_PROJECT, new ShareProjectRepresentation());
      model.addAttribute(ModelConstants.MODEL_SHARED_USERS, commandDTO.get(ContextConstants.CONTEXT_SHARED_USERS));
      request.getSession().setAttribute(ModelConstants.MODEL_SHARED_PROJECTS, commandDTO.get(ContextConstants.CONTEXT_SHARED_PROJECTS));
    } catch (ProjectsForUserGetFailedException exception) {
      log.error("an error occurred while getting projects for user", exception);
      model.addAttribute(ModelConstants.MODEL_PROJECTS_FOR_USER_GET_ERRORS,
          ErrorsRepresentation.builder()
              .errorMessages(exception.getMessages())
              .fieldErrorMessages(exception.getFieldMessages())
              .build()
      );
    }
    return ViewConstants.VIEW_HOME;
  }

  /**
   * Create project
   *
   * @param project {@link ProjectListingRepresentation}
   * @param model {@link Model}
   * @param request {@link HttpServletRequest}
   * @return {@link String}
   */
  @PostMapping("/workshop/project/create")
  public String createProject(ProjectListingRepresentation project,
                              @RequestParam("projectImage") MultipartFile imageFile,
                              Model model, HttpServletRequest request) {
    LoggedInUserRepresentation user = (LoggedInUserRepresentation) request.getSession().getAttribute(SessionConstants.SESSION_LOGGED_IN_USER);
    try {
      projectListingService.saveProject(
          CommandDTO.builder()
              .add(ContextConstants.CONTEXT_LOGGED_IN_USER, user)
              .add(ContextConstants.CONTEXT_PROJECT, project)
              .add(ContextConstants.CONTEXT_PROJECT_PROJECT_IMAGE_FILE, imageFile)
              .build()
      );
      model.addAttribute(ModelConstants.MODEL_SAVE_PROJECT_DISPLAY_SEQUENCE, project.getDisplaySequence());
    } catch (ProjectSaveFailedException exception) {
      log.error("an error occurred while saving project", exception);
      ProjectListingHelper.setOperation(model, project);
      model.addAttribute(ModelConstants.MODEL_SAVE_PROJECT_ERRORS,
          ErrorsRepresentation.builder()
              .errorMessages(exception.getMessages())
              .fieldErrorMessages(exception.getFieldMessages())
              .build()
      );
      return this.projects(model, request, project);
    }
    return this.projects(model, request, new ProjectListingRepresentation());
  }

  /**
   * Delete project
   *
   * @param projectId {@link Long}
   * @param model {@link Model}
   * @param request {@link HttpServletRequest}
   * @return {@link String}
   */
  @GetMapping("/workshop/project/delete/{id}")
  public String deleteProject(@PathVariable("id") Long projectId, Model model, HttpServletRequest request) {
    try {
      projectListingService.deleteProject(
          CommandDTO.builder()
              .add(ContextConstants.CONTEXT_PROJECT_ID, projectId)
              .build()
      );
    } catch (ProjectDeleteFailedException exception) {
      log.error("an error occurred while deleting project", exception);
      model.addAttribute(ModelConstants.MODEL_DELETE_PROJECT_ERRORS,
          ErrorsRepresentation.builder()
              .errorMessages(exception.getMessages())
              .fieldErrorMessages(exception.getFieldMessages())
              .build()
      );
    }
    return this.projects(model, request, new ProjectListingRepresentation());
  }
}
