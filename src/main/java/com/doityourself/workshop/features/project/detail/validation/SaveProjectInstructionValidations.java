package com.doityourself.workshop.features.project.detail.validation;

import com.doityourself.workshop.common.utility.StringUtility;
import com.doityourself.workshop.database.entities.DiyProjectInstruction;
import com.doityourself.workshop.features.project.detail.exception.SaveProjectInstructionFailedException;
import com.doityourself.workshop.features.project.detail.representation.ProjectDetailInstructionRepresentation;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.*;

/**
 * Save Project Validations
 */
@Component
public class SaveProjectInstructionValidations {
  @Value("${project.save.instruction.validation.failed}")
  String projectSaveInstructionFailedErrorMessage;

  @Value("${project.save.instruction.validation.title.required}")
  String projectSaveInstructionTitleRequiredErrorMessage;

  @Value("${project.save.instruction.validation.title.fieldName}")
  String projectSaveInstructionTitleFieldName;

  /**
   * Method to validate {@link ProjectDetailInstructionRepresentation}
   *
   * @param instruction {@link ProjectDetailInstructionRepresentation}
   */
  public void validateSaveInstructionRequest(ProjectDetailInstructionRepresentation instruction) {
    Map<String, String> fieldMessages = new HashMap<>();
    validateTitle(instruction, fieldMessages);

    if (fieldMessages.size() > 0) {
      SaveProjectInstructionFailedException exception = new SaveProjectInstructionFailedException();
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
      SaveProjectInstructionFailedException exception = new SaveProjectInstructionFailedException();
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
      messages.add(projectSaveInstructionFailedErrorMessage);
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
      messages.put(projectSaveInstructionTitleFieldName, projectSaveInstructionTitleRequiredErrorMessage);
    }
  }
}
