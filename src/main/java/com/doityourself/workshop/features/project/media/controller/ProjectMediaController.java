package com.doityourself.workshop.features.project.media.controller;

import com.doityourself.workshop.common.command.CommandDTO;
import com.doityourself.workshop.common.constants.ContextConstants;
import com.doityourself.workshop.common.constants.ModelConstants;
import com.doityourself.workshop.common.constants.SessionConstants;
import com.doityourself.workshop.common.constants.ViewConstants;
import com.doityourself.workshop.common.enums.WebResourceType;
import com.doityourself.workshop.common.representation.error.ErrorsRepresentation;
import com.doityourself.workshop.features.login.representation.LoggedInUserRepresentation;
import com.doityourself.workshop.features.project.detail.exception.GetProjectDetailFailedException;
import com.doityourself.workshop.features.project.media.exception.*;
import com.doityourself.workshop.features.project.media.helper.ProjectMediaControllerHelper;
import com.doityourself.workshop.features.project.media.representation.ProjectDetailLocalResourceRepresentation;
import com.doityourself.workshop.features.project.media.representation.ProjectDetailRepresentation;
import com.doityourself.workshop.features.project.media.representation.ProjectDetailWebResourceRepresentation;
import com.doityourself.workshop.features.project.media.service.ProjectMediaService;
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
 * Controller to handle project image operations
 */
@Slf4j
@Controller
public class ProjectMediaController {
  @Autowired
  ProjectMediaService projectMediaService;

  /**
   * Get Project Media Detail
   *
   * @param projectId {@link Long}
   * @param model {@link Model}
   * @param request {@link HttpServletRequest}
   * @return {@link String}
   */
  @GetMapping("/workshop/project/media/{projectId}")
  public String getProjectDetail(@PathVariable("projectId") Long projectId,
                                 Model model, HttpServletRequest request) {
    LoggedInUserRepresentation user = (LoggedInUserRepresentation) request.getSession().getAttribute(SessionConstants.SESSION_LOGGED_IN_USER);
    model.addAttribute(SessionConstants.SESSION_LOGGED_IN_USER, user);
    try {
      CommandDTO dto = projectMediaService.getProjectDetail(
          CommandDTO.builder()
              .add(ContextConstants.CONTEXT_PROJECT_ID, projectId)
              .build()
      );
      model.addAttribute(ModelConstants.MODEL_PROJECT_DETAIL, dto.get(ContextConstants.CONTEXT_PROJECT_DETAIL));
      model.addAttribute(ModelConstants.MODEL_ADD_PROJECT_LOCAL_RESOURCE, new ProjectDetailLocalResourceRepresentation());
      model.addAttribute(ModelConstants.MODEL_ADD_PROJECT_WEB_RESOURCE, new ProjectDetailWebResourceRepresentation());
      model.addAttribute(ModelConstants.MODEL_MEDIA_LISTING_OPERATION, ModelConstants.MODEL_MEDIA_LISTING_OPERATION_PICTURES);
    } catch (GetProjectDetailFailedException exception) {
      log.error("an error occurred while getting project detail", exception);
      model.addAttribute(ModelConstants.MODEL_PROJECT_DETAIL, new ProjectDetailRepresentation());
      model.addAttribute(ModelConstants.MODEL_GET_PROJECT_DETAIL_ERRORS,
          ErrorsRepresentation.builder()
              .errorMessages(exception.getMessages())
              .fieldErrorMessages(exception.getFieldMessages())
              .build()
      );
    }
    return ViewConstants.VIEW_MEDIA;
  }

