package com.doityourself.workshop.features.project.detail.controller;

import com.doityourself.workshop.common.command.CommandDTO;
import com.doityourself.workshop.common.constants.ContextConstants;
import com.doityourself.workshop.common.constants.ModelConstants;
import com.doityourself.workshop.common.constants.SessionConstants;
import com.doityourself.workshop.common.constants.ViewConstants;
import com.doityourself.workshop.common.helper.SharedProjectHelper;
import com.doityourself.workshop.common.representation.error.ErrorsRepresentation;
import com.doityourself.workshop.features.login.representation.LoggedInUserRepresentation;
import com.doityourself.workshop.features.project.detail.exception.*;
import com.doityourself.workshop.features.project.detail.representation.ProjectDetailInstructionRepresentation;
import com.doityourself.workshop.features.project.detail.representation.ProjectDetailRepresentation;
import com.doityourself.workshop.features.project.detail.service.ProjectDetailService;
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
public class ProjectDetailController {
  @Autowired
  ProjectDetailService projectDetailService;

  /**
   * Get Project Detail
   *
   * @param projectId {@link Long}
   * @param model {@link Model}
   * @param request {@link HttpServletRequest}
   * @return {@link String}
   */
  @GetMapping("/workshop/project/detail/{projectId}")
  public String getProjectDetail(@PathVariable("projectId") Long projectId,
                                 Model model, HttpServletRequest request) {
    LoggedInUserRepresentation user = (LoggedInUserRepresentation) request.getSession().getAttribute(SessionConstants.SESSION_LOGGED_IN_USER);
    model.addAttribute(SessionConstants.SESSION_LOGGED_IN_USER, user);
    try {
      CommandDTO dto = projectDetailService.getProjectDetail(
          CommandDTO.builder()
              .add(ContextConstants.CONTEXT_PROJECT_ID, projectId)
              .build()
      );
      model.addAttribute(ModelConstants.MODEL_PROJECT_DETAIL, dto.get(ContextConstants.CONTEXT_PROJECT_DETAIL));
      model.addAttribute(ModelConstants.MODEL_ADD_PROJECT_DESCRIPTION, new ProjectDetailRepresentation());
      model.addAttribute(ModelConstants.MODEL_ADD_PROJECT_INSTRUCTION, new ProjectDetailInstructionRepresentation());
      SharedProjectHelper.setSharedProjectIndicator(request, projectId);
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
    return ViewConstants.VIEW_PROJECT;
  }

  /**
   * Add Project Description
   *
   * @param projectId {@link Long}
   * @param project {@link ProjectDetailRepresentation}
   * @param model {@link Model}
   * @param request {@link HttpServletRequest}
   * @return {@link String}
   */
  @PostMapping("/workshop/project/{projectId}/description/add")
  public String saveProjectDescription(@PathVariable("projectId") Long projectId,
                                      ProjectDetailRepresentation project, Model model,
                                      HttpServletRequest request) {
    try {
      projectDetailService.saveProjectDescription(
          CommandDTO.builder()
              .add(ContextConstants.CONTEXT_PROJECT_DETAIL, project)
              .build()
      );
    } catch (SaveProjectDescriptionFailedException exception) {
      log.error("an error occurred while getting project detail", exception);
      model.addAttribute(ModelConstants.MODEL_ADD_PROJECT_DESCRIPTION, project);
      model.addAttribute(ModelConstants.MODEL_ADD_PROJECT_DESCRIPTION_ERRORS,
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
  @GetMapping("/workshop/project/{projectId}/description/edit")
  public String editProjectDescription(@PathVariable("projectId") Long projectId,
                                       Model model, HttpServletRequest request) {
    String view = getProjectDetail(projectId, model, request);
    model.addAttribute(ModelConstants.MODEL_ADD_PROJECT_DESCRIPTION, model.getAttribute(ModelConstants.MODEL_PROJECT_DETAIL));
    model.addAttribute(ModelConstants.MODEL_EDIT_PROJECT_DESCRIPTION, model.getAttribute(ModelConstants.MODEL_PROJECT_DETAIL));
    return view;
  }

  /**
   * Add Project Instruction
   *
   * @param projectInstruction {@link ProjectDetailInstructionRepresentation}
   * @param model {@link Model}
   * @param request {@link HttpServletRequest}
   * @param projectId {@link Long}
   * @return {@link String}
   */
  @PostMapping("/workshop/project/{projectId}/instruction/add")
  public String addProjectInstruction(ProjectDetailInstructionRepresentation projectInstruction,
                                      Model model, HttpServletRequest request,
                                      @PathVariable("projectId") Long projectId) {
    try {
      projectDetailService.saveProjectInstruction(
          CommandDTO.builder()
              .add(ContextConstants.CONTEXT_PROJECT_ID, projectId)
              .add(ContextConstants.CONTEXT_PROJECT_INSTRUCTION, projectInstruction)
              .build()
      );
    } catch (GetProjectDetailFailedException | SaveProjectInstructionFailedException exception) {
      log.error("an error occurred while adding project instruction", exception);
      model.addAttribute(ModelConstants.MODEL_ADD_PROJECT_INSTRUCTION, projectInstruction);
      model.addAttribute(ModelConstants.MODEL_ADD_PROJECT_INSTRUCTION_ERRORS,
          ErrorsRepresentation.builder()
              .errorMessages(exception.getMessages())
              .fieldErrorMessages(exception.getFieldMessages())
              .build()
      );
    }
    return getProjectDetail(projectId, model, request);
  }

  /**
   * Update Project Instruction
   *
   * @param projectInstruction {@link ProjectDetailInstructionRepresentation}
   * @param model {@link Model}
   * @param request {@link HttpServletRequest}
   * @param projectId {@link Long}
   * @return {@link String}
   */
  @PostMapping("/workshop/project/{projectId}/instruction/update")
  public String editProjectInstruction(ProjectDetailInstructionRepresentation projectInstruction,
                                       Model model, HttpServletRequest request,
                                       @PathVariable("projectId") Long projectId) {
    try {
      projectDetailService.updateProjectInstruction(
          CommandDTO.builder()
              .add(ContextConstants.CONTEXT_PROJECT_ID, projectId)
              .add(ContextConstants.CONTEXT_PROJECT_INSTRUCTION, projectInstruction)
              .build()
      );
    } catch (GetProjectDetailFailedException | UpdateProjectInstructionFailedException exception) {
      log.error("messages: "+ exception.getMessages());
      log.error("an error occurred while updating project instruction", exception);
      model.addAttribute(ModelConstants.MODEL_ADD_PROJECT_INSTRUCTION, projectInstruction);
      model.addAttribute(ModelConstants.MODEL_ADD_PROJECT_INSTRUCTION_ERRORS,
          ErrorsRepresentation.builder()
              .errorMessages(exception.getMessages())
              .fieldErrorMessages(exception.getFieldMessages())
              .build()
      );
    }
    return getProjectDetail(projectId, model, request);
  }


  /**
   * Show Edit Project Instruction
   *
   * @param projectId {@link Long}
   * @param instructionId {@link Long}
   * @param model {@link Model}
   * @param request {@link HttpServletRequest}
   * @return {@link String}
   */
  @GetMapping("/workshop/project/{projectId}/instruction/edit/{instructionId}")
  public String editProjectInstruction(@PathVariable("projectId") Long projectId,
                                       @PathVariable("instructionId") Long instructionId,
                                       Model model, HttpServletRequest request) {
    try {
      CommandDTO commandDTO = projectDetailService.getProjectInstruction(
          CommandDTO.builder()
              .add(ContextConstants.CONTEXT_INSTRUCTION_ID, instructionId)
              .build()
      );
      model.addAttribute(ModelConstants.MODEL_EDIT_PROJECT_INSTRUCTION, commandDTO.get(ContextConstants.CONTEXT_PROJECT_INSTRUCTION));
    } catch (GetProjectInstructionFailedException exception) {
      log.error("an error occurred while getting project instruction", exception);
      model.addAttribute(ModelConstants.MODEL_EDIT_PROJECT_INSTRUCTION_ERRORS,
          ErrorsRepresentation.builder()
              .errorMessages(exception.getMessages())
              .fieldErrorMessages(exception.getFieldMessages())
              .build()
      );
    }
    String view = getProjectDetail(projectId, model, request);
    model.addAttribute(ModelConstants.MODEL_ADD_PROJECT_INSTRUCTION, model.getAttribute(ModelConstants.MODEL_EDIT_PROJECT_INSTRUCTION));
    return view;
  }

  /**
   * Delete Project Instruction
   *
   * @param projectId {@link Long}
   * @param instructionId {@link Long}
   * @param model {@link Model}
   * @param request {@link HttpServletRequest}
   * @return {@link String}
   */
  @GetMapping("/workshop/project/{projectId}/instruction/delete/{instructionId}")
  public String deleteProjectInstruction(@PathVariable("projectId") Long projectId,
                                         @PathVariable("instructionId") Long instructionId,
                                         Model model, HttpServletRequest request) {
    try {
      projectDetailService.deleteProjectInstruction(
          CommandDTO.builder()
              .add(ContextConstants.CONTEXT_INSTRUCTION_ID, instructionId)
              .build()
      );
    } catch (DeleteProjectInstructionFailedException exception) {
      log.error("an error occurred while deleting project instruction", exception);
      model.addAttribute(ModelConstants.MODEL_DELETE_PROJECT_INSTRUCTION_ERRORS,
          ErrorsRepresentation.builder()
              .errorMessages(exception.getMessages())
              .fieldErrorMessages(exception.getFieldMessages())
              .build()
      );
    }
    return getProjectDetail(projectId, model, request);
  }
}
