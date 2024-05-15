package com.doityourself.workshop.features.project.media.validation;

import com.doityourself.workshop.features.project.media.exception.DeleteProjectLocalResourceFailedException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * Delete Project Local Resource Validations
 */
@Component
public class DeleteProjectLocalResourceValidations {
  @Value("${project.delete.localresource.validation.id.required}")
  String projectDeleteLocalResourceIdRequiredErrorMessage;

  @Value("${project.delete.localresource.validation.id.fieldName}")
  String projectDeleteLocalResourceIdFieldName;

  /**
   * Method to validate project local resource id
   *
   * @param id {@link Long}
   */
  public void validateDeleteProjectLocalResourceRequest(Long id) {
    Map<String, String> fieldMessages = new HashMap<>();
    validateProjectLocalResourceId(id, fieldMessages);

    if (fieldMessages.size() > 0) {
      DeleteProjectLocalResourceFailedException exception = new DeleteProjectLocalResourceFailedException();
      exception.setFieldMessages(fieldMessages);
      throw exception;
    }
  }

  /**
   * Method to validate project local resource id
   *
   * @param id {@link Long}
   * @param messages {@link Map}&lt;{@link String}, {@link String}&gt;
   */
  private void validateProjectLocalResourceId(Long id, Map<String, String> messages) {
    if (Objects.isNull(id)) {
      messages.put(projectDeleteLocalResourceIdFieldName, projectDeleteLocalResourceIdRequiredErrorMessage);
    }
  }
}
