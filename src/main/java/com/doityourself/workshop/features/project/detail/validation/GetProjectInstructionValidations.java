package com.doityourself.workshop.features.project.detail.validation;

import com.doityourself.workshop.database.entities.DiyProjectInstruction;
import com.doityourself.workshop.features.project.detail.exception.GetProjectInstructionFailedException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.*;

/**
 * Get Project Instruction Validations
 */
@Component
public class GetProjectInstructionValidations {
  @Value("${project.instruction.validation.failed}")
  String projectInstructionFailedErrorMessage;

  @Value("${project.instruction.validation.id.required}")
  String projectInstructionIdRequiredErrorMessage;

  @Value("${project.instruction.validation.id.fieldName}")
  String projectInstructionIdFieldName;

  /**
   * Method to validate project instruction id
   *
   * @param id {@link Long}
   */
  public void validateGetProjectInstructionRequest(Long id) {
    Map<String, String> fieldMessages = new HashMap<>();
    validateProjectInstructionId(id, fieldMessages);

    if (fieldMessages.size() > 0) {
      GetProjectInstructionFailedException exception = new GetProjectInstructionFailedException();
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
      GetProjectInstructionFailedException exception = new GetProjectInstructionFailedException();
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
      messages.add(projectInstructionFailedErrorMessage);
    }
  }

  /**
   * Method to validate project instruction id
   *
   * @param id {@link Long}
   * @param messages {@link Map}&lt;{@link String}, {@link String}&gt;
   */
  private void validateProjectInstructionId(Long id, Map<String, String> messages) {
    if (Objects.isNull(id)) {
      messages.put(projectInstructionIdFieldName, projectInstructionIdRequiredErrorMessage);
    }
  }
}
