package com.doityourself.workshop.features.project.material.controller;

import com.doityourself.workshop.common.command.CommandDTO;
import com.doityourself.workshop.common.constants.ContextConstants;
import com.doityourself.workshop.common.constants.ModelConstants;
import com.doityourself.workshop.common.constants.SessionConstants;
import com.doityourself.workshop.common.constants.ViewConstants;
import com.doityourself.workshop.common.representation.error.ErrorsRepresentation;
import com.doityourself.workshop.features.login.representation.LoggedInUserRepresentation;
import com.doityourself.workshop.features.project.detail.exception.GetProjectDetailFailedException;
import com.doityourself.workshop.features.project.material.exception.*;
import com.doityourself.workshop.features.project.material.representation.ProjectDetailMaterialRepresentation;
import com.doityourself.workshop.features.project.material.representation.ProjectDetailRepresentation;
import com.doityourself.workshop.features.project.material.service.ProjectMaterialService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletRequest;

/**
 * Controller to handle project detail operations
 */
@Slf4j
@Controller
public class ProjectDetailMaterialController {
  @Autowired
  ProjectMaterialService projectDetailService;

  /**
   * Get Project Material Detail
   *
   * @param projectId {@link Long}
   * @param model {@link Model}
   * @param request {@link HttpServletRequest}
   * @return {@link String}
   */
  @GetMapping("/workshop/project/detail/material/{projectId}")
  public String getProjectDetail(@PathVariable("projectId") Long projectId,
                                 Model model, HttpServletRequest request) {
    LoggedInUserRepresentation user = (LoggedInUserRepresentation) request.getSession().getAttribute(SessionConstants.SESSION_LOGGED_IN_USER);
    model.addAttribute(SessionConstants.SESSION_LOGGED_IN_USER, user);
    try {
      CommandDTO dto = projectDetailService.getProjectMaterials(
          CommandDTO.builder()
              .add(ContextConstants.CONTEXT_PROJECT_ID, projectId)
              .build()
      );
      model.addAttribute(ModelConstants.MODEL_PROJECT_DETAIL, dto.get(ContextConstants.CONTEXT_PROJECT_DETAIL));
      model.addAttribute(ModelConstants.MODEL_ADD_PROJECT_MATERIAL, new ProjectDetailMaterialRepresentation());
      model.addAttribute(ModelConstants.MODEL_ADD_PROJECT_BUDGET, new ProjectDetailRepresentation());
    } catch (GetProjectDetailFailedException exception) {
      log.error("an error occurred while getting project detail", exception);
      model.addAttribute(ModelConstants.MODEL_PROJECT_DETAIL, new ProjectDetailRepresentation());
      model.addAttribute(ModelConstants.MODEL_ADD_PROJECT_BUDGET, new ProjectDetailRepresentation());
      model.addAttribute(ModelConstants.MODEL_GET_PROJECT_DETAIL_ERRORS,
          ErrorsRepresentation.builder()
              .errorMessages(exception.getMessages())
              .fieldErrorMessages(exception.getFieldMessages())
              .build()
      );
    }
    return ViewConstants.VIEW_MATERIAL;
  }

