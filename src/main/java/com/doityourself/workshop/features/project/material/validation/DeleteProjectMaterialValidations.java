package com.doityourself.workshop.features.project.material.validation;

import com.doityourself.workshop.features.project.material.exception.DeleteProjectMaterialFailedException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * Delete Project Material Validations
 */
@Component
public class DeleteProjectMaterialValidations {
  @Value("${project.delete.material.validation.id.required}")
  String projectDeleteMaterialIdRequiredErrorMessage;

  @Value("${project.delete.material.validation.id.fieldName}")
  String projectDeleteMaterialIdFieldName;

  /**
   * Method to validate project material id
   *
   * @param id {@link Long}
   */
  public void validateDeleteProjectMaterialRequest(Long id) {
    Map<String, String> fieldMessages = new HashMap<>();
    validateProjectMaterialId(id, fieldMessages);

    if (fieldMessages.size() > 0) {
      DeleteProjectMaterialFailedException exception = new DeleteProjectMaterialFailedException();
      exception.setFieldMessages(fieldMessages);
      throw exception;
    }
  }

  /**
   * Method to validate project material id
   *
   * @param id {@link Long}
   * @param messages {@link Map}&lt;{@link String}, {@link String}&gt;
   */
  private void validateProjectMaterialId(Long id, Map<String, String> messages) {
    if (Objects.isNull(id)) {
      messages.put(projectDeleteMaterialIdFieldName, projectDeleteMaterialIdRequiredErrorMessage);
    }
  }
}
