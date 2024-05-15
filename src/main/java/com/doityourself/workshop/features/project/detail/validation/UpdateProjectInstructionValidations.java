package com.doityourself.workshop.features.project.detail.validation;

import com.doityourself.workshop.common.utility.StringUtility;
import com.doityourself.workshop.database.entities.DiyProjectInstruction;
import com.doityourself.workshop.features.project.detail.exception.UpdateProjectInstructionFailedException;
import com.doityourself.workshop.features.project.detail.representation.ProjectDetailInstructionRepresentation;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.*;

/**
 * Update Project Validations
 */
@Component
public class UpdateProjectInstructionValidations {
  @Value("${project.update.instruction.validation.failed}")
  String projectUpdateInstructionFailedErrorMessage;

  @Value("${project.update.instruction.validation.title.required}")
  String projectUpdateInstructionTitleRequiredErrorMessage;

  @Value("${project.update.instruction.validation.title.fieldName}")
  String projectUpdateInstructionTitleFieldName;

  @Value("${project.update.instruction.validation.id.required}")
  String projectUpdateInstructionIdRequiredErrorMessage;

  @Value("${project.update.instruction.validation.id.fieldName}")
  String projectUpdateInstructionIdFieldName;

  /**
   * Method to validate {@link ProjectDetailInstructionRepresentation}
   *
   * @param instruction {@link ProjectDetailInstructionRepresentation}
   */
  public void validateUpdateInstructionRequest(ProjectDetailInstructionRepresentation instruction) {
    Map<String, String> fieldMessages = new HashMap<>();
    validateTitle(instruction, fieldMessages);
    validateId(instruction, fieldMessages);

    if (fieldMessages.size() > 0) {
      UpdateProjectInstructionFailedException exception = new UpdateProjectInstructionFailedException();
      exception.setFieldMessages(fieldMessages);
      throw exception;
    }
  }

  /**
   * Method to validate entity
   *
   * @param diyProjectInstruction {@link DiyProjectInstruction}
   */
  public void validateDiyProjectInstructionEntity(DiyProjectInstruction diyProjectInstruction) {
    List<String> messages = new ArrayList<>();
    validateNullDiyProjectInstruction(diyProjectInstruction, messages);

    if (messages.size() > 0) {
      UpdateProjectInstructionFailedException exception = new UpdateProjectInstructionFailedException();
      exception.setMessages(messages);
      throw exception;
    }
  }

  /**
   * Method to validate {@link DiyProjectInstruction} entity
   *
   * @param diyProjectInstruction {@link DiyProjectInstruction}
   * @param messages {@link List}&lt;{@link String}&gt;
   */
  private void validateNullDiyProjectInstruction(DiyProjectInstruction diyProjectInstruction, List<String> messages) {
    if (Objects.isNull(diyProjectInstruction)) {
      messages.add(projectUpdateInstructionFailedErrorMessage);
    }
  }

  /**
   * Method to validate title
   *
   * @param instruction {@link ProjectDetailInstructionRepresentation}
   * @param messages {@link Map}&lt;{@link String}, {@link String}&gt;
   */
  private void validateTitle(ProjectDetailInstructionRepresentation instruction, Map<String, String> messages) {
    if (StringUtility.isEmpty(instruction.getTitle())) {
      messages.put(projectUpdateInstructionTitleFieldName, projectUpdateInstructionTitleRequiredErrorMessage);
    }
  }

  /**
   * Method to validate id
   *
   * @param instruction {@link ProjectDetailInstructionRepresentation}
   * @param messages {@link Map}&lt;{@link String}, {@link String}&gt;
   */
  private void validateId(ProjectDetailInstructionRepresentation instruction, Map<String, String> messages) {
    if (Objects.isNull(instruction.getId())) {
      messages.put(projectUpdateInstructionIdFieldName, projectUpdateInstructionIdRequiredErrorMessage);
    }
  }
}