  /**
   * Add Project Local Resource
   *
   * @param localResource {@link ProjectDetailLocalResourceRepresentation}
   * @param model {@link Model}
   * @param request {@link HttpServletRequest}
   * @param projectId {@link Long}
   * @return {@link String}
   */
  @PostMapping("/workshop/project/{projectId}/localresource/add")
  public String addProjectLocalResource(ProjectDetailLocalResourceRepresentation localResource,
                                @RequestParam("localFile") MultipartFile localFile,
                                Model model, HttpServletRequest request,
                                @PathVariable("projectId") Long projectId) {
    try {
      CommandDTO commandDTO = projectMediaService.saveProjectLocalResource(
          CommandDTO.builder()
              .add(ContextConstants.CONTEXT_PROJECT_ID, projectId)
              .add(ContextConstants.CONTEXT_PROJECT_LOCAL_RESOURCE, localResource)
              .add(ContextConstants.CONTEXT_PROJECT_LOCAL_RESOURCE_FILE, localFile)
              .build()
      );
      ProjectMediaControllerHelper.setLocalResourceArtifact(model, (String) commandDTO.get(ContextConstants.CONTEXT_PROJECT_LOCAL_RESOURCE_ARTIFACT));
    } catch (GetProjectDetailFailedException | SaveProjectLocalResourceFailedException exception) {
      log.error("an error occurred while adding project image", exception);
      String view = getProjectDetail(projectId, model, request);
      model.addAttribute(ModelConstants.MODEL_ADD_PROJECT_LOCAL_RESOURCE, localResource);
      ProjectMediaControllerHelper.setModelListingOperationResourceTypeErrors(model, localResource.getResourceType());
      ProjectMediaControllerHelper.setModelDisplaySequence(model, localResource.getResourceType(), localResource.getDisplaySequence());
      ProjectMediaControllerHelper.setLocalResourceArtifact(model, localResource.getResourceType());
      model.addAttribute(ModelConstants.MODEL_ADD_PROJECT_LOCAL_RESOURCE_ERRORS,
          ErrorsRepresentation.builder()
              .errorMessages(exception.getMessages())
              .fieldErrorMessages(exception.getFieldMessages())
              .build()
      );
      return view;
    }
    String view = getProjectDetail(projectId, model, request);
    ProjectMediaControllerHelper.setModelListingOperationResourceTypeSuccess(model, localResource.getResourceType());
    ProjectMediaControllerHelper.setModelDisplaySequence(model, localResource.getResourceType(), localResource.getDisplaySequence());
    return view;
  }

  /**
   * Add Project Web Resource
   *
   * @param projectWebResource {@link ProjectDetailWebResourceRepresentation}
   * @param model {@link Model}
   * @param request {@link HttpServletRequest}
   * @param projectId {@link Long}
   * @return {@link String}
   */
  @PostMapping("/workshop/project/{projectId}/webresource/add")
  public String addProjectWebResource(ProjectDetailWebResourceRepresentation projectWebResource,
                                Model model, HttpServletRequest request,
                                @PathVariable("projectId") Long projectId) {
    try {
      projectMediaService.saveProjectWebResource(
          CommandDTO.builder()
              .add(ContextConstants.CONTEXT_PROJECT_ID, projectId)
              .add(ContextConstants.CONTEXT_PROJECT_WEB_RESOURCE, projectWebResource)
              .build()
      );
      model.addAttribute(ModelConstants.MODEL_PROJECT_WEB_RESOURCE_ARTIFACT, true);
    } catch (GetProjectDetailFailedException | SaveProjectWebResourceFailedException exception) {
      log.error("an error occurred while adding project web resource", exception);
      String view = getProjectDetail(projectId, model, request);
      ProjectMediaControllerHelper.setModelListingOperationWebResourceErrors(model);
      model.addAttribute(ModelConstants.MODEL_SAVE_WEBLINK_DISPLAY_SEQUENCE, projectWebResource.getDisplaySequence());
      model.addAttribute(ModelConstants.MODEL_ADD_PROJECT_WEB_RESOURCE, projectWebResource);
      model.addAttribute(ModelConstants.MODEL_PROJECT_WEB_RESOURCE_ARTIFACT, true);
      model.addAttribute(ModelConstants.MODEL_ADD_PROJECT_WEB_RESOURCE_ERRORS,
          ErrorsRepresentation.builder()
              .errorMessages(exception.getMessages())
              .fieldErrorMessages(exception.getFieldMessages())
              .build()
      );
      return view;
    }
    String view = getProjectDetail(projectId, model, request);
    ProjectMediaControllerHelper.setModelListingOperationWebResourceSuccess(model);
    model.addAttribute(ModelConstants.MODEL_SAVE_WEBLINK_DISPLAY_SEQUENCE, projectWebResource.getDisplaySequence());
    return view;
  }