  /**
   * Get Project Print Material Detail
   *
   * @param projectId {@link Long}
   * @param model {@link Model}
   * @param request {@link HttpServletRequest}
   * @return {@link String}
   */
  @GetMapping("/workshop/project/print/material/{projectId}")
  public String printProjectMaterials(@PathVariable("projectId") Long projectId,
                                 Model model, HttpServletRequest request) {
    LoggedInUserRepresentation user = (LoggedInUserRepresentation) request.getSession().getAttribute(SessionConstants.SESSION_LOGGED_IN_USER);
    model.addAttribute(SessionConstants.SESSION_LOGGED_IN_USER, user);
    try {
      CommandDTO dto = projectDetailService.printProjectMaterials(
          CommandDTO.builder()
              .add(ContextConstants.CONTEXT_PROJECT_ID, projectId)
              .build()
      );
      model.addAttribute(ModelConstants.MODEL_PROJECT_DETAIL, dto.get(ContextConstants.CONTEXT_PROJECT_DETAIL));
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
    return ViewConstants.VIEW_PRINT_MATERIAL;
  }

  /**
   * Add Project Budget
   *
   * @param projectId {@link Long}
   * @param project {@link ProjectDetailRepresentation}
   * @param model {@link Model}
   * @param request {@link HttpServletRequest}
   * @return {@link String}
   */
  @PostMapping("/workshop/project/{projectId}/budget/add")
  public String addProjectBudget(@PathVariable("projectId") Long projectId,
                                 ProjectDetailRepresentation project, Model model,
                                 HttpServletRequest request) {
    try {
      projectDetailService.saveProjectBudget(
          CommandDTO.builder()
              .add(ContextConstants.CONTEXT_PROJECT_DETAIL, project)
              .build()
      );
    } catch (SaveProjectBudgetFailedException exception) {
      log.error("an error occurred while getting project budget", exception);
      model.addAttribute(ModelConstants.MODEL_ADD_PROJECT_BUDGET, project);
      model.addAttribute(ModelConstants.MODEL_ADD_PROJECT_BUDGET_ERRORS,
          ErrorsRepresentation.builder()
              .errorMessages(exception.getMessages())
              .fieldErrorMessages(exception.getFieldMessages())
              .build()
      );
    }
    return getProjectDetail(projectId, model, request);
  }

  /**
   * Add Project Budget
   *
   * @param projectId {@link Long}
   * @param model {@link Model}
   * @param request {@link HttpServletRequest}
   * @return {@link String}
   */
  @GetMapping("/workshop/project/{projectId}/budget/delete")
  public String deleteProjectBudget(@PathVariable("projectId") Long projectId,
                                 Model model,
                                 HttpServletRequest request) {
    try {
      projectDetailService.deleteProjectBudget(
          CommandDTO.builder()
              .add(ContextConstants.CONTEXT_PROJECT_ID, projectId)
              .build()
      );
    } catch (SaveProjectBudgetFailedException exception) {
      log.error("an error occurred while getting project budget", exception);
      model.addAttribute(ModelConstants.MODEL_ADD_PROJECT_BUDGET_ERRORS,
          ErrorsRepresentation.builder()
              .errorMessages(exception.getMessages())
              .fieldErrorMessages(exception.getFieldMessages())
              .build()
      );
    }
    return getProjectDetail(projectId, model, request);
  }

  /**
   * Show Edit Project Description
   *
   * @param projectId {@link Long}
   * @param model {@link Model}
   * @param request {@link HttpServletRequest}
   * @return {@link String}
   */
  @GetMapping("/workshop/project/{projectId}/budget/edit")
  public String editProjectBudget(@PathVariable("projectId") Long projectId,
                                       Model model, HttpServletRequest request) {
    String view = getProjectDetail(projectId, model, request);
    model.addAttribute(ModelConstants.MODEL_ADD_PROJECT_BUDGET, model.getAttribute(ModelConstants.MODEL_PROJECT_DETAIL));
    model.addAttribute(ModelConstants.MODEL_EDIT_PROJECT_BUDGET, model.getAttribute(ModelConstants.MODEL_PROJECT_DETAIL));
    return view;
  }

  /**
   * Add Project Material
   *
   * @param projectMaterial {@link ProjectDetailMaterialRepresentation}
   * @param model {@link Model}
   * @param request {@link HttpServletRequest}
   * @param projectId {@link Long}
   * @return {@link String}
   */
  @PostMapping("/workshop/project/{projectId}/material/add")
  public String addProjectMaterial(ProjectDetailMaterialRepresentation projectMaterial,
                                      Model model, HttpServletRequest request,
                                      @PathVariable("projectId") Long projectId) {
    try {
      projectDetailService.saveProjectMaterial(
          CommandDTO.builder()
              .add(ContextConstants.CONTEXT_PROJECT_ID, projectId)
              .add(ContextConstants.CONTEXT_PROJECT_MATERIAL, projectMaterial)
              .build()
      );
    } catch (GetProjectDetailFailedException | SaveProjectMaterialFailedException exception) {
      log.error("an error occurred while adding project material", exception);
      model.addAttribute(ModelConstants.MODEL_ADD_PROJECT_MATERIAL, projectMaterial);
      model.addAttribute(ModelConstants.MODEL_ADD_PROJECT_MATERIAL_ERRORS,
          ErrorsRepresentation.builder()
              .errorMessages(exception.getMessages())
              .fieldErrorMessages(exception.getFieldMessages())
              .build()
      );
    }
    return getProjectDetail(projectId, model, request);
  }

  /**
   * Update Project Material
   *
   * @param projectMaterial {@link ProjectDetailMaterialRepresentation}
   * @param model {@link Model}
   * @param request {@link HttpServletRequest}
   * @param projectId {@link Long}
   * @return {@link String}
   */
  @PostMapping("/workshop/project/{projectId}/material/update")
  public String editProjectMaterial(ProjectDetailMaterialRepresentation projectMaterial,
                                       Model model, HttpServletRequest request,
                                       @PathVariable("projectId") Long projectId) {
    try {
      projectDetailService.updateProjectMaterial(
          CommandDTO.builder()
              .add(ContextConstants.CONTEXT_PROJECT_ID, projectId)
              .add(ContextConstants.CONTEXT_PROJECT_MATERIAL, projectMaterial)
              .build()
      );
    } catch (GetProjectDetailFailedException | UpdateProjectMaterialFailedException exception) {
      log.error("messages: "+ exception.getMessages());
      log.error("an error occurred while updating project material", exception);
      model.addAttribute(ModelConstants.MODEL_EDIT_PROJECT_MATERIAL, projectMaterial);
      model.addAttribute(ModelConstants.MODEL_ADD_PROJECT_MATERIAL_ERRORS,
          ErrorsRepresentation.builder()
              .errorMessages(exception.getMessages())
              .fieldErrorMessages(exception.getFieldMessages())
              .build()
      );
      String view = getProjectDetail(projectId, model, request);
      model.addAttribute(ModelConstants.MODEL_ADD_PROJECT_MATERIAL, projectMaterial);
      return view;
    }
    return getProjectDetail(projectId, model, request);
  }


  /**
   * Show Edit Project Material
   *
   * @param projectId {@link Long}
   * @param materialId {@link Long}
   * @param model {@link Model}
   * @param request {@link HttpServletRequest}
   * @return {@link String}
   */
  @GetMapping("/workshop/project/{projectId}/material/edit/{materialId}")
  public String editProjectMaterial(@PathVariable("projectId") Long projectId,
                                       @PathVariable("materialId") Long materialId,
                                       Model model, HttpServletRequest request) {
    try {
      CommandDTO commandDTO = projectDetailService.getProjectMaterial(
          CommandDTO.builder()
              .add(ContextConstants.CONTEXT_MATERIAL_ID, materialId)
              .build()
      );
      model.addAttribute(ModelConstants.MODEL_EDIT_PROJECT_MATERIAL, commandDTO.get(ContextConstants.CONTEXT_PROJECT_MATERIAL));
    } catch (GetProjectMaterialFailedException exception) {
      log.error("an error occurred while getting project material", exception);
      model.addAttribute(ModelConstants.MODEL_EDIT_PROJECT_MATERIAL_ERRORS,
          ErrorsRepresentation.builder()
              .errorMessages(exception.getMessages())
              .fieldErrorMessages(exception.getFieldMessages())
              .build()
      );
    }
    String view = getProjectDetail(projectId, model, request);
    model.addAttribute(ModelConstants.MODEL_ADD_PROJECT_MATERIAL, model.getAttribute(ModelConstants.MODEL_EDIT_PROJECT_MATERIAL));
    return view;
  }

  /**
   * Delete Project Material
   *
   * @param projectId {@link Long}
   * @param materialId {@link Long}
   * @param model {@link Model}
   * @param request {@link HttpServletRequest}
   * @return {@link String}
   */
  @GetMapping("/workshop/project/{projectId}/material/delete/{materialId}")
  public String deleteProjectMaterial(@PathVariable("projectId") Long projectId,
                                      @PathVariable("materialId") Long materialId,
                                      Model model, HttpServletRequest request) {
    try {
      projectDetailService.deleteProjectMaterial(
          CommandDTO.builder()
              .add(ContextConstants.CONTEXT_MATERIAL_ID, materialId)
              .build()
      );
    } catch (DeleteProjectMaterialFailedException exception) {
      log.error("an error occurred while deleting project material", exception);
      model.addAttribute(ModelConstants.MODEL_DELETE_PROJECT_MATERIAL_ERRORS,
          ErrorsRepresentation.builder()
              .errorMessages(exception.getMessages())
              .fieldErrorMessages(exception.getFieldMessages())
              .build()
      );
    }
    return getProjectDetail(projectId, model, request);
  }
}
