package com.doityourself.workshop.features.project.detail.validation;

import com.doityourself.workshop.features.project.detail.exception.DeleteProjectInstructionFailedException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * Delete Project Instruction Validations
 */
@Component
public class DeleteProjectInstructionValidations {
  @Value("${project.delete.instruction.validation.id.required}")
  String projectDeleteInstructionIdRequiredErrorMessage;

  @Value("${project.delete.instruction.validation.id.fieldName}")
  String projectDeleteInstructionIdFieldName;

  /**
   * Method to validate project instruction id
   *
   * @param id {@link Long}
   */
  public void validateDeleteProjectInstructionRequest(Long id) {
    Map<String, String> fieldMessages = new HashMap<>();
    validateProjectInstructionId(id, fieldMessages);

    if (fieldMessages.size() > 0) {
      DeleteProjectInstructionFailedException exception = new DeleteProjectInstructionFailedException();
      exception.setFieldMessages(fieldMessages);
      throw exception;
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
      messages.put(projectDeleteInstructionIdFieldName, projectDeleteInstructionIdRequiredErrorMessage);
    }
  }
}