  /**
   * Update Project Local Resource
   *
   * @param localResource {@link ProjectDetailLocalResourceRepresentation}
   * @param localFile {@link MultipartFile}
   * @param model {@link Model}
   * @param request {@link HttpServletRequest}
   * @param projectId {@link Long}
   * @return {@link String}
   */
  @PostMapping("/workshop/project/{projectId}/localresource/update")
  public String updateProjectLocalResource(ProjectDetailLocalResourceRepresentation localResource,
                                 @RequestParam("localFile") MultipartFile localFile,
                                 Model model, HttpServletRequest request,
                                 @PathVariable("projectId") Long projectId) {
    try {
      CommandDTO commandDTO = projectMediaService.updateProjectLocalResource(
          CommandDTO.builder()
              .add(ContextConstants.CONTEXT_PROJECT_ID, projectId)
              .add(ContextConstants.CONTEXT_PROJECT_LOCAL_RESOURCE, localResource)
              .add(ContextConstants.CONTEXT_PROJECT_LOCAL_RESOURCE_FILE, localFile)
              .build()
      );
      ProjectMediaControllerHelper.setLocalResourceArtifact(model, (String) commandDTO.get(ContextConstants.CONTEXT_PROJECT_LOCAL_RESOURCE_ARTIFACT));
    } catch (GetProjectDetailFailedException | UpdateProjectLocalResourceFailedException exception) {
      log.error("messages: "+ exception.getMessages());
      log.error("an error occurred while updating project local resource", exception);
      String view = getProjectDetail(projectId, model, request);
      ProjectMediaControllerHelper.setLocalResourceArtifact(model, localResource.getResourceType());
      model.addAttribute(ModelConstants.MODEL_EDIT_PROJECT_LOCAL_RESOURCE, localResource);
      model.addAttribute(ModelConstants.MODEL_ADD_PROJECT_LOCAL_RESOURCE, localResource);
      model.addAttribute(ModelConstants.MODEL_ADD_PROJECT_LOCAL_RESOURCE_ERRORS,
          ErrorsRepresentation.builder()
              .errorMessages(exception.getMessages())
              .fieldErrorMessages(exception.getFieldMessages())
              .build()
      );
      return view;
    }
    return getProjectDetail(projectId, model, request);
  }

  /**
   * Update Project Web Resource
   *
   * @param webResource {@link ProjectDetailWebResourceRepresentation}
   * @param model {@link Model}
   * @param request {@link HttpServletRequest}
   * @param projectId {@link Long}
   * @return {@link String}
   */
  @PostMapping("/workshop/project/{projectId}/webresource/update")
  public String updateProjectWebResource(ProjectDetailWebResourceRepresentation webResource,
                                 Model model, HttpServletRequest request,
                                 @PathVariable("projectId") Long projectId) {
    try {
      projectMediaService.updateProjectWebResource(
          CommandDTO.builder()
              .add(ContextConstants.CONTEXT_PROJECT_ID, projectId)
              .add(ContextConstants.CONTEXT_PROJECT_WEB_RESOURCE, webResource)
              .build()
      );
      model.addAttribute(ModelConstants.MODEL_PROJECT_WEB_RESOURCE_ARTIFACT, true);
    } catch (GetProjectDetailFailedException | UpdateProjectWebResourceFailedException exception) {
      log.error("messages: "+ exception.getMessages());
      log.error("an error occurred while updating project web resource", exception);
      String view = getProjectDetail(projectId, model, request);
      model.addAttribute(ModelConstants.MODEL_PROJECT_WEB_RESOURCE_ARTIFACT, true);
      model.addAttribute(ModelConstants.MODEL_EDIT_PROJECT_WEB_RESOURCE, webResource);
      model.addAttribute(ModelConstants.MODEL_ADD_PROJECT_WEB_RESOURCE, webResource);
      model.addAttribute(ModelConstants.MODEL_ADD_PROJECT_WEB_RESOURCE_ERRORS,
          ErrorsRepresentation.builder()
              .errorMessages(exception.getMessages())
              .fieldErrorMessages(exception.getFieldMessages())
              .build()
      );
      return view;
    }
    return getProjectDetail(projectId, model, request);
  }


