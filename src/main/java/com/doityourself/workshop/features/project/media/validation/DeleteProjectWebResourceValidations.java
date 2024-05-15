package com.doityourself.workshop.features.project.media.validation;

import com.doityourself.workshop.features.project.media.exception.DeleteProjectWebResourceFailedException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * Delete Project Image Validations
 */
@Component
public class DeleteProjectWebResourceValidations {
  @Value("${project.delete.webresource.validation.id.required}")
  String projectDeleteWebResourceIdRequiredErrorMessage;

  @Value("${project.delete.webresource.validation.id.fieldName}")
  String projectDeleteWebResourceIdFieldName;

  /**
   * Method to validate project web resource id
   *
   * @param id {@link Long}
   */
  public void validateDeleteProjectWebResourceRequest(Long id) {
    Map<String, String> fieldMessages = new HashMap<>();
    validateProjectWebResourceId(id, fieldMessages);

    if (fieldMessages.size() > 0) {
      DeleteProjectWebResourceFailedException exception = new DeleteProjectWebResourceFailedException();
      exception.setFieldMessages(fieldMessages);
      throw exception;
    }
  }

  /**
   * Method to validate project web resource id
   *
   * @param id {@link Long}
   * @param messages {@link Map}&lt;{@link String}, {@link String}&gt;
   */
  private void validateProjectWebResourceId(Long id, Map<String, String> messages) {
    if (Objects.isNull(id)) {
      messages.put(projectDeleteWebResourceIdFieldName, projectDeleteWebResourceIdRequiredErrorMessage);
    }
  }
}