  /**
   * Show Edit Project Local Resource
   *
   * @param projectId {@link Long}
   * @param localResourceId {@link Long}
   * @param model {@link Model}
   * @param request {@link HttpServletRequest}
   * @return {@link String}
   */
  @GetMapping("/workshop/project/{projectId}/localresource/edit/{localResourceId}/{localResourceType}")
  public String editProjectLocalResource(@PathVariable("projectId") Long projectId,
                                         @PathVariable("localResourceId") Long localResourceId,
                                         @PathVariable("localResourceType") String localResourceType,
                                         Model model, HttpServletRequest request) {
    try {
      CommandDTO commandDTO = projectMediaService.getProjectLocalResource(
          CommandDTO.builder()
              .add(ContextConstants.CONTEXT_LOCAL_RESOURCE_ID, localResourceId)
              .build()
      );
      model.addAttribute(ModelConstants.MODEL_EDIT_PROJECT_LOCAL_RESOURCE, commandDTO.get(ContextConstants.CONTEXT_PROJECT_LOCAL_RESOURCE));
      ProjectMediaControllerHelper.setLocalResourceArtifact(model, (String) commandDTO.get(ContextConstants.CONTEXT_PROJECT_LOCAL_RESOURCE_ARTIFACT));
    } catch (GetProjectLocalResourceFailedException exception) {
      log.error("an error occurred while getting project local resource", exception);
      ProjectMediaControllerHelper.setLocalResourceArtifact(model, localResourceType);
      model.addAttribute(ModelConstants.MODEL_EDIT_PROJECT_LOCAL_RESOURCE_ERRORS,
          ErrorsRepresentation.builder()
              .errorMessages(exception.getMessages())
              .fieldErrorMessages(exception.getFieldMessages())
              .build()
      );
    }
    String view = getProjectDetail(projectId, model, request);
    model.addAttribute(ModelConstants.MODEL_ADD_PROJECT_LOCAL_RESOURCE, model.getAttribute(ModelConstants.MODEL_EDIT_PROJECT_LOCAL_RESOURCE));
    return view;
  }

  /**
   * View Project Local Resource
   *
   * @param projectId {@link Long}
   * @param localResourceId {@link Long}
   * @param model {@link Model}
   * @param request {@link HttpServletRequest}
   * @return {@link String}
   */
  @GetMapping("/workshop/project/{projectId}/localresource/view/{localResourceId}/{localResourceType}/{displaySequence}")
  public String openLocalResource(@PathVariable("projectId") Long projectId,
                                  @PathVariable("localResourceId") Long localResourceId,
                                  @PathVariable("localResourceType") String localResourceType,
                                  @PathVariable("displaySequence") Integer displaySequence,
                                  Model model, HttpServletRequest request) {
    CommandDTO commandDTO = null;
    try {
      commandDTO = projectMediaService.openFile(
          CommandDTO.builder()
              .add(ContextConstants.CONTEXT_PROJECT_ID, projectId)
              .add(ContextConstants.CONTEXT_LOCAL_RESOURCE_ID, localResourceId)
              .build()
      );
    } catch (GetProjectLocalResourceFailedException exception) {
      log.error("an error occurred while getting project local resource", exception);
      ProjectMediaControllerHelper.setLocalResourceArtifact(model, localResourceType);
      model.addAttribute(ModelConstants.MODEL_EDIT_PROJECT_LOCAL_RESOURCE_ERRORS,
          ErrorsRepresentation.builder()
              .errorMessages(exception.getMessages())
              .fieldErrorMessages(exception.getFieldMessages())
              .build()
      );
    }
    String view = getProjectDetail(projectId, model, request);
    ProjectMediaControllerHelper.setOpenLocalResourceModelDetails(model, commandDTO, displaySequence);
    ProjectMediaControllerHelper.setLocalResourceArtifact(model, localResourceType);
    return view;
  }

  /**
   * Show Edit Project Web Resource
   *
   * @param projectId {@link Long}
   * @param webResourceId {@link Long}
   * @param model {@link Model}
   * @param request {@link HttpServletRequest}
   * @return {@link String}
   */
  @GetMapping("/workshop/project/{projectId}/webresource/edit/{webResourceId}")
  public String editProjectWebResource(@PathVariable("projectId") Long projectId,
                                 @PathVariable("webResourceId") Long webResourceId,
                                 Model model, HttpServletRequest request) {
    try {
      CommandDTO commandDTO = projectMediaService.getProjectWebResource(
          CommandDTO.builder()
              .add(ContextConstants.CONTEXT_WEB_RESOURCE_ID, webResourceId)
              .build()
      );
      model.addAttribute(ModelConstants.MODEL_PROJECT_WEB_RESOURCE_ARTIFACT, true);
      model.addAttribute(ModelConstants.MODEL_EDIT_PROJECT_WEB_RESOURCE, commandDTO.get(ContextConstants.CONTEXT_PROJECT_WEB_RESOURCE));
    } catch (GetProjectWebResourceFailedException exception) {
      log.error("an error occurred while getting project web resource", exception);
      model.addAttribute(ModelConstants.MODEL_PROJECT_WEB_RESOURCE_ARTIFACT, true);
      model.addAttribute(ModelConstants.MODEL_EDIT_PROJECT_WEB_RESOURCE_ERRORS,
          ErrorsRepresentation.builder()
              .errorMessages(exception.getMessages())
              .fieldErrorMessages(exception.getFieldMessages())
              .build()
      );
    }
    String view = getProjectDetail(projectId, model, request);
    model.addAttribute(ModelConstants.MODEL_ADD_PROJECT_WEB_RESOURCE, model.getAttribute(ModelConstants.MODEL_EDIT_PROJECT_WEB_RESOURCE));
    return view;
  }

  /**
   * Delete Project Image
   *
   * @param projectId {@link Long}
   * @param localResourceId {@link Long}
   * @param model {@link Model}
   * @param request {@link HttpServletRequest}
   * @return {@link String}
   */
  @GetMapping("/workshop/project/{projectId}/localresource/delete/{localResourceId}/{localResourceType}")
  public String deleteProjectLocalResource(@PathVariable("projectId") Long projectId,
                                           @PathVariable("localResourceId") Long localResourceId,
                                           @PathVariable("localResourceType") String localResourceType,
                                           Model model, HttpServletRequest request) {
    try {
      projectMediaService.deleteProjectLocalResource(
          CommandDTO.builder()
              .add(ContextConstants.CONTEXT_LOCAL_RESOURCE_ID, localResourceId)
              .build()
      );
      ProjectMediaControllerHelper.setLocalResourceArtifact(model, localResourceType);
    } catch (GetProjectLocalResourceFailedException | DeleteProjectLocalResourceFailedException exception) {
      log.error("an error occurred while deleting project local resource", exception);
      ProjectMediaControllerHelper.setLocalResourceArtifact(model, localResourceType);
      model.addAttribute(ModelConstants.MODEL_DELETE_PROJECT_LOCAL_RESOURCE_ERRORS,
          ErrorsRepresentation.builder()
              .errorMessages(exception.getMessages())
              .fieldErrorMessages(exception.getFieldMessages())
              .build()
      );
    }
    String view = getProjectDetail(projectId, model, request);
    ProjectMediaControllerHelper.setModelListingOperationResourceTypeSuccess(model, localResourceType);
    ProjectMediaControllerHelper.setModelDisplaySequence(model, localResourceType, 1);
    return view;
  }

  /**
   * Delete Project Web Resource
   *
   * @param projectId {@link Long}
   * @param webResourceId {@link Long}
   * @param model {@link Model}
   * @param request {@link HttpServletRequest}
   * @return {@link String}
   */
  @GetMapping("/workshop/project/{projectId}/webresource/delete/{webResourceId}")
  public String deleteProjectWebResource(@PathVariable("projectId") Long projectId,
                                   @PathVariable("webResourceId") Long webResourceId,
                                   Model model, HttpServletRequest request) {
    try {
      projectMediaService.deleteProjectWebResource(
          CommandDTO.builder()
              .add(ContextConstants.CONTEXT_WEB_RESOURCE_ID, webResourceId)
              .build()
      );
      model.addAttribute(ModelConstants.MODEL_PROJECT_WEB_RESOURCE_ARTIFACT, true);
    } catch (GetProjectLocalResourceFailedException | DeleteProjectWebResourceFailedException exception) {
      log.error("an error occurred while deleting project web resource", exception);
      model.addAttribute(ModelConstants.MODEL_PROJECT_WEB_RESOURCE_ARTIFACT, true);
      model.addAttribute(ModelConstants.MODEL_DELETE_PROJECT_WEB_RESOURCE_ERRORS,
          ErrorsRepresentation.builder()
              .errorMessages(exception.getMessages())
              .fieldErrorMessages(exception.getFieldMessages())
              .build()
      );
    }
    String view = getProjectDetail(projectId, model, request);
    model.addAttribute(ModelConstants.MODEL_MEDIA_LISTING_OPERATION, WebResourceType.WEB.getValue());
    model.addAttribute(ModelConstants.MODEL_SAVE_WEBLINK_DISPLAY_SEQUENCE, 1);
    return view;
  }
}
